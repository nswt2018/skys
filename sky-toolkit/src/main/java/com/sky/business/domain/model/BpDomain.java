package com.sky.business.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpDomain")
@Table(name="BP_DOMAIN")
public class BpDomain {
	
    private String cname;



    @GeneratedValue(generator="UUID")
    private String ename;

    }
    
    public void setCname(String cname){
    }
    
    public String getDataLen(){
    }
    
    public void setDataLen(String dataLen){
    }
    
    public String getDataType(){
    }
    
    public void setDataType(String dataType){
    }
    
    public String getEname(){
    }
    
    public void setEname(String ename){
    }
}
