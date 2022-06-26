package com.sp.infra.svc.gov.agent.agent.filter.custom;

import java.util.List;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**普通方法过滤，过滤掉指定的类的方法。
 * 
 * 
 * 
 * @author luchao  2019-05-15
 *
 */
public class CustomMethodFilter {
    private List<String> methodList;

    public CustomMethodFilter(List<String> methodList) {
        this.methodList = methodList;
    }

    public ElementMatcher.Junction<MethodDescription> doFilter() {
    	ElementMatcher.Junction<MethodDescription> ret = null;
        for (String method : methodList) {
        	ret = ret == null ? ElementMatchers.<MethodDescription>nameStartsWith(method)
                    : ret.or(ElementMatchers.<MethodDescription>nameStartsWith(method));
        }

        return ret;
    }
}
