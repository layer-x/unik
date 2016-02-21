#!/bin/bash
set -x
set -e
SUDO=sudo
UNIKERNELMOUNTPOINT=/mnt/unikernel
THISINSTANCEID=`wget -q -O - http://instance-data/latest/meta-data/instance-id`
THISREGION=`wget -q -O - http://instance-data/latest/dynamic/instance-identity/document | awk '/region/ {gsub(/[",]/, "", $3); print $3}'`
THISAVAILABILITYZONE=`wget -q -O - http://instance-data/latest/dynamic/instance-identity/document | awk '/availabilityZone/ {gsub(/[",]/, "", $3); print $3}'`
case "${THISREGION}" in ap-northeast-1) KERNELID=aki-176bf516; ;; ap-southeast-1) KERNELID=aki-503e7402; ;; ap-southeast-2) KERNELID=aki-c62fff9; ;; eu-central-1) KERNELID=aki-184c7a05; ;; eu-west-1) KERNELID=aki-52a34525; ;; sa-east-1) KERNELID=aki-5553f448; ;; us-east-1) KERNELID=aki-919dcaf8; ;; us-gov-west-1) KERNELID=aki-1de98d3e; ;; us-west-1) KERNELID=aki-880531cd; ;; us-west-2) KERNELID=aki-fc8f11cc; ;; *) echo $"Error selecting pvgrub kernel for region"; exit 1; esac

# Make name unique to avoid registration clashes
NAME=${UNIKERNEL_APP_NAME}-`date +"%d-%b-%Y-%s"`

echo Name : ${NAME}
echo THISREGION: ${THISREGION}
echo THISINSTANCEID: ${THISINSTANCEID}
echo THISAVAILABILITYZONE: ${THISAVAILABILITYZONE}
echo UNIKERNELMOUNTPOINT: ${UNIKERNELMOUNTPOINT}
echo UNIKERNELFILE: ${UNIKERNELFILE}
echo UNIKERNEL_APP_NAME: ${UNIKERNEL_APP_NAME}

##########################################################################################
##########################################################################################
###### prepare block device
###### this will be the root file system for the unikernel
##########################################################################################
##########################################################################################

# create a 1 GB EBS volume using the AWS console
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-CreateVolume.html
UNIKERNELVOLUMEID=`ec2-create-volume --availability-zone ${THISAVAILABILITYZONE} --region ${THISREGION} -s 1 | awk '{print $2}'`

# wait for EC2 to get its act together
echo Waiting for create volume to complete......
sleep 10

# attach the EBS volume
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-AttachVolume.html
ec2-attach-volume ${UNIKERNELVOLUMEID} --region ${THISREGION} --instance ${THISINSTANCEID} --device /dev/xvdf

echo Waiting for attach volume to complete......
sleep 10

# unmount any existing volume at the UNIKERNELMOUNTPOINT
set +e
${SUDO} umount ${UNIKERNELMOUNTPOINT}
set -e

# create the UNIKERNELMOUNTPOINT
${SUDO} mkdir -p ${UNIKERNELMOUNTPOINT}

# format the EBS volume as ext2
${SUDO} mkfs -t ext2 /dev/xvdf

#Label the disk. AWS has an unofficial tutorial that does not include this step.
${SUDO} tune2fs -L '/' /dev/xvdf

# mount the device
${SUDO} mount /dev/xvdf ${UNIKERNELMOUNTPOINT}

# set permissions
${SUDO} chmod -R ug+rwx ${UNIKERNELMOUNTPOINT}

${SUDO} cp -r ${UNIKERNELFILE}/* ${UNIKERNELMOUNTPOINT}

# show what is in the target
${SUDO} find ${UNIKERNELMOUNTPOINT}

# unmount any existing volume at the UNIKERNELMOUNTPOINT
${SUDO} umount ${UNIKERNELMOUNTPOINT}

# detach the EBS volume
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-DetachVolume.html
ec2-detach-volume --region ${THISREGION} ${UNIKERNELVOLUMEID}


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
sleep 40

AMIID=`ec2-register --name "${NAME}" \
--description "${NAME}" \
-a x86_64 \
-s ${UNIKERNELSNAPSHOTID} \
--region ${THISREGION} \
--kernel ${KERNELID} \
--virtualization-type paravirtual \
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