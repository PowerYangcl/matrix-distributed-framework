package com.matrix.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.request.AddMcOrganizationRequest;
import com.matrix.pojo.request.DeleteMcOrganizationRequest;
import com.matrix.pojo.request.FindMcOrganizationRequest;
import com.matrix.pojo.request.FindStoreListRequest;
import com.matrix.pojo.request.UpdateMcOrganizationRequest;
import com.matrix.pojo.request.UpdateTreeNodesRequest;
import com.matrix.pojo.request.UpdateUserAddOrgRequest;
import com.matrix.pojo.view.McOrganizationView;
import com.matrix.pojo.view.UserOrgTreeListView;

public interface IMcOrganizationService extends IBaseService<Long, McOrganization, McOrganizationDto, McOrganizationView>{

	/**
	 * @description: 组织机构树形结构列表|controller层未调用。
	 * 
	 * @param dto
	 * @author Yangcl
	 * @date 2021-5-25 15:09:08
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Result<UserOrgTreeListView> ajaxTreeList(FindMcOrganizationRequest param);

	/**
	 * @description: 添加一个组织结构节点到数据库|controller层未调用。
	 *
	 * @param e
	 * @author Yangcl
	 * @date 2018年10月24日 下午4:43:42 
	 * @version 1.0.0.1
	 */
	public Result<McOrganization> addOrganizationInfo(AddMcOrganizationRequest param);

	/**
	 * @description: 编辑一个组织机构节点
	 *
	 * @param e 
	 * @author Yangcl
	 * @date 2018年10月25日 下午1:56:19 
	 * @version 1.0.0.1
	 */
	public Result<McOrganization> updateOrganizationInfo(UpdateMcOrganizationRequest param);

	/**
	 * @description: 删除多个组织机构节点
	 *
	 * @author Yangcl
	 * @date 2018年10月25日 下午2:17:36 
	 * @version 1.0.0.1
	 */
	public Result<?> deleteOrganizationInfo(DeleteMcOrganizationRequest param);

	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param dto.ustring id@seqnum,id@seqnum 
	 * @author Yangcl 
	 * @date 2018年10月25日 下午3:03:52 
	 * @version 1.0.0.1
	 */
	public Result<?> updateTreeNodes(UpdateTreeNodesRequest param);

	/**
	 * @description: 数据权限与用户关联
	 *
	 * @author Yangcl
	 * @date 2018年10月30日 上午11:27:25 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxUserAddOrgRequest(UpdateUserAddOrgRequest param);

	/**
	 * @description: 获取一级组织机构下所有的门店
	 * 
	 * @param dto
	 * @author Yangcl
	 * @date 2021-5-26 18:07:27
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Result<List<McOrganizationView>> ajaxStoreList(FindStoreListRequest param);
}































