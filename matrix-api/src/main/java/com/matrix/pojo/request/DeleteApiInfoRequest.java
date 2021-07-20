package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IAcApiInfoMapper;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteApiInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 4991730520584403461L;

	private McUserInfoView userCache;
	
	private IAcApiInfoMapper acApiInfoMapper;
	
	private Long id;
	
	private String target; // 可利用的返回值。
	
	public Result<AcApiInfoCache> validate(IAcApiInfoMapper acApiInfoMapper){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		AcApiInfo api = acApiInfoMapper.find(id);
		if(api == null) { // 600010078=目标接口: {0} 不存在!数据库无此记录,修改失败!
			Result.ERROR(this.getInfo(600010078), ResultCode.NOT_FOUND);
		}
		this.target = api.getTarget();
		return Result.SUCCESS();
	}
	
	
	public AcApiInfo buildAjaxApiInfoRemove() {
		AcApiInfo e = new AcApiInfo();
		e.setId(id);
		e.setDeleteFlag(0);
		e.buildUpdateCommon(userCache);
		return e;
	}
}




















