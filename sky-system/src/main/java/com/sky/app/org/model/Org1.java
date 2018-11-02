package com.sky.app.org.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("org1")
@Table(name = "sys_org")
public class Org1 implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	// 机构ID
	@Column(name = "ORG_ID")
	private String orgId;
	// 机构编码
	@Column(name = "ORG_CODE")
	private String orgCode;
	// 机构简称
	@Column(name = "ORG_BRIEF")
	private String orgBrief;
	// 直接上级机构ID
	@Column(name = "up_ORG_CODE")
	private String upOrgCode;
	// 机构类别
	@Column(name = "ORG_CATEGORY")
	private String orgCategory;
	// 机构级别
	@Column(name = "ORG_GRADE")
	private String orgGrade;
	// 机构层级
	@Column(name = "ORG_LEVEL")
	private String orgLevel;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgBrief() {
		return orgBrief;
	}

	public void setOrgBrief(String orgBrief) {
		this.orgBrief = orgBrief;
	}

	public String getUpOrgCode() {
		return upOrgCode;
	}

	public void setUpOrgCode(String upOrgCode) {
		this.upOrgCode = upOrgCode;
	}

	public String getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(String orgCategory) {
		this.orgCategory = orgCategory;
	}

	public String getOrgGrade() {
		return orgGrade;
	}

	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
}
