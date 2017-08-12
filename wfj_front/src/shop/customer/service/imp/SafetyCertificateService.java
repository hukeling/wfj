package shop.customer.service.imp;
import shop.customer.dao.ISafetyCertificateDao;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.service.ISafetyCertificateService;
import util.service.BaseService;
/**
 * 安全认证Service接口实现类
 * @author ss
 *
 */
public class SafetyCertificateService extends BaseService  <SafetyCertificate> implements ISafetyCertificateService{
	@SuppressWarnings("unused")
	private ISafetyCertificateDao safetyCertificateDao;
	public void setSafetyCertificateDao(ISafetyCertificateDao safetyCertificateDao) {
		this.baseDao = this.safetyCertificateDao = safetyCertificateDao;
	}
}
