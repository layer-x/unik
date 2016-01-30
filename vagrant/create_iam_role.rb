def create_iam_role region
  begin
    require 'aws-sdk'
  rescue LoadError
    abort("vagrant Unik requires installation of the aws-sdk gem. please run 'vagrant plugin install aws-sdk' and try again")
  end

  iam = Aws::IAM::Client.new(region: region)

  begin
    iam.create_instance_profile(:instance_profile_name => 'UNIK_INSTANCE_PROFILE')
    puts 'creating instance profile "UNIK_INSTANCE_PROFILE"'
  rescue Aws::IAM::Errors::EntityAlreadyExists
    puts 'instance profile "UNIK_INSTANCE_PROFILE" already exists'
  end



end
