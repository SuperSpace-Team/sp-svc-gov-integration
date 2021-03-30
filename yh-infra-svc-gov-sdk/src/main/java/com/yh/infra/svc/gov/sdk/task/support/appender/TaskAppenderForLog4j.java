package com.yh.infra.svc.gov.sdk.task.support.appender;

import com.yh.infra.svc.gov.sdk.util.TaskHelper;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class TaskAppenderForLog4j extends AppenderSkeleton {


    /**
     * Subclasses of <code>AppenderSkeleton</code> should implement this method to perform actual
     * logging. See also {@link #doAppend AppenderSkeleton.doAppend} method.
     *
     * @param event
     * @since 0.9.0
     */
    @Override
    protected void append(LoggingEvent event) {
        String level = "INFO"; //默认info
        String message = null;
        Throwable throwable = null;
        
        if (event.getLevel() != null) {
            level = event.getLevel().toString();
        }
        if (null != event.getMessage()) {
            message = event.getMessage().toString();
        }
        if (null != event.getThrowableInformation()) {
            throwable = event.getThrowableInformation().getThrowable();
        }
        
        TaskHelper.appendLog(level, message, throwable);

    }


    /**
     * Release any resources allocated within the appender such as file handles, network
     * connections, etc.
     *
     * <p>
     * It is a programming error to append to a closed appender.
     *
     * @since 0.8.4
     */
    @Override
    public void close() {
        closed = true; // 释放资源
    }

    /**
     * Configurators call this method to determine if the appender requires a layout. If this method
     * returns <code>true</code>, meaning that layout is required, then the configurator will
     * configure an layout using the configuration information at its disposal. If this method
     * returns <code>false</code>, meaning that a layout is not required, then layout configuration
     * will be skipped even if there is available layout configuration information at the disposal
     * of the configurator..
     *
     * <p>
     * In the rather exceptional case, where the appender implementation admits a layout but can
     * also work without it, then the appender should return <code>true</code>.
     *
     * @since 0.8.4
     */
    @Override
    public boolean requiresLayout() {
        return false;
    }



}
