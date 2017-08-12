package shop.information.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.information.pojo.Information;
import shop.information.pojo.InformationComment;
import shop.information.service.IInformationCategoryService;
import shop.information.service.IInformationCommentService;
import shop.information.service.IInformationService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/**
 * 资讯留言Action类
 * @author mqr
 */
@SuppressWarnings("serial")
public class InformationCommentAction extends BaseAction{
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
	/**单个资讯回复统计**/
	private Integer imcount;
	private String  verificationCode;
	/**
	 * 异步校验 --验证码是否正确
	 */
	public void  verificationCode() throws IOException{
		JSONObject jo=new JSONObject();
		String serverCode = (String) request.getSession().getAttribute("verificationCode");//服务器上的
		if(StringUtils.isNotEmpty(verificationCode)){
			if(verificationCode.equalsIgnoreCase(serverCode)){
				//重置session失效时间，防止验证码3分钟失效后影响用户登录的session信息使用
				request.getSession().setMaxInactiveInterval(Integer.parseInt(String.valueOf(fileUrlConfig.get("session_user"))));
				jo.accumulate("success", "1");
			}else{
				jo.accumulate("success", "2");
			}
		}else{
			jo.accumulate("success", "3");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//评论
	public String addComment(){
		InformationComment ic=new InformationComment();
		//获取cookie中评论内容
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			//自带的判断是否相等方法
			if(cookie.getName().contentEquals("thshop_comment")){
				try {
					//jquery.cookie.js中使用自己的编码方式，后台要用相应的解码方式
					//System.out.println(URLDecoder.decode(cookie.getValue(), "UTF-8"));
					String unescape = util.other.Escape.unescape(URLDecoder.decode(cookie.getValue(), "UTF-8"));
					ic.setContent(unescape);//评论内容
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		   }
		}
		Customer customer = (Customer) session.getAttribute("customer");
		ic.setCustomerId(customer.getCustomerId());//评论人
		ic.setArticleId(Integer.parseInt(articleId));//评论文章
		ic.setCreateTime(new Date());
		ic.setState(1);
		ic.setIp(request.getRemoteHost());
		informationCommentService.saveOrUpdateObject(ic);
		return SUCCESS;
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
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}
