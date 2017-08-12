package util.other;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 将json对对象中的日期属性进行格式化后再重新指定格式重新进行格式化
 * @author 刘青松
 * 使用方法：
 * JsonConfig jsonConfig = new JsonConfig();
   jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
   JSONObject jo = JSONObject.fromObject(users,jsonConfig);
   实体类中时间类型为java.util.Date时转换到mysql的datetime时会出错，数据库中保存的是0000-00-00 00：00：00，在格式化的时候会出错
   在配置数据源时加上&amp;zeroDateTimeBehavior=convertToNull这句话，把0000-00-00 00：00：00类型转换成null，在下面的方法做转换的
   时候去判断不为null时才可以。
 */
public class JSONFormatDate implements JsonValueProcessor {
	private String format = "yyyy-MM-dd";
	public JSONFormatDate() {
		super();
	}
	public JSONFormatDate(String format) {
		super();
		this.format = format;
	}
	/**
	 * 自定义格式化Json数组。
	 * 
	 * @param value
	 *            传入需要格式化的对象数据
	 * @param jsonConfig
	 *            Json配置对象
	 * @return 新的对象
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Date[] && value!=null) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}
	/**
	 * 自定义格式化Json对象。
	 * 
	 * @param key
	 *            传入关键字
	 * @param value
	 *            传入需要格式化的对象数据
	 * @param jsonConfig
	 *            Json配置对象
	 * @return 新的对象
	 */
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if (value instanceof java.util.Date && value!=null) {
			String str = new SimpleDateFormat(format).format((Date) value);
			return str;
		}
		return value;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
