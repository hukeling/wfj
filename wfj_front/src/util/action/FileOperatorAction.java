package util.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import util.upload.ImageFileUploadUtil;
/**
 * Action类 - 文件操作Action
 * 
 * */
@SuppressWarnings( { "serial" })
public class FileOperatorAction extends BaseAction {
	private File fileupload;
	private String fileuploadFileName; // 上传来的文件的名字
	private String fileUrl;
	private String fileUploadKey;
	private String categoryZipCode;
	private String code;
	/**
	 * asyncUploadFile 表示异步上传文件
	 */
	public void asyncUploadFile() {
		PrintWriter out;
		try {
			response.setHeader("Content-Type", "text/plain;charset=UTF-8");
			out = response.getWriter();
			String message = ImageFileUploadUtil.uploadImageFileNotShop(fileupload, fileuploadFileName, fileUrlConfig, fileUploadKey);
			out.print(message + "," + fileuploadFileName);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String uploadImage() throws Exception {
		return "success";
	}
	public File getFileupload() {
		return fileupload;
	}
	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileuploadFileName() {
		return fileuploadFileName;
	}
	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}
	public String getFileUploadKey() {
		return fileUploadKey;
	}
	public void setFileUploadKey(String fileUploadKey) {
		this.fileUploadKey = fileUploadKey;
	}
	public String getCategoryZipCode() {
		return categoryZipCode;
	}
	public void setCategoryZipCode(String categoryZipCode) {
		this.categoryZipCode = categoryZipCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
