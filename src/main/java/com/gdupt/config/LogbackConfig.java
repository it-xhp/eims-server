package com.gdupt.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * lobgack配置类
 */
public class LogbackConfig extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent iLoggingEvent) {
        Level level = iLoggingEvent.getLevel();
        switch (level.toInt()) {
            //ERROR等级为红色
            case Level.ERROR_INT:
                return ANSIConstants.RED_FG;
            //WARN等级为黄色
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;
            //INFO等级为蓝色
            case Level.INFO_INT:
                return ANSIConstants.BLUE_FG;
            //DEBUG等级为绿色
            case Level.DEBUG_INT:
                return ANSIConstants.GREEN_FG;
            //其他为默认颜色
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

}