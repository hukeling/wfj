package shop.front.customer.service.imp;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.customer.dao.IEvaluateGoodsDao;
import shop.customer.dao.IMallCoinDao;
import shop.customer.dao.imp.EvaluateGoodsDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.pojo.MallCoin;
import shop.customer.pojo.Sonaccount;
import shop.front.customer.service.IAccountCommentService;
import shop.order.dao.IOrdersDao;
import shop.order.dao.IOrdersListDao;
import shop.order.dao.IOrdersOPLogDao;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.pojo.OrdersOPLog;
import shop.product.dao.IProductInfoDao;
import shop.store.dao.IShopInfoDao;
import shop.store.dao.ISupplierMallCoinDetailDao;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.SupplierMallCoinDetail;
import util.other.SerialNumberUtil;
import util.other.Utils;
import util.service.BaseService;
/**
 * AccountOrderListService - 用户评价订单service
 */
public class AccountCommentService extends BaseService<EvaluateGoods> implements IAccountCommentService{
	/**商品评价DAO**/
	private IEvaluateGoodsDao evaluateGoodsDao;
	/**店铺DAO**/
	private IShopInfoDao shopInfoDao;
	/**订单明细DAO**/
	private IOrdersListDao ordersListDao;
	/**订单DAO**/
	private IOrdersDao ordersDao;
	/**订单日志DAO**/
	private IOrdersOPLogDao ordersOPLogDao;
    private IMallCoinDao mallCoinDao ;
    /**商品详情DAO**/
    private IProductInfoDao productInfoDao;
    
    /**商城币收支明细dao**/
    private ISupplierMallCoinDetailDao supplierMallCoinDetailDao;
    /**********************************end*****************************************/
    public void setEvaluateGoodsDao(EvaluateGoodsDao evaluateGoodsDao) {
		this.baseDao = this.evaluateGoodsDao = evaluateGoodsDao;
	}
	/**
	 * 添加评价信息
	 * @param order 订单信息
	 * @param array 评价信息
	 * @param customer 用户
	 * @return 
	 */
	public Boolean saveComment(Orders order,JSONArray array,Customer customer,ServletContext servletContext,Sonaccount son,ShopInfo si){
		Boolean bool=true;
		
		try{
			//店铺信息
			int shopInfoId = order.getShopInfoId();
			ShopInfo shopInfo =  (ShopInfo) shopInfoDao.getObjectById(shopInfoId + "");
			BigDecimal totalCoid = order.getVirtualCoin();
			BigDecimal virtualCoinNumber = order.getVirtualCoinNumber();
			for (int i = 0; i < array.size(); i++) {
				//{"level":"1","content":"123123123","isa":"1","productId":"2","orderDetailId":"44","ordersId":"19"}
				JSONObject obj = array.getJSONObject(i);
				int level = Integer.parseInt(obj.getString("level"));
				String content = obj.getString("content");
				int isa = obj.getInt("isa");
				int productId = obj.getInt("productId");
				int orderDetailId = obj.getInt("orderDetailId");
				int ordersId = obj.getInt("ordersId");
				String ordersNo = obj.getString("ordersNo");
				//if(level==1){
				//	ProductInfo productInfo = productInfoDao.get(" where o.productId="+productId);
				//	totalCoid = totalCoid.add(productInfo.getVirtualCoinNumber());
				//}
				OrdersList ol = (OrdersList) ordersListDao.getObjectById(orderDetailId+"");
				EvaluateGoods e = new EvaluateGoods();
				e.setOrdersId(ordersId);
				e.setOrderDetailId(orderDetailId);
				e.setProductId(productId);
				e.setOrdersNo(ordersNo);
				e.setProductFullName(ol.getProductFullName());
				e.setSalesPrice(ol.getSalesPrice());
				e.setLogoImg(ol.getLogoImage());
				e.setLevel(level);
				e.setContent(content);
				e.setIsAnonymous(isa);
				e.setCreateTime(new Date());
				e.setShopInfoId(shopInfo.getShopInfoId());
				e.setShopName(shopInfo.getShopName());
				e.setAppraiserId(customer.getCustomerId());
				/*if(customer.getType()!=3){
					e.setAppraiserName(si.getCompanyName()+":"+son.getNickname());
				}else{
					e.setAppraiserName(customer.getLoginName());
				}*/
				e.setAppraiserName(customer.getLoginName());
				e.setAcceptAppraiserId(shopInfo.getCustomerId());
				e.setAcceptAppraiserName(shopInfo.getCustomerName());
				e.setEvaluateState(0);
				e.setExplain("");
				e.setBothState(1);
				e.setShowTime(new Date());
				e.setEvaluateUserType(1);
				evaluateGoodsDao.saveOrUpdateObject(e); //添加评价
				//记录订单日志
				OrdersOPLog oplog = new OrdersOPLog();
				oplog.setOrdersId(order.getOrdersId());
				oplog.setOrdersNo(ol.getOrdersNo());
				oplog.setOoperatorId(customer.getCustomerId());
				/*if(customer.getType()!=3){
					oplog.setOperatorName(si.getCompanyName()+":"+son.getNickname());
				}else{
					oplog.setOperatorName(customer.getLoginName());
				}*/
				oplog.setOperatorName(customer.getLoginName());
				//oplog.setOperatorName(si.getCompanyName()+":"+son.getNickname());
				oplog.setOperatorTime(new Date());
				oplog.setOoperatorSource("1");
				oplog.setOrdersOperateState(9);
				ordersOPLogDao.saveOrUpdateObject(oplog);
			}
			order.setOrdersState(9); //更新订单状态
			order.setUpdateTime(new Date());
			ordersDao.saveOrUpdateObject(order);
			//赠送商城币
			if(Utils.objectIsNotEmpty(virtualCoinNumber)&&virtualCoinNumber.compareTo(new BigDecimal(0))>0){
				//商城币明细表-------开始
				//获取当前用户最新的商城币记录
				MallCoin oldMallCoin = mallCoinDao.get("where o.customerId="+customer.getCustomerId()+" order by o.tradeTime desc");
				MallCoin mallCoin = new MallCoin();
				if(oldMallCoin!=null && oldMallCoin.getRemainingNumber()!=null){
					mallCoin.setRemainingNumber(oldMallCoin.getRemainingNumber().add(virtualCoinNumber));//余额
				}else{
					mallCoin.setRemainingNumber(virtualCoinNumber);//余额
				}
				mallCoin.setOrdersId(order.getOrdersId());
				mallCoin.setCustomerId(customer.getCustomerId());
				mallCoin.setOrdersNo(order.getOrdersNo());
				mallCoin.setOperatorTime(new Date());
				SerialNumberUtil serialNumberUtil = new SerialNumberUtil();
				mallCoin.setSerialNumber(serialNumberUtil.VirtualCoinNumber());//虚拟金币流水号
				mallCoin.setRemarks(customer.getLoginName()+"评价订单号为："+order.getOrdersNo()+"获取商城币"+virtualCoinNumber+"个！");
				mallCoin.setTradeTime(new Date());
				mallCoin.setTransactionNumber(virtualCoinNumber);//获取的商城币
				mallCoin.setType(1);//1.收入2.支出
				mallCoinDao.saveOrUpdateObject(mallCoin);
				//商城币明细表-------结束
				//保存商城币收支明细
				SupplierMallCoinDetail supplierMallCoinDetail =  new SupplierMallCoinDetail();
				supplierMallCoinDetail.setSupplierId(shopInfo.getShopInfoId());
				supplierMallCoinDetail.setSupplierLoginName(shopInfo.getCustomerName());
				supplierMallCoinDetail.setOrdersId(order.getOrdersId());
				supplierMallCoinDetail.setOrdersNo(order.getOrdersNo());
				supplierMallCoinDetail.setTradingTime(new Date());
				supplierMallCoinDetail.setType(2);
				supplierMallCoinDetail.setTradMallCoin(virtualCoinNumber);//支出的商城币
				SupplierMallCoinDetail supplierMallCoinDetail1 = supplierMallCoinDetailDao.get("where o.supplierId="+shopInfo.getShopInfoId()+" order by o.supplierMallCoinDetailId desc limit 1");
				if(supplierMallCoinDetail1==null){
					supplierMallCoinDetail.setTotalOutPut(virtualCoinNumber);
					supplierMallCoinDetail.setTotalInPut(new BigDecimal(0));
				}else{
					BigDecimal totalOutPut = new BigDecimal(0);
					totalOutPut=supplierMallCoinDetail1.getTotalOutPut().add(virtualCoinNumber);
					supplierMallCoinDetail.setTotalOutPut(totalOutPut);
					supplierMallCoinDetail.setTotalInPut(supplierMallCoinDetail1.getTotalInPut());
				}
				supplierMallCoinDetailDao.saveObject(supplierMallCoinDetail);
			}
		}catch(Exception e){
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	public void setEvaluateGoodsDao(IEvaluateGoodsDao evaluateGoodsDao) {
		this.evaluateGoodsDao = evaluateGoodsDao;
	}
	public void setShopInfoDao(IShopInfoDao shopInfoDao) {
		this.shopInfoDao = shopInfoDao;
	}
	public void setOrdersListDao(IOrdersListDao ordersListDao) {
		this.ordersListDao = ordersListDao;
	}
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	public void setOrdersOPLogDao(IOrdersOPLogDao ordersOPLogDao) {
		this.ordersOPLogDao = ordersOPLogDao;
	}
	public void setProductInfoDao(IProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}
	public void setMallCoinDao(IMallCoinDao mallCoinDao) {
		this.mallCoinDao = mallCoinDao;
	}
	public void setSupplierMallCoinDetailDao(
			ISupplierMallCoinDetailDao supplierMallCoinDetailDao) {
		this.supplierMallCoinDetailDao = supplierMallCoinDetailDao;
	}
	
}
