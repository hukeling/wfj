package util.upload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import util.other.Utils;
/**
 * 工具类 - 上传文件处理，文件名路径处理
 * 
 * */
public class FileUploadUtil {
	/**
	 * 上传文件拓展名，生成新的文件名。
	 * 
	 * @param root
	 *            传入路径
	 * @param folderName
	 *            目录名称
	 * @param fileuploadFileName
	 *            文件上传名称
	 * @param fileupload
	 *            文件真实路径
	 * @return 全新路径和文件地址
	 */
	public static String uploadFile(String root,String folderName,String fileuploadFileName,File fileupload) {
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		//String savePath = ServletActionContext.getServletContext().getRealPath("/"); // 获取项目根路径
		//savePath = savePath + "/"+fileUrl+"/";
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		String randomCode=Utils.getRandomCode();
		// 获取拓展名
		if (fileuploadFileName.lastIndexOf(".") >= 0) extName = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
		try {
			newFileName = randomCode + extName; // 文件重命名后的名字
			String filePath = root+"/"+folderName +"/"+ newFileName;
			FileUtils.copyFile(fileupload, new File(filePath));// 检查上传的路径
			String savepath=folderName +"/"+ newFileName;
			return savepath;
		} catch (IOException e) {
			return "上传失败，出错啦!";
		}
	}
	/**
	 * 上传文件拓展名，生成新的文件名。
	 * 
	 * @param root
	 *            传入路径
	 * @param fileUrl
	 *            文件地址
	 * @param fileuploadFileName
	 *            文件上传名称
	 * @param is
	 *            附件真实文件流
	 * @return 全新路径和文件地址
	 */
	public static String uploadFile(String root,String fileUrl,String fileuploadFileName,InputStream is) {
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		String randomCode=Utils.getRandomCode();
		// 获取拓展名
		if (fileuploadFileName.lastIndexOf(".") >= 0) extName = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
		try {
			newFileName = randomCode + extName; // 文件重命名后的名字
			//String filePath = root+"/"+fileUrl +"/"+ newFileName;
			File outFile=new File(root+"/"+fileUrl);
			if (!outFile.exists()) {
			    outFile.mkdirs();
			}
			OutputStream out=new FileOutputStream(outFile+"/"+newFileName);
			byte [] buf=new byte[1024];
			int length=0;
			while((length=is.read(buf))!=-1){
				out.write(buf, 0, length); 
			}
			out.flush();
			out.close();
			is.close();
			// 检查上传的是否是图片
			String savepath="/"+fileUrl +"/"+ newFileName;
			return savepath;
		} catch (IOException e) {
			e.printStackTrace();
			return "上传失败了";
		}
	}
}
