package shop.product.service.imp;
import shop.product.dao.IProductImgDao;
import shop.product.pojo.ProductImg;
import shop.product.service.IProductImgService;
import util.service.BaseService;
/**
 * ProductImgService - 类描述信息
 */
public class ProductImgService extends BaseService<ProductImg> implements IProductImgService {
		@SuppressWarnings("unused")
		private IProductImgDao productImgDao;
		public void setProductImgDao(IProductImgDao productImgDao) {
			this.baseDao=this.productImgDao = productImgDao;
		}
}
