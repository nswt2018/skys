package com.sky.business.commonfield.model;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpCommonfield")
@Table(name="BP_COMMONFIELD")
public class BpCommonfield {
    @Id
    private String colCode;









    }
    
    public void setColCode(String colCode){
    }
    
    public String getColName(){
    }
    
    public void setColName(String colName){
    }
    
    public String getDataLen(){
    }
    
    public void setDataLen(String dataLen){
    }
    
    public String getDataType(){
    }
    
    public void setDataType(String dataType){
    }
    
    public String getJoinColCode(){
    }
    
    public void setJoinColCode(String joinColCode){
    }
    
    public String getJoinTabCode(){
    }
    
    public void setJoinTabCode(String joinTabCode){
    }
    
    public String getJoinWhere(){
    }
    
    public void setJoinWhere(String joinWhere){
    }
    
    public String getUiType(){
    }
    
    public void setUiType(String uiType){
    }
    
    public String getValBetween(){
    }
    
    public void setValBetween(String valBetween){
    }
}
