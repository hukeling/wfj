package util.other;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * SerialNumberUtil - 编号生成工具类（订单编号、支付编号、退款编号、发货编号、退货编号）
 */
public class SerialNumberUtil {
	/**
	 * 生成当前时间（yyyyMMddHHmmss）
	 * @return 当前时间
	 */
	public static String createTime(){
		Date createTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(createTime);
		return currentTime;
	}
	/**
	 * 生成订单编号(16位)
	 * @return 订单编号
	 */
	public static String OrderSnNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String ordersNo = createTime()+num;
		return ordersNo;
	}
	/**
	 * 生成支付编号(16位)
	 * @return 支付编号
	 */
	public static String PaymentSnNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String paymentNo = createTime()+num;
		return paymentNo;
	}
	/**
	 * 生成退款编号(16位)
	 * @return 退款编号
	 */
	public static String RefundSnNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String refundNo = createTime()+num;
		return refundNo;
	}
	/**
	 * 生成发货编号(16位)
	 * @return 发货编号
	 */
	public static String ShippingSnNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String shippingSNo = createTime()+num;
		return shippingSNo;
	}
	/**
	 * 生成退货编号(16位)
	 * @return 退货编号
	 */
	public static String ReshipSnNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String reshipSNo = createTime()+num;
		return reshipSNo;
	}
	/**
	 * 生成退换申请编号(16位)
	 * @return 退换申请编号
	 */
	public static String ReturnsApplyNoNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String returnsApplyNo = createTime()+num;
		return returnsApplyNo;
	}
	/**
	 * 生成虚拟金币编号(16位)
	 * @return 虚拟金币编号
	 */
	public static String VirtualCoinNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String returnsApplyNo = createTime()+num;
		return returnsApplyNo;
	}
	/**
	 * 生成超级终端编号(16位)
	 * @return 超级终端号
	 */
	public static String SuperTerminalNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String returnsApplyNo = createTime()+num;
		return returnsApplyNo;
	}
	/**
	 * 生成分销商编号(16位)
	 * @return 分销商编号
	 */
	public static String distributorNumber(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String returnsApplyNo = createTime()+num;
		return returnsApplyNo;
	}
	/**
	 * 商品编号(16位)
	 * @return 商品编号
	 */
	public static String productCode(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String productCode = createTime()+num;
		return productCode;
	}
	/**
	 * 品牌联盟(16位)
	 * @return 品牌联盟编号
	 */
	public static String brandAllianceCode(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String productCode = createTime()+num;
		return productCode;
	}
	/**
	 * 年费分享(16位)
	 * @return 年费分享编号
	 */
	public static String AnnualShareCode(){
		Double random = Math.random();
		String num = random.toString().substring(2, 4);
		String productCode = createTime()+num;
		return productCode;
	}
}
