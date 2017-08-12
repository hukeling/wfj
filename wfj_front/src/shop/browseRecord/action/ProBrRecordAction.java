package shop.browseRecord.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.browseRecord.pojo.ProBrRecord;
import shop.browseRecord.pojo.SingleProBrRecord;
import shop.browseRecord.service.IProBrRecordService;
import shop.customer.pojo.Customer;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.service.ICustomerService;
import shop.customer.service.ISafetyCertificateService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * 浏览商品记录action操作类
 * @author 张攀攀
 *
 */
@SuppressWarnings("all")
public class ProBrRecordAction extends BaseAction {
	private IProBrRecordService proBrRecordService;
	private ICustomerService customerService;
	private IProductInfoService productInfoService;
	private ISafetyCertificateService safetyCertificateService;
	private List<ProBrRecord> proBrRecordList;
	private List<ProductInfo> productInfoList ;
	private ProBrRecord proBrRecord;
	private String proBrRecordId;
	private String ids;
	private Integer productId;
	//修改密码属性
	private String password;
	//找回密码属性
	private String username;
	private String trueAnswer;
	private String userAnswer;
	//用户注册测试属性
	private String checkNo;
	private String loginName;
	private Customer customer;
	//验证用户名是否重复（已存在）
	public void customerCheck() throws IOException{
		customer = (Customer)customerService.getObjectByParams(" where o.loginName='"+loginName+"'");
		if(customer == null){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("ok");
			out.flush();
			out.close();
		}
	}
	//用户注册
	public void customerRegister() throws IOException{
		String trueNo = (String)session.getAttribute("rand");
		session.removeAttribute("rand");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(checkNo != null && checkNo.equals(trueNo)){
			//验证码正确
			if(customer != null){
				//系统时间
				customer.setRegisterDate(new Date());
				customerService.saveOrUpdateObject(customer);
				out.print("ok");
				out.flush();
				out.close();
			}
		}
	}
	//重设密码
	@SuppressWarnings("static-access")
	public void setNewPassword() throws IOException{
		//密码要进行md5加密
		if(password != null){
			Customer customerTemp = (Customer)customerService.getObjectByParams(" where o.loginName='"+username+"'");
			Utils utilMD5 = new Utils();
			String encodeMd5One = utilMD5.EncodeMd5(password);
			String encodeMd5Two = utilMD5.EncodeMd5(encodeMd5One);
			//查询数据库中此客户记录
			if(customerTemp != null){
				customerTemp.setPassword(encodeMd5Two);
				customerService.saveOrUpdateObject(customerTemp);
				PrintWriter out = response.getWriter();
				out.print("ok");
				out.flush();
				out.close();
			}
		}
	}
	//安全问题验证
	@SuppressWarnings("static-access")
	public void safeQuestion() throws IOException{
		Utils utilMD5 = new Utils();
		String encodeMd5 = utilMD5.EncodeMd5(userAnswer);
		String encodeMd5End = utilMD5.EncodeMd5(encodeMd5);
		if(encodeMd5End.equals(trueAnswer)){
			//问题回答正确！
			PrintWriter out = response.getWriter();
			out.print("ok");
			out.flush();
			out.close();
		}
	}
	//找回密码测试
	public void findUser() throws IOException{
		Customer customerTemp = (Customer)customerService.getObjectByParams(" where o.loginName='"+username+"'");
		if(customerTemp != null){
			String customerId = customerTemp.getCustomerId().toString();
			//到用户的安全信息表中去查询安全问题
			SafetyCertificate safeCerTemp = (SafetyCertificate)safetyCertificateService.getObjectByParams(" where o.customerId='"+customerId+"'");
			if(safeCerTemp != null){
				List<String> question = new ArrayList<String>();
				String securityQuestion = safeCerTemp.getSecurityQuestion();
				if(securityQuestion != null && !"".equals(securityQuestion)){
					String[] safeQu = securityQuestion.split(":");
					question.add(safeQu[0]);
					question.add(safeQu[1]);
					//用户名也封装进去
					question.add(username);
				}
				JSONArray jo = JSONArray.fromObject(question);
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	//用户重置密码
	@SuppressWarnings("static-access")
	public void updatePassword() throws IOException{
		//密码要进行md5加密
		if(password != null){
			//模拟客户登陆成功，并将和客户记录放入session中。假如客户名为zpp(密码zpp经过MD5加密)
			Customer customer = new Customer();
			customer.setLoginName("zpp");
			//真实登陆客户
//			Customer customer = (Customer)session.getAttribute("customer");
			Utils utilMD5 = new Utils();
			String encodeMd5One = utilMD5.EncodeMd5(password);
			String encodeMd5Two = utilMD5.EncodeMd5(encodeMd5One);
			//查询数据库中此客户记录
			Customer customerTrue = (Customer)customerService.getObjectByParams(" where o.loginName='zpp'");
			if(customerTrue != null){
				customerTrue.setPassword(encodeMd5Two);
				customerService.saveOrUpdateObject(customerTrue);
				PrintWriter out = response.getWriter();
				out.print("ok");
				out.flush();
				out.close();
			}
		}
	}
	//用户修改密码
	@SuppressWarnings("static-access")
	public void updatePasswordCheck() throws IOException{
		//密码要进行md5加密
		if(password != null){
			//模拟客户登陆成功，并将和客户记录放入session中。假如客户名为zpp(密码zpp经过MD5加密)
			Customer customer = new Customer();
			customer.setLoginName("zpp");
			//真实登陆客户
//			Customer customer = (Customer)session.getAttribute("customer");
			Utils utilMD5 = new Utils();
			String encodeMd5One = utilMD5.EncodeMd5(password);
			String encodeMd5Two = utilMD5.EncodeMd5(encodeMd5One);
			//查询数据库中此客户记录
			Customer customerTrue = (Customer)customerService.getObjectByParams(" where o.loginName='zpp'");
			if(customerTrue != null){
				String truePasword = customerTrue.getPassword();
				PrintWriter out = response.getWriter();
				if(truePasword.equals(encodeMd5Two)){
					//客户输入的密码正确
					out.print("ok");
					out.flush();
					out.close();
				}
			}
		}
	}
	//商品浏览记录保存到cookie中，每天的固定时间将cookie中的值保存在数据库中。
	@SuppressWarnings("unchecked")
	public void opBrRecord(){
		//用户点击商品时，首先将点击记录放到cookie中。之后再同步到内存中（即创建一个单例类来保存记录）
		//模拟用户
		Customer customer = new Customer();
		customer.setCustomerId(123);
		//登陆的客户
		//Customer customer = (Customer)session.getAttribute("customer");
		//当前的系统时间
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String currentTime = sdf.format(new Date());
		Date currentTime = new Date();
		//查询该浏览器的cookie，查看其中是否有了该用户浏览商品的记录。（要判断，添加，去重，追加）
		Cookie[] cookies = request.getCookies();
		boolean memIsExsit = true;//用来判断该用户是否为第一次点击商品
		String cookieValue = "";//放入最新的cookie记录值
		for(Cookie c : cookies){
			if(customer.getCustomerId().equals(c.getName())){
				memIsExsit = false;
				//cookie中已经存在了该用户浏览商品的记录
				String[] goodsArray = c.getValue().split(",");
				boolean flag = true;
				if(goodsArray != null && goodsArray.length > 0){
					//再一次解开cookie中的值，查这个值中的商品ID是否有重复的
					for(int i=0;i<goodsArray.length;i++){
						if(goodsArray[i] != null && !"".equals(goodsArray[i])){
							String[] goodsValue = goodsArray[i].split("_");
							if(goodsValue[0] != null && goodsValue[0].equals(productId)){
								//查询到已经重复的商品浏览记录,新的值放入cookie值中
								cookieValue = productId+"_"+currentTime+","+cookieValue+",";
								flag = false;
							}else{
								//原先不重复的重新放入到cookie值中
								cookieValue += goodsArray[i]+",";
							}
						}
					}
				}
				if(flag){
					//cookie中有当前用户的记录，但此时访问的商品cookie中没有记录。追加此记录
					cookieValue = productId+"_"+currentTime+","+cookieValue;
				}
				c.setValue(cookieValue);
				c.setMaxAge(60*60*24);
				response.addCookie(c);
			}
		}
		if(memIsExsit){
			//本cookie记录该用户首次浏览商品，设置cookie(创建新的cookie)
			cookieValue += productId+"_"+currentTime;
			Cookie newCookie = new Cookie(customer.getCustomerId().toString(), cookieValue);
			newCookie.setMaxAge(60*60*24);
			response.addCookie(newCookie);
		}
		//上面cookie操作完成，接下来将cookie中的值保存到单列类中（在内存中）
		//获得商品浏览记录实例对象单例类
		SingleProBrRecord spbr = SingleProBrRecord.getInstance();
		//查询商品浏览记录单例类，取出其中保存的值作为比较
		Iterator<ProBrRecord> singleProBrRecord = spbr.getSingleProBrRecord();
		//客户名称 ：customer.getCustomerId()，商品ID及时间：cookieValue
		boolean flag = true;
		while(singleProBrRecord.hasNext()){
			ProBrRecord oldPBR = singleProBrRecord.next();
			String oldCustomerId = oldPBR.getCustomerId().toString();
			String oldProductId = oldPBR.getProductId().toString();
			if(oldCustomerId.equals(customer.getCustomerId()) && oldProductId.equals(productId)){
				//有重复的记录，先移除之前的数据，再添加新的记录
				flag = false;
				spbr.removeProBrRecord(oldPBR);
				ProBrRecord newPBR = new ProBrRecord();
				newPBR.setCustomerId(customer.getCustomerId());
				newPBR.setProductId(productId);
				newPBR.setLastBrTime(currentTime);
				spbr.addProBrRecord(newPBR);
				break;
			}
		}
		if(flag){
			//没有重复的,可以继续添加
			ProBrRecord newPBR = new ProBrRecord();
			newPBR.setCustomerId(customer.getCustomerId());
			newPBR.setProductId(productId);
			newPBR.setLastBrTime(currentTime);
			spbr.addProBrRecord(newPBR);
			//添加完成之后查看单例保存集合对象的大小
			//保存完之后，查询记录大小是否已经达到了入库的标准，没达到不做任何操作，打到了就入库，并将记录清零。
			SingleProBrRecord currentSpbr = SingleProBrRecord.getInstance();
			int proBrRecordCount = currentSpbr.getProBrRecordCount();
			//查询点击商品总量数，到了一定的数量（在配置文件中设定的）之后，保存入库。
			Map propertyValue = (Map)session.getAttribute("fileUrlConfig");
			String value = (String)propertyValue.get("browseSaveCount");
			int topCount = Integer.parseInt(value);
			if(proBrRecordCount >= topCount){
				//查询商品浏览记录表中所有数据
				List<ProBrRecord> recordObjects = proBrRecordService.findObjects("");
				//将数据入库
				Iterator<ProBrRecord> pbrList = currentSpbr.getSingleProBrRecord();
				while (pbrList.hasNext()) {
					ProBrRecord nowPBR = pbrList.next();
					String nowMem = nowPBR.getCustomerId().toString();
					String nowPro = nowPBR.getProductId().toString();
					for(ProBrRecord reObject : recordObjects){
						String beforeMem = reObject.getCustomerId().toString();
						String beforePro = reObject.getProductId().toString();
						if(nowMem!=null&&nowPro!=null&&nowMem.equals(beforeMem)&&nowPro.equals(beforePro)){
							//和库中的数据重复，删除库中的数据
							proBrRecordService.deleteObjectByParams(" where o.proBrRecordId='"+reObject.getProBrRecordId()+"'");
							break;
						}
					}
					proBrRecordService.saveOrUpdateObject(nowPBR);
				}
				//入库完成将数据记录删除
				currentSpbr.removeAllProBrRecord();
			}
		}
	}
	//查看商品浏览历史记录
	public void lookRecordByCookie() throws IOException, ParseException{
		//模拟用户
		Customer customer = new Customer();
		customer.setCustomerId(123);
		//登陆的客户
		//Customer customer = (Customer)session.getAttribute("customer");
		Cookie[] cookies = request.getCookies();
		proBrRecordList = new ArrayList<ProBrRecord>();
		for(Cookie c : cookies){
			if((customer.getCustomerId()).equals(c.getName())){
				String[] goodsArray = c.getValue().split(",");
				if(goodsArray != null && goodsArray.length > 0){
					//再一次解开cookie中的值，封装商品浏览记录对象
					for(int i=0;i<goodsArray.length;i++){
						if(goodsArray[i] != null && !"".equals(goodsArray[i])){
							String[] goodsValue = goodsArray[i].split("_");
							if(goodsValue[0] != null && goodsValue[1] != null){
								//查询到已经重复的商品浏览记录,新的值放入cookie值中
								ProBrRecord pBR = new ProBrRecord();
								pBR.setCustomerId(customer.getCustomerId());
								pBR.setProductId(Integer.parseInt(goodsValue[0]));
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date lbtime = sdf.parse(goodsValue[1]);
								pBR.setLastBrTime(lbtime);
								proBrRecordList.add(pBR);
							}
						}
					}
				}
			}
		}
		JSONArray joa = JSONArray.fromObject(proBrRecordList);
		PrintWriter out = response.getWriter();
		out.print(joa.toString());
		out.flush();out.close();
	}
	//查看商品浏览历史记录
	public void lookRecord() throws IOException{
		//假定客户的ID为“1”
		String customerId = "1";
		proBrRecordList = proBrRecordService.findObjects(" where o.customerId='"+customerId+"' order by o.lastBrTime desc");
		JSONArray joa = JSONArray.fromObject(proBrRecordList);
		PrintWriter out = response.getWriter();
		out.print(joa.toString());
		out.flush();out.close();
	}
	//测试浏览商品记录
	public String findProInfo(){
		productInfoList = productInfoService.findObjects("");
		return SUCCESS;
	}
	//前台用户点击商品时添加商品浏览记录
	@SuppressWarnings("unchecked")
	public void operateProBrRecord() throws ParseException{
		//模拟客户数据（设定客户ID为1）
		String customerIdTemp = "1";
		//查询点击商品总量数，到了一定的数量（在配置文件中设定的）之后，保存入库。
		Map propertyValue = (Map)session.getAttribute("fileUrlConfig");
		String value = (String)propertyValue.get("browseSaveCount");
		int topCount = Integer.parseInt(value);
		//获得商品浏览记录实例对象
		SingleProBrRecord spbr = SingleProBrRecord.getInstance();
		ProBrRecord pbrTemp = new ProBrRecord();
		//系统当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = sdf.parse(new Date().toString());
		Iterator<ProBrRecord> singleProBrRecord = spbr.getSingleProBrRecord();
		//判断是否有重复的商品记录
		boolean flag = true;
		while(singleProBrRecord.hasNext()){
			ProBrRecord oldPproBrRecord = singleProBrRecord.next();
			 //判断重复与否的代码
			String memCheckId = oldPproBrRecord.getCustomerId().toString();
			String proCheckId = oldPproBrRecord.getProductId().toString();
			if(customerIdTemp.equals(memCheckId) && productId.equals(proCheckId)){
				//浏览商品重复(删除之前的记录，重新保存当前的)
				flag=false;
				spbr.removeProBrRecord(oldPproBrRecord);
				pbrTemp.setCustomerId(Integer.parseInt(customerIdTemp));
				pbrTemp.setProductId(productId);
				pbrTemp.setLastBrTime(currentTime);
				spbr.addProBrRecord(pbrTemp);
				break;
			}
		}
		if(flag){
			//之前没有重复的记录
			pbrTemp.setCustomerId(Integer.parseInt(customerIdTemp));
			pbrTemp.setProductId(productId);
			pbrTemp.setLastBrTime(currentTime);
			spbr.addProBrRecord(pbrTemp);
			//保存完之后，查询记录大小是否已经达到了入库的标准，没达到不做任何操作，打到了就入库，并将记录清零。
			SingleProBrRecord currentSpbr = SingleProBrRecord.getInstance();
			int proBrRecordCount = currentSpbr.getProBrRecordCount();
			if(proBrRecordCount >= topCount){
				//查询商品浏览记录表中所有数据
				List<ProBrRecord> recordObjects = proBrRecordService.findObjects("");
				//将数据入库
				Iterator<ProBrRecord> pbrList = currentSpbr.getSingleProBrRecord();
				while (pbrList.hasNext()) {
					ProBrRecord nowPBR = pbrList.next();
					String nowMem = nowPBR.getCustomerId().toString();
					String nowPro = nowPBR.getProductId().toString();
					for(ProBrRecord reObject : recordObjects){
						String beforeMem = reObject.getCustomerId().toString();
						String beforePro = reObject.getProductId().toString();
						if(nowMem!=null&&nowPro!=null&&nowMem.equals(beforeMem)&&nowPro.equals(beforePro)){
							//和库中的数据重复，删除库中的数据
							proBrRecordService.deleteObjectByParams(" where o.proBrRecordId='"+reObject.getProBrRecordId()+"'");
							break;
						}
					}
					proBrRecordService.saveOrUpdateObject(nowPBR);
				}
				//入库完成将数据记录删除
				currentSpbr.removeAllProBrRecord();
			}
		}
	}
	//删除商品浏览记录
	public void deleteProBrRecord(){
		proBrRecordService.deleteObjectsByIds("proBrRecordId", ids);
	}
	//得到商品浏览记录
	public void getProBrRecordObject() throws IOException{
		proBrRecord = (ProBrRecord)proBrRecordService.getObjectByParams(" where o.proBrRecordId='"+proBrRecordId+"'");
		JSONObject jo = JSONObject.fromObject(proBrRecord);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//保存商品浏览记录
	public void saveOrUpdateProBrRecord(){
		if(proBrRecord != null && proBrRecord.getProductId() != null && proBrRecord.getCustomerId() != null){
			//查询某该用户商品浏览表的所有所有记录
			String hql = " where o.customerId='"+proBrRecord.getCustomerId()+"'";
			proBrRecordList = proBrRecordService.findObjects(hql);
			boolean flag = true;
			for(ProBrRecord pbr : proBrRecordList){
				if(pbr.getProductId().equals(proBrRecord.getProductId())){
					flag = false;
					pbr.setLastBrTime(proBrRecord.getLastBrTime());
					proBrRecordService.saveOrUpdateObject(pbr);
					break;
				}
			}
			if("".equals(proBrRecord.getProBrRecordId())){
				proBrRecord.setProBrRecordId(null);
			}
			if(flag){
				proBrRecordService.saveOrUpdateObject(proBrRecord);
			}
		}
	}
	//商品浏览记录列表
	public void proBrRecordList() throws IOException{
		int totalRecordCount = proBrRecordService.getCount("");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		proBrRecordList = proBrRecordService.findListByPageHelper(null,pageHelper, " order by o.proBrRecordId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", proBrRecordList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//商品浏览记录页面初始化
	public String gotoProBrRecordPage(){
		//查询所有的用户
		List<Customer> customerList = customerService.findObjects("");
		session.setAttribute("customerList", customerList);
		session.setMaxInactiveInterval(30*24*3600);
		//查询所有商品
		List<ProductInfo> productInfoList = productInfoService.findObjects("");
		session.setAttribute("productInfoList", productInfoList);
		return SUCCESS;
	}
	public void setProBrRecordService(IProBrRecordService proBrRecordService) {
		this.proBrRecordService = proBrRecordService;
	}
	public List<ProBrRecord> getProBrRecordList() {
		return proBrRecordList;
	}
	public void setProBrRecordList(List<ProBrRecord> proBrRecordList) {
		this.proBrRecordList = proBrRecordList;
	}
	public ProBrRecord getProBrRecord() {
		return proBrRecord;
	}
	public void setProBrRecord(ProBrRecord proBrRecord) {
		this.proBrRecord = proBrRecord;
	}
	public String getProBrRecordId() {
		return proBrRecordId;
	}
	public void setProBrRecordId(String proBrRecordId) {
		this.proBrRecordId = proBrRecordId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setSafetyCertificateService(
			ISafetyCertificateService safetyCertificateService) {
		this.safetyCertificateService = safetyCertificateService;
	}
	public String getTrueAnswer() {
		return trueAnswer;
	}
	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Customer getCustomer() {
		return customer;
	}
}
