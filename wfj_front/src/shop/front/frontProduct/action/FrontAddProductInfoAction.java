package shop.front.frontProduct.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.front.frontProduct.service.IFrontProductInfoService;
import shop.front.store.service.IShopProCateClassService;
import shop.product.pojo.Brand;
import shop.product.pojo.Brand2;
import shop.product.pojo.ParamGroup;
import shop.product.pojo.ParamGroupInfo;
import shop.product.pojo.ProductAttr;
import shop.product.pojo.ProductAttrIndex;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IProductAttrIndexService;
import shop.product.service.IProductImgService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductSpecificationService;
import shop.product.service.IProductSpecificationValueService;
import shop.product.service.IProductTypeService;
import shop.product.service.ISpecificationValueService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopProCategoryService;
import shop.tags.pojo.ShopProductTradeSituationTag;
import shop.tags.service.IShopProductTradeSituationTagService;
import util.action.BaseAction;
import util.other.InputExcel;
import util.upload.ImageFileUploadUtil;
/**
 * FrontAddProductInfoAction - 前台卖家中心商品添加修改查询ction
 */
public class FrontAddProductInfoAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 260940050385308630L;
	/**前台商品Service**/
	private IFrontProductInfoService frontProductInfoService;
	/**详细内容明细集合**/
	private List<ParamGroupInfo> listParamGroupInfo = new ArrayList<ParamGroupInfo>();
	/**详细参数内容集合**/
	private List<ParamGroup> listParamGroup;
	/**商品其他页面的图片上传**/
	private List<File> otherUploadImgs;
	private List<String>  otherUploadImgsFileName;
	/**详情页面图片**/
	private List<File> listProductUploadImgs;
	/**详情页面图片名称**/
	private List<String>  listProductUploadImgsFileName;
	/**商品详情的图片**/
	private List<ProductImg> listProductImage = new ArrayList<ProductImg>();
	/**商品信息表**/
	private ProductInfo productInfo;
	/**商品信息表**/
	private ProductAttrIndex productAttrIndex;
	/**保存的商品的属性**/
	private List<ProductAttr> listProdAttr = new ArrayList<ProductAttr>();
	private String parameters;//得到规格
	/**店铺内部商品分类和商品关系Service**/
	@SuppressWarnings("unused")
	private IShopProCateClassService shopProCateClassService;
	/**店铺内部商品分类Service**/
	@SuppressWarnings("unused")
	private IShopProCategoryService shopProCategoryService;
	private String shopProCategoryId;//店铺内部分类ID
	private Integer productId;//商品ID
	private String goods;//商品分组
	private IProductInfoService productInfoService;//商品信息Service
	private IProductImgService productImgService;//商品图片Service
	private String title;//标题
	private String orders;//排序
	private String logoImg;//商品logo图片
	private List<ProductImg> uproductImgList = new ArrayList<ProductImg>();//原来存储的图片
	private IProductSpecificationService productSpecificationService;//商品和规格关系Service
	private IProductSpecificationValueService productSpecificationValueService;//商品和规格值关系Service
	private ISpecificationValueService specificationValueService;//规格值Service
	private IShopProductTradeSituationTagService shopProductTradeSituationTagService;//商品关联适用行业与使用场合service
	private IProductAttrIndexService productAttrIndexService;
	private IProductTypeService productTypeService;
	private IShopInfoService shopInfoService;
	private IBrandService brandService;
	
	// 异步上传文件路径
	private File imagePath;
	// 异步上传文件名称
	private String imagePathFileName;
	/**删除商品详情图片传递的图片id**/
	private Integer productImageId;
	/**tab标记**/
	private Integer index;
	/*******************************end********************************************/
	
	
	public String goToExcelProductInfo(){
		return "success";
	}
	/**
	 * Excel表头排列顺序{"productName","sortName","categoryLevel1","categoryLevel2","categoryLevel3","shopInfoName","brandName","productFullName","salesPrice","marketPrice",
	 * "costPrice","weight","weightUnit","measuringUnitName","packingSpecifications","specification","manufacturerModel","describle","productCode","storeNumber","note"};
	 * 保存Excel导入的数据
	 * @throws Exception
	 */
	public void saveOrUpdateExcelProductInfo() throws Exception{
		List<ProductInfo> productList=new ArrayList<ProductInfo>();
		List dataList=InputExcel.getData(imagePath,imagePathFileName);
		StringBuffer errorBrandStrs=new StringBuffer();//重复出现的品牌名
	  if(dataList!=null&&dataList.size()>0){
		for(int i=0;i<dataList.size();i++){
			List li=(ArrayList)dataList.get(i);
			ProductInfo pi=new ProductInfo();
			pi.setProductName((String)li.get(0));
			String sortName=(String)li.get(1);
			String categoryLevel1=(String)li.get(2);
			String categoryLevel2=(String)li.get(3);
			String categoryLevel3=(String)li.get(4);
			String shopName=(String)li.get(5);
			String brandName=(String)li.get(6);
			//根据分类名获取分类
			ProductType pt=(ProductType)productTypeService.getObjectByParams("where o.sortName='"+sortName+"'");
			ProductType pt1=(ProductType)productTypeService.getObjectByParams("where o.sortName='"+categoryLevel1+"'");
			ProductType pt2=(ProductType)productTypeService.getObjectByParams("where o.sortName='"+categoryLevel2+"'");
			ProductType pt3=(ProductType)productTypeService.getObjectByParams("where o.sortName='"+categoryLevel3+"'");
			pi.setProductTypeId(pt.getProductTypeId());
			pi.setCategoryLevel1(pt1.getProductTypeId());
			pi.setCategoryLevel2(pt2.getProductTypeId());
			pi.setCategoryLevel3(pt3.getProductTypeId());
			//根据店铺名获取店铺
			ShopInfo si=(ShopInfo)shopInfoService.getObjectByParams("where o.shopName='"+shopName+"'");
			pi.setShopInfoId(si.getShopInfoId());
			pi.setShopName(si.getShopName());
			//根据品牌名获取品牌
			Brand bd=(Brand)brandService.getObjectByParams("where o.brandName='"+brandName+"'");
			pi.setBrandId(bd.getBrandId());
			pi.setBrandName(bd.getBrandName());
			
			pi.setProductFullName((String)li.get(7));
			pi.setSalesPrice(new BigDecimal((String)li.get(8)));
			pi.setMarketPrice(new BigDecimal((String)li.get(9)));
			pi.setCostPrice(new BigDecimal((String)li.get(10)));
			pi.setWeight(Double.parseDouble((String)li.get(11)));
			pi.setWeightUnit((String)li.get(12));
			pi.setMeasuringUnitName((String)li.get(13));
			pi.setPackingSpecifications((String)li.get(14));
			pi.setSpecification((String)li.get(15));
			pi.setManufacturerModel((String)li.get(16));
			pi.setDescrible((String)li.get(17));
			pi.setProductCode((String)li.get(18));
			pi.setStoreNumber(Integer.valueOf((String)li.get(19)));
			pi.setNote((String)li.get(20));
			pi.setCreateDate(new Date());
			pi.setUpdateDate(new Date());
			pi.setIsPutSale(0);//默认新添加商品不上架
			pi.setIsPass(0);//默认待审核
			pi.setIsRecommend(0);//默认不推荐
			pi.setIsNew(1);//默认是新商品
			pi.setIsHot(0);//默认非热销商品
			pi.setIsTop(0);//默认非置顶商品
//			productInfoService
			productList.add(pi);
			
		}
	  } 
//	  JSONObject jo = new JSONObject();
//	  jo.accumulate("isSuccess", false);
//	  jo.accumulate("msg", "Excel导入数据成功！");  
//	  response.setContentType("text/html;charset=utf-8");
//	  PrintWriter out = response.getWriter();
//	  out.println(jo.toString());
//	  out.flush();
//	  out.close();
	  
	}
	
	
	
	/**
	 * 保存或者修改商品
	 * @throws Exception 
	 */
	public String saveOrUpdateFrontProduct() throws Exception{
		/*System.out.println("++++++++++");
		System.out.println(this.productInfo.getFunctionDesc());*/
		//商品参数的处理
		if(listParamGroup!=null && listParamGroup.size()>0){
			for(ParamGroup pg:listParamGroup ){
				List<ParamGroupInfo> listPGI=new ArrayList<ParamGroupInfo>();
				for(ParamGroupInfo pgi:listParamGroupInfo){
					if(pg.getParamGroupId().equals(pgi.getPgiId())){
						listPGI.add(pgi);
					}
				}
				pg.setParamGroupInfo(listPGI);
			}
		}
		//商品图片处理
		if(otherUploadImgs!=null && otherUploadImgs.size()>0){
			String otherImg = ImageFileUploadUtil.uploadImageFile(otherUploadImgs.get(0), otherUploadImgsFileName.get(0), fileUrlConfig, "image_product");
			productInfo.setLogoImg(otherImg);
		}
		//商品扩展属性处理
		String productAttribute = "";
		for(ProductAttr pa:listProdAttr){
			if(productAttribute==""){
				productAttribute = pa.getValue();
			}else{
				productAttribute = productAttribute+","+ pa.getValue();
			}
		}
		
		productInfo.setProductAttribute(productAttribute);//商品属性值的保存
		if(listParamGroup!=null && listParamGroup.size()>0){
			JSONArray jbListPG = JSONArray.fromObject(listParamGroup);//json格式的商品参数
			productInfo.setProductParametersValue(jbListPG.toString());
		}
		if(listProdAttr!=null && listProdAttr.size()>0){
			JSONArray jbListPA = JSONArray.fromObject(listProdAttr);//json格式的商品属性
			productInfo.setProductAttributeValue(jbListPA.toString());
		}
		if(productInfo.getIsChargeFreight()==1){//不收取运费
			productInfo.setFreightPrice(new BigDecimal(0));//运费==0.0
		}
		productInfo.setCreateDate(new Date());
		productInfo.setUpdateDate(new Date());
		productInfo.setIsNew(1);//是否为新商品
		productInfo.setIsTop(0);//是否置顶
		productInfo.setIsHot(0);//是否热销
		productInfo.setIsPass(2);//是否通过审核（待申请）
		productInfo.setIsPutSale(1);//是否上架
		productInfo.setIsRecommend(0);//是否推荐
		productInfo.setTotalSales(0);//销售量
		productInfo.setIsShow(1);//是否显示为显示
		//去分组的最大值
		Integer maxGoods = (Integer) frontProductInfoService.getMaxDataSQL("select max(goods) from shop_productinfo");
		if(maxGoods==null){
			productInfo.setGoods(1);
		}else{
			productInfo.setGoods(maxGoods+1);
		}
		//获取商品关联标签jsonArray信息
		String tages = request.getParameter("tages");
		JSONArray jsonArray=null;
		if(tages!=null&&!"".equals(tages)){
			jsonArray=JSONArray.fromObject(tages);
		}
		//商品属性关系表的处理
		List<ProductAttrIndex> paiList = new ArrayList<ProductAttrIndex>();
		for(ProductAttr pa:listProdAttr){
			ProductAttrIndex pai = new ProductAttrIndex();
			pai.setProductAttrId(Integer.parseInt(pa.getName()));
			pai.setAttrValueId(Integer.parseInt(pa.getValue()));
			paiList.add(pai);
		}
		frontProductInfoService.saveOrUpdateListProductInfo(shopProCategoryId,productInfo, parameters,listProductImage,fileUrlConfig,listProductUploadImgs,listProductUploadImgsFileName,jsonArray,paiList);
		return "success";
	}
	//前台修改店铺商品基本信息
	public String updateFrontProductInfo(){
		if(productId!=null){
			//更新商品标签信息
			//1.tages删除关联标签数据
			shopProductTradeSituationTagService.deleteObjectByParams(" where o.productId="+productId);
			//2.重新插入关联标签数据
			//获取商品关联标签jsonArray信息
			String tages = request.getParameter("tages");
			JSONArray jsonArray=null;
			if(tages!=null&&!"".equals(tages)){
				jsonArray=JSONArray.fromObject(tages);
			}
			if(jsonArray!=null&&jsonArray.size()>0){
				int jsonLength = jsonArray.size();
				//对json数组进行循环
				for (int p = 0; p < jsonLength;p++) {
					JSONObject jo=JSONObject.fromObject(jsonArray.get(p));
					String ttId=jo.getString("ttId");
					String ids=jo.getString("ids");
					if(StringUtils.isNotEmpty(ids)){
						String[] split = ids.split(",");
						for(String stId:split){
							ShopProductTradeSituationTag sptst=new ShopProductTradeSituationTag();
							sptst.setProductId(productId);
							sptst.setStId(Integer.parseInt(stId));
							sptst.setTtId(Integer.parseInt(ttId));
							shopProductTradeSituationTagService.saveOrUpdateObject(sptst);
						}
					}
				}
			}
			ProductInfo product = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+productId);
			//把新修改的基本数据复制到原始数据里面
			product.setStoreNumber(productInfo.getStoreNumber());//库存
			product.setStockUpDate(productInfo.getStockUpDate());//预计发货日
			product.setDeliveryAddressProvince(productInfo.getDeliveryAddressProvince());//发货地
			product.setDeliveryAddressCities(productInfo.getDeliveryAddressCities());//发货地
			product.setProductCode(productInfo.getProductCode());//商品编号
			product.setProductTypeId(productInfo.getProductTypeId());//商品分类ID
			product.setCategoryLevel1(productInfo.getCategoryLevel1());//一级分类ID
			product.setCategoryLevel2(productInfo.getCategoryLevel2());//二级分类ID
			product.setCategoryLevel3(productInfo.getCategoryLevel3());//三级分类ID
			product.setCategoryLevel4(productInfo.getCategoryLevel4());//四级分类ID
			//product.setProductName(productInfo.getProductName());//商品名称
			product.setBrandId(productInfo.getBrandId());//品牌
			product.setMarketPrice(productInfo.getMarketPrice());//市场价格
			product.setSalesPrice(productInfo.getSalesPrice());//销售价格
			product.setCostPrice(productInfo.getCostPrice());//进货价
			product.setWeight(productInfo.getWeight());//商品重量
			product.setWeightUnit(productInfo.getWeightUnit());//重量单位
			product.setMeasuringUnitName(productInfo.getMeasuringUnitName());//计量单位
			product.setPackingSpecifications(productInfo.getPackingSpecifications());//包装规格
			product.setDescrible(productInfo.getDescrible());//商品描述
			product.setIsPutSale(1);//是否上架
			product.setIsPass(2);//待申请
			product.setIsChargeFreight(productInfo.getIsChargeFreight());//是否收取运费
			product.setFreightPrice(productInfo.getFreightPrice());//运费价格
			product.setNote(productInfo.getNote());//商品备注
			product.setProductTag(productInfo.getProductTag());//商品搜索标签TAG
			product.setSeoTitle(productInfo.getSeoTitle());//seo标题
			product.setSeoKeyWord(productInfo.getSeoKeyWord());//seo关键字
			product.setSeoDescription(productInfo.getSeoDescription());//seo描述
			product.setFunctionDesc(productInfo.getFunctionDesc());//功能介绍
			product.setManufacturerModel(productInfo.getManufacturerModel());//制造商型号
			//product.setVirtualCoinNumber(productInfo.getVirtualCoinNumber());//SHOPJSP币
			//商品属性值处理
			/*String productAttribute = "";
			for(ProductAttr pa:listProdAttr){
				if(productAttribute==""){
					productAttribute = pa.getValue();
				}else{
					productAttribute = productAttribute+","+ pa.getValue();
				}
			}
			product.setProductAttribute(productAttribute);//商品值的保存*/
			//删掉旧有的ProductAttrIndex类
			productAttrIndexService.deleteObjectsByIds("productId", product.getProductId()+"");
			//添加新的ProductAttrIndex类
			for(ProductAttr pa:listProdAttr){
				if(pa != null){
					ProductAttrIndex pai = new ProductAttrIndex();
					pai.setProductAttrId(Integer.parseInt(pa.getName()));
					pai.setAttrValueId(Integer.parseInt(pa.getValue()));
					pai.setProductId(product.getProductId());
					pai.setProductTypeId(product.getProductTypeId());
					productAttrIndexService.saveOrUpdateObject(pai);
				}
			}
			
			//商品参数的处理
			if(listParamGroup!=null && listParamGroup.size()>0){
				for(ParamGroup pg:listParamGroup ){
					List<ParamGroupInfo> listPGI=new ArrayList<ParamGroupInfo>();
					for(ParamGroupInfo pgi:listParamGroupInfo){
						if(pg.getParamGroupId().equals(pgi.getPgiId())){
							listPGI.add(pgi);
						}
					}
					pg.setParamGroupInfo(listPGI);
				}
			}
			if(listParamGroup!=null && listParamGroup.size()>0){
				JSONArray jbListPG = JSONArray.fromObject(listParamGroup);//json格式的商品参数
				product.setProductParametersValue(jbListPG.toString());
			}
			if(listProdAttr!=null && listProdAttr.size()>0){
				JSONArray jbListPA = JSONArray.fromObject(listProdAttr);//json格式的商品属性
				product.setProductAttributeValue(jbListPA.toString());
			}
			frontProductInfoService.saveOrUpdateBasicProduct(product, Integer.parseInt(shopProCategoryId));
		}
		return SUCCESS;
	}
	//前台修改店铺商品介绍
	public String updateFrontProductReferral(){
		if(productId!=null){
			ProductInfo product = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+productId);
			if(productInfo!=null){
				product.setFunctionDesc(productInfo.getFunctionDesc());
			}
			//查询出同组的商品
			List<ProductInfo> productList = productInfoService.findObjects(" where o.goods='"+goods+"'");
			//循环更新商品
			for(ProductInfo p : productList){
				product.setProductId(p.getProductId());
				productInfoService.saveOrUpdateObject(product);
			}
		}
		return SUCCESS;
	}
	//前台修改店铺商品图片
	public String updateFrontProductImg(){
		if(productId!=null){
			ProductInfo product = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+productId);
			if(otherUploadImgs!=null && otherUploadImgs.size()>0){
				//商品图片处理
				String LogoImg = ImageFileUploadUtil.uploadImageFile(otherUploadImgs.get(0), otherUploadImgsFileName.get(0), fileUrlConfig, "image_product");
				product.setLogoImg(LogoImg);
			}
			frontProductInfoService.saveOrUpdateProdImg(product, listProductImage);
		}
		return SUCCESS;
	}
	//前台修改店铺商品属性
	public String updateFrontProductAttribute() throws UnsupportedEncodingException{
		String productAttribute = "";
		for(ProductAttr pa:listProdAttr){
			if(productAttribute==""){
				productAttribute = pa.getValue();
			}else{
				productAttribute = productAttribute+","+ pa.getValue();
			}
		}
		if(productId!=null){
			ProductInfo product = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+productId);
			product.setProductAttribute(productAttribute);
			if(listProdAttr!=null && listProdAttr.size()>0){
				JSONArray jbListPA = JSONArray.fromObject(listProdAttr);//json格式的商品属性
				product.setProductAttributeValue(jbListPA.toString());
			}
			//查询出同组的商品
			List<ProductInfo> productList = productInfoService.findObjects(" where o.goods='"+goods+"'");
			//循环更新商品
			for(ProductInfo p : productList){
				product.setProductId(p.getProductId());
				productInfoService.saveOrUpdateObject(product);
			}
		}
		return SUCCESS;
	}
	//前台修改店铺商品参数
	public String updateFrontProductParameter() throws UnsupportedEncodingException{
		if(productId!=null){
			ProductInfo product = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+productId);
			if(listParamGroup!=null && listParamGroup.size()>0){
				JSONArray jbListPG = JSONArray.fromObject(listParamGroup);//json格式的商品参数
				product.setProductParametersValue(jbListPG.toString());
			}
			//查询出同组的商品
			List<ProductInfo> productList = productInfoService.findObjects(" where o.goods='"+goods+"'");
			//循环更新商品
			for(ProductInfo p : productList){
				product.setProductId(p.getProductId());
				productInfoService.saveOrUpdateObject(product);
			}
		}
		return SUCCESS;
	}
	//前台修改店铺商品规格updateFrontProductSpecification
	public String updateFrontProductSpecification(){
		frontProductInfoService.updateFrontProductSpecification(productId, goods, parameters);
		return SUCCESS;
	}
	/**
	 * 异步ajax 图片上传
	 * @throws IOException
	 */
	public void uploadImageFront() throws IOException  {
		try {
			JSONObject jo = new JSONObject();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			ProductImg productImg = new ProductImg();
			productImg = frontProductInfoService.uploadProductImage(imagePath, imagePathFileName, productImg, fileUrlConfig);
			if(imagePathFileName.equals(productImg.getSource()) || imagePathFileName.equals("图片上传失败!")){
				jo.accumulate("photoUrl", "false");
			}else{
				jo.accumulate("photoUrl", productImg);
				jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
			}
			out.println(jo.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * 异步删除商品详情图片
	 */
	public void deleteProductImg(){
		try {
			if(productImageId!=null){
				boolean flag = productImgService.deleteObjectByParams("where o.productImageId="+productImageId);
				JSONObject jo = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out;
				out = response.getWriter();
				jo.accumulate("flag", flag);
				out.println(jo.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Integer getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(Integer productImageId) {
		this.productImageId = productImageId;
	}
	public List<ParamGroupInfo> getListParamGroupInfo() {
		return listParamGroupInfo;
	}
	public void setListParamGroupInfo(List<ParamGroupInfo> listParamGroupInfo) {
		this.listParamGroupInfo = listParamGroupInfo;
	}
	public List<ParamGroup> getListParamGroup() {
		return listParamGroup;
	}
	public void setListParamGroup(List<ParamGroup> listParamGroup) {
		this.listParamGroup = listParamGroup;
	}
	public List<File> getOtherUploadImgs() {
		return otherUploadImgs;
	}
	public void setOtherUploadImgs(List<File> otherUploadImgs) {
		this.otherUploadImgs = otherUploadImgs;
	}
	public List<String> getOtherUploadImgsFileName() {
		return otherUploadImgsFileName;
	}
	public void setOtherUploadImgsFileName(List<String> otherUploadImgsFileName) {
		this.otherUploadImgsFileName = otherUploadImgsFileName;
	}
	public List<ProductImg> getListProductImage() {
		return listProductImage;
	}
	public void setListProductImage(List<ProductImg> listProductImage) {
		this.listProductImage = listProductImage;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public List<ProductAttr> getListProdAttr() {
		return listProdAttr;
	}
	public void setListProdAttr(List<ProductAttr> listProdAttr) {
		this.listProdAttr = listProdAttr;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public void setFrontProductInfoService(
			IFrontProductInfoService frontProductInfoService) {
		this.frontProductInfoService = frontProductInfoService;
	}
	public List<File> getListProductUploadImgs() {
		return listProductUploadImgs;
	}
	public void setListProductUploadImgs(List<File> listProductUploadImgs) {
		this.listProductUploadImgs = listProductUploadImgs;
	}
	public List<String> getListProductUploadImgsFileName() {
		return listProductUploadImgsFileName;
	}
	public void setListProductUploadImgsFileName(
			List<String> listProductUploadImgsFileName) {
		this.listProductUploadImgsFileName = listProductUploadImgsFileName;
	}
	public void setShopProCateClassService(
			IShopProCateClassService shopProCateClassService) {
		this.shopProCateClassService = shopProCateClassService;
	}
	public void setShopProCategoryService(
			IShopProCategoryService shopProCategoryService) {
		this.shopProCategoryService = shopProCategoryService;
	}
	public String getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(String shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setProductImgService(IProductImgService productImgService) {
		this.productImgService = productImgService;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	public List<ProductImg> getUproductImgList() {
		return uproductImgList;
	}
	public void setUproductImgList(List<ProductImg> uproductImgList) {
		this.uproductImgList = uproductImgList;
	}
	public void setProductSpecificationService(
			IProductSpecificationService productSpecificationService) {
		this.productSpecificationService = productSpecificationService;
	}
	public void setProductSpecificationValueService(
			IProductSpecificationValueService productSpecificationValueService) {
		this.productSpecificationValueService = productSpecificationValueService;
	}
	public void setSpecificationValueService(
			ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
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
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public void setShopProductTradeSituationTagService(
			IShopProductTradeSituationTagService shopProductTradeSituationTagService) {
		this.shopProductTradeSituationTagService = shopProductTradeSituationTagService;
	}
	public ProductAttrIndex getProductAttrIndex() {
		return productAttrIndex;
	}
	public void setProductAttrIndex(ProductAttrIndex productAttrIndex) {
		this.productAttrIndex = productAttrIndex;
	}
	public void setProductAttrIndexService(
			IProductAttrIndexService productAttrIndexService) {
		this.productAttrIndexService = productAttrIndexService;
	}

	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
}
