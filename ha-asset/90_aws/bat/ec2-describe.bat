@echo off

rem ------------------------------------------------------------------------
rem EC2インスタンスの状態を確認するバッチ
rem ------------------------------------------------------------------------

cls

aws ec2 describe-instances --query "{InstanceID: Reservations[].Instances[].InstanceId, PrivateIP: Reservations[].Instances[].PrivateIpAddress, PublicIP: Reservations[].Instances[].PublicIpAddress, SubnetID: Reservations[].Instances[].SubnetId, VPC: Reservations[].Instances[].VpcId}"
