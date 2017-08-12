package tang.impression.service.imp;

import tang.impression.dao.ITangHomeDao;
import tang.impression.pojo.TangHome;
import tang.impression.service.ITangHomeService;
import util.service.BaseService;

public class TangHomeService extends BaseService<TangHome> implements ITangHomeService {
	@SuppressWarnings("unused")
	private ITangHomeDao tangHomeDao;

	public void setTangHomeDao(ITangHomeDao tangHomeDao) {
		this.baseDao=this.tangHomeDao = tangHomeDao;
	}
	
	
}
