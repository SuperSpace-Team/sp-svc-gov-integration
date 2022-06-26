package com.sp.infra.svc.gov.agent.agent.filter.method;

import static net.bytebuddy.matcher.ElementMatchers.isToString;

import com.sp.infra.svc.gov.agent.agent.filter.Filter;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author lin-pc
 */
public class ToStringFilter implements Filter<MethodDescription> {

    @Override
    public ElementMatcher.Junction<MethodDescription> doFilter(ElementMatcher.Junction<MethodDescription> methodJunction) {
        methodJunction = methodJunction == null ? isToString() : methodJunction.or(isToString());
        return methodJunction;
    }
}
