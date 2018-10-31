package com.sky.business.pageElement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpColumns")
@Table(name="bp_columns")
public class BpColumns implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAG_KEY")
    private String tagKey;

	@Id
	@Column(name="ELE_CODE")
	private String eleCode;
	
	@Column(name="TAG_NAME")
	private String tagName;
	
	@Column(name="TAG_PROP")
    private String tagProp;
	
	@Column(name="PROP_TYPE")
    private String propType;

	@Column(name="TAG_VALUE")
    private String tagValue;

	public String getEleCode() {
		return eleCode;
	}

	public void setEleCode(String eleCode) {
		this.eleCode = eleCode;
	}

	public String getTagKey() {
		return tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
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

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}