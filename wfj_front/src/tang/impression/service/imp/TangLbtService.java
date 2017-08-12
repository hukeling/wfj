package tang.impression.service.imp;

import tang.impression.dao.ITangLbtDao;
import tang.impression.pojo.TangLbt;
import tang.impression.service.ITangLbtService;
import util.service.BaseService;

public class TangLbtService extends BaseService<TangLbt> implements ITangLbtService {

	@SuppressWarnings("unused")
	private ITangLbtDao tangLbtDao;

	public void setTangLbtDao(ITangLbtDao tangLbtDao) {
		this.baseDao=this.tangLbtDao = tangLbtDao;
	}

	 
	
	
}
