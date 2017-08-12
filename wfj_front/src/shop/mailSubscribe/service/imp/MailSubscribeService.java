package shop.mailSubscribe.service.imp;
import shop.mailSubscribe.dao.IMailSubscribeDao;
import shop.mailSubscribe.pojo.MailSubscribe;
import shop.mailSubscribe.service.IMailSubscribeService;
import util.service.BaseService;
/**
 * 
 * @author lqn
 *
 */
public class MailSubscribeService extends BaseService<MailSubscribe> implements IMailSubscribeService{
	private IMailSubscribeDao mailSubscribeDao;
	public void setMailSubscribeDao(IMailSubscribeDao mailSubscribeDao) {
		this.baseDao = this.mailSubscribeDao = mailSubscribeDao;
	}
}
