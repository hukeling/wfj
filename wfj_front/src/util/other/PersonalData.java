package util.other;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.util.HttpURLConnection;

public class PersonalData
{
	public static void captureHtml(String info) throws Exception {
		String strURL = "http://item.grainger.cn/s/"+info+"/";
		URL url = new URL(strURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn
				.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		int beginIx = buf.indexOf("<th>分类</th>");
		int endIx = buf.indexOf("<th>品牌</th>");
		String result = buf.substring(beginIx, endIx);
		result= result.replaceAll("<th>分类</th><td><div id=\"summary_category_list\" class=\"summary_list split_line5\"><div class=\"split_list\">", "");
		result =result.replaceAll("</div></div></td></tr><tr>","");
		String[] split = result.split("</a>");
		List<String> stringList=new ArrayList<String>();
		for(String s:split){
			s=s.substring(s.lastIndexOf(">")+1, s.length());
			stringList.add(s);
		}
		for(String s2:stringList){
			//System.out.println(s2);
		}
	}
	public static void main(String[] args) throws Exception{
		captureHtml("115.28.83.213");
	}
}