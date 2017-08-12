package shop.shopUtil.action;
import java.io.PrintWriter;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import util.action.BaseAction;
/**
 * 前后台通用校验类
 * 
 * @author 刘钦楠
 * 
 */
public class CheckAction extends BaseAction {
	private static final long serialVersionUID = -1025714522244548741L;
	private ICustomerService customerService;
	/******************************************************************************/
	/**
	 * 校验该手机号在客户表中是否存在
	 */
	public void checkPhone() throws Exception{
		String phone = request.getParameter("phone");
		Customer c = (Customer) customerService.getObjectByParams("  where o.phone = '"+phone.trim()+"'");
		JSONObject jo = new JSONObject();
		if(c!=null){
			jo.accumulate("isSuccess", "false");
		}else{
			jo.accumulate("isSuccess", "true");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public void checkEmail() throws Exception{
		String email = request.getParameter("email");
		Customer c = (Customer) customerService.getObjectByParams(" where o.email = '"+email.trim()+"'");
		JSONObject jo = new JSONObject();
		if(c!=null){
			jo.accumulate("isSuccess", "false");
		}else{
			jo.accumulate("isSuccess", "true");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/******************************************************************************/
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
