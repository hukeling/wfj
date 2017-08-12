package shop.product.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.product.pojo.ParamGroup;
import shop.product.pojo.ParamGroupInfo;
import shop.product.pojo.ProductParameters;
import shop.product.pojo.ProductType;
import shop.product.service.IProductParametersService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
/**
 * ProductParametersAction - 商品详细参数Action类
 * @author 孟琦瑞
 */
@SuppressWarnings({ "serial", "unused" })
public class ProductParametersAction extends BaseAction {
	private IProductParametersService productParametersService;//商品详细参数service
	private IProductTypeService productTypeService;//商品分类service
	private ProductType productType=new ProductType();//商品分类对象
	private List<ProductType> productTypeList=new ArrayList<ProductType>();//商品分类集合
	private List<ProductParameters> productParametersList=new ArrayList<ProductParameters>();//商品详细参数集合
	private ProductParameters productParameters = new ProductParameters();//商品详细参数对象
	private String productParametersId;//商品信息参数ID
	private String productTypeId;//商品信息参数ID
	private String ids;
	/**详细参数内容集合**/
	private List<ParamGroup> listParamGroup;
	/**详细内容明细集合**/
	private List<ParamGroupInfo> listParamGroupInfo;
	/**商品扩展属性action**/
	private ProductAttributeAction productAttributeAction;
	/**商品分类的集合**/
	private List<ProductType> prodTypeList = new ArrayList<ProductType>();
	/**所属分类名称**/
	private String prodTypeNames;
	/******************************end***********************************************/
	public String gotoProductParametersPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	public void listProductParameters() throws IOException{
		String totalHql="select count(a.productTypeId) from ProductParameters a ,ProductType b where a.productTypeId=b.productTypeId";
		String hql="select a.productParametersId as productParametersId ,a.name as name,a.sort as sort," +
				" b.sortName as sortName,b.productTypeId as productTypeId from ProductParameters a ,ProductType b where a.productTypeId=b.productTypeId";
		String paramName = (String) request.getParameter("paramName");
		//String prodType = (String) request.getParameter("prodType");
		if(StringUtils.isNotEmpty(paramName)){
			paramName = paramName.trim();
			totalHql+=" and a.name like '%"+paramName+"%'";
			hql+=" and a.name like '%"+paramName+"%'";
		}
//		if(null!=prodType && !"-1".equals(prodType)){
//			totalHql+=" and a.productTypeId = "+prodType;
//			hql+=" and a.productTypeId = "+prodType;
//		}
		hql+=" order by a.productParametersId desc";
		int totalRecordCount = productParametersService.getMoreTableCount(totalHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> list=productParametersService.findListMapPage(hql, pageHelper);
		List<Map> newLsit = new ArrayList<Map>();
		for(Map<String, Object> map:list){
			prodTypeNames="";
			getProductTypeName(Integer.parseInt(map.get("productTypeId").toString()));
			if(StringUtils.isNotEmpty(prodTypeNames)){
				map.put("sortName", prodTypeNames);
				newLsit.add(map);
			}
		}
		//productParametersList = productParametersService.findListByPageHelper(null,pageHelper, " order by o.sort ");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", newLsit);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateProductParameters() throws IOException{
		//将参数信息，归属到参数组中
		for(ParamGroup pg:listParamGroup ){
			List<ParamGroupInfo> listPGI=new ArrayList<ParamGroupInfo>();
			for(ParamGroupInfo pgi:listParamGroupInfo){
				if(pg.getParamGroupId().equals(pgi.getPgiId())){
					listPGI.add(pgi);
				}
			}
			pg.setParamGroupInfo(listPGI);
		}
		//格式化参数组信息
		JSONArray jbListPG = JSONArray.fromObject(listParamGroup);
		if(productParameters!=null){
			productParameters.setInfo(jbListPG.toString());
			productParameters = (ProductParameters) productParametersService.saveOrUpdateObject(productParameters);
			if(productParameters.getProductParametersId()!=null){
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
	//获取一条记录
	public void getPorductParametersInfo() throws IOException{
		productParameters = (ProductParameters)productParametersService.getObjectByParams(" where o.productParametersId='"+productParametersId+"'");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		getProductTypeName(productParameters.getProductTypeId());
		jsonMap.put("productParameters", productParameters);
		jsonMap.put("prodTypeNames",prodTypeNames);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//获取productTypeList
	public void getPorductTypeList() {
		productTypeList = (List<ProductType>)productTypeService.findObjects(" where o.parentId=1");
		try {
			JSONArray jo = JSONArray.fromObject(productTypeList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
			out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取productTypeName
	public void getPorductTypeName() throws IOException{
		productType = (ProductType)productTypeService.getObjectByParams(" where o.productTypeId='"+productTypeId+"'");
		JSONObject jo = JSONObject.fromObject(productType);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteProductParameters() throws IOException{
		Boolean isSuccess = productParametersService.deleteObjectsByIds("productParametersId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**查看数据库中是否已经配置此分类的参数***/
	public void getPorductParameters() throws IOException{
		productParameters = (ProductParameters)productParametersService.getObjectByParams("where o.productTypeId="+productTypeId);
		JSONObject jo = JSONObject.fromObject(productParameters);
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
		//获取当前的对象
		ProductType pt =  (ProductType) productTypeService.getObjectByParams(" where o.productTypeId="+prodTypeId);
		if(pt!=null){
			//加上超链接
			String aLabel =pt.getSortName();
			if(StringUtils.isNotEmpty(prodTypeNames)){
				prodTypeNames = aLabel +"&nbsp;&gt;&nbsp;"+prodTypeNames;
			}else{
				prodTypeNames = aLabel;
			}
		}
		//递归
		if(pt!=null && pt.getParentId()!=1){
			getProductTypeName(pt.getParentId());
		}
	}
	/**
	 * setter getter
	 * @return
	 */
	public IProductParametersService getProductParametersService() {
		return productParametersService;
	}
	public String getProdTypeNames() {
		return prodTypeNames;
	}
	public void setProdTypeNames(String prodTypeNames) {
		this.prodTypeNames = prodTypeNames;
	}
	public void setProductParametersService(
			IProductParametersService productParametersService) {
		this.productParametersService = productParametersService;
	}
	public List<ProductParameters> getProductParametersList() {
		return productParametersList;
	}
	public void setProductParametersList(
			List<ProductParameters> productParametersList) {
		this.productParametersList = productParametersList;
	}
	public ProductParameters getProductParameters() {
		return productParameters;
	}
	public void setProductParameters(ProductParameters productParameters) {
		this.productParameters = productParameters;
	}
	public void setProductAttributeAction(
			ProductAttributeAction productAttributeAction) {
		this.productAttributeAction = productAttributeAction;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getProductParametersId() {
		return productParametersId;
	}
	public void setProductParametersId(String productParametersId) {
		this.productParametersId = productParametersId;
	}
	public IProductTypeService getProductTypeService() {
		return productTypeService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public List<ParamGroup> getListParamGroup() {
		return listParamGroup;
	}
	public void setListParamGroup(List<ParamGroup> listParamGroup) {
		this.listParamGroup = listParamGroup;
	}
	public List<ParamGroupInfo> getListParamGroupInfo() {
		return listParamGroupInfo;
	}
	public void setListParamGroupInfo(List<ParamGroupInfo> listParamGroupInfo) {
		this.listParamGroupInfo = listParamGroupInfo;
	}
	public List<ProductType> getProdTypeList() {
		return prodTypeList;
	}
	public void setProdTypeList(List<ProductType> prodTypeList) {
		this.prodTypeList = prodTypeList;
	}
}
