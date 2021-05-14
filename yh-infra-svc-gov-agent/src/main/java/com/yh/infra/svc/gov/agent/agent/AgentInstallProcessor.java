package com.yh.infra.svc.gov.agent.agent;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.not;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import com.yh.infra.svc.gov.agent.agent.filter.clz.AopMatcher;
import com.yh.infra.svc.gov.agent.agent.filter.clz.ClzFilter;
import com.yh.infra.svc.gov.agent.agent.filter.custom.CustomMethodFilter;
import com.yh.infra.svc.gov.agent.agent.filter.method.MethodFilterChain;
import com.yh.infra.svc.gov.agent.util.StringUtils;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.utility.JavaModule;

/**
 * @author luchao
 * @version 1.0
 * @description
 * @date 2019/2/22 13:41
 **/
public class AgentInstallProcessor {
	private static final Logger logger = Logger.getLogger(AgentInstallProcessor.class.getName());

	private Instrumentation instrumentation;
	private final CopyOnWriteArrayList<ResettableClassFileTransformer> transformers;
	private final ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.of(false));
	private String agentVersion = null;
	
	public AgentInstallProcessor(Instrumentation instrumentation, String agentVersion) {
		this.instrumentation = instrumentation;
		transformers = new CopyOnWriteArrayList<ResettableClassFileTransformer>();
		this.agentVersion = agentVersion;
	}

	private synchronized void load(Map<String, List<String>> entryMap, int version) {
		logger.info("Begin to load. " + version + "  current agent version :" + agentVersion);  //NOSONAR
		
		for (Map.Entry<String,List<String>> entry : entryMap.entrySet()) {
			String className = entry.getKey();
			logger.info(StringUtils.format("setup transformer for entry {}, {}", className, entryMap.get(className)));
			AgentBuilder.Transformer transformer = buildTransformer(entry.getValue());

			AgentBuilder.Identified.Extendable javassist = new AgentBuilder.Default(byteBuddy)
					.with(new AgentLoadListener())
					.with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
					.disableClassFormatChanges()
					.type(new ClzFilter(className).doFilter())
					.and(new AopMatcher())
					.transform(transformer);

			ResettableClassFileTransformer resettable = javassist.installOn(this.instrumentation);
			transformers.add(resettable);
		}
		logger.info("Finished load. " + version);  //NOSONAR
	}

	public synchronized void reload(Map<String, List<String>> entryMap, int version) {
		if (this.transformers.size() > 0) {
			if (!this.unload()) {
				logger.severe("unload failed.");
			}
		}
		this.load(entryMap, version);
	}

	private synchronized boolean unload() {
		boolean reset = true;

		for (ResettableClassFileTransformer tf : transformers) {
			reset = tf.reset(this.instrumentation, AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);
			if (!reset) {
				logger.severe("reset failed.");
				break;
			}
		}

		this.transformers.clear();
		return reset;
	}

	/**
	 * 为全链路监控使用。 用于对指定方法生成transformer
	 * 
	 * @param methods
	 * @return
	 */
	private AgentBuilder.Transformer buildTransformer(final List<String> methods) {
		return new AgentBuilder.Transformer() {
			@Override
			public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule javaModule) {
				return builder.visit(Advice
							.to(MethodAdvice.class)
							.on(isMethod()
									.and(new CustomMethodFilter(methods).doFilter())
									.and(not(new MethodFilterChain().doFilter()))));
			}
		};
	}

	
}
