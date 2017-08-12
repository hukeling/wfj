package shop.information.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.information.pojo.InformationCategory;
import shop.information.service.IInformationCategoryService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * 店铺内部资讯分类Action类
 * @author mqr
 */
@SuppressWarnings("serial")
public class InformationCategoryAction extends BaseAction{
	/**资讯分类service**/
	private IInformationCategoryService informationCategoryService;
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**资讯分类obj**/
	private InformationCategory informationCategory;
	/**店铺obj**/
	private ShopInfo shopInfo;
	/**资讯分类List**/
	private List<InformationCategory> informationCategoryList = new ArrayList<InformationCategory>();
	/**资讯分类ID**/
	private String categoryId;
	/**ids**/
	private String ids;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**
	 * 跳转-店铺内部资讯分类页面
	 * @return
	 */
	public String gotoInformationCategoryPage(){
		Customer customer = (Customer) session.getAttribute("customer");
		Integer totalRecordCount = informationCategoryService.getCount(null);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		//查询条件:  level=1  order by sortCode asc
		informationCategoryList=informationCategoryService.findListByPageHelper(null, pageHelper, " where o.level=1 and o.customerId="+customer.getCustomerId()+" order by o.sortCode");
		return SUCCESS;
	}
	/**
	 * 查询店铺一个资讯分类
	 */
	public String addOrEditShopCategory(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
		}
		if(informationCategory!=null && informationCategory.getCategoryId()!=null){
			informationCategory=(InformationCategory) informationCategoryService.getObjectByParams(" where o.categoryId="+informationCategory.getCategoryId());
		}
		return SUCCESS;
	}
	/**
	 *  异步ajax 图片上传
	 * @throws IOException
	 */
	public void uploadImageFront() throws IOException  {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		out = response.getWriter();
		String returnImagePathFileName=	ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_informationCategory");
		if(imagePathFileName.equals(returnImagePathFileName) || imagePathFileName.equals("图片上传失败!")){
			jo.accumulate("photoUrl", "false");
		}else{
			jo.accumulate("photoUrl", returnImagePathFileName);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 修改或者保存资讯分类
	 */
	public void saveOrUpdateShopCategory(){
		Customer customer = (Customer) session.getAttribute("customer");
		try {
			if(null!=informationCategory){
				informationCategory.setCustomerId(customer.getCustomerId());
				informationCategoryService.saveOrUpdateObject(informationCategory);
			}
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
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
	 * 删除资讯分类
	 */
	public void deleteShopCategory(){
		try {
			ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
			if(StringUtils.isNotEmpty(categoryId)){
				informationCategoryService.deleteObjectByParams(" where o.categoryId="+categoryId+" and o.shopInfoId="+shopInfo.getShopInfoId());
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out;
				out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//setter getter 
	public InformationCategory getInformationCategory() {
		return informationCategory;
	}
	public void setInformationCategory(InformationCategory informationCategory) {
		this.informationCategory = informationCategory;
	}
	public List<InformationCategory> getInformationCategoryList() {
		return informationCategoryList;
	}
	public void setInformationCategoryList(
			List<InformationCategory> informationCategoryList) {
		this.informationCategoryList = informationCategoryList;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setInformationCategoryService(
			IInformationCategoryService informationCategoryService) {
		this.informationCategoryService = informationCategoryService;
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
