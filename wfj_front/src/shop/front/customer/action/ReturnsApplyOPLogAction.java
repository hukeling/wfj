package shop.front.customer.action;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.customer.pojo.Customer;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.pojo.ReturnsApplyOPLog;
import shop.returnsApply.service.IReturnsApplyOPLogService;
import shop.returnsApply.service.IReturnsApplyService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/**
 * 退换申请操作日志Action
 * @author mqr
 *
 */
@SuppressWarnings("serial")
public class ReturnsApplyOPLogAction extends BaseAction{
	/**退换货申请service**/
	private IReturnsApplyService returnsApplyService;
	/**退换申请操作日志Service**/
	private IReturnsApplyOPLogService returnsApplyOPLogService;
	/**商品Service**/
	private IProductInfoService productInfoService;
	/**退换货申请Obj*/
	private ReturnsApply returnsApply=new ReturnsApply();
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**店铺Obj**/
	private ShopInfo shopInfo=new ShopInfo();
	/**当前页ID**/
	private Integer pageIndex=1;
	/**退换货记录List**/
	private List<Map> returnsApplyListMap=new ArrayList<Map>();
	/**审核状态**/
	private Integer asState;
	/**退货处理状态**/
	private Integer rsState;
	/**退货处理id**/
	private Integer returnsApplyId;
	//生成日志
	public String updataSate() throws ParseException{
		Customer customer =(Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		String sql1 = " select s.trueName as trueName from shop_customerinfo s where s.customerId ="+customer.getCustomerId()+" ";
		List<Map<String,Object>> list = returnsApplyService.findListMapBySql(sql1);//取得用户真实名称
		//申请(修改状态)
		ReturnsApply ra=(ReturnsApply) returnsApplyService.getObjectByParams(" where o.returnsApplyId="+returnsApplyId +" and o.shopInfoId="+shopInfo.getShopInfoId());
		if(ra!=null){
			if(asState!=null){
				ra.setApplyState(asState);
			}
			ra.setReturnsState(rsState);
			returnsApply.setUpdateMember(customer.getLoginName());
			returnsApply.setUpdateTime(new Date());
			ra = (ReturnsApply) returnsApplyService.saveOrUpdateObject(ra);
			//如果该申请的审核状态为同意（2）退货状态为订单处理完成（5）则修改商品库存
			if(ra!=null&&ra.getApplyState()==2&&ra.getReturnsState()==5){
				ProductInfo productInfo = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+ra.getProductId());
				if(productInfo!=null){
					//ra.getCount()为该申请中 退货的商品数量
					//productInfo.getStoreNumber()为退货商品的库存
					productInfo.setStoreNumber(productInfo.getStoreNumber()+ra.getCount());
					productInfoService.saveOrUpdateObject(productInfo);
				}
			}
			//日志
			ReturnsApplyOPLog raol=new ReturnsApplyOPLog();
			if(asState!=null){
				raol.setOperatorLoginName(customer.getLoginName());//登陆名称
				//raol.setOperatorName(shopInfo.getShopName()); //操作人姓名
				raol.setOperatorName(customer.getLoginName());
				raol.setReturnsApplyNo(ra.getReturnsApplyNo());//申请退货单号
				raol.setApplyId(ra.getReturnsApplyId());//id
				raol.setShopInfoId(ra.getShopInfoId());//店铺id
				if(asState==2){
					raol.setComments("审核：同意");//处理信息
				}else{
					raol.setComments("审核：拒绝");//处理信息
				}
				raol.setOperatorTime(new Date());//操作时间
				returnsApplyOPLogService.saveOrUpdateObject(raol);
			}
			raol=new ReturnsApplyOPLog();
			raol.setOperatorLoginName(customer.getLoginName());//登陆名称
			//raol.setOperatorName(shopInfo.getShopName()); //操作人姓名
			raol.setOperatorName(customer.getLoginName());
			raol.setReturnsApplyNo(ra.getReturnsApplyNo());//申请退货单号
			raol.setApplyId(ra.getReturnsApplyId());//id
			raol.setShopInfoId(ra.getShopInfoId());//店铺id
			String sata="";//退货状态
			if(rsState==1){
				sata="退货中";
			}else if(rsState==2){
				sata="退货完成";
			}else if(rsState==3){
				sata="退款中";
			}else if(rsState==4){
				sata="退款完成";
			}else if(rsState==5){
				sata="订单处理完成";
			}
			raol.setComments("退货状态："+sata);//处理信息
			raol.setOperatorTime(new Date());//操作时间
			returnsApplyOPLogService.saveOrUpdateObject(raol);
		}
		return SUCCESS;	
	}
	public ReturnsApply getReturnsApply() {
		return returnsApply;
	}
	public void setReturnsApply(ReturnsApply returnsApply) {
		this.returnsApply = returnsApply;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public List<Map> getReturnsApplyListMap() {
		return returnsApplyListMap;
	}
	public void setReturnsApplyListMap(List<Map> returnsApplyListMap) {
		this.returnsApplyListMap = returnsApplyListMap;
	}
	public Integer getAsState() {
		return asState;
	}
	public void setAsState(Integer asState) {
		this.asState = asState;
	}
	public Integer getRsState() {
		return rsState;
	}
	public void setRsState(Integer rsState) {
		this.rsState = rsState;
	}
	public Integer getReturnsApplyId() {
		return returnsApplyId;
	}
	public void setReturnsApplyId(Integer returnsApplyId) {
		this.returnsApplyId = returnsApplyId;
	}
	public void setReturnsApplyService(IReturnsApplyService returnsApplyService) {
		this.returnsApplyService = returnsApplyService;
	}
	public void setReturnsApplyOPLogService(
			IReturnsApplyOPLogService returnsApplyOPLogService) {
		this.returnsApplyOPLogService = returnsApplyOPLogService;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
}
