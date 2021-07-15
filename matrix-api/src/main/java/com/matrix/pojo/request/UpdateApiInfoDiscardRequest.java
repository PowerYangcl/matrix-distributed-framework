package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IAcApiInfoMapper;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateApiInfoDiscardRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 1503663015241360956L;

	private McUserInfoView userCache;

	private IAcApiInfoMapper acApiInfoMapper;
	
	private Long id;
	private Integer discard;
	
	private AcApiInfo api;		// 可利用的返回值。
    
	public Result<?> validate(IAcApiInfoMapper acApiInfoMapper){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(discard == null) {	// 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103, "discard"), ResultCode.MISSING_ARGUMENT);
		}
		AcApiInfo api = acApiInfoMapper.find(id);
		if(api == null) { 		// 100020116=数据库无此记录,修改失败!
			return Result.ERROR(this.getInfo(100020116), ResultCode.NOT_FOUND);
		}
		this.api = api;
		return Result.SUCCESS();
	}
	
	
	public AcApiInfo buildAjaxApiInfoDiscard() {
		AcApiInfo e = new AcApiInfo();
		e.setId(id);
		e.setDiscard(discard);
		e.buildUpdateCommon(userCache);
		return e;
	}
}




















