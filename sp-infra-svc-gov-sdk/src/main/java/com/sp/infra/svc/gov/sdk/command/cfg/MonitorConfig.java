package com.sp.infra.svc.gov.sdk.command.cfg;

import java.util.ArrayList;
import java.util.List;

public class MonitorConfig {
    private List<BizFactor> factors = new ArrayList<BizFactor>();
	private List<Node> nodes = new ArrayList<Node>();
	private List<TransformNode> transformNodes = new ArrayList<TransformNode>();
    private List<LogTarget> logTargets = new ArrayList<LogTarget>();


    public List<BizFactor> getFactors() {
        return factors;
    }

    public void setFactors(List<BizFactor> factors) {
        this.factors = factors;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<TransformNode> getTransformNodes() {
        return transformNodes;
    }

    public void setTransformNodes(List<TransformNode> transformNodes) {
        this.transformNodes = transformNodes;
    }

    public List<LogTarget> getLogTargets() {
        return logTargets;
    }

    public void setLogTargets(List<LogTarget> logTargets) {
        this.logTargets = logTargets;
    }
}
