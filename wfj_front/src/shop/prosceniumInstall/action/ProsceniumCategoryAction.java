package shop.prosceniumInstall.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.product.pojo.ProductType;
import shop.product.service.IProductTypeService;
import shop.prosceniumInstall.pojo.ProsceniumCategory;
import shop.prosceniumInstall.service.IProsceniumCategoryService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * ProsceniumCategoryAction - 前台中间部分分类Action类
 */
@SuppressWarnings("serial")
public class ProsceniumCategoryAction extends BaseAction{
	private IProsceniumCategoryService prosceniumCategoryService;//前台中间部分分类service
	private List<ProsceniumCategory> prosceniumCategoryList=new ArrayList<ProsceniumCategory>();//前台中间部分分类List
	private IProductTypeService productTypeService;//商品分类Service
	private List<ProductType> productTypeList=new ArrayList<ProductType>();//一级级分类List
	private List<ProductType> secProductTypeList=new ArrayList<ProductType>();//二级级分类List
	private ProsceniumCategory prosceniumCategory=new ProsceniumCategory();//前台中间部分分类对象
	private String prosceniumCategoryId;
	private String ids;
	/**商品分类的父Id**/
	private String parentId ;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**商品所属分类**/
	private String prodTypeNames;
	/*******************************end******************************************/
	//ajax异步上传图片
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf(".") + 1);
			if ("jpg".equals(typeStr) || "JPG".equals(typeStr) || "png".equals(typeStr) || "PNG".equals(typeStr) || "GIF".equals(typeStr) ||"gif".equals(typeStr) || "".equals(typeStr)) {
				String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_prosceniumCategory");
				jo.accumulate("photoUrl", otherImg);
				jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
			} else {
				jo.accumulate("photoUrl", "false2");
			}
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public String gotoProsceniumCategoryPage(){
		productTypeList=productTypeService.findObjects(" where o.parentId=1 and o.isRecommend=1 and o.isShow=1  order by sortCode");
		return SUCCESS;
	}
	//查询前台中间部分分类列表信息
	@SuppressWarnings("unchecked")
	public void listProsceniumCategory()throws IOException {
		int totalRecordCount = prosceniumCategoryService.getCount("order by o.sortCode ");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String sql ="select a.prosceniumCategoryId as prosceniumCategoryId,a.productTypeId as productTypeId,a.secondProductTypeId as secondProductTypeId,a.prosceniumCategoryUrl as prosceniumCategoryUrl,a.title as title," +
				" a.synopsis as synopsis,a.interlinkage as interlinkage,a.sortCode as sortCode,a.isShow as isShow,b.productTypeId as productTypeId from  ProsceniumCategory  a ,ProductType b " +
				" where a.productTypeId=b.productTypeId order by a.prosceniumCategoryId desc";
		List<Map> list=prosceniumCategoryService.findListMapPage(sql,pageHelper);
		List<Map> newLsit = new ArrayList<Map>();
		for(Map<String, Object> map:list){
			prodTypeNames="";
			getProductTypeName(Integer.valueOf(map.get("secondProductTypeId").toString()));
			map.put("sortName", prodTypeNames);
			newLsit.add(map);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", newLsit);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//添加或修改
	public void saveOrUpdateProsceniumCategory() throws IOException{
		if(prosceniumCategory!=null){
			ProductType oneproductType = (ProductType) productTypeService.getObjectByParams(" where o.isRecommend=1 and o.isShow=1 and o.productTypeId='"+prosceniumCategory.getProductTypeId()+"'");
			ProductType secondproductType = (ProductType) productTypeService.getObjectByParams(" where o.isRecommend=1 and o.isShow=1 and o.productTypeId='"+prosceniumCategory.getSecondProductTypeId()+"'");
			prosceniumCategory.setProductTypeName(oneproductType.getSortName());
			prosceniumCategory.setSecondProductTypeName(secondproductType.getSortName());
			prosceniumCategory = (ProsceniumCategory) prosceniumCategoryService.saveOrUpdateObject(prosceniumCategory);
			if(prosceniumCategory.getProsceniumCategoryId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	//获得一条记录
	public void getProsceniumCategoryInfo() throws IOException{
		//前台中间部分分类对象
		prosceniumCategory = (ProsceniumCategory) prosceniumCategoryService.getObjectByParams(" where o.prosceniumCategoryId='"+prosceniumCategoryId+"'");
		//该对象的一级分类
		ProductType productType = (ProductType) productTypeService.getObjectByParams(" where o.productTypeId="+prosceniumCategory.getProductTypeId());
		//该对象的二级分类
		ProductType productType2 = (ProductType) productTypeService.getObjectByParams(" where o.productTypeId="+prosceniumCategory.getSecondProductTypeId());
		//二级分类List
		List<ProductType> productTypeList2 = productTypeService.findObjects(" where o.parentId="+productType.getProductTypeId());
		//一级分类List
		productTypeList=productTypeService.findObjects(" where o.parentId=1 order by sortCode");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("prosceniumCategory", prosceniumCategory);
		jsonMap.put("productType", productType);
		jsonMap.put("productType2", productType2);
		jsonMap.put("productTypeList", productTypeList);
		jsonMap.put("productTypeList2", productTypeList2);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//查找分类
	public void findProductType() throws IOException{
		secProductTypeList = productTypeService.findObjects("where o.parentId="+parentId);
		JSONArray jo = JSONArray.fromObject(secProductTypeList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除
	public void deleteProsceniumCategory() throws IOException{
		Boolean isSuccess = prosceniumCategoryService.deleteObjectsByIds("prosceniumCategoryId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 获取当前分类名称（无级限）
	 */
	public  void getProductTypeName(Integer prodTypeId){
			String path = request.getContextPath();
			//获取当前的对象
			ProductType pt =  (ProductType) productTypeService.getObjectByParams(" where o.productTypeId="+prodTypeId);
			if(pt!=null){
				//加上超链接
				if(StringUtils.isNotEmpty(prodTypeNames)){
					prodTypeNames = pt.getSortName() +"&nbsp;&gt;&nbsp;"+prodTypeNames;
				}else{
					prodTypeNames = pt.getSortName();
				}
			}
			//递归
			if(pt!=null && pt.getParentId()!=1){
				getProductTypeName(pt.getParentId());
			}
	}
	/**
	 * setter getter
	 */
	public void setProsceniumCategoryService(
			IProsceniumCategoryService prosceniumCategoryService) {
		this.prosceniumCategoryService = prosceniumCategoryService;
	}
	public List<ProsceniumCategory> getProsceniumCategoryList() {
		return prosceniumCategoryList;
	}
	public void setProsceniumCategoryList(
			List<ProsceniumCategory> prosceniumCategoryList) {
		this.prosceniumCategoryList = prosceniumCategoryList;
	}
	public ProsceniumCategory getProsceniumCategory() {
		return prosceniumCategory;
	}
	public void setProsceniumCategory(ProsceniumCategory prosceniumCategory) {
		this.prosceniumCategory = prosceniumCategory;
	}
	public String getProsceniumCategoryId() {
		return prosceniumCategoryId;
	}
	public void setProsceniumCategoryId(String prosceniumCategoryId) {
		this.prosceniumCategoryId = prosceniumCategoryId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public File getImagePath() {
		return imagePath;
	}
	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePathFileName() {
		return imagePathFileName;
	}
	public void setImagePathFileName(String imagePathFileName) {
		this.imagePathFileName = imagePathFileName;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<ProductType> getSecProductTypeList() {
		return secProductTypeList;
	}
	public void setSecProductTypeList(List<ProductType> secProductTypeList) {
		this.secProductTypeList = secProductTypeList;
	}
	public String getProdTypeNames() {
		return prodTypeNames;
	}
	public void setProdTypeNames(String prodTypeNames) {
		this.prodTypeNames = prodTypeNames;
	}
}
