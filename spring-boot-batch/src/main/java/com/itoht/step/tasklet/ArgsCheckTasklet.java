package com.itoht.step.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ArgsCheckTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(ArgsCheckTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("{} 起動開始", chunkContext.getStepContext().getStepName());
		logger.info("{} 起動終了", chunkContext.getStepContext().getStepName());
		return RepeatStatus.FINISHED;
	}

}
