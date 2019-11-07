package com.matrix.security;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.util.ExceptionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;

/**
 * @description: 数据安全维护者
 *		{"url":"","name":"","password":"","shell":""}
 *
 * @author Yangcl
 * @date 2019年1月6日 下午7:51:08 
 * @version 1.0.0.1
 */
public class DataBaseGuard extends BaseClass implements IBaseExecute {

		
	@Override
	public String execute(String json) {
		JSONObject result = new JSONObject();
		JSONObject request = JSONObject.parseObject(json);
		if(StringUtils.isAnyBlank(request.getString("url") , request.getString("name") , request.getString("password") , request.getString("shell"))) {
			result.put("status", "error");
			result.put("msg", "参数为空!");
			result.put("dto", request);
			return result.toJSONString();
		}
		
		Connection con = this.getConnection(request.getString("url") , request.getString("name") , request.getString("password")); // 获取连接
		try {
			PreparedStatement pstmt = con.prepareStatement(request.getString("shell"));
			int count=pstmt.executeUpdate();
			this.close(pstmt, con);
			
			result.put("status", "success");
			result.put("msg", "命令执行成功!");
			result.put("dto", request);
			result.put("count", count);
			return result.toJSONString();
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "SQLException!");
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
		}
		return result.toJSONString();
	}

	private Connection getConnection(String url , String name , String password) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, name, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void close(PreparedStatement pstmt,Connection con){
		if(pstmt!=null){
			try {
				pstmt.close();
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}





































