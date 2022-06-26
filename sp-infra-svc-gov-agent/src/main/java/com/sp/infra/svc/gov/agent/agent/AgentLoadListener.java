/**
 * 
 */
package com.sp.infra.svc.gov.agent.agent;

import java.util.logging.Logger;

import com.sp.infra.svc.gov.agent.constant.Constants;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * @author luchao 2019-03-29
 *
 */
public class AgentLoadListener implements AgentBuilder.Listener {
	public static final Logger logger = Logger.getLogger(AgentLoadListener.class.getName());

	@Override
	public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
		// 如果不加注释sonar过不了
	}

	@Override
	public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
		String typeName = typeDescription.getName();
		if (typeName.startsWith(Constants.PKG_NAME_YONGHUI_FULL)
				|| typeName.startsWith(Constants.PKG_NAME_YONGHUI_SHORT)
				|| typeName.startsWith(Constants.PKG_NAME_YONGHUI_JUMBO)) {
			logger.info("TRANSFORM : " + typeName);
		}
	}

	@Override
	public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
//		if (logger.isLoggable(Level.FINE)) {
//			String typeName = typeDescription.getName();
//			if (typeName.startsWith(Constants.PKG_YONGHUI))
//				logger.fine("IGNORE    : " + typeName);
//		}
	}

	@Override
	public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
		throwable.printStackTrace(); // NOSONAR
	}

	@Override
	public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//		if (logger.isLoggable(Level.FINE) && typeName.startsWith(Constants.PKG_YONGHUI)) {
//			logger.fine("COMPLETE  : " + typeName);
//		}
	}

}
