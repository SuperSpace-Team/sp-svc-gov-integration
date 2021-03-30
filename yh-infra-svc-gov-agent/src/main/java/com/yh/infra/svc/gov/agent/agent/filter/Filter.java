package com.yh.infra.svc.gov.agent.agent.filter;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author lin-pc
 */
public interface Filter<T> {

    /**
     * 过滤
     *
     * @param methodJunction
     * @return
     */
    ElementMatcher.Junction<T> doFilter(ElementMatcher.Junction<T> methodJunction);
}
