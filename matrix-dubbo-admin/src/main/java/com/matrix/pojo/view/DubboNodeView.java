package com.matrix.pojo.view;

public class DubboNodeView {
	public static final short PROVIDER = 1;
	public static final short CONSUMER = 2;
	public static final short PROVIDER_AND_CONSUMER = 3; 

    private String application; /* 应用名 */
    private String username;      /* 提供者用户名 */
    private short type;
    private String flag; // 生产者-消费者
    private String dubboAddr; // dubbo节点IP+端口号

    public short getType() {
        return type;
    }
    public void setType(short type) {
        this.type = type;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getApplication() {
        return application;
    }
    public void setApplication(String application) {
        this.application = application;
    }
    public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDubboAddr() {
		return dubboAddr;
	}
	public void setDubboAddr(String dubboAddr) {
		this.dubboAddr = dubboAddr;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DubboNodeView that = (DubboNodeView) o;

        if (application != null ? !application.equals(that.application) : that.application != null) 
        	return false;
        
        if (username != null ? !username.equals(that.username) : that.username != null) 
        	return false;
        
        if (dubboAddr != null ? !dubboAddr.equals(that.dubboAddr) : that.dubboAddr != null) 
        	return false;

        return true;
    }

    @java.lang.Override
    public int hashCode() {
        int result = application != null ? application.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 32 * result + (dubboAddr != null ? dubboAddr.hashCode() : 0);
        return result;
    }
}

















