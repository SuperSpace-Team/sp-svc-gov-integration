/**
 * 
 */
package com.yh.infra.svc.gov.sdk.command;

import java.io.Serializable;

/**
 * @author luchao 2019-10-21
 *
 */

public class RefreshToken implements Serializable{

    private static final long serialVersionUID = -829459440927130571L;

    //返回状态(true: token正常刷新; false: 重新生成token)
    private boolean refreshFlag;
    
    private String token;
    
    private Long expireDate;
    
    public boolean isRefreshFlag(){
        return refreshFlag;
    }
    public void setRefreshFlag(boolean refreshFlag){
        this.refreshFlag = refreshFlag;
    }
    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }
    public Long getExpireDate(){
        return expireDate;
    }
    public void setExpireDate(Long expireDate){
        this.expireDate = expireDate;
    }
    
    
    
}
