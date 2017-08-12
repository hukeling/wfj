package shop.front.coupon.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.upload.ImageFileUploadUtil;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.IDiscountCouponService;
/**
 * CouponAction - 店铺发放优惠券信息Action
 * @author XHT
 */
@SuppressWarnings("all")
public class ShopCouponAction extends BaseAction{
	/** 商品优惠券Service **/
	private IDiscountCouponService discountCouponService;
	/** 店铺Service **/
	private IShopInfoService shopInfoService;
	/** 商品优惠券实体类 **/
	private DiscountCoupon discountCoupon;
	/** 优惠劵集合 **/
	private List<DiscountCoupon> discountCouponList = new ArrayList<DiscountCoupon>();
	/** 分页每页数目 **/
	private Integer pageSize = 5;
	/** 分页ID **/
	private String pageIndex = "1";
	/** 优惠券ID **/
	private String discountCouponID;
	/** 上传文件路径 **/
	private File imagePath;
	/** 上传文件名称 **/
	private String imagePathFileName;
	/**
	 * 查询跳转
	 */
	public String gotoShopCoupon(){
		Customer customer =  (Customer) session.getAttribute("customer");
		if(customer!=null){
			ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.customerId = "+customer.getCustomerId());
			if(shopInfo!=null){
				int totalRecordCount=discountCouponService.getCount(" where o.shopInfoId ="+shopInfo.getShopInfoId());
				pageHelper.setPageInfo(pageSize, totalRecordCount,currentPage);
				discountCouponList = discountCouponService.findListByPageHelper(null, pageHelper," where o.shopInfoId ="+shopInfo.getShopInfoId()+" order by o.createTime desc");
				return SUCCESS;
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	/**
	 * 删除
	 */
	public void deleteFrontShopCoupon() throws IOException{
		discountCouponService.deleteObjectByParams(" where o.discountCouponID ="+discountCouponID);
		String state = "on";
		JSONObject jo = new JSONObject();
		jo.accumulate("state", state);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 保存优惠券
	 * @throws ParseException 
	 */
	public void saveFrontShopCoupon() throws IOException, ParseException{
		if(discountCoupon!=null){
			Customer customer =  (Customer) session.getAttribute("customer");
			ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId = "+customer.getCustomerId());
			//discountCoupon.setShopInfoId(shopInfo.getShopInfoId());
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date1 = dateformat.format(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date2 = sdf.parse(date1);
			discountCoupon.setCreateTime(date2);
			discountCouponService.saveOrUpdateObject(discountCoupon);
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**
	 * 修改优惠券
	 */
	public void changeCoupon() throws IOException{
		discountCoupon = (DiscountCoupon) discountCouponService.getObjectById(discountCouponID);
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(discountCoupon,jsonConfig);
		jo.accumulate("isSuccess", "true");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 异步ajax 图片上传
	 */
	public void uploadImageFront() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_discountCoupon");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}
	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}
	public void setDiscountCouponService(
			IDiscountCouponService discountCouponService) {
		this.discountCouponService = discountCouponService;
	}
	public List<DiscountCoupon> getDiscountCouponList() {
		return discountCouponList;
	}
	public void setDiscountCouponList(List<DiscountCoupon> discountCouponList) {
		this.discountCouponList = discountCouponList;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getDiscountCouponID() {
		return discountCouponID;
	}
	public void setDiscountCouponID(String discountCouponID) {
		this.discountCouponID = discountCouponID;
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
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
