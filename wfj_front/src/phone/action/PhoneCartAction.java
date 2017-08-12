package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import discountcoupon.service.ICustomerdiscountcouponService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.MallCoin;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.customer.service.ICustomerService;
import shop.customer.service.IMallCoinService;
import shop.front.shoppingOnLine.action.ShoppingCartAction;
import shop.front.shoppingOnLine.action.ShoppingCartAction.Test;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartOrderService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.MemberShip;
import shop.store.service.IMemberShipService;
import util.action.BaseAction;
import util.other.Utils;
import util.service.IBaseService;
import phone.pojo.Cart;

/**
 * 购物车接口
 * /phone/*.action
 * @author Norberta
 *
 */
@SuppressWarnings("all")
public class PhoneCartAction extends BaseAction {
	private IShoppingCartService shoppingCartService;//购物车service
	private IProductInfoService productInfoService;//商品service
	private ICustomerService customerService;//客户信息service
	private IMemberShipService memberShipService;//客户关系service
	private IShoppingCartOrderService shoppingCartOrderService;//购物车生成订单service
	private ICustomerAcceptAddressService customerAcceptAddressService;//收货地址service
	private IMallCoinService mallCoinService;//商城币service
	private String num;//接收商品数量
	private String customerId;//用户id
	//多件商品的json字符串接受
	private String jsonCart;
	private String productIds;//商品id,拼接
	private String shopCartIds;//购物车ids
	private List<Map> cartMap;
	private Map<Object, Object> shopInfoMap;
	private String isAllTransactionFlag;
	private BigDecimal buyPrice;
	private BigDecimal coinTotal;
	private String buyPriceCustomer = "0";
	/** 总订单号 */
	private String totalOrdersNo;
	/** 子订单号字符串组 */
	private String ordersNoAll;
	/** 支付宝显示的商品描述 **/
	private String subject = "您从SHOPJSP购买商品";
	/** 增值税发票抬头 */
	private String shopInfoCompanyName;
	private String discount;
	/** 订单号 **/
	private String ordersNo;

	private IOrdersService ordersService;
	/** 用户地址List **/
	private List<CustomerAcceptAddress> customerAcceptAddressList;
	/** 订单 **/
	private Orders orders = new Orders();
	/** 商品Id直接购买，不经过购物车 **/
	private Integer productId;
	/** 店铺Id直接购买，不经过购物车 **/
	private String shopInfoId;

	private String addressisok;// 配送方式
	private String customerDiscountCouponID;// 优惠券id
	private ICustomerdiscountcouponService customerdiscountcouponService;// 用户优惠卷service
	private List<Map> ordersListMap;// 优惠券集合
	@SuppressWarnings({ "unused", "rawtypes" })
	private List<Map> toPayList;
	/** 直接购买商品的数量 **/
	private Integer productNum;
	private Double salesPrice;
	/** 获取国家信息 **/
	@SuppressWarnings("rawtypes")
	private List<Map> countrys; // 国家
	/** 支付宝参数map **/
	private Map<String, Object> payMap = new HashMap<String, Object>();

	private CustomerAcceptAddress custAddress;

	@Resource
	private IOrdersListService ordersListService;

	private String ordersId;

	//商品加入购物车
	public void addProToCart() throws IOException {
		String customerId = (String) session.getAttribute("customerId");
		if (customerId != null) {
			Customer customer = (Customer) customerService
					.getObjectById(customerId);
			ProductInfo productInfo = (ProductInfo) productInfoService
					.getObjectById(productId.toString());
			 shoppingCartService.saveOrUpdateCar(productId, "1", productInfo
					.getShopInfoId().toString(), customer, productInfo
					.getStockUpDate().toString(), productInfo.getSku());
		}
	}
	
	//再次购买
	public String readdToPhoneCart() {
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		List<OrdersList> list = ordersListService
				.findObjects(" where ordersId=" + ordersId);
		if (list.size() > 0) {
			for (OrdersList ordersList : list) {
				ProductInfo productInfo = (ProductInfo) productInfoService
						.getObjectById(ordersList.getProductId().toString());
				shoppingCartService.saveOrUpdateCar(productId, "1", productInfo
						.getShopInfoId().toString(), customer, productInfo
						.getStockUpDate().toString(), productInfo.getSku());
			}
		}
		return SUCCESS;
	}

	/**
	 * 跳转到订单商品列表页面
	 * 
	 * @return
	 */
	public String orderPro() {
		return SUCCESS;
	}

	//跳转到购物车页面
	public String goPhoneCart() {
		session.setAttribute("customerId", customerId);
		return SUCCESS;
	}

	//跳转到购物车页面
	public String gotoPhoneCart() {
		if (customerId == null) {
			customerId = (String) session.getAttribute("customerId");
		}
		List<ShoppingCart> cartList = shoppingCartService
				.findObjects(" where customerId=" + customerId);
		List<Cart> list = new ArrayList<Cart>();
		for (ShoppingCart shoppingCart : cartList) {
			ProductInfo productInfo = (ProductInfo) productInfoService
					.getObjectById(shoppingCart.getProductId().toString());
			Cart cart = new Cart();
			cart.setShoppingCart(shoppingCart);
			cart.setProductInfo(productInfo);
			list.add(cart);
		}
		request.setAttribute("customerId", customerId);
		session.setAttribute("customerId", customerId);
		session.setMaxInactiveInterval(7 * 24 * 3600);
		request.setAttribute("list", list);
		return SUCCESS;
	}

	//购物车页面商品加减
	public String changeNum() throws IOException {
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		ShoppingCart cart = (ShoppingCart) shoppingCartService
				.getObjectByParams(" where customerId=" + customerId
						+ " and productId=" + productId);
		List<Cart> list = new ArrayList<Cart>();
		cart.setQuantity(cart.getQuantity() + Integer.parseInt(num));
		shoppingCartService.saveOrUpdateCar(productId, num, cart
				.getShopInfoId().toString(), customer, cart.getStockUpDate(),
				cart.getSku());
		List<ShoppingCart> cartList = shoppingCartService
				.findObjects(" where customerId=" + customerId);
		for (ShoppingCart shoppingCart : cartList) {
			ProductInfo productInfo = (ProductInfo) productInfoService
					.getObjectById(shoppingCart.getProductId().toString());
			Cart c = new Cart();
			c.setShoppingCart(shoppingCart);
			c.setProductInfo(productInfo);
			list.add(c);
		}
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		jo.accumulate("data", cart.getQuantity());
		pw.println(jo);
		pw.flush();
		pw.close();
		request.setAttribute("customerId", customerId);
		session.setAttribute("customerId", customerId);
		session.setMaxInactiveInterval(7 * 24 * 3600);
		request.setAttribute("list", list);
		return SUCCESS;
	}

	//购物车页面跳转到订单确认页面
	public String toCheckOut() {
		if (session.getAttribute("customerId") != null) {
			customerId = session.getAttribute("customerId").toString();
		}
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		session.setAttribute("customer", customer);
		session.setAttribute("customerId", customerId);
		session.setMaxInactiveInterval(30 * 24 * 3600);
		if (StringUtils.isNotEmpty(jsonCart)) {

			if (StringUtils.isEmpty(shopCartIds)) {
				shopCartIds = "";
			}
			JSONArray array = JSONArray.fromObject(jsonCart);// 先读取串
			Object[] obj = array.toArray(); // 转成对像数组
			for (int i = 0; i < obj.length; i++) {
				JSONObject a = JSONObject.fromObject(obj[i]);// 再使用JsonObject遍历一个个的对像
				Test test = (Test) JSONObject.toBean(a, Test.class);// //指定转换的类型，但仍需要强制转化-成功
				// 通过商品Id查询商品信息
				ProductInfo productInfo = (ProductInfo) productInfoService
						.getObjectByParams(" where o.productId="
								+ test.getProductId());
				MemberShip memberShip = (MemberShip) memberShipService
						.getObjectByParams(" where o.state=2 and o.shopInfoId="
								+ productInfo.getShopInfoId()
								+ " and o.customerId=" + customerId);
				ShoppingCart shopCart = (ShoppingCart) shoppingCartService
						.getObjectByParams(" where o.productId="
								+ test.getProductId() + " and o.customerId="
								+ customerId);
				if (Utils.objectIsNotEmpty(shopCart)) {
					if (Utils.objectIsNotEmpty(memberShip)) {
						shopCart.setDiscount(memberShip.getDiscount());
					}
					shopCart.setQuantity(Integer.parseInt(test.getCount()));
					shoppingCartService.saveOrUpdateObject(shopCart);
					shopCartIds = shopCartIds + "," + shopCart.getShopCartId();
				}
			}
			if (StringUtils.isNotEmpty(shopCartIds)) {
				shopCartIds = shopCartIds.substring(1, shopCartIds.length());
			}
		}
		session.setAttribute("shopCartIds", shopCartIds);
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式大小写有区别
		String sysDatetime = fmt.format(rightNow.getTime());
		String hql = "";
		cartMap = new ArrayList<Map>();
		if (productId != null) {
			// 直接购买
			hql = "select d.brandId as brandId, c.marketPrice as marketPrice,b.shopInfoId as shopInfoId,c.productId as productId ,c.productFullName as productFullName,"
					+ " c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice ,"
					+ " b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl from ShopInfo b ,ProductInfo c,Brand d"
					+ " where b.shopInfoId=c.shopInfoId and d.brandId=c.brandId and c.productId ="
					+ productId + " and  b.shopInfoId=" + shopInfoId;
			cartMap = shoppingCartOrderService.findListMapByHql(hql);
		} else if (StringUtils.isNotEmpty(shopCartIds)) {
			// 购物车信息
			hql = "select d.brandId as brandId, a.quantity as quantity,a.discount as discount, c.marketPrice as marketPrice, a.shopCartId as shopCartId, a.stockUpDate as stockUpDate, b.shopInfoId as shopInfoId,b.minAmount as minAmount,b.postage as postage,c.productId as productId ,"
					+ " c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice, c.storeNumber as storeNumber, c.productFullName as productFullName,"
					+ " b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale,c.stockUpDate as stockUpDate,c.sku as sku,"
					+ " b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl  from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d"
					+ " where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId in("
					+ shopCartIds
					+ ") and a.customerId="
					+ customer.getCustomerId();
			cartMap = shoppingCartOrderService.findListMapByHql(hql);
		}
		if (cartMap != null && cartMap.size() > 0) {
			shopInfoMap = new HashMap<Object, Object>();// 存放店铺集合
			List<Map> list = new ArrayList<Map>();// 一个list表示一个店铺
			Integer isAllTransaction = 0;// 记录订单中商品不能交易个数
			for (Map<String, Object> map : cartMap) {// 迭代出每一件购买的商品
				Integer isTransaction = 0;
				if (Integer.parseInt(String.valueOf(map.get("shopIsPass"))) != 3) {
					isTransaction = 1;
				}
				if (Integer.parseInt(String.valueOf(map.get("isClose"))) != 0) {
					isTransaction = 1;
				}
				if (Integer.parseInt(String.valueOf(map.get("isPass"))) != 1) {
					isTransaction = 1;
				}
				if (Integer.parseInt(String.valueOf(map.get("isPutSale"))) != 2) {
					isTransaction = 1;
				}
				if (isTransaction == 1) {
					isAllTransaction++;
				}
				map.put("flag", 0);
				list = new ArrayList<Map>();
				if (shopInfoMap.get(map.get("shopInfoId")) == null) {// 如果店铺集合中没有当前店铺则添加
					BigDecimal quantity = new BigDecimal(String.valueOf(map
							.get("quantity")));// 购买数量
					BigDecimal price = new BigDecimal(String.valueOf(map
							.get("salesPrice")));// 商品成交价
					BigDecimal minAmount = new BigDecimal(String.valueOf(map
							.get("minAmount")));
					BigDecimal totalPrice = new BigDecimal(0);
					// 总消费金额
					totalPrice = price.multiply(quantity);
					map.put("totalPrice",
							totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
					if (totalPrice.compareTo(minAmount) >= 0) {
						map.put("postage", 0);
					}
					list.add(map);
					// 保存店铺list（list为店铺中商品的集合）
					shopInfoMap.put(map.get("shopInfoId"), list);
				} else {// 如果在shopInfoMap中有当前店铺刚取出店铺
					list = (List<Map>) shopInfoMap.get(map.get("shopInfoId"));
					// 最底消费金额
					BigDecimal minAmount = new BigDecimal(String.valueOf(map
							.get("minAmount")));
					// 满足则包邮
					minAmount = minAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					// 商品成交价
					BigDecimal price = new BigDecimal(String.valueOf(map
							.get("salesPrice")));
					// 购买数量
					BigDecimal quantity = new BigDecimal(String.valueOf(map
							.get("quantity")));
					Map oneMap = list.get(0);
					BigDecimal totalPrice = (BigDecimal) oneMap
							.get("totalPrice");
					if (Utils.objectIsNotEmpty(totalPrice)) {
						// 总消费金额
						totalPrice = totalPrice.add(price.multiply(quantity));
					} else {
						totalPrice = price.multiply(quantity);
					}
					map.put("totalPrice",
							totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
					List<Map> tempList = new ArrayList<Map>();
					for (Map m : list) {
						if (totalPrice.compareTo(minAmount) >= 0) {
							m.put("postage", 0);
						}
						m.put("totalPrice", totalPrice.setScale(2,
								BigDecimal.ROUND_HALF_UP));
						tempList.add(m);
					}
					if (totalPrice.compareTo(minAmount) >= 0) {
						map.put("postage", 0);
					}
					tempList.add(map);
					// 保存店铺list（list为店铺中商品的集合）
					shopInfoMap.put(map.get("shopInfoId"), tempList);
					// 向店铺中添加商品
					// list.add(map);
				}
			}
			// 判断是否订单中所有商品均不满足交易条件
			if (isAllTransaction == cartMap.size()) {
				isAllTransactionFlag = "all";
			} else if (isAllTransaction < cartMap.size()
					&& isAllTransaction > 0) {
				isAllTransactionFlag = "oneMore";
			}
			/** 运费总价格 */
			BigDecimal freightPrice = new BigDecimal(0);
			/** 商品总价格 */
			BigDecimal totalPrice = new BigDecimal(0);
			/** 市场价总价格 */
			// BigDecimal totalMarketPrice = new BigDecimal(0);
			/** 支付总价格 */
			BigDecimal finalAmount = new BigDecimal(0);

			BigDecimal num = new BigDecimal(0);
			if (Utils.objectIsNotEmpty(cartMap)) {
				for (Map map : cartMap) {// 迭代出每一件购买的商品
					if (Integer.parseInt(String.valueOf(map.get("flag"))) == 0) {// 判断是否是可交易商品
						BigDecimal marketPrice = new BigDecimal(
								String.valueOf(map.get("marketPrice")));// 市场价
						// 计算当前商品的会员价格
						// getCustomerProductPrice(customer,marketPrice,new
						// BigDecimal(String.valueOf(map.get("salesPrice"))));
						// BigDecimal price=buyPrice;//商品成交价
						BigDecimal price = new BigDecimal(String.valueOf(map
								.get("salesPrice")));// 商品成交价
						map.put("buyPrice", buyPrice);
						map.put("buyPriceCustomer", buyPriceCustomer);
						if (productNum != null) {
							num = new BigDecimal(productNum);
							map.put("productNum", productNum);
							productId = Integer.parseInt(String.valueOf(map
									.get("productId")));
							shopInfoId = String.valueOf(map.get("shopInfoId"));
						} else {
							num = new BigDecimal(String.valueOf(map
									.get("quantity")));
							map.put("productNum", num);
						}
						// 取得折扣比例
						BigDecimal bdDiscount = (BigDecimal) map
								.get("discount");
						// 商品销售价*折扣比例*数量=消费总金额
						BigDecimal temTotalPrice = new BigDecimal(0);
						if (Utils.objectIsNotEmpty(bdDiscount)) {
							BigDecimal temp = new BigDecimal(0.1);
							// 消费总金额=商品销售价*折扣比例*数量
							temTotalPrice = price.multiply(num)
									.multiply(bdDiscount).multiply(temp);
							map.put("subTotal", temTotalPrice);
						} else {
							temTotalPrice = price.multiply(num);
							map.put("subTotal", temTotalPrice);
						}
						// 最终支付金额（折扣已打）商品销售价*折扣比例*数量
						finalAmount = finalAmount.add(temTotalPrice);
						// 商品总金额（折扣未计算）商品销售价*数量
						totalPrice = totalPrice.add(marketPrice.multiply(num));

						// totalMarketPrice =
						// totalMarketPrice.add(marketPrice.multiply(num));
						// if("2".equals(String.valueOf(map.get("isChargeFreight")))){
						// if(null!=map.get("freightPrice")){
						// freightPrice=freightPrice.add(new
						// BigDecimal(String.valueOf(map.get("freightPrice"))));
						// }
						// }
						buyPrice = new BigDecimal(0);
						buyPriceCustomer = "0";
					}
				}
			}
			// 计算运费
			Iterator<Object> keys = shopInfoMap.keySet().iterator();
			while (keys.hasNext()) {
				Integer key = (Integer) keys.next();
				List<Map> listMap = (List<Map>) shopInfoMap.get(key);
				Map m = listMap.get(0);
				freightPrice = freightPrice.add(new BigDecimal(String.valueOf(m
						.get("postage"))));
			}

			productNum = num.intValue();
			discount = totalPrice.subtract(finalAmount)
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();// (减法)折扣
			request.setAttribute("discount", discount);
			finalAmount = finalAmount.add(freightPrice.setScale(2,
					BigDecimal.ROUND_HALF_UP));// +运费
			orders.setFinalAmount(finalAmount.setScale(2,
					BigDecimal.ROUND_HALF_UP));// 最终支付金额
			orders.setFreight(freightPrice
					.setScale(2, BigDecimal.ROUND_HALF_UP));// 运费
			orders.setTotalAmount(totalPrice.setScale(2,
					BigDecimal.ROUND_HALF_UP));// 商品总金额
			request.setAttribute("orders", orders);
			// 虚拟金币余额
			MallCoin vc = (MallCoin) mallCoinService
					.getObjectByParams("where o.customerId="
							+ customer.getCustomerId()
							+ " order by o.tradeTime desc,o.mallCoinId desc");
			if (vc != null && vc.getRemainingNumber() != null) {
				coinTotal = vc.getRemainingNumber();
			} else {
				coinTotal = new BigDecimal(0);
			}
			// 收货人地址
			custAddress = (CustomerAcceptAddress) customerAcceptAddressService
					.getObjectByParams(" where o.customerId="
							+ customer.getCustomerId()
							+ " and o.isSendAddress=1");

			session.setAttribute("custAddress", custAddress);
			// 查询用户可用优惠卷
			if (customer != null) {
				String hqlmc = "select o.customerDiscountCouponID as customerDiscountCouponID,"
						+ " o.discountCouponID as discountCouponID,"
						+ " o.customerId as customerId,"
						+ " o.discountCouponCode as discountCouponCode,"
						+ " o.discountCouponName as discountCouponName,"
						+ " o.discountCouponAmount as discountCouponAmount,"
						+ " o.discountCouponLowAmount as discountCouponLowAmount,"
						+ " o.beginTime as beginTime,"
						+ " o.expirationTime as expirationTime,"
						+ " o.useStatus as useStatus,"
						+ " o.status as status,"
						+ " o.createTime as createTime,"
						+ " o.updateTime as updateTime,"
						+ " o.discountImage as discountImage from Customerdiscountcoupon o ,DiscountCoupon d"
						+ " where o.discountCouponID = d.discountCouponID and d.useStatus = 1 and o.useStatus=0 and o.status=1 and o.beginTime < '"
						+ sysDatetime
						+ "' and o.customerId ="
						+ customer.getCustomerId();
				String paramsmc = " where 1=1 and o.customerId ="
						+ customer.getCustomerId();
				int totalRecordCount = customerdiscountcouponService
						.getCount(paramsmc);
				pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
				ordersListMap = customerdiscountcouponService.findListMapPage(
						hqlmc + " order by o.createTime desc", pageHelper);

				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				for (Map<String, Object> m : ordersListMap) {// 格式化创建时间
					if (m.get("beginTime") != null) {
						Date date = (Date) m.get("beginTime");
						String format = fm.format(date);
						m.put("beginTime", format);
					}
					if (m.get("expirationTime") != null) {
						Date date = (Date) m.get("expirationTime");
						String format = fm.format(date);
						m.put("expirationTime", format);
					}
				}
			}
			request.setAttribute("shopInfoMap", shopInfoMap);
			session.setAttribute("proMap", shopInfoMap);
		}
		return SUCCESS;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gotoPhoneShoppingCartOrder() {
		Customer customer = (Customer) request.getSession().getAttribute(
				"customer");
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式大小写有区别
		String sysDatetime = fmt.format(rightNow.getTime());
		String hql = "";
		cartMap = new ArrayList<Map>();
		if (productId != null) {
			// 直接购买
			hql = "select d.brandId as brandId, c.marketPrice as marketPrice,b.shopInfoId as shopInfoId,c.productId as productId ,c.productFullName as productFullName,"
					+ " c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice ,"
					+ " b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl from ShopInfo b ,ProductInfo c,Brand d"
					+ " where b.shopInfoId=c.shopInfoId and d.brandId=c.brandId and c.productId ="
					+ productId + " and  b.shopInfoId=" + shopInfoId;
			cartMap = shoppingCartOrderService.findListMapByHql(hql);
		} else if (StringUtils.isNotEmpty(shopCartIds)) {
			// 购物车信息
			hql = "select d.brandId as brandId, a.quantity as quantity,a.discount as discount, c.marketPrice as marketPrice, a.shopCartId as shopCartId, a.stockUpDate as stockUpDate, b.shopInfoId as shopInfoId,b.minAmount as minAmount,b.postage as postage,c.productId as productId ,"
					+ " c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice, c.storeNumber as storeNumber, c.productFullName as productFullName,"
					+ " b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale,c.stockUpDate as stockUpDate,c.sku as sku,"
					+ " b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl  from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d"
					+ " where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId in("
					+ shopCartIds
					+ ") and a.customerId="
					+ customer.getCustomerId();
			cartMap = shoppingCartOrderService.findListMapByHql(hql);
		}
		if (cartMap != null && cartMap.size() > 0) {
			shopInfoMap = new HashMap<Object, Object>();// 存放店铺集合
			List<Map> list = new ArrayList<Map>();// 一个list表示一个店铺
			Integer isAllTransaction = 0;// 记录订单中商品不能交易个数
			for (Map<String, Object> map : cartMap) {// 迭代出每一件购买的商品
				Integer isTransaction = 0;
				if (Integer.parseInt(String.valueOf(map.get("shopIsPass"))) != 3) {
					isTransaction = 1;
				}
				if (Integer.parseInt(String.valueOf(map.get("isClose"))) != 0) {
					isTransaction = 1;
				}
				if (Integer.parseInt(String.valueOf(map.get("isPass"))) != 1) {
					isTransaction = 1;
				}
				if (Integer.parseInt(String.valueOf(map.get("isPutSale"))) != 2) {
					isTransaction = 1;
				}
				if (isTransaction == 1) {
					isAllTransaction++;
				}
				map.put("flag", isTransaction);
				list = new ArrayList<Map>();
				if (shopInfoMap.get(map.get("shopInfoId")) == null) {// 如果店铺集合中没有当前店铺则添加
					BigDecimal quantity = new BigDecimal(String.valueOf(map
							.get("quantity")));// 购买数量
					BigDecimal price = new BigDecimal(String.valueOf(map
							.get("salesPrice")));// 商品成交价
					BigDecimal minAmount = new BigDecimal(String.valueOf(map
							.get("minAmount")));
					BigDecimal totalPrice = new BigDecimal(0);
					// 总消费金额
					totalPrice = price.multiply(quantity);
					map.put("totalPrice",
							totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
					if (totalPrice.compareTo(minAmount) >= 0) {
						map.put("postage", 0);
					}
					list.add(map);
					// 保存店铺list（list为店铺中商品的集合）
					shopInfoMap.put(map.get("shopInfoId"), list);
				} else {// 如果在shopInfoMap中有当前店铺刚取出店铺
					list = (List<Map>) shopInfoMap.get(map.get("shopInfoId"));
					// 最底消费金额
					BigDecimal minAmount = new BigDecimal(String.valueOf(map
							.get("minAmount")));
					// 满足则包邮
					minAmount = minAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					// 商品成交价
					BigDecimal price = new BigDecimal(String.valueOf(map
							.get("salesPrice")));
					// 购买数量
					BigDecimal quantity = new BigDecimal(String.valueOf(map
							.get("quantity")));
					Map oneMap = list.get(0);
					BigDecimal totalPrice = (BigDecimal) oneMap
							.get("totalPrice");
					if (Utils.objectIsNotEmpty(totalPrice)) {
						// 总消费金额
						totalPrice = totalPrice.add(price.multiply(quantity));
					} else {
						totalPrice = price.multiply(quantity);
					}
					map.put("totalPrice",
							totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
					List<Map> tempList = new ArrayList<Map>();
					for (Map m : list) {
						if (totalPrice.compareTo(minAmount) >= 0) {
							m.put("postage", 0);
						}
						m.put("totalPrice", totalPrice.setScale(2,
								BigDecimal.ROUND_HALF_UP));
						tempList.add(m);
					}
					if (totalPrice.compareTo(minAmount) >= 0) {
						map.put("postage", 0);
					}
					tempList.add(map);
					// 保存店铺list（list为店铺中商品的集合）
					shopInfoMap.put(map.get("shopInfoId"), tempList);
					// 向店铺中添加商品
					// list.add(map);
				}
			}
			// 判断是否订单中所有商品均不满足交易条件
			if (isAllTransaction == cartMap.size()) {
				isAllTransactionFlag = "all";
			} else if (isAllTransaction < cartMap.size()
					&& isAllTransaction > 0) {
				isAllTransactionFlag = "oneMore";
			}
			/** 运费总价格 */
			BigDecimal freightPrice = new BigDecimal(0);
			/** 商品总价格 */
			BigDecimal totalPrice = new BigDecimal(0);
			/** 市场价总价格 */
			// BigDecimal totalMarketPrice = new BigDecimal(0);
			/** 支付总价格 */
			BigDecimal finalAmount = new BigDecimal(0);

			BigDecimal num = new BigDecimal(0);
			if (Utils.objectIsNotEmpty(cartMap)) {
				for (Map map : cartMap) {// 迭代出每一件购买的商品
					if (Integer.parseInt(String.valueOf(map.get("flag"))) == 0) {// 判断是否是可交易商品
						BigDecimal marketPrice = new BigDecimal(
								String.valueOf(map.get("marketPrice")));// 市场价
						// 计算当前商品的会员价格
						// getCustomerProductPrice(customer,marketPrice,new
						// BigDecimal(String.valueOf(map.get("salesPrice"))));
						// BigDecimal price=buyPrice;//商品成交价
						BigDecimal price = new BigDecimal(String.valueOf(map
								.get("salesPrice")));// 商品成交价
						map.put("buyPrice", buyPrice);
						map.put("buyPriceCustomer", buyPriceCustomer);
						if (productNum != null) {
							num = new BigDecimal(productNum);
							map.put("productNum", productNum);
							productId = Integer.parseInt(String.valueOf(map
									.get("productId")));
							shopInfoId = String.valueOf(map.get("shopInfoId"));
						} else {
							num = new BigDecimal(String.valueOf(map
									.get("quantity")));
							map.put("productNum", num);
						}
						// 取得折扣比例
						BigDecimal bdDiscount = (BigDecimal) map
								.get("discount");
						// 商品销售价*折扣比例*数量=消费总金额
						BigDecimal temTotalPrice = new BigDecimal(0);
						if (Utils.objectIsNotEmpty(bdDiscount)) {
							BigDecimal temp = new BigDecimal(0.1);
							// 消费总金额=商品销售价*折扣比例*数量
							temTotalPrice = price.multiply(num)
									.multiply(bdDiscount).multiply(temp);
							map.put("subTotal", temTotalPrice);
						} else {
							temTotalPrice = price.multiply(num);
							map.put("subTotal", temTotalPrice);
						}
						// 最终支付金额（折扣已打）商品销售价*折扣比例*数量
						finalAmount = finalAmount.add(temTotalPrice);
						// 商品总金额（折扣未计算）商品销售价*数量
						totalPrice = totalPrice.add(marketPrice.multiply(num));

						// totalMarketPrice =
						// totalMarketPrice.add(marketPrice.multiply(num));
						// if("2".equals(String.valueOf(map.get("isChargeFreight")))){
						// if(null!=map.get("freightPrice")){
						// freightPrice=freightPrice.add(new
						// BigDecimal(String.valueOf(map.get("freightPrice"))));
						// }
						// }
						buyPrice = new BigDecimal(0);
						buyPriceCustomer = "0";
					}
				}
			}
			// 计算运费
			Iterator<Object> keys = shopInfoMap.keySet().iterator();
			while (keys.hasNext()) {
				Integer key = (Integer) keys.next();
				List<Map> listMap = (List<Map>) shopInfoMap.get(key);
				Map m = listMap.get(0);
				freightPrice = freightPrice.add(new BigDecimal(String.valueOf(m
						.get("postage"))));
			}

			productNum = num.intValue();
			discount = totalPrice.subtract(finalAmount)
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();// (减法)折扣
			finalAmount = finalAmount.add(freightPrice.setScale(2,
					BigDecimal.ROUND_HALF_UP));// +运费
			orders.setFinalAmount(finalAmount.setScale(2,
					BigDecimal.ROUND_HALF_UP));// 最终支付金额
			orders.setFreight(freightPrice
					.setScale(2, BigDecimal.ROUND_HALF_UP));// 运费
			orders.setTotalAmount(totalPrice.setScale(2,
					BigDecimal.ROUND_HALF_UP));// 商品总金额
			// 虚拟金币余额
			MallCoin vc = (MallCoin) mallCoinService
					.getObjectByParams("where o.customerId="
							+ customer.getCustomerId()
							+ " order by o.tradeTime desc,o.mallCoinId desc");
			if (vc != null && vc.getRemainingNumber() != null) {
				coinTotal = vc.getRemainingNumber();
			} else {
				coinTotal = new BigDecimal(0);
			}
			// 收货人地址
			custAddress = (CustomerAcceptAddress) customerAcceptAddressService
					.getObjectByParams(" where o.customerId="
							+ customer.getCustomerId()
							+ " and o.isSendAddress=1");

			session.setAttribute("custAddress", custAddress);
			// 查询用户可用优惠卷
			if (customer != null) {
				String hqlmc = "select o.customerDiscountCouponID as customerDiscountCouponID,"
						+ " o.discountCouponID as discountCouponID,"
						+ " o.customerId as customerId,"
						+ " o.discountCouponCode as discountCouponCode,"
						+ " o.discountCouponName as discountCouponName,"
						+ " o.discountCouponAmount as discountCouponAmount,"
						+ " o.discountCouponLowAmount as discountCouponLowAmount,"
						+ " o.beginTime as beginTime,"
						+ " o.expirationTime as expirationTime,"
						+ " o.useStatus as useStatus,"
						+ " o.status as status,"
						+ " o.createTime as createTime,"
						+ " o.updateTime as updateTime,"
						+ " o.discountImage as discountImage from Customerdiscountcoupon o ,DiscountCoupon d"
						+ " where o.discountCouponID = d.discountCouponID and d.useStatus = 1 and o.useStatus=0 and o.status=1 and o.beginTime < '"
						+ sysDatetime
						+ "' and o.customerId ="
						+ customer.getCustomerId();
				String paramsmc = " where 1=1 and o.customerId ="
						+ customer.getCustomerId();
				int totalRecordCount = customerdiscountcouponService
						.getCount(paramsmc);
				pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
				ordersListMap = customerdiscountcouponService.findListMapPage(
						hqlmc + " order by o.createTime desc", pageHelper);

				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				for (Map<String, Object> m : ordersListMap) {// 格式化创建时间
					if (m.get("beginTime") != null) {
						Date date = (Date) m.get("beginTime");
						String format = fm.format(date);
						m.put("beginTime", format);
					}
					if (m.get("expirationTime") != null) {
						Date date = (Date) m.get("expirationTime");
						String format = fm.format(date);
						m.put("expirationTime", format);
					}
				}
			}
		}
		return SUCCESS;
	}

	public String delCart() {
		shoppingCartService.deleteObjectByParams(" where customerId=" + customerId
				+ " and productId=" + productId);
		return SUCCESS;
	}

	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setJsonCart(String jsonCart) {
		this.jsonCart = jsonCart;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public void setMemberShipService(IMemberShipService memberShipService) {
		this.memberShipService = memberShipService;
	}

	public void setShoppingCartOrderService(
			IShoppingCartOrderService shoppingCartOrderService) {
		this.shoppingCartOrderService = shoppingCartOrderService;
	}

	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}

	public void setMallCoinService(IMallCoinService mallCoinService) {
		this.mallCoinService = mallCoinService;
	}

	public void setShopCartIds(String shopCartIds) {
		this.shopCartIds = shopCartIds;
	}

	public void setCartMap(List<Map> cartMap) {
		this.cartMap = cartMap;
	}

	public void setShopInfoMap(Map<Object, Object> shopInfoMap) {
		this.shopInfoMap = shopInfoMap;
	}

	public void setIsAllTransactionFlag(String isAllTransactionFlag) {
		this.isAllTransactionFlag = isAllTransactionFlag;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public void setCoinTotal(BigDecimal coinTotal) {
		this.coinTotal = coinTotal;
	}

	public void setBuyPriceCustomer(String buyPriceCustomer) {
		this.buyPriceCustomer = buyPriceCustomer;
	}

	public void setTotalOrdersNo(String totalOrdersNo) {
		this.totalOrdersNo = totalOrdersNo;
	}

	public void setOrdersNoAll(String ordersNoAll) {
		this.ordersNoAll = ordersNoAll;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setShopInfoCompanyName(String shopInfoCompanyName) {
		this.shopInfoCompanyName = shopInfoCompanyName;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setCustomerAcceptAddressList(
			List<CustomerAcceptAddress> customerAcceptAddressList) {
		this.customerAcceptAddressList = customerAcceptAddressList;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}

	public void setAddressisok(String addressisok) {
		this.addressisok = addressisok;
	}

	public void setCustomerDiscountCouponID(String customerDiscountCouponID) {
		this.customerDiscountCouponID = customerDiscountCouponID;
	}

	public void setCustomerdiscountcouponService(
			ICustomerdiscountcouponService customerdiscountcouponService) {
		this.customerdiscountcouponService = customerdiscountcouponService;
	}

	public void setOrdersListMap(List<Map> ordersListMap) {
		this.ordersListMap = ordersListMap;
	}

	public void setToPayList(List<Map> toPayList) {
		this.toPayList = toPayList;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public void setCountrys(List<Map> countrys) {
		this.countrys = countrys;
	}

	public void setPayMap(Map<String, Object> payMap) {
		this.payMap = payMap;
	}

	public void setCustAddress(CustomerAcceptAddress custAddress) {
		this.custAddress = custAddress;
	}

	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

}
