package tuan.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import tuan.pojo.TuanProduct;
import tuan.pojo.TuanProductType;
import tuan.service.ITuanProductService;
import tuan.service.ITuanProductTypeService;
import util.action.BaseAction;
import util.other.QRCode;
import util.other.Utils;
import util.upload.ImageFileUploadUtil;
/**
 * 团购商品Action
 * @author 
 *
 */
@SuppressWarnings("serial")
public class TuanProductAction extends BaseAction {
	/**
	 * 团购商品分类Service
	 */
	private ITuanProductTypeService tuanProductTypeService;
	/**
	 * 团购商品Service
	 */
	private ITuanProductService tuanProductService;
	/**
 	 * 店铺Service 
	 */
	private IShopInfoService shopInfoService;
	/**
	 * 团购商品分类集合
	 */
	private List<TuanProductType> tuanProductTypeList = new ArrayList<TuanProductType>();
	/**
	 * 团购商品List
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> tuanProductList = new ArrayList<Map>();
	/**
	 * 团购商品实体类
	 */
	private TuanProduct tuanProduct;
	/**
	 * 团购商品ID
	 */
	private String tuanId;
	/**
	 * 商品ID
	 */
	private String productId;
	//上传图片
	private File txImage;
	private String txImageFileName;
	private String txImageContentType;
	private String tableIndex;
	
	//跳转团购商品申请页面
	public String gotoGroupApply(){
		tuanProductTypeList = tuanProductTypeService.findObjects(" order by o.sortCode asc");
		return SUCCESS;
	}
	
	/**
	 * 异步校验参团商品
	 * @throws IOException 
	 */
	public void checkTuanProduct() throws IOException{
		boolean isExsit = true;
		//查询团购商品实体类
		int count = tuanProductService.getCount(" where o.state in (0,1) and o.productId="+productId);
		if(count!=0){
			isExsit = false;
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isExsit", isExsit);
		response.setContentType("text/html,");
		PrintWriter pw;
		pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	
	/**
	 * 保存团购申请商品
	 */
	public String saveOrUpdateGroupProduct(){
		if(Utils.objectIsNotEmpty(tuanProduct)){
			ShopInfo shopInfo =(ShopInfo)session.getAttribute("shopInfo");
			tuanProduct.setShopInfoId(shopInfo.getShopInfoId());
			tuanProduct.setCreateTime(new Date());
			tuanProduct.setState(0);
			tuanProduct.setBought(0);
			tuanProductService.saveOrUpdateObject(tuanProduct);
		}
		return SUCCESS;
	}
	
	/**
	 * 上传图片
	 * @throws IOException
	 * @throws Exception
	 */
	public void uploadImage() throws IOException {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		out = response.getWriter();
		String returnImagePathFileName = ImageFileUploadUtil.uploadImageFile(txImage, txImageFileName, fileUrlConfig, "image_group");
		if (txImageFileName.equals(returnImagePathFileName)|| txImageFileName.equals("图片上传失败!")) {
			jo.accumulate("photoUrl", "false");
		} else {
			jo.accumulate("photoUrl", returnImagePathFileName);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 团购商品列表
	 */
	public String gotoTuanProductListPage(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si = (ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			if(si!=null){
				String hql="select t.tuanId as tuanId, t.tuanImageUrl as tuanImageUrl,t.title as title,t.price as price,t.tuanPeriod as tuanPeriod,t.createTime as createTime,t.beginTime as beginTime,t.endTime as endTime,t.openGroupCount as openGroupCount,t.state as state,t.bought as bought,"
						 + "b.sortName as sortName,p.productFullName as productFullName from TuanProduct t,TuanProductType b,ProductInfo p where t.shopInfoId="+si.getShopInfoId()+" and t.productId=p.productId and t.tuanProductTypeId=b.tuanProductTypeId";
				int totalRecordCount = tuanProductService.getCount(" where o.shopInfoId="+si.getShopInfoId());
				//追加条件语句
			/*	if(productName!=null&&!"".equals(productName)){
					hql+=" and a.productFullName like '%"+productName+"%'";
				}
				if(productTypeId !=null&&productTypeId!=-1){
					hql+=" and a.productTypeId="+productTypeId;
				}
				if(isShowSate !=null&&isShowSate!=-1){
					hql+=" and a.isShow="+isShowSate;
				}*/
				//追加order by 排序参数
		/*		if(orderByParams!=null&&"Pricehighestfirst".equals(orderByParams)){//价钱
					hql+=" order by a.salesPrice desc";
				}else if(orderByParams!=null&&"Pricelowestfirst".equals(orderByParams)){
					hql+=" order by a.salesPrice ";
				}else if(orderByParams!=null&&"Soldhighestfirst".equals(orderByParams)){//销售量
					hql+=" order by a.totalSales desc ";
				}else if(orderByParams!=null&&"Soldlowestfirst".equals(orderByParams)){
					hql+=" order by a.totalSales  ";
				}else{
					hql+=" order by a.productId desc ,a.updateDate desc";
				}*/
				pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
				tuanProductList=tuanProductService.findListMapPage(hql+" order by t.createTime desc", pageHelper);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 开启团购商品
	 * @throws ParseException 
	 */
	public void updateStateOpen() throws ParseException{
		tuanProduct = (TuanProduct) tuanProductService.getObjectByParams(" where o.tuanId="+tuanId);
		tuanProduct.setState(1);//开启
		int days = tuanProduct.getTuanPeriod();
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strEndTime = "";
		strEndTime = df.format(new Date(date.getTime()+days*24*60*60*1000));
		tuanProduct.setBeginTime(date);//开启时间
		tuanProduct.setEndTime(df.parse(strEndTime));//结束时间
		String basePath = (String) fileUrlConfig.get("phoneBasePath");
		String addUrl = basePath+"/phone/tuan/gotoTuanProductInfo.action?productId="+tuanProduct.getProductId();
		String codeUrl = QRCode.createQRPng(addUrl, null, fileUrlConfig, "shop" ,"image_group");
		tuanProduct.setQrCode(codeUrl);
		tuanProduct = (TuanProduct) tuanProductService.saveOrUpdateObject(tuanProduct);
	}
	
	/**
	 * 关闭团购商品
	 */
	public void updateStateClose(){
		tuanProduct = (TuanProduct) tuanProductService.getObjectByParams(" where o.tuanId="+tuanId);
		tuanProduct.setState(2);//关闭
		tuanProduct = (TuanProduct) tuanProductService.saveOrUpdateObject(tuanProduct);
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getTuanProductList() {
		return tuanProductList;
	}
	@SuppressWarnings("rawtypes")
	public void setTuanProductList(List<Map> tuanProductList) {
		this.tuanProductList = tuanProductList;
	}
	public TuanProduct getTuanProduct() {
		return tuanProduct;
	}
	public void setTuanProduct(TuanProduct tuanProduct) {
		this.tuanProduct = tuanProduct;
	}
	public String getTuanId() {
		return tuanId;
	}
	public void setTuanId(String tuanId) {
		this.tuanId = tuanId;
	}
	public void setTuanProductService(ITuanProductService tuanProductService) {
		this.tuanProductService = tuanProductService;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setTuanProductTypeService(
			ITuanProductTypeService tuanProductTypeService) {
		this.tuanProductTypeService = tuanProductTypeService;
	}
	public List<TuanProductType> getTuanProductTypeList() {
		return tuanProductTypeList;
	}
	public void setTuanProductTypeList(List<TuanProductType> tuanProductTypeList) {
		this.tuanProductTypeList = tuanProductTypeList;
	}
	public File getTxImage() {
		return txImage;
	}
	public void setTxImage(File txImage) {
		this.txImage = txImage;
	}
	public String getTxImageFileName() {
		return txImageFileName;
	}
	public void setTxImageFileName(String txImageFileName) {
		this.txImageFileName = txImageFileName;
	}
	public String getTxImageContentType() {
		return txImageContentType;
	}
	public void setTxImageContentType(String txImageContentType) {
		this.txImageContentType = txImageContentType;
	}
	public String getTableIndex() {
		return tableIndex;
	}
	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
