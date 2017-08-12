package shop.front.frontProduct.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.front.frontProduct.service.IFrontProductInfoService;
import shop.front.store.service.IShopProCateClassService;
import shop.measuringUnit.pojo.MeasuringUnit;
import shop.measuringUnit.pojo.ProductMeasuringUnit;
import shop.measuringUnit.service.IMeasuringUnitService;
import shop.measuringUnit.service.IProductMeasuringUnitService;
import shop.product.pojo.AttributeValue;
import shop.product.pojo.Brand;
import shop.product.pojo.BrandType;
import shop.product.pojo.ParamGroup;
import shop.product.pojo.ParamGroupInfo;
import shop.product.pojo.ProductAttr;
import shop.product.pojo.ProductAttrIndex;
import shop.product.pojo.ProductAttribute;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductParameters;
import shop.product.pojo.ProductSpecification;
import shop.product.pojo.ProductSpecificationValue;
import shop.product.pojo.ProductType;
import shop.product.pojo.Specification;
import shop.product.pojo.SpecificationValue;
import shop.product.service.IAttributeValueService;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IDeployShowPhotoService;
import shop.product.service.IProductAttrIndexService;
import shop.product.service.IProductAttributeService;
import shop.product.service.IProductImgService;
import shop.product.service.IProductParametersService;
import shop.product.service.IProductSpecificationService;
import shop.product.service.IProductSpecificationValueService;
import shop.product.service.IProductTypeService;
import shop.product.service.ISpecificationService;
import shop.product.service.ISpecificationValueService;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.ShopProCategory;
import shop.store.service.IShopBrandService;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopProCategoryService;
import shop.tags.pojo.ShopTradeTag;
import shop.tags.service.IShopProductTradeSituationTagService;
import shop.tags.service.IShopSituationTagService;
import shop.tags.service.IShopTradeSituationTagService;
import shop.tags.service.IShopTradeTagService;
import util.action.BaseAction;
import basic.service.IAreaService;
/**
 * FrontAddProductInfoAction - 前台卖家中心商品添加修改查询ction
 */
@SuppressWarnings("serial")
public class FrontProductInfoAction extends BaseAction {
	/**前台商品Service**/
	private IFrontProductInfoService frontProductInfoService;
	/**商品图片展示配置**/
	private IDeployShowPhotoService  deployShowPhotoService;
	/**图片展示配置list**/
	@SuppressWarnings("rawtypes")
	private List<Map> listDeploySP;
	/**商品分类的集合**/
	private List<ProductType> prodTypeList = new ArrayList<ProductType>();
	/**品牌List**/
	private List<Brand> brandList ;
	/**商品属性值List**/
	private List<AttributeValue> attributeValueList ;
	/**选择的商品属性值List**/
	private List<ProductAttrIndex> paiList;
	/**商品计量单位List**/
	private List<MeasuringUnit> measuringUnitList;
	/**品牌Service**/
	private IBrandService brandService;
	/** 商品分类service**/
	private IProductTypeService productTypeService;
	/**商品详情的图片上传**/
	private List<ProductImg> listProductImage;
	/**商品其他页面的图片上传**/
	private List<File> otherUploadImgs;
	private List<String>  otherUploadImgsFileName;
	/**详细参数内容集合**/
	private List<ParamGroup> listParamGroup;
	/**详细内容明细集合**/
	private List<ParamGroupInfo> listParamGroupInfo = new ArrayList<ParamGroupInfo>();
	/**保存的商品的属性**/
	private List<ProductAttr> listProdAttr = new ArrayList<ProductAttr>();
	/**商品信息表**/
	private ProductInfo productInfo;
	/**店铺**/
	private ShopInfo shopInfo;
	/***店铺service*/
	private IShopInfoService shopInfoService;
	/**分类id**/
	private Integer selectProductTypeId;
	/**属性service**/
	private IProductAttributeService productAttributeService;
	/**参数service**/
	private IProductParametersService productParametersService;
	/**参数**/
	private ProductParameters productPara;
	/**属性list**/
	private List<ProductAttribute> listProductAttr = new ArrayList<ProductAttribute>();
	/**商品分类下的参数**/
	private JSONObject ppINfo;
	/**商品中的参数**/
	private JSONObject joProductPara;
	/**商品属性**/
	private JSONArray jaListProductAttr;
	private JSONArray jaattributeValueList;
	private JSONArray japaiList;
	/**cx添加的规格**/
	private ISpecificationService specificationService;//商品规格Service
	private ISpecificationValueService specificationValueService;//商品规格值service
	private IProductSpecificationService productSpecificationService;//商品和规格中间表Service
	private IProductSpecificationValueService productSpecificationValueService;//商品和规格值中间表Service
	private IAttributeValueService attributeValueService;//商品和属性值中间表Service
	private IProductAttrIndexService productAttrIndexService;
	private IProductMeasuringUnitService productMeasuringUnitService;//商品和计量单位中间表Service
	private IMeasuringUnitService measuringUnitService;//计量单位Service
	private IProductImgService productImgService;//商品图片
	private Map<Object,Object> map = new LinkedHashMap<Object,Object>();
	private String parameters;//得到规格
	private String productTypeId;//商品分类ID
	private Integer productId;//商品ID
	private List<ProductSpecification> productSpecificationList = new ArrayList<ProductSpecification>();//商品已经选择的规格
	private List<ProductImg> productImgList = new ArrayList<ProductImg>();//商品的图片
	private IShopBrandService shopBrandService;
	private Brand brand;
	/**店铺内部商品分类和商品关系Service**/
	private IShopProCateClassService shopProCateClassService;
	/**店铺内部商品分类Service**/
	private IShopProCategoryService shopProCategoryService;
	private List<ShopProCategory> shopProCategoryList = new ArrayList<ShopProCategory>();//店铺内部分类List
	private String selectshopProCategoryId;//选中的店铺内部商品分类ID
	private List<ProductInfo> productList = new ArrayList<ProductInfo>();//商品列表
	private Integer pageIndex2;//下架商品的当前页
	private IShopTradeTagService shopTradeTagService;//适用行业标签service
	private IShopSituationTagService shopSituationTagService;//使用场合标签service
	private IShopTradeSituationTagService shopTradeSituationTagService;//适用行业与使用场合中间关联表service
	private IShopProductTradeSituationTagService shopProductTradeSituationTagService;//商品关联适用行业与使用场合service
	
	/**************************修改规则使用的属性***********************************/
	/** 商品选择的规格值 **/
	private List<SpecificationValue> specificationValueList;
	/**商品所属分组**/
	private String goods;
	/** 商品选择的规格值 **/
	private List<ProductSpecificationValue> productSpecificationValueList = new ArrayList<ProductSpecificationValue>();
	/**得到规格**/
	private String specifications;
	/**品牌分类Service**/
	private IBrandTypeService brandTypeService;
	/** 指定分类下的规格 **/
	List<Specification> specificationList = new ArrayList<Specification>();
	/**区域service**/
	private IAreaService areaService;
	/**一级地址**/
	@SuppressWarnings("rawtypes")
	private List<Map> firstAreaList;
	/**二级地址**/
	@SuppressWarnings("rawtypes")
	private List<Map> secondAreaList;
	/**
	 * 添加商品
	 * @return
	 */
	public String frontAddProduct(){
		//根据当前登录人得到店铺信息
		Customer customer = (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId="+customer.getCustomerId());
			if(shopInfo!=null){
				shopProCategoryList = shopProCategoryService.findObjects(" where o.shopInfoId='"+shopInfo.getShopInfoId()+"' and o.level=1 and o.isShow=1");
			}
		}
		//查询适用行业相关标签信息
		List<ShopTradeTag> shopTradeTagList = shopTradeTagService.findObjects(" where o.useState=1 order by o.ttId desc");
		request.setAttribute("shopTradeTagList", shopTradeTagList);
		if(shopTradeTagList!=null&&shopTradeTagList.size()>0){
			request.setAttribute("firstShopTradeTag", JSONObject.fromObject(shopTradeTagList.get(0)).toString());
		}
		//一级地区parent=0
		String firstHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		firstAreaList=areaService.findListMapByHql(firstHql);
		return "success";
	}
	
	/**
	 * 通过适用行业ID获取相关联的适用场合信息
	 */
	public void getShopSituationTagInfoList() throws IOException{
		//获取ttId
		String ttId = request.getParameter("ttId");
		String hql="select a.tstId as tstId,c.tageName as tageName,c.stId as stId,c.useState as useState from ShopTradeSituationTag a ,ShopTradeTag b ,ShopSituationTag c where a.stId=c.stId and a.ttId=b.ttId and c.useState=1 and a.ttId="+ttId;
		List<Map> listMap = shopTradeSituationTagService.findListMapByHql(hql);
		JSONArray jo = JSONArray.fromObject(listMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	//根据商品ID查询商品
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProductInfoByProductId(){
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		productInfo = (ProductInfo) frontProductInfoService.getObjectByParams(" where o.productId='" + productId + "' and o.shopInfoId="+shopInfo.getShopInfoId());
		selectProductTypeId = productInfo.getProductTypeId();// 选择的分类
		// 查询分类下的品牌
		List<BrandType> brandTypeList = brandTypeService.findObjects(null, " where o.productTypeId='" + selectProductTypeId + "'");
		brandList = new ArrayList<Brand>();
		if (brandTypeList != null && brandTypeList.size() > 0) {
			for (BrandType bt : brandTypeList) {
				Brand brand = (Brand) brandService.getObjectByParams(" where brandId='" + bt.getBrandId() + "'");
				if (brand != null) {
					brandList.add(brand);
				}
			}
		}
		Brand brand = (Brand) brandService.getObjectByParams(" where brandId=1" );
		if (brand != null) {
			brandList.add(brand);
		}
		//查询商品计量单位名称
		List<ProductMeasuringUnit> productMeasuringUnitList = productMeasuringUnitService.findObjects(null, " where o.productTypeId=" + selectProductTypeId);
		measuringUnitList = new ArrayList<MeasuringUnit>();
		if (productMeasuringUnitList != null && productMeasuringUnitList.size() > 0) {
			for (ProductMeasuringUnit pmu : productMeasuringUnitList) {
				MeasuringUnit measuringUnit = (MeasuringUnit) measuringUnitService.getObjectByParams(" where measuringUnitId=" + pmu.getMeasuringUnitId()+"and useState = 1");
				if (measuringUnit != null) {
					measuringUnitList.add(measuringUnit);
				}
			}
		}
		MeasuringUnit measuringUnit = (MeasuringUnit) measuringUnitService.getObjectByParams(" where measuringUnitId=1");
		if (measuringUnit != null) {
			measuringUnitList.add(measuringUnit);
		}
		/*// 该商品所属分类下的所有属性和参数(暂时不实现，依据新的结构需要重新开发)
		listProductAttr = productAttributeService.findObjects(null, "where o.productTypeId=" + selectProductTypeId);
		jaListProductAttr = JSONArray.fromObject(listProductAttr);*/
		// 指定分类下的参数
		productPara =  (ProductParameters) productParametersService.getObjectByParams("where o.productTypeId="+selectProductTypeId);
		listProductAttr = productAttributeService.findObjects(null, "where o.productTypeId=" + selectProductTypeId+" and isListShow = 1");
		String productAttrIds="";
		if (listProductAttr != null && listProductAttr.size() > 0) {
			for (int i=0;i<listProductAttr.size();i++) {
				if(i==listProductAttr.size()-1){
					productAttrIds+=listProductAttr.get(i).getProductAttrId();
				}else{
					productAttrIds+=listProductAttr.get(i).getProductAttrId()+",";
				}
			}
			attributeValueList = attributeValueService.findObjects(null," where productAttrId in ("+productAttrIds+")");
			jaListProductAttr = JSONArray.fromObject(listProductAttr);
		}
		jaattributeValueList = JSONArray.fromObject(attributeValueList);
		//商品的属性
		paiList = productAttrIndexService.findObjects(null, " where o.productId='" + productInfo.getProductId() +"'");
		japaiList = JSONArray.fromObject(paiList);
		//查询商品的图片
		productImgList = productImgService.findObjects(null, " where o.productId='" + productInfo.getProductId() + "'");
		// 指定分类下的规格
		specificationList = specificationService.findObjects(null, " where o.productTypeId='" + selectProductTypeId + "'");
		//当前店铺中的内部分类
		shopProCategoryList = shopProCategoryService.findObjects(" where o.shopInfoId ="+productInfo.getShopInfoId());
		//当前商品所属店铺的内部分类
		List<Map<String,Object>> listMap= shopProCateClassService.findListMapBySql("select a.shopProCategoryId from shop_shopprocateclass a where a.productId="+productInfo.getProductId());
		
		if(listMap.size()>0){
			 selectshopProCategoryId = String.valueOf(listMap.get(0).get("shopProCategoryId"));
		}
		
		//查询当前商品的关联标签信息
			//1.查出基础的适用行业标签
			List<ShopTradeTag> shopTradeTagList = shopTradeTagService.findObjects(" where o.useState=1 order by o.ttId desc");
			request.setAttribute("shopTradeTagList", shopTradeTagList);
			if(shopTradeTagList!=null&&shopTradeTagList.size()>0){
				request.setAttribute("firstShopTradeTag", JSONObject.fromObject(shopTradeTagList.get(0)).toString());
			}
			//2.获取商品关联标签信息 处理成jsonArray的格式传到前台
			String sql="SELECT a.ttId as ttId,group_concat(a.stId) as ids FROM ShopProductTradeSituationTag a where a.productId="+productInfo.getProductId()+" group by a.ttId order by a.ttId desc";
			List<Map> shopProductTradeSituationTagListMap = shopProductTradeSituationTagService.findListMapByHql(sql);
			request.setAttribute("shopProductTradeSituationTagJsonArray", JSONArray.fromObject(shopProductTradeSituationTagListMap).toString());
		//一级地区parent=0
		String firstHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		firstAreaList=areaService.findListMapByHql(firstHql);
		//二级地区parent=productInfo.getDeliveryAddressProvince()
		String secondHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId="+productInfo.getDeliveryAddressProvince()+" order by a.areaId";
		secondAreaList=areaService.findListMapByHql(secondHql);
		return SUCCESS;
	}
	/**
	 * 根据选择 的分类id查找属性和参数
	 */
	public void findAttrPara(){
		try {
			if(selectProductTypeId!=null){
				//ss的属性和参数
				productPara =  (ProductParameters) productParametersService.getObjectByParams("where o.productTypeId="+selectProductTypeId);
				listProductAttr =  productAttributeService.findObjects("where o.productTypeId="+selectProductTypeId);
				jaListProductAttr = JSONArray.fromObject(listProductAttr);
				List<BrandType> brandTypeList = brandTypeService.findObjects(null, " where o.productTypeId=" + selectProductTypeId );
				brandList = new ArrayList<Brand>();
				if (brandTypeList != null && brandTypeList.size() > 0) {
					for (BrandType bt : brandTypeList) {
						Brand brand = (Brand) brandService.getObjectByParams(" where brandId=" + bt.getBrandId());
						if (brand != null) {
							brandList.add(brand);
						}
					}
				}else{
					Brand brand = (Brand) brandService.getObjectByParams(" where brandId=1" );
					if (brand != null) {
						brandList.add(brand);
					}
				}
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("productPara", productPara);
			map.put("listProductAttr", jaListProductAttr);
			map.put("brandList", brandList);
			JSONObject jo = JSONObject.fromObject(map);
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
	/**
	 * 根据商品分类查询对应的品牌、参数和规格
	 */
	@SuppressWarnings("unchecked")
	public void selectOtherInfoByProductTypeId() throws IOException {
		if (selectProductTypeId != null) {
			// 指定分类下的参数
			productPara = (ProductParameters) productParametersService.getObjectByParams("where o.productTypeId=" + selectProductTypeId);
			// 指定分类下的属性
			listProductAttr = productAttributeService.findObjects(null, "where o.productTypeId=" + selectProductTypeId+"and isListShow = 1");
			attributeValueList = new ArrayList<AttributeValue>();
			String productAttrIds="";
			if (listProductAttr != null && listProductAttr.size() > 0) {
				for (int i=0;i<listProductAttr.size();i++) {
					if(i==listProductAttr.size()-1){
						productAttrIds+=listProductAttr.get(i).getProductAttrId();
					}else{
						productAttrIds+=listProductAttr.get(i).getProductAttrId()+",";
					}
				}
				attributeValueList = attributeValueService.findObjects(null," where productAttrId in ("+productAttrIds+")");
			}
			jaListProductAttr = JSONArray.fromObject(listProductAttr);
			// 查询分类下的品牌
			List<BrandType> brandTypeList = brandTypeService.findObjects(null, " where o.productTypeId=" + selectProductTypeId );
			brandList = new ArrayList<Brand>();
			if (brandTypeList != null && brandTypeList.size() > 0) {
				for (BrandType bt : brandTypeList) {
					Brand brand = (Brand) brandService.getObjectByParams(" where brandId=" + bt.getBrandId());
					if (brand != null) {
						brandList.add(brand);
					}
				}
			}
			Brand brand = (Brand) brandService.getObjectByParams(" where brandId=1" );
			if (brand != null) {
				brandList.add(brand);
			}
			//查询商品计量单位名称
			List<ProductMeasuringUnit> productMeasuringUnitList = productMeasuringUnitService.findObjects(null, " where o.productTypeId=" + selectProductTypeId);
			measuringUnitList = new ArrayList<MeasuringUnit>();
			if (productMeasuringUnitList != null && productMeasuringUnitList.size() > 0) {
				for (ProductMeasuringUnit pmu : productMeasuringUnitList) {
					MeasuringUnit measuringUnit = (MeasuringUnit) measuringUnitService.getObjectByParams(" where measuringUnitId=" + pmu.getMeasuringUnitId()+"and useState=1");
					if (measuringUnit != null) {
						measuringUnitList.add(measuringUnit);
					}
				}
			}
			MeasuringUnit measuringUnit = (MeasuringUnit) measuringUnitService.getObjectByParams(" where measuringUnitId=1" );
			if (measuringUnit != null) {
				measuringUnitList.add(measuringUnit);
			}
			// 指定分类下的规格
			specificationList = specificationService.findObjects(null, " where o.productTypeId='" + selectProductTypeId + "'");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productPara", productPara);
		map.put("listProductAttr", jaListProductAttr);
		map.put("attributeValueList", attributeValueList);
		map.put("brandList", brandList);
		map.put("measuringUnitList", measuringUnitList);
		map.put("specificationList", specificationList);
		JSONObject jo = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 异步获取指定规格的规格值
	 */
	@SuppressWarnings("unchecked")
	public void getSpecificationValueBySpecificationId() {
		String specificationId = request.getParameter("specificationId");
		specificationValueList = ((List<SpecificationValue>) specificationValueService.findObjects(null, " where o.specificationId=" + specificationId));
		JSONArray jo = JSONArray.fromObject(specificationValueList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 异步删除商品的图片
	 */
	public void deleteProductImgById(){
		boolean isSuccess=productImgService.deleteObjectById(request.getParameter("productImgId"));
		JSONObject jo =new JSONObject();
		jo.accumulate("isSuccess", isSuccess);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取产品组的规格值
	 */
	@SuppressWarnings("unchecked")
	public void getGoodsProductSpecificationValue(){
		// 商品选定规格值
		List<List<ProductSpecificationValue>> psvGroupList=new ArrayList<List<ProductSpecificationValue>>();
		//指定当前商品的所有规格值,先加到列表中，表示列表的第一个规格组为当前商品的默认规格组
		productSpecificationValueList=productSpecificationValueService.findObjects(null, " where o.productId="+productId+" order by psvId");
		psvGroupList.add(productSpecificationValueList);
		//指定当前商品所在组其他商品的所有规格值
		productSpecificationValueList=productSpecificationValueService.findObjects(null, " where o.goodId="+goods+" and o.productId!="+productId+" order by psvId");
		if(productSpecificationValueList!=null&&productSpecificationValueList.size()>0){
			String old_productId="";
			List <ProductSpecificationValue> psvList=null;
			for(int i=0;i<productSpecificationValueList.size();i++){
				ProductSpecificationValue psv=productSpecificationValueList.get(i);
				String productId=psv.getProductId().toString();
				if(!old_productId.equals(productId)){
					if(i!=0){
						psvGroupList.add(psvList);
					}
					psvList=new ArrayList<ProductSpecificationValue>();
				}
				psvList.add(psv);
				old_productId=productId;
			}
			psvGroupList.add(psvList);
		}
		JSONArray jo = JSONArray.fromObject(psvGroupList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解除该商品所在商品组的规格以及对应的规格值的关联关系
	 */
	public void removeProductSpecificationValueGoodsGuanlian(){
		String optionProductId = request.getParameter("optionProductId");
		boolean isSuccess=frontProductInfoService.removeProductSpecificationValueGoodsGuanlian(productId,optionProductId);
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			jo.accumulate("isSuccess", isSuccess);
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * new解除该商品所在商品组的规格以及对应的规格值的关联关系
	 * @return
	 */
	public String updateFrontProductSpecification(){
		try {
			frontProductInfoService.updateFrontProductSpecification(specifications,productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;  
	}
	public void setFrontProductInfoService(
			IFrontProductInfoService frontProductInfoService) {
		this.frontProductInfoService = frontProductInfoService;
	}
	public List<ProductType> getProdTypeList() {
		return prodTypeList;
	}
	public void setProdTypeList(List<ProductType> prodTypeList) {
		this.prodTypeList = prodTypeList;
	}
	public List<Brand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public void setDeployShowPhotoService(
			IDeployShowPhotoService deployShowPhotoService) {
		this.deployShowPhotoService = deployShowPhotoService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getListDeploySP() {
		return listDeploySP;
	}
	@SuppressWarnings("unchecked")
	public void setListDeploySP(List<Map> listDeploySP) {
		this.listDeploySP = listDeploySP;
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
	public List<ProductImg> getListProductImage() {
		return listProductImage;
	}
	public void setListProductImage(List<ProductImg> listProductImage) {
		this.listProductImage = listProductImage;
	}
	public List<ProductAttr> getListProdAttr() {
		return listProdAttr;
	}
	public void setListProdAttr(List<ProductAttr> listProdAttr) {
		this.listProdAttr = listProdAttr;
	}
	public Map<Object, Object> getMap() {
		return map;
	}
	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}
	public void setSpecificationService(ISpecificationService specificationService) {
		this.specificationService = specificationService;
	}
	public void setSpecificationValueService(
			ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public Integer getSelectProductTypeId() {
		return selectProductTypeId;
	}
	public void setSelectProductTypeId(Integer selectProductTypeId) {
		this.selectProductTypeId = selectProductTypeId;
	}
	public ProductParameters getProductPara() {
		return productPara;
	}
	public void setProductPara(ProductParameters productPara) {
		this.productPara = productPara;
	}
	public List<ProductAttribute> getListProductAttr() {
		return listProductAttr;
	}
	public void setListProductAttr(List<ProductAttribute> listProductAttr) {
		this.listProductAttr = listProductAttr;
	}
	public void setProductAttributeService(
			IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}
	public void setProductParametersService(
			IProductParametersService productParametersService) {
		this.productParametersService = productParametersService;
	}
	public JSONObject getJoProductPara() {
		return joProductPara;
	}
	public void setJoProductPara(JSONObject joProductPara) {
		this.joProductPara = joProductPara;
	}
	public JSONArray getJaListProductAttr() {
		return jaListProductAttr;
	}
	public void setJaListProductAttr(JSONArray jaListProductAttr) {
		this.jaListProductAttr = jaListProductAttr;
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
	public void setProductSpecificationService(
			IProductSpecificationService productSpecificationService) {
		this.productSpecificationService = productSpecificationService;
	}
	public void setProductSpecificationValueService(
			IProductSpecificationValueService productSpecificationValueService) {
		this.productSpecificationValueService = productSpecificationValueService;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public List<ProductSpecification> getProductSpecificationList() {
		return productSpecificationList;
	}
	public void setProductSpecificationList(
			List<ProductSpecification> productSpecificationList) {
		this.productSpecificationList = productSpecificationList;
	}
	@SuppressWarnings("unchecked")
	public List<ProductSpecificationValue> getProductSpecificationValueList() {
		return productSpecificationValueList;
	}
	public void setProductSpecificationValueList(
			List<ProductSpecificationValue> productSpecificationValueList) {
		this.productSpecificationValueList = productSpecificationValueList;
	}
	public void setProductImgService(IProductImgService productImgService) {
		this.productImgService = productImgService;
	}
	public List<ProductImg> getProductImgList() {
		return productImgList;
	}
	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public void setShopBrandService(IShopBrandService shopBrandService) {
		this.shopBrandService = shopBrandService;
	}
	public void setShopProCateClassService(
			IShopProCateClassService shopProCateClassService) {
		this.shopProCateClassService = shopProCateClassService;
	}
	public void setShopProCategoryService(
			IShopProCategoryService shopProCategoryService) {
		this.shopProCategoryService = shopProCategoryService;
	}
	public List<ShopProCategory> getShopProCategoryList() {
		return shopProCategoryList;
	}
	public void setShopProCategoryList(List<ShopProCategory> shopProCategoryList) {
		this.shopProCategoryList = shopProCategoryList;
	}
	public String getSelectshopProCategoryId() {
		return selectshopProCategoryId;
	}
	public void setSelectshopProCategoryId(String selectshopProCategoryId) {
		this.selectshopProCategoryId = selectshopProCategoryId;
	}
	public List<ProductInfo> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductInfo> productList) {
		this.productList = productList;
	}
	public Integer getPageIndex2() {
		return pageIndex2;
	}
	public void setPageIndex2(Integer pageIndex2) {
		this.pageIndex2 = pageIndex2;
	}
	public List<SpecificationValue> getSpecificationValueList() {
		return specificationValueList;
	}
	public void setSpecificationValueList(
			List<SpecificationValue> specificationValueList) {
		this.specificationValueList = specificationValueList;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public List<Specification> getSpecificationList() {
		return specificationList;
	}
	public void setSpecificationList(List<Specification> specificationList) {
		this.specificationList = specificationList;
	}
	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}
	public JSONObject getPpINfo() {
		return ppINfo;
	}
	public void setPpINfo(JSONObject ppINfo) {
		this.ppINfo = ppINfo;
	}
	public void setShopTradeTagService(IShopTradeTagService shopTradeTagService) {
		this.shopTradeTagService = shopTradeTagService;
	}

	public void setShopSituationTagService(
			IShopSituationTagService shopSituationTagService) {
		this.shopSituationTagService = shopSituationTagService;
	}

	public void setShopTradeSituationTagService(
			IShopTradeSituationTagService shopTradeSituationTagService) {
		this.shopTradeSituationTagService = shopTradeSituationTagService;
	}

	public void setShopProductTradeSituationTagService(
			IShopProductTradeSituationTagService shopProductTradeSituationTagService) {
		this.shopProductTradeSituationTagService = shopProductTradeSituationTagService;
	}

	public List<MeasuringUnit> getMeasuringUnitList() {
		return measuringUnitList;
	}

	public void setMeasuringUnitList(List<MeasuringUnit> measuringUnitList) {
		this.measuringUnitList = measuringUnitList;
	}

	public void setProductMeasuringUnitService(
			IProductMeasuringUnitService productMeasuringUnitService) {
		this.productMeasuringUnitService = productMeasuringUnitService;
	}

	public void setMeasuringUnitService(IMeasuringUnitService measuringUnitService) {
		this.measuringUnitService = measuringUnitService;
	}

	public void setAttributeValueService(
			IAttributeValueService attributeValueService) {
		this.attributeValueService = attributeValueService;
	}

	public List<AttributeValue> getAttributeValueList() {
		return attributeValueList;
	}

	public void setAttributeValueList(List<AttributeValue> attributeValueList) {
		this.attributeValueList = attributeValueList;
	}

	public JSONArray getJaattributeValueList() {
		return jaattributeValueList;
	}

	public void setJaattributeValueList(JSONArray jaattributeValueList) {
		this.jaattributeValueList = jaattributeValueList;
	}

	public List<ProductAttrIndex> getPaiList() {
		return paiList;
	}

	public void setPaiList(List<ProductAttrIndex> paiList) {
		this.paiList = paiList;
	}

	public void setProductAttrIndexService(
			IProductAttrIndexService productAttrIndexService) {
		this.productAttrIndexService = productAttrIndexService;
	}

	public JSONArray getJapaiList() {
		return japaiList;
	}

	public void setJapaiList(JSONArray japaiList) {
		this.japaiList = japaiList;
	}

	public List<Map> getFirstAreaList() {
		return firstAreaList;
	}

	public void setFirstAreaList(List<Map> firstAreaList) {
		this.firstAreaList = firstAreaList;
	}

	public List<Map> getSecondAreaList() {
		return secondAreaList;
	}

	public void setSecondAreaList(List<Map> secondAreaList) {
		this.secondAreaList = secondAreaList;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

}
