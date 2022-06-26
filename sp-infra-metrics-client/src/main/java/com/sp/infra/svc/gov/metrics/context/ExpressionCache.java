package com.sp.infra.svc.gov.metrics.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author: luchao
 * @date: Created in 4/16/21 8:13 PM
 */
public class ExpressionCache {
    private static final Logger logger = LoggerFactory.getLogger(ExpressionCache.class);
    private static ExpressionCache instance = new ExpressionCache();

    private Map<String, Expression> parseCache = new ConcurrentHashMap<>();
    private ReentrantReadWriteLock locker = new ReentrantReadWriteLock();

    public ExpressionCache() {
    }

    public static ExpressionCache getInstance(){
        return instance;
    }

    /**
     * 获取一个表达式Cache,若不存在则创建
     * @param expStr
     * @return
     */
    public Expression getExpression(String expStr){
        Expression expression = getCachedExpression(expStr);
        if(expression != null){
            return expression;
        }

        ReentrantReadWriteLock.WriteLock writeLock = locker.writeLock();
        writeLock.lock();

        try {
            //重新取一次。确保前面取之后，没有被更新过
            expression = parseCache.get(expStr);
            if(expression != null){
                writeLock.unlock();
                return expression;
            }

            SpelExpressionParser parser = new SpelExpressionParser();
            expression = parser.parseExpression(expStr);
            parseCache.put(expStr, expression);
        }catch (Exception e){
            logger.error("System error for expression {}", expStr, e);
        }

        writeLock.unlock();
        return expression;
    }

    /**
     * 获取缓存的表达式
     * @param expStr
     * @return
     */
    private Expression getCachedExpression(String expStr) {
        Expression ret = null;

        ReentrantReadWriteLock.ReadLock readLock = locker.readLock();
        readLock.lock();
        ret = parseCache.get(expStr);
        readLock.unlock();
        return ret;
    }
}
