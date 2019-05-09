package com.matrix.pojo.view;




public class Node extends BaseDubboModel{
	
	private String nodeAddress;
	private String type; // 生产者 - 消费者


    public String getNodeAddress() {
        return nodeAddress;
    }
    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals(Object o) {
        if (this == o) 
        	return true;
        if (o == null || getClass() != o.getClass()) 
        	return false;

        Node node = (Node) o;

        if (nodeAddress != null ? !nodeAddress.equals(node.nodeAddress) : node.nodeAddress != null) 
        	return false;

        return true;
    }

    public int hashCode() {
        return nodeAddress != null ? nodeAddress.hashCode() : 0;
    }
}




































