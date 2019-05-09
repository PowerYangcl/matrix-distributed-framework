package com.matrix.pojo.view;

import java.io.Serializable;

import com.matrix.base.BaseView;

public class CompanyPaymentInfoView extends BaseView implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8267112714514018157L;
	/**
	 * 公司支付信息id
	 */
	private Long id;
    /** 客户id*/
    private Long cid;
    /** APPID*/
    private String appid;

    /** 应用公钥*/
    private String publicKey;

    /** 商户私钥*/
    private String privateKey;

    /** 商户号*/
    private String partner;

    /** 应用秘钥*/
    private String appSecret;

    /** 支付类型*/
    private Integer payType;

   
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey == null ? null : publicKey.trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}