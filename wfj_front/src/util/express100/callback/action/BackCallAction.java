package util.express100.callback.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import net.sf.json.JSONObject;
import shop.order.pojo.Orders;
import shop.order.pojo.Shipping;
import shop.order.service.IOrdersService;
import shop.order.service.IShippingService;
import util.action.BaseAction;
import util.express100.bean.NoticeRequest;
import util.express100.bean.NoticeResponse;
import util.express100.bean.Result;
import util.express100.callback.demo.JacksonHelper;
import util.other.Utils;
/**
 * 信息回调Action
 */
public class BackCallAction extends BaseAction{
	private static final long serialVersionUID = -6254331811888737617L;
	private IShippingService shippingService;
	private IOrdersService ordersService;
	
	public void backMessage() throws IOException{
		NoticeResponse resp = new NoticeResponse();
		resp.setResult(false);
		resp.setReturnCode("500");
		resp.setMessage("保存失败");
		String param = request.getParameter("param");
		try {
			NoticeRequest nReq = JacksonHelper.fromJSON(param,NoticeRequest.class);
			Result result = nReq.getLastResult();
			// 处理快递结果，在此进行保存操作，并且返回操作是否完成状态，根据状态判断是否返回结果。
			Shipping shipping = (Shipping) shippingService.getObjectByParams("where o.deliverySn="+result.getNu()+" and o.code='"+result.getCom()+"'");
			//取出物流当前状态
			String state = result.getState();
			int stateTemp = Integer.parseInt(state);
			if(Utils.objectIsNotEmpty(state)){
				//发货详情中的物流动态如果为已经签收时不改变状态
				if(Utils.objectIsNotEmpty(shipping)){
					if(stateTemp==3){//当物流状态为已签收时改变订单状态为已完成
						Orders orders = (Orders) ordersService.getObjectById(String.valueOf(shipping.getOrdersId()));
						orders.setOrdersState(5);
						orders.setUpdateTime(new Date());
						ordersService.saveOrUpdateObject(orders);
					}
					JSONObject jo = JSONObject.fromObject(result);// 格式化result
					shipping.setState(stateTemp);//当前物流状态
					shipping.setExpressInfo(jo.toString());//物流信息
					shippingService.saveOrUpdateObject(shipping);
					resp.setResult(true);
					resp.setReturnCode("200");
					resp.setMessage("模块更新物流信息保存成功");
					response.getWriter().print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
					response.getWriter().flush();
					response.getWriter().close();
				}
			}else{
				resp.setMessage("物流无信息，保存失败");
				PrintWriter out = response.getWriter();
				out.print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage("模块异常保存失败" + e.getMessage());
			PrintWriter out = response.getWriter();
			out.print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
			out.flush();
			out.close();
		}
		//System.out.println("后台打印快递信息回调接口："+JacksonHelper.toJSON(resp)+"____"+param);
	}

	public void setShippingService(IShippingService shippingService) {
		this.shippingService = shippingService;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	
}
