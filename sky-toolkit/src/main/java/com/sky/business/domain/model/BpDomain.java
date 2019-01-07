package com.sky.business.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpDomain")
@Table(name="BP_DOMAIN")
public class BpDomain {
	
    private String cname;
    private String dataLen;
    private String dataType;
    @Id
    @GeneratedValue(generator="UUID")
    private String ename;
    public String getCname(){        return this.cname;
    }
    
    public void setCname(String cname){        this.cname = cname;
    }
    
    public String getDataLen(){        return this.dataLen;
    }
    
    public void setDataLen(String dataLen){        this.dataLen = dataLen;
    }
    
    public String getDataType(){        return this.dataType;
    }
    
    public void setDataType(String dataType){        this.dataType = dataType;
    }
    
    public String getEname(){        return this.ename;
    }
    
    public void setEname(String ename){        this.ename = ename;
    }
}

