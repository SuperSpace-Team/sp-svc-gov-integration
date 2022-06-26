/**
 * 
 */
package com.sp.infra.svc.gov.agent.agent.filter.clz;

import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.bytebuddy.agent.builder.AgentBuilder.RawMatcher;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.utility.JavaModule;

/**
 * 排除掉 AOP 类， 因为这些类是对业务方法的拦截， 参数实际是业务方法的参数。
 * 拦截AOP类会造成 不可知的 后果。 比如 触发lazy load。
 * 
 * @author luchao  2019-05-20
 *
 */
public class AopMatcher implements RawMatcher {
	public static final Logger logger = Logger.getLogger(AopMatcher.class.getName());
	
	
	
	@Override
	public boolean matches(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain) {
		AnnotationList al = typeDescription.getDeclaredAnnotations();
		for (AnnotationDescription ad : al) {
			if (ad.getAnnotationType().getTypeName().equals("org.aspectj.lang.annotation.Aspect")) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Ignored AOP type is " + typeDescription.getTypeName());
				}
				return false;
			}
		}
		return true;
	}

}
