package util.other;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class InputExcel {
	/**
	 * 将商品分类及品牌信息由Excel导入数据库
	 */
	public static List getData(File imagePath,String imagePathFileName)throws IOException{
		Workbook book;//定义excel文件 
		List dataLi=new ArrayList(); 
			try {
				book = Workbook.getWorkbook(new File(imagePath.getPath()));//把文件加载到内存
				Sheet[] sheets = book.getSheets();//获取sheet
				for(Sheet sheet:sheets){
				    int rows = sheet.getRows();//获取当前sheet里边的总行数
				    for(int a=1;a<rows;a++){//循环行
				    	List li=new ArrayList();
						Cell[] nowRow = sheet.getRow(a);//获取当前行所有列 
							for(Cell cell:nowRow){//循环列
								li.add(cell.getContents());
							}
							if(li.get(0)!=null){
							dataLi.add(li);
							}
				    }		 
				}
			} catch (BiffException e) {
				e.printStackTrace();
			}
		 return dataLi;
	}
}
