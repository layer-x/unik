def create_iam_role region
  begin
    require 'aws-sdk'
  rescue LoadError
    abort("vagrant Unik requires installation of the aws-sdk gem. please run 'vagrant plugin install aws-sdk' and try again")
  end

  iam = Aws::IAM::Client.new(region: region)

  begin
    role_resp = iam.create_role({
        :role_name => 'UNIK_BACKEND',
        :assume_role_policy_document => '{
          "Version": "2012-10-17",
          "Statement": {
            "Effect": "Allow",
            "Principal": {"Service": "ec2.amazonaws.com"},
            "Action": "sts:AssumeRole"
          }
        }',
      })
    puts 'created IAM role "UNIK_BACKEND"'
  rescue Aws::IAM::Errors::EntityAlreadyExists
    puts 'IAM role "UNIK_BACKEND" already exists'
  end

  begin
    attach_policy_resp = iam.attach_role_policy({
      role_name: "UNIK_BACKEND",
      policy_arn: "arn:aws:iam::aws:policy/AmazonEC2FullAccess",
    })
    puts 'attached EC2FullAccess policy to role'
  rescue Aws::IAM::Errors::LimitExceeded
    puts 'policy already attached to role'
  end

  begin
    instance_profile_resp = iam.create_instance_profile(:instance_profile_name => 'UNIK_INSTANCE_PROFILE')
    puts 'created instance profile "UNIK_INSTANCE_PROFILE"'
  rescue Aws::IAM::Errors::EntityAlreadyExists
    instance_profile_resp = iam.get_instance_profile({
        instance_profile_name: "UNIK_INSTANCE_PROFILE",
    })
    puts 'instance profile "UNIK_INSTANCE_PROFILE" already exists'
  end

  begin
    iam.add_role_to_instance_profile({
        instance_profile_name: "UNIK_INSTANCE_PROFILE",
        role_name: "UNIK_BACKEND",
    })
    puts 'attached role "UNIK_BACKEND" to instance profile "UNIK_INSTANCE_PROFILE"'
  rescue Aws::IAM::Errors::LimitExceeded
    puts 'instance profile "UNIK_INSTANCE_PROFILE" already has attached role "UNIK_BACKEND"'
  end

  instance_profile_resp[:instance_profile][:arn]
end
