package util.common;

import java.math.BigDecimal;

/**
 * 工具类 - 商城总订单拆分子订单按照比例的抵扣金额
 * 赠送：积分赠送(包含节日积分、生日积分等)、红包赠送、商品赠送
 * 抵扣：积分抵扣、红包抵扣、满减抵扣、折扣抵扣
 * */
public class ShopDeductionMoneyUtils {
	
	//默认除法运算精度，小数点位数
	private static final int DEF_DIV_SCALE = 10;
	//默认返回值，小数点位数
	private static final int DEF_RETURN_SCALE = 2;
	
	/**
	 * 销售订单中子订单的赠送、抵扣金额比例算法 = (子订单金额 / 总订单金额) * 抵扣[赠送]金额
	 * 用于总订单拆分子订单时，每个子订单占用的抵扣金额
	 * @param childOrdersAmount 子订单金额 被除数
	 * @param totalOrdersAmount 总订单金额  除数
	 * @param deductionAmount 抵扣[赠送]金额【或者抵扣[赠送]积分值】
	 * 
	 * @return 每个子订单占用的抵扣金额
	 * */
	public static BigDecimal orderDeductionMoney(BigDecimal childOrdersAmount,BigDecimal totalOrdersAmount ,BigDecimal deductionAmount) {
		//计算出子订单占总订单的比例
		BigDecimal proportion = childOrdersAmount.divide(totalOrdersAmount,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
		//子订单占总订单的比例 * 抵扣金额
		BigDecimal orderDeductionMoney =  proportion.multiply(deductionAmount);
		return orderDeductionMoney.setScale(DEF_RETURN_SCALE,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 销售订单明细中商品赠送、抵扣金额比例算法 = 商品金额 / 订单(总订单或者子订单) * 订单的抵扣[赠送]金额
	 * 用于保存订单商品明细时，每个商品占用的抵扣金额
	 * @param orderAmount 订单金额 除数
	 * @param productAmount 商品金额 被除数
	 * @param orderDeductionAmount 订单的抵扣[赠送]金额【或者抵扣[赠送]积分值】
	 * 
	 * @return 每个商品占用的抵扣金额
	 * */
	public static BigDecimal orderDetailProductDeductionMoney(BigDecimal orderAmount,BigDecimal productAmount,BigDecimal orderDeductionAmount) {
		//计算出商品占订单的比例
		BigDecimal proportion = productAmount.divide(orderAmount,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
		//商品占订单的比例 * 订单的抵扣[赠送]金额
		BigDecimal orderDeductionMoney =  proportion.multiply(orderDeductionAmount);
		return orderDeductionMoney.setScale(DEF_RETURN_SCALE,BigDecimal.ROUND_HALF_UP);
	}
	
	public static void main(String[] args) {
//		BigDecimal temp = ShopDeductionMoneyUtils.orderDetailProductDeductionMoney(new BigDecimal(20.25),new BigDecimal(100.75),new BigDecimal(5.35));
//		System.out.println(temp);
	}
	
}
