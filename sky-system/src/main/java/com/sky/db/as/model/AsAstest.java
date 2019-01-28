package com.sky.db.as.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.ibatis.type.Alias;

@Alias("AsAstest")
@Table(name="astest")
public class AsAstest {
    @Id
    private Integer astestKey;
    private String astestOne;
    
    public Integer getAstestKey() {
		return astestKey;
	}
	public void setAstestKey(Integer astestKey) {
		this.astestKey = astestKey;
	}
	public String getAstestOne(){
        return this.astestOne;
    }
    public void setAstestOne(String astestOne){
        this.astestOne = astestOne;
    }

}

