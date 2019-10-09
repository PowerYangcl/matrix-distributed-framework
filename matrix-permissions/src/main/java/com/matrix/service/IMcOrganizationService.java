package com.matrix.service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.view.McOrganizationView;

public interface IMcOrganizationService extends IBaseService<Long, McOrganization, McOrganizationDto, McOrganizationView>{

	/**
	 * @description: 组织机构树形结构列表
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年10月23日 下午5:39:49 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxTreeList(McOrganizationDto dto);

	/**
	 * @description: 添加一个组织结构节点到数据库
	 *
	 * @param e
	 * @author Yangcl
	 * @date 2018年10月24日 下午4:43:42 
	 * @version 1.0.0.1
	 */
	public JSONObject addOrganizationInfo(McOrganization e);

	/**
	 * @description: 编辑一个组织机构节点
	 *
	 * @param e 
	 * @author Yangcl
	 * @date 2018年10月25日 下午1:56:19 
	 * @version 1.0.0.1
	 */
	public JSONObject editOrganizationInfo(McOrganization e);

	/**
	 * @description: 删除多个组织机构节点
	 *
	 * @param e 
	 * @author Yangcl
	 * @date 2018年10月25日 下午2:17:36 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteOrganizationInfo(McOrganizationDto dto);

	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param dto.ustring id@seqnum,id@seqnum 
	 * @author Yangcl 
	 * @date 2018年10月25日 下午3:03:52 
	 * @version 1.0.0.1
	 */
	public JSONObject updateTreeNodes(McOrganizationDto dto);

	/**
	 * @description: 数据权限与用户关联
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年10月30日 上午11:27:25 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxUserAddOrganization(McOrganizationDto dto);

	/**
	 * @Author mashaohua
	 * @Description 获取一级组织机构下所有的门店
	 * @Date 17:48 2018/12/17
	 * @Param [dto]
	 **/
	JSONObject ajaxStoreList(McOrganizationDto dto);
}































