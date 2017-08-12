package shop.prosceniumInstall.service.imp;
import java.util.List;

import shop.prosceniumInstall.dao.IProsceniumCategoryDao;
import shop.prosceniumInstall.pojo.ProsceniumCategory;
import shop.prosceniumInstall.service.IProsceniumCategoryService;
import util.service.BaseService;
/**
 * ProsceniumCategoryService - 前台中间部分分类Service接口实现类
 */
public class ProsceniumCategoryService extends BaseService  <ProsceniumCategory> implements IProsceniumCategoryService{
	private IProsceniumCategoryDao prosceniumCategoryDao;
	public void setProsceniumCategoryDao(
			IProsceniumCategoryDao prosceniumCategoryDao) {
		this.baseDao = this.prosceniumCategoryDao = prosceniumCategoryDao;
	}
	//根据列名去重数据
	@SuppressWarnings("unchecked")
	public List findUnDistinctList(String column,String params){
		return prosceniumCategoryDao.findUnDistinctList(column, params);
	}
}
