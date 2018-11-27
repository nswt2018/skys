package com.sky.app.coder.model;

public class Systems {
	//系统编号
	private String sysKey;
	//系统二位简码
	private String sysCode;
	//系统名称
	private String sysName;
	//上级系统名称
	private String upperName;
	//上级系统编号
	private String upperSys;
	//挂载模块代码
	private String modCode;
	private String vuePath;
	private String javaPath;
	private String packName;
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
	public String getUpperName() {
		return upperName;
	}
	public void setUpperName(String upperName) {
		this.upperName = upperName;
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
	public String getUpperSys() {
		return upperSys;
	}
	public void setUpperSys(String upperSys) {
		this.upperSys = upperSys;
	}
	
}
