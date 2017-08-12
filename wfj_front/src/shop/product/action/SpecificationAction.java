package shop.product.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.product.pojo.ProductType;
import shop.product.pojo.Specification;
import shop.product.pojo.SpecificationValue;
import shop.product.service.IProductTypeService;
import shop.product.service.ISpecificationService;
import shop.product.service.ISpecificationValueService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * SpecificationAction - 商品规格Action类
 */
@SuppressWarnings({ "serial", "unused" })
public class SpecificationAction extends BaseAction{
	private ISpecificationService specificationService;//商品规格Service
	private ISpecificationValueService specificationValueService;//商品规格值service
	private IProductTypeService productTypeService;//商品分类Service
	private List<Specification> specificationList = new ArrayList<Specification>();//商品规格List
	private List<SpecificationValue> specificationValueList = new ArrayList<SpecificationValue>();//商品规格值List
	private List<ProductType> productTypeList = new ArrayList<ProductType>();//商品分类List
	private List<ProductType> sendProductTypeList = new ArrayList<ProductType>();//商品二级分类List
	private Specification specification;//商品规格
	private String specificationId;//商品规格ID
	private String ids;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**商品扩展属性action**/
	private ProductAttributeAction productAttributeAction;
	private Map<Object,Object> map = new HashMap<Object,Object>();
	public String gotoTestPage(){
		productTypeList = productTypeService.findObjects(" where o.parentId not in(0)");
		return SUCCESS;
	}
	public String findSpecificationByProductTypeId(){
		String productTypeId = request.getParameter("productTypeId");
		List<Specification> specificationList = specificationService.findObjects(" where o.productTypeId='"+productTypeId+"'");
		for(Specification sp : specificationList){
			List<SpecificationValue> specificationValueList = specificationValueService.findObjects(" where o.specificationId='"+sp.getSpecificationId()+"'");
			map.put(sp, specificationValueList);
		}
		return SUCCESS;
	}
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_specification");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转到商品规格列表页面
	public String gotoSpecificationPage(){
		List<ProductType> oldProdTypeList = productTypeService.findObjects(" where o.parentId=1");//一级分类
		for(ProductType prodType:oldProdTypeList){
			productTypeList.add(prodType);//添加一级分类
			int i=productTypeList.indexOf(prodType);//获取当前一级下标
			List<ProductType> listProductType2=productTypeService.findObjects(" where o.parentId="+prodType.getProductTypeId());//所属二级
			for(ProductType prodType2:listProductType2){
				List<ProductType> listProductType3=productTypeService.findObjects(" where o.parentId="+prodType2.getProductTypeId());//所属三级
				productTypeList.add(i+1, prodType2);//添加一级下的二级分类
				int j=productTypeList.indexOf(prodType2);//获取当前二级分类的下标
				for(ProductType prodType3:listProductType3){
					productTypeList.add(j+1, prodType3);//添加二级下的三级分类
				}
			}
		}
		return SUCCESS;
	}
	//查询所有的商品规格
	public void listSpecification() throws IOException{
//		String selectFlag=request.getParameter("selectFlag");
//		StringBuffer hqlsb = new StringBuffer();
//		if("true".equals(selectFlag)){//判断是否点击查询按钮
//			if(!"-1".equals(request.getParameter("customerId"))){
//				StringBuffer sb=CreateWhereSQLForSelect.appendEqual("customerId",request.getParameter("customerId"));
//				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
//			}
//		}
//		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" customerId desc"));
		int totalRecordCount = specificationService.getCount(" order by o.specificationId desc");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		specificationList = specificationService.findListByPageHelper(null,pageHelper, " order by o.specificationId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", specificationList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//查询商品分类
	public void getPorductTypeList(){
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
			e.printStackTrace();
		}
	}
	//保存或者修改商品规格
	public void saveOrUpdateSpecification() throws IOException{
		String image = "";
		if(specification!=null){
			specification = (Specification) specificationService.saveOrUpdateObject(specification);
			List<String> list = new ArrayList<String>();
			List<String> iamgeList = new ArrayList<String>();
			String parameter = request.getParameter("info");//规格值信息(文本)
			String imageUrl = request.getParameter("imageUrl");
			if(parameter!=null && !"".equals(parameter)){
				if(imageUrl!=null && !"".equals(imageUrl)){//规格值信息(图片前台代码已注释不再使用)
					String[] splitImage = imageUrl.split(",");
					for(int i=1;i<splitImage.length;i++){
						String[] subSplitImage = splitImage[i].split("_");
						iamgeList.add(subSplitImage[1].replace(","," "));
					}
				}
				String[] split = parameter.split("@");//将规格值分割
				for(int i=0;i<split.length;i++){
					if(iamgeList.size()>0){
						image = image+split[i]+","+iamgeList.get(i)+"@";
					}else{
						image = image+split[i]+"@";
					}
				}
				split = image.split("@");
				for(int i=0;i<split.length;i++){
					if(!split[i].contains("undefined,undefined")){
						list.add(split[i]);
					}
				}
				for(String s : list){
					SpecificationValue sv = new SpecificationValue();
					String[] split2 = s.split(",");
					sv.setSpecificationId(specification.getSpecificationId());
					sv.setSort(Integer.parseInt(split2[0]));
					sv.setName(split2[1]);
					if(iamgeList.size()>0){
						sv.setImage(split2[2]);
					}
					specificationValueService.saveOrUpdateObject(sv);
				}
			}
			if(specification.getSpecificationId()!=null){
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
	//得到一条商品规格记录
	public void getSpecificationInfo() throws IOException{
		specification = (Specification)specificationService.getObjectByParams(" where o.specificationId='"+specificationId+"'");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		//jsonMap=productAttributeAction.getThreeLevelProdType(specification.getProductTypeId());
		jsonMap.put("specification", specification);
		//根据规格的ID查规格值
		specificationValueList=(List<SpecificationValue>)specificationValueService.findObjects(" where o.specificationId="+specification.getSpecificationId());
		jsonMap.put("specificationValueList", specificationValueList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除商品规格
	public void deleteSpecification() throws IOException{
		Boolean isSuccess = specificationService.deleteObjectsByIds("specificationId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据商品规格ID查询此ID下的商品规格值列表页面
	public String findSpecificationValueListBySpecificationId(){
		request.setAttribute("specificationId",specificationId);
		return SUCCESS;
	}
	//根据规格ID查找规格值
	public void listSpecificationValueListBySpecificationId() throws IOException{
		int totalRecordCount = specificationValueService.getCount(" where o.specificationId='"+specificationId+"' order by o.specificationValueId");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		specificationValueList = specificationValueService.findListByPageHelper(null,pageHelper, " where o.specificationId='"+specificationId+"' order by o.specificationValueId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", specificationValueList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据父ID查找商品分类
	public void findProductTypeListByParentId() throws IOException{
		String parentProductTypeId = request.getParameter("parentProductTypeId");
		sendProductTypeList = productTypeService.findObjects(" where o.parentId='"+parentProductTypeId+"'");
		JSONArray ja = JSONArray.fromObject(sendProductTypeList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(ja.toString());
		out.flush();
		out.close();
	}
	public List<Specification> getSpecificationList() {
		return specificationList;
	}
	public void setSpecificationList(List<Specification> specificationList) {
		this.specificationList = specificationList;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public Specification getSpecification() {
		return specification;
	}
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
	public String getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setSpecificationService(ISpecificationService specificationService) {
		this.specificationService = specificationService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
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
	public void setSpecificationValueService(
			ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
	}
	public List<SpecificationValue> getSpecificationValueList() {
		return specificationValueList;
	}
	public void setSpecificationValueList(
			List<SpecificationValue> specificationValueList) {
		this.specificationValueList = specificationValueList;
	}
	public List<ProductType> getSendProductTypeList() {
		return sendProductTypeList;
	}
	public void setSendProductTypeList(List<ProductType> sendProductTypeList) {
		this.sendProductTypeList = sendProductTypeList;
	}
	public ProductAttributeAction getProductAttributeAction() {
		return productAttributeAction;
	}
	public void setProductAttributeAction(
			ProductAttributeAction productAttributeAction) {
		this.productAttributeAction = productAttributeAction;
	}
	public Map<Object, Object> getMap() {
		return map;
	}
	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}
}
