package shop.front.customer.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/**
 * 发票Action
 * @author wsy
 *
 */
@SuppressWarnings("serial")
public class ForInvoiceAction extends BaseAction {
	/**企业信息或店铺信息service**/
	private IShopInfoService shopInfoService;
	/**企业信息实体或店铺信息**/
	private ShopInfo shopInfo;
	/**企业信息实体或店铺信息**/
	private Integer shopInfoId;
	
    private static boolean match( String regex ,String str ){
        Pattern pattern = Pattern.compile(regex);
        Matcher  matcher = pattern.matcher( str );
        return matcher.matches();
    }
	/**跳转发票添加**/
	public String gotoForInvoicePage(){
		return SUCCESS;
	}
	/**
	 * 保存发票
	 * @return
	 * @throws IOException
	 */
	public String saveOrUpdateForInvoice() throws IOException{
		Customer customer = (Customer)session.getAttribute("customer");
		ShopInfo shopInfo_1 = (ShopInfo)session.getAttribute("shopInfo");
		if(shopInfo_1!=null){
			/*if(shopInfo_1.getInvoiceCheckStatus()!=null&&shopInfo_1.getInvoiceCheckStatus()==1){
				return SUCCESS;
			}*/
			shopInfo_1.setCompanyName(shopInfo.getCompanyName());//企业名称
			shopInfo_1.setTaxpayerNumber(shopInfo.getTaxpayerNumber());//纳税人识别编号
			shopInfo_1.setAddressForInvoice(shopInfo.getAddressForInvoice());//发票地址
			shopInfo_1.setPhoneForInvoice(shopInfo.getPhoneForInvoice());//发票电话
			shopInfo_1.setOpeningBank(shopInfo.getOpeningBank());//开户行
			shopInfo_1.setBankAccountNumber(shopInfo.getBankAccountNumber());//账号
//			shopInfo_1.setInvoiceCheckStatus(1);//第一次保存发票，状态为1，未审核
			shopInfo = (ShopInfo)shopInfoService.saveOrUpdateObject(shopInfo_1);
		}else{
			ShopInfo s = new ShopInfo();
			s.setCustomerId(customer.getCustomerId());
			s.setCustomerName(customer.getLoginName());
			s.setCompanyName(shopInfo.getCompanyName());//企业名称
			s.setTaxpayerNumber(shopInfo.getTaxpayerNumber());//纳税人识别编号
			s.setAddressForInvoice(shopInfo.getAddressForInvoice());//发票地址
			s.setPhoneForInvoice(shopInfo.getPhoneForInvoice());//发票电话
			s.setOpeningBank(shopInfo.getOpeningBank());//开户行
			s.setBankAccountNumber(shopInfo.getBankAccountNumber());//账号
			s.setInvoiceCheckStatus(1);//第一次保存发票，状态为1，未审核
			shopInfo = (ShopInfo)shopInfoService.saveOrUpdateObject(s);
		}
		if(shopInfo.getShopInfoId()!=null){
			session.setAttribute("shopInfo", shopInfo);
			return SUCCESS;
		}
		return "";
	}
	/**
	 * 检测企业名称是否存在
	 * @param companyName 企业名称
	 * @author wsy
	 */
	public void checkCompanyName() throws Exception{
		//获取真实姓名
		String companyName = request.getParameter("companyName");
		Integer count =  shopInfoService.getCount(" where o.companyName='"+companyName.trim()+"' and o.shopInfoId <> "+shopInfoId);
		JSONObject jo=new JSONObject();
		if(count>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测纳税人编号是否存在
	 * @param taxpayerNumber 纳税人编号
	 * @author wsy
	 */
	public void checkTaxpayerNumber() throws Exception{
		//获取纳税人编号
		String taxpayerNumber = request.getParameter("taxpayerNumber");
		Integer count =  shopInfoService.getCount(" where o.taxpayerNumber='"+taxpayerNumber+"' and o.shopInfoId <> "+shopInfoId);
		JSONObject jo=new JSONObject();
		if(count>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测地址是否存在
	 * @param addressForInvoice 地址
	 * @author wsy
	 */
	public void checkAddressForInvoice() throws Exception{
		//获取真实姓名
		String addressForInvoice = request.getParameter("addressForInvoice");
		Integer count =  shopInfoService.getCount(" where o.addressForInvoice='"+addressForInvoice.trim()+"' and o.shopInfoId <> "+shopInfoId);
		JSONObject jo=new JSONObject();
		if(count>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测电话是否存在
	 * @param phoneForInvoice 电话
	 * @author wsy
	 */
	public void checkPhoneForInvoice() throws Exception{
		//获取电话
		String phoneForInvoice = request.getParameter("phoneForInvoice");
		JSONObject jo=new JSONObject();
		Integer count =  shopInfoService.getCount(" where o.phoneForInvoice='"+phoneForInvoice+"' and o.shopInfoId <> "+shopInfoId);
		if(count>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
	response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测账号是否存在
	 * @param bankAccountNumber 账号
	 * @author wsy
	 */
	public void checkBankAccountNumber() throws Exception{
		//获取电话
		String bankAccountNumber = request.getParameter("bankAccountNumber");
		Integer count =  shopInfoService.getCount(" where o.bankAccountNumber='"+bankAccountNumber+"' and o.shopInfoId <> "+shopInfoId);
		JSONObject jo=new JSONObject();
		if(count>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
}
