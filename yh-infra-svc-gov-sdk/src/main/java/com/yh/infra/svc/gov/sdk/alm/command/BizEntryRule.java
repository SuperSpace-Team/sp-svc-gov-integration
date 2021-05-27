package com.yh.infra.svc.gov.sdk.alm.command;

import com.yh.infra.svc.gov.sdk.command.cfg.Node;
import com.yh.infra.svc.gov.sdk.command.cfg.TransformNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luchao
 * @date 2021/4/25 5:49 下午
 */
@Data
public class BizEntryRule {
    private List<Node> nodeList = new ArrayList<Node>();
    private List<TransformNode> transformNodeList = new ArrayList<TransformNode>();
}
