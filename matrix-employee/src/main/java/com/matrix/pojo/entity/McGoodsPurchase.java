package com.matrix.pojo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McGoodsPurchase extends BaseEntity{
	private static final long serialVersionUID = 8191737379576585088L;

	/**物资采购记录|归属EHR系统*/
    private Long id;

    /**采购批次流水号*/
    private String serialNumber;

    /**采购商品名称*/
    private String produceName;

    /**采购商品的sku*/
    private String sku;

    /**采购商品图片，多图以逗号分隔*/
    private String picUrl;

    /**
     * 	采购类型：
     * 		1-大棚生产物资 
     * 		2-员工福利商品 
     * 		3-员工食堂物资 
     * 		4-办公耗材(纸张/油墨/门锁/饮用水) 
     * 		5-拜访客户礼品 
     * 		6-敬老助学礼品
     * */
    private Integer purchaseType;

    /**采购渠道：京东|淘宝|拼多多|阿里巴巴|实体商户*/
    private String purchaseChannle;

    /**采购店铺名称*/
    private String shopName;

    /**商品单价*/
    private BigDecimal unitPrice;

    /**商品采购数量*/
    private Integer quantity;

    /**实际入库数量，由入库员更新*/
    private Integer realGetQuantity;

    /**商品保质期，单位：天*/
    private Integer dateOfMinimumDurability;

    /**商品下单时间*/
    private Date orderTime;

    /**商品到达时间*/
    private Date arrivalTime;

    /**付款人银行名称*/
    private String draweeBankName;

    /**付款人银行账户*/
    private String draweeBankCard;

    /**财务实际拨款金额*/
    private BigDecimal allocateFunds;

    /**采购人员实际付款金额*/
    private BigDecimal paymentFunds;

    /**采购人员实际付款日期*/
    private Date paymentFundsTime;

    /**退款金额，如果出现商品采购数量大于入库数量则会产生退款*/
    private BigDecimal refund;

    /**退款状态，由财务更新 0-未产生退款 1-退款未归还 2-退款已归还财务*/
    private Integer refundStatus;

    /**
     * 	审批流转状态：
     * 		0-申请中 
     * 		1-通过 
     * 		2-驳回 
     * 		3-财务拨款中 
     * 		4-拨款完成 
     * 		5-采购中 
     * 		6-采购完成 
     * 		7-已到货 
     * 		8-验货完成 
     * 		9-已收货 
     * 		10-已拒收 
     * 		11-已入库 
     * 		12-产生退款(计算后自动触发-通知付款人) 
     * 		13-退款归还完成(财务触发) 
     * 		14-流转完成(触发条件：驳回|已拒收|入库完成|退款完成)
     * */
    private Integer status;

    /**发票图片，多图以逗号分隔。采购负责人进行更新。*/
    private String invoiceUrl;

    /**采购申请人id*/
    private Long applicantUserId;

    /**采购负责人id，如果采购商品质量出现问题则由此人负责*/
    private Long responsibleUserId;

    /**验货人id，如果采购商品质量出现问题则此人共同担责*/
    private Long surveyorUserId;

    /**入库人id*/
    private Long storeroomUserId;

    /**备注，实际入库数量与采购数量不一致需要描述清楚，货物破损是否重发了*/
    private String remark;

}















