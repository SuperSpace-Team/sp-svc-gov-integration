package com.sp.infra.svc.gov.agent.agent.filter.method;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

import com.sp.infra.svc.gov.agent.agent.filter.Filter;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author lin-pc
 */
public class ObjectFilter implements Filter<MethodDescription> {

    @Override
    public ElementMatcher.Junction<MethodDescription> doFilter(ElementMatcher.Junction methodJunction) {
        methodJunction = methodJunction == null ? isDeclaredBy(Object.class) : methodJunction.or(isDeclaredBy(Object.class));
        return methodJunction;
    }
}
