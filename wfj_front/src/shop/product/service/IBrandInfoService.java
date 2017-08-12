package shop.product.service;
import java.io.IOException;
import java.util.Map;

import shop.product.pojo.BrandInfo;
import shop.product.pojo.BrandType;
import util.service.IBaseService;
/**
 * 品牌详情Service接口
 *
 */
public interface IBrandInfoService  extends IBaseService <BrandInfo> {
	/**
	 * 根据品牌ID查询品牌详情信息 
	 * @throws IOException 
	 */
	 BrandInfo selectBrandInfoById(Integer brandId) throws IOException;
	 /**
	  * 添加/修改品牌详情信息Integer brandId,String brandStory,String brandCountry
	  * @param 
	  * @throws Exception 
	  */
	 boolean saveOrUpdatebrandInfo(Map strMap) throws Exception;
	 /**
	  * 删除品牌详情信息
	  * @param brandInfoService
	  */
	 boolean deleteBrandInfoById(Integer brandId) throws IOException;
	 
}
