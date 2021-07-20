package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.matrix.base.Result;

public interface IApiService {

	public Result<?> apiService(HttpServletRequest request, HttpServletResponse response , HttpSession session , String json); 

}
