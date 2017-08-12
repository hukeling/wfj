package discountcoupon.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.FileUtils;

import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.IDiscountCouponService;
/**
 * DiscountCouponAction - 优惠券信息Action
 */
@SuppressWarnings("all")
public class DiscountCouponAction extends BaseAction {
	private IDiscountCouponService discountCouponService;//优惠券Service
	private List<DiscountCoupon> discountCouponList = new ArrayList<DiscountCoupon>();//优惠券List
	private List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();//店铺信息List
	private IShopInfoService shopInfoService;//店铺Service
	private DiscountCoupon discountCoupon;//优惠券对象
	private String ids;
	private String headerType;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	//跳转到优惠券列表页面
	public String gotofrontcoupon(){
		StringBuffer hqlsb = new StringBuffer();
		Calendar rightNow = Calendar.getInstance(); 
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式大小写有区别
		String sysDatetime = fmt.format(rightNow.getTime());
		hqlsb.append(" where isPass=2 and useStatus=1 and expirationTime > '"+sysDatetime+"' order by discountCouponID desc");
//		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" discountCouponID desc"));
		int totalRecordCount = discountCouponService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		discountCouponList = discountCouponService.findListByPageHelper(null,pageHelper, hqlsb.toString());
//		headerType=request.getAttribute("");
		//初始化店铺记录
//		shopInfoList = shopInfoService.findObjects(" where o.isClose = '0'");
		return SUCCESS;
	}
	//查询所有的优惠券记录
	public void listDiscountCouponApply() throws IOException {
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String shopInfoId = request.getParameter("shopInfoId");
			String discountCouponAmount = request.getParameter("discountCouponAmount");
			String beginTime = request.getParameter("beginTime");
			String expirationTime = request.getParameter("expirationTime");
			String createTime = request.getParameter("createTime");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(!"-1".equals(shopInfoId)){
				sb.append(CreateWhereSQLForSelect.appendLike("shopInfoId","like",shopInfoId));
			}
			if(discountCouponAmount!=null&&!"".equals(discountCouponAmount)){
				sb.append(CreateWhereSQLForSelect.appendLike("discountCouponAmount","like",discountCouponAmount));
			}
			if(beginTime!=null&&!"".equals(beginTime)){
				sb.append(CreateWhereSQLForSelect.appendLike("beginTime","like",beginTime));
			}
			if(expirationTime!=null&&!"".equals(expirationTime)){
				sb.append(CreateWhereSQLForSelect.appendLike("expirationTime","like",expirationTime));
			}
			if(createTime!=null&&!"".equals(createTime)){
				sb.append(CreateWhereSQLForSelect.appendLike("createTime","like",createTime));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" discountCouponID desc"));
		int totalRecordCount = discountCouponService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		discountCouponList = discountCouponService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", discountCouponList);
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//添加或修改优惠券
	public void saveOrUpdateDiscountCoupon() throws IOException, ParseException{
		Date createTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatTime = sdf.format(createTime);
		Date createTimes = sdf.parse(formatTime);
		if(discountCoupon!=null){
			discountCoupon.setCreateTime(createTimes);
			discountCoupon = (DiscountCoupon) discountCouponService.saveOrUpdateObject(discountCoupon);
			if(discountCoupon.getDiscountCouponID()!=null){
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
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf(".") + 1);
			if ("jpg".equals(typeStr) || "JPG".equals(typeStr) || "png".equals(typeStr) || "PNG".equals(typeStr) || "GIF".equals(typeStr) ||"gif".equals(typeStr) || "".equals(typeStr)) {
				// 需要修改图片的存放地址
				String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
				newName = UUID.randomUUID() + newName;
				File savefile = new File(new File((String) fileUrlConfig.get("fileUploadRoot") + "/"+ (String) fileUrlConfig.get("shop") + "/image/discountcoupon"), newName);
				if (!savefile.getParentFile().exists()) {
					savefile.getParentFile().mkdirs();
				}
				FileUtils.copyFile(imagePath, savefile);
				imagePathFileName = (String) fileUrlConfig.get("shop") + "/image/discountcoupon/" + newName;
				jo.accumulate("photoUrl", imagePathFileName);
				jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
			} else {
				jo.accumulate("photoUrl", "false2");
			}
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//批量删除
	public void deleteDiscountCoupon() throws IOException{
		Boolean isSuccess = discountCouponService.deleteObjectsByIds("discountCouponID",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//获取一条记录
	public void getDiscountCouponById() throws IOException{
		discountCoupon = (DiscountCoupon) discountCouponService.getObjectByParams(" where o.discountCouponID='"+ids+"'");
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(discountCoupon,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
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
	public List<ShopInfo> getShopInfoList() {
		return shopInfoList;
	}
	public void setShopInfoList(List<ShopInfo> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}
	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
}
