package phone.service.imp;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;
import phone.dao.IPhoneCategoryhibDao;
import phone.service.IPhoneCategoryhibService;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class PhoneCategoryhibService implements IPhoneCategoryhibService {



	private IPhoneCategoryhibDao phoneCategoryHibDao;

	public void setPhoneCategoryHibDao(IPhoneCategoryhibDao phoneCategoryHibDao) {
		this.phoneCategoryHibDao = phoneCategoryHibDao;
	}

//	 private IPhonecategoryDao phoneCategoryHibDao;
//	
//	 public void setPhoneCategoryHibDao(IPhonecategoryDao phoneCategoryHibDao)
//	 {
//	 this.phoneCategoryHibDao = phoneCategoryHibDao;
//	 }

	/**
	 * 
	 * @param brandId:品牌id
	 * @return 根据品牌id查询出的商品信息
	 */
	public List<ProductInfo> proInfoListByBrandId(int brandId){
		List<ProductInfo> list=phoneCategoryHibDao.proInfoListByBrandId(brandId);
		return list;
	}
	/**
	 * @param keyword:商品名关键字
	 * @return 根据商品名关键字查询所有商品列
	 */
	public List<ProductInfo> mohuSearch(String keyword){
		List<ProductInfo> list=phoneCategoryHibDao.mohuSearch(keyword);
		return list;
	}
	
	/**
	 * @param keyword:商品名关键字
	 * @param orderBy:排序方式
	 * @return 根据商品名关键字查询并按照指定标准排序后的所有商品列
	 */
	public List<ProductInfo> mohuSearchOrderBy(String keyword,String orderBy){
		List<ProductInfo> list=phoneCategoryHibDao.mohuSearchOrderBy(keyword, orderBy);
		return list;
	}
	/**
	 * 
	 * @return 推荐商品列表
	 */
	public List<ProductInfo> recommandPro(){
		List<ProductInfo> list=phoneCategoryHibDao.recommandPro();
		return list;
	}
	
	/**
	 * 
	 * @param productId
	 *            :shop_productinfo表的productId值
	 * @return proinfoList：根据productId（产品id）查询表shop_productinfo表得到productinfo信息
	 */

	public JSONObject selectProductInfoByProductid(int productId) {
		List<ProductInfo> idlist = phoneCategoryHibDao.selectProductInfoByProductid(
				productId);
		JSONObject jo = new JSONObject();
		if (idlist != null && idlist.size() > 0) {
			jo.accumulate("status", true);
		}else{
			jo.accumulate("status", false);
		}
		jo.accumulate("Data", idlist);
		return jo;
	}

	 /**
	  * 
	  * @param producttypeId:产品类别id
	  * @param orderBy：产品列表排序标准
	  * @return 按标准排序的商品list
	  */
	 public List selectProInfoByTypeId(int producttypeId, String orderBy){
		 List list=phoneCategoryHibDao.selectProInfoByTypeId(producttypeId, orderBy);
			return list;
	 }

	/**
	 * 初始化分类菜单
	 * 
	 * @return categoryMap
	 * @throws IOException
	 */
	public List<ProductType> initProdutType() throws IOException{
		List<ProductType> initlist = phoneCategoryHibDao.initProdutTypeInfo();
		return initlist;
	}
 
	
	/**
	  * 
	  * @param productTypeId
	  * @return secondProductTypeList:根据一级分类Id获取的二级分类list
	  */
	public List listCategory(int productTypeId){
		List list=phoneCategoryHibDao.listCategory(productTypeId);
		return list;
		
	}
	
	/**
	  * 
	  * @param productTypeId
	  * @return 根据二级id查询三级分类的list
	  */
	 public List<ProductType> listSanCategory(int productTypeId){
		 List list=phoneCategoryHibDao.listSanCategory(productTypeId);
			return list;
	 }


}
