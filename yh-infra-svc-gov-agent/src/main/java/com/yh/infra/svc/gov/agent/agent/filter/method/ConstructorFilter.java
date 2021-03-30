package com.yh.infra.svc.gov.agent.agent.filter.method;

import static net.bytebuddy.matcher.ElementMatchers.*;

import com.yh.infra.svc.gov.agent.agent.filter.Filter;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author lin-pc
 */
public class ConstructorFilter implements Filter<MethodDescription> {

    @Override
    public ElementMatcher.Junction<MethodDescription> doFilter(ElementMatcher.Junction<MethodDescription> methodJunction) {
        methodJunction = methodJunction == null ? isConstructor() : methodJunction.or(isConstructor());
        return methodJunction;
    }
}