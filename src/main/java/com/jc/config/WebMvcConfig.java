/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jc.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration

public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * String转换器 String-->String.trim()
     *
     * @return
     */
    @Bean
    public Converter<String, String> stringTrimConvert() {
        return new Converter<String, String>() {
            @Override
            public String convert(String source) {
                return StringUtils.trim(source);
            }
        };
    }


    /**
     * 日期转换器 Date-->String
     *
     * @return
     */
    @Bean
    public Converter<Date, String> dateToString() {
        return new Converter<Date, String>() {
            @Override
            public String convert(Date date) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                return sdf.format(date);
            }
        };
    }


    /**
     * 日期转换器 String-->Date
     *
     * @return
     */
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                source = StringUtils.trim(source);
                if (StringUtils.isBlank(source))
                    return null;

                if (source.length() == 10)
                    sdf = new SimpleDateFormat("yyyy-MM-dd");

                if (source.contains("T")) {
                    source = source.replace('T', ' ');
                    source += ":00";
                }

                Date date = null;
                try {
                    date = sdf.parse(source);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

}
