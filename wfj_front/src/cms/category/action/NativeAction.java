package cms.category.action;
import util.action.BaseAction;
/**
 *  测试国际化action
 * 
 */
@SuppressWarnings("serial")
public class NativeAction extends BaseAction {
    @SuppressWarnings("unused")
	public String gotoNative(){
    	String text = this.getText("welcome");
    	request.setAttribute("message", this.getText("welcome"));
    	request.setAttribute("wen", this.getText("wen"));
    	return SUCCESS;
    }
}
