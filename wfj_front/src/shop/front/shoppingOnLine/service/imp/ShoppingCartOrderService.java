package shop.front.shoppingOnLine.service.imp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import rating.buyersRecord.dao.IBuyersRecordDao;
import shop.common.pojo.CoinRules;
import shop.customer.dao.ICustomerAcceptAddressDao;
import shop.customer.dao.ICustomerDao;
import shop.customer.dao.IIncomePayDetailDao;
import shop.customer.dao.IMallCoinDao;
import shop.customer.dao.IVirtualCoinDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.IncomePayDetail;
import shop.customer.pojo.MallCoin;
import shop.customer.pojo.Sonaccount;
import shop.front.shoppingOnLine.dao.IShoppingCartOrderDao;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartOrderService;
import shop.lineofcreditItem.dao.ILineofcreditItemDao;
import shop.lineofcreditItem.pojo.LineofcreditItem;
import shop.order.dao.IOrdersDao;
import shop.order.dao.IOrdersListDao;
import shop.order.dao.IOrdersOPLogDao;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.pojo.OrdersOPLog;
import shop.product.dao.IBrandDao;
import shop.product.dao.IProductInfoDao;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductInfo;
import shop.store.dao.ICustomerHaveCouponDao;
import shop.store.dao.IMemberShipDao;
import shop.store.dao.IShopInfoDao;
import shop.store.dao.ISupplierMallCoinDetailDao;
import shop.store.pojo.MemberShip;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.SupplierMallCoinDetail;
import tuan.dao.ITuanProductDao;
import tuan.pojo.TuanProduct;
import util.common.ShopDeductionMoneyUtils;
import util.other.Escape;
import util.other.SerialNumberUtil;
import util.other.Utils;
import util.service.BaseService;
import discountcoupon.dao.ICustomerdiscountcouponDao;
/**
 * ShoppingCartOrderService - 确认购物车订单service
 */
@SuppressWarnings("unused")
public class ShoppingCartOrderService extends BaseService<ShoppingCart> implements IShoppingCartOrderService {
	private IShoppingCartOrderDao shoppingCartOrderDao;
    /**商品Dao**/
    private IProductInfoDao productInfoDao;
    /**收货地址Dao**/
    private ICustomerAcceptAddressDao customerAcceptAddressDao;
    /**订单DAo**/
    private IOrdersDao ordersDao;
    /**订单明细**/
    private IOrdersListDao ordersListDao;
    /**订单日志**/
    private IOrdersOPLogDao ordersOPLogDao;
    /**买家等级升迁记录service**/
    private IBuyersRecordDao buyersRecordDao;
    /**收支明细service**/
    private IIncomePayDetailDao incomePayDetailDao;
    /**商城币收支明细dao**/
    private ISupplierMallCoinDetailDao supplierMallCoinDetailDao;
    /**用户Service**/
    private ICustomerDao customerDao;
    /**客户已领优惠券表**/
    private ICustomerHaveCouponDao customerHaveCouponDao;
    /**虚拟金币DAO**/
    private IVirtualCoinDao virtualCoinDao ;
    private IMallCoinDao mallCoinDao ;
    /**店铺DAO**/
    private IShopInfoDao shopInfoDao;
    /**品牌DAO**/
    private IBrandDao brandDao;
    /**额度明细service**/
	private ILineofcreditItemDao lineofcreditItemDao;
	/**商品成交价格**/
	private BigDecimal buyPrice;
	/**店铺会员信息**/
	private IMemberShipDao memberShipDao;
	/**用户优惠券**/
	private ICustomerdiscountcouponDao customerdiscountcouponDao;
	/**团购商品**/
	private ITuanProductDao tuanProductDao;
	
    public void setShoppingCartOrderDao(IShoppingCartOrderDao shoppingCartOrderDao) {
		this.baseDao=this.shoppingCartOrderDao = shoppingCartOrderDao;
	}
	/**
	 * 根据条件查询用户下的店铺id
	 * @param column 去重字段
	 * @param whereCondition  条件
	 * @return
	 */
	public List<ShoppingCart> findShoppingCartIds(String column,String whereCondition){
		List<ShoppingCart> list=baseDao.findUnSame(column, whereCondition);
		return list;
	}
	/**
	 * 根据商品id条件查询店铺id
	 * @param column 去重字段
	 * @param whereCondition  条件
	 * @return
	 */
	public List<ProductInfo> findShopInfoIdsByProduct(String column,String whereCondition){
		List<ProductInfo> list=productInfoDao.findUnSame(column, whereCondition);
		return list;
	}
	/**
	 * 生成订单及保存相关表
	 * @param orderJson订单的相关信息
	 * @param shoppingCartIds购物车的ids
	 * @return
	 */
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public Map<String,Object>  saveOrUpdateOrders(Orders order,String shoppingCartIds ,Customer customer,CustomerAcceptAddress custAddress,String stockUpDate,Sonaccount son,ShopInfo shopInfo2,ServletContext servletContext){
		Map<String, Object> keyBookMap = (Map<String, Object>) servletContext.getAttribute("coinRules");//获取天海数据字典
		Map<String,Object> map = new HashMap<String ,Object>();
		//读取系统systemConfig.properties中的信息
		Map<?,?> fileUrlConfigMap = (Map<?,?>)ServletActionContext.getServletContext().getAttribute("fileUrlConfig");
		//查询到购物车中的信息
		//收货地址
		CustomerAcceptAddress customerAcceptAddress = customerAcceptAddressDao.get("where o.customerAcceptAddressId ="+custAddress.getCustomerAcceptAddressId());
		String hql="select a.shopInfoId as shopInfoId from ShoppingCart a where a.shopCartId in ("+shoppingCartIds+") group by a.shopInfoId";
		List<Map> shopIdMap=shoppingCartOrderDao.findListMapByHql(hql);
		String totalOrdersNo = SerialNumberUtil.OrderSnNumber();//总订单号
		String ordersNoAll = "";
		BigDecimal payAmount = new BigDecimal(0);
		//获取当前用户最新的虚拟币记录
		MallCoin oldMallCoin = mallCoinDao.get("where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc ,o.mallCoinId desc limit 1");
		BigDecimal remainingNumber = new BigDecimal(0);
		if(oldMallCoin!=null&&oldMallCoin.getRemainingNumber().compareTo(new BigDecimal(0))>0){
			remainingNumber = oldMallCoin.getRemainingNumber();
		}
		//最新的当前用户授信明细记录
		LineofcreditItem li_1 = lineofcreditItemDao.get("where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc,o.lineOfCreditId desc");
		BigDecimal remainingNumber_ed = new BigDecimal(0);
		if(li_1!=null&&li_1.getRemainingNumber().compareTo(new BigDecimal(0))>0){
			remainingNumber_ed = li_1.getRemainingNumber();
		}
		//店铺Id不为空
		if(shopIdMap!=null&&shopIdMap.size()>0){
			//店铺遍历，生成对应的订单
			for(Map<String,Object> shopInfoId:shopIdMap){
				//得到店铺对象
				ShopInfo shopInfoTemp = shopInfoDao.getObjectById(shopInfoId.get("shopInfoId")+"");
				//店铺会员申请实体类
				MemberShip memberShip = memberShipDao.get(" where state=2 and o.customerId="+customer.getCustomerId()+" and o.shopInfoId="+shopInfoId.get("shopInfoId"));
				//得到购物车中的商品在同一店铺的记录
				List<ShoppingCart> cartList = shoppingCartOrderDao.findObjects("where o.shopInfoId="+shopInfoId.get("shopInfoId")+" and o.shopCartId in("+shoppingCartIds+")");
				if(cartList!=null&&cartList.size()>0){
					Orders orders = new Orders();
					BigDecimal finalAmount = new BigDecimal(0);//最终支付金额
					BigDecimal totalAmount = new BigDecimal(0);//总金额
					BigDecimal freight = new BigDecimal(0);//运费
					BigDecimal totalVirtualCoin = new BigDecimal(0);//佣金币
					BigDecimal totalVirtualCoinNumber = new BigDecimal(0);//送个客户的商城币
					
					for(ShoppingCart sc:cartList){
						buyPrice=new BigDecimal(0);
						ProductInfo shopProductInfo =productInfoDao.get(" where o.productId="+sc.getProductId());//商品信息
						//计算成交价格
						//getCustomerProductPrice(customer,shopProductInfo.getMarketPrice(),shopProductInfo.getSalesPrice());
						buyPrice = shopProductInfo.getSalesPrice();//销售价
						//单价*数量
						totalAmount = totalAmount.add(buyPrice.multiply(BigDecimal.valueOf(sc.getQuantity())));
					
						//如果不为空则有折扣比例，可进行对商品销售价格打折
						if(Utils.objectIsNotEmpty(memberShip)&&Utils.objectIsNotEmpty(memberShip.getDiscount())){
							BigDecimal temp = new BigDecimal(0.1);
							//店铺会员折扣比例
							BigDecimal discount = memberShip.getDiscount();
							//折扣价格=商品销售价*折扣比例*0.1
							BigDecimal discountAmount = new BigDecimal(0);
							discountAmount = buyPrice.multiply(discount).multiply(temp);
							//折扣后的价格*数量
							finalAmount = finalAmount.add(discountAmount.multiply(BigDecimal.valueOf(sc.getQuantity())));
							
						}else{
							finalAmount = finalAmount.add(buyPrice.multiply(BigDecimal.valueOf(sc.getQuantity())));
						}
						//邮费价格
//						if(null!=shopProductInfo.getFreightPrice()){
//							freight = freight.add(shopProductInfo.getFreightPrice());
//						}
						//用金币
						//totalVirtualCoin = totalVirtualCoin.add(shopProductInfo.getVirtualCoinNumber().multiply(new BigDecimal(sc.getQuantity())));
						//送个客户的商城币
						totalVirtualCoinNumber = totalVirtualCoinNumber.add(shopProductInfo.getGiveAwayCoinNumber().multiply(new BigDecimal(sc.getQuantity())));
					}
					//得到最底消费金额
					BigDecimal minAmout = shopInfoTemp.getMinAmount();
					//如果商品总金额小于最底消费那么邮费客户出
					if(totalAmount.compareTo(minAmout)<0){
						freight = shopInfoTemp.getPostage();
					}
					//finalAmount = finalAmount.add(freight);//商品总价格+运费总价格
					orders.setVirtualCoin(totalVirtualCoin);//佣金币
					orders.setVirtualCoinNumber(totalVirtualCoinNumber);//总送个客户的商城币
					orders.setOrdersId(null);
				/*	if(customer.getType()!=3){
						orders.setSonaccountId(son.getSonAccountId());//采购员ID
						orders.setBuyerName(son.getNickname());//采购员姓名
					}*/
					orders.setShopInfoId(Integer.parseInt(String.valueOf(shopInfoId.get("shopInfoId"))));//前台传值
					orders.setAddress(customerAcceptAddress.getAddress());
					//orders.setBestSendDate();
					orders.setCity(customerAcceptAddress.getCity());
					orders.setComments(null);//订单附言
					orders.setConsignee(customerAcceptAddress.getConsignee());//收货人
					orders.setCountry(customerAcceptAddress.getAreaCounty());
					orders.setCreateTime(new Date());
					orders.setUpdateTime(new Date());
					orders.setCustomerId(customerAcceptAddress.getCustomerId());
					orders.setEmail(customer.getEmail());
					//orders.setEmail("sb@qq.com");
					orders.setFlagContractor(null);//标志建筑
					orders.setIsLocked(0);//锁定操作
					orders.setMobilePhone(customerAcceptAddress.getMobilePhone());
					orders.setOosOperator(null);//缺货处理(先不做)
					orders.setOrdersRemark(null);//订单备注（客服留言）
					orders.setOrdersState(1);//0、未处理；1、订单确认(含义：正在处理)；3、收款确认(含义：等待付款)；4、正在配货(含义：等待发货)；5、已经发货；6、收货确认(含义：已经完成)；7、确认作废(含义：取消订单)；
					orders.setSettlementStatus(0);//0未付款
//					orders.setOrdersState(3);//0、未处理；1、订单确认(含义：正在处理)；3、收款确认(含义：等待付款)；4、正在配货(含义：等待发货)；5、已经发货；6、收货确认(含义：已经完成)；7、确认作废(含义：取消订单)；
//					orders.setSettlementStatus(1);//0未付款,1已付款
					orders.setSettlementStatusForSellers(0);//0未付款
					orders.setPhone(customerAcceptAddress.getPhone());
					orders.setPostcode(customerAcceptAddress.getPostcode());//邮政编码
					orders.setTotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP));//销售价总金额
					orders.setRegionLocation(customerAcceptAddress.getRegionLocation());
					orders.setSendType(order.getSendType());//收货的方式
					orders.setFreight(freight);//邮费
					if(memberShip!=null){
						orders.setDiscount(memberShip.getDiscount());//折扣
					}
					orders.setPayMode(order.getPayMode());//支付方式
					orders.setBankCode(order.getBankCode());//支付银行
					
					//设置发票信息
					orders.setCompanyNameForInvoice(order.getCompanyNameForInvoice());//发票抬头
					if("3".equals(String.valueOf(shopInfo2.getInvoiceType()))){//如果invoiceType为2  表示 该订单使用的是增值税发票
						orders.setInvoiceType(shopInfo2.getInvoiceType());
						orders.setTaxpayerNumber(order.getTaxpayerNumber());//纳税人识别号
						orders.setAddressForInvoice(order.getAddressForInvoice());//发票地址
						orders.setPhoneForInvoice(order.getPhoneForInvoice());//发票电话
						orders.setOpeningBank(order.getOpeningBank());//开户行
						orders.setBankAccountNumber(order.getBankAccountNumber());//卡号
					}else if("2".equals(String.valueOf(shopInfo2.getInvoiceType()))){
						orders.setInvoiceType(shopInfo2.getInvoiceType());
						orders.setInvoiceInfo(order.getInvoiceInfo());//发票内容
					}
					orders.setInvoiceType(order.getInvoiceType());//发票类型  1.不开发票  2.普通发票  3.增值税发票
					//税率
					BigDecimal rates = null;
					//税费
					BigDecimal taxation = new BigDecimal(0);
					if(orders.getInvoiceType()==2){
						String strgeneralRates = (String) fileUrlConfigMap.get("generalRates");
						rates = new BigDecimal(strgeneralRates);
					}
					if(orders.getInvoiceType()==3){
						String vatRates = (String) fileUrlConfigMap.get("vatRates");
						rates = new BigDecimal(vatRates);
					}
					if(Utils.objectIsNotEmpty(rates)){//税率不为空
						taxation=rates.multiply(finalAmount).multiply(new BigDecimal(0.01));
			    		finalAmount = finalAmount.add(taxation);
			    	}
					orders.setTaxation(taxation);
					
					SerialNumberUtil serialNumberUtil = new SerialNumberUtil();
					String ordersNo = serialNumberUtil.OrderSnNumber();
					ordersNoAll = ordersNoAll+ordersNo+",";
					orders.setOrdersNo(ordersNo);//订单号
					orders.setTotalOrdersNo(totalOrdersNo);
					finalAmount = finalAmount.add(freight);//加上邮费
					//调用封装好的  拆单 抵扣金额计算方法 
					//BigDecimal orderDeductionMoney = ShopDeductionMoneyUtils.orderDeductionMoney(order.getTotalAmount().add(order.getFreight()), finalAmount, order.getChangeAmount());
					//orders.setChangeAmount(orderDeductionMoney);//抵扣的金额
					//orders.setUserCoin(orderDeductionMoney);//使用的虚拟币数量
					//finalAmount = finalAmount.subtract(orderDeductionMoney);
					//订单商品总金额/总订单商品金额*使用金币金额=一个订单分到的金币金额
//					BigDecimal changeAmount = ShopDeductionMoneyUtils.orderDeductionMoney(orders.getTotalAmount(),order.getTotalAmount(),order.getChangeAmount());
					//BigDecimal allAmount = order.getFinalAmount().add(order.getChangeAmount().add(order.getOrderCouponAmount()));
					//BigDecimal changeAmount = ShopDeductionMoneyUtils.orderDeductionMoney(finalAmount,allAmount,order.getChangeAmount());
					//changeAmount = changeAmount.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数
					///orders.setChangeAmount(changeAmount);//抵扣的金额
					//orders.setUserCoin(changeAmount.multiply(new BigDecimal(10)));//使用的虚拟币数量
					//finalAmount = finalAmount.subtract(changeAmount);//应付金额减去抵扣的金额
					
					//减去优惠券金额      单商品总金额/总订单商品金额*使用优惠券金额=一个订单分到的优惠券金额
//					BigDecimal couponAmount = ShopDeductionMoneyUtils.orderDeductionMoney(orders.getTotalAmount(), order.getTotalAmount(), order.getOrderCouponAmount());
					//BigDecimal couponAmount = ShopDeductionMoneyUtils.orderDeductionMoney(finalAmount,allAmount, order.getOrderCouponAmount());
					//couponAmount = couponAmount.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数
					//orders.setOrderCouponAmount(couponAmount);//抵扣的金额
					//orders.setDiscountCouponId(order.getDiscountCouponId());//使用的优惠券Id
					//orders.setIsUseCoupon(order.getIsUseCoupon());//是否使用优惠券
					//finalAmount = finalAmount.subtract(couponAmount);//应付金额减去抵扣的金额

					orders.setFinalAmount(finalAmount.setScale(2, BigDecimal.ROUND_HALF_UP));//最终应付金额
					orders.setUseLineOfCredit(finalAmount.setScale(2, BigDecimal.ROUND_HALF_UP));//使用信用额度
					orders.setCustomerType(customer.getType());//下单人类型 用于区分企业与普通用户
					orders = ordersDao.saveOrUpdateObject(orders);//保存订单
					//当抵扣金额不为null且抵扣金额大于0时，保存用的虚拟金币记录
					if(orders!=null&&orders.getUserCoin()!=null&&orders.getUserCoin().compareTo(new BigDecimal(0))>0){
						MallCoin mallCoin = new MallCoin();
						remainingNumber = remainingNumber.subtract(orders.getUserCoin());//去掉当前使用的数量
						mallCoin.setFrozenNumber(orders.getUserCoin());//当前订单使用的数量，冻结起来
						mallCoin.setRemainingNumber(remainingNumber);//余额
						mallCoin.setOrdersId(orders.getOrdersId());
						mallCoin.setCustomerId(customer.getCustomerId());
						mallCoin.setOrdersNo(orders.getOrdersNo());
						mallCoin.setOperatorTime(new Date());//操作时间
						mallCoin.setSerialNumber(serialNumberUtil.VirtualCoinNumber());//虚拟金币流水号
						mallCoin.setRemarks(customer.getLoginName()+"购买商品，生成订单号为："+orders.getOrdersNo()+"冻结商城币"+orders.getUserCoin()+"个！");
						mallCoin.setTradeTime(new Date());//创建时间
						mallCoin.setTransactionNumber(orders.getUserCoin());//获取的虚拟金币
						mallCoin.setType(2);//1.收入2.支出
						mallCoinDao.saveOrUpdateObject(mallCoin);
					}
					//payAmount = payAmount.add(orders.getFinalAmount());//累加支付金额
					BigDecimal virtualCoinNumber = new BigDecimal(0);
					for(ShoppingCart shopCart:cartList){
						ProductInfo shopProductInfo =productInfoDao.get(" where o.productId="+shopCart.getProductId());
						ShopInfo shopInfo = shopInfoDao.get(" where o.shopInfoId="+shopProductInfo.getShopInfoId());
						Brand brand = brandDao.get(" where o.brandId="+shopProductInfo.getBrandId());
						OrdersList ordersList = new OrdersList();
						ordersList.setOrderDetailId(null);
						ordersList.setCreateTime(orders.getCreateTime());//订单的生成时间
						ordersList.setOrdersNo(orders.getOrdersNo());
						ordersList.setOrdersId(orders.getOrdersId());
						ordersList.setProductId(shopCart.getProductId());
						ordersList.setProductFullName(shopProductInfo.getProductFullName());
						ordersList.setMarketPrice(shopProductInfo.getMarketPrice());
						ordersList.setSalesPrice(shopProductInfo.getSalesPrice());
						ordersList.setBarCode(shopProductInfo.getBarCode());//商品条形码
						//取得系统设置的赠送金币百分比
						List l = (List) keyBookMap.get("virtualCoinProportion");
						CoinRules o = (CoinRules) l.get(0);
						BigDecimal virtualCoinProportion=new BigDecimal(String.valueOf(o.getValue()));//将比例转化成BigDecimal类型
						ordersList.setVirtualCoinProportion(virtualCoinProportion);
						if(Utils.objectIsNotEmpty(shopProductInfo.getGiveAwayCoinNumber())){
							//商城赠送的金币
							ordersList.setVirtualCoinNumber(shopProductInfo.getGiveAwayCoinNumber());
						}
						if(Utils.objectIsNotEmpty(shopProductInfo.getGiveAwayCoinNumber())){
							//赠送佣金币
							ordersList.setVirtualCoin(shopProductInfo.getVirtualCoinNumber());
						}
						//计算成交价  这保存为会员价
						buyPrice = shopProductInfo.getSalesPrice();//销售价
						//getCustomerProductPrice(customer,shopProductInfo.getMarketPrice(),shopProductInfo.getSalesPrice());
						//如果不为空则有折扣比例，可进行对商品销售价格打折
						if(Utils.objectIsNotEmpty(memberShip)&&Utils.objectIsNotEmpty(memberShip.getDiscount())){
							BigDecimal temp = new BigDecimal(0.1);
							//店铺会员折扣比例
							BigDecimal discount = memberShip.getDiscount();
							//折扣价格=商品销售价*折扣比例*0.1
							BigDecimal discountAmount = new BigDecimal(0);
							buyPrice = buyPrice.multiply(discount).multiply(temp);
						}
						ordersList.setMemberPrice(buyPrice.setScale(2, BigDecimal.ROUND_HALF_UP));//保留两位小数
						
						//订单商品总金额/总订单商品金额*使用金币金额=一个订单分到的金币金额
						//BigDecimal changeAmount2 = ShopDeductionMoneyUtils.orderDetailProductDeductionMoney(orders.getTotalAmount(),buyPrice.multiply(new BigDecimal(shopCart.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP),order.getChangeAmount());
						//changeAmount2 = changeAmount2.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数
						//ordersList.setChangeAmount(changeAmount2);//抵扣的金额
						//ordersList.setUserCoin(changeAmount2.multiply(new BigDecimal(10)));//使用的虚拟币数量
						
						//折扣比例
						//BigDecimal discount = shopCart.getDiscount();
						//ordersList.setDiscount(discount);
						ordersList.setCount(shopCart.getQuantity());//数量
						//ordersList.setCostPrice(shopProductInfo.getCostPrice());//进货价
						//ordersList.setBuyType(1);//1商城
						if(brand!=null&&brand.getBrandId()!=null){
							ordersList.setBrandName(brand.getBrandName());
							ordersList.setBrandId(brand.getBrandId());
						}
						if(shopInfo!=null){
							ordersList.setShopInfoId(shopInfo.getShopInfoId());
							ordersList.setShopName(shopInfo.getShopName());
						}
						ordersList.setProductCode(shopProductInfo.getProductCode());
						ordersList.setLogoImage(shopProductInfo.getLogoImg());
						ordersList.setFreightPrice(shopProductInfo.getFreightPrice());//运费
						ordersList.setCustomerId(orders.getCustomerId());
						ordersList.setStockUpDate(stockUpDate);
						ordersList.setSku(shopCart.getSku());//设置订单明细中的sku订货号
						
						ordersListDao.saveOrUpdateObject(ordersList);//保存订单明细
						
						shoppingCartOrderDao.deleteObject(shopCart);//修改购物车中显示状态
						shopProductInfo.setStoreNumber(shopProductInfo.getStoreNumber()-shopCart.getQuantity());
						shopProductInfo.setUpdateDate(new Date());
						productInfoDao.saveOrUpdateObject(shopProductInfo);//修改商品库存
					}
					ordersDao.saveOrUpdateObject(orders);
					//保存订单日志
					OrdersOPLog ordersOPLog = new OrdersOPLog();
					ordersOPLog.setOrdersOPLogId(null);
					ordersOPLog.setOrdersId(orders.getOrdersId());
					ordersOPLog.setOrdersNo(orders.getOrdersNo());
					ordersOPLog.setOperatorTime(new Date());
					ordersOPLog.setOrdersOperateState(1);
					ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
					ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
				/*	if(son!=null){//子账号
						ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
					}else */
					if(customer.getType()!=2){//个人账号
						ordersOPLog.setOperatorName(customer.getLoginName());//操作人name
					}else{
						String on="";
						if(!"".equals(shopInfo2.getCompanyName())){
							on=shopInfo2.getCompanyName();
						}else{
							on=shopInfo2.getCustomerName();
						}
						ordersOPLog.setOperatorName(on);
					}
					ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
				}
			}
		}
		//修改优惠券状态
		if(Utils.objectIsNotEmpty(order.getDiscountCouponId())){
			String updateCouponSql = " update shop_customer_discountcoupon set useStatus= 1 where customerDiscountCouponID="+order.getDiscountCouponId();
			customerdiscountcouponDao.updateObject(updateCouponSql);
		}
		map.put("consignee", Escape.escape(customerAcceptAddress.getConsignee()));
		map.put("address", Escape.escape(customerAcceptAddress.getAddress()));
		map.put("phone", customerAcceptAddress.getPhone());
		map.put("mobilePhone", customerAcceptAddress.getMobilePhone());
		map.put("postCode", customerAcceptAddress.getPostcode());
		map.put("totalOrdersNo", totalOrdersNo);
		map.put("ordersNoAll", ordersNoAll.substring(0, ordersNoAll.length()-1));
		map.put("payAmount", payAmount);
		return map;
	}
	/**
	 * 计算商品会员价
	 */
	public void getCustomerProductPrice(Customer customer,BigDecimal marketPrice,BigDecimal salesPrice){
		if(customer!=null){
			String sql = "select br.levelDiscountValue as levelDiscountValue, br.buyersLevel as buyersLevel from BuyersRecord br "
					+ "where br.customerId="+customer.getCustomerId()
					+ "order by br.optionDate desc";
			List<Map> listbs= buyersRecordDao.findListMapByHql(sql);
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
	 * 直接购买--------生成订单及保存相关表和清空cookie中相关数据
	 * @param orderJson订单的相关信息
	 * @param shoppingCartIds购物车的ids
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Map<String,Object>  saveOrUpdateOrdersBuy(Orders order,Customer customer,CustomerAcceptAddress custAddress,Integer productId,Integer quantity,Sonaccount son,ShopInfo shopInfo2){
		String totalOrdersNo = SerialNumberUtil.OrderSnNumber();//总订单号
		Orders orders = new Orders();
		orders=order;
		orders.setTotalOrdersNo(totalOrdersNo);
		Map<String,Object> map = new HashMap<String ,Object>();
		ProductInfo shopProductInfo =productInfoDao.get(" where o.productId="+productId);//商品信息
		//团购商品信息
		TuanProduct tuanProduct =  tuanProductDao.get(" where o.productId="+productId);
		//查询收货地址
		CustomerAcceptAddress customerAcceptAddress = customerAcceptAddressDao.get("where o.customerAcceptAddressId ="+custAddress.getCustomerAcceptAddressId());
		orders.setShopInfoId(shopProductInfo.getShopInfoId());//前台传值
		orders.setAddress(customerAcceptAddress.getAddress());
		//orders.setBestSendDate();
		orders.setCity(customerAcceptAddress.getCity());
		orders.setComments(null);//订单附言
		orders.setConsignee(customerAcceptAddress.getConsignee());//收货人
		orders.setCountry(customerAcceptAddress.getAreaCounty());
		orders.setCreateTime(new Date());
		orders.setUpdateTime(new Date());
		orders.setCustomerId(customerAcceptAddress.getCustomerId());
		orders.setEmail(customerAcceptAddress.getEmail());
		//orders.setEmail("sb@qq.com");
		orders.setFlagContractor(null);//标志建筑
		orders.setIsLocked(0);//锁定操作
		orders.setMobilePhone(customerAcceptAddress.getMobilePhone());
		orders.setOosOperator(null);//缺货处理(先不做)
		orders.setOrdersRemark(null);//订单备注（客服留言）
		orders.setOrdersState(0);//0、未处理；1、订单确认(含义：正在处理)；3、收款确认(含义：等待付款)；4、正在配货(含义：等待发货)；5、已经发货；6、收货确认(含义：已经完成)；7、确认作废(含义：取消订单)；
		orders.setSettlementStatus(0);//0未付款
		orders.setSettlementStatusForSellers(0);//0未付款
		orders.setPayMode(1);//支付宝
		//orders.setFinalAmount(Double.valueOf(test.getSubPrice()));//最终金额
		orders.setPhone(customerAcceptAddress.getPhone());
		orders.setPostcode(customerAcceptAddress.getPostcode());//邮政编码
		//orders.setTotalAmount(Double.valueOf(test.getSubtotal()));
		orders.setRegionLocation(customerAcceptAddress.getRegionLocation());
//		orders.setSendType(1);//快递公司
		//orders.setFreight(Double.valueOf(test.getShipping()));//邮费
		SerialNumberUtil serialNumberUtil = new SerialNumberUtil();
		orders.setOrdersNo(serialNumberUtil.OrderSnNumber());//订单号
		orders=ordersDao.saveOrUpdateObject(orders);//保存订单
		ShopInfo shopInfo = shopInfoDao.get(" where o.shopInfoId="+shopProductInfo.getShopInfoId());
		Brand brand = brandDao.get(" where o.brandId="+shopProductInfo.getBrandId());
		//订单日志
		OrdersList ordersList = new OrdersList();
		ordersList.setOrderDetailId(null);
		ordersList.setOrdersNo(orders.getOrdersNo());
		ordersList.setOrdersId(orders.getOrdersId());
		ordersList.setProductId(productId);
		ordersList.setProductFullName(shopProductInfo.getProductFullName());
		ordersList.setMarketPrice(shopProductInfo.getMarketPrice());
		ordersList.setMemberPrice(shopProductInfo.getMemberPrice());
		ordersList.setSalesPrice(tuanProduct.getPrice());
		ordersList.setMemberPrice(tuanProduct.getPrice());//团购价 
		ordersList.setCount(quantity);
		ordersList.setCostPrice(shopProductInfo.getCostPrice());
		ordersList.setBuyType(1);//1商城
		if(brand!=null&&brand.getBrandId()!=null){
			ordersList.setBrandName(brand.getBrandName());
			ordersList.setBrandId(brand.getBrandId());
		}
		if(shopInfo!=null){
			ordersList.setShopInfoId(shopInfo.getShopInfoId());
			ordersList.setShopName(shopInfo.getShopName());
		}
		ordersList.setProductCode(shopProductInfo.getProductCode());
		ordersList.setLogoImage(shopProductInfo.getLogoImg());
		ordersList.setFreightPrice(shopProductInfo.getFreightPrice());
		ordersList.setCustomerId(orders.getCustomerId());
		ordersList.setStockUpDate(String.valueOf(shopProductInfo.getStockUpDate()));
		ordersListDao.saveOrUpdateObject(ordersList);//保存订单明细
		//保存订单日志
		OrdersOPLog ordersOPLog = new OrdersOPLog();
		ordersOPLog.setOrdersOPLogId(null);
		ordersOPLog.setOrdersId(orders.getOrdersId());
		ordersOPLog.setOrdersNo(orders.getOrdersNo());
		ordersOPLog.setOperatorTime(new Date());
		ordersOPLog.setOrdersOperateState(1);
		ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
		ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
		/*if(son!=null){
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
		}else{
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
		}*/
		ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
		ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
		//当抵扣金额不为null且抵扣金额大于0时，保存用的虚拟金币记录
		if(orders.getUserCoin()!=null&&orders.getUserCoin().compareTo(new BigDecimal(0))>0){
			//获取当前用户最新的虚拟币记录
			MallCoin oldMallCoin = mallCoinDao.get("where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc ,o.virtualCoinId desc");
			MallCoin mallCoin = new MallCoin();
			BigDecimal remainingNumber = oldMallCoin.getRemainingNumber();
			remainingNumber = remainingNumber.subtract(orders.getUserCoin());//去掉当前使用的数量
			mallCoin.setFrozenNumber(orders.getUserCoin());//当前订单使用的数量，冻结起来
			mallCoin.setRemainingNumber(remainingNumber);//余额
			mallCoin.setOrdersId(orders.getOrdersId());
			mallCoin.setCustomerId(customer.getCustomerId());
			mallCoin.setOrdersNo(orders.getOrdersNo());
			mallCoin.setOperatorTime(new Date());//操作时间
			mallCoin.setSerialNumber(serialNumberUtil.VirtualCoinNumber());//虚拟金币流水号
			mallCoin.setRemarks(customer.getLoginName()+"购买商品，生成订单号为："+orders.getOrdersNo()+"冻结商城币"+orders.getUserCoin()+"个！");
			mallCoin.setTradeTime(new Date());//创建时间
			mallCoin.setTransactionNumber(orders.getUserCoin());//获取的虚拟金币
			mallCoin.setType(2);//1.收入2.支出
			mallCoinDao.saveOrUpdateObject(mallCoin);
		}
		shopProductInfo.setStoreNumber(shopProductInfo.getStoreNumber()-quantity);
		shopProductInfo.setUpdateDate(new Date());
		productInfoDao.saveOrUpdateObject(shopProductInfo);//修改商品库存
		//修改团购人数
		tuanProduct.setBought(tuanProduct.getBought()+1);
		tuanProductDao.saveOrUpdateObject(tuanProduct);
		map.put("ordersNo", orders.getOrdersNo());
		map.put("consignee", Escape.escape(customerAcceptAddress.getConsignee()));
		map.put("address", Escape.escape(customerAcceptAddress.getAddress()));
		map.put("phone", customerAcceptAddress.getPhone());
		map.put("mobilePhone", customerAcceptAddress.getMobilePhone());
		map.put("postCode", customerAcceptAddress.getPostcode());
		map.put("payAmount", orders.getFinalAmount());
		return map; 
	}
	/**
	 * 支付成功后修改状态和产品的销量,收支明细
	 * @param ordersNo
	 */
	@SuppressWarnings("rawtypes")
	public void saveOrUpdateToPaySuccess(String ordersNo,Customer customer2,ShopInfo shopInfo2,Sonaccount son,String dealId){
		List<Orders> listOrders = ordersDao.findObjects(" where o.ordersId in ( "+ordersNo+" ) ");
		LineofcreditItem li = new LineofcreditItem();//额度明细
		for(Orders order:listOrders){
			//订单的更改
			//Orders order = new Orders();
			//order=(Orders) ordersDao.get("where o.ordersNo='"+ordersNo+"'");
			Customer customer = (Customer) customerDao.get("where o.customerId="+order.getCustomerId());
			//Integer num = ordersDao.getCount(" where o.totalOrdersNo='"+order.getTotalOrdersNo()+"'");//查询当前订单的父订单号有下有子订单数量
			//order.setOrdersState(1);//已付款
			order.setSettlementStatus(1);//付款成功(含义：已经付款)；
			order.setDealId(dealId);//支付交易号（支付宝，快钱等）
			order.setUpdateTime(new Date());
			//订单确认支付之后，判断用户是否使用虚拟金币抵扣，有抵扣更新冻结
			if(order.getUserCoin()!=null&&order.getUserCoin().compareTo(new BigDecimal(0))>0){
				//获取当前用户最新的虚拟币记录
				MallCoin oldMallCoin = mallCoinDao.get("where o.customerId="+customer.getCustomerId()+" and o.ordersNo='"+order.getOrdersNo()+"'");
				if(oldMallCoin!=null&&oldMallCoin.getFrozenNumber()!=null){
					oldMallCoin.setFrozenNumber(new BigDecimal(0));
					oldMallCoin.setOperatorTime(new Date());
					mallCoinDao.saveOrUpdateObject(oldMallCoin);
				}
				SupplierMallCoinDetail supplierMallCoinDetail2 =  new SupplierMallCoinDetail();
				supplierMallCoinDetail2.setSupplierId(shopInfo2.getShopInfoId());
				supplierMallCoinDetail2.setSupplierLoginName(shopInfo2.getCustomerName());
				supplierMallCoinDetail2.setOrdersId(order.getOrdersId());
				supplierMallCoinDetail2.setOrdersNo(order.getOrdersNo());
				supplierMallCoinDetail2.setTradingTime(new Date());
				supplierMallCoinDetail2.setType(1);
				supplierMallCoinDetail2.setTradMallCoin(order.getUserCoin());//收入的商城币
				SupplierMallCoinDetail supplierMallCoinDetail3 = supplierMallCoinDetailDao.get("where o.supplierId="+shopInfo2.getShopInfoId()+" order by o.supplierMallCoinDetailId desc limit 1");
				if(supplierMallCoinDetail3==null){
					supplierMallCoinDetail2.setTotalInPut(order.getUserCoin());
					supplierMallCoinDetail2.setTotalOutPut(new BigDecimal(0));
				}else{
					BigDecimal totalInPut = new BigDecimal(0);
					totalInPut=supplierMallCoinDetail3.getTotalInPut().add(order.getUserCoin());
					supplierMallCoinDetail2.setTotalInPut(totalInPut);
					supplierMallCoinDetail2.setTotalOutPut(supplierMallCoinDetail3.getTotalOutPut());
				}
				supplierMallCoinDetailDao.saveObject(supplierMallCoinDetail2);
			}
			if(customer.getType()!=3){
				LineofcreditItem item = (LineofcreditItem)lineofcreditItemDao.get("where o.ordersNo='"+order.getOrdersNo()+"'");
				if(item!=null){
					if(li.getLineOfCreditId()==null){
						String hql2 = "select o.customerId as customerId, o.ordersId as ordersId,o.ordersNo as ordersNo, o.serialNumber as serialNumber, o.type as type, o.transactionNumber as transactionNumber, "
								+ "o.remainingNumber as remainingNumber, o.frozenNumber as frozenNumber, o.tradeTime as tradeTime, o.operatorTime as operatorTime,"
								+ "o.remarks as remarks from LineofcreditItem o where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc,o.lineOfCreditId desc";
						List<Map> lmap = lineofcreditItemDao.findListMapByHql(hql2);
						if(lmap.size()>0&&lmap!=null){
							BigDecimal remainingNumber = new BigDecimal(lmap.get(0).get("remainingNumber").toString());//余额[最新一条记录的余额]
							li.setCustomerId(item.getCustomerId());
							li.setOrdersId(item.getOrdersId());//订单ID
							li.setOrdersNo(item.getOrdersNo());//订单号
							li.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());//随机流水号
							li.setType(2);//子会员类型
							li.setTransactionNumber(new BigDecimal(0));//已使用额度值
							li.setRemainingNumber(remainingNumber.add(item.getFrozenNumber()));//余额
							li.setFrozenNumber(new BigDecimal(0));//冻结额度值
							li.setTradeTime(item.getTradeTime());//交易时间
							li.setOperatorTime(new Date());//操作时间
							li.setRemarks("订单支付成功解冻额度："+item.getFrozenNumber()+"元");
							li =(LineofcreditItem) lineofcreditItemDao.saveOrUpdateObject(li);
						}
					}else{
						li.setCustomerId(item.getCustomerId());
						li.setOrdersId(item.getOrdersId());//订单ID
						li.setOrdersNo(item.getOrdersNo());//订单号
						li.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());//随机流水号
						li.setType(2);//子会员类型
						li.setTransactionNumber(new BigDecimal(0));//已使用额度值
						li.setRemainingNumber(li.getRemainingNumber().add(item.getFrozenNumber()));//余额
						li.setFrozenNumber(new BigDecimal(0));//冻结额度值
						li.setTradeTime(item.getTradeTime());//交易时间
						li.setOperatorTime(new Date());//操作时间
						li.setRemarks("订单支付成功解冻额度："+item.getFrozenNumber()+"元");
						li =(LineofcreditItem) lineofcreditItemDao.saveOrUpdateObject(li);
					}
				}
			}
			ordersDao.saveOrUpdateObject(order);
			//保存订单日志
			OrdersOPLog ordersOPLog = new OrdersOPLog();
			ordersOPLog.setOrdersId(order.getOrdersId());
			ordersOPLog.setOrdersNo(order.getOrdersNo());
			ordersOPLog.setOperatorTime(new Date());
			ordersOPLog.setOrdersOperateState(2);
			ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
			ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
			if(shopInfo2!=null){
				String companyName= shopInfo2.getCompanyName();//公司名称
				if(Utils.objectIsEmpty(companyName)){
					companyName = customer.getLoginName();
				}
				ordersOPLog.setOperatorName(companyName);//操作人name
			}else{
				ordersOPLog.setOperatorName(customer.getLoginName());//操作人name
			}
			ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
			//商品的更改
			OrdersList ordersList = ordersListDao.get(" where o.ordersNo='"+order.getOrdersNo()+"'");
			ProductInfo productInfo = new ProductInfo();
			productInfo = productInfoDao.get(" where o.productId="+ordersList.getProductId());
			productInfo.setTotalSales(productInfo.getTotalSales()+ordersList.getCount());
			productInfo.setUpdateDate(new Date());
			productInfoDao.saveOrUpdateObject(productInfo);
			//收支明细(收款人一条记录，付款人一条记录)
			//根据订单查找付款人
			String hql ="select b.customerId as customerId , b.customerName as customerName from Orders a ,ShopInfo b where b.shopInfoId=a.shopInfoId and a.ordersNo='"+order.getOrdersNo()+"'";
			List<Map> payer=ordersDao.findListMapByHql(hql);
			if(payer!=null&&payer.size()>0){
				Map mapPayer = payer.get(0);
				IncomePayDetail payerDetail = new IncomePayDetail();//付款人明细
				payerDetail.setPayerId(customer.getCustomerId());
				payerDetail.setPayerName(customer.getLoginName());
				payerDetail.setOutlay(order.getFinalAmount());
				payerDetail.setTradeTime(new Date());
				payerDetail.setSerialNumber(SerialNumberUtil.PaymentSnNumber());
				payerDetail.setIncomeExpensesType(2);//2.会员支付
				incomePayDetailDao.saveOrUpdateObject(payerDetail);
				//System.out.println(payerDetail.getDetailId());
				IncomePayDetail payeeDetail = new IncomePayDetail();//收款人明细
				payeeDetail.setPayeeId(Integer.parseInt(mapPayer.get("customerId").toString()));
				payeeDetail.setPayeeName(customer.getLoginName());
				payeeDetail.setIncome(order.getFinalAmount());
				payeeDetail.setTradeTime(new Date());
				payeeDetail.setSerialNumber(SerialNumberUtil.PaymentSnNumber());
				payeeDetail.setIncomeExpensesType(2);//2.会员支付
				incomePayDetailDao.saveOrUpdateObject(payeeDetail);
				//System.out.println(payeeDetail.getDetailId());
			}
		}
	}
	/**
	 * 支付成功后修改状态和产品的销量,收支明细
	 * @param ordersNo
	 */
	@SuppressWarnings({ "unchecked"})
	public void saveOrUpdateToPaySuccess(String tradeNo,String ordersNo){
		//订单的更改
		Orders order = new Orders();
		String[] orderNos = ordersNo.split(",");
		for(int i=0;i<orderNos.length;i++){
			order=(Orders) ordersDao.get("where o.ordersNo="+orderNos[i]);
			//order=(Orders) ordersDao.get("where o.totalOrdersNo='"+totalOrdersNo+"' and o.ordersNo='"+orderNos[i]+"'");
			if(Utils.objectIsNotEmpty(order)&&order.getOrdersState()==0){
				Customer customer = (Customer) customerDao.get("where o.customerId="+order.getCustomerId());
				order.setSettlementStatus(1);//付款成功(含义：已经付款)；
				order.setDealId(tradeNo);
				//order.setUpdateTime(new Date());
				shoppingCartOrderDao.saveOrUpdateObject(order);
				//保存订单日志
				OrdersOPLog ordersOPLog = new OrdersOPLog();
				ordersOPLog.setOrdersId(order.getOrdersId());
				ordersOPLog.setOrdersNo(order.getOrdersNo());
				ordersOPLog.setOperatorTime(new Date());
				ordersOPLog.setOrdersOperateState(2);
				ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
				ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
				ordersOPLog.setOperatorName(customer.getLoginName());//操作人name
				ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
				//商品的更改
				OrdersList ordersList = (OrdersList) ordersListDao.get(" where o.ordersNo='"+order.getOrdersNo()+"'");
				ProductInfo productInfo = new ProductInfo();
				productInfo = (ProductInfo) productInfoDao.get(" where o.productId="+ordersList.getProductId());
				productInfo.setTotalSales(productInfo.getTotalSales()+ordersList.getCount());
				productInfo.setUpdateDate(new Date());
				productInfoDao.saveOrUpdateObject(productInfo);
				
				//收支明细(收款人一条记录，付款人一条记录)
				//根据订单查找付款人
				String hql ="select b.customerId as customerId , b.customerName as customerName from Orders a ,ShopInfo b where b.shopInfoId=a.shopInfoId and a.ordersNo='"+order.getOrdersNo()+"'";
				List<Map> payer=ordersDao.findListMapByHql(hql);
				if(payer!=null&&payer.size()>0){
					Map mapPayer = payer.get(0);
					IncomePayDetail payerDetail = new IncomePayDetail();//付款人明细
					payerDetail.setPayerId(customer.getCustomerId());
					payerDetail.setPayerName(customer.getLoginName());
					payerDetail.setOutlay(order.getFinalAmount());
					payerDetail.setTradeTime(new Date());
					payerDetail.setSerialNumber(SerialNumberUtil.PaymentSnNumber());
					payerDetail.setIncomeExpensesType(2);//2.会员支付
					incomePayDetailDao.saveOrUpdateObject(payerDetail);
					//System.out.println(payerDetail.getDetailId());
					IncomePayDetail payeeDetail = new IncomePayDetail();//收款人明细
					payeeDetail.setPayeeId(Integer.parseInt(mapPayer.get("customerId").toString()));
					payeeDetail.setPayeeName(customer.getLoginName());
					payeeDetail.setIncome(order.getFinalAmount());
					payeeDetail.setTradeTime(new Date());
					payeeDetail.setSerialNumber(SerialNumberUtil.PaymentSnNumber());
					payeeDetail.setIncomeExpensesType(2);//2.会员支付
					incomePayDetailDao.saveOrUpdateObject(payeeDetail);
					//System.out.println(payeeDetail.getDetailId());
				}
			}
		}
	}
	public void setProductInfoDao(IProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}
	public void setCustomerAcceptAddressDao(
			ICustomerAcceptAddressDao customerAcceptAddressDao) {
		this.customerAcceptAddressDao = customerAcceptAddressDao;
	}
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	public void setOrdersListDao(IOrdersListDao ordersListDao) {
		this.ordersListDao = ordersListDao;
	}
	public void setOrdersOPLogDao(IOrdersOPLogDao ordersOPLogDao) {
		this.ordersOPLogDao = ordersOPLogDao;
	}
	public void setIncomePayDetailDao(IIncomePayDetailDao incomePayDetailDao) {
		this.incomePayDetailDao = incomePayDetailDao;
	}
	public void setCustomerHaveCouponDao(
			ICustomerHaveCouponDao customerHaveCouponDao) {
		this.customerHaveCouponDao = customerHaveCouponDao;
	}
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	public void setVirtualCoinDao(IVirtualCoinDao virtualCoinDao) {
		this.virtualCoinDao = virtualCoinDao;
	}
	public void setShopInfoDao(IShopInfoDao shopInfoDao) {
		this.shopInfoDao = shopInfoDao;
	}
	public void setBrandDao(IBrandDao brandDao) {
		this.brandDao = brandDao;
	}
	public void setLineofcreditItemDao(ILineofcreditItemDao lineofcreditItemDao) {
		this.lineofcreditItemDao = lineofcreditItemDao;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public void setBuyersRecordDao(IBuyersRecordDao buyersRecordDao) {
		this.buyersRecordDao = buyersRecordDao;
	}
	public void setMemberShipDao(IMemberShipDao memberShipDao) {
		this.memberShipDao = memberShipDao;
	}
	public void setMallCoinDao(IMallCoinDao mallCoinDao) {
		this.mallCoinDao = mallCoinDao;
	}
	public void setSupplierMallCoinDetailDao(
			ISupplierMallCoinDetailDao supplierMallCoinDetailDao) {
		this.supplierMallCoinDetailDao = supplierMallCoinDetailDao;
	}
	public void setCustomerdiscountcouponDao(
			ICustomerdiscountcouponDao customerdiscountcouponDao) {
		this.customerdiscountcouponDao = customerdiscountcouponDao;
	}
	public void setTuanProductDao(ITuanProductDao tuanProductDao) {
		this.tuanProductDao = tuanProductDao;
	}
}
