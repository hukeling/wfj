package shop.homeIndex.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.homeIndex.pojo.ShopHomeMiddleCategoryTAB;
import shop.homeIndex.service.IShopHomeMiddleCategoryTABService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.upload.ImageFileUploadUtil;
import basic.pojo.Users;
/**
 * 首页中间分类两侧维护
 * @author mqr
 * 2014-01-15
 */
public class ShopHomeMiddleCategoryTABAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**中间分类tab service**/
	private IShopHomeMiddleCategoryTABService shopHomeMiddleCategoryTABService;
	/**中间分类tab obj**/
	private ShopHomeMiddleCategoryTAB shopHomeMiddleCategoryTAB=new ShopHomeMiddleCategoryTAB();
	/**id**/
	private String categoryId;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**
	 * 跳转
	 */
	public String gotoShopHomeMiddleCategoryTABPage(){
		return SUCCESS;
	}
	/**
	 * 列表页面
	 */
	@SuppressWarnings("unchecked")
	public void listShopHomeMiddleCategoryTAB()throws IOException{
		//获取查询参数
		String title = request.getParameter("title");
		String isShow = request.getParameter("isShow");
		String imageType = request.getParameter("imageType");
		String where=" where 1=1";
		if(title!=null&&!"".equals(title)){
			where+=" and o.title like '%"+title+"%'";
		}
		if(isShow!=null&&!"".equals(isShow)){
			where+=" and o.isShow = "+isShow;
		}
		if(imageType!=null&&!"".equals(imageType)){
			where+=" and o.imageType = "+imageType;
		}
		Integer totalRecordCount=shopHomeMiddleCategoryTABService.getCount(where+" and o.categoryId="+categoryId);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<ShopHomeMiddleCategoryTAB> list = shopHomeMiddleCategoryTABService.findListByPageHelper(null, pageHelper, where+" and o.categoryId="+categoryId+" order by o.sortCode desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", list);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 *  异步ajax 图片上传
	 */
	public void uploadImage() {
		try {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			if (imagePath != null) {
				String photoUrl = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_homeMiddleCategoryTAB");
				String visitFileUploadRoot = (String) fileUrlConfig.get("visitFileUploadRoot");
				jsonMap.put("photoUrl", photoUrl);
				jsonMap.put("visitFileUploadRoot", visitFileUploadRoot);
			} else {
				String photoUrl = "false1";
				jsonMap.put("photoUrl", photoUrl);
			}
			JSONObject jo = JSONObject.fromObject(jsonMap);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		// 1图片上传
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 保存
	 * @throws IOException
	 */
	public void saveOrUpdateShopHomeMiddleCategoryTAB()throws IOException{
		//当前登录对象
		Users users = (Users) session.getAttribute("users");
		if(shopHomeMiddleCategoryTAB.getCategoryTabId()==null){//添加
			//创建人 、创建时间
			shopHomeMiddleCategoryTAB.setCreateTime(new Date());
			shopHomeMiddleCategoryTAB.setPublishUser(users.getUserName());
		}
		//修改人、修改时间
		shopHomeMiddleCategoryTAB.setUpdateTime(new Date());
		shopHomeMiddleCategoryTAB.setModifyUser(users.getUserName());
		Object o = shopHomeMiddleCategoryTABService.saveOrUpdateObject(shopHomeMiddleCategoryTAB);
		JSONObject jo = new JSONObject();
		if(o!=null){
			jo.accumulate("strFlag", true);
		}else{
			jo.accumulate("strFlag", false);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 获得当前对象
	 */
	public void getShopHomeMiddleCategoryTABObj()throws IOException{
		String id = request.getParameter("id");
		ShopHomeMiddleCategoryTAB smcb = (ShopHomeMiddleCategoryTAB) shopHomeMiddleCategoryTABService.getObjectByParams(" where o.categoryTabId="+id);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(smcb,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 删除分类
	 */
	public void deleteByIds()throws IOException{
		String ids = request.getParameter("ids");
		boolean bool = shopHomeMiddleCategoryTABService.deleteObjectsByIds("categoryTabId", ids);
		JSONObject jo = new JSONObject();
		if(bool){
			jo.accumulate("strFlag", true);
		}else{
			jo.accumulate("strFlag", false);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//setter getter
	public ShopHomeMiddleCategoryTAB getShopHomeMiddleCategoryTAB() {
		return shopHomeMiddleCategoryTAB;
	}
	public void setShopHomeMiddleCategoryTAB(
			ShopHomeMiddleCategoryTAB shopHomeMiddleCategoryTAB) {
		this.shopHomeMiddleCategoryTAB = shopHomeMiddleCategoryTAB;
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
	public void setShopHomeMiddleCategoryTABService(
			IShopHomeMiddleCategoryTABService shopHomeMiddleCategoryTABService) {
		this.shopHomeMiddleCategoryTABService = shopHomeMiddleCategoryTABService;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
