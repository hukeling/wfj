package shop.product.service.imp;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import shop.product.dao.IBrandInfoDao;
import shop.product.dao.IBrandTypeDao;
import shop.product.pojo.BrandInfo; 
import shop.product.service.IBrandInfoService; 
import util.service.BaseService;
/**
 * 品牌详情Service接口实现类
 *
 */
public class BrandInfoService extends BaseService <BrandInfo> implements IBrandInfoService{
	@SuppressWarnings("unused")
	private IBrandInfoDao brandInfoDao;

	public void setBrandInfoDao(IBrandInfoDao brandInfoDao) {
		this.baseDao =this.brandInfoDao=brandInfoDao;
	}
	/**
	 * 根据品牌ID查询品牌详情信息 
	 * @throws IOException 
	 */
	 public  BrandInfo selectBrandInfoById(Integer brandId) throws IOException{
		 BrandInfo bi=new BrandInfo();
		 String sql="where o.brandId="+brandId;
		 bi=(BrandInfo)getObjectByParams(sql);
		 return bi;
	 }

	 /**
	  * 添加/修改品牌详情信息Integer brandId,String brandStory,String brandCountry
	  * @param brandInfoService
	 * @throws Exception 
	  */
	 public boolean saveOrUpdatebrandInfo(Map strMap) throws Exception{
		 Integer brandId=(Integer)strMap.get("brandId");
		 String brandCountry=(String)strMap.get("brandCountry");
		 String brandStory=(String)strMap.get("brandStory");
		 BrandInfo bi=(BrandInfo)getObjectByParams("where o.brandId='"+brandId+"'");
		 if(bi==null){
			bi=new BrandInfo();
			bi.setBrandId(brandId);
		 } 
		if(brandCountry!=null&&!brandCountry.isEmpty()){
			bi.setBrandCountry(brandCountry); 
		}
		if(brandStory!=null&&!brandStory.isEmpty()){
			bi.setBrandStory(brandStory);
		}
		 bi=(BrandInfo)saveOrUpdateObject(bi);
		 if(bi!=null){
			 return true;
		 }else{
			 return false;
		 }
	 }
	 
	 /**
	  * 删除品牌详情信息
	  * @param brandInfoService
	  */
	 public boolean deleteBrandInfoById(Integer brandId) throws IOException {
		return deleteObjectByParams("where o.brandId='"+brandId+"'");
		
	}
}