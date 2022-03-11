package com.matrix.controller;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.matrix.validate.Validation;

import lombok.Data;

@Data
public class ValidationTest {
	private Long id;
	
	@NotBlank(message = "100020111")
	@Length(max = 10, message = "00000005")
	private String target;
	
	@NotBlank(message = "600010060")
	@Pattern(regexp = Validation.REGEX_LINE_TYPE, message = "atype just in : bgp|ct|un|cm|dynamic_bgp")
    private String atype;

	@Override
	public String toString() {
		return "ValidationTest [id=" + id + ", target=" + target + ", atype=" + atype + "]";
	} 
}
