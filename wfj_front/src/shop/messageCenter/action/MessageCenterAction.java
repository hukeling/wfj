package shop.messageCenter.action;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.messageCenter.pojo.MessageCenter;
import shop.messageCenter.service.IMessageCenterService;
import util.action.BaseAction;
import basic.pojo.Users;
/**
 * 商城信息(SendMessage)后台系统消息管理模块
 * 
 * @author 刘钦楠
 * 
 */
public class MessageCenterAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5475679489540950749L;
	private IMessageCenterService messageCenterService;
	private ICustomerService customerService;
	@SuppressWarnings("unchecked")
	private List<Map> messageCenterList;
	private MessageCenter messageCenter;
	private Customer customer;
	private String ids;
	private String id;
	private String params;
	/************************ 方法 ********************************/
	/**
	 * 进入站内消息中心
	 */
	public String gotoMessageCenterPage() throws Exception {
		return SUCCESS;
	}
	/**
	 * 进入发件箱页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String gotoOutBoxInfoPage() throws Exception {
		return SUCCESS;
	}
	/**
	 * 进入草稿箱页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String gotoDraftsInfopage() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询系统消息发件箱信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void listSendMessage() throws Exception {
		String hql1 = " select count(m.messageId) from MessageCenter m ";
		String hql2 = " select m.messageId as messageId,m.toMemberName as toMemberName,m.messageTitle as messageTitle,m.createDate as createDate from MessageCenter m ";
		String condition = " where m.messageType = 2 and m.messageSendState = 1 and m.messageState = 0  ";
		if (params != null) {
			String[] arr = params.split("_");
			if (!"none".equals(arr[0])) {
				condition += " and m.messageTitle like '%" + arr[0].trim() + "%'";
			}
			if (!"none".equals(arr[1]) && !"none".equals(arr[2])) {
				condition += " and m.createDate between '" + arr[1]
						+ " 00:00:00' and '" + arr[2] + " 23:59:59'";
			}
		}
		condition += "  order by m.createDate desc ";
		int totalRecordCount = messageCenterService.getCountByHQL(hql1
				+ condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		messageCenterList = messageCenterService.findListMapPage(hql2
				+ condition, pageHelper);
		// 将map中Date类型对象转换成String类型
		for (Map map : messageCenterList) {
			map.put("createDate", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(map.get("createDate"))).toString());
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", messageCenterList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 删除管理员发件箱消息记录
	 * 
	 * @throws Exception
	 */
	public void deleteMessageCenter() throws Exception {
		messageCenterService.deleteMessageCenter(ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", "true");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 删除草稿箱中的系统消息
	 * @throws Exception
	 */
	public void deleteDriftsMessage() throws Exception{
		Boolean isSuccess = messageCenterService.deleteObjectsByIds("messageId", ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess+"");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改
	 * 
	 * @throws Exception
	 */
	public void saveOrUpdateMessageCenterAjax() throws Exception {
		String[] arr = messageCenter.getToMemberId().split(",");
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		boolean flag = true;
		if(!"all".equals(messageCenter.getToMemberId())){
			for(String str:arr){
				customer = (Customer) customerService.getObjectByParams(" where o.loginName = '" + str +"'");
				if(customer!=null){
					if(sb.length()==0){
						sb.append(customer.getCustomerId());
					}else{
						sb.append(","+customer.getCustomerId());
					}
					if(sb1.length()==0){
						sb1.append(str);
					}else{
						sb1.append(","+str);
					}
				}else{
					if(sb2.length()==0){
						sb2.append(str);
					}else{
						sb2.append(","+str);
					}
					flag = false;
				}
			}
		}else{
			sb.append("all");
			sb1.append("所有会员");
		}
		JSONObject jo = new JSONObject();
		if(flag){
			messageCenter.setToMemberId(sb.toString());
			messageCenter.setToMemberName(sb1.toString());
			messageCenter.setIp(request.getRemoteHost());
			messageCenter.setFromMemberId(0);
			messageCenter.setFromMemberName("系统消息");
			Users users = (Users) request.getAttribute("users");
			if(users!=null){
				messageCenter.setFromMemberId(users.getUsersId());
				messageCenter.setFromMemberName("系统消息");
			}
			messageCenterService.saveOrUpdateSystemMessage(messageCenter);
			jo.accumulate("isSuccess", "true");
		}else{
			jo.accumulate("isSuccess", "false");
			jo.accumulate("sb2", sb2.toString());
		}		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 获得系统消息详细信息
	 * 
	 * @throws Exception
	 */
	public void getMessageCenterObject() throws Exception {
		messageCenter = (MessageCenter) messageCenterService.getObjectById(id);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("messageCenter", messageCenter);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 查询草稿箱消息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void listDraftsMessage() throws Exception {
		String hql1 = " select count(m.messageId) from MessageCenter m ";
		String hql2 = " select m.messageId as messageId,m.messageTitle as messageTitle,m.createDate as createDate from MessageCenter m ";
		String condition = " where m.messageType = 2 and m.messageSendState = 2 and m.messageState = 0 ";
		if (params != null) {
			String[] arr = params.split("_");
			if (!"none".equals(arr[0])) {
				condition += " and m.messageTitle like '%" + arr[0].trim() + "%'";
			}
			if (!"none".equals(arr[1]) && !"none".equals(arr[2])) {
				condition += " and m.createDate between '" + arr[1]
						+ " 00:00:00' and '" + arr[2] + " 23:59:59'";
			}
		}
		condition += "  order by m.createDate desc ";
		int totalRecordCount = messageCenterService.getCountByHQL(hql1
				+ condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		messageCenterList = messageCenterService.findListMapPage(hql2
				+ condition, pageHelper);
		// 将list中map对象里的Date类型转换成String类型
		for (Map map : messageCenterList) {
			map.put("createDate", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(map.get("createDate"))).toString());
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", messageCenterList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 立即发送消息
	 * 
	 * @throws Exception
	 */
	public void sendMessageNow() throws Exception {
		messageCenterService.updateMessageSendState(ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", "true");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 验证用户名是否正确
	 * @throws Exception
	 */
	public void checkLoginName() throws Exception{
	}
	/************************* set、get方法 *******************************/
	public MessageCenter getMessageCenter() {
		return messageCenter;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getMessageCenterList() {
		return messageCenterList;
	}
	@SuppressWarnings("rawtypes")
	public void setMessageCenterList(List<Map> messageCenterList) {
		this.messageCenterList = messageCenterList;
	}
	public void setMessageCenter(MessageCenter messageCenter) {
		this.messageCenter = messageCenter;
	}
	public void setMessageCenterService(
			IMessageCenterService messageCenterService) {
		this.messageCenterService = messageCenterService;
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
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
