package phone.service.imp;

import phone.dao.IPhoneFeedBackDao;
import phone.service.IPhoneFeedBackService;
import util.service.BaseService;

public class PhoneFeedBackService extends BaseService implements IPhoneFeedBackService {

	IPhoneFeedBackDao feedbackDao;

	public void setFeedbackDao(IPhoneFeedBackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}
	
}
