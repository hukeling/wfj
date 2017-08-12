package shop.messageCenter.pojo;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 商城信息(SendMessage)消息类
 * 
 * @author 刘钦楠
 * 
 */
public class MessageCenter extends BaseEntity {
	private static final long serialVersionUID = -3529039790115479049L;
	/**
	 * 站内消息ID
	 */
	private Integer messageId;
	/**
	 * 站内消息发送人ID 如果是系统消息 ID为0
	 */
	private Integer fromMemberId;
	/**
	 * 站内消息发送人用户名
	 */
	private String fromMemberName;
	/**
	 * 商城信息(SendMessage)消息接收人ID
	 */
	private String toMemberId;
	/**
	 * 站内消息接收人用户名
	 */
	private String toMemberName;
	/**
	 * 站内消息标题
	 */
	private String messageTitle;
	/**
	 * 站内消息内容
	 */
	private String content;
	/**
	 * 站内消息是否已读 0未读 1已读
	 */
	private Integer messageOpen;
	/**
	 * 站内消息状态，0为正常状态，1为发送人删除状态，2为接收人删除状态，3为发送人、接收人双方都删除
	 */
	private Integer messageState;
	/**
	 * 站内消息类型 1为普通消息 2为系统消息
	 */
	private Integer messageType;
	/**
	 * 已经读取过该消息的会员ID
	 */
	private String readMemberId;
	/**
	 * 已经删除该消息的会员ID
	 */
	private String deleteMemberId;
	/**
	 * 商城信息(SendMessage)是否为一条发给多个用户 1：一条发一个用户；2：一条发多个用户
	 */
	private Integer messageIsMore;
	/**
	 * 发送人IP地址
	 */
	private String ip;
	/**
	 * 是否立即发送 1为立即发送 2为保存草稿
	 */
	private Integer messageSendState;
	/**
	 * 站内消息创建时间
	 */
	private Date createDate;
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public Integer getFromMemberId() {
		return fromMemberId;
	}
	public void setFromMemberId(Integer fromMemberId) {
		this.fromMemberId = fromMemberId;
	}
	public String getFromMemberName() {
		return fromMemberName;
	}
	public void setFromMemberName(String fromMemberName) {
		this.fromMemberName = fromMemberName;
	}
	public String getToMemberId() {
		return toMemberId;
	}
	public void setToMemberId(String toMemberId) {
		this.toMemberId = toMemberId;
	}
	public String getToMemberName() {
		return toMemberName;
	}
	public void setToMemberName(String toMemberName) {
		this.toMemberName = toMemberName;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getMessageOpen() {
		return messageOpen;
	}
	public void setMessageOpen(Integer messageOpen) {
		this.messageOpen = messageOpen;
	}
	public Integer getMessageState() {
		return messageState;
	}
	public void setMessageState(Integer messageState) {
		this.messageState = messageState;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public String getReadMemberId() {
		return readMemberId;
	}
	public void setReadMemberId(String readMemberId) {
		this.readMemberId = readMemberId;
	}
	public String getDeleteMemberId() {
		return deleteMemberId;
	}
	public void setDeleteMemberId(String deleteMemberId) {
		this.deleteMemberId = deleteMemberId;
	}
	public Integer getMessageIsMore() {
		return messageIsMore;
	}
	public void setMessageIsMore(Integer messageIsMore) {
		this.messageIsMore = messageIsMore;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getMessageSendState() {
		return messageSendState;
	}
	public void setMessageSendState(Integer messageSendState) {
		this.messageSendState = messageSendState;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
