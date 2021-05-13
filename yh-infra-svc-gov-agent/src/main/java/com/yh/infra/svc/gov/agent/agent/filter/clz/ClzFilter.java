package com.yh.infra.svc.gov.agent.agent.filter.clz;

import static net.bytebuddy.matcher.ElementMatchers.nameMatches;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * 用于指定要监控的包。同时排除掉不能监控的包。  
 * 
 * 
 * 
 * @author luchao  2019-05-10
 *
 */
public class ClzFilter {
    private final String className;
    
    public ClzFilter(String className) {
        this.className = className;
    }

    public ElementMatcher.Junction<NamedElement> doFilter() {
    	return named(className)
    			.and(not(
    	        		nameStartsWith("com.yh.infra.svc.gov.agent")
    					.or(nameStartsWith("com.yh.infra.svc.gov.dependencies"))
    					.or(nameStartsWith("com.yh.infra.svc.gov.sdk"))
//    					.or(nameMatches("com.yh.eca.sac.remote.manager.CodeManagerRemoteApi"))
//    					.or(nameMatches("com.yh.eca.sac.remote.manager.PkManagerRemoteApi"))
    					.or(nameStartsWith("com.yh.infra.ecs.log.remote.manager"))
    					.or(nameStartsWith("com.yh.infra.platform.notice.remote.manager"))
    					.or(nameStartsWith("com.yh.infra.platform.baseservice.sac"))
    					.or(nameStartsWith("com.yh.infra.platform.baseservice.notice"))
    					.or(nameStartsWith("com.yh.infra.scm.baseservice.task"))
    	        		));
    }
}
