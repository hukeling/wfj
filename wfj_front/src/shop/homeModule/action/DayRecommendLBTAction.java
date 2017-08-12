package shop.homeModule.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.homeModule.pojo.DayRecommendLBT;
import shop.homeModule.service.IDayRecommendLBTService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * DayRecommendLBTAction -- 每日推荐轮播图Action
 * @author 张攀攀
 * 2014-01-07
 *
 */
@SuppressWarnings("serial")
public class DayRecommendLBTAction extends BaseAction {
	/**每日推荐轮播图Service**/
	private IDayRecommendLBTService dayRecommendLBTService;
	/**每日推荐轮播图集合**/
	private List<DayRecommendLBT> dayRecommendLBTList = new ArrayList<DayRecommendLBT>();
	/**每日推荐轮播图实体类**/
	private DayRecommendLBT dayRecommendLBT;
	private String broadcastingId;
	private String ids;
	/**上传文件路径**/
	private File imagePath;
	/**上传文件名称**/
	private String imagePathFileName;
	/**
	 * 跳转到每日推荐轮播图列表页面
	 * @return
	 */
	public String gotoDayRecommendLBTPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	@SuppressWarnings("unchecked")
	public void listDayRecommendLBT() throws IOException{
		String hql = " order by o.sortCode asc";
		int totalRecordCount = dayRecommendLBTService.getCount(hql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		dayRecommendLBTList = dayRecommendLBTService.findListByPageHelper(null,pageHelper,hql);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", dayRecommendLBTList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateDayRecommendLBT() throws IOException{
		if(dayRecommendLBT!=null){
			dayRecommendLBT = (DayRecommendLBT) dayRecommendLBTService.saveOrUpdateObject(dayRecommendLBT);
			if(dayRecommendLBT.getBroadcastingId()!=null){
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
	//获取一条记录
	public void getDayRecommendLBTInfo() throws IOException{
		dayRecommendLBT = (DayRecommendLBT) dayRecommendLBTService.getObjectByParams(" where o.broadcastingId='"+broadcastingId+"'");
		JSONObject jo = JSONObject.fromObject(dayRecommendLBT);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteDayRecommendLBT() throws IOException{
		Boolean isSuccess = dayRecommendLBTService.deleteObjectsByIds("broadcastingId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
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
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_dayRecommend");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<DayRecommendLBT> getDayRecommendLBTList() {
		return dayRecommendLBTList;
	}
	public void setDayRecommendLBTList(List<DayRecommendLBT> dayRecommendLBTList) {
		this.dayRecommendLBTList = dayRecommendLBTList;
	}
	public DayRecommendLBT getDayRecommendLBT() {
		return dayRecommendLBT;
	}
	public void setDayRecommendLBT(DayRecommendLBT dayRecommendLBT) {
		this.dayRecommendLBT = dayRecommendLBT;
	}
	public String getBroadcastingId() {
		return broadcastingId;
	}
	public void setBroadcastingId(String broadcastingId) {
		this.broadcastingId = broadcastingId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setDayRecommendLBTService(IDayRecommendLBTService dayRecommendLBTService) {
		this.dayRecommendLBTService = dayRecommendLBTService;
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
