package tang.tangProduct.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import tang.tangProduct.dao.ITangProductDao;
import tang.tangProduct.pojo.TangProduct;
import tang.tangProduct.service.ITangProductService;
import util.service.BaseService;
@Service
public class TangProductService extends BaseService<TangProduct> implements ITangProductService {

	@Resource
	private ITangProductDao tangProductDao;
	
	public void setTangProductDao(ITangProductDao tangProductDao) {
		super.setBaseDao(tangProductDao);
		this.baseDao =this.tangProductDao = tangProductDao;
	}

}
