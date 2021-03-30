package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * 组织以及组织类型信息
 * @author 李光辉
 * @date 2015年3月16日 下午7:37:22
 * @since
 */
public class OperationUnitCommand implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8166888133354990053L;

	/** ID */
    private Long id;
    
    /** 组织编码 */
    private String code;

    /** 组织名称 */
    private String name;
    
    /** 组织类型名称 */
    private String ouTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOuTypeName() {
        return ouTypeName;
    }

    public void setOuTypeName(String ouTypeName) {
        this.ouTypeName = ouTypeName;
    }
    
}
