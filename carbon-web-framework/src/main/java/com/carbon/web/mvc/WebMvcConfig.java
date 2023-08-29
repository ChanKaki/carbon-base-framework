package com.carbon.web.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * 类<code>WebMvcConfig</code>说明：
 *
 * @author kaki
 * @since 26/8/2023
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

   @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customHttpMessageConverter());
    }

    private HttpMessageConverter<?> customHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new MyJacksonMapper());
        return converter;
    }

}
