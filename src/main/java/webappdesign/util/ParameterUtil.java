package webappdesign.util;

import javax.servlet.http.HttpServletRequest;

public final class ParameterUtil {
    public static String getParameter(String paramName, HttpServletRequest request) {
        return request.getParameter(paramName);
    }

    public static void setParameter(String paramName, Object paramValue, HttpServletRequest request) {
        request.setAttribute(paramName, paramValue);
    }
}
