package shop.customer.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ISonaccountService;
import util.action.BaseAction;
import util.action.SecurityCode;
import util.action.SecurityCode.SecurityCodeLevel;
import util.other.Utils;
/**
 * 会员子帐号action
 * @author wsy
 *
 */
@SuppressWarnings("serial")
public class SonaccountAction extends BaseAction {
	/**会员子帐号service**/
	private ISonaccountService sonaccountService;
	/**子帐号List**/
	@SuppressWarnings("rawtypes")
	private List<Map> sonaccountList = new ArrayList<Map>();
	private Sonaccount sonaccount;
	private String ids;
	private String customerId;
	private String sonAccountId;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	private Integer pageIndex=1;//订单列表的当前页
	private Integer pageSize=10;//分页-每一页显示的个数
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String flag;//标记 1表示添加 2表示修改
	/**跳转注册子页面**/
	public String gotoADFPage(){
		return SUCCESS;
	}
	/**展示所有当前会员的子帐号
	 * @throws IOException **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String listSonaccount() throws IOException{
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer!=null){
			//hql查询语句
			String hql="SELECT a.sonAccountId as sonAccountId, a.customerId as customerId, a.loginName as loginName, a.password as password, a.nickname as nickname, "
					+ "a.phone as phone, a.email as email, a.photoUrl as photoUrl, a.registerIp as registerIp, a.registerDate as registerDate, a.lockedState as lockedState, a.lockedDate as lockedDate, "
					+ "a.type as type FROM Sonaccount a where a.customerId="+customer.getCustomerId(); 
			String countHql="SELECT count(a.sonAccountId) FROM Sonaccount a where a.customerId="+customer.getCustomerId(); 
			if(!"Start".equals(startTime)&&!StringUtils.isEmpty(startTime)){
				hql+=" and UNIX_TIMESTAMP(a.registerDate)>=UNIX_TIMESTAMP('"+startTime+"')";
				countHql+=" and UNIX_TIMESTAMP(a.registerDate)>=UNIX_TIMESTAMP('"+startTime+"')";
			}
			if(!StringUtils.isEmpty(endTime)&&!"End".equals(endTime)){
				hql+=" and UNIX_TIMESTAMP(a.registerDate)<=UNIX_TIMESTAMP('"+endTime+"')";
				countHql+=" and UNIX_TIMESTAMP(a.registerDate)<=UNIX_TIMESTAMP('"+endTime+"')";
			}
			if(sonaccount!=null &&!"Enter order No".equals(sonaccount.getNickname())&& !StringUtils.isEmpty(sonaccount.getNickname())){
				hql+=" and a.nickname like '%"+sonaccount.getNickname().trim()+"%'";
				countHql+=" and a.nickname like '%"+sonaccount.getNickname().trim()+"%'";
			}
			if(sonaccount!=null && sonaccount.getType()!=null ){
				hql+=" and a.type="+sonaccount.getType();
				countHql+=" and a.type="+sonaccount.getType();
			}
			int totalRecordCount = sonaccountService.getMultilistCount(countHql);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			sonaccountList= sonaccountService.findListMapPage(hql+" order by a.registerDate desc", pageHelper);
			if(sonaccountList!=null&&sonaccountList.size()>0){
				for(Map m:sonaccountList){
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
					String time = fm.format(m.get("registerDate"));
					m.put("registerDate", time);
					if(m.get("lockedDate")!=null){
						String time2 = fm.format(m.get("lockedDate"));
						m.put("lockedDate", time2);
					}
				}
			}
		}
		return SUCCESS;
	}
	/**保存子帐号基本信息
	 * @throws IOException **/
	@SuppressWarnings("static-access")
	public void saveOrUpdateSonaccount() throws ParseException, IOException{
		JSONObject jo = new JSONObject();
		Customer customer = (Customer) session.getAttribute("customer");
		String ip=request.getRemoteHost();
		Sonaccount newSonaccount=new Sonaccount();
		newSonaccount.setLockedState(0);//0：未冻结1：已冻结
		newSonaccount.setType(sonaccount.getType());//会员类型默认为采购人员  1
		newSonaccount.setCustomerId(customer.getCustomerId());
		newSonaccount.setNickname(sonaccount.getNickname());//真是姓名
		newSonaccount.setRegisterIp(ip);
		newSonaccount.setRegisterDate(new Date());
		if(sonaccount!=null){
			//产生4位随机字母
			String str = SecurityCode.getSecurityCode(4,SecurityCodeLevel.onlyChar, true);
			if(sonaccount.getLoginName()==null){//pc端注册
				//获取用户的邮箱
				String email=sonaccount.getEmail();
				/*拼装用户loginName
			       规则：邮箱@前字符+下斜线+随机字母（4位随机数），比如：94876_oppo） */
				String loginName=email.split("@")[0]+"_"+str;
				newSonaccount.setLoginName(loginName);
				Utils ut = new Utils();
				String encodeMd5 = ut.EncodeMd5(sonaccount.getPassword());//密码加密
				String encodeMd52 = ut.EncodeMd5(encodeMd5);
				newSonaccount.setPassword(encodeMd52); 
				newSonaccount.setPhone(sonaccount.getPhone());
				newSonaccount.setEmail(sonaccount.getEmail());
			}
			Object obj;
			if(sonaccount.getSonAccountId()!=null){//修改时有ID
				Sonaccount sonaccount3 = (Sonaccount) sonaccountService.getObjectByParams("where o.sonAccountId= '"+sonaccount.getSonAccountId()+"'");
				//获取用户的邮箱
				String email=sonaccount.getEmail();
				sonaccount.setRegisterDate(new Date());
				sonaccount.setRegisterIp(ip);
				/*拼装用户loginName
			       规则：邮箱@前字符+下斜线+随机字母（4位随机数），比如：94876_oppo） */
				String loginName=email.split("@")[0]+"_"+str;
				sonaccount.setLoginName(loginName);
				if(!sonaccount.getPassword().equals("")&&sonaccount.getPassword()!=sonaccount3.getPassword()){
					Utils ut = new Utils();
					String encodeMd5 = ut.EncodeMd5(sonaccount.getPassword());//密码加密
					String encodeMd52 = ut.EncodeMd5(encodeMd5);
					sonaccount.setPassword(encodeMd52);
				}else{
					sonaccount.setPassword(sonaccount3.getPassword());
				}
				obj = sonaccountService.saveOrUpdateObject(sonaccount);
			}else{
				obj = sonaccountService.saveOrUpdateObject(newSonaccount);
			}
			if(obj!=null){
				jo.accumulate("success", true);
			}else{
				jo.accumulate("success", false);
			}
		}else{
			jo.accumulate("success", false);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//得到Sonaccount对象
	public String getSonaccountObject() throws IOException{
		if(sonaccount != null){
			sonaccount = (Sonaccount)sonaccountService.getObjectByParams(" where o.sonAccountId='"+sonaccount.getSonAccountId()+"'");
		}
		return SUCCESS;
	}
	public void setSonaccountService(ISonaccountService sonaccountService) {
		this.sonaccountService = sonaccountService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getSonaccountList() {
		return sonaccountList;
	}
	@SuppressWarnings("rawtypes")
	public void setSonaccountList(List<Map> sonaccountList) {
		this.sonaccountList = sonaccountList;
	}
	public Sonaccount getSonaccount() {
		return sonaccount;
	}
	public void setSonaccount(Sonaccount sonaccount) {
		this.sonaccount = sonaccount;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSonAccountId() {
		return sonAccountId;
	}
	public void setSonAccountId(String sonAccountId) {
		this.sonAccountId = sonAccountId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
