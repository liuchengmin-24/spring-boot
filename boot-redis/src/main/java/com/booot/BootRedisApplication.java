package com.booot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootRedisApplication.class, args);
	}
	//E:\Redis-x64-3.2.100>redis-server.exe redis.windows.conf
	//E:\Redis-x64-3.2.100>redis-cli.exe -h 127.0.0.1 -p 6379
	//启动命令:redis-server redis.windows.conf
	//设置Redis服务:redis-server --service-install redis.windows-service.conf --loglevel verbose
	//卸载服务：redis-server --service-uninstall
	//开启服务：redis-server --service-start
	//停止服务：redis-server --service-stop
}
