package tang.impression.action;

import java.io.IOException;

import phone.util.JsonIgnore;
import tang.impression.pojo.TangImpression;
import tang.impression.service.ITangImpressionService;
import util.action.BaseAction;

public class TangImpressionAction extends BaseAction{
 
	private static final long serialVersionUID = -4542321187658194273L;
	private ITangImpressionService tangImpressionService;
	
	private Integer type;//判断1是PC端请求，0或null是手机端请求
	/**
	 * http://192.168.1.115:8080/wfj_front/phone/tangImpression/getImpression.action
	 * 获取一条印象王府井信息
	 * @throws IOException
	 */
	public void getImpression() throws IOException{
		String sql="where o.isShow=1";
		TangImpression ti=(TangImpression)tangImpressionService.getObjectByParams(sql);
		
		if(type!=null&&type==1){
			JsonIgnore.outputobj(response, ti);
		}else{
			JsonIgnore.outputobj(response, ti,JsonIgnore.getIgnoreTangImpression());
		}
		
	}
	
	
	
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}





	public void setTangImpressionService(
			ITangImpressionService tangImpressionService) {
		this.tangImpressionService = tangImpressionService;
	}
	
	

}
