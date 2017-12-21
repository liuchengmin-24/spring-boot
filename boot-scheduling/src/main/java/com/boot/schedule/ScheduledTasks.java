package com.boot.schedule;

import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @date 2017/11/9
 */
@Component
@Log4j
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
    @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
    @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
    @Scheduled(cron=” /5 “) ：通过cron表达式定义规则，什么是cro表达式，自行搜索引擎*/
    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 */1 * * * ?")
    public void reportCurrentTime() {
        //每隔5秒执行一次：*/5 * * * * ?
        //每隔1分钟执行一次：0 */1 * * * ?
        //每天23点执行一次：0 0 23 * * ?
        //每天凌晨1点执行一次：0 0 1 * * ?
        //每月1号凌晨1点执行一次：0 0 1 1 * ?
        //每月最后一天23点执行一次：0 0 23 L * ?
        //每周星期天凌晨1点实行一次：0 0 1 ? *L
        //在26分、29 分、33 分执行一次：0 26, 29, 33 * * * ?
        //每天的0点、13 点、18 点、21 点都执行一次：0 0 0, 13, 18, 21 * * ?
        //每隔5分钟执行一次：0 0 / 5 * * * ?
        log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
		log.info("The time is now {}" + dateFormat.format(new Date()));
    }
}
