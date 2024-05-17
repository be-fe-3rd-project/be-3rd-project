#!/bin/bash

sudo chown -R ec2-user:ec2-user /home/ec2-user/spring
chmod +x /home/ec2-user/spring/scripts/stop.sh
chmod +x /home/ec2-user/spring/scripts/start.sh
chmod 666 /home/ec2-user/spring/stop.log
chmod 666 /home/ec2-user/spring/start.log
chmod 666 /home/ec2-user/spring/application.log
chmod 666 /home/ec2-user/spring/error.log
