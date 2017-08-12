package util.action;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import util.upload.FileUploadUtil;
/**
 * Action类 - 多文件上传Action
 * 
 * */
public class MultiFileOperatorAction extends BaseAction {
	private static final long serialVersionUID = 1476087700949260846L;
	private String fileFileName;
	private String fileContentType;
	private File file;
	private String folderName;
	private String entityName;
	private String id;
	private String deleteURL;
	/**
	 * upload 表示异步上传文件
	 */
	@SuppressWarnings("unchecked")
	public void uploadFile() {
		try {
			List <String> urlList=(List<String>)session.getAttribute("urlList");
			if(urlList==null)urlList=new ArrayList<String>();
			String savePath="";
			savePath=FileUploadUtil.uploadFile((String)fileUrlConfig.get("fileUploadRoot"),folderName+"/fj", fileFileName, file);
			urlList.add(savePath+"@"+fileFileName);//@为url和真是文件名称的分隔符
			session.setAttribute("urlList", urlList);
		} catch (Exception ex) {
			session.setAttribute("urlList", session.getAttribute("urlList"));
		}
	}
	/**
	 * 获取上传文件的地址
	 */
	@SuppressWarnings("unchecked")
	public void getURLResult() throws Exception {
		List <String> urlList=(List<String>)session.getAttribute("urlList");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONArray json=JSONArray.fromObject(urlList);  
        out.print(json.toString());  
        out.flush(); 
        out.close();
	}
	/**
	 * 移除session的文件
	 */
	public void reomveSessionInfo() throws Exception {
		request.getSession().removeAttribute("urlList");
	}
	/**
	 * 删除文件
	 */
	public void deleteFile() throws Exception {
		request.setCharacterEncoding("UTF-8");
		String url=request.getParameter("deleteURL");
		url=url.substring(0,url.indexOf("@"));
		String filePath=((String)fileUrlConfig.get("fileUploadRoot")+"\\"+url).replace("/", "\\");
		File file = new File(filePath);
	    if(file.exists()){
	      boolean d = file.delete();
	    }
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeleteURL() {
		return deleteURL;
	}
	public void setDeleteURL(String deleteURL) {
		this.deleteURL = deleteURL;
	}
}
