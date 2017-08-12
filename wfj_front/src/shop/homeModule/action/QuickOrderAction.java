package shop.homeModule.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductInfo;
import shop.product.service.IBrandService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * QuickOrderAction --快速下单Action
 * @author Administrator
 * 2014-06-23
 */
@SuppressWarnings("serial")
public class QuickOrderAction extends BaseAction {
	/**商品Service**/
	private IProductInfoService productInfoService;
	/**品牌Service**/
	private IBrandService brandService;
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**sku订货号**/
	private String sku;
	/**商品实体类**/
	private ProductInfo productInfo;
	/**品牌实体类**/
	private Brand brand;
	/**sku组**/
	private String skus;
	/**商品集合**/
	private List<Map> productInfoList  = new ArrayList<Map>();
	/**用于初始化头部样式**/
	private String headerType;
	//跳转到快速下单页面
	public String quickOrder(){
		return SUCCESS;
	}
	//根据SKU异步查询商品
	public void getProductInfoBySku() throws IOException{
		if(Utils.objectIsNotEmpty(sku)){
			productInfo = (ProductInfo) productInfoService.getObjectByParams(" where o.sku=('"+sku+"') and o.isPass=1 and o.isPutSale=2");
			pageHelper.setPageInfo(1, 1, 1);
			if(Utils.objectIsNotEmpty(productInfo)){
				brand = (Brand) brandService.getObjectByParams(" where o.brandId = "+productInfo.getBrandId());
				ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+productInfo.getShopInfoId()+" and o.isPass=3 and o.isClose=0");
				if(shopInfo==null){
					productInfo=null;
				}
			}
			Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
			jsonMap.put("productInfo", productInfo);
			jsonMap.put("brand", brand);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
			JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//批量添加sku
	@SuppressWarnings({"rawtypes" })
	public void listProductInfoBySku() throws IOException{
		if(Utils.objectIsNotEmpty(skus)){
			String sql = "select p.productId as productId,p.measuringUnitName as measuringUnitName,p.productFullName as productFullName,p.sku as sku,p.salesPrice as salesPrice,p.marketPrice as marketPrice,p.shopInfoId as shopInfoId,p.stockUpDate as stockUpDate,p.logoImg as logoImg,b.brandName as brandName"
					   + " from ProductInfo p,Brand b where p.brandId = b.brandId and p.sku in ("+skus+")";
			List<Map> productInfoList = productInfoService.findListMapByHql(sql);
			//String []selectColumns = {"productId","sku","describle","salesPrice","shopInfoId","stockUpDate"};
			//productInfoList = productInfoService.findObjects(selectColumns, " where o.sku in ("+skus+")");
			JSONArray ja = JSONArray.fromObject(productInfoList);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(ja.toString());
			out.flush();
			out.close();
		}
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public String getSkus() {
		return skus;
	}
	public void setSkus(String skus) {
		this.skus = skus;
	}
	public List<Map> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<Map> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}		
	
}
