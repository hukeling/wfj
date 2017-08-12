package phone.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import phone.dao.IPhoneLBTDao;
import phone.pojo.PhoneLBT;
import phone.service.IPhoneLBTService;

import util.service.BaseService;

/**
 * 首页轮播图Service
 * 
 * @author xxk
 * @time 20140113
 */
@Service
public class PhoneLBTService extends BaseService<PhoneLBT> implements
		IPhoneLBTService {
	@Resource
	private IPhoneLBTDao phoneLBTDao;

	public void setphoneLBTDao(IPhoneLBTDao phoneLBTDao) {
		super.setBaseDao(phoneLBTDao);
		this.baseDao = this.phoneLBTDao = phoneLBTDao;
	}

	public IPhoneLBTDao getPhoneLBTDao() {
		return phoneLBTDao;
	}
	
}
