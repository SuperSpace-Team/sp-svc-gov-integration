package com.yh.infra.svc.gov.sdk.auth.uac.app.util;

import cn.hutool.core.exceptions.ValidateException;
import com.yh.infra.svc.gov.sdk.auth.uac.app.util.ValidateItem.ValidType;

public class ValidateUtil {

	
	private final static String error18KeyStartWith="general.validate.";
	
	
	public static void valid(String nameKey,Object value,ValidateItem... vis){
		
		for(ValidateItem valid:vis){
			ValidateItem.ValidType vt=valid.valid(value);
			if(vt!=null){
				
				if(vt==ValidateItem.ValidType.range){
					String[] params=valid.getParameter().split("-");
					throw new ValidateException(nameKey,error18KeyStartWith+ValidType.range.toString(),params);
				}
				else if(vt==ValidateItem.ValidType.notnull){
					
					throw new ValidateException(nameKey,error18KeyStartWith+ValidType.notnull.toString(),null);
				}
				else if(vt==ValidateItem.ValidType.date){
					
					throw new ValidateException(nameKey,error18KeyStartWith+ValidType.date.toString(),null);
				}
				else {
					throw new ValidateException(nameKey,error18KeyStartWith+ValidType.reg.toString(),null);
				}
					
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ValidateUtil.valid("member.loginEmail", "sdfsdf.@a.com", new ValidateItem(ValidateItem.ValidType.email));
	}

}
