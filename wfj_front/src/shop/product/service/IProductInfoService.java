package shop.product.service;

import java.util.List;
import java.util.Map;

import shop.product.pojo.ProductInfo;
import shop.store.pojo.ShopInfo;
import util.service.IBaseService;

/**
 * 商品Service接口
 * 
 * @author ss
 * 
 */
public interface IProductInfoService extends IBaseService<ProductInfo> {
	/**
	 * 删除商品 同时删除相关表中的数据
	 * 
	 * @param productId
	 *            商品ID
	 */
	public void deleteProduct(Integer productId, ShopInfo shopInfo);

	/**
	 * 根据商品查询分类ID、分类名称集合
	 * 
	 * @param productInfoList
	 *            商品集合
	 * @return 
	 *         List<Map>：map.key=productTypeId，map.value=相应的分类ID值；map.key=sortName
	 *         ，map.value=相应的分类名称值
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProductToTypeInfo(List<ProductInfo> productInfoList);

	/**
	 * 根据商品查询品牌ID、品牌名称集合
	 * 
	 * @param productInfoList
	 *            商品集合
	 * @return 
	 *         List<Map>：map.key=brandId，map.value=相应的品牌ID值；map.key=brandName，map
	 *         .value=相应的品牌名称值
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProductToBrandInfo(List<ProductInfo> productInfoList);

	/**
	 * 搜索引擎检索无数据，回传一些随机商品信息
	 * 
	 * @param productInfoList
	 */
	public List<ProductInfo> findRandomProductInfoList();

	/**
	 * 更新商品 方便商品下架时受到事务控制，以便于更新compass的索引文件
	 */
	public ProductInfo saveOrUpdateProductInfo(ProductInfo productInfo);
}
