package shop.front.customer.service.imp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.customer.dao.IVirtualCoinDao;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.ICustomerService;
import shop.customer.service.IEvaluateGoodsService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.pojo.OrdersOPLog;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.other.Utils;
/**
 * 自动评价定时器Service
 * @author mqr
 *
 */
public class AutoCommentService {
	/**商品Service**/
    private IProductInfoService productInfoService;
    /**订单Service**/
    private IOrdersService ordersService;
    /**店铺Service**/
    private IShopInfoService shopInfoService;
    /**订单日志Service**/
    private IOrdersOPLogService ordersOPLogService;
    /**订单明细Service**/
    private IOrdersListService ordersListService;
    /**订单评价Service**/
    private IEvaluateGoodsService evaluateGoodsService;
    /**用户Service**/
    private ICustomerService customerService;
    /**虚拟金币DAO**/
    private IVirtualCoinDao virtualCoinDao ;
    /*********************************end****************************************/
    /**
	 * 对订单中商品的自动评价
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	public void saveAutoComment(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date d=new Date();//当前时间
		String date = sdf.format(new Date(d.getTime() - 7 * 24 * 60 * 60 * 1000));//七天前的时间
		//查询订单信息
		String hql = "select a.ordersId as ordersId,a.customerId as customerId,a.shopInfoId as shopInfoId ,c.loginName as loginName,a.updateTime as updateTime from Orders a ,Customer c  where  a.customerId=c.customerId " +
				" and  UNIX_TIMESTAMP(a.updateTime)<=UNIX_TIMESTAMP('"+date+"') and a.ordersState=5";
		//List<OrdersList> orderList = ordersListService.findObjects("where UNIX_TIMESTAMP(o.operatorTime)>=UNIX_TIMESTAMP('"+date+"') and o.ordersState=5");
		List<Map> listMap = ordersService.findListMapByHql(hql);
		if(Utils.collectionIsNotEmpty(listMap)){
			for(Map map:listMap){//o为订单
				Integer ordersId=Integer.parseInt(String.valueOf(map.get("ordersId")));//订单id
				//Integer count = evaluateGoodsService.getCount(" where o.ordersId="+ordersId);//评价
				//用户信息
				//Customer customer = (Customer) customerService.getObjectByParams(" where o.customerId="+o.getCustomerId());
				//店铺信息
				Integer shopInfoId = Integer.parseInt(String.valueOf(map.get("shopInfoId")));
				ShopInfo shopInfo =  (ShopInfo) shopInfoService.getObjectById(shopInfoId + "");
				//总天海币数
				//if(count==0){//没有评价
					String updateTime =sdf.format(map.get("updateTime"));//订单修改时间
					//如果订单修改时间   与   七天前的时间相同 则自动评价（好评）
					//if(updateTime.equals(date)){
						//查询订单明细
						List<OrdersList> olList = ordersListService.findObjects(" where o.ordersId="+ordersId);
						Orders orders = (Orders) ordersService.getObjectByParams("where o.ordersId="+ordersId);
						if(olList!=null&&olList.size()>0){
							for(OrdersList ol:olList){
								EvaluateGoods e = new EvaluateGoods();
								e.setOrdersId(ordersId);
								e.setOrderDetailId(ol.getOrderDetailId());
								e.setProductId(ol.getProductId());
								e.setOrdersNo(ol.getOrdersNo());
								e.setProductFullName(ol.getProductFullName());
								e.setSalesPrice(ol.getSalesPrice());
								e.setLevel(1);//好评
								e.setContent("");
								e.setIsAnonymous(0);//非匿名评价
								e.setCreateTime(new Date());
								e.setShopInfoId(shopInfo.getShopInfoId());
								e.setShopName(shopInfo.getShopName());
								e.setAppraiserId(Integer.parseInt(String.valueOf(map.get("customerId"))));
								e.setAppraiserName(String.valueOf(map.get("loginName")));
								e.setAcceptAppraiserId(shopInfo.getCustomerId());
								e.setAcceptAppraiserName(shopInfo.getCustomerName());
								e.setEvaluateState(0);
								e.setExplain("");
								e.setBothState(1);
								e.setShowTime(new Date());
								e.setEvaluateUserType(1);
								evaluateGoodsService.saveOrUpdateObject(e); //添加评价
								//记录订单日志
								OrdersOPLog oplog = new OrdersOPLog();
								oplog.setOrdersId(Integer.parseInt(String.valueOf(map.get("ordersId"))));
								oplog.setOoperatorId(0);//0代表系统
								oplog.setOperatorName("系统");
								oplog.setOperatorTime(new Date());
								oplog.setOoperatorSource("1");//好评
								oplog.setOrdersOperateState(9);
								ordersOPLogService.saveOrUpdateObject(oplog);
							}
							orders.setOrdersState(9); //更新订单状态
							orders.setUpdateTime(new Date());
							ordersService.saveOrUpdateObject(orders);
					}
			}
		}
	}
	//setter getter
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setVirtualCoinDao(IVirtualCoinDao virtualCoinDao) {
		this.virtualCoinDao = virtualCoinDao;
	}
}
