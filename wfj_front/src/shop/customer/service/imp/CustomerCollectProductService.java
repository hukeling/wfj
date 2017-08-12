package shop.customer.service.imp;
import shop.customer.dao.ICustomerCollectProductDao;
import shop.customer.pojo.CustomerCollectProduct;
import shop.customer.service.ICustomerCollectProductService;
import util.service.BaseService;
/**
 * 用户收藏Service接口实现类
 * @author ss
 *
 */
public class CustomerCollectProductService extends BaseService  <CustomerCollectProduct> implements ICustomerCollectProductService{
	@SuppressWarnings("unused")
	private ICustomerCollectProductDao customerCollectProductDao;
	public void setCustomerCollectProductDao(
			ICustomerCollectProductDao customerCollectProductDao) {
		this.baseDao = this.customerCollectProductDao = customerCollectProductDao;
	}
}