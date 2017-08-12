package shop.product.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.product.pojo.Brand;
import shop.product.pojo.BrandType;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
/**
 * 品牌和分类Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class BrandTypeAction extends BaseAction {
	private IBrandTypeService brandTypeService;//品牌分类Service
	private IProductTypeService ProductTypeService;//商品分类Service
	private IBrandService brandService;//品牌Service
	private List<BrandType> brandTypeList = new ArrayList<BrandType>();//品牌分类List
	private List<ProductType> productTypeList = new ArrayList<ProductType>();//商品分类List
	private String brandId;
	private Integer [] productTypeIds;
	private BrandType brandType;
	private String productTypeId;
	/**品牌Id集合**/
	private Integer[] brandIds;
	//查选所有的分类和品牌已经选择的分类
	public String getProductTypeListByBrandId(){
		productTypeList = ProductTypeService.findObjects(null);
		brandTypeList = brandTypeService.findObjects(" where o.brandId='"+brandId+"'");
		request.setAttribute("brandId", brandId);
		return SUCCESS;
	}
	//给品牌添加分类
	public void saveBrandType(){
		brandId = brandType.getBrandId().toString();
		brandTypeList = brandTypeService.findObjects(" where o.brandId='"+brandId+"'");
		if(brandTypeList!=null && brandTypeList.size()>0){
			for(BrandType bt : brandTypeList){
				brandTypeService.deleteObjectByParams(" where o.brandTypeId='"+bt.getBrandTypeId()+"'");
			}
			if(productTypeIds!=null && productTypeIds.length>0){
				for(int i=0;i<productTypeIds.length;i++){
					BrandType brandType = new BrandType();
					brandType.setBrandId(Integer.parseInt(brandId));
					brandType.setProductTypeId(productTypeIds[i]);
					brandTypeService.saveOrUpdateObject(brandType);
				}
			}
		}else{
			if(productTypeIds!=null && productTypeIds.length>0){
				for(int i=0;i<productTypeIds.length;i++){
					BrandType brandType = new BrandType();
					brandType.setBrandId(Integer.parseInt(brandId));
					brandType.setProductTypeId(productTypeIds[i]);
					brandTypeService.saveOrUpdateObject(brandType);
				}
			}
		}
	}
	//根据分类查询品牌
	public String gotoBrandListByProductTypeId(){
		request.setAttribute("productTypeId",productTypeId);
		return SUCCESS;
	}
	//根据分类ID查询商品
	 public void listBrandByProductTypeId() throws IOException{
		List<Brand> brandList = new ArrayList<Brand>();
		List<BrandType> brandTypeList = brandTypeService.findObjects(" where o.productTypeId='"+productTypeId+"' order by o.productTypeId");
		int totalRecordCount = brandTypeService.getCount(" where o.productTypeId='"+productTypeId+"' order by o.productTypeId");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		for(BrandType bt : brandTypeList){
			Brand brand = (Brand) brandService.getObjectById(bt.getBrandId().toString());
			brandList.add(brand);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", brandList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	 }
	//删除商品和分类关系
	 public void deleteBrandType() throws IOException{
		Boolean isSuccess = brandTypeService.deleteObjectByParams(" where o.brandId='"+brandId+"'and o.productTypeId='"+productTypeId+"'");
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	 }
	 /**
	  * 查找所有品牌，除当前分类下已选择的
	  */
	public void findTypeBrand(){
		 try {
			 String hql = "select a.brandId as brandId,a.brandName  from shop_brand a  where a.brandId not in (select x.brandId as oldBrndId from shop_brandtype x where x.productTypeId="+productTypeId+")";
			 List<Map<String, Object>> list = brandService.findListMapBySql(hql);
			 JSONArray ja = JSONArray.fromObject(list);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			 out = response.getWriter();
			 out.println(ja.toString());
			 out.flush();
			 out.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	/**
	 * 批量保存品牌和分类关系
	 */
	public void saveMoreBrandType(){
		try {
			boolean flag = true;
			if(brandIds.length>0){
				flag = brandTypeService.saveMoreBrandType(brandIds, Integer.parseInt(productTypeId));
			}
			JSONObject ja = JSONObject.fromObject(flag);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.println(ja.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<BrandType> getBrandTypeList() {
		return brandTypeList;
	}
	public void setBrandTypeList(List<BrandType> brandTypeList) {
		this.brandTypeList = brandTypeList;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public Integer[] getProductTypeIds() {
		return productTypeIds;
	}
	public void setProductTypeIds(Integer[] productTypeIds) {
		this.productTypeIds = productTypeIds;
	}
	public BrandType getBrandType() {
		return brandType;
	}
	public void setBrandType(BrandType brandType) {
		this.brandType = brandType;
	}
	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		ProductTypeService = productTypeService;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public Integer[] getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(Integer[] brandIds) {
		this.brandIds = brandIds;
	}
}
