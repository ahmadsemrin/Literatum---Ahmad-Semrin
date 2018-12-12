package webappdesign.util;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

public final class ErrorHandlerUtil {
    public static void setLoginInsertionError(HttpServletRequest request) {
        request.setAttribute("hiddenFieldLogin", "Make sure you inserted the right email and password.");
    }

    public static void setLoginFirstError(HttpServletRequest request) {
        request.setAttribute("hiddenFieldLogin", "You must login first.");
    }

    public static void setSignUpPasswordNotSameError(HttpServletRequest request) {
        request.setAttribute("hiddenFieldSignUp", "Passwords are not the same.");
    }

    public static void setFoundEmailError() {
        JOptionPane.showMessageDialog(null, "Email that you are trying to insert is already "
                + "existed. Please insert another one.");
    }

    public static void setFoundFileError() {
        JOptionPane.showMessageDialog(null, "File that you are trying to insert is already "
                + "existed. Please insert another one.");
    }
}
