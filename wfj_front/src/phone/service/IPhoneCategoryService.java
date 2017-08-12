package phone.service;

import java.io.IOException;

import net.sf.json.JSONObject;

 


public interface IPhoneCategoryService {

	/**
	 * 
	 * @param productId:shop_productinfo表的productId值
	 * @return proinfoList：根据productId（产品id）查询表shop_productinfo表得到productinfo信息
	 */
		public JSONObject selectProductInfoByProductid(int productId);
		
	/**
		 * 
		 * @param productTypeId:shop_productinfo表的productTypeId值
		 * @return proinfoList：根据productTypeId（类别id）查询表shop_productinfo表得到productinfo信息
		 */
		public JSONObject selectProductInfoByProducttypetid(int productTypeId) ;
		
		/**
		 *  初始化分类菜单
		 * @return categoryMap
		 * @throws IOException
		 */
		public JSONObject initProdutTypeInfo() throws IOException;
}
