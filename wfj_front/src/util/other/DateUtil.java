package util.other;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 封装时间和日期的各种计算方法
 * @author 刘青松
 *
 */
public class DateUtil {
	/**
	 * 封装了某年某月的开始日期和结束日期
	 * @param yearString
	 * @param monthNum
	 * @return
	 */
	public static String getMonthOfBeginAndEndDate(String yearString,String monthNum){
	    String beginDateString="01";
		String endDateString=null;
	    String beginString="00:00:00";
	    String endString="23:59:59";
	    int year=Integer.valueOf(yearString);
		if("01".equals(monthNum)||"03".equals(monthNum)||"05".equals(monthNum)||"07".equals(monthNum)||"08".equals(monthNum)||"10".equals(monthNum)||"12".equals(monthNum)){
			endDateString="31";
		}
	    if("02".equals(monthNum)){
	    	endDateString="28";
	    	if(year%4==0&&year%100!=0||year%400==0){
	    		endDateString="29";
	    	}
	    }
	    if("04".equals(monthNum)||"06".equals(monthNum)||"09".equals(monthNum)||"11".equals(monthNum)){
	    	endDateString="30";
	    }
		return yearString+"-"+monthNum+"-"+beginDateString+"  "+beginString+","+yearString+"-"+monthNum+"-"+endDateString+"  "+endString;
	}
	/**
	 * 获取当前年月的月的第一天
	 * @param yearString
	 * @param monthNum
	 * @return
	 */
	public static String getMonthBegin(String yearString,String monthNum){
		String beginDateString="01";
		return yearString+"-"+monthNum+"-"+beginDateString;
	}
	/**
	 * 获取当前年月的月的第一天带上开始时间
	 * @param yearString
	 * @param monthNum
	 * @return
	 */
	public static String getMonthBeginWithBeginTime(String yearString,String monthNum){
		String beginDateString="01";
		String beginString="00:00:00";
		return yearString+"-"+monthNum+"-"+beginDateString+"  "+beginString;
	}
	/**
	 * 获取当前年月的月的第一天带上结束时间
	 * @param yearString
	 * @param monthNum
	 * @return
	 */
	public static String getMonthBeginWithEndTime(String yearString,String monthNum){
		String beginDateString="01";
		String endString="23:59:59";
		return yearString+"-"+monthNum+"-"+beginDateString+"  "+endString;
	}
	/**
	 * 获取当前年月的月的最后一天
	 * @param yearString
	 * @param monthNum
	 * @return
	 */
	public static String getMonthEnd(String yearString,String monthNum){
		String endDateString=null;
		int year=Integer.valueOf(yearString);
		if("01".equals(monthNum)||"03".equals(monthNum)||"05".equals(monthNum)||"07".equals(monthNum)||"08".equals(monthNum)||"10".equals(monthNum)||"12".equals(monthNum)){
			endDateString="31";
		}
		if("02".equals(monthNum)){
			endDateString="28";
			if(year%4==0&&year%100!=0||year%400==0){
				endDateString="29";
			}
		}
		if("04".equals(monthNum)||"06".equals(monthNum)||"09".equals(monthNum)||"11".equals(monthNum)){
			endDateString="30";
		}
		return yearString+"-"+monthNum+"-"+endDateString;
	}
	/**
	 * 获取当前年月的月的最后一天带上时间
	 * @param yearString
	 * @param monthNum
	 * @return
	 */
	public static String getMonthEndWithTime(String yearString,String monthNum){
		String endDateString=null;
		String endString="23:59:59";
		int year=Integer.valueOf(yearString);
		if("01".equals(monthNum)||"03".equals(monthNum)||"05".equals(monthNum)||"07".equals(monthNum)||"08".equals(monthNum)||"10".equals(monthNum)||"12".equals(monthNum)){
			endDateString="31";
		}
		if("02".equals(monthNum)){
			endDateString="28";
			if(year%4==0&&year%100!=0||year%400==0){
				endDateString="29";
			}
		}
		if("04".equals(monthNum)||"06".equals(monthNum)||"09".equals(monthNum)||"11".equals(monthNum)){
			endDateString="30";
		}
		return yearString+"-"+monthNum+"-"+endDateString+"  "+endString;
	}
	/**
	 * 日期格式化工具类，格式为 yyyy-MM-dd 年月日
	 * @param date 时间
	 * @return
	 */
	public static String dateToStringYTD(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 日期格式化工具类，格式为 yyyy-MM-dd 年月日
	 * @param date 时间
	 * @return
	 */
	public static String dateToStringYear(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}
	/**
	 * 日期格式化工具类，格式为 yyyy-MM-dd 年月日
	 * @param date 时间
	 * @return
	 */
	public static Date stringToDateYTD(String date){
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
}
