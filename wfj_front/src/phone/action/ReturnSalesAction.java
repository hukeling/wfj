package phone.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.pojo.ReturnsApplyOPLog;
import shop.returnsApply.service.IReturnsApplyOPLogService;
import shop.returnsApply.service.IReturnsApplyService;
import util.action.BaseAction;
import util.other.SerialNumberUtil;
import util.upload.ImageFileUploadUtil;

public class ReturnSalesAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IOrdersService ordersService;
	private IOrdersListService ordersListService;
	private ICustomerService customerService;
	private IProductInfoService productInfoService;
	private IReturnsApplyService returnsApplyService;// 退换申请Service
	private IReturnsApplyOPLogService returnsApplyOPLogService;// 退换申请操作日志Service
	private String ordersNo;// 退货订单号
	private int returnReasonType;// 退货原因类型:1收到商品破损,2商品错发/漏发,3商品需要维修,4发票问题,5收到商品不符,6商品质量问题,7未收到货,8未按约定时间发货,9其他
	private String returnReasonDescription;// 退货原因说明
	private int productId;// 退货商品id
	private int count;// 退货商品数量
	private int returnType;// 退货类型1：仅退款2：退款且退货
	private File txImage;// 传入文件路径
	private String txImageFileName;// 文件名称

	/**
	 * https://192.168.10.210:8443/wfj_front/phone/returnSales.action?ordersNo=
	 * 2016010617142025&productId=845&count=1&returnType=1&returnReasonType=1&
	 * returnReasonDescription=asdfgh 退货接口 
	 * @throws IOException
	 */
	public void returnGoods() throws IOException {
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		String wherehql2 = "where o.ordersNo=" + ordersNo+" and productId="+productId;
		ReturnsApply re=(ReturnsApply)returnsApplyService.getObjectByParams(wherehql2);
		//防止重复提交退货申请（同一个单的同一件商品只允许退一次）
		if(re==null){ 
		String wherehql = "where o.ordersNo=" + ordersNo;
		Orders or = (Orders) ordersService.getObjectByParams(wherehql);
		if (or != null) {
			Customer customer = (Customer) customerService.getObjectById(String
					.valueOf(or.getCustomerId()));
			// 退货第一步(如果该订单没有其他的商品)修改订单状态为7
			List<OrdersList> olList=new ArrayList<OrdersList>();
			String wherehql3="where o.ordersNo="+ordersNo;
			olList=ordersListService.findObjects(wherehql3);
			int countDetail=0;//记录一个订单对应的未退货的订单明细的数量
			for(OrdersList ol:olList){
				int productId2=ol.getProductId();
				if(productId!=productId2){
					countDetail++;
				}
			}
			if(countDetail==0){//countDetail=0说明该订单对应的未退货的订单明细为0
				or.setOrdersState(7);
			}
			or = (Orders) ordersService.saveOrUpdateObject(or);
			ProductInfo pro = (ProductInfo) productInfoService.getObjectById(String.valueOf(productId));
			// 退货第二步增加一条退货申请记录
			ReturnsApply returnsApply = new ReturnsApply();
			returnsApply.setCustomerId(or.getCustomerId());
			returnsApply.setOrdersId(or.getOrdersId());
			returnsApply.setShopInfoId(or.getShopInfoId());
			returnsApply.setOrdersNo(or.getOrdersNo());
			returnsApply.setProductId(productId);// 商品id
			returnsApply.setProductName(pro.getProductName());// 商品名称
			returnsApply.setProblemDescription(returnReasonDescription);// 退货原因
			returnsApply.setLinkman(or.getConsignee());// 联系人
			returnsApply.setMobilePhone(or.getMobilePhone());// 联系电话
			returnsApply.setApplyTime(new Date());// 退货申请时间
			returnsApply.setUpdateTime(new Date());// 退货申请修改时间
			returnsApply.setCount(count);// 退货的商品数量
			String str=SerialNumberUtil.ReturnsApplyNoNumber();
			returnsApply.setReturnsApplyNo(str);//退换货申请单号
			returnsApply.setApplyState(1);//审核状态(默认：处理中)
			returnsApply.setReturnsState(0);//退款状态(默认：0未处理)
			returnsApply.setDisposeMode("3");// 期望处理方式
			// returnsApply.setShippingAddress(or.getAddress());//收货地址
			// returnsApply.setDeliverySn();//退货物流运单号
			// returnsApply.setDeliveryCorpName("");//退货物流公司名称
			// returnsApply.setDeliveryUrl();//退货物流公司查询网址
			// 退货图片上传
			String uploadImage = ImageFileUploadUtil.uploadImageFile(txImage,
					txImageFileName, fileUrlConfig, "image_returnSales");
			returnsApply.setUploadImage(uploadImage);// 退货时上传的图片

			int random = (int) (Math.random() * 100);// 0到100的随机数
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			String returnsApplyNo = sdf.format(new Date())
//					+ String.valueOf(random);
//			returnsApply.setReturnsApplyNo(returnsApplyNo);// 退货申请单号=当前系统时间+两位随机数
			returnsApply = (ReturnsApply) returnsApplyService
					.saveOrUpdateObject(returnsApply);
			// //退货第三步，增加一条退货申请操作日志记录
			ReturnsApplyOPLog returnsApplyOPLog = new ReturnsApplyOPLog();
			returnsApplyOPLog.setApplyId(returnsApply.getReturnsApplyId());// 退换货申请ID
			returnsApplyOPLog.setShopInfoId(returnsApply.getShopInfoId());// 店铺id
			returnsApplyOPLog.setReturnsApplyNo(returnsApply
					.getReturnsApplyNo());// 退货申请单号
			returnsApplyOPLog.setOperatorLoginName(customer.getLoginName());// 操作人登录名
			returnsApplyOPLog.setOperatorTime(new Date());// 操作时间
			returnsApplyOPLog.setOperatorName(customer.getLoginName());// 操作人姓名
			returnsApplyOPLog.setComments("退货申请");// 处理信息
			returnsApplyOPLog = (ReturnsApplyOPLog) returnsApplyOPLogService
					.saveOrUpdateObject(returnsApplyOPLog);
			if (returnsApply != null && returnsApplyOPLog != null) {
				jo.accumulate("Status", true);
				jo.accumulate("Data", "退货申请已提交，待卖家审核");
			} else {
				jo.accumulate("Status", false);
				jo.accumulate("Data", "退货申请失败，请重新申请");
			}
		} else {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "订单不存在");
		}
	}else{
		jo.accumulate("Status", false);
		jo.accumulate("Data", "该商品已经提交退货申请");
	}
	 	
		out.write(jo.toString());
		System.out.println(jo.toString());
		out.flush();
		out.close();
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setReturnsApplyService(IReturnsApplyService returnsApplyService) {
		this.returnsApplyService = returnsApplyService;
	}

	public void setReturnsApplyOPLogService(
			IReturnsApplyOPLogService returnsApplyOPLogService) {
		this.returnsApplyOPLogService = returnsApplyOPLogService;
	}

	public void setReturnReasonType(int returnReasonType) {
		this.returnReasonType = returnReasonType;
	}

	public void setReturnReasonDescription(String returnReasonDescription) {
		this.returnReasonDescription = returnReasonDescription;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public void setTxImage(File txImage) {
		this.txImage = txImage;
	}

	public void setTxImageFileName(String txImageFileName) {
		this.txImageFileName = txImageFileName;
	}

	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	
}
