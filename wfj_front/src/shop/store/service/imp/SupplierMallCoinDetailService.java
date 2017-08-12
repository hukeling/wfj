package shop.store.service.imp;

import shop.store.dao.ISupplierMallCoinDetailDao;
import shop.store.pojo.SupplierMallCoinDetail;
import shop.store.service.ISupplierMallCoinDetailService;
import util.service.BaseService;
/**
 * 供应商商城币收支明细Service实现类
 * @author lmh
 * **/
public class SupplierMallCoinDetailService extends BaseService<SupplierMallCoinDetail> implements ISupplierMallCoinDetailService{
	@SuppressWarnings("unused")
	private ISupplierMallCoinDetailDao supplierMallCoinDetailDao;
	public void setSupplierMallCoinDetailDao(
			ISupplierMallCoinDetailDao supplierMallCoinDetailDao) {
		this.baseDao=this.supplierMallCoinDetailDao = supplierMallCoinDetailDao;
	}
}
