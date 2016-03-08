
define buildcontainer
$(eval words = $(subst -, ,$@))
$(eval folder = $(word 1, $(words)))
$(eval framework = $(word 2, $(words)))
$(eval platform = $(word 3, $(words)))
docker build -t $@ -f $(folder)/$(framework)/Dockerfile.$(platform) $(folder)/$(framework)
endef

.PHONY: all build rumpstager rumpcompiler-base-common rumpcompiler-base-hw rumpcompiler-base-xen rumpcompiler-go-hw rumpcompiler-go-xen rumpcdebugger-gdb-hw

all: build

rumpcompiler-base-common:
	$(buildcontainer)

rumpcompiler-base-hw: rumpcompiler-base-common
	$(buildcontainer)

rumpcompiler-base-xen: rumpcompiler-base-common
	$(buildcontainer)

rumpcompiler-go-hw: rumpcompiler-base-hw
	$(buildcontainer)

rumpcompiler-go-xen: rumpcompiler-base-xen
	$(buildcontainer)

rumpcdebugger-gdb-hw: rumpcompiler-base-xen
	$(buildcontainer)

rumpstager:
 docker build -t $@ $@

build: rumpstager rumpcompiler-base-hw rumpcompiler-base-xen rumpcompiler-go-hw rumpcompiler-go-xen rumpcdebugger-gdb-hw
