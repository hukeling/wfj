package phone.util;
/**
 *程序中涉及到的一些静态变量
 */
public class PhoneStaticConstants {
	//订单状态
	public static final int  osUntreatedOrders=1;//"未处理订单" ;
	public static final int  osPayedOrders=2;//"已付款";
	public static final int  osDistributedOrders=3;//"配货中";
	public static final int  osshippedOrders=4;//"已发货";
	public static final int  osGetgoodsOrders=5;//"已收货";
	public static final int  osCancelOrders=6;//"已取消";
	public static final int  osReturnorchangeOrders=7;//"退换货";
	public static final int  osEvaluatedOrders=8;//"已评价";
	//订单类型1待付款2待发货4待收货5待评价7退换货的
	public static final int toPay=1;//待付款
	public static final int toSendGoods=2;//待发货
	public static final int toReceiveGoods=4;//待收货
	public static final int toEvaluate=5;//待评价
	public static final int backOrChangeGoods=7;//退换货
	//唐智府模块店铺
	public static final int AllOldBrandStore=3;//获取老字号所有店铺信息
	public static final int OldEat=56;//获取老字号吃喝
	public static final int OldOther=57;//获取老字号其他
	public static final int AllWZWFJ=58;//获取玩转王府井所有店铺
	public static final int WfjYl=59;//玩转王府井娱乐
	public static final int WfjHotel=60;//玩转王府井酒店
	public static final int WfjJingdian=62;//玩转王府井景点
	//pc端参数
	public static final int tangStorePageSize=16;//唐智府店铺每页显示条数 
	//高德地图
	public static final String key="b01784c8ae872d2e9be258cad796d666";
}
