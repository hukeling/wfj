package gou.experience.service.imp;

import gou.experience.dao.IExperienceInfoDao;
import gou.experience.pojo.ExperienceInfo;
import gou.experience.service.IExperienceInfoService;
import util.service.BaseService;

public class ExperienceInfoService extends BaseService<ExperienceInfo> implements IExperienceInfoService {

	@SuppressWarnings("unused")
	private IExperienceInfoDao experienceInfoDao;

	public void setExperienceInfoDao(IExperienceInfoDao experienceInfoDao) {
		this.baseDao=this.experienceInfoDao = experienceInfoDao;
	}

	
}
