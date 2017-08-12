package shop.prosceniumInstall.service;
import java.util.List;

import shop.prosceniumInstall.pojo.ProsceniumCategory;
import util.service.IBaseService;
/**
 * IProsceniumCategoryService - 前台中间部分分类Service接口
 */
public interface IProsceniumCategoryService extends IBaseService <ProsceniumCategory>{
	//根据列名去重数据
	@SuppressWarnings("unchecked")
	public List findUnDistinctList(String column,String params);
}
