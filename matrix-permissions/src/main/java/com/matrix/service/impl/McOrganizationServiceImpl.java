package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcOrganizationMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserInfoOrganizationMapper;
import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserInfoOrganization;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.request.AddMcOrganizationRequest;
import com.matrix.pojo.request.DeleteMcOrganizationRequest;
import com.matrix.pojo.request.FindMcOrganizationRequest;
import com.matrix.pojo.request.FindStoreListRequest;
import com.matrix.pojo.request.UpdateMcOrganizationRequest;
import com.matrix.pojo.request.UpdateTreeNodesRequest;
import com.matrix.pojo.request.UpdateUserAddOrgRequest;
import com.matrix.pojo.view.McOrganizationView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.pojo.view.UserOrgTreeListView;
import com.matrix.service.IMcOrganizationService;
import com.matrix.service.IStoreInfoService;

@Service("mcOrganizationService") 
public class McOrganizationServiceImpl extends BaseServiceImpl<Long, McOrganization, McOrganizationDto, McOrganizationView> implements IMcOrganizationService {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();

	@Resource
	private IMcOrganizationMapper mcOrganizationMapper;
	@Resource
	private IMcUserInfoOrganizationMapper mcUserInfoOrganizationMapper;
	@Resource
	private IMcUserInfoMapper mcUserInfoMapper;
	@Resource
	private IStoreInfoService storeInfoService;

	/**
	 * @description: 组织机构树形结构列表|controller层未调用。
	 * 		组织机构树形结构列表|此方法有三个地方调用
	 * 
	 * @param dto.redisKey 如果不传入则认为是单纯的展示一颗树，传入则勾选树种对应的节点
	 * @author Yangcl
	 * @date 2021-5-25 15:09:08
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Result<UserOrgTreeListView> ajaxTreeList(FindMcOrganizationRequest param) {
		McOrganizationDto dto = param.buildAjaxTreeList();
		List<McOrganizationView> list = mcOrganizationMapper.findViewListByDto(dto);
		if (list == null || list.size() == 0) {
			list = new ArrayList<McOrganizationView>();
		}
		McOrganizationView view = new McOrganizationView();
		view.setId(0L);        // 0 为所有节点树的跟起点，长整型无负数
		view.setCid(dto.getCid());
		view.setName("root: 组织机构树");
		view.setType(0);
		view.setPlatform(dto.getUserCache().getPlatform());
		view.setSeqnum(1);
		list.add(view);
		
		UserOrgTreeListView view_ = new UserOrgTreeListView();
		view_.getList().addAll(list);
		if (StringUtils.isNotBlank(param.getRedisKey())) { 			// 数据权限功能 - 弹窗中的树 - 勾选
			String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp, "UserInfoNpInit").get(param.getRedisKey());
			McUserInfoView user = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
			if(!CollectionUtils.isEmpty(user.getOrgidList())) {
				view_.getOrgidList().addAll(user.getOrgidList());
			}
		}
		return Result.SUCCESS(view_);
	}

	/**
	 * @description: 添加一个组织结构节点到数据库
	 * 
	 * @author Yangcl
	 * @date 2018年10月24日 下午4:43:42
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<McOrganization> addOrganizationInfo(AddMcOrganizationRequest param) {
		McOrganization e = param.buildAddOrganizationInfo();
		try {
			int flag = mcOrganizationMapper.insertGotEntityId(e);
			if(flag != 1) {
				return Result.ERROR(this.getInfo(100010103), ResultCode.ERROR_INSERT);
			}
			// 判断type是否为3 添加门店记录		TODO 业务逻辑不合理，待讨论，2021-06-07
//			if (e.getType() == 3) {
//				StoreInfo storeInfo = new StoreInfo();
//				convertObject(e, storeInfo);
//				storeInfoService.addStoreInfo(storeInfo);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010103));
		}
		return Result.SUCCESS(this.getInfo(100010102), e);
	}

	private void convertObject222222(McOrganization e, StoreInfo storeInfo) {
		storeInfo.setId(e.getId());
		storeInfo.setCid(e.getCid());
		if (e.getType()!=3){
			storeInfo.setDeleteFlag(0);
		}
		storeInfo.setName(e.getName());
		storeInfo.setAddress(e.getAddress());
		storeInfo.setPhone(e.getMobile());
		storeInfo.setMcOrganizationId(e.getId());  // 设置组织机构ID
		storeInfo.setType(e.getStoreType().shortValue());
		storeInfo.buildAddCommon(e.getUserCache());
	}

	/**
	 * @description: 编辑一个组织机构节点
	 * 
	 * @author Yangcl
	 * @date 2018年10月25日 下午1:56:19
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<McOrganization> updateOrganizationInfo(UpdateMcOrganizationRequest param) {
		McOrganization entity = param.buildUpdateOrganizationInfo();
		if (entity.getId() == null) {  // 101010025=更新失败 + 101010012=节点id不得为空!
			return Result.ERROR(this.getInfo(101010025) + "," + this.getInfo(101010012), ResultCode.ERROR_UPDATE);
		}
		if (entity.getType() != 3) {
			entity.setStoreType(0);
		}

		try {
			int flag = mcOrganizationMapper.updateSelective(entity);
			if(flag != 1) {
				return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);
			}
			// 只要更新组织机构信息就操作门店表		TODO 业务逻辑不合理，待讨论，2021-06-07
//			StoreInfo storeInfo = new StoreInfo();
//			convertObject(entity, storeInfo);
//			storeInfoService.editStoreInfo(storeInfo);
		} catch (Exception ex) {
			ex.printStackTrace();	// 100010105=数据更新失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010105));
		}
		return Result.SUCCESS(this.getInfo(100010104), entity);
	}

	/**
	 * @description: 删除多个组织机构节点
	 * 
	 * @author Yangcl
	 * @date 2018年10月25日 下午2:17:36
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> deleteOrganizationInfo(DeleteMcOrganizationRequest param) {
		if(StringUtils.isBlank(param.getIds())) {  // 101010002=删除失败 + 101010012=节点id不得为空!
			return Result.ERROR(this.getInfo(101010002) + "," + this.getInfo(101010012), ResultCode.ERROR_DELETE);
		}

		try {
			McUserInfoView userCache = param.getUserCache();
			String arr[] = param.getIds().split(",");
			for (int i = 0; i < arr.length; i++) {
				McOrganizationDto dto = new McOrganizationDto();
				dto.setId(Long.parseLong(arr[i]));
				McOrganization e = mcOrganizationMapper.findEntityByDto(dto);
				if(e == null) {
					continue;
				}
				e.setId(Long.parseLong(arr[i]));
				e.setDeleteFlag(0);
				e.buildUpdateCommon(userCache);
				mcOrganizationMapper.updateSelective(e);
//				if (e != null && e.getType() == 3) {								TODO 业务逻辑不合理，待讨论，2021-06-07
//					StoreInfo storeInfo = new StoreInfo();
//					convertObject(e, storeInfo);
//					storeInfoService.deleteStoreInfo(storeInfo);
//				}
			}
			return Result.SUCCESS(this.getInfo(100010106));		// 100010106=数据删除成功!
		}catch (Exception ex) {
			ex.printStackTrace();  	// 100010107=数据删除失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010107));
		}
	}

	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param dto.ustring id@seqnum,id@seqnum
	 * @author Yangcl
	 * @date 2018年10月25日 下午3:03:52
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> updateTreeNodes(UpdateTreeNodesRequest param) {
		if(StringUtils.isBlank(param.getUstring())) {  // 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103,  "ustring") , ResultCode.OPERATION_FAILED);
		}
		
		try {
			String[] arr = param.getUstring().split(",");
			for (int i = 0; i < arr.length; i++) {
				McOrganization e = new McOrganization();
				e.setId(Long.valueOf(arr[i].split("@")[0]));
				e.setSeqnum(Integer.valueOf(arr[i].split("@")[1]));
				e.buildUpdateCommon(param.getUserCache());
				mcOrganizationMapper.updateSelective(e);
			}
			return Result.SUCCESS(this.getInfo(101010035));  // 101010035=同层节点位置更换成功
		} catch (Exception ex) {
			ex.printStackTrace();   // 100010105=数据更新失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010105));
		}
	}

	/**
	 * @description: 数据权限与用户关联
	 * 
	 * @param param
	 * @author Yangcl
	 * @date 2018年10月30日 上午11:27:25
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> ajaxUserAddOrgRequest(UpdateUserAddOrgRequest param) {
		if (StringUtils.isBlank(param.getIds())) { // 101010039=用户数据权限已经解除
			return Result.ERROR(this.getInfo(101010039), ResultCode.OPERATION_FAILED);
		}
		try {
			McUserInfoView userInfo = param.getUserCache();
			McUserInfoOrganization  entity = new McUserInfoOrganization();
			entity.setMcUserInfoId(param.getUserId());
			entity.buildUpdateCommon(userInfo);
			mcUserInfoOrganizationMapper.deleteByEntity(entity);
			
			String arr[] = param.getIds().split(",");
			for (int i = 0; i < arr.length; i++) {
				McUserInfoOrganization e = new McUserInfoOrganization();
				e.setCid(userInfo.getCid());
				e.setMcUserInfoId(param.getUserId());
				e.setMcOrganizationId(Long.valueOf(arr[i]));
				e.setPlatform(userInfo.getPlatform());
				e.buildAddCommon(userInfo);
				mcUserInfoOrganizationMapper.insertSelective(e);
			}
			McUserInfo user = mcUserInfoMapper.find(param.getUserId());        // 开始重置用户缓存
			launch.loadDictCache(DCacheEnum.UserInfoNp, "").del(user.getUserName() + "," + user.getPassword());
			launch.loadDictCache(DCacheEnum.UserInfoNp, "UserInfoNpInit").get(user.getUserName() + "," + user.getPassword());
			return Result.SUCCESS(this.getInfo(101010040));		// 101010040=用户数据权限关联成功
		} catch (Exception ex) {
			ex.printStackTrace();   // 100010105=数据更新失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010105));
		}
	}

	/**
	 * @description: 获取一级组织机构下所有的门店
	 * 
	 * @param dto
	 * @author Yangcl
	 * @date 2021-5-26 18:07:27
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Result<List<McOrganizationView>> ajaxStoreList(FindStoreListRequest param) {
		McOrganizationDto dto = new McOrganizationDto();
		dto.setCid(param.getUserCache().getCid());
		dto.setParentId(0L);
		List<McOrganizationView> list = mcOrganizationMapper.findViewListByDto(dto);
		if (list == null || list.size() == 0) {
			list = new ArrayList<McOrganizationView>();
		} else {
			for (McOrganizationView mcOrganizationView : list) {
				McOrganizationDto views = new McOrganizationDto();
				views.setType(3);
				views.setCid(mcOrganizationView.getCid());
				views.setParentId(mcOrganizationView.getId());
				List<McOrganizationView> listTotal = mcOrganizationMapper.findListByParentId(views);
				mcOrganizationView.setOrginList(listTotal);
			}
		}
		return Result.SUCCESS(this.getInfo(100010100), list);  // 100010100=数据请求成功!
	}


}


































































