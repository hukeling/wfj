package util.other;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 工具类 - 属性文件处理
 * 
 * */
@SuppressWarnings("unchecked")
public class TestProperties extends ActionSupport {
	private static final long serialVersionUID = -2106492186563403345L;
	static{
		String filePath = System.getProperty("user.dir")+"/fileUrlConfig.properties";
		Properties ps = new Properties();
		try {
			InputStream	in = new BufferedInputStream(new FileInputStream(filePath));
			ps.load(in);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}catch (IOException e) {
			//log4j
			e.printStackTrace();
		}
		Map fileUrlConfig = new HashMap();
		fileUrlConfig = ps;
		Set<Map.Entry<String,String>> set = fileUrlConfig.entrySet();
		Iterator it = set.iterator(); 
		while(it.hasNext()){
			Map.Entry<String,String> me = (Map.Entry<String,String>) it.next();
			String key = (String) me.getKey();
			String value = (String) me.getValue();
//			System.out.println("key is =  "+key+" , value is =  "+value);
		}
    }
	public TestProperties() {
	}
}
