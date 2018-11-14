package com.sky.business.tableDefinition.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpTable")
@Table(name="bp_tables")
public class BpTable implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAB_CODE")
	private String tabCode;
	
	@Column(name="TAB_NAME")
	private String tabName;

	@Column(name="TAB_COMM")
    private String tabComm;

	@Column(name="CRT_DATE")
    private Date crtDate;

	@Column(name="UPD_DATE")
    private Date updDate;

	public String getTabCode() {
		return tabCode;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabComm() {
		return tabComm;
	}

	public void setTabComm(String tabComm) {
		this.tabComm = tabComm;
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