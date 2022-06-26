package com.sp.infra.svc.gov.agent.agent.filter.method;

import static net.bytebuddy.matcher.ElementMatchers.isSetter;

import com.sp.infra.svc.gov.agent.agent.filter.Filter;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class SetterFilter implements Filter<MethodDescription> {

    @Override
    public ElementMatcher.Junction<MethodDescription> doFilter(ElementMatcher.Junction<MethodDescription> methodJunction) {
        methodJunction = methodJunction == null ? isSetter() : methodJunction.or(isSetter());
        return methodJunction;
    }
}
