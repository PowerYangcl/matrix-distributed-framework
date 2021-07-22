package com.matrix.cache.enums;

/**
 * @desc Redis连接方式
 *          包含:standalone-单节点部署方式
 *              sentinel-哨兵部署方式
 *              cluster-集群方式
 *              masterslave-主从部署方式
 *
 * @author xub
 * @date 2019/6/20 下午4:21
 */
public enum RedisModel {

	standalone("standalone", "单节点部署方式"),
	sentinel("sentinel", "哨兵部署方式"),
	cluster("cluster", "集群方式"),
	masterReplica("master-replica", "主从部署方式");

    private final String model;
    private final String desc;

    private RedisModel(String model, String desc) {
        this.model = model;
        this.desc = desc;
    }

    public String getModel() {
        return model;
    }

    public String getDesc() {
        return desc;
    }
}
