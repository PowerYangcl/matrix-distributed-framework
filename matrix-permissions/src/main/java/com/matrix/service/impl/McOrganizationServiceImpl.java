package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.jsqlparser.expression.LongValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcOrganizationMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserInfoOrganizationMapper;
import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.dto.McUserInfoOrganizationDto;
import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserInfoOrganization;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.view.McOrganizationView;
import com.matrix.pojo.view.McUserInfoView;
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
	 * @description: 组织机构树形结构列表|此方法有三个地方调用
	 * @param dto.redisKey 如果不传入则认为是单纯的展示一颗树，传入则勾选树种对应的节点
	 * 
	 * @author Yangcl
	 * @date 2018年10月23日 下午5:39:49
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxTreeList(McOrganizationDto dto) {
		JSONObject result = new JSONObject();
		McUserInfoView userCache = dto.getUserCache();
		dto.setCid(userCache.getCid());
		List<McOrganizationView> list = mcOrganizationMapper.findViewListByDto(dto);
		if (list == null || list.size() == 0) {
			list = new ArrayList<McOrganizationView>();
		}
		McOrganizationView view = new McOrganizationView();
		view.setId(0L);        // 0 为所有节点树的跟起点，长整型无负数
		view.setCid(userCache.getCid());
		view.setName("root: 组织机构树");
		view.setType(0);
		view.setPlatform(userCache.getPlatform());
		view.setSeqnum(1);
		list.add(view);

		if (StringUtils.isNotBlank(dto.getRedisKey())) { // 数据权限功能 - 弹窗中的树 - 勾选
			String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp, "InitUserInfoNp").get(dto.getRedisKey());
			McUserInfoView user = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
			result.put("orgIds", user.getOrgidList() == null ? "" : user.getOrgidList().toString().substring(1, user.getOrgidList().toString().length() - 1));
		}
		result.put("status", "success");
		result.put("list", list);
		return result;
	}

	/**
	 * @description: 添加一个组织结构节点到数据库
	 * 
	 * @param e
	 * @author Yangcl
	 * @date 2018年10月24日 下午4:43:42
	 * @version 1.0.0.1
	 */
	public JSONObject addOrganizationInfo(McOrganization e) {
		JSONObject result = new JSONObject();
		McUserInfoView userCache = e.getUserCache();

		e.setCid(userCache.getCid());
		e.setPlatform(userCache.getPlatform());

		e.setCreateTime(new Date());
		e.setCreateUserId(userCache.getId());
		e.setCreateUserName(userCache.getUserName());
		e.setUpdateTime(e.getCreateTime());
		e.setUpdateUserId(userCache.getId());
		e.setUpdateUserName(userCache.getUserName());

		int flag = 0;
		try {
			flag = mcOrganizationMapper.insertGotEntityId(e);
			// 判断type是否为3 添加门店记录
			if (e.getType() == 3) {
				StoreInfo storeInfo = new StoreInfo();
				convertObject(e, storeInfo);
				storeInfoService.addStoreInfo(storeInfo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026));    // 101010026=服务器异常
			return result;
		}
		if (flag == 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010023));    // 101010023=添加失败
			return result;
		}

		result.put("status", "success");
		result.put("entity", e);
		result.put("msg", this.getInfo(101010022));    // 101010022=添加成功
		return result;
	}

	private void convertObject(McOrganization e, StoreInfo storeInfo) {
		storeInfo.setId(e.getId());
		storeInfo.setCid(e.getCid());
		storeInfo.setCreateTime(e.getCreateTime());
		storeInfo.setCreateUserId(e.getCreateUserId());
		storeInfo.setCreateUserName(e.getCreateUserName());
		storeInfo.setUpdateTime(e.getUpdateTime());
		storeInfo.setUpdateUserId(e.getUpdateUserId());
		storeInfo.setUpdateUserName(e.getUpdateUserName());
		storeInfo.setDeleteFlag(e.getDeleteFlag());
		if (e.getType()!=3){
			storeInfo.setDeleteFlag(0);
		}
		storeInfo.setName(e.getName());
		storeInfo.setMcOrganizationId(e.getId());  // 设置组织机构ID
		storeInfo.setPhone(e.getMobile());
		storeInfo.setAddress(e.getAddress());
		storeInfo.setType(e.getStoreType().shortValue());
	}

	/**
	 * @description: 编辑一个组织机构节点
	 * 
	 * @param e
	 * @author Yangcl
	 * @date 2018年10月25日 下午1:56:19
	 * @version 1.0.0.1
	 */
	public JSONObject editOrganizationInfo(McOrganization e) {
		JSONObject result = new JSONObject();
		if (e.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010025) + "," + this.getInfo(101010012));    // 101010025=更新失败 + 101010012=节点id不得为空!
			return result;
		}

		McUserInfoView userCache = e.getUserCache();
		e.setCid(userCache.getCid());
		e.setUpdateTime(e.getCreateTime());
		e.setUpdateUserId(userCache.getId());
		e.setUpdateUserName(userCache.getUserName());
		if (e.getType() != 3) {
			e.setStoreType(0);
		}

		int flag = 0;
		try {
			flag = mcOrganizationMapper.updateSelective(e);
			// 只要更新组织机构信息就操作门店表
			StoreInfo storeInfo = new StoreInfo();
			convertObject(e, storeInfo);
			storeInfoService.editStoreInfo(storeInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026));    // 101010026=服务器异常
			return result;
		}
		if (flag == 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010025));    // 101010025=更新失败
			return result;
		}

		result.put("status", "success");
		result.put("entity", e);
		result.put("msg", this.getInfo(101010024));    // 101010024=更新成功
		return result;
	}

	/**
	 * @description: 删除多个组织机构节点
	 * 
	 * @param e
	 * @author Yangcl
	 * @date 2018年10月25日 下午2:17:36
	 * @version 1.0.0.1
	 */
	public JSONObject deleteOrganizationInfo(McOrganizationDto dto) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(dto.getIds())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010002) + "," + this.getInfo(101010012));    // 101010002=删除失败 + 101010012=节点id不得为空!
			return result;
		}

		McUserInfoView userCache = dto.getUserCache();
		try {
			String arr[] = dto.getIds().split(",");
			for (int i = 0; i < arr.length; i++) {
				/*McOrganization e = new McOrganization();*/
				dto.setId(Long.parseLong(arr[i]));
				McOrganization e = mcOrganizationMapper.findEntityByDto(dto);
				e.setId(Long.parseLong(arr[i]));
				e.setDeleteFlag(0);
				e.setUpdateTime(e.getCreateTime());
				e.setUpdateUserId(userCache.getId());
				e.setUpdateUserName(userCache.getUserName());
				mcOrganizationMapper.updateSelective(e);
				if (e != null) {
					if (e.getType() == 3) {
						StoreInfo storeInfo = new StoreInfo();
						convertObject(e, storeInfo);
						storeInfoService.deleteStoreInfo(storeInfo);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026));    // 101010026=服务器异常
			return result;
		}

		result.put("status", "success");
		result.put("msg", this.getInfo(101010001));    // 101010001=删除成功
		return result;
	}

	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param dto.ustring id@seqnum,id@seqnum
	 * @author Yangcl
	 * @date 2018年10月25日 下午3:03:52
	 * @version 1.0.0.1
	 */
	public JSONObject updateTreeNodes(McOrganizationDto dto) {
		JSONObject result = new JSONObject();
		try {
			McUserInfoView userInfo = dto.getUserCache();
			String[] arr = dto.getUstring().split(",");
			for (int i = 0; i < arr.length; i++) {
				McOrganization e = new McOrganization();
				e.setId(Long.valueOf(arr[i].split("@")[0]));
				e.setSeqnum(Integer.valueOf(arr[i].split("@")[1]));
				e.setUpdateTime(new Date());
				e.setUpdateUserId(userInfo.getId());
				e.setUpdateUserName(userInfo.getUserName());
				mcOrganizationMapper.updateSelective(e);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026));    // 101010026=服务器异常
			return result;
		}

		result.put("status", "success");
		result.put("msg", this.getInfo(101010035));    // 101010035=同层节点位置更换成功
		return result;
	}

	/**
	 * @description: 数据权限与用户关联
	 * 
	 * @param dto
	 * @author Yangcl
	 * @date 2018年10月30日 上午11:27:25
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxUserAddOrganization(McOrganizationDto dto) {
		JSONObject result = new JSONObject();

		McUserInfoView userInfo = dto.getUserCache();
		McUserInfoOrganizationDto dto_ = new McUserInfoOrganizationDto();
		dto_.setMcUserInfoId(dto.getUserId());
		dto_.setUpdateTime(new Date());
		dto_.setUpdateUserId(userInfo.getId());
		dto_.setUpdateUserName(userInfo.getUserName());
		mcUserInfoOrganizationMapper.deleteByDto(dto_);

		if (StringUtils.isBlank(dto.getIds())) {
			result.put("status", "success");
			result.put("msg", this.getInfo(101010039));    // 101010039=用户数据权限已经解除
			return result;
		}

		String arr[] = dto.getIds().split(",");
		try {
			for (int i = 0; i < arr.length; i++) {
				McUserInfoOrganization e = new McUserInfoOrganization();
				e.setCid(userInfo.getCid());
				e.setMcUserInfoId(dto.getUserId());
				e.setMcOrganizationId(Long.valueOf(arr[i]));
				e.setPlatform(userInfo.getPlatform());

				e.setCreateTime(new Date());
				e.setCreateUserId(userInfo.getId());
				e.setCreateUserName(userInfo.getUserName());
				e.setUpdateTime(new Date());
				e.setUpdateUserId(userInfo.getId());
				e.setUpdateUserName(userInfo.getUserName());
				mcUserInfoOrganizationMapper.insertSelective(e);
			}

			McUserInfo user = mcUserInfoMapper.find(dto.getUserId());        // 开始重置用户缓存
			launch.loadDictCache(DCacheEnum.UserInfoNp, "").del(user.getUserName() + "," + user.getPassword());
			launch.loadDictCache(DCacheEnum.UserInfoNp, "InitUserInfoNp").get(user.getUserName() + "," + user.getPassword());
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "success");
			result.put("msg", this.getInfo(101010026));    // 101010026=服务器异常
			return result;
		}


		result.put("status", "success");
		result.put("msg", this.getInfo(101010040));    // 101010040=用户数据权限关联成功
		return result;
	}

	/**
	 * @description:  获取一级组织机构下所有的门店
	 *
	 * @param dto
	 * @return 
	 * @author mashaohua
	 * @date 2019年5月2日 上午11:37:25 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxStoreList(McOrganizationDto dto) {
		JSONObject result = new JSONObject();
		McUserInfoView userCache = dto.getUserCache();
		dto.setCid(userCache.getCid());
		long a = (int) 0;
		dto.setParentId(a);
		List<McOrganizationView> list = mcOrganizationMapper.findViewListByDto(dto);
		if (list == null || list.size() == 0) {
			list = new ArrayList<McOrganizationView>();
		} else {
			for (McOrganizationView mcOrganizationView : list) {
				McOrganizationDto views = new McOrganizationDto();
				views.setType(3);
				views.setCid(mcOrganizationView.getCid());
				views.setParentId(mcOrganizationView.getId());
				List<McOrganizationView> listtotal = mcOrganizationMapper.findListByParentId(views);
				mcOrganizationView.setOrginList(listtotal);
			}
		}
		result.put("status", "success");
		result.put("list", list);
		return result;
	}


		/*public List<McOrganizationView> getStoreList(McOrganizationView dto) {
				List<McOrganizationView> listall = new ArrayList<McOrganizationView>();
				McOrganizationDto views = new McOrganizationDto();
				views.setType(3);
				views.setParentId(dto.getId());
				List<McOrganizationView> list1 = mcOrganizationMapper.findViewListByDto(views);
				if (list1 != null) {
					for (McOrganizationView organizationView : list1) {
						listall.add(organizationView);
						this.getStoreList(organizationView);
					}
				} else {
					return listall;
				}

				return listall;
		}*/




}


































































