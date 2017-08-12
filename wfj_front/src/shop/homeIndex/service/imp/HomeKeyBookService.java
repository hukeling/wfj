package shop.homeIndex.service.imp;
import java.util.List;

import shop.homeIndex.dao.IHomeKeyBookDao;
import shop.homeIndex.pojo.HomeKeyBook;
import shop.homeIndex.service.IHomeKeyBookService;
import util.service.BaseService;
public class HomeKeyBookService extends BaseService<HomeKeyBook> implements IHomeKeyBookService{
	private IHomeKeyBookDao homeKeyBookDao;
	public void setHomeKeyBookDao(IHomeKeyBookDao homeKeyBookDao) {
		this.baseDao=this.homeKeyBookDao = homeKeyBookDao;
	}
	/**
	 * 数据字典根据列名去重
	 * 
	 * @param column  列名
	 * 
	 * @param whereCondition  查询条件
	 * 
	 * @return 返回List集合
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List distinctType(String column,String whereCondition){
		return homeKeyBookDao.findUnSame(column, whereCondition);
	}
}
