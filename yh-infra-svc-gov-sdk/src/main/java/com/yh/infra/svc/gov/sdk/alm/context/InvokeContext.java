package com.yh.infra.svc.gov.sdk.alm.context;

import com.yh.infra.svc.gov.sdk.alm.command.MonitorLogMessage;
import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.command.cfg.TransformNode;
import com.yh.infra.svc.gov.sdk.util.MethodWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 8:22 下午
 */
public class InvokeContext {
	
	private Class<?> targetClass;
	private MethodWrapper method;
	private Object[] args;
	private Object returned;
	private Throwable throwable;
	
	private int version; // 记录本IC使用的version
	
	private String transId;
	private int seq;  // 本次调用的seq
	private int lastSeq;  // 本次调用中包含的所有子调用的最大的seq，用于顺序计算下一个节点的seq
	
	// 入口的string；
	private String clzEntry;
	// 方法定义的入口的string；
	private String defClzEntry;
	

	// 保存入参的引用。
	private Map<String, Object> inputData = new HashMap<>();

	// 每个biz 中 ， 使用的node。
	private Map<Integer, Node> bizNodeMap = new HashMap<>();
	// 每个biz 中 ， 监控节点生成的入参日志。
	// key是node code， 由于node code 全系统唯一， 所以多个biz的node也是不会重复的。
	private Map<Integer, List<String>> nodeInLogMap = new HashMap<>();

	// 每个biz 中 ， 使用的t node。
	private Map<Integer, TransformNode> bizTransMap = new HashMap<>();

	// 每个biz 中 ，生成的log command
	private List<MonitorLogMessage> bizLogCmdList = new ArrayList<>();

	/**
	 * 是否需要处理本次拦截。
	 */
	private boolean deal = false;
	
	
	public InvokeContext(int ver) {
		this.version = ver;
	}
	
	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public MethodWrapper getMethod() {
		return method;
	}

	public void setMethod(MethodWrapper method) {
		this.method = method;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Object getReturned() {
		return returned;
	}

	public void setReturned(Object returned) {
		this.returned = returned;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public List<MonitorLogMessage> getBizLogCmdList() {
		return bizLogCmdList;
	}

	public Map<Integer, List<String>> getNodeInLogMap() {
		return nodeInLogMap;
	}

	public Map<Integer, TransformNode> getBizTransMap() {
		return bizTransMap;
	}

	public Map<Integer, Node> getBizNodeMap() {
		return bizNodeMap;
	}

	public String getClzEntry() {
		return clzEntry;
	}

	public Map<String, Object> getInputData() {
		return inputData;
	}

	public void setClzEntry(String clzEntry) {
		this.clzEntry = clzEntry;
	}

	public boolean isDeal() {
		return deal;
	}

	public void setDeal(boolean deal) {
		this.deal = deal;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getLastSeq() {
		return lastSeq;
	}

	public void setLastSeq(int lastSeq) {
		this.lastSeq = lastSeq;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDefClzEntry() {
		return defClzEntry;
	}

	public void setDefClzEntry(String defClzEntry) {
		this.defClzEntry = defClzEntry;
	}

}
