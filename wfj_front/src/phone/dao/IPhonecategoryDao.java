package phone.dao;

import java.io.IOException;
import java.util.List;

 

@SuppressWarnings("rawtypes")
public interface IPhonecategoryDao {

	/**
	 * 
	 * @param productId:shop_productinfo表的productId值
	 * @return proinfoList：根据productId（产品id）查询表shop_productinfo表得到productinfo信息
	 */
		public List  selectProductInfoByProductid(int productId);
		
	/**
		 * 
		 * @param productTypeId:shop_productinfo表的productTypeId值
		 * @return proinfoList：根据productTypeId（类别id）查询表shop_productinfo表得到productinfo信息
		 */
		public List selectProductInfoByProducttypetid(int productTypeId) ;
		
		/**
		 *  初始化分类菜单
		 * @return categoryMap
		 * @throws IOException
		 */
		public List initProdutTypeInfo() throws IOException;
		
		
		
		
}
