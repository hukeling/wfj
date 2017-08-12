package tuan.service.imp;

import tuan.dao.ITuanProductDao;
import tuan.pojo.TuanProduct;
import tuan.service.ITuanProductService;
import util.service.BaseService;
/**
 * 商品Service接口实现类
 * @author 
 *
 */
public class TuanProductService extends BaseService  <TuanProduct> implements ITuanProductService{
	@SuppressWarnings("unused")
	private ITuanProductDao tuanProductDao;

	public void setTuanProductDao(ITuanProductDao tuanProductDao) {
		this.baseDao = this.tuanProductDao = tuanProductDao;
	}

}