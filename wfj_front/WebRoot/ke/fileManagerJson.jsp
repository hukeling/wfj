<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.json.simple.*" %>
<%

/**
 * KindEditor JSP
 *
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 *
 */
//子模块名称，如cms、shop、bbs要和systemConfig.properties中的key一致
String subSys=request.getParameter("subSys");
//文件保存目录路径
 String rootPath = "";

 //文件保存目录URL
 String rootUrl  = "";
 /* ***********************************************
  * 自定义文件路径配置信息 
  * ***********************************************
  * */
 //得到初始化相对路径，在项目根路径下，比如：/userfiles/image
 //加载systemConfig.properties文件中配置文件，得到是否开启自定义路径
 Boolean fileOpen = false;
 //加载全局初始化配置文件 systemConfig.properties 中属性
 Map mapKey = (Map) (getServletContext().getAttribute("fileUrlConfig"));
 fileOpen = Boolean.parseBoolean( String.valueOf(mapKey.get("ke_file_open")) ) ;
 if (fileOpen == null) fileOpen = false ;
 if(fileOpen){
 	//得到FCK中自定义的绝对路径：X:/shopjsp/userfiles/image
 	rootPath = String.valueOf(mapKey.get("ke_file_path"))+"/"+String.valueOf(mapKey.get("ke_file"))+"/"+subSys;
 	rootUrl = String.valueOf(mapKey.get("visitFileUploadRoot"))+String.valueOf(mapKey.get("ke_file"))+"/"+subSys;
 }else{
 	//得到FCK中绝对路径：D:\tomcat6\webapps\lqshop\ userfiles\image
 	rootPath = pageContext.getServletContext().getRealPath("/") +"/"+String.valueOf(mapKey.get("ke_file"))+"/"+subSys;
 	rootUrl  = request.getContextPath() +"/"+String.valueOf(mapKey.get("ke_file"))+"/"+subSys;
 }

//图片扩展名
String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

String dirName = request.getParameter("dir");
if (dirName != null) {
	if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
		out.println("无效的路径名");
		return;
	}
	rootPath += "/"+dirName + "/";
	rootUrl += "/"+dirName + "/";
	File saveDirFile = new File(rootPath);
	if (!saveDirFile.exists()) {
		saveDirFile.mkdirs();
	}
}
//根据path参数，设置各路径和URL
String path = request.getParameter("path") != null ? request.getParameter("path") : "";
String currentPath = rootPath + path;
String currentUrl = rootUrl + path;
String currentDirPath = path;
String moveupDirPath = "";
if (!"".equals(path)) {
	String str = currentDirPath.substring(0, currentDirPath.length() - 1);
	moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
}

//排序形式，name or size or type
String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

//不允许使用..移动到上一级目录
if (path.indexOf("..") >= 0) {
	out.println("Access is not allowed.");
	return;
}
//最后一个字符不是/
if (!"".equals(path) && !path.endsWith("/")) {
	out.println("Parameter is not valid.");
	return;
}
//目录不存在或不是目录
File currentPathFile = new File(currentPath);
if(!currentPathFile.isDirectory()){
	out.println("Directory does not exist.");
	return;
}

//遍历目录取的文件信息
List<Hashtable> fileList = new ArrayList<Hashtable>();
if(currentPathFile.listFiles() != null) {
	for (File file : currentPathFile.listFiles()) {
		Hashtable<String, Object> hash = new Hashtable<String, Object>();
		String fileName = file.getName();
		if(file.isDirectory()) {
			hash.put("is_dir", true);
			hash.put("has_file", (file.listFiles() != null));
			hash.put("filesize", 0L);
			hash.put("is_photo", false);
			hash.put("filetype", "");
		} else if(file.isFile()){
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			hash.put("is_dir", false);
			hash.put("has_file", false);
			hash.put("filesize", file.length());
			hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
			hash.put("filetype", fileExt);
		}
		hash.put("filename", fileName);
		hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
		fileList.add(hash);
	}
}

if ("size".equals(order)) {
	Collections.sort(fileList, new SizeComparator());
} else if ("type".equals(order)) {
	Collections.sort(fileList, new TypeComparator());
} else {
	Collections.sort(fileList, new NameComparator());
}
JSONObject result = new JSONObject();
result.put("moveup_dir_path", moveupDirPath);
result.put("current_dir_path", currentDirPath);
result.put("current_url", currentUrl);
result.put("total_count", fileList.size());
result.put("file_list", fileList);

response.setContentType("application/json; charset=UTF-8");
out.println(result.toJSONString());
%>
<%!
public class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
		}
	}
}
public class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
				return 1;
			} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
public class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
		}
	}
}
%>