#部署
我们在服务器上需要安装
##依赖
1.Git 拉取代码
2.JDK 编译运行
3.Maven 构建项目
4.MySql
##步骤
-yum
-yum update
-yum install git
根目录中创建一个名为App的目录
-mkdir App
-cd App
进入到App目录
-git clone https://github.com/1798972/yang-repository.git
查看当前mul
-pwd
安装maven(它自动安装了java8)
-yum install maven
-mvn -v
-java -version
-mvn clean compile package
编译完成后，查看配置
-more src/main/resources/application.properties
复制一份用于生产环境的配置文件
-cp src/main/resources/application.properties src/main/resources/application-production.properties
编辑新文件
-vim src/main/resources/application-production.properties
进入后按a编辑 esc两次后:wq退出并保存
-mvn package
运行
-java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar