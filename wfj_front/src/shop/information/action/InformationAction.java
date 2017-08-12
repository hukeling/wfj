package shop.information.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.information.pojo.Information;
import shop.information.pojo.InformationCategory;
import shop.information.pojo.InformationComment;
import shop.information.service.IInformationCategoryService;
import shop.information.service.IInformationCommentService;
import shop.information.service.IInformationService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * 店铺内部资讯分类Action类
 * @author mqr
 */
@SuppressWarnings("serial")
public class InformationAction extends BaseAction{
	/**资讯分类service**/
	private IInformationCategoryService informationCategoryService;
	/**资讯评论service**/
	private IInformationCommentService informationCommentService;
	/**资讯信息service**/
	private IInformationService  informationService;
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**资讯信息obj**/
	private Information information;
	/**店铺obj**/
	private ShopInfo shopInfo;
	/**资讯信息List**/
	private List<Information> informationList = new ArrayList<Information>();
	/**资讯分类ID**/
	private String categoryId;
	/**资讯信息ID**/
	private String articleId;
	/**店铺ID**/
	private String shopInfoId;
	/**ids**/
	private String ids;
	/**单个资讯回复统计**/
	private Integer imcount;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**
	 * 跳转-店铺内部资讯信息页面
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gotoInformationPage(){
		Customer customer = (Customer) session.getAttribute("customer");
		Integer totalRecordCount = informationService.getCount( " where o.customerId="+customer.getCustomerId()+" and o.categoryId="+categoryId);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String hql="select a.title as title,a.articleId as articleId ,a.imgUrl as imgUrl,a.isEssence as isEssence,a.brief as brief,a.sortCode as sortCode,a.isShow as isShow from Information a where a.customerId="+customer.getCustomerId()+" and a.categoryId="+categoryId+"  order by a.sortCode";
		List<Map> informationList = informationService.findListMapPage(hql, pageHelper);
		if(informationList!=null&&informationList.size()>0){
			for(Map m:informationList){
				String articleId = String.valueOf(m.get("articleId"));
				Integer count = informationCommentService.getCount(" where o.articleId="+articleId);
				if(count>0){
					m.put("count", count);
				}else{
					m.put("count", 0);
				}
			}
		}
		request.setAttribute("informationList", informationList);
		return SUCCESS;
	}
	/**
	 * 查询店铺一个分类
	 */
	public String addOrEditInforMation(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
		}
		if(information!=null && information.getArticleId()!=null){
			information=(Information) informationService.getObjectByParams(" where o.articleId="+information.getArticleId());
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
		String returnImagePathFileName=	ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_information");
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
	 * 修改或者保存资讯
	 */
	public void saveOrUpdateInforMation(){
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(null!=information){
				if(information.getArticleId()==null){
					information.setCreateTime(fm.format(new Date()));//创建时间
					information.setClickCount(0);//点击次数
				}
				information.setModifyUser(information.getPublishUser());//修改人
				information.setCustomerId(customer.getCustomerId());
				information.setShopInfoId(shopInfo.getShopInfoId());
				information.setUpdateTime(fm.format(new Date()));//修改时间
				information.setCategoryId(Integer.parseInt(categoryId));
				informationService.saveOrUpdateObject(information);
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
	 * 删除资讯
	 */
	public void deleteInforMation(){
		try {
			ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
			if(StringUtils.isNotEmpty(articleId)){
				informationService.deleteObjectByParams(" where o.articleId="+articleId+" and o.shopInfoId="+shopInfo.getShopInfoId());
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
	/**
	 * 资讯预览
	 */
	public String preview(){
		//获取session中的店铺信息
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		shopInfoId=String.valueOf(shopInfo.getShopInfoId());
		return SUCCESS;
	}
	/**
	 * 查看留言
	 * @return
	 */
	public String previewComment(){
		//查询资料
		information = (Information) informationService.getObjectByParams(" where o.articleId="+articleId);
		//查询分类
		InformationCategory informationCategory = (InformationCategory) informationCategoryService.getObjectByParams(" where o.categoryId="+information.getCategoryId());
		request.setAttribute("informationCategory", informationCategory);
		categoryId=String.valueOf(informationCategory.getCategoryId());
		//店铺信息
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		request.setAttribute("shopInfo", shopInfo);
		//查询留言
		String hql="select b.discussId as discussId, a.loginName as loginName ,b.content as content,b.createTime as createTime,a.photoUrl as photoUrl from Customer a ,InformationComment b where a.customerId=b.customerId and b.articleId="+articleId;
		String count="select count(a.loginName)from Customer a ,InformationComment b where a.customerId=b.customerId and b.articleId="+articleId;
		Integer totalRecordCount= informationCommentService.getMultilistCount(count);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> mapList = informationCommentService.findListMapPage(hql+" order by b.createTime", pageHelper);
		//留言人
		request.setAttribute("icList", mapList);//留言list
		//查询店铺资讯分类
		List<InformationCategory> icList2 = (List<InformationCategory>) informationCategoryService.findListSpecifiedNumber(null, 0, 7, " where o.shopInfoId="+shopInfo.getShopInfoId()+" and o.isShow=1");
		request.setAttribute("informationCategoryList", icList2);
		return SUCCESS;
	}
	/**
	 * 删除留言
	 */
	public void deleteComment()throws IOException{
		JSONObject jo=new JSONObject();
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		//获得留言
		InformationComment ic = (InformationComment) informationCommentService.getObjectByParams(" where o.discussId="+ids);
		if(ic!=null){
			//获得文章
			information = (Information) informationService.getObjectByParams(" where o.articleId="+ic.getArticleId());
			if(information!=null&&information.getShopInfoId().compareTo(shopInfo.getShopInfoId())==0){
				jo.accumulate("success", true);
				//删除留言
				informationCommentService.deleteObjectById(ids);
			}else{
				jo.accumulate("success", false);
			}
		}else{
			jo.accumulate("success", false);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//setter getter 
	public Information getInformation() {
		return information;
	}
	public void setInformation(Information information) {
		this.information = information;
	}
	public List<Information> getInformationList() {
		return informationList;
	}
	public void setInformationList(
			List<Information> informationList) {
		this.informationList = informationList;
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
	public void setInformationService(IInformationService informationService) {
		this.informationService = informationService;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public void setInformationCommentService(
			IInformationCommentService informationCommentService) {
		this.informationCommentService = informationCommentService;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Integer getImcount() {
		return imcount;
	}
	public void setImcount(Integer imcount) {
		this.imcount = imcount;
	}
}
