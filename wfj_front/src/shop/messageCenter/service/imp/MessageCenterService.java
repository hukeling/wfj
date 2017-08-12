package shop.messageCenter.service.imp;
import java.util.Date;

import shop.messageCenter.dao.IMessageCenterDao;
import shop.messageCenter.pojo.MessageCenter;
import shop.messageCenter.service.IMessageCenterService;
import util.service.BaseService;
/**
 * 
 * @author 刘钦楠
 * 
 */
public class MessageCenterService extends BaseService<MessageCenter> implements
		IMessageCenterService {
	private IMessageCenterDao messageCenterDao;
	public void deleteMessageCenter(String ids) {
		String[] arr = ids.split(",");
		MessageCenter messageCenter;
		for (String id : arr) {
			messageCenter = messageCenterDao.getObjectById(id);
			messageCenter.setMessageState(1);
		}
	}
	/**
	 * 保存或修改系统消息
	 */
	public MessageCenter saveOrUpdateSystemMessage(MessageCenter messageCenter) {
		if (messageCenter.getMessageId() == null
				|| "".equals(messageCenter.getMessageId())) {
			messageCenter.setCreateDate(new Date());
			messageCenter.setMessageOpen(0);
			messageCenter.setMessageIsMore(2);
			messageCenter.setMessageState(0);
			messageCenter.setMessageType(2);
		}
		messageCenter.setCreateDate(new Date());
		if(!"all".equals(messageCenter.getToMemberId())){
			messageCenter.setToMemberId(","+messageCenter.getToMemberId()+",");
		}
		return messageCenterDao.saveOrUpdateObject(messageCenter);
	}
	public void updateMessageSendState(String ids) {
		String[] arr = ids.split(",");
		MessageCenter messageCenter;
		for (String id : arr) {
			messageCenter = messageCenterDao.getObjectById(id);
			messageCenter.setMessageSendState(1);
		}
	}
	/**
	 * 保存或修改用户消息
	 */
	public MessageCenter saveOrUpdateUserMessage(MessageCenter messageCenter) {
		if (messageCenter.getMessageId() == null
				|| "".equals(messageCenter.getMessageId())) {
			messageCenter.setMessageOpen(0);
			messageCenter.setMessageState(0);
			messageCenter.setMessageType(1);
		}
		messageCenter.setCreateDate(new Date());
		messageCenter.setToMemberId(","+messageCenter.getToMemberId()+",");
		return messageCenterDao.saveOrUpdateObject(messageCenter);
	}
	public void updateMessageReadMemberId(String messageId, String customerId) {
		MessageCenter messageCenter = messageCenterDao.getObjectById(messageId);
		if(messageCenter.getReadMemberId()==null){
			messageCenter.setReadMemberId(","+customerId+",");
		}else{
			messageCenter.setReadMemberId(messageCenter.getReadMemberId()+customerId+",");
		}
	}
	public void updateMessageDeleteMemberId(String ids, String customerId) {
		String[] arr = ids.split(",");
		for(String id:arr){
			MessageCenter messageCenter = messageCenterDao.getObjectById(id);
			if(messageCenter.getDeleteMemberId()==null){
				messageCenter.setDeleteMemberId(","+customerId+",");
			}else{
				messageCenter.setDeleteMemberId(messageCenter.getDeleteMemberId()+customerId+",");
			}
		}
	}
	public void setMessageCenterDao(IMessageCenterDao messageCenterDao) {
		this.baseDao = this.messageCenterDao = messageCenterDao;
	}
}
