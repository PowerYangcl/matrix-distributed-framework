package com.matrix.pojo.entity;

import java.math.BigDecimal;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @description: 初版岗位树
 * 
		1-管理/财务/HR岗			职员/经理/总经理/副总/CEO/董事长
			1-董事长
			2-董事会成员
			3-首席执行官(管理运营岗/IT技术岗)
			4-生产总监(管理生产岗：自营/外采/自营餐厅/娱乐项目)
			5-生产总监助理
			6-园区总监(管理服务岗)
			7-园区总监助理(物资采购/会以安排等)
			8-财务总监
			9-出纳
			10-人力资源经理
			11-人力资源助理
		
		2-运营岗					职员/经理/总经理/副总			【成本中心】
			1-运营总监
			2-运营经理
			3-运营助理
			4-售后服务经理(客服)
			5-售后服务助理(客服)
			6-活动策划经理
			7-活动策划助理
			8-法务经理
			9-市场总监
			10-市场推广员(在职)
			11-市场推广员(非在职)
			12-政务总监(政府关系维护、政策处理、助贫助学)
		
		3-IT技术岗					中级/高级/资深/经理/架构/CTO				【成本中心】
			1-技术总监(CTO)
			2-首席架构师
			3-技术经理
			4-资深研发工程师
			5-高级研发工程师
			6-研发工程师
			7-运维总监
			8-运维架构师(中间件)
			9-资深运维工程师
			10-高级运维工程师
			11-DBA架构师
			12-资深DBA工程师
		
		4-生产岗(自营)				学员/初级培育员/中级培育员/高级培育员/技术专家
			1-棚区经理
			2-蔬菜培育员(技术专家)
			2-蔬菜培育员(高级培育员)
			2-蔬菜培育员(中级培育员)
			2-蔬菜培育员(初级培育员)
			2-蔬菜培育员(学员)
			
			3-家禽培育员(技术专家)
			3-家禽培育员(高级培育员)
			3-家禽培育员(中级培育员)
			3-家禽培育员(初级培育员)
			3-家禽培育员(学员)
			
			4-生猪培育员(技术专家)
			4-生猪培育员(高级培育员)
			4-生猪培育员(中级培育员)
			4-生猪培育员(初级培育员)
			4-生猪培育员(学员)
			
			5-牛羊培育员(技术专家)
			5-牛羊培育员(高级培育员)
			5-牛羊培育员(中级培育员)
			5-牛羊培育员(初级培育员)
			5-牛羊培育员(学员)
			
			
			6-物料库管员			职员
			7-家畜屠宰员			职员
			8-堆肥师				职员
		
		5-生产岗(外采)				职员/经理/总经理
			1-外采总经理
			2-外采蔬菜经理
			2-外采蔬菜助理
			3-外采家禽经理(禽蛋)
			3-外采家禽助理(禽蛋)
			4-外采牛羊肉经理
			4-外采牛羊肉助理
			5-外采食用菌经理
			5-外采食用菌助理
			6-外采水果经理
			6-外采水果助理
			
		6-生产岗(自营餐厅)
			1-餐厅经理
			2-餐厅厨师						职员
			3-餐厅切菜工					职员
			4-餐厅服务员					职员
			5-餐厅杂物工(洗碗/打扫卫生)		职员
			
		7-生产岗(娱乐项目)
			1-娱乐项目经理(管理/娱乐物资采购)
			2-游乐园安全质检员
			3-游乐园服务员(收费/服务)
			4-垂钓园服务员(收费/服务)
			5-民宿服务员
			
		8-服务岗					职员/经理						【成本中心】
			1-客户物资配送经理
			2-客户物资配送员(非在职)
			3-园区电工(非在职)
			4-园区保安经理
			5-园区保安
			6-园区清洁工(非在职)
			7-园区服务员(引导/便利店员)
			8-园区维修部经理
			9-园区维修工(棚区生产资料/车辆/娱乐设施)	
			10-园区食堂经理(厨师兼任)
			11-园区食堂厨师
			12-园区食堂服务员
			13-员工幼儿托育经理
			14-员工幼儿托育员
 * 
 * @author Yangcl
 * @date 2022-11-29 15:28:12
 * @home https://github.com/PowerYangcl
 * @path matrix-employee / com.matrix.pojo.entity.McUserPosition.java
 * @version v1.6.1.6-multiple-jspweb
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class McUserPosition  extends BaseEntity{
	private static final long serialVersionUID = 2561685678740255251L;

	/**员工岗位表主键|归属EHR系统*/
    private Long id;

    /**
     * 	岗位类型：
     * 		1-管理/财务/HR岗 
     * 		2-运营岗 
     * 		3-IT技术岗 
     * 		4-生产岗(自营) 
     * 		5-生产岗(外采) 
     * 		6-生产岗(自营餐厅) 
     * 		7-生产岗(娱乐项目) 
     * 		8-服务岗
     * */
    private Integer positionType;

    /**岗位名称*/
    private String position;

    /**
     * 	职级：
     * 		管理序列职级：职员/经理/总经理/副总/CEO/董事长
     * 		生产序列职级：学员/初级培育员/中级培育员/高级培育员/技术专家
     * 		IT技术序列职级：中级/高级/资深/架构
     * */
    private String positionLevel;

    /**岗位技能要求*/
    private String skillRequirement;

    /**薪资上限*/
    private BigDecimal salaryCap;

    /**薪资下限*/
    private BigDecimal salaryFloor;

    /**绩效薪资*/
    private BigDecimal meritPay;

    /**年终奖上限月数，仅限在职人员，标准14薪对应此基数为2*/
    private Integer annualBonus;

    /**岗位晋升条件*/
    private String promotion;

    /**1-在职岗位 2-外包岗位*/
    private Integer activeStaff;

    /**职能描述*/
    private String remark;
}