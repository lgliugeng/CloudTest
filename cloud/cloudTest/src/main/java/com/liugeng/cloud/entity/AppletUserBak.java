package com.liugeng.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@TableName("applet_user_bak")
public class AppletUserBak implements Serializable {


    private static final long serialVersionUID = -1429232562597736879L;

    @TableId
    private Integer id;


    private String openId;



    private String appletOpenId;


    private String publicOpenId;


    private String nickName;

    private String headImgUrl;


    private String sex;


    private String country;


    private String province;


    private String city;

    private String language;


    private String unionId;


    private Date createTime;


    private Date updateTime;


    private String phone;

    private String idCard;



    private String name;


    private String xqcUserName;


    private String xqcUserLogo;


    private String companyName;


    private String xqcOpenId;


    private String type;


    private String userCode;


    private String userId;


    private String bindFlag;

    @Transient
    @TableField(exist = false)
    private String account;//账户余额

    public String getAppletOpenId() {
        return appletOpenId;
    }

    public void setAppletOpenId(String appletOpenId) {
        this.appletOpenId = appletOpenId;
    }

    public String getPublicOpenId() {
        return publicOpenId;
    }

    public void setPublicOpenId(String publicOpenId) {
        this.publicOpenId = publicOpenId;
    }

    
    public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXqcUserName() {
        return xqcUserName;
    }

    public void setXqcUserName(String xqcUserName) {
        this.xqcUserName = xqcUserName;
    }

    public String getXqcUserLogo() {
        return xqcUserLogo;
    }

    public void setXqcUserLogo(String xqcUserLogo) {
        this.xqcUserLogo = xqcUserLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getXqcOpenId() {
        return xqcOpenId;
    }

    public void setXqcOpenId(String xqcOpenId) {
        this.xqcOpenId = xqcOpenId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(String bindFlag) {
        this.bindFlag = bindFlag;
    }
}
