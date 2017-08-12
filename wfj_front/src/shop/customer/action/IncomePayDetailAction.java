package shop.customer.action;
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
import shop.common.pojo.CoinRules;
import shop.customer.pojo.Customer;
import shop.customer.pojo.IncomePayDetail;
import shop.customer.pojo.VirtualCoin;
import shop.customer.service.ICustomerService;
import shop.customer.service.IIncomePayDetailService;
import shop.customer.service.IVirtualCoinService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 收支明细Action类
 * @author ss
 */
@SuppressWarnings("all")
public class IncomePayDetailAction extends BaseAction{
	private IIncomePayDetailService incomePayDetailService;//收支明细Service
	private IVirtualCoinService virtualCoinService;//虚拟金币
	private ICustomerService customerService;//客户Service
	private List<IncomePayDetail> IncomePayDetailList = new ArrayList<IncomePayDetail>();//收支明细List
	private List<Customer> customerList = new ArrayList<Customer>();//客户List
	private IncomePayDetail incomePayDetail;
	private String detailId;
	private String ids;
	private String params;//查询参数
	//跳转到收支明细列表页面
	public String gotoIncomePayDetailPage(){
		//客户
		customerList = customerService.findObjects(null);
		return SUCCESS;
	}
	//根据用户ID得到用户收支明细信息
	public void listIncomePayDetail() throws Exception {
		String cardHolder=request.getParameter("cardHolder");
		String cardNumber=request.getParameter("cardNumber");
		String phone=request.getParameter("phone");
		String state=request.getParameter("state");
		String where=" where 1=1";
		if(cardHolder!=null&&!"".equals(cardHolder)){
			where+=" and o.cardHolder like '%"+cardHolder+"%'";
		}
		if(cardNumber!=null&&!"".equals(cardNumber)){
			where+=" and o.cardNumber like '%"+cardNumber+"%'";
		}
		if(phone!=null&&!"".equals(phone)){
			where+=" and o.phone like '%"+phone+"%'";
		}
		if(state!=null&&!"".equals(state)){
			where+=" and o.state="+state;
		}
		if(cardHolder!=null&&!"".equals(cardHolder)){
			where+=" and o.cardHolder like '%"+cardHolder+"%'";
		}
		int totalRecordCount = incomePayDetailService.getCount(where);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		IncomePayDetailList = incomePayDetailService.findListByPageHelper(null,pageHelper, where+" order by o.updateTime desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", IncomePayDetailList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除用户收支明细
	public void deleteIncomePayDetail() throws Exception {
		Boolean isSuccess = incomePayDetailService.deleteObjectsByIds("detailId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//得到一条用户收支明细信息
	public void getIncomePayDetailObject() throws Exception {
		incomePayDetail = (IncomePayDetail) incomePayDetailService.getObjectById(detailId);
		Map<String,Object> map = new HashMap<String,Object>();
		if(incomePayDetail!=null){
			String hql=" where 1=1 ";
			if(incomePayDetail.getPayeeId()!=null){
				hql+="and o.customerId="+incomePayDetail.getPayeeId();
				if(incomePayDetail.getPayerId()!=null){
					hql+=" or o.customerId="+incomePayDetail.getPayerId();
				}
			}else if(incomePayDetail.getPayerId()!=null){
				hql+=" and o.customerId="+incomePayDetail.getPayerId();
			}
			Customer customer=(Customer) customerService.getObjectByParams(hql);
			map.put("incomePayDetail", incomePayDetail);
			map.put("customer", customer);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
			JSONObject jo = JSONObject.fromObject(map,jsonConfig);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//天海币提现状态更改
	@SuppressWarnings("unchecked")
	public void saveOrUpdateIncomePayDetail() throws Exception {
		String optId = request.getParameter("optId");
		String optState = request.getParameter("optState");
		incomePayDetail = (IncomePayDetail) incomePayDetailService.getObjectByParams(" where o.detailId="+optId);
		incomePayDetail.setState(Integer.parseInt(optState));
		//更新记录
		incomePayDetailService.saveOrUpdateObject(incomePayDetail);
		if(incomePayDetail.getState()==2){
			//扣除天海币相应数量
			VirtualCoin v=new VirtualCoin();
			v.setCustomerId(incomePayDetail.getPayeeId());//客户id
			//生成流水号
			SimpleDateFormat sdf1 = new  SimpleDateFormat("yyyyMMddHHmmss");
			String no = sdf1.format(new Date());
			Double random = Math.random();
			String num = random.toString().substring(2, 8);
			v.setSerialNumber(num);
			v.setType(2);//交易类型为支出  2
			//天海币提现比例
			Map<String,Object> map= (Map<String, Object>) servletContext.getAttribute("coinRules");
			List<CoinRules> crList = (List<CoinRules>) map.get("thbtxBL");
			String thbtxBL=crList.get(0).getValue();
			v.setProportion(Integer.parseInt(thbtxBL));//当时分享比例
			BigDecimal bd1=incomePayDetail.getTransactionAmount();
			BigDecimal bd2=new BigDecimal(thbtxBL);
			BigDecimal multiply = bd1.multiply(bd2);
//			String string = multiply.toString();
//			String substring = string.substring(0, string.lastIndexOf("."));
			v.setTransactionNumber(multiply);//交易数量
			//更改状态后扣除余额
			List<VirtualCoin> vcList = virtualCoinService.findObjects(" where o.customerId="+incomePayDetail.getPayeeId()+" order by o.operatorTime desc limit 1");
			BigDecimal remainingNumber = vcList.get(0).getRemainingNumber();//当前用户剩余虚拟金币数量  即：当前账户余额
//			BigDecimal bd3=new BigDecimal(remainingNumber);
//			BigDecimal bd4=new BigDecimal(Integer.parseInt(substring));
//			BigDecimal subtract = bd3.subtract(bd4);//扣除提现天海币后的账户余额
//			String string2 = subtract.toString();
//			int indexOf = string2.indexOf(".");
//			if(indexOf>=0){
//				string2= string2.substring(0, string2.lastIndexOf("."));
//			}
			remainingNumber = remainingNumber.subtract(multiply);//扣除提现天海币后的账户余额
			v.setRemainingNumber(remainingNumber);
			v.setTradeTime(incomePayDetail.getCreateTime());
			v.setOperatorTime(incomePayDetail.getUpdateTime());
			virtualCoinService.saveOrUpdateObject(v);
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess","true");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<IncomePayDetail> getIncomePayDetailList() {
		return IncomePayDetailList;
	}
	public void setIncomePayDetailList(List<IncomePayDetail> incomePayDetailList) {
		IncomePayDetailList = incomePayDetailList;
	}
	public IncomePayDetail getIncomePayDetail() {
		return incomePayDetail;
	}
	public void setIncomePayDetail(IncomePayDetail incomePayDetail) {
		this.incomePayDetail = incomePayDetail;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setIncomePayDetailService(
			IIncomePayDetailService incomePayDetailService) {
		this.incomePayDetailService = incomePayDetailService;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setVirtualCoinService(IVirtualCoinService virtualCoinService) {
		this.virtualCoinService = virtualCoinService;
	}
}
