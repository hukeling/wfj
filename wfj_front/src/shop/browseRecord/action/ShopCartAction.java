package shop.browseRecord.action;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import shop.browseRecord.pojo.GoodsInfo;
import shop.customer.pojo.Customer;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import util.action.BaseAction;
/**
 * 商品购物车action操作类
 * @author 张攀攀
 *
 */
@SuppressWarnings("all")
public class ShopCartAction extends BaseAction {
	private IProductInfoService productInfoService;
	private Integer productId;
	private Integer proCount;
	private List<GoodsInfo> goodsInfo = new ArrayList<GoodsInfo>();
	private Integer[] goodsNumbers;
	//删除购物车中的所有商品
	public String deleteAllProduct(){
		//测试用的用户
		Customer customer = new Customer();
		customer.setCustomerId(123);
//		//当前用户
//		Customer customer = (Customer)session.getAttribute("customer");
		if(customer != null){
			Cookie[] cookies = request.getCookies();
			for(Cookie c : cookies){
				if(("customer_"+customer.getCustomerId()).equals(c.getName())){
					c.setMaxAge(0);//cookie立即失效
					response.addCookie(c);
				}
			}
		}
		return "success";
	}
	//删除购物车中的商品
	public String deleteGoods(){
		if(productId != null){
			//查询当前登录的用户
			//测试用的用户
			Customer customer = new Customer();
			customer.setCustomerId(123);
//			//当前用户
//			Customer customer = (Customer)session.getAttribute("customer");
			if(customer != null){
				Cookie[] cookies = request.getCookies();
				for(Cookie c : cookies){
					if(("customer_"+customer.getCustomerId()).equals(c.getName())){
						String value = c.getValue();
						String[] goodsArray = value.split(",");
						//标记找到的字符串下标
						int flag = -1;
						for(int i=0;i<goodsArray.length;i++){
							String[] goodsValue = goodsArray[i].split("_");
							if(goodsValue[0].equals(productId)){
								//找到了对应商品的字符串
								flag = i;
								break;
							}
						}
						//重新把值放入
						String newValue = "";
						for(int j=0;j<goodsArray.length;j++){
							if(j != flag){
								newValue += goodsArray[j]+",";
							}
						}
						c.setValue(newValue);
						c.setMaxAge(60*60*24*7);
						response.addCookie(c);
					}
				}
			}
		}
		putProToShopCar();
		return SUCCESS;
	}
	//更新购物车中的所有商品
	public String updateAllProduct(){
		//更新购买商品的数量
		//测试用的用户
		Customer customer = new Customer();
		customer.setCustomerId(123);
//		//当前用户
//		Customer customer = (Customer)session.getAttribute("customer");
		if(customer != null && goodsNumbers.length != 0){
			Cookie[] cookies = request.getCookies();
			for(Cookie c : cookies){
				if(("customer_"+customer.getCustomerId()).equals(c.getName())){
					String value = c.getValue();
					String newValue = "";
					String[] goodsArray = value.split(",");
					int a = 0;
						for(int i=0;i<goodsArray.length;i++){
							if(!"".equals(goodsArray[i])){
								//对Cookie进行更新操作
								String[] goodsValue = goodsArray[i].split("_");
								ProductInfo proInfo = new ProductInfo();
								proInfo = (ProductInfo)productInfoService.getObjectByParams(" where o.productId="+goodsValue[0]);
								//Double totalMoney = (proInfo.getMemberPrice()*goodsNumbers[i-a]);
								//newValue += goodsValue[0]+"_"+goodsNumbers[i-a]+"_"+totalMoney+",";
							}else{
								++a;
							}
						}
						c.setValue(newValue);
						c.setMaxAge(60*60*24*7);
						response.addCookie(c);
						//防止跳转后将商品重复加入
				}	
			}
		}
		return "success";
	}
	//将商品添加到购物车
	public String putProToShopCar(){
		//测试用的用户
		Customer customer = new Customer();
		customer.setCustomerId(123);
//		//当前用户
//		Customer customer = (Customer)session.getAttribute("customer");
		//购物车中不同商品的的数量
//		 Integer productNumber = 0;
		//通过商品Id查询商品信息
		ProductInfo productInfo = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+productId+"'");
		if(customer != null){
			//从Cookie中取出已经选择的商品
			Cookie [] cookies = request.getCookies();
			List<GoodsInfo> goodsInfoTemp = new ArrayList<GoodsInfo>();
			boolean flag = true;
			int pIndex = -1;//重复商品的位置
			int number = 0;//商品的新数量
				for(Cookie c : cookies){
					if(("customer_"+customer.getCustomerId()).equals(c.getName())){
						if(!"".equals(c.getValue())){
							flag = false;
							String value = c.getValue();
							String[] goodsArray = value.split(",");
							//定义添加了新商品，但没有和之前重复
							boolean eE = true;
							for(int i=0;i<goodsArray.length;i++){
								if(!"".equals(goodsArray[i])){
									String[] goodsValue = goodsArray[i].split("_");
									//判断是否有重复的商品
									if(productId != null && proCount != null){
										//添加了新商品，判断是否和之前的重复，若重复则替换之前的商品，并把商品数量累加
										if(goodsValue[0].equals(productId)){
											//有重复的商品
											eE = false;
											GoodsInfo gsi = new GoodsInfo();
											ProductInfo pi = new ProductInfo();
											pi = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+goodsValue[0]+"'");
											//新商品数量
											number = Integer.parseInt(goodsValue[1])+proCount;
											//商品总价格
											//Double totalMoney = pi.getMemberPrice()*number;
											gsi.setProductInfo(pi);
											gsi.setGoodsNumber(number);
											//gsi.setTotalMoney(totalMoney);
											goodsInfoTemp.add(gsi);
											//做标记，去修改cookie中的值
											pIndex=i;
										}else{
											GoodsInfo gsi = new GoodsInfo();
											ProductInfo pi = new ProductInfo();
											pi = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+goodsValue[0]+"'");
											gsi.setProductInfo(pi);
											gsi.setGoodsNumber(Integer.parseInt(goodsValue[1]));
											gsi.setTotalMoney(Double.parseDouble(goodsValue[2]));
											goodsInfoTemp.add(gsi);
										}
									}else{
										//没有添加商品商品不会重复
										GoodsInfo gsi = new GoodsInfo();
										ProductInfo pi = new ProductInfo();
										pi = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+goodsValue[0]+"'");
										gsi.setProductInfo(pi);
										gsi.setGoodsNumber(Integer.parseInt(goodsValue[1]));
										gsi.setTotalMoney(Double.parseDouble(goodsValue[2]));
										goodsInfoTemp.add(gsi);
									}
								}
							}
							if(productInfo != null && proCount != null && eE){
								//改变cookie的值
								//Double totalMoney = (productInfo.getMemberPrice()*proCount);
								//String addStr = ","+productInfo.getProductId()+"_"+proCount+"_"+totalMoney;
								//String newValue = value+addStr;
								//c.setValue(newValue);
								c.setMaxAge(60*60*24*7);
								response.addCookie(c);
								//改变产品信息列表
								GoodsInfo gsi2 = new GoodsInfo();
								productInfo = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+productId+"'");
								gsi2.setGoodsNumber(proCount);
								gsi2.setProductInfo(productInfo);
								//gsi2.setTotalMoney(totalMoney);
								goodsInfoTemp.add(gsi2);
								goodsInfo = goodsInfoTemp;
							}else{
								goodsInfo = goodsInfoTemp;
							}
						}
					}
				}
				if(flag){
					if(productInfo != null && proCount != null){
						GoodsInfo gsi2 = new GoodsInfo();
						productInfo = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+productId+"'");
						//Double totalMoney = (productInfo.getMemberPrice()*proCount);
						gsi2.setGoodsNumber(proCount);
						gsi2.setProductInfo(productInfo);
						//gsi2.setTotalMoney(totalMoney);
						goodsInfoTemp.add(gsi2);
						goodsInfo = goodsInfoTemp;
						//第一件商品添加到cookie中
						//setCookie(customer.getCustomerId(), productInfo.getProductId(), proCount, totalMoney);
				    }
				}
				//判断是否有重复的商品，修改cookie中的值
				if(pIndex != -1){
					//有重复的商品需要修改cookie中的值
					for(Cookie c : cookies){
						if(c.getName().equals("customer_"+customer.getCustomerId())){
							String value = c.getValue();
							String[] split = value.split(",");
							ProductInfo pi = new ProductInfo();
							pi.setProductId(productInfo.getProductId());
							pi = (ProductInfo)productInfoService.getObjectByParams(" where o.productId='"+productId+"'");
							//总金额
							//Double totalM = number*pi.getMemberPrice();
							String newValue = "";
							for(int i=0;i<split.length;i++){
								if(i==0){
									if(pIndex == i){
										//商品重复的位置是第一个，拼接字符串
									//	String va = productInfo.getProductId()+"_"+number+"_"+totalM;
									//	newValue += va;
									}else{
										newValue += split[i];
									}
								}else{
									if(pIndex == i){
										//商品重复的位置是第一个，拼接字符串
										//String va = productInfo.getProductId()+"_"+number+"_"+totalM;
									//	newValue += ","+va;
									}else{
										newValue += ","+split[i];
									}
								}
							}
							c.setValue(newValue);
							c.setMaxAge(60*60*24*7);
							response.addCookie(c);
						}
					}
				}
			}
		return SUCCESS;
	}
	//添加cookie
	private void setCookie(Integer customerId, Integer productId,Integer proCount2,Double totalMoney2) {
		Cookie[] cookies = request.getCookies();
		boolean flag = true;
		for(Cookie c : cookies){
			if(("customer_"+customerId).equals(c.getName())){
				String value = c.getValue();
				String newValue = value+","+productId+"_"+proCount2+"_"+totalMoney2;
				c.setValue(newValue);
				c.setMaxAge(60*60*24*7);
				response.addCookie(c);
				flag = false;
			}
		}
		if(flag){
			Cookie cookie = new Cookie("customer_"+customerId,productId+"_"+proCount2+"_"+totalMoney2);
			cookie.setMaxAge(60*60*24*7);//设置cookie的有效期为7天
			response.addCookie(cookie);
		}
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProCount() {
		return proCount;
	}
	public void setProCount(Integer proCount) {
		this.proCount = proCount;
	}
	public List<GoodsInfo> getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(List<GoodsInfo> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public Integer[] getGoodsNumbers() {
		return goodsNumbers;
	}
	public void setGoodsNumbers(Integer[] goodsNumbers) {
		this.goodsNumbers = goodsNumbers;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
}
