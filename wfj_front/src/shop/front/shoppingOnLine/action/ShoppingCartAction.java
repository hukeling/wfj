package shop.front.shoppingOnLine.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import rating.buyersRecord.service.IBuyersRecordService;
import shop.customer.pojo.Customer;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartOrderService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductSpecificationValueService;
import shop.store.pojo.MemberShip;
import shop.store.service.IMemberShipService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * ShoppingCartAction - 商品购物车
 */
public class ShoppingCartAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**shoppingService**/
	private IShoppingCartService shoppingCartService;
	/**购物车订单Service**/
	private IShoppingCartOrderService shoppingCartOrderService;
	/**购物车信息集合**/
	private Map<Object,Object> cartMap = new HashMap<Object,Object>();
	/**shoppingIds**/
	private String ids;
	/**购物车的json格式**/
	private String jsonCart;
	/**购物车ids**/
	private String shopCartIds;
	/**折扣钱数**/
	private String discount;
	private String num;//购买数量
	private String shopInfoId;//店铺ID
	private String parameters;//选择的规格值
	private String goods;//商品分组
	private IProductInfoService productInfoService;//商品基本信息Service
	private IProductSpecificationValueService productSpecificationValueService;//商品和规格值中间关系Service
	private Integer productId;
	private String stockUpDate;//预期发货日
	private BigDecimal buyPrice;//商品成交价
	private String productData;//批量添加商品
	private String sku;//商品sku订货号
	/**buyPrice是否为会员价**/
	private String buyPriceCustomer="0";
	/**等级升迁service**/
	private IBuyersRecordService buyersRecordService;
	private IMemberShipService memberShipService;
	/**
	 * 加入购物车
	 * @return
	 */
	public String addProductToShoppCart(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			shoppingCartService.saveOrUpdateCar(productId, num,shopInfoId,customer,stockUpDate,sku);
		}
		return SUCCESS;
	}
	/**
	 * 批量加入购物车
	 */
	public String addProductsToShopCart(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			shoppingCartService.saveOrUpdateShopCar(customer,productData);
		}
		return SUCCESS;
	}
	/**
	 * 根据sku批量添加商品到购物车
	 */
	public String addSkusToShopCart(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			shoppingCartService.saveOrUpdateShopCarBySku(customer,productData);
		}
		return SUCCESS;
	}
	/**
	 * 商品详情页 异步加入购物车方法
	 */
	public void addSkusToShopCartAjax() throws IOException{
		Customer customer = (Customer) session.getAttribute("customer");
		boolean b=false;//标记是否成功加入购物车
		if(null!=customer){
			b = shoppingCartService.saveOrUpdateShopCarBySku(customer,productData);
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", String.valueOf(b));
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 *根据用户id 查找购物车信息
	 * @return
	 */
	public String gotoShoppingCart(){
    	Customer customer = (Customer) request.getSession().getAttribute("customer");
    	Cookie[] cookie = request.getCookies();
    	Map cartCountMap=new HashMap<Object, Object>();
    	cartMap = shoppingCartService.gotoShoppingCart(customer,cookie,cartCountMap);
    	session.setAttribute("cartMap", cartMap);
    	session.setAttribute("cartCountMap", String.valueOf(cartCountMap.get("count")));
    	if(cartMap==null||cartMap.size()==0){
    		cartMap=null;
    	}
    	return SUCCESS;
    }
	/**
	 *根据用户id 查找购物车信息_ajax
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void gotoShoppingCartAjax() throws IOException{
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		Map cartCountMap=new HashMap<Object, Object>();
		Cookie[] cookies = request.getCookies();
		List<Map> listMap=new ArrayList<Map>();
		if(customer!=null){//用户登录情况下
        	//购物车信息
        		String hql="select c.productFullName as productFullName , c.storeNumber as storeNumber, a.quantity as quantity, a.stockUpDate as stockUpDate,c.sku as sku, c.marketPrice as marketPrice, a.shopCartId as shopCartId,b.shopInfoId as shopInfoId,c.productId as productId ," +
    			" c.describle as describle, c.productName as productName,c.salesPrice as salesPrice,d.brandId as brandId," +
    			" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale, c.stockUpDate as stockUpDate,"+
    			" b.shopName as shopName,b.customerName as customerName,c.logoImg as smallImgUrl ,c.freightPrice as freightPrice from ShoppingCart a ,ShopInfo b ,ProductInfo c ,Brand d " +
    			" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId and c.isPutSale = 2 and a.customerId="+customer.getCustomerId();
        		List<Map> list= shoppingCartService.findListMapByHql(hql);
        		if(Utils.collectionIsNotEmpty(list)){
        			for(Map map:list){
        				if(customer.getType()==2){
        					Integer j=0;
            				if(Integer.parseInt(String.valueOf(map.get("shopIsPass")))!=3){
            					j=1;
            				}
            				if(Integer.parseInt(String.valueOf(map.get("isClose")))!=0){
            					j=1;
            				}
            				if(Integer.parseInt(String.valueOf(map.get("isPass")))!=1){
            					j=1;
            				}
            				if(Integer.parseInt(String.valueOf(map.get("isPutSale")))!=2){
            					j=1;
            				}
            				map.put("flag", j);
            				//计算成交价格
            				buyPrice=new BigDecimal(0);
            				getCustomerProductPrice(customer,new BigDecimal(String.valueOf(map.get("marketPrice"))),new BigDecimal(String.valueOf(map.get("salesPrice"))));
            				map.put("buyPrice", String.valueOf(map.get("salesPrice")));
            				listMap.add(map);
        				}else{
        					Integer j=0;
            				if(Integer.parseInt(String.valueOf(map.get("shopIsPass")))!=3){
            					j=1;
            				}
            				if(Integer.parseInt(String.valueOf(map.get("isClose")))!=0){
            					j=1;
            				}
            				if(Integer.parseInt(String.valueOf(map.get("isPass")))!=1){
            					j=1;
            				}
            				if(Integer.parseInt(String.valueOf(map.get("isPutSale")))!=2){
            					j=1;
            				}
            				map.put("flag", j);
            				//计算成交价格
            				buyPrice=new BigDecimal(0);
            				getCustomerProductPrice(customer,new BigDecimal(String.valueOf(map.get("marketPrice"))),new BigDecimal(String.valueOf(map.get("salesPrice"))));
            				map.put("buyPrice", buyPrice);
            				listMap.add(map);
        				}
        			}
        	}
    	}else{//用户没有登录时直接访问购物车
    		if(Utils.objectIsNotEmpty(cookies)){
    			for(Cookie cookie:cookies){
    				if(cookie.getName().contentEquals("customerCar")&&!"'null'".equals(cookie.getValue())){
    					try {
    						String cookieValues = URLDecoder.decode(cookie.getValue(), "UTF-8");//cookie解码
    						JSONArray ja = JSONArray.fromObject(cookieValues);//json数组格式化
    						for(Object ob :ja){//获取当前cookie中店铺id,Set集合去重
    							if(!ob.equals("null")){
    								JSONObject jo = JSONObject.fromObject(ob);
    								String hql2="select c.productFullName as productFullName , c.storeNumber as storeNumber, c.marketPrice as marketPrice, b.shopInfoId as shopInfoId,c.productId as productId ," +
        									" c.describle as describle, c.productName as productName,c.salesPrice as salesPrice,d.brandId as brandId," +
        									" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale, c.stockUpDate as stockUpDate,c.sku as sku,"+
        									" b.shopName as shopName,b.customerName as customerName,c.logoImg as smallImgUrl  from ShopInfo b ,ProductInfo c ,Brand d " +
        									" where d.brandId=c.brandId and c.productId="+jo.getString("productId");
        							List<Map> list2= shoppingCartService.findListMapByHql(hql2);
        							if(Utils.collectionIsNotEmpty(list2)){
        								Map map1=list2.get(0);
    									Integer j=0;
    									if(Utils.objectIsNotEmpty(map1.get("shopIsPass"))&&Integer.parseInt(String.valueOf(map1.get("shopIsPass")))!=3){
    										j=1;
    									}
    									if(Utils.objectIsNotEmpty(map1.get("isClose"))&&Integer.parseInt(String.valueOf(map1.get("isClose")))!=0){
    										j=1;
    									}
    									if(Utils.objectIsNotEmpty(map1.get("isPass"))&&Integer.parseInt(String.valueOf(map1.get("isPass")))!=1){
    										j=1;
    									}
    									if(Utils.objectIsNotEmpty(map1.get("isPutSale"))&&Integer.parseInt(String.valueOf(map1.get("isPutSale")))!=2){
    										j=1;
    									}
    									map1.put("flag", j);
    									map1.put("buyPrice", String.valueOf(map1.get("salesPrice")));
    									map1.put("quantity", jo.getString("num"));
    			        				listMap.add(map1);
        							}
    							}
    						}
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}
    			}
    		}
    	}
		session.setAttribute("cartCountMap", listMap.size());
		JSONArray jo = JSONArray.fromObject(listMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(String.valueOf(jo));
		pw.flush();
		pw.close();
	}
	/**
	 * 计算商品会员价
	 */
	public void getCustomerProductPrice(Customer customer,BigDecimal marketPrice,BigDecimal salesPrice){
		if(customer!=null){
			String sql = "select br.levelDiscountValue as levelDiscountValue, br.buyersLevel as buyersLevel from BuyersRecord br "
					+ "where br.customerId="+customer.getCustomerId()
					+ "order by br.optionDate desc";
			List<Map> listbs= buyersRecordService.findListMapByHql(sql);
			if(Utils.objectIsNotEmpty(marketPrice)){
				if(marketPrice.compareTo(new BigDecimal(0))>0){//将0与市场价格比较
					if(listbs!=null&&listbs.size()>0){
						Map bs = listbs.get(0);
						Object object = bs.get("levelDiscountValue");
						Integer buyersLevel = Integer.valueOf(bs.get("buyersLevel").toString());
						if(object!=null){
							//会员等级大于等于3
							if(buyersLevel>=3){
								BigDecimal bilv =new BigDecimal(String.valueOf(object));
								bilv = bilv.divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN);//0.几的比率
								BigDecimal discount = bilv.multiply(salesPrice);//优惠价
								discount = salesPrice.subtract(discount);//实际售价
								//将BigDecimal 转换成Integer
								String str = String.valueOf(discount);
								String bilv1 = String.valueOf(bilv);
								//判断str是否包含“.”
								if(str.indexOf(".")>0&&bilv1.indexOf(".")>0){
									str=str.substring(0, str.lastIndexOf("."));
									buyPrice = discount.divide(new BigDecimal(1),2, BigDecimal.ROUND_HALF_EVEN);
								}
							}else{
									buyPrice= salesPrice;//销售价
							}
						}
					}
				}
			}
		}
	}
    /**
     * 删除购物车中的商品
     * ids可以批量删除
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public void deleteShoppingCart() throws IOException{
    	Customer customer = (Customer) session.getAttribute("customer");
    	Boolean bool=shoppingCartService.deleteObjectByParams("where o.productId in ("+ids+") and o.customerId="+customer.getCustomerId());
    	JSONObject jo = JSONObject.fromObject(bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(String.valueOf(jo));
		pw.flush();
		pw.close();
    }
    /**提交到订单确认**/
    public String toCheckOut(){
    	Customer customer = (Customer) session.getAttribute("customer");
    	if(StringUtils.isNotEmpty(jsonCart)){
    		if(StringUtils.isEmpty(shopCartIds)){
    			shopCartIds="";
    		}
    		JSONArray array = JSONArray.fromObject(jsonCart);//先读取串
    		Object[] obj = array.toArray();  //转成对像数组
    		for(int i=0;i<obj.length;i++){
    			JSONObject a=JSONObject.fromObject(obj[i]);//再使用JsonObject遍历一个个的对像
    			Test test=(Test) JSONObject.toBean(a, Test.class);////指定转换的类型，但仍需要强制转化-成功
    			//通过商品Id查询商品信息
    			ProductInfo productInfo = (ProductInfo)productInfoService.getObjectByParams(" where o.productId="+test.getProductId());
    			MemberShip memberShip = (MemberShip) memberShipService.getObjectByParams(" where o.state=2 and o.shopInfoId="+productInfo.getShopInfoId()+" and o.customerId="+customer.getCustomerId());
    			ShoppingCart shopCart=(ShoppingCart) shoppingCartService.getObjectByParams(" where o.productId="+test.getProductId()+" and o.customerId="+customer.getCustomerId());
    			if(Utils.objectIsNotEmpty(shopCart)){
    				if(Utils.objectIsNotEmpty(memberShip)){
    					shopCart.setDiscount(memberShip.getDiscount());
    				}
    				shopCart.setQuantity(Integer.parseInt(test.getCount()));
    				shoppingCartService.saveOrUpdateObject(shopCart);
    				shopCartIds=shopCartIds+","+shopCart.getShopCartId();
    			}
    		}
    		if(StringUtils.isNotEmpty(shopCartIds)){
    			shopCartIds = shopCartIds.substring(1, shopCartIds.length());
    		}
    	}
    	return "toShopCartOrder";
    }
    /**
     * 内部类，将jsonString 转化成javaBean使用
     * @author Administrator
     *
     */
 public static class Test{
	    /**商品id**/
    	private String productId;
    	/**数量**/
    	private String count;
    	/**总价**/
    	private String totalPrice;
		private String quantity;
		/**预计出货日**/
		private String stockUpDate;
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(String totalPrice) {
			this.totalPrice = totalPrice;
		}
		public String getStockUpDate() {
			return stockUpDate;
		}
		public void setStockUpDate(String stockUpDate) {
			this.stockUpDate = stockUpDate;
		}
    }
	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Map<Object, Object> getCartMap() {
		return cartMap;
	}
	public void setCartMap(Map<Object, Object> cartMap) {
		this.cartMap = cartMap;
	}
	public void setShoppingCartOrderService(
			IShoppingCartOrderService shoppingCartOrderService) {
		this.shoppingCartOrderService = shoppingCartOrderService;
	}
	public String getJsonCart() {
		return jsonCart;
	}
	public void setJsonCart(String jsonCart) {
		this.jsonCart = jsonCart;
	}
	public String getShopCartIds() {
		return shopCartIds;
	}
	public void setShopCartIds(String shopCartIds) {
		this.shopCartIds = shopCartIds;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setProductSpecificationValueService(
			IProductSpecificationValueService productSpecificationValueService) {
		this.productSpecificationValueService = productSpecificationValueService;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getStockUpDate() {
		return stockUpDate;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public void setStockUpDate(String stockUpDate) {
		this.stockUpDate = stockUpDate;
	}
	public String getProductData() {
		return productData;
	}
	public void setProductData(String productData) {
		this.productData = productData;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getBuyPriceCustomer() {
		return buyPriceCustomer;
	}
	public void setBuyPriceCustomer(String buyPriceCustomer) {
		this.buyPriceCustomer = buyPriceCustomer;
	}
	public void setBuyersRecordService(IBuyersRecordService buyersRecordService) {
		this.buyersRecordService = buyersRecordService;
	}
	public void setMemberShipService(IMemberShipService memberShipService) {
		this.memberShipService = memberShipService;
	}
}