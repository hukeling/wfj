package shop.front.shoppingOnLine.action;
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

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import rating.buyersRecord.service.IBuyersRecordService;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.MallCoin;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.customer.service.IMallCoinService;
import shop.customer.service.IVirtualCoinService;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartOrderService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.lineofcreditItem.service.ILineofcreditItemService;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.newAlipay.util.AlipayNotify;
import util.newAlipay.util.AlipaySubmit;
import util.other.Escape;
import util.other.Utils;
import util.other.WebUtil;
import util.quickBuck.Pkipair;
import basic.service.IAreaService;
import discountcoupon.service.ICustomerdiscountcouponService;
/**
 * ShoppingCartOrderAction - 商品购物车
 */
public class ShoppingCartOrderAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**shoppingCartOrderService**/
	private IShoppingCartOrderService shoppingCartOrderService; 
	/**客户收货地址Service**/
	private ICustomerAcceptAddressService customerAcceptAddressService;
	/**订单日志**/
	@SuppressWarnings("unused")
	private IOrdersOPLogService ordersOPLogService ;
	/**虚拟金币service**/
	private IVirtualCoinService virtualCoinService; 
	private IMallCoinService mallCoinService; 
	/**购物车Service**/
	private IShoppingCartService shoppingCartService;
	private ShoppingCart shoppingCart;
	/**订单信息**/
	@SuppressWarnings("rawtypes")
	private List<Map> cartMap ;
	/**shoppingIds**/
	private String ids;
	/**收货地址**/
	private CustomerAcceptAddress custAddress;
	/**购物车Ids**/
	private String shopCartIds;
	/**提交订单json格式存放**/
	private String orderJson;
	/**折扣钱数**/
	private String discount;
	/**订单号**/
	private String ordersNo;
	private IOrdersService ordersService;
	/**用户地址List**/
	private List<CustomerAcceptAddress> customerAcceptAddressList;
	/**订单**/
	private Orders orders = new Orders();
	/**商品Id直接购买，不经过购物车**/
	private Integer productId;
	/**店铺Id直接购买，不经过购物车**/
	private String shopInfoId;
	/**去支付时的信息 **/
	@SuppressWarnings({ "unused","rawtypes" })
	private List<Map> toPayList;	
	/**直接购买商品的数量**/
	private Integer productNum;
	private Double salesPrice;
	/**获取国家信息**/
	@SuppressWarnings("rawtypes")
	private List<Map> countrys; //国家
	/**支付宝参数map**/
	private Map<String,Object> payMap = new HashMap<String,Object>();
	/**区域service**/
	private IAreaService areaService;
	/**一级地址**/
	@SuppressWarnings("rawtypes")
	private List<Map> firstAreaList;
	/**安全认证pojo**/
	private SafetyCertificate safetyCertificate;
	/**按照不同的店铺拆分显示**/
	private Map<Object,Object> shopInfoMap;
	/**用户虚拟金币余额**/
	private BigDecimal coinTotal;
	/**记录是否所有商品均满足交易**/
	private String isAllTransactionFlag;
	/**是否开发票**/
	private Integer isInvoice;
	/**会员可用授信额度值**/
	private String lineOfCredit;
	/**会员总授信额度值**/
	private String lineOfCreditTal;
	/**会员授信额度使用明细表service**/
	private ILineofcreditItemService lineofcreditItemService;
	/**会员等级升迁记录表service**/
	private IBuyersRecordService buyersRecordService;
	/**输入的额度数量**/
	private String number;
	/**商品成交价格**/
	private BigDecimal buyPrice;
	/**buyPrice是否为会员价**/
	private String buyPriceCustomer="0";
	/**总订单号*/
	private String totalOrdersNo;
	/**子订单号字符串组*/
	private String ordersNoAll;
	/**支付宝显示的商品描述**/
	private String subject ="您从SHOPJSP购买商品";
	/**增值税发票抬头*/
	private String shopInfoCompanyName;
	
	private String addressisok;//配送方式
	private String customerDiscountCouponID;//优惠券id
	private ICustomerdiscountcouponService customerdiscountcouponService;//用户优惠卷service
	private List<Map> ordersListMap ;//优惠券集合
	/**
	 * 
	 */
	private IShopInfoService shopInfoService;
	/**************************************end****************************************************************************/
	/**
	 * goto订单
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gotoShoppingCartOrder(){
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		Calendar rightNow = Calendar.getInstance(); 
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式大小写有区别
		String sysDatetime = fmt.format(rightNow.getTime());
		String hql="";
		cartMap = new ArrayList<Map>();
		if(productId!=null){
			//直接购买
	    	hql="select d.brandId as brandId, c.marketPrice as marketPrice,b.shopInfoId as shopInfoId,c.productId as productId ,c.productFullName as productFullName," +
			" c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice ," +
			" b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl from ShopInfo b ,ProductInfo c,Brand d" +
			" where b.shopInfoId=c.shopInfoId and d.brandId=c.brandId and c.productId ="+productId +" and  b.shopInfoId="+shopInfoId;
	    	cartMap=shoppingCartOrderService.findListMapByHql(hql);
		}else if(StringUtils.isNotEmpty(shopCartIds)){
			//购物车信息
			hql="select d.brandId as brandId, a.quantity as quantity,a.discount as discount, c.marketPrice as marketPrice, a.shopCartId as shopCartId, a.stockUpDate as stockUpDate, b.shopInfoId as shopInfoId,b.minAmount as minAmount,b.postage as postage,c.productId as productId ," +
				" c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice, c.storeNumber as storeNumber, c.productFullName as productFullName," +
				" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale,c.stockUpDate as stockUpDate,c.sku as sku,"+
				" b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl  from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d" +
				" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId in("+shopCartIds +") and a.customerId="+customer.getCustomerId();
			cartMap = shoppingCartOrderService.findListMapByHql(hql);
		}
		if(cartMap!=null&&cartMap.size()>0){
			shopInfoMap = new HashMap<Object,Object>();//存放店铺集合
	    	List<Map> list=new ArrayList<Map>();//一个list表示一个店铺
	    	Integer isAllTransaction=0;//记录订单中商品不能交易个数
			for(Map<String,Object> map:cartMap){//迭代出每一件购买的商品
				Integer isTransaction=0;
				if(Integer.parseInt(String.valueOf(map.get("shopIsPass")))!=3){
					isTransaction=1;
				}
				if(Integer.parseInt(String.valueOf(map.get("isClose")))!=0){
					isTransaction=1;
				}
				if(Integer.parseInt(String.valueOf(map.get("isPass")))!=1){
					isTransaction=1;
				}
				if(Integer.parseInt(String.valueOf(map.get("isPutSale")))!=2){
					isTransaction=1;
				}
				if(isTransaction==1){
					isAllTransaction++;
				}
				map.put("flag",isTransaction);
				list=new ArrayList<Map>();
				if(shopInfoMap.get(map.get("shopInfoId"))==null){//如果店铺集合中没有当前店铺则添加
					BigDecimal quantity=new BigDecimal(String.valueOf(map.get("quantity")));//购买数量
					BigDecimal price=new BigDecimal(String.valueOf(map.get("salesPrice")));//商品成交价
					BigDecimal minAmount=new BigDecimal(String.valueOf(map.get("minAmount")));
					BigDecimal totalPrice = new BigDecimal(0);
					//总消费金额
					totalPrice = price.multiply(quantity);
					map.put("totalPrice",totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
					if(totalPrice.compareTo(minAmount)>=0){
						map.put("postage",0);
					}
					list.add(map);
					//保存店铺list（list为店铺中商品的集合）
					shopInfoMap.put(map.get("shopInfoId"), list);
				}else{//如果在shopInfoMap中有当前店铺刚取出店铺
					list= (List<Map>) shopInfoMap.get(map.get("shopInfoId"));
					//最底消费金额
					BigDecimal minAmount=new BigDecimal(String.valueOf(map.get("minAmount")));
					//满足则包邮
					minAmount = minAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
					//商品成交价
					BigDecimal price=new BigDecimal(String.valueOf(map.get("salesPrice")));
					//购买数量
					BigDecimal quantity=new BigDecimal(String.valueOf(map.get("quantity")));
					Map oneMap = list.get(0);
					BigDecimal totalPrice = (BigDecimal) oneMap.get("totalPrice");
					if(Utils.objectIsNotEmpty(totalPrice)){
						//总消费金额
						totalPrice = totalPrice.add(price.multiply(quantity));
					}else{
						totalPrice = price.multiply(quantity);
					}
					map.put("totalPrice",totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
						List<Map> tempList = new ArrayList<Map>();
						for(Map m :list){
							if(totalPrice.compareTo(minAmount)>=0){
								m.put("postage",0);
							}
							m.put("totalPrice",totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
							tempList.add(m);
						}
						if(totalPrice.compareTo(minAmount)>=0){
							map.put("postage",0);
						}
						tempList.add(map);
						//保存店铺list（list为店铺中商品的集合）
						shopInfoMap.put(map.get("shopInfoId"), tempList);
					//向店铺中添加商品
					//list.add(map);
				}
			}
			//判断是否订单中所有商品均不满足交易条件
			if(isAllTransaction==cartMap.size()){
				isAllTransactionFlag ="all";
			}else if(isAllTransaction<cartMap.size()&&isAllTransaction>0){
				isAllTransactionFlag ="oneMore";
			}
			/**运费总价格*/
			BigDecimal freightPrice = new BigDecimal(0);
			/**商品总价格*/
	    	BigDecimal totalPrice=new BigDecimal(0);
	    	/**市场价总价格*/
	    	//BigDecimal totalMarketPrice = new BigDecimal(0);
	    	/**支付总价格*/
	    	BigDecimal finalAmount = new BigDecimal(0);
	    	
	    	BigDecimal num=new BigDecimal(0);
	    	if(Utils.objectIsNotEmpty(cartMap)){
	    		for(Map map:cartMap){//迭代出每一件购买的商品
	    			if(Integer.parseInt(String.valueOf(map.get("flag")))==0){//判断是否是可交易商品
	    				BigDecimal marketPrice=new BigDecimal(String.valueOf(map.get("marketPrice")));//市场价
	    				//计算当前商品的会员价格
		    			//getCustomerProductPrice(customer,marketPrice,new BigDecimal(String.valueOf(map.get("salesPrice"))));
	    				//BigDecimal price=buyPrice;//商品成交价
	    				BigDecimal price=new BigDecimal(String.valueOf(map.get("salesPrice")));//商品成交价
	    				map.put("buyPrice",buyPrice);
	    				map.put("buyPriceCustomer",buyPriceCustomer);
	    				if(productNum!=null){
	    					num=new BigDecimal(productNum);
	    					map.put("productNum", productNum);
	    					productId = Integer.parseInt(String.valueOf(map.get("productId")));
	    					shopInfoId = String.valueOf(map.get("shopInfoId"));
	    				}else{
	    					num=new BigDecimal(String.valueOf(map.get("quantity")));
	    					map.put("productNum", num);
	    				}
	    				//取得折扣比例
	    				BigDecimal bdDiscount = (BigDecimal) map.get("discount");
	    				//商品销售价*折扣比例*数量=消费总金额
	    				BigDecimal temTotalPrice = new BigDecimal(0);
	    				if(Utils.objectIsNotEmpty(bdDiscount)){
	    					BigDecimal temp = new BigDecimal(0.1);
	    					//消费总金额=商品销售价*折扣比例*数量
	    					temTotalPrice = price.multiply(num).multiply(bdDiscount).multiply(temp);
	    					map.put("subTotal",temTotalPrice);
	    				}else{
	    					temTotalPrice = price.multiply(num);
	    					map.put("subTotal",temTotalPrice);
	    				}
	    				//最终支付金额（折扣已打）商品销售价*折扣比例*数量
	    				finalAmount=finalAmount.add(temTotalPrice);
	    				//商品总金额（折扣未计算）商品销售价*数量
	    				totalPrice=totalPrice.add(marketPrice.multiply(num));
	    				
	    				//totalMarketPrice = totalMarketPrice.add(marketPrice.multiply(num));
//	    				if("2".equals(String.valueOf(map.get("isChargeFreight")))){
//	    					if(null!=map.get("freightPrice")){
//	    						freightPrice=freightPrice.add(new BigDecimal(String.valueOf(map.get("freightPrice"))));
//	    					}
//	    				}
	    				buyPrice=new BigDecimal(0);
	    				buyPriceCustomer="0";
	    			}
	    		}
	    	}
	    	//计算运费
	    	Iterator<Object> keys = shopInfoMap.keySet().iterator();
	    	while(keys.hasNext()) {
	    	   Integer key = (Integer) keys.next();
	    	   List<Map> listMap=(List<Map>) shopInfoMap.get(key);
	    	   Map m =  listMap.get(0);
	    	   freightPrice = freightPrice.add(new BigDecimal(String.valueOf(m.get("postage"))));
	    	} 
	    	
	    	productNum=num.intValue();
	    	discount = totalPrice.subtract(finalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString();//(减法)折扣
	    	finalAmount=finalAmount.add(freightPrice.setScale(2, BigDecimal.ROUND_HALF_UP));//+运费
	    	orders.setFinalAmount(finalAmount.setScale(2, BigDecimal.ROUND_HALF_UP));//最终支付金额
	    	orders.setFreight(freightPrice.setScale(2, BigDecimal.ROUND_HALF_UP));//运费
	    	orders.setTotalAmount(totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));//商品总金额
	    	//虚拟金币余额
	    	MallCoin vc = (MallCoin) mallCoinService.getObjectByParams("where o.customerId="+customer.getCustomerId()+" order by o.tradeTime desc,o.mallCoinId desc");
	    	if(vc!=null&&vc.getRemainingNumber()!=null){
	    		coinTotal = vc.getRemainingNumber();
	    	}else{
	    		coinTotal = new BigDecimal(0);
	    	}
	    	//收货人地址
	    	custAddress= (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams(" where o.customerId="+customer.getCustomerId()+" and o.isSendAddress=1");
	    	
	    	//查询用户可用优惠卷
	    	if(customer!=null){
				String hqlmc="select o.customerDiscountCouponID as customerDiscountCouponID,"
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
						+ " where o.discountCouponID = d.discountCouponID and d.useStatus = 1 and o.useStatus=0 and o.status=1 and o.beginTime < '"+sysDatetime+"' and o.customerId =" +customer.getCustomerId();
				String paramsmc=" where 1=1 and o.customerId =" +customer.getCustomerId();
				int totalRecordCount = customerdiscountcouponService.getCount(paramsmc);
				pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
				ordersListMap = customerdiscountcouponService.findListMapPage(hqlmc+" order by o.createTime desc", pageHelper);
			
	    	
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				for(Map<String,Object> m:ordersListMap){//格式化创建时间
					if(m.get("beginTime")!=null){
						Date date = (Date) m.get("beginTime");
						String format = fm.format(date);
						m.put("beginTime", format);
					}
					if(m.get("expirationTime")!=null){
						Date date = (Date) m.get("expirationTime");
						String format = fm.format(date);
						m.put("expirationTime", format);
					}
				}
	    	}
		}
    	return SUCCESS;
    }
	
	/**
	 * 检查商品是否符合购买条件
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void checkProduct() throws IOException{
		Customer customer = (Customer) session.getAttribute("customer");
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> noProdList = new ArrayList<Integer>();
		String hql="";
		boolean isFlag = true;//单个商品是否满足交易条件记录
		Integer allFlag=0;//订单中所有商品是否满足交易条件记录
		boolean edFlag = true;//是否可以下单
		String newShopCartIds="";
		if(productId!=null){
	    	hql="select count(c.productId) from ShopInfo b ,ProductInfo c,Brand d " +
			" where b.shopInfoId=c.shopInfoId and d.brandId=c.brandId and c.productId ="+productId +" and  b.shopInfoId="+shopInfoId +""
					+ " and b.isPass=3 and b.isClose=0  and c.isPass=1 and c.isPutSale=2 ";
	    	Integer i=shoppingCartOrderService.getMultilistCount(hql);
	    	if(i==0){
	    		isFlag = false;
	    		noProdList.add(productId);
	    	}
		}else if(StringUtils.isNotEmpty(shopCartIds)){
			String [] sc = shopCartIds.split(",");
			if(sc!=null&&sc.length>0){
				for(String s:sc){
					hql="select count(c.productId) from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d " +
							" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId ="+s +" and a.customerId="+customer.getCustomerId()+""
							+ " and b.isPass=3 and b.isClose=0  and c.isPass=1 and c.isPutSale=2 ";
//					if(customer.getType()!=3){
//						hql+=" and b.shopInfoCheckSatus=2";
//					}
					Integer i=shoppingCartOrderService.getMultilistCount(hql);
					if(i==0){
						ShoppingCart shoppingCart = (ShoppingCart) shoppingCartService.getObjectByParams(" where o.shopCartId="+s+" and o.customerId="+customer.getCustomerId());
						noProdList.add(shoppingCart.getProductId());
						newShopCartIds=String.valueOf(shoppingCart.getShopCartId());
			    		isFlag = false;
			    		allFlag++;
			    	}else{
			    		if(newShopCartIds==""){
			    			newShopCartIds=s;
			    		}else{
			    			newShopCartIds=newShopCartIds+","+s;
			    		}
			    	}
				}
			}
		}
		//计算buyPrice的总价格
		String hql2="select c.salesPrice as salesPrice ,c.marketPrice as marketPrice,a.quantity as quantity,c.freightPrice as freightPrice from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d " +
				" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId in ("+newShopCartIds +") and a.customerId="+customer.getCustomerId()+""
				+ " and b.isPass=3 and b.isClose=0  and c.isPass=1 and c.isPutSale=2";
		List<Map> listMap=shoppingCartOrderService.findListMapByHql(hql2);
		if(listMap!=null&&listMap.size()>0){
			BigDecimal buyPriceTotal=new BigDecimal(0);//初始化成交总价0
			for(Map m:listMap){
				//buyPrice=new BigDecimal(0);
				//BigDecimal marketPrice= new BigDecimal(String.valueOf(m.get("marketPrice")));
				BigDecimal salesPrice= new BigDecimal(String.valueOf(m.get("salesPrice")));
				BigDecimal num= new BigDecimal(String.valueOf(m.get("quantity")));
				String freightPriceStr = String.valueOf(m.get("freightPrice"));
				BigDecimal freightPrice=new BigDecimal(0);
				if(freightPriceStr!=null&&!"".equals(freightPriceStr)&&!"null".equals(freightPriceStr)){
					freightPrice = new BigDecimal(freightPriceStr);
				}
				//计算成交价格
				//getCustomerProductPrice(customer,marketPrice,salesPrice);
				buyPriceTotal=buyPriceTotal.add(salesPrice.multiply(num).add(freightPrice));
			}
			/*if(Utils.objectIsNotEmpty(buyPriceTotal)){
				String buyPriceStr = String.valueOf(buyPriceTotal);
				//如果是普通用户不用判断授信额度
				if(!"3".equals(customer.getType().toString())){
					//判断授信额度使用情况
					String hql3 = "select o.remainingNumber as remainingNumber from LineofcreditItem o where o.customerId="+customer.getCustomerId()+" order by o.lineOfCreditId desc";
					List<Map> lm = lineofcreditItemService.findListMapByHql(hql3);
					if(lm!=null&&lm.size()>0){
						Map bs = lm.get(0);
						BigDecimal b = new BigDecimal(bs.get("remainingNumber").toString());//剩余授信额度值
						int result = b.compareTo(new BigDecimal(buyPriceStr));
						if(result<0){
							edFlag = false;
						}
					}
				}
			}*/
		}
		map.put("newShopCartIds", newShopCartIds);
		map.put("noProdList", noProdList);
		map.put("isFlag", isFlag);
		map.put("edFlag", edFlag);
		JSONObject jo = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	
	/**
	 * 检查团购商品是否符合购买条件(团购倒计时)
	 * @throws IOException 
	 */
	public void checkTuanProduct() throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> noProdList = new ArrayList<Integer>();
		String hql="";
		boolean isFlag = true;//单个商品是否满足交易条件记录
		boolean edFlag = true;//是否可以下单
		if(productId!=null){
	    	hql="select count(c.productId) from ShopInfo b ,ProductInfo c,Brand d " +
			" where b.shopInfoId=c.shopInfoId and d.brandId=c.brandId and c.productId ="+productId +" and  b.shopInfoId="+shopInfoId +""
					+ " and b.isPass=3 and b.isClose=0  and c.isPass=1 and c.isPutSale=2 ";
	    	Integer i=shoppingCartOrderService.getMultilistCount(hql);
	    	if(i==0){
	    		isFlag = false;
	    		noProdList.add(productId);
	    	}
		}
		map.put("noProdList", noProdList);
		map.put("isFlag", isFlag);
		map.put("edFlag", edFlag);
		JSONObject jo = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	
	
    /**
     * 确认订单提交到成功页面
     */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public String toSuccessOrder(){
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		//税率
		BigDecimal rates = null;
		//发票信息
		if("2".equals(orders.getInvoiceType().toString())){//普通发票
			shopInfo.setInvoiceType(orders.getInvoiceType());
			shopInfo.setInvoiceInfo(orders.getInvoiceInfo());
			shopInfo.setCompanyName(orders.getCompanyNameForInvoice());
			String strgeneralRates = (String) fileUrlConfig.get("generalRates");
			rates = new BigDecimal(strgeneralRates);
		}else if("3".equals(orders.getInvoiceType().toString())){//增值税发票
			shopInfo.setInvoiceType(orders.getInvoiceType());
			shopInfo.setTaxpayerNumber(orders.getTaxpayerNumber());
			shopInfo.setAddressForInvoice(orders.getAddressForInvoice());
			shopInfo.setPhoneForInvoice(orders.getPhoneForInvoice());
			shopInfo.setOpeningBank(orders.getOpeningBank());
			shopInfo.setBankAccountNumber(orders.getBankAccountNumber());
			//shopInfo.setCompanyName(orders.getCompanyNameForInvoice());
			if(Utils.objectIsNotEmpty(shopInfoCompanyName)){
				shopInfo.setCompanyName(shopInfoCompanyName);
			}
			String vatRates = (String) fileUrlConfig.get("vatRates");
			rates = new BigDecimal(vatRates);
		}else{
			shopInfo.setInvoiceType(1);
		}
		shopInfo.setCustomerId(customer.getCustomerId());
		shopInfo = (ShopInfo) shopInfoService.saveOrUpdateObject(shopInfo);
		session.setAttribute("shopInfo", shopInfo);
		//查询之前商品的金额
		String hql="";
		if(productId!=null){
			//直接购买
	    	hql="select c.marketPrice as marketPrice,b.shopInfoId as shopInfoId," +
			" c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice,d.price as price " +
			" from ShopInfo b ,ProductInfo c,TuanProduct d" +
			" where b.shopInfoId=c.shopInfoId and d.productId=c.productId and c.productId ="+productId +" and  b.shopInfoId="+shopInfoId;
		}else{
	    	//购物车信息
			hql="select d.brandId as brandId, a.quantity as quantity,a.discount as discount, c.marketPrice as marketPrice, a.shopCartId as shopCartId, a.stockUpDate as stockUpDate, b.shopInfoId as shopInfoId,b.minAmount as minAmount,b.postage as postage,c.productId as productId ," +
			" c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice, c.storeNumber as storeNumber, c.productFullName as productFullName," +
			" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale,c.stockUpDate as stockUpDate,c.sku as sku,"+
			" b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl  from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d" +
			" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId in("+shopCartIds +") and a.customerId="+customer.getCustomerId();
		}
    	cartMap=shoppingCartOrderService.findListMapByHql(hql);
    	BigDecimal totalPrice=new BigDecimal(0);//商品总价格
    	BigDecimal freightPrice = new BigDecimal(0);//运费总价格
    	BigDecimal finalAmount = new BigDecimal(0);//支付总价格
    	BigDecimal totalMarketPrice = new BigDecimal(0);//市场价总价格
    	BigDecimal num=new BigDecimal(0);//商品数量
    	shopInfoMap = new HashMap<Object,Object>();//存放店铺集合
    	List<Map> list=new ArrayList<Map>();//一个list表示一个店铺
		for(Map map:cartMap){
			//buyPrice=new BigDecimal(0);
			BigDecimal marketPrice= new BigDecimal(String.valueOf(map.get("marketPrice")));//市场价
			BigDecimal salesPrice= new BigDecimal(String.valueOf(map.get("salesPrice")));//销售价
			//getCustomerProductPrice(customer,marketPrice,salesPrice);
    		//BigDecimal price=buyPrice;//成交价
    		if(StringUtils.isEmpty(shopCartIds) && productNum!=null){
    			num=new BigDecimal(productNum);
    		}else{
    			num=new BigDecimal(String.valueOf(map.get("quantity")));
    		}
    		map.put("productNum", num);
    		//map.put("subTotal",price.multiply(num));
    		//totalPrice=totalPrice.add(price.multiply(num));
//    		map.put("subTotal",salesPrice.multiply(num));
//    		totalPrice=totalPrice.add(salesPrice.multiply(num));
//    		totalMarketPrice = totalMarketPrice.add(marketPrice.multiply(num));
    		list=new ArrayList<Map>();
    		if(shopInfoMap.get(map.get("shopInfoId"))==null){//如果店铺集合中没有当前店铺则添加
				BigDecimal quantity=new BigDecimal(String.valueOf(map.get("quantity")));//购买数量
				BigDecimal price=new BigDecimal(String.valueOf(map.get("salesPrice")));//商品成交价
				BigDecimal minAmount=new BigDecimal(String.valueOf(map.get("minAmount")));
				BigDecimal shopInfoTotalPrice = new BigDecimal(0);
				//总消费金额
				shopInfoTotalPrice = price.multiply(quantity);
				map.put("totalPrice",shopInfoTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
				if(shopInfoTotalPrice.compareTo(minAmount)>0){
					map.put("postage",0);
				}
				list.add(map);
				//保存店铺list（list为店铺中商品的集合）
				shopInfoMap.put(map.get("shopInfoId"), list);
			}else{//如果在shopInfoMap中有当前店铺刚取出店铺
				list= (List<Map>) shopInfoMap.get(map.get("shopInfoId"));
				//最底消费金额
				BigDecimal minAmount=new BigDecimal(String.valueOf(map.get("minAmount")));
				//满足则包邮
				minAmount = minAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
				//商品成交价
				BigDecimal price=new BigDecimal(String.valueOf(map.get("salesPrice")));
				//购买数量
				BigDecimal quantity=new BigDecimal(String.valueOf(map.get("quantity")));
				Map oneMap = list.get(0);
				BigDecimal shopInfoTotalPrice = (BigDecimal) oneMap.get("totalPrice");
				if(Utils.objectIsNotEmpty(shopInfoTotalPrice)){
					//总消费金额
					shopInfoTotalPrice = shopInfoTotalPrice.add(price.multiply(quantity));
				}else{
					shopInfoTotalPrice = price.multiply(quantity);
				}
				map.put("totalPrice",shopInfoTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
					List<Map> tempList = new ArrayList<Map>();
					for(Map m :list){
						if(shopInfoTotalPrice.compareTo(minAmount)>0){
							m.put("postage",0);
						}
						tempList.add(m);
					}
					if(shopInfoTotalPrice.compareTo(minAmount)>0){
						map.put("postage",0);
					}
					tempList.add(map);
					//保存店铺list（list为店铺中商品的集合）
					shopInfoMap.put(map.get("shopInfoId"), tempList);
				
				
				//向店铺中添加商品
				//list.add(map);
			}
    		
    		//取得折扣比例
			BigDecimal bdDiscount = (BigDecimal) map.get("discount");
			//商品销售价*折扣比例*数量=消费总金额
			BigDecimal temTotalPrice = new BigDecimal(0);
			if(Utils.objectIsNotEmpty(bdDiscount)){
				BigDecimal temp = new BigDecimal(0.1);
				//消费总金额=商品销售价*折扣比例*数量
				temTotalPrice = salesPrice.multiply(num).multiply(bdDiscount).multiply(temp);
				map.put("subTotal",temTotalPrice);
			}else{
				temTotalPrice = salesPrice.multiply(num);
				map.put("subTotal",temTotalPrice);
			}
			//最终支付金额（折扣已打）商品销售价*折扣比例*数量
			finalAmount=finalAmount.add(temTotalPrice);
			//商品总金额（折扣未计算）商品销售价*数量
			totalPrice=totalPrice.add(salesPrice.multiply(num));
    		
//    		if("2".equals(String.valueOf(map.get("isChargeFreight")))){
//    			if(null!=map.get("freightPrice")){
//    				freightPrice=freightPrice.add(new BigDecimal(String.valueOf(map.get("freightPrice"))));
//    			}
//    		}
    	}
    	//finalAmount = totalPrice.add(freightPrice);//加上运费计算
    	//finalAmount = finalAmount.subtract(orders.getChangeAmount());//去掉信用额度抵扣
		Iterator<Object> keys = shopInfoMap.keySet().iterator();
		//遍历得到运费总额
		//定义一个店铺id字符串，用于校验当前商品是否存储在于同一店铺内
		String shopIds=",";
		while(keys.hasNext()) {
			Integer key = (Integer) keys.next();
			List<Map> listMap=(List<Map>) shopInfoMap.get(key);
			Map m =  listMap.get(0);
			//获取店铺ID
			String shopInfoId = String.valueOf(m.get("shopInfoId"));
			//判断店铺id是否被记录
			int index = shopIds.indexOf(","+shopInfoId+",");
			if(index<0){//没有被记录
				//做记录
				shopIds+=shopIds+",";
				//计入运费信息
				freightPrice = freightPrice.add(new BigDecimal(String.valueOf(m.get("postage"))));
			}
		} 
    	finalAmount = finalAmount.add(freightPrice);//加上运费计算
    	//finalAmount = finalAmount.subtract(orders.getChangeAmount());//去掉金币抵扣值
    	
    	if(Utils.objectIsNotEmpty(rates)){//税率不为空
    		finalAmount = finalAmount.add( rates.multiply(finalAmount.subtract(freightPrice)).multiply(new BigDecimal(0.01)));
    	}
    	finalAmount = finalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数
    	//页面取得应付金额
    	BigDecimal ordersFinalAmount = orders.getFinalAmount();
    	ordersFinalAmount = ordersFinalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数
    	finalAmount = finalAmount.subtract(orders.getChangeAmount());//去掉金币抵扣值
    	if(Utils.objectIsNotEmpty(orders.getOrderCouponAmount())){
    		finalAmount = finalAmount.subtract(orders.getOrderCouponAmount());//去掉优惠券抵扣值
    	}
    	orders.setTotalAmount(totalPrice);//商品总金额
    	if(freightPrice.compareTo(orders.getFreight())!=0){//运费金额比较
			ordersNo=null ;
    	}else if(finalAmount.compareTo(ordersFinalAmount)!=0){//应付总金额比较
			ordersNo=null ;
    	}else{
    		ShoppingCart sc = (ShoppingCart)shoppingCartService.getObjectByParams("where o.customerId="+customer.getCustomerId());
    		if(StringUtils.isNotEmpty(shopCartIds)){
    			payMap = shoppingCartOrderService.saveOrUpdateOrders(orders, shopCartIds,customer,custAddress,sc.getStockUpDate(),son,shopInfo,servletContext);
    		}else{
    			payMap = shoppingCartOrderService.saveOrUpdateOrdersBuy(orders, customer, custAddress, productId, productNum,son,shopInfo);
    		}
			payMap.put("subject", Escape.escape("您从商城购买商品"));
			ordersNo= String.valueOf(payMap.get("totalOrdersNo"));
			totalOrdersNo = String.valueOf(payMap.get("totalOrdersNo"));
			//safetyCertificate = getSafety(orders.getCustomerId());
			ordersNoAll = String.valueOf(payMap.get("ordersNoAll"));
    	}
		return SUCCESS;
	}
	
	/**
	 * 团购订单提交
	 */
	public String toTuanSuccessOrder(){
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		//税率
		BigDecimal rates = null;
		//发票信息
		if("2".equals(orders.getInvoiceType().toString())){//普通发票
			shopInfo.setInvoiceType(orders.getInvoiceType());
			shopInfo.setInvoiceInfo(orders.getInvoiceInfo());
			shopInfo.setCompanyName(orders.getCompanyNameForInvoice());
			String strgeneralRates = (String) fileUrlConfig.get("generalRates");
			rates = new BigDecimal(strgeneralRates);
		}else if("3".equals(orders.getInvoiceType().toString())){//增值税发票
			shopInfo.setInvoiceType(orders.getInvoiceType());
			shopInfo.setTaxpayerNumber(orders.getTaxpayerNumber());
			shopInfo.setAddressForInvoice(orders.getAddressForInvoice());
			shopInfo.setPhoneForInvoice(orders.getPhoneForInvoice());
			shopInfo.setOpeningBank(orders.getOpeningBank());
			shopInfo.setBankAccountNumber(orders.getBankAccountNumber());
			//shopInfo.setCompanyName(orders.getCompanyNameForInvoice());
			if(Utils.objectIsNotEmpty(shopInfoCompanyName)){
				shopInfo.setCompanyName(shopInfoCompanyName);
			}
			String vatRates = (String) fileUrlConfig.get("vatRates");
			rates = new BigDecimal(vatRates);
		}else{
			shopInfo.setInvoiceType(1);
		}
		shopInfo.setCustomerId(customer.getCustomerId());
		shopInfo = (ShopInfo) shopInfoService.saveOrUpdateObject(shopInfo);
		session.setAttribute("shopInfo", shopInfo);
		payMap = shoppingCartOrderService.saveOrUpdateOrdersBuy(orders, customer, custAddress, productId, productNum,son,shopInfo);
		payMap.put("subject", Escape.escape("您从商城购买商品"));
		ordersNo= String.valueOf(payMap.get("totalOrdersNo"));
		totalOrdersNo = String.valueOf(payMap.get("totalOrdersNo"));
		//safetyCertificate = getSafety(orders.getCustomerId());
		ordersNoAll = String.valueOf(payMap.get("ordersNoAll"));
		return SUCCESS;
	}
	
	/**
	 * 去支付
	 * @return
	 */
	public String toPay(){
		String tradeId = (String)request.getParameter("tradeId");
		//模拟支付-----begin
		//orders = (Orders)ordersService.getObjectByParams(" where o.totalOrdersNo='"+tradeId+"'");
		List<Orders> ordersList = ordersService.findObjects(" where o.totalOrdersNo='"+tradeId+"'");
		for(Orders o :ordersList){
			o.setOrdersState(0);//订单状态
			o.setSettlementStatus(1);//确定付款
			ordersService.saveOrUpdateObject(o);
		}
//		if(orders != null){
//			orders.setOrdersState(2);//收款确认
//			ordersService.saveOrUpdateObject(orders);
//		}
		//模拟支付-----end
		ordersNo =tradeId;
		return SUCCESS;
	}
	/**
	 * 支付成功返回action(快钱)
	 * @return
	 * @throws IOException 
	 */
	public void toPaySuccess() throws IOException{
		//人民币网关账号，该账号为11位人民币网关商户编号+01,该值与提交时相同。
		String merchantAcctId = request.getParameter("merchantAcctId");
		//网关版本，固定值：v2.0,该值与提交时相同。
		String version = request.getParameter("version");
		//语言种类，1代表中文显示，2代表英文显示。默认为1,该值与提交时相同。
		String language = request.getParameter("language");
		//签名类型,该值为4，代表PKI加密方式,该值与提交时相同。
		String signType = request.getParameter("signType");
		//支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10,该值与提交时相同。
		String payType = request.getParameter("payType");
		//银行代码，如果payType为00，该值为空；如果payType为10,该值与提交时相同。
		String bankId = request.getParameter("bankId");
		//商户订单号，该值与提交时相同。
		String orderId = request.getParameter("orderId");
		//订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101,该值与提交时相同。
		String orderTime = request.getParameter("orderTime");
		//订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试,该值与支付时相同。
		String orderAmount = request.getParameter("orderAmount");
		// 快钱交易号，商户每一笔交易都会在快钱生成一个交易号。
		String dealId = request.getParameter("dealId");
		//银行交易号 ，快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空
		String bankDealId = request.getParameter("bankDealId");
		//快钱交易时间，快钱对交易进行处理的时间,格式：yyyyMMddHHmmss，如：20071117020101
		String dealTime = request.getParameter("dealTime");
		//商户实际支付金额 以分为单位。比方10元，提交时金额应为1000。该金额代表商户快钱账户最终收到的金额。
		String payAmount = request.getParameter("payAmount");
		//费用，快钱收取商户的手续费，单位为分。
		String fee = request.getParameter("fee");
		//扩展字段1，该值与提交时相同。
		String ext1 = request.getParameter("ext1");
		//扩展字段2，该值与提交时相同。
		String ext2 = request.getParameter("ext2");
		//处理结果， 10支付成功，11 支付失败，00订单申请成功，01 订单申请失败
		String payResult = request.getParameter("payResult");
		//错误代码 ，请参照《人民币网关接口文档》最后部分的详细解释。
		String errCode = request.getParameter("errCode");
		//签名字符串 
		String signMsg = request.getParameter("signMsg");
		String merchantSignMsgVal = "";
		merchantSignMsgVal = appendParam(merchantSignMsgVal,"merchantAcctId", merchantAcctId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "version",version);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "language",language);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType",signType);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType",payType);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId",bankId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId",orderId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime",orderTime);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount",orderAmount);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId",dealId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId",bankDealId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime",dealTime);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount",payAmount);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult",payResult);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode",errCode);
		Pkipair pki = new Pkipair();
		boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
		int rtnOK =0;
	  	String rtnUrl="";
		if(flag){
		  		switch(Integer.parseInt(payResult))
		  		{
		  			case 10:
		  					/*
		  					此处商户可以做业务逻辑处理
		  					*/
		  					Customer customer2 = (Customer) session.getAttribute("customer");
		  					ShopInfo shopInfo2 = (ShopInfo) session.getAttribute("shopInfo");
		  					Sonaccount sonaccount2 = (Sonaccount) session.getAttribute("sonaccount");
		  					String No = ext1;
		  					if(No!=null){
		  						shoppingCartOrderService.saveOrUpdateToPaySuccess(No,customer2,shopInfo2,sonaccount2,dealId);
		  					}else{
		  						shoppingCartOrderService.saveOrUpdateToPaySuccess(ordersNo,customer2,shopInfo2,sonaccount2,dealId);
		  					}
		  					rtnOK=1;
		  					//以下是我们快钱设置的show页面，商户需要自己定义该页面。
		  					rtnUrl=String.valueOf(fileUrlConfig.get("quickbuck_paySuccessPageUrl_success"));//从配置文件中获取支付成功的页面
		  					break;
		  			default:
		  					rtnOK=0;
		  					//以下是我们快钱设置的show页面，商户需要自己定义该页面。
		  					rtnUrl="http://219.233.173.50:8801/RMBPORT/show.jsp?msg=false";
		  					break;
		  		}
		  	}else{
		  		rtnOK=0;
		  		//以下是我们快钱设置的show页面，商户需要自己定义该页面。
		  		rtnUrl="http://219.233.173.50:8801/RMBPORT/show.jsp?msg=error";
		  	}
			PrintWriter out=response.getWriter();
			out.print("<result>"+rtnOK+"</result><redirecturl>"+rtnUrl+"</redirecturl>");
			out.flush();
			out.close();
	}
	/**
	 * 快钱提供的工具方法
	 * @param returns
	 * @param paramId
	 * @param paramValue
	 * @return
	 */
	public String appendParam(String returns, String paramId, String paramValue) {
		if (!returns.equals("")) {
			if (!"".equals(paramValue)) {
				returns += "&" + paramId + "=" + paramValue;
			}
		} else {
			if (!"".equals(paramValue)) {
				returns = paramId + "=" + paramValue;
			}
		}
		return returns;
	}
	
	/**
	 * 支付成功后点击返回商户跳转
	 */
	public String gotoPaySuccessPage(){
		return SUCCESS;
	}
	
	/***
	 * 获得登录会员的收获地址集合
	 * @return
	 */
	public String gotoCustomerAddressPage(){
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		custAddress = (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams("where o.isSendAddress=1 and o.customerId="+customer.getCustomerId());
		//测试取会员ID：1
		//countrys =geoIpCountryService.getAllCountry();//查询geo国家数据
		customerAcceptAddressList=customerAcceptAddressService.findObjects(" where o.isSendAddress=0 and o.customerId="+customer.getCustomerId()+" order by isSendAddress desc,customerAcceptAddressId");
		//一级地区parent=0
    	String firstHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
    	firstAreaList=areaService.findListMapByHql(firstHql);
		return SUCCESS;
	}
	
	/**
	* 支付宝请求
	 * @throws Exception 
	*/
	public void alipayapi() throws Exception{
		//取得本网站地址
		String basePath = WebUtil.getDomainUrl(request);
		//支付类型,必填，不能修改
		String payment_type = "1";
		//支付宝，合作身份者ID，以2088开头由16位纯数字组成的字符串
		String alipayPartner = (String) fileUrlConfig.get("alipayPartner");
		//支付宝接，口名称
		String alipayService = (String) fileUrlConfig.get("alipayService");
		//支付宝，字符编码格式 目前支持 gbk 或 utf-8
		String alipayInputCharset = (String) fileUrlConfig.get("alipayInputCharset");
		//服务器异步通知页面路径,需http://格式的完整路径，不能加?id=123这类自定义参数
//		String notify_url = "http://192.168.1.33:8080/thshop/toPay/toPaySuccess.action";
		String notify_url = basePath+"alipay/notifyUrlAlipay.action";//回调地址

		//页面跳转同步通知页面路径,需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
//		String return_url = "http://192.168.1.33:8080/thshop/toPay/toPaySuccess.action"; 
		String return_url = basePath+"alipay/toPaySuccessAlipay.action";//回调地址

		//卖家支付宝帐户,必填
		String seller_email = (String) fileUrlConfig.get("alipaySellerEmail");

		//商户订单号,商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//订单描述,存放子订单号集合
		String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
		
		//订单名称,必填
		String subject = this.subject+",订单号为："+out_trade_no;
		//订单集合
		List<Orders> ordersList=null;
		//查询订单，未付款，总订单号，子订单号集合
		String hql = " where o.totalOrdersNo='"+out_trade_no+"' and o.ordersNo in("+body+") and o.settlementStatus=0";
		if(Utils.objectIsNotEmpty(out_trade_no)&&Utils.objectIsNotEmpty(body)){
			if(out_trade_no.equals(body)){
				hql = "where o.ordersNo='"+body+"' and o.settlementStatus=0";
			}
			ordersList = ordersService.findObjects(hql);
		}
		BigDecimal finalPrice = new BigDecimal(0);
		for(Orders o : ordersList){
			finalPrice = finalPrice.add(o.getFinalAmount());//支付金额
		}
		//保留两位小数
		finalPrice = finalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		//付款金额,必填
		String price = finalPrice.toString();

		//商品数量,必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		String quantity = "1";

		//物流费用,必填，即运费
		String logistics_fee = "0.00";

		//物流类型,必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		String logistics_type = "EXPRESS";

		//物流支付方式,必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		String logistics_payment = "SELLER_PAY";
		//签名类型
		String sign_type = (String) fileUrlConfig.get("alipaySignType");

		//商品展示地址,需以http://开头的完整路径，如：http://www.xxx.com/myorder.html
		//String show_url = new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
		//收货人姓名,如：张三
		String receive_name = "";
		//收货人地址,XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号
		String receive_address = "";
		//收货人邮编,如：123456
		String receive_zip = "";
		//收货人电话号码,0571-88158090
		String receive_phone = "";
		//收货人手机号码,如：13312341234
		String receive_mobile = "";
		Integer payMode = null;
		String bankCode = "";
		if(Utils.objectIsNotEmpty(ordersList)&&ordersList.size()>0){
			orders = ordersList.get(0);
			if(Utils.objectIsNotEmpty(orders.getConsignee())){
				receive_name = orders.getConsignee();
			}
			if(Utils.objectIsNotEmpty(orders.getAddress())){
				receive_address = orders.getAddress();
			}
			if(Utils.objectIsNotEmpty(orders.getPostcode())){
				receive_zip = orders.getPostcode();
			}
			if(Utils.objectIsNotEmpty(orders.getPhone())){
				receive_phone = orders.getPhone();
			}
			if(Utils.objectIsNotEmpty(orders.getMobilePhone())){
				receive_mobile = orders.getMobilePhone();
			}
			if(Utils.objectIsNotEmpty(orders.getPayMode())){
				payMode = orders.getPayMode();
			}
			if(Utils.objectIsNotEmpty(orders.getBankCode())){
				bankCode = orders.getBankCode();
			}
		}
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", alipayService);//trade_create_by_buyer标准双接口  create_partner_trade_by_buyer担保交易
        sParaTemp.put("partner", alipayPartner);//支付宝，合作身份者ID
        sParaTemp.put("_input_charset", alipayInputCharset);//支付宝，字符编码格式 目前支持 gbk 或 utf-8
		sParaTemp.put("payment_type", payment_type);//支付类型
		sParaTemp.put("notify_url", notify_url);//服务器异步通知页面路径
		sParaTemp.put("return_url", return_url);//页面跳转同步通知页面路径
		sParaTemp.put("seller_email", seller_email);//卖家支付宝帐户,必填
		sParaTemp.put("out_trade_no", out_trade_no);//商户订单号,商户网站订单系统中唯一订单号，必填
		sParaTemp.put("subject", subject);//商品名称
		sParaTemp.put("price", price);//付款金额,必填
		sParaTemp.put("quantity", quantity);//商品数量
		sParaTemp.put("logistics_fee", logistics_fee);//物流费用
		sParaTemp.put("logistics_type", logistics_type);//物流类型
		sParaTemp.put("logistics_payment", logistics_payment);//物流支付方式
		sParaTemp.put("body", body);//订单描述,存放子订单号集合
		sParaTemp.put("sign_type",sign_type) ;//签名类型
		if(payMode != null){
			if(payMode == 4){
				sParaTemp.put("paymethod","bankPay");
				sParaTemp.put("defaultbank", bankCode);
			}
		}
		//sParaTemp.put("show_url", show_url);
		if(Utils.objectIsNotEmpty(receive_name)){
			sParaTemp.put("receive_name", receive_name);//收货人姓名
		}
		if(Utils.objectIsNotEmpty(receive_address)){
			sParaTemp.put("receive_address", receive_address);//收货人地址
		}
		if(Utils.objectIsNotEmpty(receive_zip)){
			sParaTemp.put("receive_zip", receive_zip);//收货人邮编
		}
		if(Utils.objectIsNotEmpty(receive_phone)){
			sParaTemp.put("receive_phone", receive_phone);//收货人电话
		}
		if(Utils.objectIsNotEmpty(receive_mobile)){
			sParaTemp.put("receive_mobile", receive_mobile);//收货人手机号
		}
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");

/********************以下是将请求数据发向支付宝***********************/
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		out = response.getWriter();
		if(Utils.objectIsNotEmpty(out_trade_no)&&Utils.objectIsNotEmpty(body)){
			out.write(String.valueOf(sHtmlText));
		}else{
			out.write("支付失败！");
		}
		out.flush();
		out.close();
		
	}
	/**
	 * 支付宝支付成功返回action同步通知
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toPaySuccessAlipay(){
		return SUCCESS;
	}
	/**
	 功能：支付宝服务器异步通知Action
	 版本：3.3
	 日期：2012-08-17
	 说明：
	 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	 //***********功能说明***********
	 该方法不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该方法。
	 该页面调试工具请使用写文本函数logResult，该函数在com.alipay.util文件夹的AlipayNotify.java类文件中
	 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
* @throws IOException 
**/
public void notifyUrlAlipay() throws IOException{
	//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		params.put(name, valueStr);
	}
	//交易状态
	String trade_status = request.getParameter("trade_status");
	//String totalOrdersNo = request.getParameter("out_trade_no");//总订单号
	String orderNo = request.getParameter("body");//子订单号数组
	String tradeNo = request.getParameter("trade_no");//支付成功，支付宝返回的交易号
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
	response.setContentType("text/html;charset=utf-8");
	PrintWriter out;
	out = response.getWriter();
	try {
		if(AlipayNotify.verify(params)){//验证成功
			if(trade_status.equals("TRADE_FINISHED")&&Utils.objectIsNotEmpty(orderNo)&&Utils.objectIsNotEmpty(tradeNo)){//成功
				shoppingCartOrderService.saveOrUpdateToPaySuccess(tradeNo,orderNo);
				out.println("success");	//请不要修改或删除
			}else if (trade_status.equals("TRADE_SUCCESS")&&Utils.objectIsNotEmpty(orderNo)&&Utils.objectIsNotEmpty(tradeNo)){//成功
				shoppingCartOrderService.saveOrUpdateToPaySuccess(tradeNo,orderNo);
				out.println("success");	//请不要修改或删除
			}else{
				out.println("fail");
			}
		}else{//验证失败
			out.println("fail");
		}
	}catch(Exception e){
		out.println("fail");
	}finally{
		out.flush();
		out.close();
	}
}
	
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setShoppingCartOrderService(
			IShoppingCartOrderService shoppingCartOrderService) {
		this.shoppingCartOrderService = shoppingCartOrderService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public CustomerAcceptAddress getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(CustomerAcceptAddress custAddress) {
		this.custAddress = custAddress;
	}
	public String getShopCartIds() {
		return shopCartIds;
	}
	public void setShopCartIds(String shopCartIds) {
		this.shopCartIds = shopCartIds;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getCartMap() {
		return cartMap;
	}
	@SuppressWarnings("rawtypes")
	public void setCartMap(List<Map> cartMap) {
		this.cartMap = cartMap;
	}
	public String getOrderJson() {
		return orderJson;
	}
	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public List<CustomerAcceptAddress> getCustomerAcceptAddressList() {
		return customerAcceptAddressList;
	}
	public void setCustomerAcceptAddressList(
			List<CustomerAcceptAddress> customerAcceptAddressList) {
		this.customerAcceptAddressList = customerAcceptAddressList;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getCountrys() {
		return countrys;
	}
	@SuppressWarnings("rawtypes")
	public void setCountrys(List<Map> countrys) {
		this.countrys = countrys;
	}
	public Map<String, Object> getPayMap() {
		return payMap;
	}
	public void setPayMap(Map<String, Object> payMap) {
		this.payMap = payMap;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getFirstAreaList() {
		return firstAreaList;
	}
	@SuppressWarnings("rawtypes")
	public void setFirstAreaList(List<Map> firstAreaList) {
		this.firstAreaList = firstAreaList;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public Map<Object, Object> getShopInfoMap() {
		return shopInfoMap;
	}
	public void setShopInfoMap(Map<Object, Object> shopInfoMap) {
		this.shopInfoMap = shopInfoMap;
	}
	public BigDecimal getCoinTotal() {
		return coinTotal;
	}
	public void setCoinTotal(BigDecimal coinTotal) {
		this.coinTotal = coinTotal;
	}
	public void setVirtualCoinService(IVirtualCoinService virtualCoinService) {
		this.virtualCoinService = virtualCoinService;
	}
	public String getIsAllTransactionFlag() {
		return isAllTransactionFlag;
	}
	public void setIsAllTransactionFlag(String isAllTransactionFlag) {
		this.isAllTransactionFlag = isAllTransactionFlag;
	}
	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	public Integer getIsInvoice() {
		return isInvoice;
	}
	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}
	public String getLineOfCredit() {
		return lineOfCredit;
	}
	public void setLineOfCredit(String lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}
	public void setLineofcreditItemService(
			ILineofcreditItemService lineofcreditItemService) {
		this.lineofcreditItemService = lineofcreditItemService;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public String getLineOfCreditTal() {
		return lineOfCreditTal;
	}
	public void setLineOfCreditTal(String lineOfCreditTal) {
		this.lineOfCreditTal = lineOfCreditTal;
	}
	public void setBuyersRecordService(IBuyersRecordService buyersRecordService) {
		this.buyersRecordService = buyersRecordService;
	}
	public SafetyCertificate getSafetyCertificate() {
		return safetyCertificate;
	}
	public void setSafetyCertificate(SafetyCertificate safetyCertificate) {
		this.safetyCertificate = safetyCertificate;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getTotalOrdersNo() {
		return totalOrdersNo;
	}
	public void setTotalOrdersNo(String totalOrdersNo) {
		this.totalOrdersNo = totalOrdersNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOrdersNoAll() {
		return ordersNoAll;
	}
	public void setOrdersNoAll(String ordersNoAll) {
		this.ordersNoAll = ordersNoAll;
	}
	public String getShopInfoCompanyName() {
		return shopInfoCompanyName;
	}
	public void setShopInfoCompanyName(String shopInfoCompanyName) {
		this.shopInfoCompanyName = shopInfoCompanyName;
	}
	public void setMallCoinService(IMallCoinService mallCoinService) {
		this.mallCoinService = mallCoinService;
	}
	public List<Map> getToPayList() {
		return toPayList;
	}
	public void setToPayList(List<Map> toPayList) {
		this.toPayList = toPayList;
	}
	public String getBuyPriceCustomer() {
		return buyPriceCustomer;
	}
	public void setBuyPriceCustomer(String buyPriceCustomer) {
		this.buyPriceCustomer = buyPriceCustomer;
	}
	public String getAddressisok() {
		return addressisok;
	}
	public void setAddressisok(String addressisok) {
		this.addressisok = addressisok;
	}
	public String getCustomerDiscountCouponID() {
		return customerDiscountCouponID;
	}
	public void setCustomerDiscountCouponID(String customerDiscountCouponID) {
		this.customerDiscountCouponID = customerDiscountCouponID;
	}
	public List<Map> getOrdersListMap() {
		return ordersListMap;
	}
	public void setOrdersListMap(List<Map> ordersListMap) {
		this.ordersListMap = ordersListMap;
	}
	public void setCustomerdiscountcouponService(
			ICustomerdiscountcouponService customerdiscountcouponService) {
		this.customerdiscountcouponService = customerdiscountcouponService;
	}
}
