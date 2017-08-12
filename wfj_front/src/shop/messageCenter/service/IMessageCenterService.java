package shop.messageCenter.service;
import shop.messageCenter.pojo.MessageCenter;
import util.service.IBaseService;
/**
 * 
 * @author 刘钦楠
 * 
 */
public interface IMessageCenterService extends IBaseService<MessageCenter> {
	/**
	 * 修改消息状态位  表示删除
	 * @param ids
	 */
	public void deleteMessageCenter(String ids);
	/**
	 * 保存或修改站内系统消息
	 * @param messageCenter
	 * @return
	 */
	public MessageCenter saveOrUpdateSystemMessage(MessageCenter messageCenter);
	/**
	 * 立即发送消息
	 * @param ids
	 */
	public void updateMessageSendState(String ids);
	/**
	 * 保存或修改用户发送的消息
	 * @param messageCenter
	 * @return
	 */
	public MessageCenter saveOrUpdateUserMessage(MessageCenter messageCenter);
	/**
	 * 将读过该消息的用户记录到数据库中
	 * @param id
	 */
	public void updateMessageReadMemberId(String messageId,String customerId);
	/**
	 * 将删除该消息的用户记录到数据库中
	 * @param messageId
	 * @param customerId
	 */
	public void updateMessageDeleteMemberId(String ids,String customerId);
}
