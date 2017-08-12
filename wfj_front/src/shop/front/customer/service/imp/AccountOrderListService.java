package shop.front.customer.service.imp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import shop.customer.dao.IIncomePayDetailDao;
import shop.customer.dao.IMallCoinDao;
import shop.customer.dao.IVirtualCoinDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.MallCoin;
import shop.customer.pojo.Sonaccount;
import shop.front.customer.service.IAccountOrderListService;
import shop.order.dao.IOrdersDao;
import shop.order.dao.IOrdersListDao;
import shop.order.dao.IOrdersOPLogDao;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.pojo.OrdersOPLog;
import shop.product.dao.IProductInfoDao;
import shop.product.pojo.ProductInfo;
import shop.store.dao.ICustomerHaveCouponDao;
import shop.store.pojo.ShopInfo;
import util.other.SerialNumberUtil;
import util.service.BaseService;
/**
 * AccountOrderListService - 用户订单service
 */
public class AccountOrderListService extends BaseService<Orders> implements IAccountOrderListService{
	/**商品Dao**/
    private IProductInfoDao productInfoDao;
    /**订单DAo**/
    private IOrdersDao ordersDao;
    /**订单日志**/
    private IOrdersOPLogDao ordersOPLogDao;
    /**订单明细**/
    private IOrdersListDao ordersListDao;
    /**收支明细service**/
    @SuppressWarnings("unused")
	private IIncomePayDetailDao incomePayDetailDao;
    /**客户已领优惠券表**/
    @SuppressWarnings("unused")
	private ICustomerHaveCouponDao customerHaveCouponDao;
	/**虚拟金币DAO**/
    private IVirtualCoinDao virtualCoinDao; 
    private IMallCoinDao mallCoinDao; 
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.baseDao=this.ordersDao = ordersDao;
	}
	/**
	 * 用户取消订单
	 * @param ordersId
	 */
	public void saveOrUpdateCancelOrder(Orders order,Customer customer,ShopInfo shopInfo2,Sonaccount son){
		try{
			Orders orders = new Orders();
			orders = ordersDao.get(" where o.ordersId="+order.getOrdersId()+" and o.customerId="+customer.getCustomerId());
			if(orders!=null){
			/*
			 * 1、修改原始数据：类型（4.交易作废）
			   2、插入一条新纪录：类型（3.去掉订单，并且解冻冻结的天海币【动作为：修改之前冻结金额为0，在于【把冻结金额累加在余额字段上】）
			 * */
			//找到当前冻结虚拟金币记录,将冻结的数量归零
				MallCoin mallCoin = mallCoinDao.get(" where o.type=2 and  o.ordersNo='"+orders.getOrdersNo()+"' and o.ordersId="+orders.getOrdersId());
				if(mallCoin!=null&&mallCoin.getFrozenNumber()!=null){
					//获取当前用户最新的虚拟币记录
					MallCoin oldMallCoin = mallCoinDao.get("where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc ,o.mallCoinId desc");
					BigDecimal remainingNumber = oldMallCoin.getRemainingNumber();
					MallCoin newMallCoin = new MallCoin();
					remainingNumber = remainingNumber.add(mallCoin.getFrozenNumber());//加上当前冻结的数量
					newMallCoin.setFrozenNumber(new BigDecimal(0));//当前订单使用的数量，冻结起来
					newMallCoin.setRemainingNumber(remainingNumber);//余额
					newMallCoin.setOrdersId(orders.getOrdersId());
					newMallCoin.setCustomerId(customer.getCustomerId());
					newMallCoin.setOrdersNo(orders.getOrdersNo());
					newMallCoin.setOperatorTime(new Date());//操作时间
					newMallCoin.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());//虚拟金币流水号
					newMallCoin.setRemarks(customer.getLoginName()+"取消订单号为："+orders.getOrdersNo()+"，解除冻结商城币"+orders.getUserCoin()+"个！");
					newMallCoin.setTradeTime(new Date());//创建时间
					newMallCoin.setTransactionNumber(mallCoin.getFrozenNumber());//获取的虚拟金币
					newMallCoin.setType(3);//1.收入2.支出3.取消订单（返回天海币）4.交易作废
					mallCoinDao.saveOrUpdateObject(newMallCoin);
				}
			}
			if(orders.getOrdersId()!=null){
				//生成新的订单号
				orders.setTotalOrdersNo(SerialNumberUtil.OrderSnNumber());
				orders.setUpdateTime(new Date());
				orders.setOrdersState(6);//确认作废(含义：取消订单)；
				ordersDao.saveOrUpdateObject(orders);
				//修改商品
				List<OrdersList> listOrdersDetail = ordersListDao.findObjects(" where o.ordersId="+orders.getOrdersId());
				for(OrdersList ordersList:listOrdersDetail){
					ProductInfo productInfo = new ProductInfo();
					productInfo=productInfoDao.get(" where o.productId="+ordersList.getProductId());
					productInfo.setStoreNumber(productInfo.getStoreNumber()+ordersList.getCount());
					productInfo.setUpdateDate(new Date());
					productInfoDao.saveOrUpdateObject(productInfo);
				}
				//订单日志
				OrdersOPLog ordersOPLog = new OrdersOPLog();
				ordersOPLog.setOrdersId(orders.getOrdersId());
				ordersOPLog.setOrdersNo(orders.getOrdersNo());
				ordersOPLog.setOperatorTime(new Date());
				ordersOPLog.setOrdersOperateState(6);//取消订单
				ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
				ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
				/*if(son!=null){
					ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
				}else{
					ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
				}*/
				//ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
				ordersOPLog.setOperatorName(customer.getLoginName());
				ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 确认收货
	 * @param ordersId
	 * @param customer
	 */
	public void saveConfirmOrder(Integer ordersId,Customer customer,ShopInfo shopInfo2,Sonaccount son){
		Orders orders = new Orders();
		orders = ordersDao.getRandom(" where o.ordersId="+ordersId+" and o.customerId="+customer.getCustomerId());
		orders.setOrdersState(5);//确认收货；
		orders.setUpdateTime(new Date());
		ordersDao.saveOrUpdateObject(orders);
		//订单日志
		OrdersOPLog ordersOPLog = new OrdersOPLog();
		ordersOPLog.setOrdersId(orders.getOrdersId());
		ordersOPLog.setOrdersNo(orders.getOrdersNo());
		ordersOPLog.setOperatorTime(new Date());
		ordersOPLog.setOrdersOperateState(5);//确认收货；
		ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
		ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
		/*if(son!=null){
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
		}else{
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
		}*/
		ordersOPLog.setOperatorName(customer.getLoginName());//操作人name
		ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
	}
	public void setOrdersListDao(IOrdersListDao ordersListDao) {
		this.ordersListDao = ordersListDao;
	}
	public void setIncomePayDetailDao(IIncomePayDetailDao incomePayDetailDao) {
		this.incomePayDetailDao = incomePayDetailDao;
	}
	public void setCustomerHaveCouponDao(
			ICustomerHaveCouponDao customerHaveCouponDao) {
		this.customerHaveCouponDao = customerHaveCouponDao;
	}
	public void setProductInfoDao(IProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}
	public void setOrdersOPLogDao(IOrdersOPLogDao ordersOPLogDao) {
		this.ordersOPLogDao = ordersOPLogDao;
	}
	public void setVirtualCoinDao(IVirtualCoinDao virtualCoinDao) {
		this.virtualCoinDao = virtualCoinDao;
	}
	public void setMallCoinDao(IMallCoinDao mallCoinDao) {
		this.mallCoinDao = mallCoinDao;
	}
}
