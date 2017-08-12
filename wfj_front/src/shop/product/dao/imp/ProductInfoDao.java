package shop.product.dao.imp;
import java.util.List;

import shop.product.dao.IProductInfoDao;
import shop.product.pojo.ProductInfo;
import util.dao.BaseDao;
/**
 * 商品Dao接口实现类
 * @author ss
 *
 */
public class ProductInfoDao extends BaseDao <ProductInfo> implements IProductInfoDao {
	

	public static List<ProductInfo> findList(String hql) {
		
		return null;
	}
}
