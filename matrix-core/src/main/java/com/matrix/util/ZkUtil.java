package com.matrix.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZkUtil  implements Watcher{
	
	private ZooKeeper zookeeper;
	private static final int SESSION_TIME_OUT = 2000;
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	
	public ZkUtil(String host) throws Exception {
		this.connectZookeeper(host);
	}
	
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
	}

	/**
	 * @description: 连接zookeeper
	 *
	 * @param host
	 * @throws Exception 
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:32:53 
	 * @version 1.0.0.1
	 */
	public void connectZookeeper(String host) throws Exception{
		zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
		countDownLatch.await();
	}
	
	/**
	 * @description: 创建节点
	 *
	 * @param path
	 * @param data
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:35:13 
	 * @version 1.0.0.1
	 */
	public String addNode(String path,String data) throws Exception{
		return this.zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	/**
	 * @description: 删除节点
	 *
	 * @param path
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:39:22 
	 * @version 1.0.0.1
	 */
	public void deleteNode(String path) throws InterruptedException, KeeperException{
		zookeeper.delete(path, -1);
	}
	
	/**
	 * @description: 设置节点信息
	 *
     * @param path  路径
     * @param data  数据
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:37:46 
	 * @version 1.0.0.1
	 */
	public Stat updateData(String path , String data) throws KeeperException, InterruptedException{
		Stat stat = zookeeper.setData(path, data.getBytes(), -1);
		return stat;
	}
	
	/**
	 * @description: 获取节点上面的数据
	 *
	 * @param path
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:36:47 
	 * @version 1.0.0.1
	 */
	public String findNodeValue(String path) throws KeeperException, InterruptedException {
		byte[] data = zookeeper.getData(path, false, null);
		if (data == null) {
			return "";
		}
		return new String(data);
	}
	
	/**
	 * @description: 获取路径下所有子节点
	 *
	 * @param path
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:35:53 
	 * @version 1.0.0.1
	 */
	public List<String> listFirstNode(String path) throws KeeperException, InterruptedException{
		List<String> children = zookeeper.getChildren(path, false);
        return children;
	}
	
	public List<String> listAllNode(String path) throws Exception {
		List<String> list = new ArrayList<String>();
        this.lsr(path, list);
		System.out.println(list.size());
        return list;
    }
	
	private void lsr(String path , List<String> list_) throws Exception {
        List<String> list = zookeeper.getChildren(path , false);
        // 判断是否有子节点
        if(list.isEmpty() || list == null){
        	list_.add(path);
            return ;
        }
        
        for(String s : list){
            if(path.equals("/")){	// 判断是否为根目录
            	lsr(path + s ,  list_);
            }else {
            	lsr(path +"/" + s ,  list_);
            }
        }
    }
	
	
	/**
	 * @description: 获取创建时间
	 *
	 * @param path
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:40:34 
	 * @version 1.0.0.1
	 */
	public String getCreateTime(String path) throws KeeperException, InterruptedException{
		Stat stat = zookeeper.exists(path, false);
		return String.valueOf(stat.getCtime());
	}
	
	/**
	 * @description: 获取某个路径下孩子的数量
	 *
	 * @param path
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:40:57 
	 * @version 1.0.0.1
	 */
	public Integer getChildrenNum(String path) throws KeeperException, InterruptedException{
		int childenNum = zookeeper.getChildren(path, false).size();
		return childenNum;
	}
	
	/**
	 * @description: 关闭连接
	 *
	 * @author Yangcl
	 * @date 2017年7月13日 下午3:41:41 
	 * @version 1.0.0.1
	 */
	public void closeConn(){
		if (zookeeper != null) {
	         try {
				zookeeper.close();
			} catch (InterruptedException e) {
			}
	      }
	}
}





















