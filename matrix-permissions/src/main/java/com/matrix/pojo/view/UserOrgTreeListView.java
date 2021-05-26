package com.matrix.pojo.view;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserOrgTreeListView {
	
	private List<McOrganizationView> list = new ArrayList<McOrganizationView>();
	
	private List<Long> orgidList = new ArrayList<Long>(); 
}
