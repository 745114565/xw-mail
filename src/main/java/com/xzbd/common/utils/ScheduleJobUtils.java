package com.xzbd.common.utils;

import com.xzbd.common.domain.ScheduleJob;
import com.xzbd.common.domain.TaskDO;
import com.xzbd.mail.domain.BiEmailJobConfigDO;
import org.quartz.JobDataMap;

public class ScheduleJobUtils {
	public static ScheduleJob entityToData(TaskDO scheduleJobEntity) {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());
		return scheduleJob;
	}
	public static ScheduleJob entityToData(BiEmailJobConfigDO scheduleJobEntity) {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setBeanClass(scheduleJobEntity.getJobBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getJobDesc());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsconcurrent() + "");
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus()+"");
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean("");

		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("email_job_id", scheduleJobEntity.getId());//传递参数 email job id
		scheduleJob.setJobDataMap(jobDataMap);
		return scheduleJob;
	}
}