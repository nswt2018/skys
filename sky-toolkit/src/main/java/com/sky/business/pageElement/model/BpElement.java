package com.sky.business.pageElement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpElement")
@Table(name="bp_element")
public class BpElement implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@Column(name="ELE_CODE")
	private Integer eleCode;

	@Column(name="ELE_ENAME")
    private String eleEName;

	@Column(name="ELE_CNAME")
    private String eleCName;
	
	@Column(name="UNIT_CODE")
    private String unitCode;

	@Column(name="TAG_INFO")
    private String tagInfo;

	@Column(name="CRT_DATE")
    private Date crtDate;
	
	@Column(name="UPD_DATE")
    private Date updDate;

	@Column(name="COM_CODE")
    private String comCode;
	
	@Column(name="COM_NAME")
    private String comName;
	
	@Column(name="MODU_CODE")
    private String moduCode;
	
	@Column(name="UNIT_NAME")
    private String unitName;

	
	public Integer getEleCode() {
		return eleCode;
	}

	public void setEleCode(Integer eleCode) {
		this.eleCode = eleCode;
	}

	public String getEleEName() {
		return eleEName;
	}

	public void setEleEName(String eleEName) {
		this.eleEName = eleEName;
	}

	public String getEleCName() {
		return eleCName;
	}

	public void setEleCName(String eleCName) {
		this.eleCName = eleCName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getTagInfo() {
		return tagInfo;
	}

	public void setTagInfo(String tagInfo) {
		this.tagInfo = tagInfo;
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

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getModuCode() {
		return moduCode;
	}

	public void setModuCode(String moduCode) {
		this.moduCode = moduCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}