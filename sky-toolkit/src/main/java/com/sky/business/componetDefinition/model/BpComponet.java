package com.sky.business.componetDefinition.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpComponet")
@Table(name="bp_componet")
public class BpComponet implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="COM_CODE")
	private String comCode;

	@Column(name="COM_NAME")
    private String comName;

	@Column(name="TEMPLATE")
    private String template;

	@Column(name="REL_TAG")
    private String relTag;

	@Column(name="MOD_CODE")
    private String modCode;
	
	@Column(name="CRT_DATE")
    private Date crtDate;
	
	@Column(name="UPD_DATE")
    private Date updDate;

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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getRelTag() {
		return relTag;
	}

	public void setRelTag(String relTag) {
		this.relTag = relTag;
	}

	public String getModCode() {
		return modCode;
	}

	public void setModCode(String modCode) {
		this.modCode = modCode;
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