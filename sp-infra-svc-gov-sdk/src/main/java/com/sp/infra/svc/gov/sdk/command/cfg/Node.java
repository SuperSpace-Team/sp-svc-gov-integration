/**
 * 
 */
package com.sp.infra.svc.gov.sdk.command.cfg;

import com.sp.infra.svc.gov.sdk.constant.SdkCommonConstant;

/**
 * @author luchao 2018-12-14
 *
 */
public class Node {
	
	private Integer code;
	private Integer bizCode;
	private Integer entryCode;
	private Integer seq;
	private String seqMatchExp;
	private String matchExp;
	private String keyExp;
	private String keyTransAlias;
	private String inLogExp;
	private String outLogExp;
	private String errorLogExp;
	private Integer threshold;
	private int type = SdkCommonConstant.ALM_NODE_TYPE_NORMAL; // 1: 默认的监控节点。   2：自定义监控节点。
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getBizCode() {
		return bizCode;
	}
	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}
	public Integer getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(Integer entryCode) {
		this.entryCode = entryCode;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSeqMatchExp() {
		return seqMatchExp;
	}
	public void setSeqMatchExp(String seqMatchExp) {
		this.seqMatchExp = seqMatchExp;
	}
	public String getMatchExp() {
		return matchExp;
	}
	public void setMatchExp(String matchExp) {
		this.matchExp = matchExp;
	}
	public String getKeyExp() {
		return keyExp;
	}
	public void setKeyExp(String keyExp) {
		this.keyExp = keyExp;
	}
	public String getKeyTransAlias() {
		return keyTransAlias;
	}
	public void setKeyTransAlias(String keyTransAlias) {
		this.keyTransAlias = keyTransAlias;
	}
	public String getInLogExp() {
		return inLogExp;
	}
	public void setInLogExp(String inLogExp) {
		this.inLogExp = inLogExp;
	}
	public String getOutLogExp() {
		return outLogExp;
	}
	public void setOutLogExp(String outLogExp) {
		this.outLogExp = outLogExp;
	}
	public String getErrorLogExp() {
		return errorLogExp;
	}
	public void setErrorLogExp(String errorLogExp) {
		this.errorLogExp = errorLogExp;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Node [code=" + code + ", bizCode=" + bizCode + ", entryCode=" + entryCode + ", seq=" + seq + ", seqMatchExp=" + seqMatchExp + ", matchExp="
				+ matchExp + ", keyExp=" + keyExp + ", keyTransAlias=" + keyTransAlias + ", inLogExp=" + inLogExp + ", outLogExp=" + outLogExp
				+ ", errorLogExp=" + errorLogExp + ", threshold=" + threshold + ", type=" + type + "]";
	}

	
}
