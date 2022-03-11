package com.matrix.processor.common;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.matrix.validate.Validation;

import lombok.Data;

@Data
@Validated
public class ValidationTest {
	private Long id;
	
	@NotBlank(message = "target is blank")
	@Length(max = 10, message = "00000005")
	private String target;
	
	@NotBlank(message = "atype is blank")
	@Pattern(regexp = Validation.REGEX_LINE_TYPE, message = "atype just in : bgp|ct|un|cm|dynamic_bgp")
    private String atype;

	@Override
	public String toString() {
		return "ValidationTest [id=" + id + ", target=" + target + ", atype=" + atype + "]";
	} 
}
