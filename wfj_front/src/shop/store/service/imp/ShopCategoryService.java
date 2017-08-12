package shop.store.service.imp;
import java.util.List;

import shop.store.dao.IShopCategoryDao;
import shop.store.pojo.ShopCategory;
import shop.store.service.IShopCategoryService;
import util.service.BaseService;
/**
 * ShopCategoryService - 店铺分类Service接口实现类
 */
@SuppressWarnings("unused")
public class ShopCategoryService extends BaseService  <ShopCategory> implements IShopCategoryService{
	private IShopCategoryDao shopCategoryDao;//店铺分类Dao
	public void setShopCategoryDao(IShopCategoryDao shopCategoryDao) {
		this.baseDao = this.shopCategoryDao = shopCategoryDao;
	}
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
	 * @param productTypeId   商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String shopCategoryId) {
		ShopCategory shopCategory = (ShopCategory)shopCategoryDao.getObjectById(shopCategoryId);
		shopCategory.setLoadType("2");
		shopCategoryDao.updateObject(shopCategory);
	}
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("unchecked")
	public List queryByParentId(String id) {
		return shopCategoryDao.findObjects(" where 1=1 and o.parentId="+id);
	}
}
