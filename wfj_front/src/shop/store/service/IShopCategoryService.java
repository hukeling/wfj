package shop.store.service;
import java.util.List;

import shop.store.pojo.ShopCategory;
import util.service.IBaseService;
/**
 * IShopCategoryService - 店铺分类Service接口
 */
public interface IShopCategoryService extends IBaseService <ShopCategory> {
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
	 * @param productTypeId   商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String shopCategoryId);
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("unchecked")
	public List queryByParentId(String id);
}
