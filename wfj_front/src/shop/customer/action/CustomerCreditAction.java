package shop.customer.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.CustomerCredit;
import shop.customer.service.ICustomerCreditService;
import util.action.BaseAction;
import util.upload.ImageFileUploadUtil;
/**
 * 会员等级Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class CustomerCreditAction extends BaseAction {
	private ICustomerCreditService customerCreditService;
	private List<CustomerCredit> customerCreditList = new ArrayList<CustomerCredit>();
	private CustomerCredit customerCredit;
	private String ids;
	private String customerCreditId;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	//跳转到数据字典列表页面
	public String gotoCustomerCreditPage(){
		return SUCCESS;
	}
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_customerCredit");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
			} else {
				jo.accumulate("photoUrl", "false2");
			}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//查询所有信息列表
	public void listCustomerCredit() throws IOException{
		int totalRecordCount = customerCreditService.getCount(" order by o.customerCreditId");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		customerCreditList = customerCreditService.findListByPageHelper(null,pageHelper, " order by o.customerCreditId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", customerCreditList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateCustomerCredit() throws IOException{
		if(customerCredit!=null){
			customerCredit = (CustomerCredit) customerCreditService.saveOrUpdateObject(customerCredit);
			if(customerCredit.getCustomerCreditId()!=null){
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
	public void getCustomerCreditObject() throws IOException{
		customerCredit = (CustomerCredit) customerCreditService.getObjectByParams(" where o.customerCreditId='"+customerCreditId+"'");
		JSONObject jo = JSONObject.fromObject(customerCredit);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteCustomerCredit() throws IOException{
		Boolean isSuccess = customerCreditService.deleteObjectsByIds("customerCreditId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<CustomerCredit> getCustomerCreditList() {
		return customerCreditList;
	}
	public void setCustomerCreditList(List<CustomerCredit> customerCreditList) {
		this.customerCreditList = customerCreditList;
	}
	public CustomerCredit getCustomerCredit() {
		return customerCredit;
	}
	public void setCustomerCredit(CustomerCredit customerCredit) {
		this.customerCredit = customerCredit;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCustomerCreditId() {
		return customerCreditId;
	}
	public void setCustomerCreditId(String customerCreditId) {
		this.customerCreditId = customerCreditId;
	}
	public File getImagePath() {
		return imagePath;
	}
	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}
	public void setCustomerCreditService(
			ICustomerCreditService customerCreditService) {
		this.customerCreditService = customerCreditService;
	}
	public void setImagePathFileName(String imagePathFileName) {
		this.imagePathFileName = imagePathFileName;
	}
}
