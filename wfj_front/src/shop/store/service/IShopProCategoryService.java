package shop.store.service;
import java.util.List;

import shop.store.pojo.ShopProCategory;
import util.service.IBaseService;
/**
 * IShopProCategoryService - 店铺内部商品分类Service接口
 */
@SuppressWarnings("unused")
public interface IShopProCategoryService extends IBaseService <ShopProCategory> {
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
	 * @param productTypeId   商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String shopProCategoryId);
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("unchecked")
	public List queryByParentId(String id);
	/**
	 * 修改或者保存店铺分类
	 * @param shopCategory
	 * @return
	 */
	public ShopProCategory saveOrUpdateShopCategory(ShopProCategory shopProCategory);
	/**
	 * 删除店铺分类by id
	 * @param id
	 */
	public void deleteShopCategory(String id);
}
