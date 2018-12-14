package com.sky.business.systemModule.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpTransaction")
@Table(name="bp_transaction")
public class BpTransaction implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NOD_CODE")
	private String nodCode;

	@Column(name="NOD_NAME")
    private String nodName;

	@Column(name="TRAN_CODE")
    private String tranCode;

	@Column(name="SHOW_COND")
    private String showCond;
	
	@Column(name="SHOW_PARAM")
    private String showParam;
	
	@Column(name="UP_NOD_CODE")
	private String upNodCode;
	
	@Column(name="UP_NOD_NAME")
	private String upNodName;

	public String getUpNodName() {
		return upNodName;
	}

	public void setUpNodName(String upNodName) {
		this.upNodName = upNodName;
	}

	public String getNodCode() {
		return nodCode;
	}

	public void setNodCode(String nodCode) {
		this.nodCode = nodCode;
	}

	public String getNodName() {
		return nodName;
	}

	public void setNodName(String nodName) {
		this.nodName = nodName;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getShowCond() {
		return showCond;
	}

	public void setShowCond(String showCond) {
		this.showCond = showCond;
	}

	public String getShowParam() {
		return showParam;
	}

	public void setShowParam(String showParam) {
		this.showParam = showParam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUpNodCode() {
		return upNodCode;
	}

	public void setUpNodCode(String upNodCode) {
		this.upNodCode = upNodCode;
	}
}