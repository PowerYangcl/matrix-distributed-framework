package com.matrix.processor.common;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.matrix.validate.Validation;

import lombok.Data;

@Data
public class ValidationTest {
	private Long id;
	
	@NotBlank(message = "100020111")
	@Length(max = 10, message = "600010072")
	private String target;
	
	@NotBlank(message = "600010060")
	@Pattern(regexp = Validation.REGEX_LINE_TYPE, message = "600010068")
    private String atype;

	@Override
	public String toString() {
		return "ValidationTest [id=" + id + ", target=" + target + ", atype=" + atype + "]";
	} 
}
