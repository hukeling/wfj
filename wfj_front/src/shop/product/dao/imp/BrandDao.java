package shop.product.dao.imp;

import java.util.List;

import shop.product.dao.IBrandDao;
import shop.product.pojo.Brand;
import util.dao.BaseDao;

/**
 * 品牌Dao接口实现类
 * 
 * @author ss
 * 
 */
public class BrandDao extends BaseDao<Brand> implements IBrandDao {

	public List<Brand> findPhoneBrandListBySql(String sql) {
		List<Brand> list = this.hibernateTemplate.find(sql);
		return list;
	}

}
