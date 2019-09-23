package com.matrix.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public interface IFileUploadService {

	public JSONObject apiFileRemoteUpload(HttpServletRequest request);

	public JSONObject saveFileToLinux(String fileName , byte[] file); 

	public JSONObject apiFileRemoteInject(HttpServletRequest request);

}
