package util.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
/**
 * Action类 - 上传文件Action
 * 
 * */
@SuppressWarnings("serial")
public class UploadAction extends BaseAction {
	private File smallImg;
	private File bigImg;
	private String smallImgUrl;
	private String bigImgUrl;
	private String smallImgContentType;
	private String bigImgContentType;
	private String smallImgFileName;
	private String bigImgFileName;
	private String smallImgFileName_TIME;
	private String bigImgFileName_TIME;
	public void uploadImgs() throws IOException {
		String realpath = ServletActionContext.getServletContext().getRealPath(
				"/image");
		File file = new File(realpath);
		if (!file.exists())
			file.mkdirs();
		if (smallImg != null) {
			smallImgFileName_TIME = generateFilename(smallImgFileName);
			FileUtils.copyFile(smallImg, new File(file, smallImgFileName_TIME));
			smallImgUrl = request.getContextPath() + "/image"
					+ smallImgFileName_TIME;
		}
		if (bigImg != null) {
			bigImgFileName_TIME = generateFilename(bigImgFileName);
			FileUtils.copyFile(bigImg, new File(file, bigImgFileName_TIME));
			bigImgUrl = request.getContextPath() + "/image"
					+ bigImgFileName_TIME;
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("smallImgUrl", smallImgUrl);
		jo.accumulate("bigImgUrl", bigImgUrl);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
	}
	@SuppressWarnings("null")
	public String generateFilename(String filename) {
		SimpleDateFormat simpleDateFormat = null;
		new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fixName = filename.substring(filename.lastIndexOf("."));
		return "/" + simpleDateFormat.format(new Date()).toString().toUpperCase() + "_" + fixName;
	}
	public File getSmallImg() {
		return smallImg;
	}
	public void setSmallImg(File smallImg) {
		this.smallImg = smallImg;
	}
	public File getBigImg() {
		return bigImg;
	}
	public void setBigImg(File bigImg) {
		this.bigImg = bigImg;
	}
	public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public String getBigImgUrl() {
		return bigImgUrl;
	}
	public void setBigImgUrl(String bigImgUrl) {
		this.bigImgUrl = bigImgUrl;
	}
	public String getSmallImgContentType() {
		return smallImgContentType;
	}
	public void setSmallImgContentType(String smallImgContentType) {
		this.smallImgContentType = smallImgContentType;
	}
	public String getBigImgContentType() {
		return bigImgContentType;
	}
	public void setBigImgContentType(String bigImgContentType) {
		this.bigImgContentType = bigImgContentType;
	}
	public String getSmallImgFileName() {
		return smallImgFileName;
	}
	public void setSmallImgFileName(String smallImgFileName) {
		this.smallImgFileName = smallImgFileName;
	}
	public String getBigImgFileName() {
		return bigImgFileName;
	}
	public void setBigImgFileName(String bigImgFileName) {
		this.bigImgFileName = bigImgFileName;
	}
	public String getSmallImgFileName_TIME() {
		return smallImgFileName_TIME;
	}
	public void setSmallImgFileName_TIME(String smallImgFileNameTIME) {
		smallImgFileName_TIME = smallImgFileNameTIME;
	}
	public String getBigImgFileName_TIME() {
		return bigImgFileName_TIME;
	}
	public void setBigImgFileName_TIME(String bigImgFileNameTIME) {
		bigImgFileName_TIME = bigImgFileNameTIME;
	}
}
