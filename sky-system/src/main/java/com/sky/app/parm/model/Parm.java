package com.sky.app.parm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;
		/*
		 * 系统管理->系统参数-参数表   Parm类
		 */
@Alias("parm")
@Table(name="sys_parm")
public class Parm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	    * 主键
	    */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*
	 * 参数代码
	 */
	private String paraCode;
	/*
	 * 参数名称
	 */
	private String paraName;
	/*
	 * 参数值
	 */
	private String paraValue;
	/*
	 * 参数描述
	 */
	private String paraDesc;
	
	/*
	 * 参数类别
	 */
	private String categoryId;
	/*
	 * 上级分类
	 */
	private String parentId;
	/*
	 * 创建人
	 */
	private String crtUserCode;
	/*
	 * 创建人机构
	 */
	private String crtOrgCode;
	/*
	 * 创建时间
	 */
	private Date crtDate;
	/*
	 * 修改人
	 */
	private String updUserCode;
	/*
	 * 修改机构
	 */
	private String updOrgCode;
	/*
	 * 修改时间
	 */
	private Date updDate;
	
	public String getParaCode() {
		return paraCode;
	}
	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}
	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	public String getParaValue() {
		return paraValue;
	}
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	public String getParaDesc() {
		return paraDesc;
	}
	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCrtUserCode() {
		return crtUserCode;
	}
	public void setCrtUserCode(String crtUserCode) {
		this.crtUserCode = crtUserCode;
	}
	public String getCrtOrgCode() {
		return crtOrgCode;
	}
	public void setCrtOrgCode(String crtOrgCode) {
		this.crtOrgCode = crtOrgCode;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getUpdUserCode() {
		return updUserCode;
	}
	public void setUpdUserCode(String updUserCode) {
		this.updUserCode = updUserCode;
	}
	public String getUpdOrgCode() {
		return updOrgCode;
	}
	public void setUpdOrgCode(String updOrgCode) {
		this.updOrgCode = updOrgCode;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
