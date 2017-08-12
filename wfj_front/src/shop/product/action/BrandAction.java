package shop.product.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.product.pojo.Brand;
import shop.product.pojo.BrandType;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;

/**
 * 品牌Action
 * 
 * @author ss
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class BrandAction extends BaseAction {
	private IBrandService brandService;// 品牌Servie
	private IBrandTypeService brandTypeService;// 品牌分类中间表service
	private Brand brand;
	private List<Brand> brandList = new ArrayList<Brand>();// 品牌list
	private String brandId;
	private String ids;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;

	@Override
	public void addActionError(String anErrorMessage) {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		String fileUploadErrorMessage = getText("struts.multipart.maxSize.limit");// 改从国际化里取值
		try {
			out = response.getWriter();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 改从国际化里取值
		if (anErrorMessage.startsWith("Request exceeded allowed size limit")) {
			super.addActionError(fileUploadErrorMessage);
			request.setAttribute(fileUploadErrorMessage,
					"fileUploadErrorMessage");
			jo.accumulate("photoUrl", "false_maxSize");
			out.println(jo.toString());
			out.flush();
			out.close();
		} else {
			super.addActionError(anErrorMessage);
			jo.accumulate("photoUrl", "false_maxSize");
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}

	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath,
					imagePathFileName, fileUrlConfig, "image_brand");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot",
					(String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	// 跳转到商品品牌列表页面
	public String gotoBrandPage() {
		return SUCCESS;
	}

	// 查询所有信息列表
	public void listBrand() throws IOException {
		String selectFlag = request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		hqlsb.append(" where 1=1");
		if ("true".equals(selectFlag)) {// 判断是否点击查询按钮
			String brandName = request.getParameter("brandName");
			String isShow = request.getParameter("isShow");
			String isRecommend = request.getParameter("isRecommend");
			String isHomePage = request.getParameter("isHomePage");
			if (StringUtils.isNotEmpty(brandName)) {
				brandName = brandName.trim();
				hqlsb.append(" and o.brandName like '%" + brandName + "%'");
			}
			if (!"-1".equals(isHomePage)) {
				hqlsb.append(" and o.isHomePage = " + isHomePage);
			}
			if (!"-1".equals(isShow)) {
				hqlsb.append(" and o.isShow = " + isShow);
			}
			if (!"-1".equals(isRecommend)) {
				hqlsb.append(" and o.isRecommend =" + isRecommend);
			}
		}
		hqlsb.append(" order by o.brandId desc");
		int totalRecordCount = brandService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		brandList = brandService.findListByPageHelper(null, pageHelper,
				hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", brandList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	// 查询首页推荐品牌,根据isShow和isRecommend字段查询结果
	public void phoneListBrand() throws IOException {
		String isRecommend = request.getParameter("isRecommend");
		String sql = "select brandId,brandName,brandBigImageUrl,isShow,isRecommend from Brand where isRecommend"
				+ isRecommend + "and isShow=1";
		brandList = brandService.findPhoneBrandListBySql(sql);
		JSONObject jo = JSONObject.fromObject(brandList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	// 保存或者修改
	public void saveOrUpdateBrand() throws IOException {
		if (brand != null) {
			brand = (Brand) brandService.saveOrUpdateObject(brand);
			if (brand.getBrandId() != null) {
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

	// 获取一条记录
	public void getBrandInfo() throws IOException {
		brand = (Brand) brandService.getObjectByParams(" where o.brandId='"
				+ brandId + "'");
		JSONObject jo = JSONObject.fromObject(brand);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	// 删除记录
	public void deleteBrand() throws IOException {
		Boolean isSuccess = brandService.deleteObjectsByIds("brandId", ids);
		String[] split = ids.split(",");// 解析ids
		for (String id : split) {
			List<BrandType> brandTypeList = brandTypeService
					.findObjects(" where o.brandId='" + id + "'");
			for (BrandType bt : brandTypeList) {
				brandTypeService.deleteObjectByParams(" where o.brandTypeId='"
						+ bt.getBrandTypeId() + "'");
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
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

	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}
}
