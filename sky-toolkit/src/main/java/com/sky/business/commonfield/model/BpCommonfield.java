package com.sky.business.commonfield.model;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.Alias;

@Alias("bpCommonfield")
@Table(name="BP_COMMONFIELD")
public class BpCommonfield {
    @Id
    private String colCode;
    private String colName;
    private String dataLen;
    private String dataType;
    private String joinColCode;
    private String joinTabCode;
    private String joinWhere;
    private String uiType;
    private String valBetween;
    public String getColCode(){        return this.colCode;
    }
    
    public void setColCode(String colCode){        this.colCode = colCode;
    }
    
    public String getColName(){        return this.colName;
    }
    
    public void setColName(String colName){        this.colName = colName;
    }
    
    public String getDataLen(){        return this.dataLen;
    }
    
    public void setDataLen(String dataLen){        this.dataLen = dataLen;
    }
    
    public String getDataType(){        return this.dataType;
    }
    
    public void setDataType(String dataType){        this.dataType = dataType;
    }
    
    public String getJoinColCode(){        return this.joinColCode;
    }
    
    public void setJoinColCode(String joinColCode){        this.joinColCode = joinColCode;
    }
    
    public String getJoinTabCode(){        return this.joinTabCode;
    }
    
    public void setJoinTabCode(String joinTabCode){        this.joinTabCode = joinTabCode;
    }
    
    public String getJoinWhere(){        return this.joinWhere;
    }
    
    public void setJoinWhere(String joinWhere){        this.joinWhere = joinWhere;
    }
    
    public String getUiType(){        return this.uiType;
    }
    
    public void setUiType(String uiType){        this.uiType = uiType;
    }
    
    public String getValBetween(){        return this.valBetween;
    }
    
    public void setValBetween(String valBetween){        this.valBetween = valBetween;
    }
}

