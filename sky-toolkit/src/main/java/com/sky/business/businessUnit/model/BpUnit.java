package com.sky.business.businessUnit.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpUnit")
@Table(name="bp_unit")
public class BpUnit implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="UNIT_CODE")
	private String unitCode;

	@Column(name="UNIT_NAME")
    private String unitName;

	@Column(name="MODU_CODE")
    private String moduCode;
	
	@Column(name="COM_CODE")
	private String comCode;
	
	@Column(name="COM_NAME")
	private String comName;
	
	@Column(name="REL_TABLE")
    private String relTable;

	@Column(name="REL_COLUMN")
    private String relColumn;

	@Column(name="REL_INFO")
    private String relInfo;
	
	@Column(name="CRT_DATE")
    private Date crtDate;
	
	@Column(name="UPD_DATE")
    private Date updDate;

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getModuCode() {
		return moduCode;
	}

	public void setModuCode(String moduCode) {
		this.moduCode = moduCode;
	}

	public String getRelTable() {
		return relTable;
	}

	public void setRelTable(String relTable) {
		this.relTable = relTable;
	}

	public String getRelColumn() {
		return relColumn;
	}

	public void setRelColumn(String relColumn) {
		this.relColumn = relColumn;
	}

	public String getRelInfo() {
		return relInfo;
	}

	public void setRelInfo(String relInfo) {
		this.relInfo = relInfo;
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
}