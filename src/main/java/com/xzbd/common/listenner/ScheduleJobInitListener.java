package com.xzbd.common.listenner;

import com.xzbd.common.service.JobService;
import com.xzbd.mail.service.BiEmailJobConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    private JobService scheduleJobService;
    @Autowired
    private BiEmailJobConfigService biEmailJobConfigService;


    @Override
    public void run(String... arg0) throws Exception {
        try {
            // 初始化通知任务
            scheduleJobService.initSchedule();
            // 初始化邮件任务
            biEmailJobConfigService.initSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}