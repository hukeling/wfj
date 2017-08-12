package tang.impression.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import phone.util.JsonIgnore;

import tang.impression.pojo.TangLbt;
import tang.impression.service.ITangLbtService;
import util.action.BaseAction;
import util.other.JSONFormatDate;

public class TangLbtAction extends BaseAction {
	private static final long serialVersionUID = -2143814674174970273L;

	private ITangLbtService tangLbtService;

	
	/**
	 * http://192.168.1.115:8080/wfj_front/phone/tangLbt/getTangLbt.action
	 * 获取所有轮播图
	 * @param tangLbtService
	 * @throws IOException 
	 */
	public void getTangLbt() throws IOException{
		List<TangLbt> lbtList=new ArrayList<TangLbt>();
		lbtList=tangLbtService.findObjects("where o.isShow=1 and o.showLocation=2");
		JsonIgnore.outputJo(response, lbtList, JsonIgnore.getIgnoreTangLbt());
	}
	
	
	
	public void setTangLbtService(ITangLbtService tangLbtService) {
		this.tangLbtService = tangLbtService;
	}
	
	
	
	
	
}
