package phone.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;

import net.sf.json.JSONObject;


public interface IPhoneCategoryhibService {
	/**
	 * 
	 * @param brandId:品牌id
	 * @return 根据品牌id查询出的商品信息
	 */
	public List<ProductInfo> proInfoListByBrandId(int brandId);
	/**
	 * @param keyword:商品名关键字
	 * @return 根据商品名关键字查询所有商品列
	 */
	public List  mohuSearch(String keyword);
	
	/**
	 * @param keyword:商品名关键字
	 * @param orderBy:排序方式
	 * @return 根据商品名关键字查询并按照指定标准排序后的所有商品列
	 */
	public List<ProductInfo> mohuSearchOrderBy(String keyword,String orderBy);
	/**
	 * 
	 * @return 推荐商品列表
	 */
	public List<ProductInfo> recommandPro();
	
	/**
	  * @param productid:产品ID
	  * @return productinfoList：根据产品id（productid）查询productinfo得到产品信息
	  */

	 public JSONObject selectProductInfoByProductid(int productid);

	 /**
		 * @return categoryMap:三级分类信息
		 * @throws IOException
		 */

	 public List<ProductType> initProdutType() throws IOException;
	 
	 /**
	  * @param productTypeId
	  * @return secondProductTypeList:根据一级分类Id获取的二级分类list
	  */
	 public List listCategory(int productTypeId);
	 
	 /**
	  * @param productTypeId
	  * @return 根据二级id查询三级分类的list
	  */
	 public List<ProductType> listSanCategory(int productTypeId);
	 
	 /**
	  * @param producttypeId:产品类别id
	  * @param orderBy：产品列表排序标准
	  * @return 按标准排序的商品list
	  */
	 public List selectProInfoByTypeId(int producttypeId, String orderBy);

}
