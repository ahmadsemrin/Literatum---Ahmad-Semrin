package webappdesign.util;

import javax.servlet.http.HttpServletRequest;

public final class AttributeUtil {
    public static void setAttribute(String attributeName, Object attributeValue, HttpServletRequest request) {
        request.setAttribute(attributeName, attributeValue);
    }

    public static Object getAttribute(String attributeName, HttpServletRequest request) {
        return request.getAttribute(attributeName);
    }
}
