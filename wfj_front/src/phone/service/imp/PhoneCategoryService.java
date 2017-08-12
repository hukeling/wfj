package phone.service.imp;

import java.io.IOException;
import java.util.List;
import net.sf.json.JSONObject;

import net.sf.json.JSONObject;
import phone.dao.IPhoneCategoryhibDao;
import phone.dao.IPhonecategoryDao;
import phone.dao.imp.PhoneCategoryhibDao;
import phone.dao.imp.PhonecategoryDao;
import phone.service.IPhoneCategoryService;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;

public class PhoneCategoryService implements IPhoneCategoryService {

	IPhonecategoryDao phoneCategoryDao=new PhonecategoryDao();
	
		public JSONObject selectProductInfoByProductid(int productId){
			List idlist = phoneCategoryDao
					.selectProductInfoByProductid(productId);
			JSONObject jo = new JSONObject();
			if (idlist != null && idlist.size() > 0) {
				jo.accumulate("Status", true);
			}else{
				jo.accumulate("Status", false);
			}
			jo.accumulate("Data", idlist);
			return jo;
		}


		/**
		 * 
		 * @param productTypeId
		 *            :shop_productinfo表的productTypeId值
		 * @return 
		 *         proinfoList：根据productTypeId（类别id）查询表shop_productinfo表得到productinfo信息
		 */
		public JSONObject selectProductInfoByProducttypetid(int productTypeId) {
			List<ProductInfo> typeIdList = phoneCategoryDao
					.selectProductInfoByProducttypetid(productTypeId);
			JSONObject jo = new JSONObject();
			
			if (typeIdList != null && typeIdList.size() > 0) {
				jo.accumulate("Status", true);
			}else{
				jo.accumulate("Status", false);
			}
			jo.accumulate("Data", typeIdList);
			return jo;
		}
	


	/**
	 * 初始化分类菜单
	 * 
	 * @return categoryMap
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public JSONObject initProdutTypeInfo() throws IOException {
		List<ProductInfo> initlist = phoneCategoryDao.initProdutTypeInfo();
		JSONObject jo = new JSONObject();
		if (initlist != null && initlist.size() > 0) {
			jo.accumulate("Status", true);
		} else {
			jo.accumulate("Status", false);
		}
		jo.accumulate("Data", initlist);
		return jo;
	}


}
