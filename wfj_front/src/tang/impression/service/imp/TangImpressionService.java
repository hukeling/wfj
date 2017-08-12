package tang.impression.service.imp;

import tang.impression.dao.ITangImpressionDao;
import tang.impression.pojo.TangImpression;
import tang.impression.service.ITangImpressionService;
import util.service.BaseService;

public class TangImpressionService extends BaseService<TangImpression> implements ITangImpressionService {
	@SuppressWarnings("unused")
	private ITangImpressionDao tangImpressionDao;

	public void setTangImpressionDao(ITangImpressionDao tangImpressionDao) {
		this.baseDao =this.tangImpressionDao = tangImpressionDao;
	}
}
