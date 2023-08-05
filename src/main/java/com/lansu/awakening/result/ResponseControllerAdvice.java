package com.lansu.awakening.result;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;

/**
 * 响应控制器通知
 *
 * @author sulan
 * @date 2023/08/06
 */
@RestControllerAdvice(basePackages = {
        "com.lansu.awakening.controller"
})
@Slf4j
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //不拦截@ResponseAnnotation接口
        AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
        ResponseAnnotation responseAnnotation = AnnotationUtils.findAnnotation(annotatedElement, ResponseAnnotation.class);
        if (responseAnnotation != null) {
            return false;
        }
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !returnType.getParameterType().equals(R.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return JSONObject.toJSONString(R.ok().data(body));
        }
        return R.ok().data(body);
    }
}
