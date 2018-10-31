package com.sky.business.tagDefinition.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpTagTree")
@Table(name="bp_tagTree")
public class BpTagTree implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAG_ID")
	private String tagID;

	@Column(name="TAG_NAME")
    private String tagName;

	@Column(name="UP_TAG_ID")
    private String upTagID;

	@Column(name="CRT_DATE")
    private Date crtDate;

	@Column(name="IS_ROOT")
    private String isRoot;
	
	public String getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}

	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getUpTagID() {
		return upTagID;
	}

	public void setUpTagID(String upTagID) {
		this.upTagID = upTagID;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}