package cms.article.action;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import util.action.BaseAction;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import cms.attachement.pojo.ArticleAttachment;
import cms.attachement.service.IArticleAttachmentService;
import cms.category.pojo.CmsCategory;
import cms.category.service.ICategoryService;
/**
 *  
 * 文章
 */
@SuppressWarnings("serial")
public class ActicleAction extends BaseAction {
	private IArticleService articleService;
	private IArticleAttachmentService articleAttachmentService;
	private ICategoryService categoryService;
	private String categoryName;
	private List<CmsArticle> articleList;
	private CmsArticle cmsArticle;
	private CmsCategory category;
	private String ids;
	private String id;
	private String categoryId;
	private String params;
	private String attUrls;
	private String attUrlsImg;
	// 修改审核状态使用的
	private String isPass;
	private String articleId;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	/**
	 * 附件路径初始化 
	 * 
	 */
	public String gotoArticleInfoPage() throws Exception {
		CmsCategory cmsCategory = (CmsCategory)categoryService.getObjectById(categoryId);
		categoryName = cmsCategory.getCategoryName();
		request.setAttribute("folderName", (String) fileUrlConfig.get("cms"));
		request.setAttribute("doc_file_upload", (String) fileUrlConfig.get("doc_file_upload"));
		request.setAttribute("img_file_upload", (String) fileUrlConfig.get("img_file_upload"));
		request.setAttribute("zip_file_upload", (String) fileUrlConfig.get("zip_file_upload"));
		return SUCCESS;
	}
	/**
	 * 文章列表
	 * 
	 */
	public void listArticle() throws Exception {
		String hql = "where 1=1 and o.categoryId=" + categoryId + " ";
		// 前台字符串对应的字段
		// params
		// =title+"_"+articleType+"_"+isDeal+"_"+isEssence+"_"+isPass+"_"+isOpenDiscuss+"_"+isShow;
		if (params != null && !"".equals(params)) {
			String[] strArr = params.split("_");
			if (!"none".equals(strArr[0])&&!"".equals(strArr[0].trim())) {
				hql += " and o.title like '%" + strArr[0].trim() + "%'";
			}
			if (!"none".equals(strArr[1])) {
				hql += " and o.articleType=" + strArr[1];
			}
			if (!"none".equals(strArr[2])) {
				hql += " and o.isDeal=" + strArr[2];
			}
			if (!"none".equals(strArr[3])) {
				hql += " and o.isEssence=" + strArr[3];
			}
			if (!"none".equals(strArr[4])) {
				hql += " and o.isPass=" + strArr[4];
			}
			if (!"none".equals(strArr[5])) {
				hql += " and o.isOpenDiscuss=" + strArr[5];
			}
			if (!"none".equals(strArr[6])) {
				hql += " and o.isShow=" + strArr[6];
			}
		}
		int totalRecordCount = articleService.getCount(hql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		articleList = articleService.findListByPageHelper(null,pageHelper, hql + " order by o.articleId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", articleList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改文章
	 * 
	 */
	public void saveOrUpdateArticle() throws Exception {
		//把上面的三个操作放到同一个 service下进行管理
		if(cmsArticle.getArticleType().equals("0")){
			cmsArticle = (CmsArticle) articleService.saveOrUpdateArticleAndAtt(cmsArticle,"0");
		}
		if(cmsArticle.getArticleType().equals("1") ||cmsArticle.getArticleType().equals("2")){
			cmsArticle = (CmsArticle) articleService.saveOrUpdateArticleAndAtt(cmsArticle,attUrlsImg);
		}
		if(cmsArticle.getArticleType().equals("3") ||cmsArticle.getArticleType().equals("4")){
			cmsArticle = (CmsArticle) articleService.saveOrUpdateArticleAndAtt(cmsArticle,attUrls);
		}
		if (cmsArticle.getCategoryId() != null) {
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
	 * 异步ajax 图片上传
	 * 
	 */
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf(".") + 1);
			if ("jpg".equals(typeStr) || "JPG".equals(typeStr) || "png".equals(typeStr) || "PNG".equals(typeStr)
					|| "GIF".equals(typeStr) || "gif".equals(typeStr) || "".equals(typeStr)) {
				// 需要修改图片的存放地址
				String uuId = UUID.randomUUID().toString();
				String imagePathFileName2=uuId+"_"+imagePathFileName;
				File savefile = new File(new File((String) fileUrlConfig.get("fileUploadRoot") + "/"
						+ (String) fileUrlConfig.get("cms") + "/image/"), imagePathFileName2);
				if (!savefile.getParentFile().exists()) {
					savefile.getParentFile().mkdirs();
				}
				FileUtils.copyFile(imagePath, savefile);
				imagePathFileName2 = (String) fileUrlConfig.get("cms") + "/image/" + imagePathFileName2;
				jo.accumulate("photoUrl", imagePathFileName2);
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
	/**
	 * 得到文章对象和得到附件对象 
	 * 
	 */
	public void getArticleObject() throws Exception {
		cmsArticle = (CmsArticle) articleService.getObjectById(id);
		List<ArticleAttachment> attList = articleAttachmentService.getAttrByArticleId(id);
		// cmsArticle yu attList 是一对多的关系 
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("cmsArticle", cmsArticle);
		jsonMap.put("attList", attList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 删除文章
	 */
	public void deleteArticle() throws Exception {
		Boolean isSuccess = articleService.deleteArticleAndAttByIds(ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 审核文章
	 * 
	 */
   public void updateIsPass() throws Exception{
	   cmsArticle = (CmsArticle) articleService.updateIsPass(articleId,isPass);
	   if (cmsArticle.getCategoryId() != null) {
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
   }
	public IArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public List<CmsArticle> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<CmsArticle> articleList) {
		this.articleList = articleList;
	}
	public CmsArticle getCmsArticle() {
		return cmsArticle;
	}
	public void setCmsArticle(CmsArticle cmsArticle) {
		this.cmsArticle = cmsArticle;
	}
	public CmsCategory getCategory() {
		return category;
	}
	public void setCategory(CmsCategory category) {
		this.category = category;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getAttUrls() {
		return attUrls;
	}
	public void setAttUrls(String attUrls) {
		this.attUrls = attUrls;
	}
	public void setArticleAttachmentService(IArticleAttachmentService articleAttachmentService) {
		this.articleAttachmentService = articleAttachmentService;
	}
	public String getAttUrlsImg() {
		return attUrlsImg;
	}
	public void setAttUrlsImg(String attUrlsImg) {
		this.attUrlsImg = attUrlsImg;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
