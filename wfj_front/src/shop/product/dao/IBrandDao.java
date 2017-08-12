package shop.product.dao;
import java.util.List;

import shop.product.pojo.Brand;
import util.dao.IBaseDao;
/**
 * 品牌Dao接口
 * @author ss
 *
 */
public interface IBrandDao extends IBaseDao <Brand>{

	List<Brand> findPhoneBrandListBySql(String sql);
}
