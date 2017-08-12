package shop.product.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.product.pojo.Brand;
import shop.product.pojo.ParamGroup;
import shop.product.pojo.ParamGroupInfo;
import shop.product.pojo.ProductAttribute;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductParameters;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IProductAttributeService;
import shop.product.service.IProductImgService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductParametersService;
import shop.product.service.IProductTypeService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.QRCode;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
/**
 * 商品Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class ProductInfoAction extends BaseAction {
	private IProductInfoService productInfoService;//商品Service
	private IBrandService brandService;//品牌Service
	private List<Map> productInfoList;//商品信息List
	private List<Brand> brandList = new ArrayList<Brand>();//品牌List
	/**商品类**/
	private ProductInfo productInfo;
	/**店铺Service**/
	private IShopInfoService shopInfoService;
	private Integer productId;
	private String ids;
	private String showplaces;//展示位置
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**商品分类的父Id**/
	private String parentId ;
	/** 商品分类service**/
	private IProductTypeService productTypeService;
	/**商品分类的集合**/
	private List<ProductType> prodTypeList = new ArrayList<ProductType>();
	/**选择的分类（子节点）**/
	private Integer productTypeId;
	/**属性service**/
	private IProductAttributeService productAttributeService;
	/**参数service**/
	private IProductParametersService productParametersService;
	/**商品分类的属性action**/
	private ProductAttributeAction productAttributeAction;
	/**详细参数内容集合**/
	private List<ParamGroup> listParamGroup;
	/**详细内容明细集合**/
	private List<ParamGroupInfo> listParamGroupInfo;
	/**商品图片对象**/
	@SuppressWarnings("unused")
	private ProductImg productImgObj=new ProductImg();
	/**商品图片service**/
	@SuppressWarnings("unused")
	private IProductImgService productImgService;
	/**店铺List**/
	private List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();
	/**商品分类名称**/
	private String prodTypeNames;
	/**区域Service**/
	private IAreaService areaService;
	//跳转到商品列表页面
	public String gotoProductInfoPage(){
		//得到店铺Lis
		shopInfoList = shopInfoService.findObjects(null);
		//得到品牌Lis
		brandList = brandService.findObjects(null);
		return SUCCESS;
	}
	/**
	 * 根据分类的父ID查询商品分类的集合
	 * 做连动的时候使用的
	 * @throws IOException 
	 */
	public void findProductType() throws IOException{
		prodTypeList = productTypeService.findObjects("where o.parentId="+parentId);
		JSONArray jo = JSONArray.fromObject(prodTypeList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
		//return "toAddPage";
	}
	/**
	 * 查询商品列表
	 * @author 刘钦楠
	 * @throws IOException
	 */
	public void listProductInfo() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hql = new StringBuffer(" select p.productId as productId,p.productFullName as productFullName,p.productCode as productCode,p.salesPrice as salesPrice,p.isPutSale as isPutSale,p.isPass as isPass,p.isRecommend as isRecommend,s.shopName as shopName from ProductInfo p,ShopInfo s where p.shopInfoId = s.shopInfoId ");
		StringBuffer hql1 = new StringBuffer(" select count(p) from ProductInfo p,ShopInfo s where p.shopInfoId = s.shopInfoId ");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String productCode = request.getParameter("productCode");
			String isPutSale = request.getParameter("isPutSale");
			String isPass = request.getParameter("isPass");
			String prodName = request.getParameter("prodName");
			String shopName = request.getParameter("shopName");
//			String prodType = request.getParameter("prodType");
//			hqlsb.append(" where 1=1");
			if(StringUtils.isNotEmpty(productCode)){
				productCode=productCode.trim();
				hqlsb.append(" and p.productCode like '%"+productCode+"%'");
			}
			if(!"-1".equals(isPutSale)){
				hqlsb.append(" and p.isPutSale ="+isPutSale);
			}
			if(!"-1".equals(isPass)){
				hqlsb.append(" and p.isPass ="+isPass);
			}
//			if(!"-1".equals(prodType)){
//				hqlsb.append(" and o.productTypeId ="+prodType);
//			}
			if(StringUtils.isNotEmpty(prodName)){
				prodName=prodName.trim();
				hqlsb.append(" and p.productName like '%"+prodName+"%'");
			}
			if(shopName!=null&&!"".equals(shopName.trim())){
				shopName=shopName.trim();
				hqlsb.append(" and s.shopName like '%"+shopName+"%'");
			}
		}
		hqlsb.append(" order by p.productId desc");
		hql1.append(hqlsb);
		int totalRecordCount = productInfoService.getCountByHQL(hql1.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		hql.append(hqlsb);
		productInfoList = productInfoService.findListMapPage(hql.toString(), pageHelper);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", productInfoList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateProductInfo() throws Exception{
		if(null!=productId){
			String isPutSale = request.getParameter("isPutSale");
			String isPass = request.getParameter("isPass");
			String isTop = request.getParameter("isTop");
			String isRecommend = request.getParameter("isRecommend");
//			String isNew = request.getParameter("isNew");
//			String isHot = request.getParameter("isHot");
			productInfo = (ProductInfo) productInfoService.getObjectByParams(" where o.productId='"+productId+"'");
			if(productInfo!=null){
				String url = request.getContextPath();
				//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+url+"/";
				String basePath = (String) fileUrlConfig.get("phoneBasePath");
				String addUrl = basePath+"/phone/gotoProductInfo.action?productId="+productInfo.getProductId();
				String codeUrl = QRCode.createQRPng(addUrl, null, fileUrlConfig, "shop" ,"image_product");
				productInfo.setQrCode(codeUrl);
			}
			productInfo.setIsPutSale(Integer.parseInt(isPutSale));
			if(Integer.parseInt(isPutSale)==2){
				productInfo.setPutSaleDate(new Date());
			}
			productInfo.setIsPass(Integer.parseInt(isPass));
			productInfo.setIsTop(Integer.parseInt(isTop));
			productInfo.setIsRecommend(Integer.parseInt(isRecommend));
//			productInfo.setIsNew(Integer.parseInt(isNew));
//			productInfo.setIsHot(Integer.parseInt(isHot));
			productInfo.setUpdateDate(new Date());
			productInfoService.saveOrUpdateObject(productInfo);
			if(productInfo.getProductId()!=null){
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
	/**
	 * 根据选择的分类查找属性和参数
	 * @throws IOException 
	 */
	public void getAttrAndPara() throws IOException{
		ProductParameters productPara =  (ProductParameters) productParametersService.getObjectByParams("where o.productTypeId="+productTypeId);
		List<ProductAttribute> listProductAttr =  productAttributeService.findObjects("where o.productTypeId="+productTypeId);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("productPara", productPara);
		jsonMap.put("listProductAttr", listProductAttr);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//获取一条记录
	public void getProductInfoObject() throws IOException{
		productInfo = (ProductInfo) productInfoService.getObjectByParams(" where o.productId='"+productId+"'");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		getProductTypeName(productInfo.getProductTypeId());
		//获取分类end
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createDate ="";
		if(productInfo.getCreateDate()!=null){
			createDate = formatter.format(productInfo.getCreateDate());
		}
		String putSaleDate ="";
		if(productInfo.getPutSaleDate()!=null){
			putSaleDate = formatter.format(productInfo.getPutSaleDate());
		}
		String updateDate="";
		if(null!=productInfo.getUpdateDate()){
			updateDate = formatter.format(productInfo.getUpdateDate());
		}
		String deliveryAddress="";
		if(null!=productInfo.getDeliveryAddressCities()){
			BasicArea basicArea = (BasicArea)areaService.getObjectById(productInfo.getDeliveryAddressCities()+"");
			deliveryAddress = basicArea.getFullName();
		}
		jsonMap.put("createDate",createDate );
		jsonMap.put("putSaleDate", putSaleDate);
		jsonMap.put("updateDate", updateDate);
		jsonMap.put("productInfo", productInfo);
		jsonMap.put("productTypeName", prodTypeNames);
		jsonMap.put("deliveryAddress", deliveryAddress);
		JSONObject jo = JSONObject.fromObject(jsonMap);
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
	//删除记录
	public void deleteProductInfo() throws IOException{
		Boolean isSuccess = productInfoService.deleteObjectsByIds("productId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	
	public List<Map> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<Map> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public List<Brand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
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
	public String getShowplaces() {
		return showplaces;
	}
	public void setShowplaces(String showplaces) {
		this.showplaces = showplaces;
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
	public List<ProductType> getProdTypeList() {
		return prodTypeList;
	}
	public void setProdTypeList(List<ProductType> prodTypeList) {
		this.prodTypeList = prodTypeList;
	}
	public void setProductAttributeService(
			IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}
	public void setProductParametersService(
			IProductParametersService productParametersService) {
		this.productParametersService = productParametersService;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public void setProductAttributeAction(
			ProductAttributeAction productAttributeAction) {
		this.productAttributeAction = productAttributeAction;
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
	public void setProductImgService(IProductImgService productImgService) {
		this.productImgService = productImgService;
	}
	public List<ShopInfo> getShopInfoList() {
		return shopInfoList;
	}
	public void setShopInfoList(List<ShopInfo> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
