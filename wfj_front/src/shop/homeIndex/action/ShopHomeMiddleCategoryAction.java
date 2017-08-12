package shop.homeIndex.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import shop.homeIndex.pojo.ShopHomeMiddleCategory;
import shop.homeIndex.service.IShopHomeMiddleCategoryService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * 首页中间分类
 * @author mqr
 * 2014-01-15
 */
public class ShopHomeMiddleCategoryAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**中间分类service**/
	private IShopHomeMiddleCategoryService shopHomeMiddleCategoryService;
	/**中间分类service**/
	private ShopHomeMiddleCategory shopHomeMiddleCategory;
	/**父ID**/
	private String parentId;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**
	 * 跳转
	 */
	public String gotoShopHomeMiddleCategoryTree(){
		return SUCCESS;
	}
	/**
	 * 得到树的节点
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getNodes() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		List<ShopHomeMiddleCategory> list = shopHomeMiddleCategoryService.findObjects(null, "where 1=1 and o.parentId="+parentId+" order by o.sortCode,o.categoryId");
		StringBuffer sbf = new StringBuffer();
		ShopHomeMiddleCategory smc = null;
		sbf.append("<List>");
		for (Iterator ite = list.iterator(); ite.hasNext();) {
			smc = (ShopHomeMiddleCategory) ite.next();
			if (smc!= null) {
				sbf.append("<category>");
				sbf.append("<name>").append(smc.getCategoryName()).append("</name>");
				sbf.append("<parentId>").append(smc.getParentId()).append("</parentId>");
				sbf.append("<id>").append(smc.getCategoryId()).append("</id>");
				sbf.append("<isLeaf>").append(smc.getIsLeaf()).append("</isLeaf>");
				sbf.append("<level>").append(smc.getLevel()).append("</level>");
				sbf.append("</category>");
			}
		}
		sbf.append("</List>");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(sbf.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  异步ajax 图片上传
	 */
	public void uploadImage() {
		try {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			if (imagePath != null) {
				String photoUrl = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_homeMiddleCategory");
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
	public void saveOrEditShopHomeMiddleCategory()throws IOException{
		if(shopHomeMiddleCategory.getCategoryId()==null){//添加
			shopHomeMiddleCategory.setIsLeaf(1);
			//父对象
			ShopHomeMiddleCategory shm = (ShopHomeMiddleCategory) shopHomeMiddleCategoryService.getObjectByParams(" where o.categoryId="+shopHomeMiddleCategory.getParentId());
			//计算级别
			Integer level=0;
			if(shm!=null){
				level = shm.getLevel();
				shm.setIsLeaf(0);
				shopHomeMiddleCategoryService.saveOrUpdateObject(shm);//更改父对象的节点信息
			}
			shopHomeMiddleCategory.setLevel(level+1);
		}
		Object o = shopHomeMiddleCategoryService.saveOrUpdateObject(shopHomeMiddleCategory);
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
	public void getShopHomeMiddleCategoryObj()throws IOException{
		String id = request.getParameter("id");
		ShopHomeMiddleCategory smc = (ShopHomeMiddleCategory) shopHomeMiddleCategoryService.getObjectByParams(" where o.categoryId="+id);
		JSONObject jo = JSONObject.fromObject(smc);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 删除分类
	 */
	public void delShopHomeMiddleCategory()throws IOException{
		String id = request.getParameter("id");
		//查看上级数据是否仍未叶子
		ShopHomeMiddleCategory shopHomeMiddleCategory = (ShopHomeMiddleCategory) shopHomeMiddleCategoryService.getObjectByParams(" where o.categoryId="+id);
		//获取fuId
		Integer parentId2 = shopHomeMiddleCategory.getParentId();
		boolean bool = shopHomeMiddleCategoryService.deleteObjectByParams(" where o.categoryId="+id);
		Integer count = shopHomeMiddleCategoryService.getCount(" where o.parentId="+parentId2);
		if(count==0){
			ShopHomeMiddleCategory smc = (ShopHomeMiddleCategory) shopHomeMiddleCategoryService.getObjectByParams(" where o.categoryId="+parentId2);
			smc.setIsLeaf(1);//叶子
			shopHomeMiddleCategoryService.saveOrUpdateObject(smc);
		}
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
	/**
	 * 通过树来维护列表
	 */
	public String gotoShopHomeMiddleCategoryTablePage(){
		return SUCCESS;
	}
	//setter getter
	public void setShopHomeMiddleCategoryService(
			IShopHomeMiddleCategoryService shopHomeMiddleCategoryService) {
		this.shopHomeMiddleCategoryService = shopHomeMiddleCategoryService;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public ShopHomeMiddleCategory getShopHomeMiddleCategory() {
		return shopHomeMiddleCategory;
	}
	public void setShopHomeMiddleCategory(
			ShopHomeMiddleCategory shopHomeMiddleCategory) {
		this.shopHomeMiddleCategory = shopHomeMiddleCategory;
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
}
