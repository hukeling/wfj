package shop.store.service.imp;
import java.util.List;

import shop.store.dao.IShopProCategoryDao;
import shop.store.pojo.ShopProCategory;
import shop.store.service.IShopProCategoryService;
import util.service.BaseService;
/**
 * ShopProCategoryService - 店铺内部商品分类Service接口实现类
 */
@SuppressWarnings("unused")
public class ShopProCategoryService extends BaseService  <ShopProCategory> implements IShopProCategoryService{
	private IShopProCategoryDao shopProCategoryDao;
	public void setShopProCategoryDao(IShopProCategoryDao shopProCategoryDao) {
		this.baseDao = this.shopProCategoryDao = shopProCategoryDao;
	}
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
	 * @param productTypeId   商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String shopProCategoryId) {
		ShopProCategory shopProCategory = (ShopProCategory)shopProCategoryDao.getObjectById(shopProCategoryId);
		shopProCategory.setLoadType("2");
		shopProCategoryDao.updateObject(shopProCategory);
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
		return shopProCategoryDao.findObjects(" where 1=1 and o.parentId="+id);
	}
	/**
	 * 修改或者保存店铺分类
	 * @param shopCategory
	 * @return
	 */
	public ShopProCategory saveOrUpdateShopCategory(ShopProCategory shopProCategory){
		shopProCategoryDao.saveOrUpdateObject(shopProCategory);
		return shopProCategory;
	}
	/**
	 * 删除店铺分类by id
	 * @param id
	 */
	public void deleteShopCategory(String id){
		shopProCategoryDao.deleteObjectById(id);
	}
}
