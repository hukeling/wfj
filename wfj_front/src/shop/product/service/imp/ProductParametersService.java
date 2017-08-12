package shop.product.service.imp;
import shop.product.dao.IProductParametersDao;
import shop.product.pojo.ProductParameters;
import shop.product.service.IProductParametersService;
import util.service.BaseService;
/**
 * IProductParametersService - 商品详细参数service接口实现类
 * @author 孟琦瑞
 */
public class ProductParametersService extends BaseService  <ProductParameters> implements IProductParametersService{
	@SuppressWarnings("unused")
	private IProductParametersDao productParametersDao;
	public void setProductParametersDao(IProductParametersDao productParametersDao) {
		this.baseDao =this.productParametersDao= productParametersDao;
	}
}