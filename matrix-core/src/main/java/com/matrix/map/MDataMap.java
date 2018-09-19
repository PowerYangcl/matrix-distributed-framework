package com.matrix.map;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.matrix.helper.FormatHelper;

/**
 * @descriptions come from hjy , lost the auther's name
 *
 * @date 2016年11月12日 上午12:43:22
 * @version 1.0.1
 */
public class MDataMap extends MObjMap<String, String>implements Map<String, String> {
 
	private static final long serialVersionUID = 1L; 

	/**
	 * 转换主键到数组
	 * 
	 * @return
	 */
	public String[] convertKeysToStrings() {
		return this.getKeys().toArray(new String[] {});
	}

	/**
	 * 获取子数据 根据传入参数取得以该参数开头的集合
	 * alias upSubMap
	 * @param sStartString
	 * @return
	 */
	public MDataMap getSubMap(String sStartString) {
		MDataMap mReturn = new MDataMap();
		for (String sKey : this.getKeys()) {
			if (StringUtils.startsWith(sKey, sStartString)) {
				mReturn.put(StringUtils.substringAfter(sKey, sStartString), this.get(sKey));
			}
		}
		return mReturn;
	}

	/**
	 * 添加URL参数 URL结构体为a=b&c=d
	 * 
	 * @param sParams
	 * @return
	 */
	public MDataMap inUrlParams(String sParams) {
		this.initKeyValues(FormatHelper.upUrlStrings(sParams));
		return this;
	}

	// alias upStrings
	public String[] toStringArray() {
		ArrayList<String> aList = new ArrayList<String>();
		for (String sKey : this.getKeys()) {
			aList.add(sKey);
			aList.add(this.get(sKey));
		}
		return aList.toArray(new String[] {});
	}

	public MDataMap() {

	}

	public MDataMap(String... sInputs) {
		initKeyValues(sInputs);
	}

	public MDataMap(Map<String, Object> mInput) {
		for (String s : mInput.keySet()) {
			if (mInput.get(s) != null) {
				put(s, mInput.get(s).toString());
			} else {
				put(s, "");
			}
		}
	}

	// alias upValue
	public String getValue(String sKey) {
		return getValue(sKey, "");
	}

	// alias upValue
	public String getValue(String sKey, String sDefault) {
		String sReturn = sDefault;
		if (this.containsKey(sKey)) {
			sReturn = this.get(sKey);
		}
		return sReturn;
	}

}
