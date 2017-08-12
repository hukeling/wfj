package shop.front.customer.action;
import java.io.PrintWriter;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import util.action.BaseAction;
/**
 * 推荐好友Action
 * @author 刘钦楠
 *
 */
public class ADFAction extends BaseAction{
	private static final long serialVersionUID = 2150753241120695837L;
	/*******************************************************************/
	public String gotoADFPage() throws Exception{
		return SUCCESS;
	}
	public void createUrl() throws Exception{
		String url = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+url+"/";
		Customer c = (Customer) request.getSession().getAttribute("customer");
		String addUrl = basePath+"register/gotoRegister.action?id="+c.getCustomerId();
		JSONObject jo=new JSONObject();
		jo.accumulate("addUrl", addUrl);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/*******************************************************************/
}
