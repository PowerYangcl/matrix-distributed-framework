package com.matrix.pojo.dto;

/**
 * @description: 仅用于样例展示使用|TestPublicProcessor.java|TestPrivateProcessor.java
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年12月19日 下午10:53:34 
 * @version 1.0.0.1
 */
public class ProcessorTestDto {
	private Integer id;
    private String name = "";
    private String password = "";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
