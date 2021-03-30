/**
 * 
 */
package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;
import java.util.List;

/**
 * @author LSH10022
 *
 */
public class QueryUserLoginLogResult  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    private boolean isSuccess;
    
    private List<UserLoginLogCommand> list;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<UserLoginLogCommand> getList() {
		return list;
	}

	public void setList(List<UserLoginLogCommand> list) {
		this.list = list;
	}
    
}
