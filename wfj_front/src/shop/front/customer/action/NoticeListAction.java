package shop.front.customer.action;
import java.util.List;

import shop.messageCenter.pojo.MessageCenter;
import shop.messageCenter.service.IMessageCenterService;
import util.action.BaseAction;
import util.pojo.PageHelper;
public class NoticeListAction extends BaseAction {
	@SuppressWarnings("unused")
	private IMessageCenterService messageCenterService;
	@SuppressWarnings("unused")
	private List<MessageCenter> messages; //信息列表
	//分页
	@SuppressWarnings("unused")
	private String page;
	@SuppressWarnings("unused")
	private PageHelper pageHelper;
	@SuppressWarnings("unused")
	private int pageSize = 10;
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺收藏列表
	 * @return
	 */
	public String messages(){
 		return SUCCESS;
	}
}
