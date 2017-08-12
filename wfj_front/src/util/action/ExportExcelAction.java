package util.action;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;

/**
 * Action类 - 文件导出Execl文档Action
 * 
 * */
public class ExportExcelAction  extends BaseAction {
	private static final long serialVersionUID = 4387011828388708189L;
	private InputStream excelInputStream; // 这个输入流对应struts.xml中配置的那个excelStream，两者必须一致
	private String fileName; // 这个名称就是用来传给struts.xml中的${fileName}的
	//通用下载模块
	private InputStream downloadStream; // 这个输入流对应struts.xml中配置的那个downloadStream，两者必须一致
	private String downloadFileName; // 这个名称就是用来传给struts.xml中的${downloadFileName}的
	private String downloadFileUrl; // 文件的相对路径
	
	public String exportExcel() throws Exception {
		/*
		第一步：获取查询参数 这个应用要导出的其实是用户查询返回的结果列表（List），而“导出Excel”这个按钮就是放到查询结果显示页面上的。
			 在这个页面上，用一个form来将用户上一步查询的参数保存下来，然后当用户点击“导出Excel”按钮的时候，
			 其实是再执行了一次form表单提交来执行本Action。这两次查询其实是大同小异的，
			 只是显示查询结果的时候还需要做一个分页的功能，而这里导出则不用进行分页。
		第二步：得到两个List
			 第一个List保存的是列表的头部信息
			 第二个List保存的是相对应的数据，数据形式是二维List<List<String>> columnRowsList组成的二维表模式
			 (List<String>) session.getAttribute("columnNames"),(List<List<String>>) session.getAttribute("columnNames")
		 第三步：创建Excel工作簿。
		 */
		HSSFWorkbook workbook = getWorkbook((List<String>) session.getAttribute("columnNames"),(List<List<String>>) session.getAttribute("columnValues"));
		if (workbook != null) {
			try {
		// 第四步：将工作簿写入最上面定义的InputStream流——名称为excelStream，这个名字对应struts.xml中配置的inputName参数
				excelInputStream = workbook2InputStream(workbook);
				this.fileName=getCurrentDate( (String) session.getAttribute("moduleName") );
				this.fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				session.removeAttribute("columnNames");//去除session中保存的数据
				session.removeAttribute("columnValues");//去除session中保存的数据
				return  "success";
			} catch (IOException e) {
				e.printStackTrace();
				this.addActionError("创建Excel失败");
				return "error";
			}
		} else {
			this.addActionError("创建Excel失败");
			return "error";
		}
	}
	/**
	 * 通用下载模块
	 * **/
	/*@Action(
			value = "/download/downloadFile", 
			results = {
				@Result(name = "success", type = "stream", params = {
						"contentType", "application/x-msdownload;charset=UTF-8", 
						"inputName","downloadStream", 
						"contentDisposition",	"attachment;filename=\"${downloadFileName}\"", 
						"bufferSize","2048" }),
				@Result(name = "error", location = "/error.jsp" , type = "dispatcher")
			}
		)*/
		public String downloadFile() throws Exception {
			if (!"".equals(downloadFileUrl) || downloadFileUrl !=null) {
				// 【这个方法是临时的，以后文档服务器 ，需要去掉次方法】
				if(downloadFileUrl.split("http:").length >1){
					//如果地址是 http://10.18.8.21http://10.18.8.26:7888/SJCJ/process/xxx.doc ，则截取地址
					downloadFileUrl = downloadFileUrl.substring(downloadFileUrl.lastIndexOf("http:"));
				}
				try {
					URL url = new URL(downloadFileUrl);
					downloadStream = url.openStream();
					String downloadFileNameTemp = new String(new BASE64Decoder().decodeBuffer(downloadFileName.replaceAll(" ", "+")));
					this.downloadFileName = java.net.URLEncoder.encode(downloadFileNameTemp, "UTF-8");
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
	/** 下面这个方法是将list转换为Excel工作表的 */
	public HSSFWorkbook getWorkbook(List<String> columnNamesList,List<List<String>> columnRowsList) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet( (String) session.getAttribute("moduleName") );//(String) session.getAttribute("moduleName") 重新命名excel的sheet
		// 读取list中的第一个元素，根据它来确定工作表的列名，以及输出数据所对应的方法数组
		HSSFRow row = sheet.createRow(0); // 创建第1行，也就是输出表头
		HSSFCell cell;
		for (int i = 0; i < columnNamesList.size(); i++) {
			cell = row.createCell(i); // 创建第i列
			cell.setCellValue(columnNamesList.get(i));
		}
		// 下面是输出各行的数据
		//List<List<String>> columnRowsList 是一个list组成的二维数组 最外层是行，最里曾是列
		for (int i = 0; i < columnRowsList.size(); i++) {
			row = sheet.createRow(i + 1);// 创建第i+1行
			for (int j = 0; j < columnRowsList.get(i).size(); j++) {
				cell=row.createCell(j);//创建第j列
				cell.setCellValue(columnRowsList.get(i).get(j).toString());
			}
		}
		return workbook;
	}
	/**将Workbook写入到InputStream**/ 
	public InputStream workbook2InputStream(HSSFWorkbook workbook) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		baos.flush();
		byte[] buf = baos.toByteArray();
		InputStream excelStream = new ByteArrayInputStream(buf, 0, buf.length);
		baos.close();
		return excelStream;
	}
	/**得到当前日期**/
	public String getCurrentDate(String name) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String month_ = new String("" + month);
		if (month < 10) {
			month_ = "0" + month;
		}
		int day = c.get(Calendar.DAY_OF_MONTH);
		String day_ = new String("" + day);
		if (day < 10) {
			day_ = "0" + day;
		}
		return name+ "_"+year + "-" + month_ + "-" + day_ + "";
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getDownloadStream() {
		return downloadStream;
	}
	public InputStream getExcelInputStream() {
		return excelInputStream;
	}
	public void setExcelInputStream(InputStream excelInputStream) {
		this.excelInputStream = excelInputStream;
	}
	public void setDownloadStream(InputStream downloadStream) {
		this.downloadStream = downloadStream;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public String getDownloadFileUrl() {
		return downloadFileUrl;
	}
	public void setDownloadFileUrl(String downloadFileUrl) {
		this.downloadFileUrl = downloadFileUrl;
	}
}
