package com.matrix.pojo.view;

public class BaseDubboModel {
	
	private Long id;
	
	public BaseDubboModel(){}
	
    public BaseDubboModel(Long id){
        this.id = id;
    }


    public long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return "BasicModel{" + "id=" + id + '}';
    }
}
