#!/bin/bash

#TODO
# add meta data file to specify args, and volume mount points

# Assume we are running from AWS instance with IAM role
set -x
SUDO=sudo
UNIKERNELMOUNTPOINT=/mnt/unikernel
VOL1MOUNTPOINT=/mnt/vol1
VOL2MOUNTPOINT=/mnt/vol2
BOOTMOUNTPOINT=/mnt/boot

set -e

# in GB
SIZE=1

UNIKERNELFILE="/unikernel"
UNIKERNELFILE_ROOT="$UNIKERNELFILE/root"
VOL1FILE_ROOT="$UNIKERNELFILE/vol1"
VOL2FILE_ROOT="$UNIKERNELFILE/vol2"

GRUB_FILE=/tmp/grub.img
IMAGE_FILE=/tmp/disk.img

if [ ! "$(ls -A $UNIKERNELFILE)" ]; then
  echo "Bad usage! no partitions provided!"
  exit 1
fi


dd if=/dev/zero of=$IMAGE_FILE bs=1 count=0 seek=${SIZE}G
DEVICE=$(losetup -f --show $IMAGE_FILE)


dd if=/dev/zero of=$GRUB_FILE bs=1 count=0 seek=${SIZE}G
GRUB_DEVICE_LOOP=$(losetup -f --show $GRUB_FILE)

SECTORS=$[ $(ls -l $GRUB_FILE |cut -d' ' -f5)/512]
DEVNUM=$(ls -l $GRUB_DEVICE_LOOP|cut -d' ' -f5|tr -d ,):$(ls -l $GRUB_DEVICE_LOOP|cut -d' ' -f6)
${SUDO}  echo "0 $SECTORS linear $DEVNUM 0" | sudo dmsetup create hda
GRUB_DEVICE=/dev/mapper/hda


##########################################################################################
##########################################################################################
###### prepare block device
###### this will be the root file system for the unikernel
##########################################################################################
##########################################################################################

# create a 1 GB EBS volume using the AWS console
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-CreateVolume.html


# create the UNIKERNELMOUNTPOINT
${SUDO} mkdir -p ${UNIKERNELMOUNTPOINT}
${SUDO} mkdir -p ${BOOTMOUNTPOINT}
${SUDO} mkdir -p ${VOL1MOUNTPOINT}
${SUDO} mkdir -p ${VOL2MOUNTPOINT}


# partition
# ${SUDO} parted --script $GRUB_DEVICE mklabel gpt
# ${SUDO} parted --script $GRUB_DEVICE mkpart no-fs 0 2
# ${SUDO} parted --script $GRUB_DEVICE mkpart ext2 ext2 2 100
# ${SUDO} parted --script $GRUB_DEVICE set  1 bios_grub on

${SUDO} parted --script $GRUB_DEVICE mklabel msdos
${SUDO} parted --script $GRUB_DEVICE mkpart primary ext2 0 100

# get second partition..
BOOT_DEVICE=/dev/mapper/$(kpartx -avs $GRUB_DEVICE | head -1 | tail -1 | cut -d' ' -f3)


# create MBR partition for grub1
# ${SUDO} gptsync $GRUB_DEVICE
# gpt sync doesnt work for us :( going to do it manually
#PARTITIONMAP=$(parted --script --machine $GRUB_DEVICE "unit s" "print"|tail -n +2)
#FIN2_SECTOR=$(echo "$PARTITIONMAP" | grep -e "^2:"|cut -d: -f3|tr -d s)
#ST2SECTOR=$(echo "$PARTITIONMAP" | grep -e "^2:"|cut -d: -f2|tr -d s)
#FIN2_SECTOR=$(echo "$PARTITIONMAP" | grep -e "^2:"|cut -d: -f3|tr -d s)

#set +e
#${SUDO} fdisk $GRUB_DEVICE <<EOF
#d
#n
#p
#2
#$ST2SECTOR
#$FIN2_SECTOR
#w
#EOF
## it will fail reading partition table cause it is gpt
#set -e

${SUDO} parted --script $DEVICE mklabel bsd
${SUDO} parted --script $DEVICE mkpart  ext2   2 100
${SUDO} parted --script $DEVICE mkpart  ext2 100 200
${SUDO} parted --script $DEVICE mkpart  ext2 200 300

set +e
for PARTI in 1 2 3; do
 ${SUDO} dmsetup remove partition${PARTI}
done
set -e

PARTITIONMAP=$(parted --machine $DEVICE "unit s" "print"|tail -n +2)
for PARTI in 1 2 3; do
     PARTNUM=$[$PARTI+0]
     SECTOR=$(echo "$PARTITIONMAP" | grep -e "^${PARTNUM}:"|cut -d: -f2|tr -d s)
     SIZE=$(echo "$PARTITIONMAP" | grep -e "^${PARTNUM}:"|cut -d: -f4|tr -d s)
     ${SUDO} dmsetup create partition${PARTI} --table "0 $SIZE linear $DEVICE $SECTOR"
done

DEVICE1=/dev/mapper/partition1
DEVICE2=/dev/mapper/partition2
DEVICE3=/dev/mapper/partition3

${SUDO} mkfs.ufs -O 2 ${DEVICE1}
${SUDO} mkfs.ufs -O 2 ${DEVICE2}

# mount the device
${SUDO} fuse-ufs ${DEVICE1} ${VOL1MOUNTPOINT} -o rw
${SUDO} fuse-ufs ${DEVICE2} ${VOL2MOUNTPOINT} -o rw

${SUDO} cp -r ${VOL1FILE_ROOT}/* ${VOL1MOUNTPOINT}
${SUDO} cp -r ${VOL2FILE_ROOT}/* ${VOL2MOUNTPOINT}

if [ "$AWS" != true ]; then
NETCFG='
 "net" :  {
   "if":  "vioif0",
   "type": "inet",
   "method": "static",
   "addr": "10.0.1.101",
   "mask": "8",
 }'
else
NETCFG='
  "net" :  {
   "if":  "xenif0",
   "cloner": "true",
   "type": "inet",
   "method": "dhcp",
  }'
fi


if [ "$AWS" != true ]; then
BLKCFG='
"blk" : {
  "source": "dev",
  "path": "/dev/ld1a",
  "fstype": "blk",
  "mountpoint": "/etc",
},
"blk" : {
  "source": "dev",
  "path": "/dev/ld1b",
  "fstype": "blk",
  "mountpoint": "/data",
}'
else
  BLKCFG='
   "blk" : {
      "source": "etfs",
      "path": "sda",
      "fstype": "blk",
    },
    "blk" : {
        "source": "etfs",
        "path": "sdb",
        "fstype": "blk",
    }
    '
fi

JSONCONFIG='{"cmdline":"program.bin",
'$NETCFG',
'$BLKCFG',
}'
JSONCONFIG=$(echo $JSONCONFIG |tr -d '\n')

create_boot () {
ROOTDRIVE=$1

# format the EBS volume as ext2
BOOTLABEL=bootfs
${SUDO} mkfs -L $BOOTLABEL -I 128 -t ext2 ${BOOT_DEVICE}
#Label the disk. AWS has an unofficial tutorial that does not include this step.
${SUDO} tune2fs -L $BOOTLABEL ${BOOT_DEVICE}

${SUDO} mount ${BOOT_DEVICE} ${BOOTMOUNTPOINT}

${SUDO} mkdir -p ${BOOTMOUNTPOINT}/boot/grub

${SUDO} cp -r ${UNIKERNELFILE_ROOT}/program.bin ${BOOTMOUNTPOINT}/boot/

${SUDO} cat  > ${BOOTMOUNTPOINT}/boot/grub/grub.conf <<EOF
default=0
fallback=1
timeout=1
hiddenmenu

title Unik
root $ROOTDRIVE
kernel /boot/program.bin $JSONCONFIG
EOF

${SUDO} cp ${BOOTMOUNTPOINT}/boot/grub/grub.conf  ${BOOTMOUNTPOINT}/boot/grub/menu.lst
}

create_boot "(hd0,0)"

# hd0,0 is for grub1; grub2 will ignore this anyway..
${SUDO} cat > ${BOOTMOUNTPOINT}/boot/grub/device.map <<EOF
(hd0) ${GRUB_DEVICE}
# (hd1) ${DEVICE}
EOF


# install grub!
echo GRUB_DEVICE = $GRUB_DEVICE
echo DEVICE = $DEVICE
${SUDO} grub-install --no-floppy --root-directory=${BOOTMOUNTPOINT} ${GRUB_DEVICE}

# show what is in the target
# ${SUDO} find ${UNIKERNELMOUNTPOINT}

# unmount any existing volumes
${SUDO} umount ${BOOTMOUNTPOINT}
${SUDO} umount ${VOL1MOUNTPOINT}
${SUDO} umount ${VOL2MOUNTPOINT}

set +e
for PARTI in 1 2 3; do
  ${SUDO} dmsetup remove partition${PARTI}
done
${SUDO} kpartx -d $GRUB_DEVICE
${SUDO} losetup -d $GRUB_DEVICE

${SUDO}  dmsetup remove hda

${SUDO} losetup -d $DEVICE
${SUDO} losetup -d $GRUB_DEVICE_LOOP
set -e

echo Image ready!
${SUDO} cp $GRUB_FILE $UNIKERNELFILE
${SUDO} cp $IMAGE_FILE $UNIKERNELFILE


if [ "$AWS" = false ]; then
  echo "Done, rune with:"
  echo 'qemu-system-x86_64 -drive file=/path/to/grub.img,format=raw,if=virtio -drive file=/path/to/disk.img,format=raw,if=virtio'
  exit 0
fi

# create aws imagese on iam ec2 instance
# if AWS is not explictly off, auto detect
THISREGION=`wget -q -O - http://instance-data/latest/dynamic/instance-identity/document | awk '/region/ {gsub(/[",]/, "", $3); print $3}'`

if [ "$AWS" = "true" ]; then
  if [ "$THISREGION" = "" ]; then
    exit 1
  fi
elif [ "$AWS" = "" ]; then
  if [ "$THISREGION" != "" ]; then
    AWS=true
  else
    AWS=false
    exit 0
  fi
fi

# unfortunatly wasnt able to get network on an HVM instance, not even when
# i added the ixgbe intel driver to rump run and used amazon m4 instance (that should have inhanced networking)

VTYPE="hvm"
VTYPE="paravirtual"


THISINSTANCEID=`wget -q -O - http://instance-data/latest/meta-data/instance-id`
THISAVAILABILITYZONE=`wget -q -O - http://instance-data/latest/dynamic/instance-identity/document | awk '/availabilityZone/ {gsub(/[",]/, "", $3); print $3}'`


# create and attach two volumes
BOOTVOLID=`ec2-create-volume --availability-zone ${THISAVAILABILITYZONE} -s 1 | awk '{print $2}'`
DATAVOLID=`ec2-create-volume --availability-zone ${THISAVAILABILITYZONE} -s 1 | awk '{print $2}'`

while [ $(ec2-describe-volumes |grep $BOOTVOLID|awk '{print $5}') != "available" ]; do
   sleep 5
done

while [ $(ec2-describe-volumes |grep $DATAVOLID|awk '{print $5}') != "available" ]; do
   sleep 5
done

BOOT_DEVICE=/dev/xvdg
DATA_DEVICE=/dev/xvdf
ec2-attach-volume ${DATAVOLID} --instance ${THISINSTANCEID} --device $DATA_DEVICE
ec2-attach-volume ${BOOTVOLID} --instance ${THISINSTANCEID} --device $BOOT_DEVICE

while [ ! -e $BOOT_DEVICE ]; do
  sleep 5
done

while [ ! -e $DATA_DEVICE ]; do
  sleep 5
done

# copy all the stuff we've done
dd if=$IMAGE_FILE of=$DATA_DEVICE bs=512
dd if=$GRUB_FILE  of=$BOOT_DEVICE bs=512

if [ "$VTYPE" = "paravirtual" ]; then
  case "${THISREGION}" in ap-northeast-1) KERNELID=aki-176bf516; ;; ap-southeast-1) KERNELID=aki-503e7402; ;; ap-southeast-2) KERNELID=aki-c362fff9; ;; eu-central-1) KERNELID=aki-184c7a05; ;; eu-west-1) KERNELID=aki-52a34525; ;; sa-east-1) KERNELID=aki-5553f448; ;; us-east-1) KERNELID=aki-919dcaf8; ;; us-gov-west-1) KERNELID=aki-1de98d3e; ;; us-west-1) KERNELID=aki-880531cd; ;; us-west-2) KERNELID=aki-fc8f11cc; ;; *) echo $"Error selecting pvgrub kernel for region"; exit 1; esac
  KERNELARG="--kernel ${KERNELID}"
  ROOTDEVNAME="/dev/sda"
  DATADEVNAME="/dev/sdb"
  INSTTYPE=m1.small

else
  ROOTDEVNAME="/dev/xvda"
  DATADEVNAME="/dev/xvdb"
  INSTTYPE=m4.large

fi

# detach!
ec2-detach-volume ${BOOTVOLID}
ec2-detach-volume ${DATAVOLID}

while [ -e $BOOT_DEVICE ]; do
  sleep 5
done
while [ -e $DATA_DEVICE ]; do
  sleep 5
done


BOOT_SNAPSHOTID=`ec2-create-snapshot --description 'unikernel boot volume' ${BOOTVOLID} | awk '{print $2}'`
DATA_SNAPSHOTID=`ec2-create-snapshot --description 'unikernel boot volume' ${DATAVOLID} | awk '{print $2}'`

while [ $(ec2-describe-snapshots |grep ${BOOT_SNAPSHOTID}|awk '{print $4}') != "completed" ]; do
  sleep 5
done
while [ $(ec2-describe-snapshots |grep ${DATA_SNAPSHOTID}|awk '{print $4}') != "completed" ]; do
  sleep 5
done

# Create image/AMI from the snapshot
#http://docs.aws.amazon.com/AWSEC2/latest/CommandLineReference/ApiReference-cmd-CreateImage.html
## HAVING TROUBLE? COULD IT BE [--root-device-name name]



# take name from command line or use default unique name to avoid registration clashes
NAME=${1:-unikernel-`date +"%d-%b-%Y-%s"`}
AMIID=`ec2-register --name "${NAME}" \
--description "${NAME}" \
-a x86_64 \
-s ${BOOT_SNAPSHOTID} \
--root-device-name $ROOTDEVNAME \
-b "$DATADEVNAME=${DATA_SNAPSHOTID}" \
--virtualization-type $VTYPE $KERNELARG \
| awk '{print $2}'`

##########################################################################################
###### finish
##########################################################################################
echo You can now start this instance via:
echo "INSTID=\$(ec2-run-instances --instance-type $INSTTYPE ${AMIID}|head -n 2|tail -n 1|awk '{print \$2}')"
# INSTID=$(ec2-run-instances --instance-type t2.micro ${AMIID} | head -n 2|tail -n 1 |awk '{print $2}')
echo ""
echo Check output with
echo "aws ec2 get-console-output --instance-id \$INSTID --region=$THISREGION| jq -r .Output"
echo ""
echo Don\'t forget to customise this with a security group, as the
echo default one won\'t let any inbound traffic in.

# run like this:
#
