package util.filter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import util.other.Escape;
import util.other.WebUtil;
/**
 * 判断用户是否具有店铺权限过滤器
 * @author CYS
 *
 */
public class StoreLogonValidateFilter implements Filter {
	List<String> pageNameList= new ArrayList<String>();
	public void destroy() {
	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String is_ajax = request.getParameter("is_ajax");
		Customer customer = WebUtil.getCustomer(request);//得到顾客对象
		String header = request.getHeader("X-Requested-With");  
		if (header != null && "XMLHttpRequest".equals(header)){
			String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
			String pagename = url.substring(url.lastIndexOf("/"), url.length());
			if(!pagename.contains(".action")){
				chain.doFilter(req, res);//店铺通过审核
			}else if(pageNameList.contains(pagename)&&customer != null){
				chain.doFilter(req, res);//店铺通过审核
			}else{
				HttpServletResponse response = (HttpServletResponse)res;
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/json;charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				PrintWriter out = response.getWriter();
				JSONObject responseJson=new JSONObject();
				responseJson.put("_err_code", "10002");
				if(customer == null){
					url = Escape.escape(url);
					StringBuilder sbr = new StringBuilder();
					sbr.append("<script type='text/javascript'>")
						.append("window.open('"+request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+url+"','_top')")
						.append("</script>");
					//将跳转页在整个窗口中显示（如有iframe的可以跳出）
					response.setContentType("text/html;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache, must-revalidate");
					out.write(sbr.toString());  
					out.flush();  
					out.close();
//					responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+  UUID.randomUUID());
//					out.print(responseJson);
//					out.close();
				}else{
					/*ShopInfo shopInfo = WebUtil.getShopInfo(request);//得到店铺对象
					if(shopInfo!=null){
						if(shopInfo.getIsClose()==0){//未关闭
							if(shopInfo.getIsPass()==3){
								chain.doFilter(req, res);//店铺通过审核
							}else if(shopInfo.getIsPass()==2){
								responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=2");
								out.print(responseJson);
								out.close();
							}else{
								responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=4");
								out.print(responseJson);
								out.close();
							}
						}else if(shopInfo.getIsClose()==1){//已关闭
							responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=5");
							out.print(responseJson);
							out.close();
						}
					}else{
						responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=3");
						out.print(responseJson);
						out.close();
					}*/
					chain.doFilter(req, res);//店铺通过审核
				}
			}
		}else if("2".equals(is_ajax)){
			String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
			String pagename = url.substring(url.lastIndexOf("/"), url.length());
			if(!pagename.contains(".action")){
				chain.doFilter(req, res);//店铺通过审核
			}else if(pageNameList.contains(pagename)&&customer != null){
				chain.doFilter(req, res);//店铺通过审核
			}
			else{
				HttpServletResponse response = (HttpServletResponse)res;
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				PrintWriter out = response.getWriter();
				JSONObject responseJson=new JSONObject();
				responseJson.put("_err_code", "10002");
				if(customer == null){
					url = Escape.escape(url);
					StringBuilder sbr = new StringBuilder();
					sbr.append("<script type='text/javascript'>")
						.append("window.open('"+request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+url+"','_top')")
						.append("</script>");
					//将跳转页在整个窗口中显示（如有iframe的可以跳出）
					response.setContentType("text/html;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache, must-revalidate");
					out.write(sbr.toString());  
					out.flush();  
					out.close();
//					responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+  UUID.randomUUID());
//					out.print(responseJson);
//					out.close();
				}else{
					ShopInfo shopInfo = WebUtil.getShopInfo(request);//得到店铺对象
					if(shopInfo!=null){
						if(shopInfo.getIsPass()==3){
							chain.doFilter(req, res);//店铺通过审核
						}else if(shopInfo.getIsPass()==2){
							responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=2");
							out.print(responseJson);
							out.close();
						}else{
							responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=4");
							out.print(responseJson);
							out.close();
						}
					}else{
						responseJson.put("_redirect_url", request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=3");
						out.print(responseJson);
						out.close();
					}
				}
			}
		}else{
			String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
			String pagename = url.substring(url.lastIndexOf("/"), url.length());
			if(!pagename.contains(".action")){
				chain.doFilter(req, res);//店铺通过审核
			}else if(pageNameList.contains(pagename)&&customer != null){
				chain.doFilter(req, res);//店铺通过审核
			}
			else{
				HttpServletResponse response = (HttpServletResponse)res;
				if(customer == null){
					url = Escape.escape(url);
					url=url.replaceAll("%", "_");
					StringBuilder sbr = new StringBuilder();
					sbr.append("<script type='text/javascript'>")
						.append("window.open('"+request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+url+"','_top')")
						.append("</script>");
					//将跳转页在整个窗口中显示（如有iframe的可以跳出）
					response.setContentType("text/html;charset=utf-8");
					response.setHeader("Cache-Control", "no-cache, must-revalidate");
					java.io.PrintWriter out = response.getWriter();
					out.write(sbr.toString());  
					out.flush();  
					out.close();
					//response.sendRedirect(request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+ UUID.randomUUID());
				}else{
//					ShopInfo shopInfo = WebUtil.getShopInfo(request);//得到店铺对象
//					if(shopInfo!=null){
						/*if(shopInfo.getIsClose()!=null&&shopInfo.getIsClose()==0){//未关闭
							if(shopInfo.getIsPass()==3){
								chain.doFilter(req, res);//店铺通过审核
							}else if(shopInfo.getIsPass()!=null&&shopInfo.getIsPass()==2){
								//loginCustomer/appleSeller.action
								///lqshop/loginCustomer/appleSeller.action
								response.sendRedirect(request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=2");//店铺未通过审核
							}else{
								response.sendRedirect(request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=4");//店铺审核中
							}
						}else if(shopInfo.getIsClose()!=null&&shopInfo.getIsClose()==1){//已关闭
							response.sendRedirect(request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=5");//店铺审核中
						}else{
							chain.doFilter(req, res);//店铺通过审核
						}
					}else{
						response.sendRedirect(request.getContextPath()+"/loginCustomer/appleSeller.action?directUrl="+UUID.randomUUID()+"&sellerflag=3");//店铺未申请
					}*/
						chain.doFilter(req, res);//店铺通过审核
//					}
				}
			}
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
		pageNameList.add("/gotoFrontShopInfoPage.action");
		pageNameList.add("/saveOrUpdateShopInfo.action");
		pageNameList.add("/uploadImageFront.action?is_ajax=2");
	}
}
