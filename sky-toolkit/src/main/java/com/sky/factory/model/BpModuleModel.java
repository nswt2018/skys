package com.sky.factory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("BpModuleModel")
@Table(name="bp_module")
public class BpModuleModel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MODU_CODE")
	private String moduCode;

	@Column(name="MODU_CNAME")
    private String moduCName;

	@Column(name="MODU_ENAME")
    private String moduEName;

	@Column(name="MODU_TC")
    private String moduTC;
	
	@Column(name="MODU_MODEL")
    private String moduModel;
	
	@Column(name="MOD_NAME")
    private String modName;

	@Column(name="CRT_DATE")
    private Date crtDate;

	@Column(name="UPD_DATE")
    private Date updDate;
	
	@Column(name="REL_TABLE")
	private String relTable;
	
	@Column(name="REL_INFO")
	private String relInfo;

	public String getModuCode() {
		return moduCode;
	}

	public void setModuCode(String moduCode) {
		this.moduCode = moduCode;
	}

	public String getModuCName() {
		return moduCName;
	}

	public void setModuCName(String moduCName) {
		this.moduCName = moduCName;
	}

	public String getModuEName() {
		return moduEName;
	}

	public void setModuEName(String moduEName) {
		this.moduEName = moduEName;
	}

	public String getModuTC() {
		return moduTC;
	}

	public void setModuTC(String moduTC) {
		this.moduTC = moduTC;
	}

	public String getModuModel() {
		return moduModel;
	}

	public void setModuModel(String moduModel) {
		this.moduModel = moduModel;
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

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getRelTable() {
		return relTable;
	}

	public void setRelTable(String relTable) {
		this.relTable = relTable;
	}

	public String getRelInfo() {
		return relInfo;
	}

	public void setRelInfo(String relInfo) {
		this.relInfo = relInfo;
	}
}