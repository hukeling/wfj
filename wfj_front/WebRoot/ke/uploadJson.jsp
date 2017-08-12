<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
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
String savePath = "";

//文件保存目录URL
String saveUrl  = "";

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
	savePath = String.valueOf(mapKey.get("ke_file_path"))+"/"+String.valueOf(mapKey.get("ke_file"))+"/"+subSys;
	System.out.println(savePath);
	saveUrl = String.valueOf(mapKey.get("visitFileUploadRoot"))+String.valueOf(mapKey.get("ke_file")) +"/"+ subSys;
}else{
	//得到FCK中绝对路径：D:\tomcat6\webapps\lqshop\ userfiles\image
	savePath = pageContext.getServletContext().getRealPath("/") +"/"+String.valueOf(mapKey.get("ke_file"))+"/"+subSys;
	saveUrl  = request.getContextPath() +"/"+String.valueOf(mapKey.get("ke_file"))+"/"+ subSys;
}
File savePathFile=new File(savePath);
if(!savePathFile.exists()){
	savePathFile.mkdirs();
}


//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
long maxSize = 1000000;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}
//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.isDirectory()){
	out.println(getError("上传目录不存在。"));
	return;
}
//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}
/**/
String dirName = request.getParameter("dir");
if (dirName == null) {
	dirName = "image";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
}
//创建文件夹
savePath +="/"+ dirName + "/";
saveUrl +="/"+  dirName + "/"; 
File saveDirFile = new File(savePath);
if (!saveDirFile.exists()) {
	saveDirFile.mkdirs();
}
SimpleDateFormat sdf = new SimpleDateFormat(String.valueOf(mapKey.get("folder_renameRule")));
String ymd = sdf.format(new Date());
savePath += ymd + "/";
saveUrl += ymd + "/";
File dirFile = new File(savePath);
if (!dirFile.exists()) {
	dirFile.mkdirs();
}

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return;
		}

		SimpleDateFormat df = new SimpleDateFormat(String.valueOf(mapKey.get("file_renameRule")));
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		try{
			File uploadedFile = new File(savePath, newFileName);
			item.write(uploadedFile);
		}catch(Exception e){
			out.println(getError("上传文件失败。"));
			return;
		}

		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl + newFileName);
		out.println(obj.toJSONString());
	}
} 
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>