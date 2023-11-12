## Java SpringBoot Lambda Tutorial

### Intallation Steps

1) Use pip to install samcli 
```Bash
pip install aws-sam-cli
```
2) In project directory init project with the following command. Specity template, Hello World w/ Powertools, Xray/Cloudwatch metrics enabled. A hello-world lambda will be created. 
```Bash
sam init --name my-springboot-lambda --runtime java8 --dependency-manager maven
```
3) Enable docker and install openjdk8

4) Compile code to be containerized with 
```Bash
sam build
```

4) Test lambda on local with following cmd 
```Bash
sam local invoke -e events/event.json
```

5) Run lambda on localhost with
```Bash
sam local start-api
```

6) Hit lambda on localhost with
```Bash
sam local start-apisam local start-api
```