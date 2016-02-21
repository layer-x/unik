
NAME = uvgroovy/rump-go-stager
VERSION = lastest

.PHONY: all build run

all: build

build:
	docker build -t $(NAME):$(VERSION)

test: build
	docker run --rm --privileged -v /dev:/dev -v ~/Work/cf/unikernel/testnginx/:/unikernel uvgroovy/rump-go-stager -v vol1:/etc -v vol2:/data
