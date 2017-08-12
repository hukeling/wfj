package phone.action;

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.json.JSONObject;
import phone.pojo.CustomerFeedback;
import phone.service.IPhoneFeedBackService;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import util.action.BaseAction;
// /phone/feedback.action?customerId=456&customerEmail=123@129.com&customerPhone=123456789&fbtype=1&fbcontent=asdfghjk
public class PhoneFeedBackAction extends BaseAction{

	private int customerId;
	private String customerEmail;
	private String customerPhone;
	private int fbtype;//反馈意见类型
	private String fbcontent;//反馈内容
	
	IPhoneFeedBackService feedbackService;
	ICustomerService customerService;
	public void addFeedBack() throws IOException{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		JSONObject jo=new JSONObject();
		fbcontent=new String(fbcontent.getBytes("iso8859-1"),"utf-8");
		CustomerFeedback feedback=new CustomerFeedback();
		feedback.setFbtype(fbtype);
		feedback.setCustomerId(customerId);
		feedback.setFbcontent(fbcontent);
		feedback.setCustomerEmail(customerEmail);
		feedback.setCustomerPhone(customerPhone);
		feedback.setIsDeal(0);
		Customer customer=(Customer)customerService.getObjectById(String.valueOf(customerId));
		if(customerEmail==null){
			feedback.setCustomerEmail(customer.getEmail());
		}
		if(customerPhone==null){
			feedback.setCustomerPhone(customer.getPhone());
		}
		feedback=(CustomerFeedback)feedbackService.saveOrUpdateObject(feedback);
		if(feedback!=null){
			jo.accumulate("Status", true);
			jo.accumulate("Data", "反馈成功");
		}else{
			jo.accumulate("Status", false);
			jo.accumulate("Data", "反馈失败");
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	}
	
	
	
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setFeedbackService(IPhoneFeedBackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public void setFbtype(int fbtype) {
		this.fbtype = fbtype;
	}
	public void setFbcontent(String fbcontent) {
		this.fbcontent = fbcontent;
	}
}
