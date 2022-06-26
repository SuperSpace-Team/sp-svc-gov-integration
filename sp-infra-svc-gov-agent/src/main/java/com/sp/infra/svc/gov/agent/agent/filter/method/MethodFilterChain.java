package com.sp.infra.svc.gov.agent.agent.filter.method;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.sp.infra.svc.gov.agent.agent.filter.Filter;

/**
 * 忽略掉一些 不监控的方法。
 * 
 * @author lin-pc
 */
public class MethodFilterChain implements Filter<MethodDescription> {
    private final Set<Filter<MethodDescription>> filters = new HashSet<>(Arrays.asList(new ConstructorFilter(),new ObjectFilter(),new GetterFilter(),new SetterFilter(),new ToStringFilter()));

    @Override
    public ElementMatcher.Junction<MethodDescription> doFilter(ElementMatcher.Junction<MethodDescription> methodJunction) {
        ElementMatcher.Junction<MethodDescription> junction = null;
        for (Filter<MethodDescription> filter : filters) {
            junction = filter.doFilter(junction);
        }
        return junction;
    }

    public ElementMatcher.Junction<MethodDescription> doFilter() {
        return this.doFilter(null);
    }
}
