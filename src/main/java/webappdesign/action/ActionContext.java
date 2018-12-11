package webappdesign.action;

public class ActionContext {
    private IAction action;

    public ActionContext(IAction action) {
        this.action = action;
    }

    public Object executeAction(Object object) {
        return action.doAction(object);
    }
}
