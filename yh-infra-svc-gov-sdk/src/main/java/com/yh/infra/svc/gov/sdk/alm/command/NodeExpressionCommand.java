package com.yh.infra.svc.gov.sdk.alm.command;

import org.springframework.expression.Expression;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 5:49 下午
 */
public class NodeExpressionCommand {
	private int nodeCode;
	private Expression seqMatchExp;
	private Expression matchExp;
	private Expression inExp;
	private Expression outExp;
	private Expression keyExp;
	private Expression errorExp;
	private Expression srcExp;
	private Expression targetExp;
	
	public int getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(int nodeCode) {
		this.nodeCode = nodeCode;
	}
	public Expression getSeqMatchExp() {
		return seqMatchExp;
	}
	public void setSeqMatchExp(Expression seqMatchExp) {
		this.seqMatchExp = seqMatchExp;
	}
	public Expression getMatchExp() {
		return matchExp;
	}
	public void setMatchExp(Expression matchExp) {
		this.matchExp = matchExp;
	}
	public synchronized Expression getInExp() {
		return inExp;
	}
	public synchronized void setInExp(Expression inExp) {
		this.inExp = inExp;
	}
	public synchronized Expression getOutExp() {
		return outExp;
	}
	public synchronized void setOutExp(Expression outExp) {
		this.outExp = outExp;
	}
	public synchronized Expression getKeyExp() {
		return keyExp;
	}
	public synchronized void setKeyExp(Expression keyExp) {
		this.keyExp = keyExp;
	}
	public synchronized Expression getErrorExp() {
		return errorExp;
	}
	public synchronized void setErrorExp(Expression errorExp) {
		this.errorExp = errorExp;
	}
	public synchronized Expression getSrcExp() {
		return srcExp;
	}
	public synchronized void setSrcExp(Expression srcExp) {
		this.srcExp = srcExp;
	}
	public synchronized Expression getTargetExp() {
		return targetExp;
	}
	public synchronized void setTargetExp(Expression targetExp) {
		this.targetExp = targetExp;
	}
}
