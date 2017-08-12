package util.upload;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 工具类 - 上传图片处理
 * 
 * */
public class FileTypeUtil {
	/**
	 * 时间格式化，可以加减时间。
	 * 
	 * @param date
	 *            传入时间
	 * @param fileRule
	 *            文件生成目录规则：年月日
	 * @param index
	 *            加减时间
	 * @return 新格式化后时间
	 */
	public static String getSerial(Date date, String fileRule , int index) {
		long msel = date.getTime();
		SimpleDateFormat fm = new SimpleDateFormat(fileRule);
		msel += index;
		date.setTime(msel);
		String serials = fm.format(date);
		return serials;
	}
	/**
	 * 时间格式化
	 * 
	 * @param date
	 *            传入时间
	 * @param fileRule
	 *            文件生成目录规则：年月日
	 * @return 新格式化后时间
	 */
	public static String getSerial(Date date, Object fileRule) {
		SimpleDateFormat fm = new SimpleDateFormat( String.valueOf(fileRule) );
		String serials = fm.format(date);
		return serials;
	}
	/**
	 * 检查是否是图片格式。
	 * 
	 * @param imgStr
	 *            图片全名称
	 * @return 真假
	 */
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			if (imgStr.equalsIgnoreCase(".gif")
					|| imgStr.equalsIgnoreCase(".jpg")
					|| imgStr.equalsIgnoreCase(".jpeg")
					|| imgStr.equalsIgnoreCase(".png")
					|| imgStr.equalsIgnoreCase(".bmp")) {
				flag = true;
			}
		}
		return flag;
	}
}
