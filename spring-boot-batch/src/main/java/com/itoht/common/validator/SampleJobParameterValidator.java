package com.itoht.common.validator;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class SampleJobParameterValidator implements JobParametersValidator {

	private static final Logger logger = LoggerFactory.getLogger(SampleJobParameterValidator.class);

	private Object paramDto;

	public SampleJobParameterValidator(Object dtoClass) throws JobParametersInvalidException {
		this.paramDto = dtoClass;
	}

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		Iterator<Entry<Object, Object>> keys = parameters.toProperties().entrySet().iterator();
		while (keys.hasNext()) {
			Entry<Object, Object> entry = keys.next();
			logger.info("args -> key:{} value:{}", entry.getKey(), entry.getValue());
		}
		this.setValue(this.paramDto, parameters.toProperties());
		if (!this.isValid(this.paramDto)) {
			throw new JobParametersInvalidException("起動引数の不正を検知しました");
		}
	}

	private void setValue(Object dtoClass, Properties params) {
		Set<Object> keys = params.keySet();

		// DTOへパラメータを設定
		for (Object key : keys) {
			try {
				Field field = dtoClass.getClass().getDeclaredField(key.toString());
				field.setAccessible(true);
				field.set(dtoClass, params.getProperty(key.toString()));
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// エラー出力後に継続
				logger.error(e.getMessage(), e);
				continue;
			} catch (NoSuchFieldException e) {
				// フィールド無しは処理継続
				continue;
			}
		}
	}

	private boolean isValid(Object dto) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> result = validator.validate(dto);
		if (result.isEmpty()) {
			return true;
		}
		for (ConstraintViolation<Object> constraintViolation : result) {
			logger.error(constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString());
		}
		return false;
	}
}
