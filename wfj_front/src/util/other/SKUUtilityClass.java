package util.other;
import java.math.BigDecimal;
import java.text.DecimalFormat;
/**
 * SKU工具类
 * 		此工具类用于生成商品唯一标识：SKU订货号
 * @author mf
 */
public class SKUUtilityClass {
	/**
	 * 生成SKU订货号
	 * @param theLatestSKU		最新的SKU订货号
	 * @return 
	 * @return generatedSKU		生成的SKU订货号
	 */
	public static String getGeneratedSKU(String theLatestSKU){
		//SKU生成规则：1位数字+1个字母+5位数字
		//例如：1A56859
		//第一部分：1位数字
		String firstPart="1";
		//第二部分：1个字母
		String secondPart="A";
		//第三部分：5位数字
		String thirdPart="00001";
		//分割最新的SKU订货号：theLatestSKU（不为空的情况下）
		if(Utils.objectIsNotEmpty(theLatestSKU)){
			firstPart = theLatestSKU.substring(0, 1);
			secondPart = theLatestSKU.substring(1, 2);
			thirdPart = theLatestSKU.substring(2, theLatestSKU.length());
			//如果第三部分为99999 则将第三部分变为
			if("99999".equals(thirdPart)){
				//第三部分变为初始值
				thirdPart="00001";
				//字母进1
				int hashCode = secondPart.hashCode();//将字母转换成数字hashCode
				BigDecimal secBD=new BigDecimal(hashCode);//转化成BD类型
				//判断字母是否为Z
				if("Z".equals(secondPart)){
					//第二部分变为初始值
					secondPart="A";
					//第一部分进1
					BigDecimal firBD=new BigDecimal(firstPart);//转化成BD类型
					firBD=firBD.add(new BigDecimal(1));//+1
					firstPart=String.valueOf(firBD);
				}else{
					secBD=secBD.add(new BigDecimal(1));//+1
					secondPart=String.valueOf((char)secBD.intValue());//将数字转换成字母
				}
			}else{
				DecimalFormat df=new DecimalFormat("00000");//定义格式化格式
				//第三部分数字进1 ，其他部分不动
				BigDecimal thirBD=new BigDecimal(thirdPart);//转化成BD类型
				thirBD=thirBD.add(new BigDecimal(1));//+1
				thirdPart = df.format(thirBD);//格式化 重新赋值给第三部分
			}
		}
		return firstPart+secondPart+thirdPart;
	}
	public static void main(String[] args) {
		//测试SKU订货号
		String sku="";
		for(int i=0;i<100000;i++){
			//System.out.println(getGeneratedSKU(sku));
			sku=getGeneratedSKU(sku);
		}
	}
}
