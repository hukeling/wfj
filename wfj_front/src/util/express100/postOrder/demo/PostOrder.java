package util.express100.postOrder.demo;

import java.util.HashMap;

import util.express100.bean.TaskRequest;
import util.express100.bean.TaskResponse;


public class PostOrder {

	public static void main(String[] args){
		TaskRequest req = new TaskRequest();
		req.setCompany("ems");
		req.setFrom("上海浦东新区");
		req.setTo("广东深圳南山区");
		req.setNumber("5124440192102");
		req.getParameters().put("callbackurl", "http://www.yourdmain.com/kuaidi");
		req.setKey("mHdSlmlZ4073");
		
		HashMap<String, String> p = new HashMap<String, String>(); 
		p.put("schema", "json");
		p.put("param", JacksonHelper.toJSON(req));
		try {
			String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
			TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
			if(resp.getResult()==true){
				//System.out.println("订阅成功");
			}else{
				//System.out.println("订阅失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
