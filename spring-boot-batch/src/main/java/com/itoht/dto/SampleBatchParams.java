package com.itoht.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class SampleBatchParams implements Serializable{

	private static final long serialVersionUID = 6045869548847935051L;

	@Getter
	@Setter
	@NotEmpty
	@Length(max = 3, min = 1)
	private String param1;

	@Getter
	@Setter
	@NotEmpty
	private String param2;

	@Getter
	@Setter
	@NotEmpty
	private String param3;

}
