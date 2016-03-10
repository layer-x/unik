# List of containers

## rumpstager
Turns baked rump unikernels to VM images.

To run (from the folder where the unikernel is):

    docker run --rm -v /dev:/dev --privileged -v $PWD/:/unikernel/ rumpstager -mode vmware

## rumpcompiler

Compile code to a rump unikernel.

To run (from the folder where the code is):

    docker run --rm -v $PWD/:/opt/code/ rumpcompiler-go-hw

## rumpdebugger

Gdb ready to debug rump unikernels on qemu
this will get you a shell with gdb installed.

You can use this container to compile as well, so the gdb can do source level debugging on your code.

From OS X with docker tools installed, R like this (from the folder where the program is):

    docker run --rm -ti --net="host" -v $PWD/:/code/ rump-hw-debug

    $ /opt/gdb-7.11/gdb/gdb -ex 'target remote 192.168.99.1:1234' /code/program.bin