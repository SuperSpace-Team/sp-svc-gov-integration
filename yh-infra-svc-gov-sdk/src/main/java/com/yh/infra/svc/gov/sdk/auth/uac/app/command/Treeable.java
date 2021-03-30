package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.io.Serializable;

/**
 * 可被树化的接口，实现这个接口就可以使用TreeUtil生成树结构
 * 
 * @author 李舜
 *
 */
public interface Treeable {
	Serializable getId();//节点id
	String getText();//节点文字
	Serializable getParentId();//父节点id
	boolean isOpen();
	boolean isChecked(); 
	String getSrc();   //设置该节点的链接
}
