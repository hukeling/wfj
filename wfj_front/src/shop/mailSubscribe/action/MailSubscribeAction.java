package shop.mailSubscribe.action;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.mailSubscribe.pojo.MailSubscribe;
import shop.mailSubscribe.service.IMailSubscribeService;
import util.action.BaseAction;
import util.mail.Mail;
import util.other.JSONFormatDate;
import basic.pojo.Users;
/**
 * 邮件订阅action
 * @author lqn
 *
 */
public class MailSubscribeAction extends BaseAction{
	private static final long serialVersionUID = -244674565742414224L;
	private IMailSubscribeService mailSubscribeService;
	private ICustomerService customerService;
	private List<Customer> customerList;
	private List<MailSubscribe> mailSubscribeList;
	private MailSubscribe mailSubscribe;
	private String ids;
	private String id;
	private String params;
	/**
	 * 进入邮件订阅页面
	 * @return
	 * @throws Exception
	 */
	public String gotoMailSubscribeInfoPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 查询邮件订阅信息
	 * @throws Exception
	 */
	public void listMailSubscribes() throws Exception{
		String hql = " where 1 = 1 ";
		if (params != null && !"".equals(params)) {
			String[] strArr = params.split("_");
			if (!"none".equals(strArr[0])&&!"".equals(strArr[0].trim())) {
				hql += " and o.title like '%" + strArr[0].trim() + "%'";
			}
			if (!"none".equals(strArr[1])) {
				hql += " and o.isSend=" + strArr[1];
			}
		}
		int total = mailSubscribeService.getCount(null);
		pageHelper.setPageInfo(pageSize, total, currentPage);
		mailSubscribeList = mailSubscribeService.findListByPageHelper(null, pageHelper, hql+" order by o.createTime desc ");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", total);// total键 存放总记录数，必须的
		jsonMap.put("rows", mailSubscribeList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改邮件订阅信息
	 * @throws Exception
	 */
	public void saveOrUpdateMailSubscribe() throws Exception{
		Users user = (Users) request.getSession().getAttribute("users");
		if(mailSubscribe.getArticleId()!=null&&!"".equals(mailSubscribe.getArticleId())){
			mailSubscribe.setUpdateTime(new Date()); //修改时间
			mailSubscribe.setModifyUser(user.getUserName());//修改者即登陆的管理员
		}else{
			mailSubscribe.setCreateTime(new Date());//创建时间
			mailSubscribe.setUpdateTime(mailSubscribe.getCreateTime());//修改时间
			mailSubscribe.setPublishUser(user.getUserName());//发布者即登陆的管理员
		}
		mailSubscribe = (MailSubscribe) mailSubscribeService.saveOrUpdateObject(mailSubscribe);
		JSONObject jo = new JSONObject();
		if(mailSubscribe!=null){
			jo.accumulate("isSuccess", "true");
		}else{
			jo.accumulate("isSuccess", "faile");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 删除邮件订阅信息
	 * @throws Exception
	 */
	public void deleteMailSubscribe() throws Exception{
		Boolean isSuccess = mailSubscribeService.deleteObjectsByIds("articleId", ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 获取邮件订阅详情
	 * @throws Exception
	 */
	public void getMailSubscribeObject() throws Exception{
		mailSubscribe = (MailSubscribe) mailSubscribeService.getObjectById(id);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		jsonMap.put("mailSubscribe", mailSubscribe);
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 查找用户信息
	 * 
	 * @throws Exception
	 */
	public void listCustomer() throws Exception {
		int totalRecordCount=customerService.getCount(null);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		customerList=customerService.findListByPageHelper(null, pageHelper, null);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", customerList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 发送邮件
	 * @throws Exception
	 */
	public void sendMail() throws Exception{
		mailSubscribe = (MailSubscribe) mailSubscribeService.getObjectById(id);
		String hql = " select c.email as email from Customer c where c.customerId in ("+ ids + ")";
		List<String> customerList = customerService.findObjectsByHQL(hql);
		boolean flag = true;
		String subject=mailSubscribe.getTitle();// 邮件标题
		String content=mailSubscribe.getContent();//邮件内容
		String email = null;
		for (int i = 0; i < customerList.size(); i++) {
			email = null;
			email = customerList.get(i);
			if(email!=null&&!"".equals(email)){
				//System.out.println(email);
				Mail.sent(email, subject, content,servletContext);
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", flag + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public void setMailSubscribeService(IMailSubscribeService mailSubscribeService) {
		this.mailSubscribeService = mailSubscribeService;
	}
	public List<MailSubscribe> getMailSubscribeList() {
		return mailSubscribeList;
	}
	public void setMailSubscribeList(List<MailSubscribe> mailSubscribeList) {
		this.mailSubscribeList = mailSubscribeList;
	}
	public MailSubscribe getMailSubscribe() {
		return mailSubscribe;
	}
	public void setMailSubscribe(MailSubscribe mailSubscribe) {
		this.mailSubscribe = mailSubscribe;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
}
