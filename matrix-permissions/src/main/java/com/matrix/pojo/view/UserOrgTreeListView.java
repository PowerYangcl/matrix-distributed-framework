package com.matrix.pojo.view;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserOrgTreeListView implements Serializable{
	
	private static final long serialVersionUID = -8619112923277580914L;

	private List<McOrganizationView> list = new ArrayList<McOrganizationView>();
	
	private List<Long> orgidList = new ArrayList<Long>(); 
}
