package util.dao;
import java.util.List;
import java.util.Map;

import util.pojo.BaseEntity;
public interface IBaseDao<T extends BaseEntity> {
	/**
	 * 添加
	 */
	public T saveObject(Object obj);
	/**
	 * 修改
	 */
	public T updateObject(Object obj);
	/**
	 * 保存或修改
	 */
	public T saveOrUpdateObject(Object obj);
	/**
	 * 通过指定 的Id删除一条记录
	 */
	public boolean deleteObjectById(String id);
	/**
	 * 通过指定 的pojo删除一条记录
	 */
	public boolean deleteObject(Object obj);
	/**
	 * 通过指定 的条件删除记录
	 */
	public boolean deleteByParams(String whereCondition);
	/**
	 * 通过指定 的Id查找 id 是整数
	 */
	public T getObjectById(String id);
	/**
	 * 通过组合条件来查询一个对象
	 */
	public T get(String whereCondition);
	/**
	 * 获取一条数据 firstIndex 开始索引
	 */
	public T get(int firstIndex, String whereCondition);
	/**
	 * 获取随机记录
	 */
	public T getRandom(String whereCondition);
	/**
	 * 查询所有的记录的分页数目
	 */
	public int getCount(String whereCondition);
	/**
	 * 查询所有的记录的分页数目自定义SQL
	 */
	public int getCountByHQL(String whereCondition);
	/**
	 * 获取一些数据 column：排重的列 params：where 条件参数
	 */
	public List <T> findUnSame(String column, String whereCondition);
	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数
	 */
	public List<T> findSome(int firstIndex, int maxResult, String whereCondition);
	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数
	 */
	public List<T> findSome(String [] selectColumns, int firstIndex, int maxResult, String whereCondition);
	/**
	 * 求和 field 开始索引 params where 条件参数
	 */
	public T getSum(String field, String whereCondition);
	/**
	 * 查询所有的记录
	 */
	public List <T> findObjects(String whereCondition);
	/**
	 * 查询所有的记录
	 */
	public List findObjects(String [] selectColumns,String whereCondition);
	/**
	 * 创建nativeQuery 查询所有的记录
	 */
	public List <T> findByNative(String whereCondition);
	/**
	 * 根据一个列的所有数据
	 * 
	 * @author 
	 */
	public List <T> findUnList(String column, String whereCondition);
	/**
	 * 根据一个列的所有数据，去重复
	 * 
	 * @author 崔云松
	 */
	public List <T> findUnDistinctList(String column, String params);
	/**
	 * 根据一个列查询最大数据
	 * 
	 * @author 
	 */
	public T getMaxData(String column, String params);
	/**
	 * 根据一个列查询最大数据，执行SQL查询
	 * 
	 * @author 崔云松
	 */
	public Object getMaxDataSQL(String sql);
	/**
	 * 获取一些数据 column：排重的列 params：where 条件参数
	 */
	public List <T> findProdProgressSame(String column, String whereCondition);
	/**
	 * 通过HQL语句以及字段投影查询
	 */
	public List<?> findByHQL(String [] selectColumns,int firstIndex, int maxResult,String hql);
	/**
	 * 通过HQL
	 */
	public List<?> findByHQL(String [] selectColumns,String hql,int limit);
	/**
	 * 通过HQL
	 */
	public List<?> findByHQL(String [] selectColumns,String hql);
	/**
	 * 获取一些数据 (多个表)
	 * firstIndex 开始索引
	 * maxResult 需要获取的最大记录数
	 * params where 条件参数
	 */
	public List findSomeManyTable(int firstIndex, int maxResult,String whereCondition);
	/**
	 * 查询所有的记录的分页数目(多个表 )
	 */
	public int getCountManyTable(String params);
	/**
	 * 通过HQL
	 */
	public List<?> findByHQL(String hql);
	/**
	 * 获取list<map>集合
	 * @param Hql自定义Hql 语句
	 * @return返回结果为list<map>
	 * @author
	 */
	public List<Map> findListMapByHql(String hql);
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author
	 */
	public List<Map> findListMapPage(String hql,int firstIndex,int maxResult);
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param sql
	 * @return
	 * @author 
	 */
	public List<Map<String,Object>> findListMapPageBySql(String sql,int firstIndex,int maxResult);
	/**
	 * 获取多表联合查询的数据总数
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author 
	 */
	public int getMultilistCount(String hql);
	/**
	 * 获取查询的数据总数(多表，单表都可以)
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author 
	 * modle :select count(a.id) from table a 
	 */
	public int getMoreTableCount(String hql);
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author 
	 */
	public List<Map<String,Object>> findListMapBySql(String sql);
	/**
	 * 通过sql语句修改单个对象或者批量对象
	 */
	public boolean updateObject(String sql);
	
	/**
	 * 通过占位符的方式进行查询一个分页集合
	 * @param whereHQL 条件语句
	 * @param paramsMap 参数Map
	 * @param sortParam 排序字段
	 * @return
	 */
	public List <T> findListByParamsMap(String selectColumns[],int firstIndex, int maxResult,String whereHQL,Map<Object, Object> paramsMap, String sortParam);
	/**
	 * 通过占位符的方式进行查询一个对象
	 * @param whereHQL 条件语句
	 * @param paramsMap 参数Map
	 * @param sortParam 排序字段
	 * @return
	 */
	public T findObjectByParamsMap(String selectColumns[],String whereHQL,Map<Object, Object> paramsMap);
}
