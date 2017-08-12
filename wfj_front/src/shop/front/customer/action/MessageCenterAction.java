package shop.front.customer.action;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.messageCenter.pojo.MessageCenter;
import shop.messageCenter.service.IMessageCenterService;
import util.action.BaseAction;
/**
 * 用户商城信息(SendMessage)管理模块
 * @author 刘钦楠
 *
 */
public class MessageCenterAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8533984773545940890L;
	private IMessageCenterService messageCenterService;
	private ICustomerService customerService;
	private Customer customer;
	@SuppressWarnings("unchecked")
	private List<Map> messageCenterList;
	private MessageCenter messageCenter;
	private String id;
	private String ids;
	private String params;
	/*************************************************************/
	/**
	 * 新建商城信息(SendMessage)
	 */
	public String gotoNewMessagePage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 查询用户发件箱信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listOutBoxMessage() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		String hql1 = " select count(m.messageId) from MessageCenter m ";
		String hql2 = " select m.messageId as messageId,m.toMemberId as toMemberId,m.toMemberName as toMemberName,m.messageTitle as messageTitle,m.createDate as createDate from MessageCenter m ";
		String condition = " where m.fromMemberId = "+customer.getCustomerId()+" and m.messageType = 1 and m.messageSendState = 1 and (m.messageState = 0 or m.messageState = 2) ";
		condition += "  order by m.createDate desc ";
		int totalRecordCount = messageCenterService.getCountByHQL(hql1+condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		messageCenterList = messageCenterService.findListMapPage(hql2+condition, pageHelper);
		for(Map map : messageCenterList){
			map.put("createDate",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("createDate"))).toString());
		}
		return SUCCESS;
	}
	/**
	 * 查找用户草稿箱信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listDraftsMessage() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		String hql1 = " select count(m.messageId) from MessageCenter m ";
		String hql2 = " select m.messageId as messageId,m.toMemberName as toMemberName,m.messageTitle as messageTitle,m.createDate as createDate from MessageCenter m ";
		String condition = " where m.fromMemberId = "+customer.getCustomerId()+" and m.messageType = 1 and m.messageSendState = 2 and m.messageState = 0  ";
		condition += "  order by m.createDate desc ";
		int totalRecordCount = messageCenterService.getCountByHQL(hql1+condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		messageCenterList = messageCenterService.findListMapPage(hql2+condition, pageHelper);
		for(Map map : messageCenterList){
			map.put("createDate",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("createDate"))).toString());
		}
		return SUCCESS;
	}
	/**
	 * 获取消息详细信息
	 * @throws Exception
	 */
	public String getMessageObject() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		messageCenter = (MessageCenter) messageCenterService.getObjectById(id);
		if(messageCenter!=null){
			String str = messageCenter.getReadMemberId();
			if(str==null||str.indexOf(","+customer.getCustomerId()+",")==-1){
				messageCenterService.updateMessageReadMemberId(id, customer.getCustomerId().toString());
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询用户发送的信息详情
	 * @return
	 * @throws Exception
	 */
	public String getCusMessageObject() throws Exception{
		messageCenter = (MessageCenter) messageCenterService.getObjectById(id);
		return SUCCESS;
	}
	/**
	 * 校验用户输入的收件人是否存在
	 */
	public void checkCustomerName() throws Exception{
		String [] arr = ids.split(",");
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		boolean flag = true;
		for(String str:arr){
			customer = (Customer) customerService.getObjectByParams(" where o.loginName = '" +str+"'");
			if(customer==null){
				if(sb.length()==0){
					sb.append(str);
				}else{
					sb.append(","+str);
				}
				flag = false;
			}else{
				if(sb1.length()==0){
					sb1.append(customer.getCustomerId());
				}else{
					sb1.append(","+customer.getCustomerId());
				}
			}
		}
		JSONObject jo = new JSONObject();
		if(flag){
			jo.accumulate("isSuccess", "true");
			jo.accumulate("sb1", sb1.toString());
		}else{
			jo.accumulate("isSuccess", "false");
			jo.accumulate("sb", sb.toString());
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改用户商城信息(SendMessage)
	 * @throws Exception
	 */
	public String saveOrUpdateMessageCenter() throws Exception{
		messageCenter.setIp(request.getRemoteHost());
		customer = (Customer) request.getSession().getAttribute("customer");
		messageCenter.setFromMemberId(customer.getCustomerId());
		messageCenter.setFromMemberName(customer.getLoginName());
		String[] arr1 = messageCenter.getToMemberName().toString().split(",");
		if(arr1.length>1){
			messageCenter.setMessageIsMore(2);
		}else{
			messageCenter.setMessageIsMore(1);
		}
		messageCenter = messageCenterService.saveOrUpdateUserMessage(messageCenter);
		if(messageCenter.getMessageSendState()==2){
			return "drafts";
		}else{
			return "success";
		}
	}
	/**
	 * 删除用户发送接收到的站内消息
	 * @throws Exception
	 */
	public String deleteFrontMessageCenter() throws Exception {
		messageCenterService.deleteMessageCenter(ids);
		return SUCCESS;
	}
	/**
	 * 查询用户收件箱信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listInBoxMessage() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		String hql1 = " select count(m.messageId) from MessageCenter m  ";
		String hql2 = " select m.messageId as messageId,m.fromMemberName as fromMemberName,m.messageTitle as messageTitle,m.readMemberId as readMemberId,m.createDate as createDate from MessageCenter m ";
		String condition = " where m.messageSendState = 1 and m.messageType = 1 and (m.deleteMemberId not like '%,"+customer.getCustomerId()+",%' or m.deleteMemberId is null) and m.toMemberId like '%,"+customer.getCustomerId()+",%' ";
		condition += "  order by m.createDate desc ";
		int totalRecordCount = messageCenterService.getCountByHQL(hql1+condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		messageCenterList = messageCenterService.findListMapPage(hql2+condition, pageHelper);
		String str = null;
		for(Map map : messageCenterList){
			map.put("createDate",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("createDate"))).toString());
			str = (String) map.get("readMemberId");
			if(str!=null){
				if(str.indexOf(","+customer.getCustomerId()+",")!=-1){
					map.put("isRead", "已读");
				}else{
					map.put("isRead","未读");
				}
			}else{
				map.put("isRead","未读");
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询用户接收到的系统消息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listSystemMessage() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		String hql1 = " select count(m.messageId) from MessageCenter m ";
		String hql2 = " select m.messageId as messageId,m.fromMemberName as fromMemberName,m.messageTitle as messageTitle,m.readMemberId as readMemberId,m.createDate as createDate from MessageCenter m ";
		String condition = " where m.messageType = 2 and m.messageSendState = 1 and (m.toMemberId = 'all' OR m.toMemberId LIKE '%,"+customer.getCustomerId()+",%') and (m.deleteMemberId NOT LIKE '%,"+customer.getCustomerId()+",%' OR m.deleteMemberId is NULL) ";
		condition += "  order by m.createDate desc ";
		int totalRecordCount = messageCenterService.getCountByHQL(hql1+condition);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		messageCenterList = messageCenterService.findListMapPage(hql2+condition, pageHelper);
		String str = null;
		for(Map map : messageCenterList){
			map.put("createDate",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("createDate"))).toString());
			str = (String) map.get("readMemberId");
			if(str!=null){
				if(str.indexOf(","+customer.getCustomerId()+",")!=-1){
					map.put("isRead", "已读");
				}else{
					map.put("isRead","未读");
				}
			}else{
				map.put("isRead","未读");
			}
		}
		return SUCCESS;
	}
	/**
	 * 将读取该消息的用户添加到数据库中
	 * @throws Exception
	 */
	public void readMessage() throws Exception{
		messageCenterService.updateMessageReadMemberId(id, "1");
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", "true");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 将删除该消息的用户添加到数据库中相应字段
	 * @throws Exception
	 */
	public String customerDeleteMessage() throws Exception{
		customer = (Customer) request.getSession().getAttribute("customer");
		messageCenterService.updateMessageDeleteMemberId(ids, customer.getCustomerId().toString());
		return SUCCESS;
	}
	/**
	 * 删除用户草稿箱中的信息  彻底删除  不是更改状态位
	 * @throws Exception
	 */
	public String deleteMessageCenter() throws Exception{
		messageCenterService.deleteObjectsByIds("messageId", ids);
		return SUCCESS;
	}
	/*************************************************************/
	public void setMessageCenterService(IMessageCenterService messageCenterService) {
		this.messageCenterService = messageCenterService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getMessageCenterList() {
		return messageCenterList;
	}
	@SuppressWarnings("unchecked")
	public void setMessageCenterList(List<Map> messageCenterList) {
		this.messageCenterList = messageCenterList;
	}
	public MessageCenter getMessageCenter() {
		return messageCenter;
	}
	public void setMessageCenter(MessageCenter messageCenter) {
		this.messageCenter = messageCenter;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
