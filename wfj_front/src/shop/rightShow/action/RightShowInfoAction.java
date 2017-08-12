package shop.rightShow.action;
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
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.rightShow.pojo.RightShowInfo;
import shop.rightShow.pojo.RightShowType;
import shop.rightShow.service.IRightShowInfoService;
import shop.rightShow.service.IRightShowTypeService;
import util.action.BaseAction;
/**
 * 首页右面模块信息action的操作
 * @author 张攀攀、ss
 */
@SuppressWarnings("serial")
public class RightShowInfoAction extends BaseAction {
	private IRightShowInfoService rightShowInfoService;//右侧信息Service
	private IRightShowTypeService rightShowTypeService;//右侧分类Service
	private IProductInfoService productInfoService;//商品信息Service
	private IProductTypeService productTypeService;//商品分类Service
	private IBrandService brandService;//品牌信息Service
	private IBrandTypeService brandTypeService;//品牌和分类Service
	private List<Brand> brandList = new ArrayList<Brand>();//品牌List
	private RightShowInfo rightShowInfo;//右侧信息类
	private List<RightShowInfo> rightShowInfoList = new ArrayList<RightShowInfo>();//右侧信息List
	private String rightShowInfoId;//右侧信息ID
	private String rightShowTypeId;//右侧信息分类ID
	private String type;//类型（1、商品；2、品牌；3、品种）
	private String ids;
	private String showThingId;//展示ID（商品、品种、品牌的ID）
	private Integer productTypeId;
	private String params;
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();//商品信息List
	private List<ProductType> productTypeList = new ArrayList<ProductType>();//商品分类List
	private List<String> brands;//提交多个品牌信息 格式：“brandId_brandName”
	private List<String> productInfos;//提交多个商品信息 格式：“productId_productName”
	private List<String> sorts;//排序
	private List<String> kinds;//提交多个品种信息 格式：“kindId_kindName”
	//查询商品列表
	public String findProducts() throws IOException{
		 String hql = "";
		 List<ProductInfo> productList = new ArrayList<ProductInfo>();
		 if(productTypeId != null && !"".equals(productTypeId.toString())){
			 productList = new ArrayList<ProductInfo>();
			 String pros = "(";
			 hql = " where o.productId in "+pros+" order by o.productId";
		 }
		if(params != null && !"".equals(params)){
			String[] value = params.split("_");
			hql = " where o.productName like '%"+value[1]+"%'";
		}
		int totalRecordCount = productInfoService.getCount(hql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		productList = productInfoService.findObjects(hql);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", productList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
		return SUCCESS;
	}
	//获得品牌对象信息
	public void getRightShowBrandObject() throws IOException{
		Brand brand = (Brand)brandService.getObjectByParams(" where o.brandId="+showThingId);
		response.setContentType("text/html;charset=utf-8");
		JSONObject jo = JSONObject.fromObject(brand);
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//删除产品展示信息
	public void deleteRightShowInfo(){
		rightShowInfoService.deleteObjectsByIds("rightShowInfoId", ids);
	}
	//保存展示信息
	public String saveOrUpdateRightShowInfo(){
		List <String> list=new ArrayList<String>();
		if(sorts!=null && sorts.size()>0){
			for(String sort:sorts){//去掉数组中空字符串值
				if(sort!=null&&!"".equals(sort))
				list.add(sort);
			}
			if("1".equals(type)){//商品
				if(productInfos!=null && productInfos.size()>0){
					for(int i=0;i<list.size();i++){//批量添加
						RightShowInfo rightShowInfo=new RightShowInfo();
						rightShowInfo.setRightShowTypeId(Integer.parseInt(rightShowTypeId));
						String [] mbis=productInfos.get(i).split("_");
						rightShowInfo.setShowThingId(Integer.parseInt(mbis[0]));
						rightShowInfo.setFlagNo(Integer.parseInt(type));
						rightShowInfo.setSort(Integer.parseInt(list.get(i)));
						rightShowInfoService.saveOrUpdateObject(rightShowInfo);
					}
				}
				return "product";
			}else if("2".equals(type)){//品牌
				if(brands!=null && brands.size()>0){
					for(int i=0;i<list.size();i++){//批量添加
						RightShowInfo rightShowInfo=new RightShowInfo();
						rightShowInfo.setRightShowTypeId(Integer.parseInt(rightShowTypeId));
						String [] mbis=brands.get(i).split("_");
						rightShowInfo.setShowThingId(Integer.parseInt(mbis[0]));
						rightShowInfo.setFlagNo(Integer.parseInt(type));
						rightShowInfo.setSort(Integer.parseInt(list.get(i)));
						rightShowInfoService.saveOrUpdateObject(rightShowInfo);
					}
				}
				return "brand";
			}else if("3".equals(type)){//品种
				if(kinds!=null && kinds.size()>0){
					for(int i=0;i<list.size();i++){//批量添加
						RightShowInfo rightShowInfo=new RightShowInfo();
						rightShowInfo.setRightShowTypeId(Integer.parseInt(rightShowTypeId));
						String [] mbis=kinds.get(i).split("_");
						rightShowInfo.setShowThingId(Integer.parseInt(mbis[0]));
						rightShowInfo.setFlagNo(Integer.parseInt(type));
						rightShowInfo.setSort(Integer.parseInt(list.get(i)));
						rightShowInfoService.saveOrUpdateObject(rightShowInfo);
					}
				}
				return "kind";
			}
		}
		return null;
	}
	//首页右面模块展示品牌列表
	public void listRightShowProductInfo() throws IOException{
		//品牌展示列表
		int totalRecordCount = rightShowInfoService.getCount(" where o.rightShowTypeId="+rightShowTypeId);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		rightShowInfoList = rightShowInfoService.findListByPageHelper(null,pageHelper, " where o.rightShowTypeId="+rightShowTypeId+" order by o.rightShowInfoId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", rightShowInfoList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//首页右面模块展示品牌列表
	public void listRightShowBrandInfo() throws IOException{
		//品牌展示列表
		int totalRecordCount = rightShowInfoService.getCount(" where o.rightShowTypeId="+rightShowTypeId.substring(0, 1));
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		rightShowInfoList = rightShowInfoService.findListByPageHelper(null,pageHelper, " where o.rightShowTypeId="+rightShowTypeId.substring(0, 1)+" order by o.rightShowInfoId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", rightShowInfoList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//首页模块展示信息后台初始化页面
	public String gotoRightShowInfoPage(){
		//右面要展示的模块查询出来
		List<RightShowType> rightShowTypeList = rightShowTypeService.findObjects(" where o.parentId=0");
		session.setAttribute("rightShowTypeList", rightShowTypeList);
		return SUCCESS;
	}
	//模块信息列表
	public String gotoRightShowInfoListPage(){
		productTypeList = productTypeService.findObjects(" where o.parentId not in(0)");
		//type类型为选择的是哪一种类型的。值为1表示展示商品，2为展示品牌，3为展示品种,后继还可以加
		if(type!=null && !"".equals(type)){
			if(type.equals("1")){
				productInfoList = productInfoService.findObjects(null);
				return "product";
			}else if(type.equals("2")){
				//查询所有品牌
				brandList = brandService.findObjects(null);
				return "brand";
			}else if(type.equals("3")){
				//查询所有品种
				return "kind";
			}
		}
		return null;
	}
	//根据分类查询品牌
	public void listBrand() throws IOException{
		String productTypeId = request.getParameter("ptId");
		String hql = "";
		if("-1".equals(productTypeId)){
			hql = " order by o.brandId";
			brandList = brandService.findObjects(hql);
		}else{
			List<BrandType> brandTypeList = brandTypeService.findObjects(" where o.productTypeId="+productTypeId);
			if(brandTypeList!=null && brandTypeList.size()>0){
				for(BrandType bt : brandTypeList){
					Brand brand = (Brand) brandService.getObjectByParams(" where o.brandId="+bt.getBrandId());
					brandList.add(brand);
				}
			}
		}
		JSONArray jo = JSONArray.fromObject(brandList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据条件查询商品信息
	public void listProductInfo() throws IOException{
		String productTypeId = request.getParameter("ptId");
		String hql = "";
		if("-1".equals(productTypeId)){
			hql = " order by o.productId";
			productInfoList = productInfoService.findObjects(hql);
		}else{
		}
		JSONArray jo = JSONArray.fromObject(productInfoList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//商品分类查询
	public String productSplit(){
		session.setAttribute("rightShowTypeId", rightShowTypeId);
		return SUCCESS;
	}
	//跳转左侧页面
	public String leftShowTypeList(){
		return SUCCESS;
	}
	public RightShowInfo getRightShowInfo() {
		return rightShowInfo;
	}
	public void setRightShowInfo(RightShowInfo rightShowInfo) {
		this.rightShowInfo = rightShowInfo;
	}
	public List<RightShowInfo> getRightShowInfoList() {
		return rightShowInfoList;
	}
	public void setRightShowInfoList(List<RightShowInfo> rightShowInfoList) {
		this.rightShowInfoList = rightShowInfoList;
	}
	public String getRightShowInfoId() {
		return rightShowInfoId;
	}
	public void setRightShowInfoId(String rightShowInfoId) {
		this.rightShowInfoId = rightShowInfoId;
	}
	public void setRightShowInfoService(IRightShowInfoService rightShowInfoService) {
		this.rightShowInfoService = rightShowInfoService;
	}
	public String getRightShowTypeId() {
		return rightShowTypeId;
	}
	public void setRightShowTypeId(String rightShowTypeId) {
		this.rightShowTypeId = rightShowTypeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public List<Brand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
	public void setRightShowTypeService(IRightShowTypeService rightShowTypeService) {
		this.rightShowTypeService = rightShowTypeService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getShowThingId() {
		return showThingId;
	}
	public void setShowThingId(String showThingId) {
		this.showThingId = showThingId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}
	public List<String> getBrands() {
		return brands;
	}
	public void setBrands(List<String> brands) {
		this.brands = brands;
	}
	public List<String> getSorts() {
		return sorts;
	}
	public void setSorts(List<String> sorts) {
		this.sorts = sorts;
	}
	public List<String> getProductInfos() {
		return productInfos;
	}
	public void setProductInfos(List<String> productInfos) {
		this.productInfos = productInfos;
	}
	public List<String> getKinds() {
		return kinds;
	}
	public void setKinds(List<String> kinds) {
		this.kinds = kinds;
	}
}
