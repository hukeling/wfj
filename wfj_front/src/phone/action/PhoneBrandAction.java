package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import phone.service.IPhoneCategoryhibService;
import phone.util.JsonIgnore;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.product.pojo.Brand;
import shop.product.pojo.Brand2;
import shop.product.pojo.BrandInfo;
import shop.product.pojo.ProductInfo;
import shop.product.service.IBrandInfoService;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductInfoService;
import util.action.BaseAction;

public class PhoneBrandAction extends BaseAction{

	private IBrandService brandService;// 品牌Servie
	private IBrandInfoService brandInfoService;//品牌详情service
	private IProductInfoService productInfoService;//商品详情service
	private IBrandTypeService brandTypeService;//品牌分类Service
	private IPhoneCategoryhibService phoneCategoryHibService;
	private int brandId;//品牌id
	private int productId;//商品id
	 
	/**
	 * 根据品牌id获取商品分类信息
	 */
	public void getProductTypeBybrandId(){}
	
	/**
	 * 根据brandId查询商品信息
	 * @return
	 * @throws IOException
	 */
		public String getProInfoByBrandId() throws IOException{
			Brand brand = (Brand) brandService.getObjectByParams(" where o.isShow=1 and o.brandId='"
					+ brandId + "'");
			request.setAttribute("brand", brand);
			List<ProductInfo> list=phoneCategoryHibService.proInfoListByBrandId(brandId);
			request.setAttribute("brandProinfoList", list);
			return SUCCESS;
		}
		public void getProductInfoByBrandId() throws IOException{
			List<ProductInfo> prolist=phoneCategoryHibService.proInfoListByBrandId(brandId);
			JsonIgnore.outputJo(response, prolist, JsonIgnore.getIgnoreProInfo2());
		}
		/**
		 * 根据品牌id查询商品类别信息
		 */
		public void getProductTypeByBrandId(){
			
		}
		/**
		 * 查询所有品牌信息（带品牌详情即品牌故事与品牌国家）
		 * @throws IOException 
		 */
		public void getAllBrands() throws IOException{
			List<Brand> brandList=new ArrayList<Brand>();
			brandList=brandService.findObjects("where o.isShow=1");
			List<Brand2> brand2List=new ArrayList<Brand2>();
			for(Brand bd:brandList){
				Brand2 bd2=new Brand2();
				BrandInfo bi=brandInfoService.selectBrandInfoById(bd.getBrandId());
				if(bi==null){
					bi=new BrandInfo();
				}
				bd2=this.getBrand2(bd, bi);
				brand2List.add(bd2);
			}
			JsonIgnore.output(response, brand2List);
		}
		/**
		 * 根据商品id查询品牌信息
		 * @return
		 */
		public Brand2 getBrandByProductId(){
			ProductInfo pi=(ProductInfo)productInfoService.getObjectByParams("where o.productId='"+productId+"'");
			brandId=pi.getBrandId();
			Brand bd=(Brand)brandService.findObjects("where o.isShow=1 and o.brandId='"+brandId+"'");
			BrandInfo bi=(BrandInfo)brandInfoService.findObjects("where o.brandId='"+brandId+"'");
			if(bi==null){
				bi=new BrandInfo();
			}
			Brand2 bd2=this.getBrand2(bd, bi);
			return bd2;
		}
		/**
		 * 根据所属国家查询品牌信息
		 */
		public void getBrandsByCountry(){
			
		}
		
		/**
		 * 拼接Brand和BrandInfo成为Brand2
		 * @return
		 */
		public Brand2 getBrand2(Brand bd,BrandInfo bi){
			Brand2 bd2=new Brand2();
			bd2.setBrandId(bd.getBrandId());
			bd2.setBrandName(bd.getBrandName());
			bd2.setTitle(bd.getTitle());
			bd2.setSynopsis(bd.getSynopsis());
			bd2.setBrandImageUrl(bd.getBrandImageUrl());
			bd2.setBrandBigImageUrl(bd.getBrandBigImageUrl());
			bd2.setSortCode(bd.getSortCode());
			bd2.setIsShow(bd.getIsShow());
			bd2.setIsRecommend(bd.getIsRecommend());
			bd2.setIsHomePage(bd.getIsHomePage());
			bd2.setBrandStory(bi.getBrandStory());
			bd2.setBrandCountry(bi.getBrandCountry());
			return bd2;
		}
		
		public int getBrandId() {
			return brandId;
		}

		public void setBrandId(int brandId) {
			this.brandId = brandId;
		}

		public void setBrandService(IBrandService brandService) {
			this.brandService = brandService;
		}

		public void setPhoneCategoryHibService(
				IPhoneCategoryhibService phoneCategoryHibService) {
			this.phoneCategoryHibService = phoneCategoryHibService;
		}
		public void setBrandInfoService(IBrandInfoService brandInfoService) {
			this.brandInfoService = brandInfoService;
		}
		public void setProductInfoService(IProductInfoService productInfoService) {
			this.productInfoService = productInfoService;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}

		public void setBrandTypeService(IBrandTypeService brandTypeService) {
			this.brandTypeService = brandTypeService;
		}
		
		 
	 
	 
	 
}
