package com.yh.infra.svc.gov.agent.agent;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author luchao
 * @description
 * @create 2019年01月29日10:36
 */
public abstract class AbstractJunction<V> implements ElementMatcher.Junction<V> {
    @Override
    public <U extends V> Junction<U> and(ElementMatcher<? super U> other) {
        return new Conjunction(this, other);
    }

    @Override
    public <U extends V> Junction<U> or(ElementMatcher<? super U> other) {
        return new Disjunction(this, other);
    }
}
