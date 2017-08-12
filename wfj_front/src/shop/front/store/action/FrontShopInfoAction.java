package shop.front.store.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import promotion.service.IStoreApplyPromotionService;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.front.store.pojo.OrdersHomeStatus;
import shop.order.service.IOrdersService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopCategory;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopCategoryService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.Utils;
import util.upload.ImageFileUploadUtil;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
/**
 * ShopInfoAction - 前台店铺申请信息
 * @Author:§j*fm§
 */
@SuppressWarnings({ "serial", "unused" })
public class FrontShopInfoAction extends BaseAction {
	private IShopInfoService shopInfoService;//店铺基本信息Service
	private List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();//店铺信息列表
	@SuppressWarnings("rawtypes")
	private List companyRegisteredList = new ArrayList();//店铺信息列表
	private ShopInfo shopInfo;//店铺基本信息实体
	private String[] mainProduct = new String[4];
	private String nationalIso;
	@SuppressWarnings("rawtypes")
	private List<Map> countrys; //国家
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**店铺分类**/
	private IShopCategoryService shopCategoryService ;
	/**店铺分类集合**/
	private List<ShopCategory> listShopCate = new ArrayList<ShopCategory>();
	/**用户信息**/
	private Customer customer;
	/**订单的service**/
	private IOrdersService ordersService;
	/**商品信息service**/
	private IProductInfoService productInfoService ;
	/**店铺促销活动service**/
	private IStoreApplyPromotionService storeApplyPromotionService;
	/**等待发货订单**/
	@SuppressWarnings("rawtypes")
	private List<Map> waitOrdersList;
	/**会员service**/
	private ICustomerService customerService;
	/**店铺首页显示的几种状态数量**/
	private OrdersHomeStatus ordersHomeStatus = new OrdersHomeStatus();
	/**查看店铺申请信息标识**/
	private String viewFlag;
	/**区域service**/
	private IAreaService areaService;
	/**一级地址**/
	@SuppressWarnings("rawtypes")
	private List<Map> firstAreaList;
	/**公司注册地址**/
	private String companyAddress;
	/**------------------------------------------------end------------------------------------------------------------------**/
	/**
	 * 申请开店和查询店铺申请信息
	 */
	public String gotoFrontShopInfoPage() {
		//检测当前登录用户是否已经开店
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo si = (ShopInfo) shopInfoService.getObjectByParams(" where o.customerId = "+customer.getCustomerId());
		//更新shopInfo信息到session中 平台修改店铺审核状态后 可以立马在前台体现
		session.setAttribute("shopInfo", si);
		if(si!=null&&si.getShopInfoCheckSatus()!=null&&si.getShopInfoCheckSatus()==3){//当前用户是否已经开启店铺
			return "isShop";
		}else{
			try{
				if(customer!=null){
					shopInfo = si;
				}
				//店铺分类
				List<ShopCategory> oldProdTypeList = shopCategoryService.findObjects(" where o.parentId=1");//一级分类
				for(ShopCategory prodType:oldProdTypeList){
					listShopCate.add(prodType);//添加一级分类
					int i=listShopCate.indexOf(prodType);//获取当前一级下标
					List<ShopCategory> listProductType2=shopCategoryService.findObjects(" where o.parentId="+prodType.getShopCategoryId());//所属二级
					for(ShopCategory prodType2:listProductType2){
						List<ShopCategory> listProductType3=shopCategoryService.findObjects(" where o.parentId="+prodType2.getShopCategoryId());//所属三级
						listShopCate.add(i+1, prodType2);//添加一级下的二级分类
						int j=listShopCate.indexOf(prodType2);//获取当前二级分类的下标
						for(ShopCategory prodType3:listProductType3){
							listShopCate.add(j+1, prodType3);//添加二级下的三级分类
						}
					}
				}
				//一级地区parent=0
				String firstHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
				firstAreaList=areaService.findListMapByHql(firstHql);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(viewFlag!=null){
			return "view";
		}else{
			return SUCCESS;
		}
	}
	/**
	 * 查看店铺信息
	 * @return
	 */
	public String showShopInfo(){
		customer =  (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
			if(shopInfo!=null){
				//申请开店
				if(Utils.stringIsNotEmpty(shopInfo.getRegionLocation())){
					shopInfo.setRegionLocation(getShopAddress(Integer.parseInt(shopInfo.getRegionLocation())));
				}
				if(Utils.stringIsNotEmpty(shopInfo.getCity())){
					shopInfo.setCity(getShopAddress(Integer.parseInt(shopInfo.getCity())));
				}
				if(Utils.stringIsNotEmpty(shopInfo.getAreaCounty())){
					shopInfo.setAreaCounty(getShopAddress(Integer.parseInt(shopInfo.getAreaCounty())));
				}
			}
		}
		//店铺分类
		List<ShopCategory> oldProdTypeList = shopCategoryService.findObjects(" where o.parentId=1");//一级分类
		for(ShopCategory prodType:oldProdTypeList){
			listShopCate.add(prodType);//添加一级分类
			int i=listShopCate.indexOf(prodType);//获取当前一级下标
			List<ShopCategory> listProductType2=shopCategoryService.findObjects(" where o.parentId="+prodType.getShopCategoryId());//所属二级
			for(ShopCategory prodType2:listProductType2){
				List<ShopCategory> listProductType3=shopCategoryService.findObjects(" where o.parentId="+prodType2.getShopCategoryId());//所属三级
				listShopCate.add(i+1, prodType2);//添加一级下的二级分类
				int j=listShopCate.indexOf(prodType2);//获取当前二级分类的下标
				for(ShopCategory prodType3:listProductType3){
					listShopCate.add(j+1, prodType3);//添加二级下的三级分类
				}
			}
		}
		//一级地区parent=0
		String firstHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		firstAreaList=areaService.findListMapByHql(firstHql);
		return SUCCESS;
	}
	//保存店铺申请数据
	public String saveOrUpdateShopInfo() throws IOException{
		//检测当前登录用户是否已经开店
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo si=(ShopInfo) session.getAttribute("shopInfo");
		if(si!=null){
			if(si.getShopInfoCheckSatus()!=null&&si.getShopInfoCheckSatus()==1){//企业审核状态
				return SUCCESS;
			}else{
				if(customer.getType()==2){
					//si.setShopName(shopInfo.getShopName());//店铺名乱码
					//si.setMainProduct(shopInfo.getMainProduct());//主要销售产品乱码
					si.setMarketBrandUrl(shopInfo.getMarketBrandUrl());//销售品牌LOGO
					si.setCustomerName(customer.getLoginName());
					//si.setShopCategoryId(shopInfo.getShopCategoryId());
					//si.setIsPass(1);//是否通过审核
					//si.setIsClose(0);//是否关闭
				}
				if(Utils.objectIsNotEmpty(shopInfo.getShopInfoCheckSatus())){
					if(shopInfo.getShopInfoCheckSatus()!=2){
						si.setShopInfoCheckSatus(1);//待审核
					}
				}else{
					si.setShopInfoCheckSatus(1);//待审核
				}
				si.setBusinessLicense(shopInfo.getBusinessLicense());//税务登记
				si.setCompanyDocuments(shopInfo.getCompanyDocuments());//组织机构代码证
				si.setTaxRegistrationDocuments(shopInfo.getTaxRegistrationDocuments());//营业执照
				si.setPostCode(shopInfo.getPostCode());//邮政编码
				si.setCompanyName(shopInfo.getCompanyName());//公司名称
//				si.setRegionLocation(shopInfo.getRegionLocation());//省
//				si.setCity(shopInfo.getCity());//市
//				si.setAreaCounty(shopInfo.getAreaCounty());//地区
				si.setAddress(shopInfo.getAddress());//详细地址
				shopInfo = (ShopInfo) shopInfoService.saveOrUpdateObject(si);
			}
		}else{
			ShopInfo s = new ShopInfo();
			if(customer.getType()==3){
				s.setShopName(shopInfo.getShopName());//店铺名乱码
				s.setMainProduct(shopInfo.getMainProduct());//主要销售产品乱码
				s.setIsPass(1);//是否通过审核
				s.setIsClose(0);//是否关闭
				s.setTemplateSet(1);
			}
			s.setCustomerId(customer.getCustomerId());
			s.setCustomerName(customer.getLoginName());
			s.setShopInfoCheckSatus(1);//待审核
			if(customer.getType()==2){
				s.setMarketBrandUrl(shopInfo.getMarketBrandUrl());//销售品牌LOGO
			}
			s.setBusinessLicense(shopInfo.getBusinessLicense());//税务登记
			s.setCompanyDocuments(shopInfo.getCompanyDocuments());//组织机构代码证
			s.setTaxRegistrationDocuments(shopInfo.getTaxRegistrationDocuments());//营业执照
			s.setPostCode(shopInfo.getPostCode());
			s.setCompanyName(shopInfo.getCompanyName());
			shopInfo = (ShopInfo) shopInfoService.saveOrUpdateObject(s);
		}
		if(shopInfo.getShopInfoId()!=null){
			session.setAttribute("shopInfo", shopInfo);
			return SUCCESS;
		}
		return "";
	}
	// 异步ajax 图片上传
	public void uploadImageFront() throws IOException  {
		JSONObject jo = new JSONObject();
		PrintWriter out;
		out = response.getWriter();
		String returnImagePathFileName=	ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_shopInfo");
		if(imagePathFileName.equals(returnImagePathFileName) || imagePathFileName.equals("图片上传失败!")){
			jo.accumulate("photoUrl", "false");
		}else{
			jo.accumulate("photoUrl", returnImagePathFileName);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		}
		response.setContentType("text/html;charset=utf-8");
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 前台卖家登录之后页面，卖家中心
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String goToShopLoginHome(){
		customer = (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
			//等待确认配送的订单
			pageHelper.setPageRecordBeginIndex(0);
			pageHelper.setPageSize(5);
			if(Utils.objectIsNotEmpty(shopInfo)){
				String hql="select a.ordersId as ordersId,a.ordersNo as ordersNo,a.shopInfoId as shopInfoId,a.customerId as customerId," +
						" a.createTime as createTime ,a.ordersState as ordersState,a.finalAmount as finalAmount ,b.loginName as loginName ,a.settlementStatus as settlementStatus" +
						" from Orders a ,Customer b where a.ordersState=0 and a.settlementStatus=1 and a.customerId=b.customerId and a.shopInfoId ="+shopInfo.getShopInfoId()+" order by a.createTime desc ";
				waitOrdersList = ordersService.findListMapPage(hql, pageHelper);
			//等待确认配送数量
			Integer waitNum=ordersService.getCount(" where o.settlementStatus=1 and o.shopInfoId ="+shopInfo.getShopInfoId());
			ordersHomeStatus.setWaitingNum(waitNum);
			//退货数量
			Integer refundNum=ordersService.getCount(" where o.ordersState =7 and o.shopInfoId ="+shopInfo.getShopInfoId());
			ordersHomeStatus.setRefundNum(refundNum);
			//上架数量
			String onShelfHql="select COUNT(a.productId) as onShelfNum from ProductInfo a ,ShopInfo b where a.shopInfoId = b.shopInfoId and a.isPutSale =2 and a.isPass=1 and b.shopInfoId="+shopInfo.getShopInfoId();
			List<Map> onShelfList = productInfoService.findListMapByHql(onShelfHql);
			if(null!=onShelfList && onShelfList.size()>0){
				Map map1=onShelfList.get(0);
				ordersHomeStatus.setOnShelfNum(Integer.parseInt(String.valueOf(map1.get("onShelfNum"))));
			}else{
				ordersHomeStatus.setOnShelfNum(0);
			}
			//下架数量
			String offShelfHql="select COUNT(a.productId) as offShelfNum FROM ProductInfo a,ShopInfo b,ProductType c   WHERE b.shopInfoId="+shopInfo.getShopInfoId()+" AND b.shopInfoId=a.shopInfoId and a.productTypeId=c.productTypeId AND a.isPutSale=1 and a.isPass in (0,2,3)";
			List<Map> offShelfList = productInfoService.findListMapByHql(offShelfHql);
			if(null!=offShelfList && offShelfList.size()>0){
				Map map1=offShelfList.get(0);
				ordersHomeStatus.setOffShelfNum(Integer.parseInt(String.valueOf(map1.get("offShelfNum"))));
			}else{
				ordersHomeStatus.setOffShelfNum(0);
			}
			//参与活动数量
			Integer activeNum= storeApplyPromotionService.getCount(" where  o.promotionState=1 and o.shopInfoId ="+shopInfo.getShopInfoId());
			ordersHomeStatus.setActivityNum(activeNum);
			}
		}
		return SUCCESS;
	}
	/**
	 * 前台店铺的基本信息(添加或者修改页面)
	 * @return
	 */
	public String sellerBasicInfo(){
		Customer customer =  (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
			//更新shopInfo信息到session中 平台修改店铺审核状态后 可以立马在前台体现
			session.setAttribute("shopInfo", shopInfo);
		}
		listShopCate = shopCategoryService.findObjects(" where o.parentId>0");
		return SUCCESS;
	}
	/**
	 * 保存店铺基本信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String saveOrUpdateSellerInfo() throws UnsupportedEncodingException{
		ShopInfo sessionShopInfo = (ShopInfo) session.getAttribute("shopInfo");
		if(shopInfo!=null&&sessionShopInfo!=null){
			ShopInfo shop = new ShopInfo();
			shop = (ShopInfo) shopInfoService.getObjectByParams("where o.shopInfoId ="+sessionShopInfo.getShopInfoId());
			shop.setShopName(shopInfo.getShopName());
			shop.setShopCategoryId(shopInfo.getShopCategoryId());
			shop.setLogoUrl(shopInfo.getLogoUrl());
			shop.setSynopsis(shopInfo.getSynopsis());
			shop.setBusinessHoursStart(shopInfo.getBusinessHoursStart());
			shop.setBusinessHoursEnd(shopInfo.getBusinessHoursEnd());
			shop.setTemplateSet(shopInfo.getTemplateSet());
			shop.setBannerUrl(shopInfo.getBannerUrl());
			shop.setIsPass(1);//当店铺信息做修改操作是 将其改为待审核状态 锁定
			shop.setMainProduct(shopInfo.getMainProduct());
			shop.setAddressForInvoice(shopInfo.getAddressForInvoice());//店铺地址
			shop.setPhone(shopInfo.getPhone());//店铺电话
			shop.setMinAmount(shopInfo.getMinAmount());//最小订单金额
			shop.setPostage(shopInfo.getPostage());//邮费
			shopInfo=(ShopInfo) shopInfoService.saveOrUpdateObject(shop);
			//当店铺信息成功更新后 需要将新的shopInfo存入到session域中
			if(shopInfo!=null){
				session.setAttribute("shopInfo", shopInfo);
			}
		}
		return SUCCESS;
	}
	/**
	 * 通过区域Id查找店铺地址
	 * @param 区域Id
	 * @return区域名称
	 */
	public String getShopAddress(Integer areaId){
		String aereName="";
		try{
			if(null!=areaId){
				BasicArea basicArea = (BasicArea) areaService.getObjectByParams(" where o.areaId="+areaId);
				if(basicArea!=null && basicArea.getName()!=null){
					aereName = basicArea.getName();
				}
			}
			return aereName;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 通过地址Code查找地址区域
	 * @return
	 */
	public ShopInfo findShopAddressByCode(String areaCode){
		BasicArea basicArea = (BasicArea) areaService.getObjectByParams(" where o.areaCode='"+areaCode+"'");
		ShopInfo shop = new ShopInfo();
		if(!",".equals(basicArea.getTreePath())){
			String treePath = basicArea.getTreePath().substring(1, basicArea.getTreePath().lastIndexOf(","));
			String[] area = treePath.split(",");
			shop.setRegionLocation(area[0]);
			if(area.length==2){
				shop.setCity(area[1]);
				shop.setAreaCounty(String.valueOf(basicArea.getAreaId()));
			}else{
				shop.setCity(String.valueOf(basicArea.getAreaId()));
			}
		}else{
			shop.setRegionLocation(String.valueOf(basicArea.getAreaId()));
		}
		return shop;
	}
	/**
	 * 校验用户企业名称唯一性
	 * @return
	 */
	public void checkCompanyName()throws IOException{
		String companyName = request.getParameter("companyName");
		if(StringUtils.isNotEmpty(companyName)){
			Integer count = shopInfoService.getCount(" where o.companyName='"+companyName+"'");
			JSONObject jo=new JSONObject();
			if(count>0){
				jo.accumulate("isSuccess","false");
			}else{
				jo.accumulate("isSuccess","true");
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**
	 * 校验用户企业名称唯一性
	 * @return
	 */
	public void checkShopName()throws IOException{
		String shopName = request.getParameter("shopName");
		if(StringUtils.isNotEmpty(shopName)){
			Integer count = shopInfoService.getCount(" where o.shopName='"+shopName+"'");
			JSONObject jo=new JSONObject();
			if(count>0){
				jo.accumulate("isSuccess","false");
			}else{
				jo.accumulate("isSuccess","true");
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	public List<ShopInfo> getShopInfoList() {
		return shopInfoList;
	}
	public void setShopInfoList(List<ShopInfo> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}
	@SuppressWarnings("rawtypes")
	public List getCompanyRegisteredList() {
		return companyRegisteredList;
	}
	@SuppressWarnings("rawtypes")
	public void setCompanyRegisteredList(List companyRegisteredList) {
		this.companyRegisteredList = companyRegisteredList;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public String getNationalIso() {
		return nationalIso;
	}
	public void setNationalIso(String nationalIso) {
		this.nationalIso = nationalIso;
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
	public String[] getMainProduct() {
		return mainProduct;
	}
	public void setMainProduct(String[] mainProduct) {
		this.mainProduct = mainProduct;
	}
	public List<ShopCategory> getListShopCate() {
		return listShopCate;
	}
	public void setListShopCate(List<ShopCategory> listShopCate) {
		this.listShopCate = listShopCate;
	}
	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getWaitOrdersList() {
		return waitOrdersList;
	}
	@SuppressWarnings("rawtypes")
	public void setWaitOrdersList(List<Map> waitOrdersList) {
		this.waitOrdersList = waitOrdersList;
	}
	public OrdersHomeStatus getOrdersHomeStatus() {
		return ordersHomeStatus;
	}
	public void setOrdersHomeStatus(OrdersHomeStatus ordersHomeStatus) {
		this.ordersHomeStatus = ordersHomeStatus;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setStoreApplyPromotionService(
			IStoreApplyPromotionService storeApplyPromotionService) {
		this.storeApplyPromotionService = storeApplyPromotionService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public String getViewFlag() {
		return viewFlag;
	}
	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getCountrys() {
		return countrys;
	}
	@SuppressWarnings("rawtypes")
	public void setCountrys(List<Map> countrys) {
		this.countrys = countrys;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getFirstAreaList() {
		return firstAreaList;
	}
	@SuppressWarnings("rawtypes")
	public void setFirstAreaList(List<Map> firstAreaList) {
		this.firstAreaList = firstAreaList;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
