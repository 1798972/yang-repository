#部署
我们在服务器上需要安装
##依赖
1.Git 拉取代码
2.JDK 编译运行
3.Maven 构建项目
4.MySql
##步骤
-yum  <br/>
-yum update  <br/>
-yum install git  <br/> 
根目录中创建一个名为App的目录  <br/>
-mkdir App  <br/>
-cd App  <br/>
进入到App目录<br/>
-git clone https://github.com/1798972/yang-repository.git (git pull拉取代码) <br/>
查看当前目录 <br/>
-pwd  <br/>
安装maven(它自动安装了java8)  <br/>
-yum install maven  <br/>
-mvn -v  <br/>
-java -version  <br/>
-mvn clean compile package  <br/>
编译完成后，查看配置  <br/>
-more src/main/resources/application.properties  <br/>
复制一份用于生产环境的配置文件  <br/>
-cp src/main/resources/application.properties src/main/resources/application-production.properties  <br/>
编辑新文件  <br/>
-vim src/main/resources/application-production.properties  <br/>
进入后按a编辑 esc两次后:wq退出并保存  <br/>
-mvn package  <br/>
运行 ctrl+c 结束当前进程 nohup  <br/>
当前目录下会生成一个nohup.out文件，控制台输入的东西会会被转移到这个文件中。最后通过exit退出终端窗口。  <br/>
-nohup java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar  <br/>

