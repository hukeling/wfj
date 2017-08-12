package shop.product.dao.imp;
import org.springframework.stereotype.Repository;

import shop.product.dao.IProductTypeDao;
import shop.product.pojo.ProductType;
import util.dao.BaseDao;
/**
 * 商品分类Dao接口实现类
 * @author ss
 *
 */
@Repository
public class ProductTypeDao extends BaseDao <ProductType> implements IProductTypeDao {
}
