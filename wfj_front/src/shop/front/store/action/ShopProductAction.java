package shop.front.store.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopProductService;
import util.action.BaseAction;
import util.other.Utils;
/** 
 * ShopProductAction - 店铺商品Action类
 * @author 孟琦瑞
 */
public class ShopProductAction extends BaseAction{ 
	private static final long serialVersionUID = 1L;
	/**店铺商品service**/
	private IShopProductService shopProductService;
	/**商品service**/
	private IProductInfoService productInfoService;
	/**商品分类service**/
	private IProductTypeService productTypeService;
	/**上架的商品List**/
	@SuppressWarnings("rawtypes")
	private List<Map> OnshelfList=new ArrayList<Map>();
	/**下架的商品List**/
	@SuppressWarnings("rawtypes")
	private List<Map> OffshelfList=new ArrayList<Map>();
	/**审核中的商品List**/
	@SuppressWarnings("rawtypes")
	private List<Map> reviewProductList=new ArrayList<Map>();
	/**违规下架的商品List**/
	@SuppressWarnings({ "rawtypes" })
	private List<Map> violationOffProductList =new ArrayList<Map>();
	/**商品的ID**/
	private String id;
	/**已选checkbox的值**/
	private String paramsCheckbox;
	/**店铺ID**/
	private String shopInfoId;
	/**商品ID**/
	private String productId;
	/**前台select的参数,用于排序**/
	private String params="";
	/**每页显示商品的个数**/
	private Integer pageSize=15;
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**商品显示状态isShow**/
	private Integer	isShowSate;
	/**商品名称**/
	private String productName;
	/**商品分类**/
	private Integer productTypeId = -1;
	/**查询当前商品分类包含子集**/
	private String productTypeIds = "";
	/**
	  *删除商品的参数
	  *	@param deleteParams=2;
	  *			删除下架商品
	  *	@param deleteParams=4;
	  *			删除违规下架商品
	  */
	private String deleteParams;
	/**商品列表 排序参数**/
	private String orderByParams;
	/**更改商品是否显示状态标识**/
	private String changeShowSate;
	/**商品下架操作标识**/
	private String changeisPutSate;
	/**商品批量下架操作标识**/
	private String bathChangeisPutSate;
	/**删除商品操作标识**/
	private Integer deleteProductObj;
	/**商品库存数**/
	private String storeNumber;
	/*******************************************end*******************************************************/
	/**
	 * 用递归方式获得子类分类的Id
	 */
	public String findProductTypeIds(Integer prodTypeId){
		productTypeIds += prodTypeId.toString()+",";
		List<ProductType> productTypeList = productTypeService.findObjects(" where o.parentId = "+prodTypeId);
		if(Utils.collectionIsNotEmpty(productTypeList)){
			for(ProductType pt : productTypeList){
				findProductTypeIds(pt.getProductTypeId());
			}
		}
		return productTypeIds;
	}
	//批量操作下架
	public void batchProcessing(String bathChangeisPutSate,Integer sate){
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		if(shopInfo!=null){
			String[] split = bathChangeisPutSate.split(",");
			if(split.length>0){
				for(String s:split){
					ProductInfo productInfo =(ProductInfo)productInfoService.getObjectByParams(" where o.productId="+s+" and o.shopInfoId="+shopInfo.getShopInfoId());
					productInfo.setIsPutSale(sate);
					productInfo.setIsPass(2);
					ProductInfo pi=(ProductInfo)productInfoService.saveOrUpdateProductInfo(productInfo);
				}
			}
		}
	}
	//删除商品
	public void deleteProduct(Integer productId){
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		if(shopInfo!=null){
			productInfoService.deleteProduct(productId,shopInfo);
		}
	}
	//更改商品显示状态isShow
	public void changeIsShowSate(String changeShowSate){
		String[] split = changeShowSate.split(",");//分割后有两个string对象 第一个为商品要更改的状态，第二个为商品id
		ProductInfo pd = (ProductInfo) productInfoService.getObjectById(split[1]);//拿到商品对象
		if(pd!=null){
			pd.setIsShow(Integer.parseInt(split[0]));
			productInfoService.saveOrUpdateObject(pd);
		}
	}
	//单个商品下架操作
	public void changePutSaleInfo(String productId,Integer sate){
		ProductInfo pd = (ProductInfo) productInfoService.getObjectById(productId);//拿到商品对象
		if(pd!=null){
			pd.setIsPutSale(sate);
			pd.setIsPass(2);
			productInfoService.saveOrUpdateProductInfo(pd);
		}
	}
	/**单个商品申请上架操作*/
	public void changeIsPass() throws IOException{
		ProductInfo pd = (ProductInfo) productInfoService.getObjectById(productId);//拿到商品对象
		if(pd!=null){
			pd.setIsPass(3);
			ProductInfo pi = (ProductInfo)productInfoService.saveOrUpdateObject(pd);
			JSONObject jo = new JSONObject();
			if(pi!=null){
				jo.accumulate("isSuccess", "true");
			}else{
				jo.accumulate("isSuccess", "false");
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**商品批量申请上架操作*/
	public void changeIsPasses() throws IOException{
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		String[] split = productId.split(",");
		if(split.length>0){
			ProductInfo pi = new ProductInfo();
			for(String s:split){
				ProductInfo productInfo =(ProductInfo)productInfoService.getObjectByParams(" where o.productId="+s+" and o.shopInfoId="+shopInfo.getShopInfoId());
				productInfo.setIsPass(3);
				pi=(ProductInfo)productInfoService.saveOrUpdateObject(productInfo);
			}
			JSONObject jo = new JSONObject();
			if(pi!=null){
				jo.accumulate("isSuccess", "true");
			}else{
				jo.accumulate("isSuccess", "false");
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**单个商品修改库存操作*/
	public void updateStoreNumber() throws IOException{
		ProductInfo pd = (ProductInfo) productInfoService.getObjectById(productId);//拿到商品对象
		if(pd!=null){
			pd.setStoreNumber(Integer.valueOf(storeNumber));
			ProductInfo pi = (ProductInfo)productInfoService.saveOrUpdateObject(pd);
			JSONObject jo = new JSONObject();
			if(pi!=null){
				jo.accumulate("isSuccess", "true");
			}else{
				jo.accumulate("isSuccess", "false");
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**
	 * 查询上架商品
	 * @return
	 * @author mqr
	 */
	@SuppressWarnings("rawtypes")
	public String gotoPutawayProductListInfoPage(){
		//更改商品是否展示状态
		if(changeShowSate!=null&&!"".equals(changeShowSate)){
			changeIsShowSate(changeShowSate);
		}
		//商品下架操作
		if(changeisPutSate!=null&&!"".equals(changeisPutSate)){
			changePutSaleInfo(changeisPutSate,1);
		}
		//商品批量下架操作
		if(bathChangeisPutSate!=null&&!"".equals(bathChangeisPutSate)){
			batchProcessing(bathChangeisPutSate,1);
		}
		//查询列表信息
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			if(si!=null){
				shopInfoId =String.valueOf(si.getShopInfoId());
				//上架商品
				String hql="SELECT a.isShow as isShow, a.productId as productId,a.productFullName as productFullName,a.productName as productName,a.productTypeId as productTypeId,a.salesPrice as salesPrice,a.totalSales as totalSales,a.createDate as createDate,a.isPutSale as isPutSale,c.sortName as sortName,a.logoImg as logoImg,a.storeNumber as storeNumber FROM ProductInfo a,ShopInfo b,ProductType c   WHERE b.shopInfoId="+shopInfoId+" AND b.shopInfoId=a.shopInfoId and a.productTypeId=c.productTypeId AND a.isPutSale=2 and a.isPass=1 ";
				//追加条件语句
				if(productName!=null&&!"".equals(productName)){
					hql+=" and a.productFullName like '%"+productName+"%'";
				}
				if(productTypeId !=null&&productTypeId!=-1){
					hql+=" and a.productTypeId="+productTypeId;
				}
				if(isShowSate !=null&&isShowSate!=-1){
					hql+=" and a.isShow="+isShowSate;
				}
				//追加order by 排序参数
				if(orderByParams!=null&&"Pricehighestfirst".equals(orderByParams)){//价钱
					hql+=" order by a.salesPrice desc";
				}else if(orderByParams!=null&&"Pricelowestfirst".equals(orderByParams)){
					hql+=" order by a.salesPrice ";
				}else if(orderByParams!=null&&"Soldhighestfirst".equals(orderByParams)){//销售量
					hql+=" order by a.totalSales desc ";
				}else if(orderByParams!=null&&"Soldlowestfirst".equals(orderByParams)){
					hql+=" order by a.totalSales  ";
				}else{
					hql+=" order by a.productId desc ,a.updateDate desc";
				}
				List<Map> findListMapByHql = shopProductService.findListMapByHql(hql);
				//计算总条数
				int totalNum=0;
				if(findListMapByHql!=null&&findListMapByHql.size()>0){
					totalNum=findListMapByHql.size(); 
				}
				pageHelper.setPageInfo(pageSize, totalNum, currentPage);
				OnshelfList=shopProductService.findListMapPage(hql, pageHelper);
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询下架商品
	 * @return
	 * @author mqr
	 */
	@SuppressWarnings("rawtypes")
	public String gotoUndercarriageProductListPage(){
		/*//商品申请上架操作
		if(changeisPutSate!=null&&!"".equals(changeisPutSate)){
			changeIsPass(changeisPutSate,3);
		}*/
		/*//商品批量上架操作
		if(bathChangeisPutSate!=null&&!"".equals(bathChangeisPutSate)){
			batchProcessing(bathChangeisPutSate,2);
		}*/
		//删除商品标识
		if(deleteProductObj!=null){
			deleteProduct(deleteProductObj);
		}
		//查询列表信息
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			if(si!=null){
				shopInfoId =String.valueOf(si.getShopInfoId());
				String hql="SELECT a.isShow as isShow, a.productId as productId,a.productFullName as productFullName,a.productName as productName,a.productTypeId as productTypeId,a.salesPrice as salesPrice,a.totalSales as totalSales,a.createDate as createDate,a.isPutSale as isPutSale,c.sortName as sortName,a.logoImg as logoImg,a.isPass as isPass,a.storeNumber as storeNumber FROM ProductInfo a,ShopInfo b,ProductType c   WHERE b.shopInfoId="+shopInfoId+" AND b.shopInfoId=a.shopInfoId and a.productTypeId=c.productTypeId AND a.isPutSale=1 and a.isPass in (0,2)";
				//追加条件语句
				if(productName!=null&&!"".equals(productName)){
					hql+=" and a.productFullName like '%"+productName+"%'";
				}
				if(productTypeId !=null&&productTypeId!=-1){
					hql+=" and a.productTypeId="+productTypeId;
				}
				if(isShowSate !=null&&isShowSate!=-1){
					hql+=" and a.isShow="+isShowSate;
				}
				//追加order by 排序参数
				if(orderByParams!=null&&"Pricehighestfirst".equals(orderByParams)){//价钱
					hql+=" order by a.salesPrice desc";
				}else if(orderByParams!=null&&"Pricelowestfirst".equals(orderByParams)){
					hql+=" order by a.salesPrice ";
				}else if(orderByParams!=null&&"Soldhighestfirst".equals(orderByParams)){//销售量
					hql+=" order by a.totalSales desc ";
				}else if(orderByParams!=null&&"Soldlowestfirst".equals(orderByParams)){
					hql+=" order by a.totalSales  ";
				}else{
					hql+=" order by a.productId desc ,a.updateDate desc";
				}
				List<Map> findListMapByHql = shopProductService.findListMapByHql(hql);
				//计算总条数
				int totalNum=0;
				if(findListMapByHql!=null&&findListMapByHql.size()>0){
					totalNum=findListMapByHql.size(); 
				}
				pageHelper.setPageInfo(pageSize, totalNum, currentPage);
				OffshelfList=shopProductService.findListMapPage(hql, pageHelper);
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询待审核商品
	 * @return
	 * @author mqr
	 */
	@SuppressWarnings("rawtypes")
	public String gotoCheckPendingProductListPage(){
		//查询列表信息
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			if(si!=null){
				shopInfoId =String.valueOf(si.getShopInfoId());
				String hql="SELECT a.isShow as isShow, a.productId as productId,a.productFullName as productFullName,a.productName as productName,a.productTypeId as productTypeId,a.salesPrice as salesPrice,a.totalSales as totalSales,a.createDate as createDate,a.isPutSale as isPutSale,c.sortName as sortName,a.logoImg as logoImg FROM ProductInfo a,ShopInfo b,ProductType c   WHERE b.shopInfoId="+shopInfoId+" AND b.shopInfoId=a.shopInfoId and a.productTypeId=c.productTypeId and a.isPass=3 and a.isPutSale=1";
				//追加条件语句
				if(productName!=null&&!"".equals(productName)){
					hql+=" and a.productFullName like '%"+productName+"%'";
				}
				if(productTypeId !=null&&productTypeId!=-1){
					hql+=" and a.productTypeId="+productTypeId;
				}
				if(isShowSate !=null&&isShowSate!=-1){
					hql+=" and a.isShow="+isShowSate;
				}
				//追加order by 排序参数
				if(orderByParams!=null&&"Pricehighestfirst".equals(orderByParams)){//价钱
					hql+=" order by a.salesPrice desc";
				}else if(orderByParams!=null&&"Pricelowestfirst".equals(orderByParams)){
					hql+=" order by a.salesPrice ";
				}else if(orderByParams!=null&&"Soldhighestfirst".equals(orderByParams)){//销售量
					hql+=" order by a.totalSales desc ";
				}else if(orderByParams!=null&&"Soldlowestfirst".equals(orderByParams)){
					hql+=" order by a.totalSales  ";
				}else{
					hql+=" order by a.productId desc ,a.updateDate desc";
				}
				List<Map> findListMapByHql = shopProductService.findListMapByHql(hql);
				//计算总条数
				int totalNum=0;
				if(findListMapByHql!=null&&findListMapByHql.size()>0){
					totalNum=findListMapByHql.size(); 
				}
				pageHelper.setPageInfo(pageSize, totalNum, currentPage);
				reviewProductList=shopProductService.findListMapPage(hql, pageHelper);
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询违规下架商品
	 * @return
	 * @author mqr
	 */
	@SuppressWarnings("rawtypes")
	public String gotoIllegalProductListPage(){
		//删除商品标识
		if(deleteProductObj!=null){
			deleteProduct(deleteProductObj);
		}
		//查询列表信息
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			if(si!=null){
				shopInfoId =String.valueOf(si.getShopInfoId());
				//下架商品
				String hql="SELECT a.isShow as isShow, a.productId as productId,a.productFullName as productFullName,a.productName as productName,a.productTypeId as productTypeId,a.salesPrice as salesPrice,a.totalSales as totalSales,a.createDate as createDate,a.isPutSale as isPutSale,c.sortName as sortName,a.logoImg as logoImg FROM ProductInfo a,ShopInfo b,ProductType c   WHERE b.shopInfoId="+shopInfoId+" AND b.shopInfoId=a.shopInfoId and a.productTypeId=c.productTypeId and a.isPutSale=3 ";
				//追加条件语句
				if(productName!=null&&!"".equals(productName)){
					hql+=" and a.productFullName like '%"+productName+"%'";
				}
				if(productTypeId !=null&&productTypeId!=-1){
					hql+=" and a.productTypeId="+productTypeId;
				}
				if(isShowSate !=null&&isShowSate!=-1){
					hql+=" and a.isShow="+isShowSate;
				}
				//追加order by 排序参数
				if(orderByParams!=null&&"Pricehighestfirst".equals(orderByParams)){//价钱
					hql+=" order by a.salesPrice desc";
				}else if(orderByParams!=null&&"Pricelowestfirst".equals(orderByParams)){
					hql+=" order by a.salesPrice ";
				}else if(orderByParams!=null&&"Soldhighestfirst".equals(orderByParams)){//销售量
					hql+=" order by a.totalSales desc ";
				}else if(orderByParams!=null&&"Soldlowestfirst".equals(orderByParams)){
					hql+=" order by a.totalSales  ";
				}else{
					hql+=" order by a.productId desc ,a.updateDate desc";
				}
				List<Map> findListMapByHql = shopProductService.findListMapByHql(hql);
				//计算总条数
				int totalNum=0;
				if(findListMapByHql!=null&&findListMapByHql.size()>0){
					totalNum=findListMapByHql.size(); 
				}
				pageHelper.setPageInfo(pageSize, totalNum, currentPage);
				violationOffProductList=shopProductService.findListMapPage(hql, pageHelper);
			}
		}
		return SUCCESS;
	}
	/**
	 * 异步校验预览商品是否属于当前登录用户
	 * @author mf
	 */
	public void checkPreviewInfo() throws IOException{
		//定义一个布尔型变量
		boolean success = false;
		if(productId!=null&&!"".equals(productId)){
			//获取当前登录用户的店铺信息
			ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
			if(shopInfo!=null){
				//校验该商品是否属于当前操作店铺
				Integer count = productInfoService.getCount(" where o.productId="+productId+" and o.shopInfoId="+shopInfo.getShopInfoId());
				if(count>0){
					success=true;
				}
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("success", success);
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**查看标签**/
	public String lookLabels(){
//		llabels = labelsService.findObjects("where o.isShow=2 order by o.sort asc");
		return SUCCESS;
	}
	/**
	 * setter getter
	 * @param shopProductService
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getOnshelfList() {
		return OnshelfList;
	}
	@SuppressWarnings("rawtypes")
	public void setOnshelfList(List<Map> onshelfList) {
		OnshelfList = onshelfList;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getOffshelfList() {
		return OffshelfList;
	}
	@SuppressWarnings("rawtypes")
	public void setOffshelfList(List<Map> offshelfList) {
		OffshelfList = offshelfList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParamsCheckbox() {
		return paramsCheckbox;
	}
	public void setParamsCheckbox(String paramsCheckbox) {
		this.paramsCheckbox = paramsCheckbox;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setShopProductService(IShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getReviewProductList() {
		return reviewProductList;
	}
	@SuppressWarnings("rawtypes")
	public void setReviewProductList(List<Map> reviewProductList) {
		this.reviewProductList = reviewProductList;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getViolationOffProductList() {
		return violationOffProductList;
	}
	@SuppressWarnings("rawtypes")
	public void setViolationOffProductList(List<Map> violationOffProductList) {
		this.violationOffProductList = violationOffProductList;
	}
	public String getDeleteParams() {
		return deleteParams;
	}
	public void setDeleteParams(String deleteParams) {
		this.deleteParams = deleteParams;
	}
	public Integer getIsShowSate() {
		return isShowSate;
	}
	public void setIsShowSate(Integer isShowSate) {
		this.isShowSate = isShowSate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeIds() {
		return productTypeIds;
	}
	public void setProductTypeIds(String productTypeIds) {
		this.productTypeIds = productTypeIds;
	}
	public String getOrderByParams() {
		return orderByParams;
	}
	public void setOrderByParams(String orderByParams) {
		this.orderByParams = orderByParams;
	}
	public String getChangeShowSate() {
		return changeShowSate;
	}
	public void setChangeShowSate(String changeShowSate) {
		this.changeShowSate = changeShowSate;
	}
	public String getChangeisPutSate() {
		return changeisPutSate;
	}
	public void setChangeisPutSate(String changeisPutSate) {
		this.changeisPutSate = changeisPutSate;
	}
	public String getBathChangeisPutSate() {
		return bathChangeisPutSate;
	}
	public void setBathChangeisPutSate(String bathChangeisPutSate) {
		this.bathChangeisPutSate = bathChangeisPutSate;
	}
	public Integer getDeleteProductObj() {
		return deleteProductObj;
	}
	public void setDeleteProductObj(Integer deleteProductObj) {
		this.deleteProductObj = deleteProductObj;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
} 
