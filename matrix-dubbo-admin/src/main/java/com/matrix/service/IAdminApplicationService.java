package com.matrix.service;

import com.alibaba.fastjson.JSONObject;

import com.matrix.pojo.dto.AppProvideDto;
import com.matrix.pojo.dto.ApplicationDto;
import com.matrix.pojo.dto.RpcFunctionDto;

/**
 * @description: dubbo admin provider service
 *
 * @author Yangcl
 * @date 2018年8月27日 上午11:44:17 
 * @version 1.0.0.1
 */
public interface IAdminApplicationService {

	/**
	 * @description:  查询所有应用列表信息
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年8月27日 下午5:15:30 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindApplicationList(ApplicationDto dto);

	/**
	 * @description: Dubbo项目部署节点列表数据集合
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:08:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectIpList(ApplicationDto dto);

	
	/**
	 * @description:  Dubbo项目指定部署节点下的RPC接口类列表集合|jsp页面数据集合
	 *
	 * @param dto.application 	服务名称					必填
	 * @param dto.nodeAddress 	host ip address		必填
	 * 
	 * @author Yangcl
	 * @date 2018年8月30日 下午3:28:14 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectInterfaceList(ApplicationDto dto);

	/**
	 * @description: 发布服务列表数据
	 *
	 * @param dto
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 上午10:38:39 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectServiceList(AppProvideDto dto);

	/**
	 * @description: 每一个Dubbo服务(生产者)所提供的RPC接口方法所对应的消费者列表
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindDubboProjectServiceList?application=MATRIX_AEM_SERVICE
	 *
	 * @param dto.id 具体到Dubbo服务所提供的每一个RPC接口，会对应一个长整型ID.
	 *
	 * @author Yangcl
	 * @date 2018年9月3日 下午3:23:45 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectServiceConsumerList(AppProvideDto dto);

	/**
	 * @description: Dubbo rpc接口方法列表数据
	 *
	 * @param dto.rpcName
	 * 
	 * @author Yangcl
	 * @date 2018年9月4日 上午10:09:10 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindDubboProjectRpcFunctionList(RpcFunctionDto dto);

	/**
	 * @description:Dubbo应用服务部署节点列表 
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2019年1月7日 下午2:39:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxDubboNodeList(ApplicationDto dto);



}























