package com.itoht.config;

import org.apache.log4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.itoht.common.validator.SampleJobParameterValidator;
import com.itoht.dto.SampleBatchParams;
import com.itoht.listener.job.CommonJobListener;
import com.itoht.step.tasklet.ArgsCheckTasklet;

@Configuration
@EnableBatchProcessing
@EntityScan(basePackages = "com.itoht.entity")
@EnableJpaRepositories(basePackages = "com.itoht.repository")
@EnableTransactionManagement
@EnableJpaAuditing
@ComponentScan("com.itoht")
public class BatchCommonConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ArgsCheckTasklet argsCheckTasklet;

	@Autowired
	private CommonJobListener jobListener;

	@Bean
	public Job job() throws Exception {
		Job job = jobBuilderFactory.get("sample-job").listener(jobListener)
				.validator(new SampleJobParameterValidator(new SampleBatchParams())).start(step1()).build();
		MDC.put("jobid", job.getName());
		return job;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(argsCheckTasklet).build();
	}
}
