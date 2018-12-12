package webappdesign.util;

public final class StringUtil {
    private static int lastIndexOfStrInText(String str, String text) {
        return text.lastIndexOf(str);
    }

    private static String getSubFromText(int fromIndex, String text) {
        return text.substring(fromIndex);
    }

    public static String getURIPageFromFullURI(String fullURI) {
        int lastIndex = lastIndexOfStrInText("/", fullURI);

        return getSubFromText(lastIndex + 1, fullURI);
    }

    public static boolean areStringEqual(String str1, String str2) {
        return str1.equals(str2);
    }

    public static boolean isURI(String str, String pageURI) {
        return areStringEqual(str, pageURI);
    }

    public static boolean isSuperAdmin(String str) {
        return areStringEqual(str, "super");
    }

    public static boolean isAdmin(String str) {
        return areStringEqual(str, "admin");
    }

    public static boolean isBasicUser(String str) {
        return areStringEqual(str, "basic");
    }

    private static boolean isStrStartsWith(String text, String str) {
        return text.startsWith(str);
    }

    public static boolean isEmailForAdmin(String email) {
        return isStrStartsWith(email, "admin");
    }
}
