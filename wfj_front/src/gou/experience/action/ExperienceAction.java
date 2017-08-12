package gou.experience.action;

import gou.experience.pojo.Experience;
import gou.experience.pojo.ExperienceInfo;
import gou.experience.service.IExperienceInfoService;
import gou.experience.service.IExperienceService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import phone.util.JsonIgnore;

import util.action.BaseAction;

public class ExperienceAction extends BaseAction {
	private static final long serialVersionUID = 5481092316530461035L;

	private IExperienceService experienceService;
	private IExperienceInfoService experienceInfoService; 
	private String experienceId;
	private String experienceType;
	
	/**
	 * http://192.168.1.115:8080/wfj_front/phone/experience/getAllExperience.action?experienceType=travel
	 * 根据类型获取所有体验信息
	 * @throws IOException 
	 */
	public void getAllExperience() throws IOException{
		List<Experience> experienceList=new ArrayList<Experience>();
		String sql="where o.isShow=1 and o.experienceType='"+experienceType+"'";
		experienceList=experienceService.findObjects("where o.isShow=1 and o.experienceType='"+experienceType+"'");
		for(Experience e:experienceList){
			List<ExperienceInfo> expInfoList=new ArrayList<ExperienceInfo>();
			expInfoList=experienceInfoService.findObjects("where o.isShow=1 and o.experienceId="+e.getExperienceId()+"order by o.sortcode");
			e.setExpInfoList(expInfoList);
		}
		JsonIgnore.outputJo(response, experienceList, JsonIgnore.getIgnoreExperience());
	}
	
	/**
	 * http://192.168.1.115:8080/wfj_front/phone/experience/getExperienceById.action?experienceId=2
	 * 根据体验Id查询体验详情
	 * @param experienceService
	 * @throws IOException 
	 */
	public void getExperienceById() throws IOException{
		Experience exp=new Experience();
		exp=(Experience)experienceService.getObjectByParams("where o.isShow=1 and o.experienceId="+experienceId);
		List<ExperienceInfo> expInfoList=new ArrayList<ExperienceInfo>();
		expInfoList=experienceInfoService.findObjects("where o.isShow=1 and o.experienceId="+exp.getExperienceId()+"order by o.sortcode");
		exp.setExpInfoList(expInfoList);
		if(exp.getExperienceId()!=null){
			JsonIgnore.outputobj(response, exp, JsonIgnore.getIgnoreExperience2());
		}
		
	}
	
	public String getExperienceType() {
		return experienceType;
	}

	public void setExperienceType(String experienceType) {
		this.experienceType = experienceType;
	}

	public String getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(String experienceId) {
		this.experienceId = experienceId;
	}

	public void setExperienceService(IExperienceService experienceService) {
		this.experienceService = experienceService;
	}

	public void setExperienceInfoService(
			IExperienceInfoService experienceInfoService) {
		this.experienceInfoService = experienceInfoService;
	}
	
	
}
