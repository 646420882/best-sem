#best-sem

搜客Web部署说明
-------------

1. 开发版本: ```./deploy-runner.sh -e dev -c best-sem-web-dev```
2. 测试版本: ```./deploy-runner.sh -e prod -c best-sem-web-beta -p 8081```
3. 投入生产使用的版本: ```./deploy-runner.sh -e prod -c best-sem-web-prod```


> **注意:** 开发环境下请将configuration/driver下的mysql驱动包放入本地Tomcat Server的lib目录中.
