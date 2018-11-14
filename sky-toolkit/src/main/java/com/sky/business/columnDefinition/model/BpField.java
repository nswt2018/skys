package com.sky.business.columnDefinition.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpField")
@Table(name="bp_Field")
public class BpField implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TAB_CODE")
	private String tabCode;
	
	@Id
	@Column(name="COL_CODE")
	private String colCode;

	@Column(name="COL_NAME")
    private String colName;

	@Column(name="DATA_TYPE")
    private String dataType;

	@Column(name="DATA_LEN")
    private String dataLen;
	
	@Column(name="PK_GEN")
	private String pkGen;
	
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

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLen() {
		return dataLen;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public String getPkGen() {
		return pkGen;
	}

	public void setPkGen(String pkGen) {
		this.pkGen = pkGen;
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