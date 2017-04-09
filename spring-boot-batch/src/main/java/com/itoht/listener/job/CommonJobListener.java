package com.itoht.listener.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class CommonJobListener extends JobExecutionListenerSupport {

	private static final Logger logger = LoggerFactory.getLogger(CommonJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("{} 起動開始", jobExecution.getJobInstance().getJobName());
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("{} 起動終了", jobExecution.getJobInstance().getJobName());
	}
}
