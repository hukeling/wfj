package basic.service;
import java.util.List;

import util.service.IBaseService;
import basic.pojo.KeyBook;
public interface IKeyBookService  extends IBaseService <KeyBook> {
	/**
	 * 数据字典根据列名去重
	 * 
	 * @param column  列名
	 * 
	 * @param whereCondition  查询条件
	 * 
	 * @return 返回List集合
	 */
	public List<String> distinctType(String column,String whereCondition);
}
