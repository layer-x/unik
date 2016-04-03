#!/usr/bin/env bash
ssh unik@unikip "sudo sudo apt-get update -y  && sudo apt-get install libxen-dev curl git build-essential rsync"
rsync --verbose --archive --delete -z --copy-links --no-owner --no-group --rsync-path sudo rsync -e ssh -p 22 -o StrictHostKeyChecking=no -o IdentitiesOnly=true -o UserKnownHostsFile=/dev/null -i '/Users/pivotal/workspace/go/src/github.com/layer-x/LayerX-Vagrant/vagrantmesos-ncalifornia.pem' --exclude .vagrant/ /Users/pivotal/workspace/go/src/github.com/layer-x/ ubuntu@ec2-54-67-96-194.us-west-1.compute.amazonaws.com:/vagrant
