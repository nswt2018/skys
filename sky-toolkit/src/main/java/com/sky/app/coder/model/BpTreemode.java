package com.sky.app.coder.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

@Alias("BpTreemode")
@Table(name="bp_transaction")
public class BpTreemode {
    @Id
    @GeneratedValue(generator="UUID")
    private String nodCode;
    private String nodName;
    private String upNodCode;
    private String upNodName;
    private String tranCode;
    private String showCond;
    private String showParam;
    public String getNodCode(){
        return this.nodCode;
    }
    public void setNodCode(String nodCode){
        this.nodCode = nodCode;
    }
    public String getNodName(){
        return this.nodName;
    }
    public void setNodName(String nodName){
        this.nodName = nodName;
    }
    public String getUpNodCode(){
        return this.upNodCode;
    }
    public void setUpNodCode(String upNodCode){
        this.upNodCode = upNodCode;
    }
    public String getUpNodName(){
        return this.upNodName;
    }
    public void setUpNodName(String upNodName){
        this.upNodName = upNodName;
    }
    public String getTranCode(){
        return this.tranCode;
    }
    public void setTranCode(String tranCode){
        this.tranCode = tranCode;
    }
    public String getShowCond(){
        return this.showCond;
    }
    public void setShowCond(String showCond){
        this.showCond = showCond;
    }
    public String getShowParam(){
        return this.showParam;
    }
    public void setShowParam(String showParam){
        this.showParam = showParam;
    }

}
