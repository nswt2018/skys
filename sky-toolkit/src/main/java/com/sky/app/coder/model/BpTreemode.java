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








    }
    public void setNodCode(String nodCode){

    }
    public String getNodName(){

    }
    public void setNodName(String nodName){

    }
    public String getUpNodCode(){

    }
    public void setUpNodCode(String upNodCode){

    }
    public String getUpNodName(){

    }
    public void setUpNodName(String upNodName){

    }
    public String getTranCode(){

    }
    public void setTranCode(String tranCode){

    }
    public String getShowCond(){

    }
    public void setShowCond(String showCond){

    }
    public String getShowParam(){

    }
    public void setShowParam(String showParam){

    }

}
