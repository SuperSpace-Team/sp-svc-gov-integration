/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yh.infra.svc.gov.sdk.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Converts from a String any JDK-standard Date implementation.
 *
 * <p>Support Date format  including 
 * "yyyy-MM-dd","yyyy-MM-dd HH:mm","yyyy-MM-dd HH:mm:ss",
 * "yyyy/MM/dd","yyyy/MM/dd HH:mm","yyyy/MM/dd HH:mm:ss"
 * </p>
 * 
 *
 * @author Keith Donald
 * @author MSH8244
 * @since 3.0
 */
public class StringToDateConverterFactory implements ConverterFactory<String, Date> {

    public <T extends Date> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToDate<T>();
    }

    private static final class StringToDate<T extends Date> implements Converter<String, T> {


        private final Object[][] patterns = {
                               {Pattern.compile("^\\d+-\\d+-\\d+$"), "yyyy-MM-dd"},
                               {Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+$"), "yyyy-MM-dd HH:mm"},
                               {Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+:\\d+$"), "yyyy-MM-dd HH:mm:ss"},
                               {Pattern.compile("^\\d+/\\d+/\\d+$"), "yyyy/MM/dd"},
                               {Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+$"), "yyyy/MM/dd HH:mm"},
                               {Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+:\\d+$"), "yyyy/MM/dd HH:mm:ss"}
                       };
        
        public StringToDate() {
        }

        @SuppressWarnings("unchecked")
        public T convert(String source) {
            
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            String val = source.toString().trim();
            
            //确定日期格式
            String format = null;
            Pattern p;
            for (Object[] item : patterns) {
                p = (Pattern) item[0];
                if (p.matcher(val).matches()) {
                    format = item[1].toString();
                    break;
                }
            }
            //日期格式未定义
            if (format == null) {
                throw new IllegalArgumentException("ObjectToDateConvert failed, unsupport format: " + source);
            }

            // 日期转换
            try {
                return (T) new SimpleDateFormat(format).parse(val);
            } catch (ParseException e) {
                throw new RuntimeException("ObjectToDateConvert failed, bad value: " + source, e);
            }
        }
    }

}
