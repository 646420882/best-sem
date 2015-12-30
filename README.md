#best-sem

------------------
项目部署需要修改的地方：
-------------------
**TODO**

perfect-usercenter >>>> bestIndex.jsp 中的 onclick 连接

perfect-commons    >>>> AuthConstants.java 中的访问连接


------------
搜客Web部署说明
-------------

1. 开发版本: ```./deploy-runner.sh -e dev -c best-sem-web-dev```
2. 测试版本: ```./deploy-runner.sh -e prod -c best-sem-web-beta -p 8081```
3. 投入生产使用的版本: ```./deploy-runner.sh -e prod -c best-sem-web-prod```
