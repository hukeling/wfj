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

import shop.product.pojo.ProductAttribute;
import shop.product.pojo.ProductType;
import shop.product.service.IProductAttributeService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
/**
 * ProductAttributeAction - 商品的扩展属性类信息
 */
@SuppressWarnings("unused")
public class ProductAttributeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private ProductAttribute productAttribute;
	private IProductAttributeService productAttributeService;
	private IProductTypeService productTypeService;
	private String ids;
	/**商品分类的集合**/
	private List<ProductType> prodTypeList = new ArrayList<ProductType>();
	/**商品分类二级的集合**/
	private List<ProductType> prodTypeList2;
	/**商品分类三级的集合**/
	private List<ProductType> prodTypeList3;
	/**选择商品分类的一级选中的id**/
	private String parentId;
	/**二级选中的id**/
	private Integer parentId2;
	/**三级选中的id**/
	private Integer parentId3;
	private String prodTypeNames;
	/********************************end********************************************/
	public String gotoProductAttribute(){
		return SUCCESS;
	}
	/**查询所有的SHOP_商品扩展属性
	 * @throws IOException **/
	@SuppressWarnings("unchecked")
	public void findAllProductAttribute() throws IOException{
		String totalHql ="select count(a.productAttrId) from  ProductAttribute  a ,ProductType b where a.productTypeId=b.productTypeId";
		String sql ="select a.productAttrId as productAttrId,a.name as name,a.productTypeId as productTypeId,a.sort as sort," +
				" a.info as info,b.productTypeId as productTypeId from  ProductAttribute  a ,ProductType b " +
				" where a.productTypeId=b.productTypeId ";
		String paramName = (String) request.getParameter("paramName");
//		String prodType = (String) request.getParameter("prodType");
		if(StringUtils.isNotEmpty(paramName)){
			paramName = paramName.trim();
			totalHql+=" and a.name like '%"+paramName+"%'";
			sql+=" and a.name like '%"+paramName+"%'";
		}
		sql+=" order by a.productAttrId desc";
//		if(null!=prodType && !"-1".equals(prodType)){
//			totalHql+=" and a.productTypeId = "+prodType;
//			sql+=" and a.productTypeId = "+prodType;
//		}
		int totalRecordCount = productAttributeService.getMoreTableCount(totalHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> list=productAttributeService.findListMapPage(sql,pageHelper);
		List<Map> newLsit = new ArrayList<Map>();
		for(Map<String, Object> map:list){
			prodTypeNames="";
			getProductTypeName(Integer.parseInt(map.get("productTypeId").toString()));
			if(StringUtils.isNotEmpty(prodTypeNames)){
				map.put("sortName", prodTypeNames);
				newLsit.add(map);
			}
		}
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", newLsit);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 查询所有的分类
	 * @throws IOException
	 */
	public void findProductTypeList() throws IOException{
		List<ProductType> list=productTypeService.findObjects("where o.productTypeId>1");
		JSONArray ja = JSONArray.fromObject(list);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(ja.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 通过Id获取一条ProductAttribute
	 * @throws IOException
	 */
	public void getProductAttributeById() throws IOException{
		ProductAttribute productAttr = new ProductAttribute();
		productAttr = (ProductAttribute) productAttributeService.getObjectById(ids);//属性
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		getProductTypeName(productAttr.getProductTypeId());
		jsonMap.put("productAttr", productAttr);
		jsonMap.put("productTypeName", prodTypeNames);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 获取商品分类的三级连动的数据
	 * 所有的数据
	 * prodTypeId 子节点的id
	 */
	public Map<String,Object> getThreeLevelProdType(Integer prodTypeId){
		//三级节点
		ProductType productType3=(ProductType) productTypeService.getObjectById(prodTypeId.toString());//三级选中的id和父id
		//第二节点
		ProductType productType2 =null;
		productType2=(ProductType) productTypeService.getObjectById(productType3.getParentId().toString());//根据三级的父id 找到二级的选中的id
		prodTypeList3 = productTypeService.findObjects(" where o.parentId="+productType3.getParentId());//根据三级的父id查找三级的集合
		//一级节点
		ProductType productType1 = null;
		productType1=(ProductType) productTypeService.getObjectById(productType2.getParentId().toString());//根据二级的父id 找到一级的选中的id
		prodTypeList2 = productTypeService.findObjects(" where o.parentId="+productType1.getProductTypeId());//根据一级的id查找二级的集合
		prodTypeList = productTypeService.findObjects(" where o.parentId="+productType1.getParentId());//根据一级的id查找二级的集合
		parentId = productType1.getProductTypeId().toString();
		parentId2 = productType2.getProductTypeId();
		parentId3 = productType3.getProductTypeId();
		String productTypeName="&nbsp;&nbsp;"+productType1.getSortName()+"&nbsp;&nbsp;&gt&nbsp;&nbsp;"+productType2.getSortName()+"&nbsp;&nbsp;&gt&nbsp;&nbsp;"+productType3.getSortName();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("productTypeName", productTypeName);
		jsonMap.put("prodTypeList", prodTypeList);
		jsonMap.put("prodTypeList2", prodTypeList2);
		jsonMap.put("prodTypeList3", prodTypeList3);
		jsonMap.put("parentId", parentId);
		jsonMap.put("parentId2", parentId2);
		jsonMap.put("parentId3", parentId3);
		return jsonMap;
	}
	/***
	 * 根据分类的ID查找分类的名称
	 * @param productTypeId
	 * @return 所有级的名称的组拼
	 */
	public void getProductTypeName(Integer productTypeId){
			//获取当前的对象
			ProductType pt =  (ProductType) productTypeService.getObjectByParams(" where o.productTypeId="+productTypeId);
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
	 * 保存或修改扩展属性
	 * @throws IOException
	 */
	public void saveOrUpdateProductAttr() throws IOException{
		/*try {
			//属性内容的保存，先放入set 集合，然后再转变成json格式入库
			Set<String> a =  new TreeSet<String>();
			if(productAttribute!=null && productAttribute.getInfo()!=null){
				String info = productAttribute.getInfo();
				String[] infos=info.split(",");
				for(String value:infos){
					a.add(value);
				}
			}
			productAttribute.setInfo(JSONArray.fromObject(a).toString());
			if(null!=productAttribute){
				productAttribute=(ProductAttribute) productAttributeService.saveOrUpdateObject(productAttribute);
				if(productAttribute.getProductAttrId()!=null){
					JSONObject jo = new JSONObject();
					jo.accumulate("isSuccess", "true");
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					out.println(jo.toString());
					out.flush();
					out.close();
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		if(productAttribute!=null){
			productAttribute = (ProductAttribute) productAttributeService.saveOrUpdateObject(productAttribute);
			if(productAttribute.getProductAttrId()!=null){
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
	//删除记录
	public void deleteProductAttribute() throws IOException{
		Boolean isSuccess = productAttributeService.deleteObjectsByIds("productAttrId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public void setProductAttribute(ProductAttribute productAttribute) {
		this.productAttribute = productAttribute;
	}
	public void setProductAttributeService(
			IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public ProductAttribute getProductAttribute() {
		return productAttribute;
	}
	public List<ProductType> getProdTypeList() {
		return prodTypeList;
	}
	public void setProdTypeList(List<ProductType> prodTypeList) {
		this.prodTypeList = prodTypeList;
	}
	public List<ProductType> getProdTypeList2() {
		return prodTypeList2;
	}
	public void setProdTypeList2(List<ProductType> prodTypeList2) {
		this.prodTypeList2 = prodTypeList2;
	}
	public List<ProductType> getProdTypeList3() {
		return prodTypeList3;
	}
	public void setProdTypeList3(List<ProductType> prodTypeList3) {
		this.prodTypeList3 = prodTypeList3;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getParentId2() {
		return parentId2;
	}
	public void setParentId2(Integer parentId2) {
		this.parentId2 = parentId2;
	}
	public Integer getParentId3() {
		return parentId3;
	}
	public void setParentId3(Integer parentId3) {
		this.parentId3 = parentId3;
	}
}
