#!/usr/bin/env bash
set -x
set -e
THISINSTANCEID=`wget -q -O - http://instance-data/latest/meta-data/instance-id`
THISREGION=`wget -q -O - http://instance-data/latest/dynamic/instance-identity/document | awk '/region/ {gsub(/[",]/, "", $3); print $3}'`
THISAVAILABILITYZONE=`wget -q -O - http://instance-data/latest/dynamic/instance-identity/document | awk '/availabilityZone/ {gsub(/[",]/, "", $3); print $3}'`
NAME=${UNIKERNEL_APP_NAME}-`date +"%d-%b-%Y-%s"`

echo Name : ${NAME}
echo THISREGION: ${THISREGION}
echo THISINSTANCEID: ${THISINSTANCEID}
echo THISAVAILABILITYZONE: ${THISAVAILABILITYZONE}
echo UNIKERNELFILE: ${UNIKERNELFILE}
echo UNIKERNEL_APP_NAME: ${UNIKERNEL_APP_NAME}

SIZE=$(expr $(stat $UNIKERNELFILE | awk 'NR==2 {print $2}') / 1000000000 + 1)
UNIKERNELVOLUMEID=`ec2-create-volume --availability-zone ${THISAVAILABILITYZONE} --region ${THISREGION} -s $SIZE | awk '{print $2}'`

# wait for EC2 to get its act together
echo Waiting for create volume to complete......
sleep 15

# attach the EBS volume
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-AttachVolume.html
ec2-attach-volume ${UNIKERNELVOLUMEID} --region ${THISREGION} --instance ${THISINSTANCEID} --device /dev/xvdf

echo Waiting for attach volume to complete......
sleep 15

dd if=$UNIKERNELFILE of=/dev/xvdf

# detach the EBS volume
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-DetachVolume.html
ec2-detach-volume --region ${THISREGION} ${UNIKERNELVOLUMEID}

echo Waiting for detach volume to complete......
sleep 15

##########################################################################################
###### prepare the unikernel for booting on EC2
##########################################################################################

# make a snapshot of the unikernel root block volume
# -- AMI’s cannot be created from volumes, only from snapshots
UNIKERNELSNAPSHOTID=`ec2-create-snapshot --description 'unikernel boot volume' --region ${THISREGION} ${UNIKERNELVOLUMEID} | awk '{print $2}'`

# Create image/AMI from the snapshot
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-CreateImage.html
## HAVING TROUBLE? COULD IT BE [--root-device-name name]
echo Waiting for snapshot to complete......
while [ $(ec2-describe-snapshots |grep ${UNIKERNELSNAPSHOTID}|awk '{print $4}') != "completed" ]; do
  sleep 5
done

AMIID=`ec2-register --name "${NAME}" \
--description "${NAME}" \
-a x86_64 \
-s ${UNIKERNELSNAPSHOTID} \
--region ${THISREGION} \
--virtualization-type hvm \
| awk '{print $2}'`

ec2-create-tags ${UNIKERNELSNAPSHOTID} --tag UNIKERNEL_ID=${AMIID} --tag UNIKERNEL_APP_NAME=${UNIKERNEL_APP_NAME} --region ${THISREGION}
ec2-create-tags ${AMIID} --tag UNIKERNEL_APP_NAME=${UNIKERNEL_APP_NAME} --region ${THISREGION}

##########################################################################################
###### finish
##########################################################################################

echo You can now start this instance via:
echo ec2-run-instances --region ${THISREGION} ${AMIID}
echo ""

echo ${AMIID} >> ami