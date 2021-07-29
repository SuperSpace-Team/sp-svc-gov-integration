package com.yh.infra.svc.gov.sdk.alm.util;

import com.yh.infra.svc.gov.sdk.alm.command.BizEntryRule;
import com.yh.infra.svc.gov.sdk.alm.command.NodeExpressionCommand;
import com.yh.infra.svc.gov.sdk.command.cfg.*;
import com.yh.infra.svc.gov.sdk.constant.SdkCommonConstant;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author luchao
 * @date 2021/4/25 5:53 下午
 */
public class VersionDataBuilder {
    private static final Logger logger = LoggerFactory.getLogger(VersionDataBuilder.class.getName());

    /**
     * 构建 自定义node 
     *
     * @param cfg
     * @return
     */
    public static Map<Integer, Node> buildCustomizeNodeMap(AppConfig cfg) {
        List<Node> nodeList = cfg.getMonitor().getNodes();

        Map<Integer, Node> nodeMap = new HashMap<>();
        for (Node n : nodeList) {
            if (n.getType() == SdkCommonConstant.ALM_NODE_TYPE_CUSTOMIZE) {
            	nodeMap.put(n.getCode(), n);
            }
        }
        return nodeMap;
    }
    /**
     * 构建 自定义tnode 
     *
     * @param cfg
     * @return
     */
    public static Map<Integer, TransformNode> buildCustomizeTnodeMap(AppConfig cfg) {
        List<TransformNode> nodeList = cfg.getMonitor().getTransformNodes();

        Map<Integer, TransformNode> nodeMap = new HashMap<>();
        for (TransformNode n : nodeList) {
            if (n.getType() == SdkCommonConstant.ALM_NODE_TYPE_CUSTOMIZE) {
            	nodeMap.put(n.getCode(), n);
            }
        }
        return nodeMap;
    }

    /**
     * 构建 entry缓存
     *
     * @param cfg
     * @return
     */
    public static Map<String, Map<Integer, BizEntryRule>> buildEntryMap(AppConfig cfg) {
        List<Entry> entryList = cfg.getEntries();
        List<Node> nodeList = cfg.getMonitor().getNodes();
        List<TransformNode> tNodeList = cfg.getMonitor().getTransformNodes();
        List<BizFactor> bizList = cfg.getMonitor().getFactors();

        Map<String, Map<Integer, BizEntryRule>> entryMap = new HashMap<String, Map<Integer, BizEntryRule>>();
        for (Entry entry : entryList) {
            Map<Integer, BizEntryRule> bizMap = new HashMap<Integer, BizEntryRule>();
            String key = entry.getClassName() + "." + entry.getMethodName() + "(" + entry.getInputParamType() + ")";

            for (BizFactor factor : bizList) {
                BizEntryRule ber = new BizEntryRule();
                boolean found = false;
                for (Node n : nodeList) {
                    // 自定义节点entryCode为空
                    if (Objects.equals(n.getEntryCode(), entry.getCode()) && Objects.equals(n.getBizCode(), factor.getCode())) {
                        found = true;
                        ber.getNodeList().add(n);
                    }
                }
                for (TransformNode n : tNodeList) {
                    // 自定义转换节点entryCode为空
                    if (Objects.equals(n.getEntryCode(), entry.getCode()) && Objects.equals(n.getBizCode(), factor.getCode())) {
                        found = true;
                        ber.getTransformNodeList().add(n);
                    }
                }
                if (found) {
                    bizMap.put(factor.getCode(), ber);
                }
            }
            if (! bizMap.isEmpty())
            	entryMap.put(key, bizMap);
            else {
            	logger.warn("no node found for entry: " + key);
            }
        }
        return entryMap;
    }

    /**
     * 构建spring expression缓存
     *
     * @param cfg
     * @return
     */
    public static Map<String, NodeExpressionCommand> buildExpressionMap(AppConfig cfg) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Map<String, NodeExpressionCommand> expCache = new HashMap<String, NodeExpressionCommand>();
        List<Entry> entryList = cfg.getEntries();
        List<Node> nodeList = cfg.getMonitor().getNodes();
        List<TransformNode> tNodeList = cfg.getMonitor().getTransformNodes();
        List<BizFactor> bizList = cfg.getMonitor().getFactors();
        for (Entry entry : entryList) {
            for (BizFactor factor : bizList) {
                for (Node n : nodeList) {
                    // 自定义节点entryCode为空
                    if (Objects.equals(n.getEntryCode(), entry.getCode()) && Objects.equals(n.getBizCode(), factor.getCode())) {
                        NodeExpressionCommand expCmd = new NodeExpressionCommand();
                        expCmd.setNodeCode(n.getCode());
                        if (StringUtils.isNotEmpty(n.getSeqMatchExp())) {
                            Expression expression = expressionParser.parseExpression(n.getSeqMatchExp());
                            expCmd.setSeqMatchExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getMatchExp())) {
                            Expression expression = expressionParser.parseExpression(n.getMatchExp());
                            expCmd.setMatchExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getInLogExp())) {
                            Expression expression = expressionParser.parseExpression(n.getInLogExp());
                            expCmd.setInExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getOutLogExp())) {
                            Expression expression = expressionParser.parseExpression(n.getOutLogExp());
                            expCmd.setOutExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getKeyExp())) {
                            Expression expression = expressionParser.parseExpression(n.getKeyExp());
                            expCmd.setKeyExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getErrorLogExp())) {
                            Expression expression = expressionParser.parseExpression(n.getErrorLogExp());
                            expCmd.setErrorExp(expression);
                        }
                        expCache.put(SdkCommonConstant.PG_NODE_TYPE_MONITOR + "-" + n.getCode(), expCmd);
                    }
                }
                for (TransformNode n : tNodeList) {
                    // 自定义节点entryCode为空
                    if (Objects.equals(n.getEntryCode(), entry.getCode()) && Objects.equals(n.getBizCode(), factor.getCode())) {
                        NodeExpressionCommand expCmd = new NodeExpressionCommand();
                        expCmd.setNodeCode(n.getCode());
                        if (StringUtils.isNotEmpty(n.getMatchExp())) {
                            Expression expression = expressionParser.parseExpression(n.getMatchExp());
                            expCmd.setMatchExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getSourceKeyExp())) {
                            Expression expression = expressionParser.parseExpression(n.getSourceKeyExp());
                            expCmd.setSrcExp(expression);
                        }
                        if (StringUtils.isNotEmpty(n.getTargetKeyExp())) {
                            Expression expression = expressionParser.parseExpression(n.getTargetKeyExp());
                            expCmd.setTargetExp(expression);
                        }
                        expCache.put(SdkCommonConstant.PG_NODE_TYPE_TRANSFORM + "-" + n.getCode(), expCmd);
                        break;
                    }
                }
            }
        }
        return expCache;
    }

    /**
     * 构建数据缓存
     *
     * @param cfg
     * @return
     */
    public static Map<String, Object> buildDataCache(AppConfig cfg) {
        Map<String, Object> dataCache = new HashMap<String, Object>();

        // 从common数据构建缓存
        Map<String, String> common = new HashMap<String, String>();
        List<CommonConfig> cfgList = cfg.getCommonCfgs();
        for (CommonConfig cc : cfgList) {
            common.put(cc.getCfgKey(), cc.getCfgValue());
        }
        dataCache.put("COMMON_CFG", common);

        // 构建work mode的缓存
        Map<Integer, Integer> workModeMap = new HashMap<Integer, Integer>();
        List<BizFactor> factorList = cfg.getMonitor().getFactors();
        for (BizFactor f : factorList) {
            workModeMap.put(f.getCode(), f.getClientMode());
        }
        dataCache.put("CLIENT_WORK_MODE", workModeMap);

        return dataCache;
    }

}
