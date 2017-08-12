package promotion.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import promotion.pojo.PlatformPromotion;
import promotion.pojo.SalesPromotionDiscount;
import promotion.service.IPlatformPromotionService;
import promotion.service.ISalesPromotionDiscountService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import util.upload.ImageFileUploadUtil;
import basic.pojo.Users;
/** 
* PromotionAction - 平台管理促销活动Action类
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author ss,孟琦瑞
*/ 
@SuppressWarnings("all")
public class PromotionAction extends BaseAction {
	private IPlatformPromotionService platformPromotionService;//促销活动Service
	private ISalesPromotionDiscountService salesPromotionDiscountService;//促销活动折扣Service
	private PlatformPromotion platformPromotion;
	private SalesPromotionDiscount salesPromotionDiscount;
	private List<PlatformPromotion> platformPromotionList = new ArrayList<PlatformPromotion>();//促销活动List
	private String ids;
	private String promotionId;
	private Integer isPass;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_promotion");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转到促销活动列表页面
	public String gotoPromotionPage(){
		return SUCCESS;
	}
	//保存或者修改促销活动
	public void saveOrUpdatePromotion() throws Exception {
		Date date = new Date();
		if(platformPromotion.getPromotionId()==null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String createTimeStr = sdf.format(date);
			Double random = Math.random();
			String num = random.toString().substring(2, 8);
			String promotionNumber = createTimeStr+num;
			platformPromotion.setPromotionNumber(promotionNumber);
			platformPromotion.setCreateTime(date);
		}else{
			platformPromotion.setUpdateTime(date);
		}
		platformPromotion.setIsPass(1);
		platformPromotion.setUseStatus(0);
		platformPromotion=(PlatformPromotion)platformPromotionService.saveOrUpdateObject(platformPromotion);
		if(platformPromotion.getPromotionId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//审核促销活动
	public void checkPromotion() throws IOException{
		Date date = new Date();
		Users users = (Users)session.getAttribute("users");
		platformPromotion=(PlatformPromotion)platformPromotionService.getObjectById(promotionId);
		platformPromotion.setIsPass(isPass);
		platformPromotion.setVerifier(users.getUserName());
		platformPromotion.setCheckTime(date);
		platformPromotion=(PlatformPromotion)platformPromotionService.saveOrUpdateObject(platformPromotion);
		if(platformPromotion.getPromotionId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//得到一条促销活动记录
	public void getPromotionObject() throws Exception {
		platformPromotion = (PlatformPromotion) platformPromotionService.getObjectById(promotionId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(platformPromotion,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除促销活动
	public void deletePromotion() throws Exception {
		String[] a = ids.split(",");
		Boolean isSuccess = false;
		for(int i=0;i<a.length;i++){
			PlatformPromotion  platformPromotion =(PlatformPromotion) platformPromotionService.getObjectById(a[i]);
			if(platformPromotion.getIsPass()!=2&&platformPromotion.getIsPass()!=null){
				isSuccess = platformPromotionService.deleteObjectsByIds("promotionId",ids);
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 查询所有促销活动
	 * @return
	 */
	public void listPromotion() throws Exception {
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			String useStatus = request.getParameter("useStatus");
			hqlsb.append(" where 1=1");
			if(beginTime!=null&&!"".equals(beginTime)){
				hqlsb.append(" and UNIX_TIMESTAMP(o.beginTime)>=UNIX_TIMESTAMP('"+beginTime+"')");
			}
			if(endTime!=null&&!"".equals(endTime)){
				hqlsb.append(" and UNIX_TIMESTAMP(o.endTime)<=UNIX_TIMESTAMP('"+endTime+"')");
			}
			if(!"-1".equals(useStatus)){
				hqlsb.append(" and o.useStatus='"+useStatus+"'");
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" beginTime desc"));
		int totalRecordCount = platformPromotionService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		platformPromotionList = platformPromotionService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", platformPromotionList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 获取促销活动折扣
	 * @throws IOException 
	 */
	public void getSalesPromotionDiscountObject() throws IOException{
		salesPromotionDiscount = (SalesPromotionDiscount) salesPromotionDiscountService.getObjectByParams("where o.promotionId="+promotionId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(salesPromotionDiscount,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 维护促销折扣
	 * @return
	 */
	public void saveOrUpdateSalesPromotionDiscount() throws IOException{
		platformPromotion = (PlatformPromotion) platformPromotionService.getObjectById(salesPromotionDiscount.getPromotionId()+"");
		salesPromotionDiscount.setPromotionIdNumber(platformPromotion.getPromotionNumber());
		salesPromotionDiscount=(SalesPromotionDiscount)salesPromotionDiscountService.saveOrUpdateObject(salesPromotionDiscount);
		if(salesPromotionDiscount.getDisproductId()!=null){
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
	 * 手动开启促销活动
	 * @return
	 */
	public void openPlatformPromotion() throws IOException{
		String isSuccess = "";
		Date nowDate = new Date();//创建当前时间  用于比较促销活动是否过期
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = fm.format(nowDate);//格式化后的时间
		platformPromotion=(PlatformPromotion)platformPromotionService.getObjectByParams(" where UNIX_TIMESTAMP(o.beginTime) < UNIX_TIMESTAMP('"+format+"') and o.promotionId= "+promotionId);
		if(platformPromotion!=null&&platformPromotion.getIsPass()==2&&platformPromotion.getUseStatus()==0){
			String sql="update shop_platformpromotion set useStatus=1 where promotionId ="+promotionId;
			Boolean success=platformPromotionService.updateObject(sql);
			String hql = "select o.productId as productId,o.salesPrice as salesPrice,spd.discount as discount from ProductInfo o,StoreApplyPromotion sap,SalesPromotionDiscount spd where o.productId = sap.productId and sap.promotionState = 1 and sap.promotionId = "+promotionId
					+" and spd.promotionId = sap.promotionId";
			List<Map> productList=platformPromotionService.findListMapByHql(hql);
			for(Map pro:productList){
				BigDecimal salesPrice = new BigDecimal(String.valueOf(pro.get("salesPrice")));
				BigDecimal discount = new BigDecimal((Double)pro.get("discount")/10);
				BigDecimal discountPrice = salesPrice.multiply(discount);
				String sql1="update shop_productinfo set salesPriceBack="+salesPrice+",salesPrice="+discountPrice+" where productId ="+pro.get("productId");
				platformPromotionService.updateObject(sql1);
			}
			isSuccess=success+"";
		}else if(platformPromotion!=null&&platformPromotion.getIsPass()==2&&platformPromotion.getUseStatus()==1){
			isSuccess="hasOpen";
		}else if(platformPromotion!=null&&platformPromotion.getIsPass()!=2){
			isSuccess="noCheck";
		}else{
			isSuccess="early";
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 手动结束促销活动
	 * @return
	 */
	public void closePlatformPromotion() throws IOException{
		String isSuccess = "";
		Date nowDate = new Date();//创建当前时间  用于比较促销活动是否过期
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = fm.format(nowDate);//格式化后的时间
		platformPromotion=(PlatformPromotion)platformPromotionService.getObjectByParams(" where UNIX_TIMESTAMP(o.endTime) < UNIX_TIMESTAMP('"+format+"') and o.promotionId= "+promotionId);
		if(platformPromotion!=null&&platformPromotion.getIsPass()==2&&platformPromotion.getUseStatus()==1){
			String sql="update shop_platformpromotion set useStatus=2 where promotionId ="+promotionId;
			Boolean success=platformPromotionService.updateObject(sql);
			String hql = "select o.productId as productId,o.salePriceBack as salePriceBack from ProductInfo o,StoreApplyPromotion sap,SalesPromotionDiscount spd where o.productId = sap.productId and sap.promotionState = 1 and sap.promotionId = "+promotionId
					+" and spd.promotionId = sap.promotionId";
			List<Map> productList=platformPromotionService.findListMapByHql(hql);
			for(Map pro:productList){
				BigDecimal salesPrice = new BigDecimal(String.valueOf(pro.get("salePriceBack")));
				String sql1="update shop_productinfo set salesPrice="+salesPrice+" where productId ="+pro.get("productId");
				platformPromotionService.updateObject(sql1);
			}
			isSuccess=success+"";
		}else if(platformPromotion!=null&&platformPromotion.getIsPass()!=2){
			isSuccess="noCheck";
		}else{
			isSuccess="early";
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public PlatformPromotion getPlatformPromotion() {
		return platformPromotion;
	}
	public void setPlatformPromotion(PlatformPromotion platformPromotion) {
		this.platformPromotion = platformPromotion;
	}
	public List<PlatformPromotion> getPlatformPromotionList() {
		return platformPromotionList;
	}
	public void setPlatformPromotionList(
			List<PlatformPromotion> platformPromotionList) {
		this.platformPromotionList = platformPromotionList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
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
	public void setPlatformPromotionService(
			IPlatformPromotionService platformPromotionService) {
		this.platformPromotionService = platformPromotionService;
	}
	public SalesPromotionDiscount getSalesPromotionDiscount() {
		return salesPromotionDiscount;
	}
	public void setSalesPromotionDiscount(
			SalesPromotionDiscount salesPromotionDiscount) {
		this.salesPromotionDiscount = salesPromotionDiscount;
	}
	public void setSalesPromotionDiscountService(
			ISalesPromotionDiscountService salesPromotionDiscountService) {
		this.salesPromotionDiscountService = salesPromotionDiscountService;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
}
