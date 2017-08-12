package tang.impression.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import phone.util.JsonIgnore;

import tang.impression.pojo.TangHome;
import tang.impression.service.ITangHomeService;
import util.action.BaseAction;

public class TangHomeAction extends BaseAction {

	private static final long serialVersionUID = -2146651220072479803L;
	
	private ITangHomeService tangHomeService;
	
/**
 *获取唐智府首页分类
 *http://192.168.1.115:8080/wfj_front/phone/tangHome/getTHomeCategory.action
 * @throws IOException
 */
	public void getTHomeCategory() throws IOException{
		List<TangHome>  homeList=new ArrayList<TangHome>();
		homeList=tangHomeService.findObjects("where o.isShow=1");
		
		JsonIgnore.output(response, homeList);
	}
	
	

	public void setTangHomeService(ITangHomeService tangHomeService) {
		this.tangHomeService = tangHomeService;
	}
	
	

}
