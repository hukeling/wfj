package shop.product.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phone.util.JsonIgnore;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.product.pojo.Brand;
import shop.product.pojo.BrandInfo;
import shop.product.pojo.BrandType;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandInfoService;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
/**
 * 品牌详情Action
 */
@SuppressWarnings("serial")
public class BrandInfoAction extends BaseAction {
	private IBrandInfoService brandInfoService;//品牌详情Service 
	private Integer brandId;//品牌ID
	private String brandStory;//品牌故事
	private String brandCountry;//品牌所属国家
	
	 
	/**
	 * 根据品牌ID查询品牌详情信息 
	 * @throws IOException 
	 */
	 public  void selectBrandInfoById() throws IOException{
		 BrandInfo bi=brandInfoService.selectBrandInfoById(brandId);
		 JsonIgnore.outputobj(response, bi);
	 }

	 /**
	  * 添加/修改品牌详情信息Integer brandId,String brandStory,String brandCountry
	  * @param brandInfoService
	  * @throws Exception 
	  */
	 public void saveOrUpdatebrandInfo() throws Exception{
		if(brandCountry!=null&&!brandCountry.isEmpty()){
			brandCountry=new String(request.getParameter("brandCountry").getBytes("ISO-8859-1"),"UTF-8");
		}
		if(brandStory!=null&&!brandStory.isEmpty()){
			brandStory=new String(request.getParameter("brandStory").getBytes("ISO-8859-1"),"UTF-8");
		}
		Map strMap=new HashMap();
		strMap.put("brandId", brandId);
		strMap.put("brandStory", brandStory);
		strMap.put("brandCountry", brandCountry);
		boolean flag=brandInfoService.saveOrUpdatebrandInfo(strMap);
	 }
	 
	 /**
	  * 删除品牌详情信息
	  * @param brandInfoService
	  */
	public void deleteBrandInfoById() throws IOException {
		boolean flag = brandInfoService.deleteBrandInfoById(brandId);
		
	}
	 
	public void setBrandInfoService(IBrandInfoService brandInfoService) {
		this.brandInfoService = brandInfoService;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandStory(String brandStory) {
		this.brandStory = brandStory;
	}

	public void setBrandCountry(String brandCountry) {
		this.brandCountry = brandCountry;
	}

	
	 


 
	 
	 
}
