package com.matrix.base.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseDto;
import com.matrix.base.BaseEntity;
import com.matrix.base.BaseView;
import com.matrix.base.RpcResult;


/**
 * @descriptions 底层基本的service的接口
 * 
 * @param <E> 对应的实体类
 * @param <PK> primary key，即数据库主键ID | mycat数据库集群为雪花算法生成的Long型id
 * @param <D> DTO(Data Transfer Object) 数据传输模型        -- 通常包含对应的实体类中的全部字段
 * @param <V> 数据表联合视图模型	   											  -- 通常包含对应的实体类中的全部字段
 * 
 * @date 2016年5月19日下午4:27:55
 * @author Yangcl
 * @version 1.0.1
 */
public interface IBaseService<PK extends Serializable , E extends BaseEntity , D extends BaseDto, V extends BaseView> {

	/**
	 * @descriptions 保存单一对象，如果实体中的某个字段为null则不保存这个字段
	 * 	这种保存方法更加灵活。
	 *  
	 * @auto generator会自动生成该方法
	 * @param entity
	 * @date 2016年5月19日下午3:20:23
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
    public Integer insertSelective(E entity); 
    
    /**
     * @description: 保存单一对象，以Dto数据模型的方式传入参数|提供更加灵活的操作
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月24日 上午3:00:56 
     * @version 1.0.0.1
     */
    public Integer insertSelectiveByDto(D dto);
    
    /**
     * @description: 保存单一对象，并返回这条记录的生成自增id 
     * 
     * 	通常对于mycat集群数据库，返回记录主键套路如下：
     *   <insert id="insertSelective" parameterType="com.******.******.***Entity" >
     * 		<selectKey resultType="java.lang.Long" keyProperty="id" order="AFTER">
     *				select max(id) from aem_dissemination_record where cid = #{cid}
     *			< / selectKey>
     *			…… …… ……
     *			…… …… ……
     *
     * @param entity 
     * 
     * @author Yangcl
     * @date 2018年7月24日 上午3:04:01 
     * @version 1.0.0.1
     */
    public Integer insertGotEntityId(E entity); 
    
    /**
     * @description: 保存单一对象，并返回这条记录的生成自增id 
     * 
     * 	通常对于mycat集群数据库，返回记录主键套路如下：
     *   <insert id="insertSelective" parameterType="com.******.******.***Entity" >
     * 		<selectKey resultType="java.lang.Long" keyProperty="id" order="AFTER">
     *				select max(id) from aem_dissemination_record where cid = #{cid}
     *			< / selectKey>
     *			…… …… ……
     *			…… …… ……
     *
     * @param dto 
     * 
     * @author Yangcl
     * @date 2018年7月24日 上午3:04:01 
     * @version 1.0.0.1
     */
    public Integer insertGotEntityIdByDto(D dto); 
    
    /**
     * @descriptions 批量添加操作，保存一个对象集合
     * 
     * @param list
     * @date 2016年5月19日下午3:28:51
     * @author Yangcl
     * @version 1.0.0.1 
     */
    public Integer batchInsert(List<E> list); 
    
    /**
     * @description: 批量添加操作，保存一个对象集合
     *
     * @param list
     * @author Yangcl
     * @date 2018年7月24日 上午3:09:47 
     * @version 1.0.0.1
     */
    public Integer batchInsertByDto(List<D> list);
    
    
    
    
    /**
     * @descriptions 更新对象,如果属性中的主键(id)为空，则不会进行对应的属性值更新。
     * 	如果实体中的某个字段为null则不更新这个字段。这种更新方法更加灵活。
     * 
     * @auto generator会自动生成该方法
     * @param entity
     * @date 2016年5月19日下午3:33:22
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer updateSelective(E entity); 
    
    /**
     * @description: 提供dto为参数的更新方式
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月24日 上午3:10:56 
     * @version 1.0.0.1
     */
    public Integer updateSelectiveByDto(D dto); 
    
    /**
     * @descriptions 批量更新操作。
     * 
     * @param list
     * @date 2016年5月19日下午3:37:07
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer batchUpdate(List<E> list); 
    
    /**
     * @description: 批量更新操作。
     *
     * @param list
     * @author Yangcl
     * @date 2018年7月24日 上午3:12:19 
     * @version 1.0.0.1
     */
    public Integer batchUpdateByDto(List<D> list); 
    
    
    
    
    /**
     * @descriptions 根据id删除对象|软删除，更新delete_flag字段为0 
     * 
     * @auto generator会自动生成该方法
     * @param id
     * @date 2016年5月19日下午3:41:49
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer deleteById(PK id); 
    
    /**
     * @description: 根据条件删除一条记录|软删除，更新delete_flag字段为0
     *
     * @param entity
     * @author Yangcl
     * @date 2018年7月24日 上午3:14:54 
     * @version 1.0.0.1
     */
    public Integer deleteByEntity(E entity); 
    
    /**
     * @description: 根据条件删除一条记录|软删除，更新delete_flag字段为0
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月24日 上午3:13:50 
     * @version 1.0.0.1
     */
    public Integer deleteByDto(D dto); 
    
    /**
     * @descriptions 根据id list 删除对象集合|删除多条记录 |软删除，更新delete_flag字段为0
     * 
     * @param list
     * @date 2016年5月19日下午3:42:23
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer batchDelete(List<PK> list);
    
    /**
     * @descriptions 根据条件集合删除对象
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中写批量删除脚本
     * 
     * @param dto 数据传输对象(Data Transfer Object)   mybatis进行对象匹配的属性
     * @date 2016年5月19日下午3:45:00
     * @author Yangcl
     * @version 1.0.0.1
     */
//    public <DTO> void deleteByCondition(DTO dto); 

    /**
     * @description: 根据条件，物理删除一条记录|通常来讲，不推荐使用物理删除
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午2:37:09 
     * @version 1.0.0.1
     */
    public Integer deleteFromTable(D dto);
    
    
    
    /**
     * @descriptions 根据id进行对象查询
     * 
     * @param id
     * @date 2016年5月19日下午3:56:41
     * @author Yangcl
     * @version 1.0.0.1
     */
    public E find(PK id); 
    
    /**
     * @description: 根据主键返回联合视图对象
     *
     * @param id
     * @author Yangcl
     * @date 2018年7月25日 下午2:28:59 
     * @version 1.0.0.1
     */
    public V findById(PK id); 
    
    /**
	 * @descriptions 根据实体类中的字段条件返回一条记录
	 * 
	 * @param entity
	 * @date 2016年6月28日下午1:44:04
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
    public E findByType(E entity);
    
    /**
     * @description: 以DTO为查询条件，返回实体类对象模型|sql请加 limit 1
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午2:44:52 
     * @version 1.0.0.1
     */
    public E findEntityByDto(D dto);
    
    /**
     * @description: 以实体类为查询条件，返回联合视图对象模型|sql请加 limit 1
     *
     * @param entity
     * @author Yangcl
     * @date 2018年7月25日 下午2:31:21 
     * @version 1.0.0.1
     */
    public V findViewByEntity(E entity);
    
    /**
     * @description: 以DTO为查询条件，返回联合视图对象模型|sql请加 limit 1
     *
     * @param entity
     * @author Yangcl
     * @date 2018年7月25日 下午2:31:21 
     * @version 1.0.0.1
     */
    public V findViewByDto(D dto);
    
    
    
    /**
     * @descriptions 以实体类为查询条件，返回实体类对象模型列表集合
     * 
     * @param entity 包含查询条件的实体
     * @date 2016年5月19日下午4:00:11
     * @author Yangcl
     * @version 1.0.0.1
     */
    public List<E> findList(E entity); 
    
    /**
     * @description: 以DTO为查询条件，返回实体类对象模型列表集合
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午2:49:58 
     * @version 1.0.0.1
     */
    public List<E> findListByDto(D dto); 
    
    /**
     * @description: 根据主表的外键返回实体集合
     *
     * @param id
     * @author Yangcl
     * @date 2018年7月25日 下午3:12:48 
     * @version 1.0.0.1
     */
    public List<E> findListByForeignKey(PK id);
    
    /**
     * @description: 根据实体条件，返回View视图列表
     *
     * @param entity
     * @author Yangcl
     * @date 2018年8月28日 下午8:55:18 
     * @version 1.0.0.1
     */
    public List<V> findViewListByEntity(E entity);
    
    /**
     * @description: 根据数据传输模型，返回View视图列表
     *
     * @param dto
     * @author Yangcl
     * @date 2018年8月28日 下午8:57:31 
     * @version 1.0.0.1
     */
    public List<V> findViewListByDto(D dto);
    
    /**
     * @descriptions 根据条件进行数量的查询
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @param entity 包含查询条件的实体
     * @return 返回符合条件的泛型参数对应表中的数量
     * @date 2016年5月19日下午4:18:57
     * @author Yangcl
     * @version 1.0.0.1
     */
    public int count(E entity); 
    
    /**
     * @descriptions 根据任意属性进行模糊查询
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @param entity
     * @date 2016年5月19日下午4:23:24
     * @author Yangcl
     * @version 1.0.0.1
     */
    public List<E> like(E entity); 
    
    /**
     * @descriptions 查询实体对应表最大Id.该方法只有在主键为int时才有用
     * 	注意！此方法 可能 需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @exception/throws 如果主键类型不为int，会抛出类型转换异常 
     * @return 返回泛型参数对应表的主键最大值
     * @date 2016年5月19日下午4:24:36
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer selectMaxId(); 
    
    
    
    /**
     * @description: 返回json对象数据
     *
     * @param entity
     * @author Yangcl
     * @date 2018年7月25日 下午8:28:28 
     * @version 1.0.0.1
     */
    public JSONObject jsonList(E entity);
    
	 /**
     * @descriptions 分页|条件为实体类，返回Entity列表
     * 
     * @param entity
     * @date 2016年5月19日下午4:21:53
     * @author Yangcl
     * @version 1.0.0.1
     */
    public JSONObject ajaxPageData(E entity, HttpServletRequest request);
    
    
    
    /**
     * @description: 分页|条件为DTO，返回Entity列表
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午4:05:48 
     * @version 1.0.0.1
     */
    public JSONObject queryPageByDto(D dto , HttpServletRequest request);
    
    /**
     * @description: 分页|条件为DTO，返回View视图列表
     *
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午4:06:14 
     * @version 1.0.0.1
     */
    public JSONObject pageListByDto(D dto , HttpServletRequest request);
    
    
    /**
     * @description: DubboRpc分页|DTO作为查询条件，返回Entity泛型列表
 	 * 	Mapper.xml标识为：queryPageByDto
	 * 	<select id="queryPageByDto" resultMap="BaseResultMap" parameterType="com.matrix.pojo.dto.***Dto">
	 * 
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午4:05:48 
     * @version 1.0.0.1
     */
    public RpcResult<PageInfo<E>> rpcEntityPageByDto(D dto);
    
    
    /**
     * @description: DubboRpc分页|DTO作为查询条件，返回View泛型列表
     * 	Mapper.xml标识为：pageListByDto
     * 	<resultMap id="findListMsfView" type="com.matrix.pojo.view.McSysFunctionView">< / resultMap>
	 * 	<select id="pageListByDto" resultMap="findListMsfView" parameterType="com.matrix.pojo.dto.***Dto">
	 * 
	 * 
     * @param dto
     * @author Yangcl
     * @date 2018年7月25日 下午4:06:14 
     * @version 1.0.0.1
     */
    public RpcResult<PageInfo<V>> rpcViewPageByDto(D dto);
}
























