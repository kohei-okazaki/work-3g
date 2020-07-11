@echo off

rem ------------------------------------------------------------------------
rem EC2インスタンスの状態を確認するバッチ
rem ------------------------------------------------------------------------

cls

aws ec2 describe-instances --instance-ids i-001ef176d1df48738 i-0e3179737400d8ba1 i-0c32e9718a22fc32b --query "{InstanceID: Reservations[].Instances[].InstanceId, PrivateIP: Reservations[].Instances[].PrivateIpAddress, PublicIP: Reservations[].Instances[].PublicIpAddress, SubnetID: Reservations[].Instances[].SubnetId, VPC: Reservations[].Instances[].VpcId}"
