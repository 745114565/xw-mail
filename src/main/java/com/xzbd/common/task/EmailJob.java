package com.xzbd.common.task;


import com.xzbd.common.utils.DateUtil;
import com.xzbd.mail.service.BiEmailJobConfigService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class EmailJob implements Job{
	private Logger logger = LoggerFactory.getLogger(EmailJob.class);
	
	@Autowired
	private BiEmailJobConfigService biEmailJobConfigService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap dataMap =  jobDetail.getJobDataMap();
		Long email_job_id = dataMap.getLong("email_job_id");

		String date = DateUtil.getDate(DateUtil.addDay(new Date(), -1));
		try {
			biEmailJobConfigService.sendEmail(email_job_id,date);
			logger.info("任务执行成功：jobId is  " + email_job_id );
		}catch (Exception e){
			logger.error("任务执行失败：jobId is  " + email_job_id);
			e.printStackTrace();
		}

		
	}

}
