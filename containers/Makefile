
define buildcontainer
$(eval words = $(subst -, ,$@))
$(eval folder = $(word 1, $(words)))
$(eval framework = $(word 2, $(words)))
$(eval platform = $(word 3, $(words)))
docker build -t $@ -f $(folder)/$(framework)/Dockerfile.$(platform) $(folder)/$(framework)
endef

.PHONY: all build rumpcompiler-base-hw rumpcompiler-base-xen rumpcompiler-go-hw rumpcompiler-go-xen rumpcdebugger-gdb-hw

all: build

rumpcompiler-base-hw:
	$(buildcontainer)

rumpcompiler-base-xen:
	$(buildcontainer)

rumpcompiler-go-hw: rumpcompiler-base-hw
	$(buildcontainer)

rumpcompiler-go-xen: rumpcompiler-base-xen
	$(buildcontainer)


rumpcdebugger-gdb-hw: rumpcompiler-base-xen
	$(buildcontainer)

build: rumpcompiler-base-hw rumpcompiler-base-xen rumpcompiler-go-hw rumpcompiler-go-xen rumpcdebugger-gdb-hw
