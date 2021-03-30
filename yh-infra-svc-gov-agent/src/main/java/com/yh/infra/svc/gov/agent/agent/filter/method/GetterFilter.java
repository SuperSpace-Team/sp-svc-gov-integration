package com.yh.infra.svc.gov.agent.agent.filter.method;

import static net.bytebuddy.matcher.ElementMatchers.isGetter;

import com.yh.infra.svc.gov.agent.agent.filter.Filter;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author lin-pc
 */
public class GetterFilter implements Filter<MethodDescription> {

    @Override
    public ElementMatcher.Junction<MethodDescription> doFilter(ElementMatcher.Junction<MethodDescription> methodJunction) {
        methodJunction = methodJunction == null ? isGetter() : methodJunction.or(isGetter());
        return methodJunction;
    }
}