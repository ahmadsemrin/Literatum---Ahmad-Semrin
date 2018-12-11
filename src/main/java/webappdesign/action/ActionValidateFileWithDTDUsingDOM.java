package webappdesign.action;

import webappdesign.util.FileUtil;

public class ActionValidateFileWithDTDUsingDOM implements IAction {
    @Override
    public Object doAction(Object object) {
        String xml = (String) object;

        return FileUtil.isValidJATS(xml);
    }
}
