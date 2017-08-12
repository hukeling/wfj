package shop.product.service.imp;

import java.util.ArrayList;
import java.util.List;

import shop.product.dao.IBrandDao;
import shop.product.pojo.Brand;
import shop.product.pojo.Brand2;
import shop.product.service.IBrandService;
import util.service.BaseService;

/**
 * 品牌Service接口实现类
 * 
 * @author ss
 * 
 */
public class BrandService extends BaseService<Brand> implements IBrandService {
	@SuppressWarnings("unused")
	private IBrandDao brandDao;

	public void setBrandDao(IBrandDao brandDao) {
		this.baseDao = this.brandDao = brandDao;
	}

	 
	public List<Brand> findPhoneBrandListBySql(String sql) {
		return brandDao.findPhoneBrandListBySql(sql);
	}
	
}