FROM cloudrouter/osv-builder

VOLUME /opt/code
WORKDIR /opt/code

CMD sudo cp /tmp/build/* . && \
    sudo -E CC=x86_64-rumprun-netbsd-gcc CGO_ENABLED=1 GOOS=rumprun /usr/local/go/bin/go build -buildmode=c-archive -v -a -x $(find . -name "*.go") && \
    sudo -E RUMPRUN_STUBLINK=succeed x86_64-rumprun-netbsd-gcc -g -o program mainstub.c $(find . -name "*.a") && sudo -E rm -f /opt/code/mainstub.c /opt/code/gomaincaller.go && \
    sudo -E rumprun-bake xen_pv program_xen.bin program && \
    sudo -E rumprun ec2 -I 'qnet0,xenif' -W 'qnet0,inet,dhcp' program_xen.bin && \
    sudo -E bash create_ami.sh rumprun-program.bin.ec2dir
