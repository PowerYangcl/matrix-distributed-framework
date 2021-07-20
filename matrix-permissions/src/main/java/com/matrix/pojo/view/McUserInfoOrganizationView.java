package com.matrix.pojo.view;

import com.matrix.base.BaseView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserInfoOrganizationView extends BaseView {

	private static final long serialVersionUID = -7726562872648837785L;
	private Long id;
    private Long cid;
    private Long mcUserInfoId;
    private Long mcOrganizationId;
    private String platform;
}
