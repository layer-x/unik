def get_profile_arn region
  begin
    require 'aws-sdk'
  rescue LoadError
    abort("vagrant Unik requires installation of the aws-sdk gem. please run 'vagrant plugin install aws-sdk' and try again")
  end

  require_relative 'create_iam_role.rb'

  iam = Aws::IAM::Client.new(region: region)

  begin
    instance_profile_resp = iam.get_instance_profile({
        instance_profile_name: "UNIK_INSTANCE_PROFILE",
    })
  rescue Aws::IAM::Errors::NoSuchEntity
    return create_iam_role region
  end

  instance_profile_resp[:instance_profile][:arn]
end
