package util.service;
import java.util.List;
import java.util.Map;

import util.dao.IBaseDao;
import util.pojo.BaseEntity;
import util.pojo.PageHelper;
public  class BaseService <T extends BaseEntity> implements IBaseService<T>{
	public IBaseDao <T> baseDao;
	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	// 保存或者修改
	public Object saveOrUpdateObject(Object obj) {
		return baseDao.saveOrUpdateObject(obj);
	}
	// 指定主键ID删除对象
	public boolean deleteObjectById(String id) {
		return baseDao.deleteObjectById(id);
	}
	// 指定参数删除对象
	public boolean deleteObjectByParams(String params){
		return baseDao.deleteByParams(params);
	}
	// 根据id
	public Object getObjectById(String id) {
		return baseDao.getObjectById(id);
	}
	// 根据SQL语句返回一条数据
	public Object getObjectByParams(String params) {
		return baseDao.get(params);
	}
	// 查询最大行数
	public Integer getCount(String params) {
		return baseDao.getCount(params);
	}
	// 查询最大行数自定义SQL
	public Integer getCountByHQL(String hql) {
		return baseDao.getCountByHQL(hql);
	}
	//批量删除
	public boolean deleteObjectsByIds(String key,String ids) {
		String [] idss=ids.split(",");
		String queryParams=" where 1=1 and ";
		if(idss.length>1){//删除多个对象
			queryParams+="(";
			for(int i=0;i<idss.length;i++){
				if(i==0){
					queryParams+="o."+key+"='"+idss[i]+"'";
				}else{
					queryParams+=" or o."+key+"='"+idss[i]+"'";
				}
			}
			queryParams+=")";
		}else{
			queryParams+=" o."+key+"='"+idss[0]+"'";//删除一个对象
		}
		return baseDao.deleteByParams(queryParams);
	}
	//查询所有的行数 
	public List <T>findObjects(String params){
		return baseDao.findObjects(params);
	}
	//查询所有的行数 
	public List findObjects(String [] selectColumns,String params){
		return baseDao.findObjects(selectColumns,params);
	}
	//获取list<map>集合
	@SuppressWarnings("unchecked")
	public List<Map> findListMapByHql(String hql){
		return baseDao.findListMapByHql(hql);
	}
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author 
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findListMapPage(String hql, PageHelper pageHelper){
		if(pageHelper == null) {
			pageHelper = new PageHelper();
			pageHelper.setPageRecordBeginIndex(0);
			pageHelper.setPageSize(1);
		}
		return baseDao.findListMapPage(hql, pageHelper.getPageRecordBeginIndex(), pageHelper.getPageSize());
	}
	/*// 根据SQL语句返回多条数据
	public List  findListByPageHelper(PageHelper pageHelper, String params) {
		return baseDao.findSome(pageHelper.getPageRecordBeginIndex(), pageHelper.getPageSize(), params);
	}*/
	// 根据SQL语句返回多条数据
	public List <T> findListByPageHelper(String [] selectColumns, PageHelper pageHelper, String params) {
		if(pageHelper == null) {
			pageHelper = new PageHelper();
			pageHelper.setPageRecordBeginIndex(0);
			pageHelper.setPageSize(1);
		}
		return baseDao.findSome(selectColumns,pageHelper.getPageRecordBeginIndex(), pageHelper.getPageSize(), params);
	}
	/**
	 * 通过HQL语句以及字段投影查询
	 */
	public List findObjectsByHQL(String [] selectColumns,PageHelper pageHelper,String hql){
		return baseDao.findByHQL(selectColumns, pageHelper.getPageRecordBeginIndex(), pageHelper.getPageSize(), hql);
	}
	/**
	 * 通过HQL
	 */
	public List findObjectsByHQL(String [] selectColumns,String hql,int limit){
		return baseDao.findByHQL(selectColumns,hql,limit);
	}
	/**
	 * 通过HQL
	 */
	public List findObjectsByHQL(String [] selectColumns,String hql){
		return baseDao.findByHQL(selectColumns,hql);
	}
	/**
	 * 通过HQL
	 */
	public List findObjectsByHQL(String hql){
		return baseDao.findByHQL(hql);
	}	
	// 根据SQL语句返回多条数据
	public List  findListByPageHelperManyTable(PageHelper pageHelper, String params) {
		return baseDao.findSomeManyTable(pageHelper.getPageRecordBeginIndex(), pageHelper.getPageSize(), params);
	}
	// 查询最大行数(多个表)
	public Integer getCountManyTable(String params) {
		return baseDao.getCountManyTable(params);
	}
	/**
	 * 获取多表联合查询的数据总数
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMultilistCount(String hql) {
		return baseDao.getMultilistCount(hql);
	}
	/**
	 * 根据一个列查询最大数据
	 * 
	 * @author 刘青松
	 */
	public T getMaxData(String column, String whereCondition){
		return baseDao.getMaxData(column,whereCondition);
	}
	/**
	 * 根据一个列查询最大数据，执行SQL查询
	 * 
	 * @author 崔云松
	 */
	public Object getMaxDataSQL(String sql){
		return baseDao.getMaxDataSQL(sql);
	}
	/**
	 * 根据一个列的所有数据，去重复
	 * 
	 * @author 刘青松
	 */
	public List<T> findUnDistinctList(String column, String params){
		return baseDao.findUnDistinctList(column, params);
	}
	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数 params where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findSome(int firstIndex, int maxResult, String whereCondition){
		return baseDao.findSome(firstIndex, maxResult, whereCondition);
	}
	/**
	 * 获取多表查询的数据总数
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMoreTableCount(String hql){
		return baseDao.getMoreTableCount(hql);
	}
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author 
	 */
	public List<Map<String, Object>> findListMapBySql(String sql) {
		return baseDao.findListMapBySql(sql);
	}
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param sql
	 * @return
	 * @author 
	 */
	public List<Map<String,Object>> findListMapPageBySql(String sql,PageHelper pageHelper){
		if(pageHelper == null) {
			pageHelper = new PageHelper();
			pageHelper.setPageRecordBeginIndex(0);
			pageHelper.setPageSize(1);
		}
		return baseDao.findListMapPageBySql(sql,pageHelper.getPageRecordBeginIndex(), pageHelper.getPageSize());
	}
	/**
	  * 获取指定条数数据 
	  * selectColumns：投影字段
	  * beginIndex：开始索引
	  * endIndex：结束索引
	  * where：条件语句
	  * @author mqr 
	  * @return List 指定条数的集合
	  */
	 public List<?> findListSpecifiedNumber(String[] selectColumns,Integer beginIndex,Integer endIndex,String where){
		 return baseDao.findSome(selectColumns,beginIndex, endIndex, where);
	 }
	 
	 /**
	 * 通过占位符的方式进行查询一个分页集合
	 * @param whereHQL 条件语句
	 * @param paramsMap 参数Map
	 * @param sortParam 排序字段
	 * @return List
	 */
	 public List <T> findListByParamsMap(String selectColumns[],PageHelper pageHelper,String whereHQL,Map<Object, Object> paramsMap, String sortParam){
		 return baseDao.findListByParamsMap(selectColumns, pageHelper.getFirstPageIndex(), pageHelper.getPageSize(), whereHQL, paramsMap, sortParam);
	 }
	 
	 /**
	 * 通过占位符的方式进行查询一个对象
	 * @param whereHQL 条件语句
	 * @param paramsMap 参数Map
	 * @param sortParam 排序字段
	 * @return T
	 */
	 public T findObjectByParamsMap(String selectColumns[],String whereHQL,Map<Object, Object> paramsMap){
		 return (T)baseDao.findObjectByParamsMap(selectColumns, whereHQL, paramsMap);
	 }
}
