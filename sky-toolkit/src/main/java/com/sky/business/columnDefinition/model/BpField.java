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
	
	@Column(name="JOIN_TAB_CODE")
	private String joinTabCode;
	
	@Column(name="JOIN_COL_CODE")
	private String joinColCode;
	
	@Column(name="JOIN_WHERE")
	private String joinWhere;
	
	@Column(name="UI_TYPE")
	private String uiType;
	
	@Column(name="UI_ORDER")
	private Integer uiOrder;
	
	@Column(name="VAL_BETWEEN")
	private String valBetween;
	
	@Column(name="CRT_DATE")
	private Date crtDate;
	
	@Column(name="UPD_DATE")
	private Date updDate;

	@Column(name="DOMAINTYPE")
	private String doMainType;
	
	public String getTabCode() {
		return tabCode;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}
	
	public String getDoMainType() {
		return doMainType;
	}

	public void setDoMainType(String doMainType) {
		this.doMainType = doMainType;
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
	
	public String getJoinTabCode() {
		return joinTabCode;
	}

	public void setJoinTabCode(String joinTabCode) {
		this.joinTabCode = joinTabCode;
	}

	public String getJoinColCode() {
		return joinColCode;
	}

	public void setJoinColCode(String joinColCode) {
		this.joinColCode = joinColCode;
	}

	public String getJoinWhere() {
		return joinWhere;
	}

	public void setJoinWhere(String joinWhere) {
		this.joinWhere = joinWhere;
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	public Integer getUiOrder() {
		return uiOrder;
	}

	public void setUiOrder(Integer uiOrder) {
		this.uiOrder = uiOrder;
	}

	public String getValBetween() {
		return valBetween;
	}

	public void setValBetween(String valBetween) {
		this.valBetween = valBetween;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}