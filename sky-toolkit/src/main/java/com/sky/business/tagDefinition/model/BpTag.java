package com.sky.business.tagDefinition.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpTag")
@Table(name="bp_tag")
public class BpTag implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAG_KEY")
	private String tagKey;
	
	@Column(name="TAG_CODE")
	private String tagCode;

	@Column(name="TAG_NAME")
    private String tagName;

	@Column(name="TAG_PROP")
    private String tagProp;

	@Column(name="PROP_TYPE")
    private String propType;

	@Column(name="PROP_VAL")
    private String propVal;
	
	@Column(name="USE_RULE")
    private String useRule;
    
    @Column(name="DEFAULTVALUE")
    private String defaultValue;

	@Column(name="CRT_DATE")
    private Date crtDate;

	@Column(name="UPD_DATE")
    private Date updDate;

	public String getTagKey() {
		return tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagProp() {
		return tagProp;
	}

	public void setTagProp(String tagProp) {
		this.tagProp = tagProp;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

	public String getPropVal() {
		return propVal;
	}

	public void setPropVal(String propVal) {
		this.propVal = propVal;
	}

	public String getUseRule() {
		return useRule;
	}

	public void setUseRule(String useRule) {
		this.useRule = useRule;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}