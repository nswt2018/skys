package com.sky.factory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpSystems")
@Table(name="bp_systems")
public class BpSystems implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@Column(name="SYS_KEY")
	private String sysKey;

	@Column(name="SYS_CODE")
    private String sysCode;

	@Column(name="SYS_NAME")
    private String sysName;
	
	@Column(name="SYS_COMM")
	private String sysComm;
	
	@Column(name="UPPER_SYS")
	private String upperSys;
	
	@Column(name="UPPER_NAME")
	private String upperName;
	
	@Column(name="MOD_CODE")
    private String modCode;

	@Column(name="VUE_PATH")
    private String vuePath;

	@Column(name="JAVA_PATH")
    private String javaPath;
	
	@Column(name="PACK_NAME")
    private String packName;
	
	@Column(name="CRT_DATE")
    private Date crtDate;
	
	@Column(name="UPD_DATE")
	private Date updDate;

	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysComm() {
		return sysComm;
	}

	public void setSysComm(String sysComm) {
		this.sysComm = sysComm;
	}

	public String getUpperSys() {
		return upperSys;
	}

	public void setUpperSys(String upperSys) {
		this.upperSys = upperSys;
	}

	public String getModCode() {
		return modCode;
	}

	public void setModCode(String modCode) {
		this.modCode = modCode;
	}

	public String getVuePath() {
		return vuePath;
	}

	public void setVuePath(String vuePath) {
		this.vuePath = vuePath;
	}

	public String getJavaPath() {
		return javaPath;
	}

	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
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

	public String getUpperName() {
		return upperName;
	}

	public void setUpperName(String upperName) {
		this.upperName = upperName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}