package com.matrix.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.util.ExceptionUtils;

/**
 * @description: 数据维护
 * 
 * @author Yangcl
 * @date 2018年1月23日 下午7:51:08 
 * @version 1.0.0.1
 */
public class DbSupport extends BaseClass {
	
	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		String type = param.getString("type");
		String shell = "";
		String arr[] = param.getString("s_" + type).split("@");
		for(String s : arr) {
			shell += s + " ";
		}
		shell += param.getString("t_n") + " ";
		for(int i = 0 ;i < param.getInteger("num") ; i ++) {
			shell += param.getString(param.getString("type") + "_" + i) + " ";
		}

		Connection con = this.getConnection(param.getString("b_u") , param.getString("b_n") , param.getString("b_p")); // 获取连接
		try {
			PreparedStatement pstmt = con.prepareStatement(shell);
			if(!type.equals("s")) {
				int count=pstmt.executeUpdate();
				result.put("count", count);
			}else {
				ResultSet rs = pstmt.executeQuery();
				result.put("data", this.convertList(rs));
			}
			this.close(pstmt, con);
			
			result.put("status", "success");
			result.put("msg", "命令执行成功!");
			result.put("dto", param);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "SQLException!");
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
		}
		return result;
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
	
	/**
	 * @description: 返回查询结果集 
	 *
	 * @author Yangcl
	 * @date 2018年1月23日 下午6:07:30 
	 * @version 1.0.0.1
	 */
	private List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> entity = new HashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                entity.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(entity);
        }
	    return list;
	}
}








