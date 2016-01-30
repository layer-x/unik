def destroy_iam_role region
  begin
    require "aws-sdk"
  rescue LoadError
    abort("vagrant Unik requires installation of the aws-sdk gem. please run 'vagrant plugin install aws-sdk' and try again")
  end

  iam = Aws::IAM::Client.new(region: region)

  begin
    iam.remove_role_from_instance_profile({
        instance_profile_name: "UNIK_INSTANCE_PROFILE",
        role_name: "UNIK_BACKEND",
    })
  rescue=>e
    puts "could not remove role from instance profile: #{e}"
  end

  begin
  iam.detach_role_policy({
    role_name: "UNIK_BACKEND",
    policy_arn: "arn:aws:iam::aws:policy/AmazonEC2FullAccess",
  })
  rescue=>e
    puts "could not detach policy from role: #{e}"
  end

  begin
    iam.delete_role({
        role_name: "UNIK_BACKEND",
      })
    puts "deleted IAM role 'UNIK_BACKEND'"
  rescue=>e
    puts "could not delete IAM role: #{e}"
  end

  begin
    iam.delete_instance_profile(:instance_profile_name => "UNIK_INSTANCE_PROFILE")
    puts "deleted instance profile 'UNIK_INSTANCE_PROFILE'"
  rescue=>e
    puts "could not delete instance profile: #{e}"
  end
end
