package util.action;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
/**
 * Action类 - 文件下载Action
 * 
 * */
public class DownLoadFileAction extends BaseAction {
	private static final long serialVersionUID = 3293386421681730095L;
	// 通用下载模块
	private String filename;
	private String downloadFileName;
	private InputStream downloadStream; // 这个输入流对应struts.xml中配置的那个downloadStream，两者必须一致
	private String downloadFileUrl; // 文件的相对路径
	public String downloadFile() throws Exception {
		if (!"".equals(downloadFileUrl) || downloadFileUrl !=null) {
			try {
				String zm=new String(downloadFileUrl.getBytes("ISO-8859-1"),"UTF-8");
				//获取下载路径
				URL url = new URL((String) fileUrlConfig.get("visitFileUploadRoot")+zm);
				String txt2 = null;
				if(downloadFileName.substring(0,downloadFileName.indexOf("_")).equals("QiYe")){
					txt2=new String(String.valueOf(shopConfig.get("companyListXLS")).getBytes("UTF-8"),"ISO-8859-1");
				}else if(downloadFileName.substring(0,downloadFileName.indexOf("_")).equals("CaiGouYuan")){
					txt2=new String(String.valueOf(shopConfig.get("sonaccountListXLS")).getBytes("UTF-8"),"ISO-8859-1");
				}else if(downloadFileName.substring(0,downloadFileName.indexOf("_")).equals("DianPu")){
					txt2=new String(String.valueOf(shopConfig.get("storeListXLS")).getBytes("UTF-8"),"ISO-8859-1");
				}else if(downloadFileName.substring(0,downloadFileName.indexOf("_")).equals("CaiWu")){
					txt2=new String(String.valueOf(shopConfig.get("caiwuListXLS")).getBytes("UTF-8"),"ISO-8859-1");
				}else{
					txt2=new String(String.valueOf(shopConfig.get("gerenListXLS")).getBytes("UTF-8"),"ISO-8859-1");
				}
				this.downloadFileName =new String(txt2);
				downloadStream = url.openStream();
				return  "success";
			} catch (IOException e) {
				e.printStackTrace();
				this.addActionError("下载文件失败");
				return "error";
			}
		} else {
			this.addActionError("下载文件失败");
			return "error";
		}
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public InputStream getDownloadStream() {
		return downloadStream;
	}
	public void setDownloadStream(InputStream downloadStream) {
		this.downloadStream = downloadStream;
	}
	public String getDownloadFileUrl() {
		return downloadFileUrl;
	}
	public void setDownloadFileUrl(String downloadFileUrl) {
		this.downloadFileUrl = downloadFileUrl;
	}
}
