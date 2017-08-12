package util.action;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
/**
 * Action类 - 图片上传Action
 * 
 * */
public class ImageAction extends BaseAction{
	private static final long serialVersionUID = 572146812454l;
	private static final int BUFFER_SIZE = 16 * 1024;
	private File file;
	private String type;
	private String pagePath;
	public String imageUpload() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		try {
			String fileName = new Date().getTime()+ type.substring(type.length()- 4, type.length());
			String path = ServletActionContext.getServletContext().getRealPath("\\")+ "upload\\image\\";
			File imageFile = new File(path + fileName);
			pagePath = "upload\\image\\" + fileName;
			session.setAttribute("pagePath", pagePath);
			copy(file, imageFile);
			return "imageUploadSuccess";
		} catch (Exception e) {
			request.setAttribute("errorMessage", "图片上传未成功");
			return "imageUploadFail";
		}
	}
	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public static int getBufferSize() {
		return BUFFER_SIZE;
	}
}
