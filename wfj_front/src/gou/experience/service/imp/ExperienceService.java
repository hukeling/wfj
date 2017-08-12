package gou.experience.service.imp;

import gou.experience.dao.IExperienceDao;
import gou.experience.pojo.Experience;
import gou.experience.service.IExperienceService;
import util.service.BaseService;

public class ExperienceService extends BaseService<Experience> implements IExperienceService {

	@SuppressWarnings("unused")
	private IExperienceDao experienceDao;

	public void setExperienceDao(IExperienceDao experienceDao) {
		this.baseDao=this.experienceDao = experienceDao;
	}
	
}
