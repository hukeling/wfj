package basic.service.imp;
import java.util.List;

import util.service.BaseService;
import basic.dao.IKeyBookDao;
import basic.pojo.KeyBook;
import basic.service.IKeyBookService;
public class KeyBookService extends BaseService<KeyBook> implements IKeyBookService{
	private IKeyBookDao keyBookDao;
	public void setKeyBookDao(IKeyBookDao keyBookDao) {
		this.baseDao=this.keyBookDao=keyBookDao;
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
		return keyBookDao.findUnSame(column, whereCondition);
	}
}
