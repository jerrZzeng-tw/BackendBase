package com.iisi.backendbase.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebUtils {

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param data     待渲染的物件
     * @return null
     */
    public static void renderString(HttpServletResponse response, Object data) throws Exception {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(BeanUtility.objectToJson(data));
    }
}