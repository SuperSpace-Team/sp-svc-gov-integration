package com.yh.infra.svc.gov.sdk.task.support.appender;

import com.yh.infra.svc.gov.sdk.util.TaskHelper;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

/**
 * @title 自定义Appender for log4j2
 * @author MSH8244
 *
 */
@Plugin(name = "TaskAppender", category = "Core", elementType = "appender", printObject = true)
public class TaskAppenderForLog4j2 extends AbstractAppender  {

    /**
     * UID
     */
    private static final long serialVersionUID = -6079129454339987969L;

    /**
     * @title 构造函数
     * @param name
     * @param filter
     * @param layout
     */
    protected TaskAppenderForLog4j2(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }
    protected TaskAppenderForLog4j2(String name, Filter filter, Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    
    @Override
    public void append(LogEvent event) {
        String level = "INFO"; //默认info
        String message = null;
        if (event.getLevel() != null) {
            level = event.getLevel().toString();
        }
        if (event.getMessage() != null) {
            message = event.getMessage().toString();
        }
        
        Throwable throwable = event.getThrown();
        
        try {
            TaskHelper.appendLog(level, message, throwable);
        } catch (Exception ex) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException(ex);
            }
        }
    }

    @Override
    public void start() {
//        System.out.println("------------------taskAppender-log4j2-start方法被调用");
        super.start();
    }

    @Override
    public void stop() {
//        System.out.println("------------------taskAppender-log4j2-stop方法被调用");
        super.stop();
    }

    
    // 下面这个方法可以接收配置文件中的参数信息
    @PluginFactory
    public static TaskAppenderForLog4j2 createAppender(@PluginAttribute("name") String name,
            @PluginElement("Filter") final Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TaskAppenderForLog4j2(name, filter, layout, ignoreExceptions);
    }


}
