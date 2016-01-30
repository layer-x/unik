# **How to bring up your Unik vagrant box on AWS**

**1. export your AWS credentials to your env:**
```
export AWS_ACCESS_KEY_ID=<your_key>
export AWS_SECRET_ACCESS_KEY=<your_secret_key>
export AWS_REGION=<region>
```

**2. export the desired username and password to target your unik backend**
```
export UNIK_USERNAME=<anything>
export UNIK_PASSWORD=<anything>
```

**3. vagrant up**
```
vagrant up --provider=aws
```

**4. get the hostname of your instance**
```
vagrant ssh-config
Host default
  HostName ec2-54-175-75-112.compute-1.amazonaws.com
  User vagrant
  Port 22
  ...
```

**5. target your unik backend**
```
unik login -u USERNAME -p PASSWORD <hostname>
```
