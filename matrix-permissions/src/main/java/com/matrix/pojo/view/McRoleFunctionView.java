package com.matrix.pojo.view;

import com.matrix.base.BaseView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McRoleFunctionView extends BaseView {

	private static final long serialVersionUID = -6150256492091550718L;

	private Long id;
    private Long mcRoleId;
    private Long mcSysFunctionId; 
    private String remark;
    
}
