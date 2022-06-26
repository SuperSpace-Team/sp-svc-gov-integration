package com.sp.infra.svc.gov.agent.agent;

import java.lang.instrument.Instrumentation;
import java.util.Properties;
import java.util.logging.Logger;

import com.sp.infra.svc.gov.agent.util.GitPropertyUtil;

/**
 * @author luchao
 * @version 1.0
 * @description javaagent entry
 * @date 2019/2/22 13:41
 **/
public class Agent {
	private static final Logger logger = Logger.getLogger(Agent.class.getName());

    /**
     * for sonar
     */
	private Agent() {
        throw new IllegalStateException("Utility class");
    }

    public static void premain(String args, Instrumentation instrumentation) {
    	
    	Properties p = GitPropertyUtil.loadProps();
    	String agentVersion = p.getProperty("git.build.version");
    	String v = p.getProperty("git.commit.id");
    	logger.info("begin to register classloader into bean registry in Agent.premain(). git ver:" + v + " .  app ver:" + agentVersion);
    	
    	// 注册 自定义的 agent installer。 
        AgentBeanRegistry.register(new AgentInstallProcessor(instrumentation, agentVersion));
    }
}
