package com.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMongodbApplication.class, args);
    }

//命令行下运行 MongoDB 服务器
//C:\mongodb\bin\mongod --dbpath c:\data\db
//连接MongoDB
//C:\mongodb\bin\mongo.exe
    //创建配置文件   C:\mongodb\mongod.cfg
    //	systemLog:
    //	destination: file
    //	path: c:\data\log\mongod.log
    //	storage:
    //	dbPath: c:\data\db

    //安装 MongoDB服务
    //"C:\mongodb\bin\mongod.exe" --config "C:\mongodb\mongod.cfg" --install
//	启动MongoDB服务
//	net start MongoDB
//			关闭MongoDB服务
//	net stop MongoDB
//			移除MongoDB服务
//"C:\mongodb\bin\mongod.exe" --remove

}
