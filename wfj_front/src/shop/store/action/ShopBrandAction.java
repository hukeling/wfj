package shop.store.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.product.service.IBrandService;
import shop.store.pojo.ShopBrand;
import shop.store.service.IShopBrandService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/** 
 * ShopBrandAction - 店铺品牌关联表action类
 * @author 孟琦瑞
 */
public class ShopBrandAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**店铺品牌关联表service**/
	private IShopBrandService shopBrandService;
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**品牌service**/
	private IBrandService brandService;
	/**店铺品牌已关联的List**/
	@SuppressWarnings("rawtypes")
	private List<Map> shopBrandList=new ArrayList<Map>();
	/**没有关联的店铺List**/
	@SuppressWarnings("rawtypes")
	private List<Map> shopInfoList=new ArrayList<Map>();
	/**没有关联的品牌List**/
	@SuppressWarnings({"rawtypes" })
	private List<Map> brandList=new ArrayList<Map>();
	/**店铺商品关联表对象**/
	private ShopBrand shopBrand = new ShopBrand();
	/**店铺品牌关联表的ids**/
	private String ids;
	/**店铺Id**/
	private Integer shopInfoId;
	/****************************end********************************************/
	//跳转
	public String gotoShopBrandPage(){
		String shopHql="select b.shopInfoId as shopInfoId ,b.shopName as shopName from ShopInfo b where  b.isPass=3 and b.isClose=0 ";
		String brandHql="select b.brandId as brandId ,b.brandName as brandName from Brand b where b.isShow=1 ";
		shopInfoList=shopInfoService.findListMapByHql(shopHql);
		brandList=brandService.findListMapByHql(brandHql);
		return SUCCESS;
	}
	//查询店铺品牌关联 
	public void listShopBrand() throws IOException{
		String hql="select b.shopBrandId as shopBrandId , a.shopName as shopName , c.brandName as brandName from  ShopInfo a , ShopBrand b ,Brand c where   c.brandId=b.brandId and b.shopInfoId=a.shopInfoId";
		String coutnHql="select count(*) from  ShopInfo a , ShopBrand b ,Brand c where   c.brandId=b.brandId and b.shopInfoId=a.shopInfoId";
		int totalRecordCount=shopBrandService.getMultilistCount(coutnHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		shopBrandList=shopBrandService.findListMapPage(hql, pageHelper);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", shopBrandList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存
	public void save() throws IOException{
		String shopInfoId = request.getParameter("shopSelect");
		String brandId=request.getParameter("brandSelect");
		shopBrand.setShopInfoId(Integer.parseInt(shopInfoId));
		shopBrand.setBrandId(Integer.parseInt(brandId));
		shopBrand=(ShopBrand) shopBrandService.saveOrUpdateObject(shopBrand);
		if(shopBrand.getShopBrandId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//删除
	public void delete() throws IOException{
		Boolean isSuccess = shopBrandService.deleteObjectsByIds("shopBrandId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 点击店铺查找店铺和品牌中间表，查看店铺已选的品牌
	 */
	@SuppressWarnings("unchecked")
	public void findShopBrand(){
		try {
			String brandHql="select b.brandId as brandId ,b.brandName as brandName from Brand b where b.isShow=1 and b.brandId not in (select a.brandId as brandId from ShopBrand a where a.shopInfoId="+shopInfoId+")";
			brandList=brandService.findListMapByHql(brandHql);
			brandList = JSONArray.fromObject(brandList);
			JSONObject jo = new JSONObject();
			jo.accumulate("brandList",brandList);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
				out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * setter getter 
	 */
	public void setShopBrandService(IShopBrandService shopBrandService) {
		this.shopBrandService = shopBrandService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getShopBrandList() {
		return shopBrandList;
	}
	@SuppressWarnings("unchecked")
	public void setShopBrandList(List<Map> shopBrandList) {
		this.shopBrandList = shopBrandList;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getShopInfoList() {
		return shopInfoList;
	}
	@SuppressWarnings("unchecked")
	public void setShopInfoList(List<Map> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getBrandList() {
		return brandList;
	}
	@SuppressWarnings("unchecked")
	public void setBrandList(List<Map> brandList) {
		this.brandList = brandList;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
}
