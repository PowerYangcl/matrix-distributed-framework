package com.matrix.pojo.view;

public class BasicResponse {

	public static final short SUCCESS = 0;
	public static final short FAILED = 1;

	private short result = SUCCESS;

	private String memo;

	public short getResult() {
		return result;
	}

	public void setResult(short result) {
		this.result = result;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
