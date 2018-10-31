package com.sky.business.modelDefinition.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpModel")
@Table(name="bp_model")
public class BpModel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MOD_CODE")
	private String modCode;

	@Column(name="MOD_NAME")
    private String modName;

	@Column(name="MOD_VERSION")
    private String modVersion;

	@Column(name="CRT_DATE")
    private Date crtDate;

	@Column(name="UPD_DATE")
    private Date updDate;
	
	public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode == null ? null : modCode.trim();
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName == null ? null : modName.trim();
    }

    public String getModVersion() {
        return modVersion;
    }

    public void setModVersion(String modVersion) {
        this.modVersion = modVersion == null ? null : modVersion.trim();
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