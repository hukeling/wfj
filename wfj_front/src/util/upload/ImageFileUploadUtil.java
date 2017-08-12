package util.upload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import util.other.Utils;
/**
 * 工具类 - 上传图片文件处理，文件名路径处理
 * 
 * */
public class ImageFileUploadUtil {
	/**
	 * 上传图片文件拓展名，生成新的文件名。
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
			return "上传失败!";
		}
	}
	/**
	 * 上传图片文件拓展名，生成新的文件名。
	 * 
	 * @param imagePath
	 *            传入文件路径
	 * @param imagePathFileName
	 *            文件路径名称包含文件名称
	 * @param fileUrlConfig
	 *            全局上传文件总根
	 * @param fileupload
	 *            配置文件systemConfig.properties中文件路径
	 * @param imageInfoPath
	 *            获取配置文件systemConfig.properties中文件保存详细划分路径
	 * @return 全新路径和文件地址
	 */
	public static String uploadImageFile(File imagePath,String imagePathFileName,Map<Object,Object> fileUrlConfig,String imageInfoPath)  {
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
			//判断图片后缀是否符合要求，忽略大小写的判断
			if ( FileTypeUtil.checkIsImage(typeStr) ) {
				try {
					// 需要修改文件的后缀名称
					String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
					// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
					String randomCode=Utils.getRandomCode();
					//原始图片文件编译后的新名称
					newName = randomCode + newName;
					//定义新文件保存实际路径+配置文件systemConfig.properties中定义的文件生成目录规则：年月日
					String newImagePath = fileUrlConfig.get("shop") + "/"+ fileUrlConfig.get(imageInfoPath) + "/"+ FileTypeUtil.getSerial(new Date(), fileUrlConfig.get("fileRule")) + "/" ;
					//定义文件对象，需要加上盘符
					File savefile = new File((fileUrlConfig.get("fileUploadRoot") + "/"+ newImagePath), newName);
					//判断文件是否存在，不存在则新创建
					if (!savefile.getParentFile().exists()) {
						savefile.getParentFile().mkdirs();
					}
					FileUtils.copyFile(imagePath, savefile);
					imagePathFileName = newImagePath + newName;
					return imagePathFileName;
				} catch (IOException e) {
					e.printStackTrace();
					return "图片上传失败!";
				}
			}
		}
		return imagePathFileName;
	}
	/**
	 * 上传图片文件拓展名，生成：自定义名称+新的文件名
	 * 
	 * @param imagePath
	 *            传入文件路径
	 * @param imagePathFileName
	 *            文件路径名称包含文件名称
	 * @param fileUrlConfig
	 *            全局上传文件总根
	 * @param fileupload
	 *            配置文件systemConfig.properties中文件路径
	 * @param imageInfoPath
	 *            获取配置文件systemConfig.properties中文件保存详细划分路径
	 * @param settingName
	 *            自定义名称
	 * @return 全新路径和文件地址
	 */
	public static String uploadImageFileSettingName(File imagePath,String imagePathFileName,Map<Object,Object> fileUrlConfig,String imageInfoPath,String settingName)  {
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
			//判断图片后缀是否符合要求，忽略大小写的判断
			if ( FileTypeUtil.checkIsImage(typeStr) ) {
				try {
					// 需要修改文件的后缀名称
					String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
					// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
					String randomCode=Utils.getRandomCode();
					//原始图片文件编译后的新名称
					newName = randomCode + newName;
					newName = settingName + newName;
					//定义新文件保存实际路径+配置文件systemConfig.properties中定义的文件生成目录规则：年月日
					String newImagePath = fileUrlConfig.get("shop") + "/"+ fileUrlConfig.get(imageInfoPath) + "/"+ FileTypeUtil.getSerial(new Date(), fileUrlConfig.get("fileRule")) + "/" ;
					//定义文件对象，需要加上盘符
					File savefile = new File((fileUrlConfig.get("fileUploadRoot") + "/"+ newImagePath), newName);
					//判断文件是否存在，不存在则新创建
					if (!savefile.getParentFile().exists()) {
						savefile.getParentFile().mkdirs();
					}
					FileUtils.copyFile(imagePath, savefile);
					imagePathFileName = newImagePath + newName;
					return imagePathFileName;
				} catch (IOException e) {
					e.printStackTrace();
					return "图片上传失败!";
				}
			}
		}
		return imagePathFileName;
	}
	/**
	 * 上传图片文件拓展名，生成新的文件名，非商城中的图片（-张攀攀）。
	 * 
	 * @param imagePath
	 *            传入文件路径
	 * @param imagePathFileName
	 *            文件路径名称包含文件名称
	 * @param fileUrlConfig
	 *            全局上传文件总根
	 * @param fileupload
	 *            配置文件systemConfig.properties中文件路径
	 * @param imageInfoPath
	 *            获取配置文件systemConfig.properties中文件保存详细划分路径
	 * @return 全新路径和文件地址
	 */
	public static String uploadImageFileNotShop(File imagePath,String imagePathFileName,Map<Object,Object> fileUrlConfig,String imageInfoPath)  {
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
			//判断图片后缀是否符合要求，忽略大小写的判断
			if ( FileTypeUtil.checkIsImage(typeStr) ) {
				try {
					// 需要修改文件的后缀名称
					String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
					// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
					String randomCode=Utils.getRandomCode();
					//原始图片文件编译后的新名称
					newName = randomCode + newName;
					//定义新文件保存实际路径+配置文件systemConfig.properties中定义的文件生成目录规则：年月日
					String newImagePath = fileUrlConfig.get(imageInfoPath) + "/"+ FileTypeUtil.getSerial(new Date(), fileUrlConfig.get("fileRule")) + "/" ;
					//定义文件对象，需要加上盘符
					File savefile = new File((fileUrlConfig.get("fileUploadRoot") + "/"+ newImagePath), newName);
					//判断文件是否存在，不存在则新创建
					if (!savefile.getParentFile().exists()) {
						savefile.getParentFile().mkdirs();
					}
					FileUtils.copyFile(imagePath, savefile);
					imagePathFileName = newImagePath + newName;
					return imagePathFileName;
				} catch (IOException e) {
					e.printStackTrace();
					return "图片上传失败!";
				}
			}
		}
		return imagePathFileName;
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
			return "上传失败!";
		}
	}
	/**
	 * 上传图片-自定义文件夹名在systemConfig中 
	 * 
	 * @author mf
	 * 
	 * @param imagePath
	 *            传入文件路径
	 * @param imagePathFileName
	 *            文件路径名称包含文件名称
	 * @param fileUrlConfig
	 *            全局上传文件总根
	 * @param fileupload
	 *            配置文件systemConfig.properties中文件路径
	 * @param dirName
	 *            配置文件systemConfig.properties中定义的文件夹名称
	 * @param imageInfoPath
	 *            获取配置文件systemConfig.properties中文件保存详细划分路径
	 * @return 全新路径和文件地址
	 */
	public static String uploadImageFileUseRandomDirName(File imagePath,String imagePathFileName,Map<Object,Object> fileUrlConfig,String dirName,String imageInfoPath)  {
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
			//判断图片后缀是否符合要求，忽略大小写的判断
			if ( FileTypeUtil.checkIsImage(typeStr) ) {
				try {
					// 需要修改文件的后缀名称
					String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
					// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
					String randomCode=Utils.getRandomCode();
					//原始图片文件编译后的新名称
					newName = randomCode + newName;
					//定义新文件保存实际路径+配置文件systemConfig.properties中定义的文件生成目录规则：年月日
					String newImagePath = fileUrlConfig.get(dirName)+ "/"+ fileUrlConfig.get(imageInfoPath) + "/"+ FileTypeUtil.getSerial(new Date(), fileUrlConfig.get("fileRule")) + "/" ;
					//定义文件对象，需要加上盘符
					File savefile = new File((fileUrlConfig.get("fileUploadRoot") + "/"+ newImagePath), newName);
					//判断文件是否存在，不存在则新创建
					if (!savefile.getParentFile().exists()) {
						savefile.getParentFile().mkdirs();
					}
					FileUtils.copyFile(imagePath, savefile);
					imagePathFileName = newImagePath + newName;
					return imagePathFileName;
				} catch (IOException e) {
					e.printStackTrace();
					return "图片上传失败!";
				}
			}
		}
		return imagePathFileName;
	}
}
