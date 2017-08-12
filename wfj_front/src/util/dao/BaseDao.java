package util.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import util.common.EncapsulationObject;
import util.other.ReflectUtils;
import util.pojo.BaseEntity;
public class BaseDao<T extends BaseEntity> implements IBaseDao<T> {
	protected HibernateTemplate hibernateTemplate;
	protected Log log = LogFactory.getLog(this.getClass());
	protected Class <T> entityClass;
	/**
	 * 在构造函数中利用反射机制获得参数T的具体类
	 */
	public BaseDao() {
		entityClass = ReflectUtils.getClassGenricType(getClass());
		log.info(entityClass.getName());
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public Session getSession() {
		try {
			SessionFactory sf = hibernateTemplate.getSessionFactory();
			return sf.openSession();
		} catch (Exception e) {
			log.error("获取session工厂出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 添加
	 */
	@SuppressWarnings("unchecked")
	public T saveObject(Object obj) {
		try {
			hibernateTemplate.save(obj);
			return (T) obj;
		} catch (Exception e) {
			log.error("保存出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	public T saveOrUpdateObject(Object obj) {
		try {
			hibernateTemplate.saveOrUpdate(obj);
			return (T) obj;
		} catch (Exception e) {
			log.error("保存或更新出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 修改
	 */
	@SuppressWarnings("unchecked")
	public T updateObject(Object obj) {
		try {
			hibernateTemplate.update(obj);
			return (T)obj;
		} catch (Exception e) {
			log.error("指定对象更新出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 通过指定 的Id删除一条记录
	 */
	public boolean deleteObjectById(String id) {
		try {
		Object obj = hibernateTemplate.get(entityClass, Integer.parseInt(id));
		if (obj != null) {
				hibernateTemplate.delete(obj);
		}
		} catch (Exception e) {
			log.error("通过Id删除对象出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 通过指定 的pojo删除一条记录
	 */
	public boolean deleteObject(Object obj) {
		if (obj != null) {
			try {
				hibernateTemplate.delete(obj);
			} catch (Exception e) {
				log.error("通过制定对象删除对象出现异常:" + e);
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	/**
	 * 通过指定 的条件删除记录
	 */
	public boolean deleteByParams(String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		List<?> list = hibernateTemplate.find(hql);
		try {
			hibernateTemplate.deleteAll(list);
		} catch (Exception e) {
			log.error("通过条件删除对象出现异常:" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 通过指定 的Id查找一个对象
	 */
	@SuppressWarnings("unchecked")
	public T getObjectById(String id) {
		try {
			Object obj = hibernateTemplate.get(entityClass.getName(), Integer.parseInt(id));
			return (T)obj;
		} catch (Exception e) {
			log.error("通过Id查询对象出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 通过指定的条件查找
	 */
	@SuppressWarnings("unchecked")
	public T get(String whereCondition) {
		Session session = null;
		String hql = "select o from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			session = this.getSession();
			Query query = session.createQuery(hql);
			query.setMaxResults(1);
			Object obj = query.uniqueResult();
			return (T)obj;
		} catch (Exception e) {
			log.error("通过条件查询对象出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数
	 */
	@SuppressWarnings("unchecked")
	public T get(int firstIndex, String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery(hql);
			query.setMaxResults(1);
			query.setFirstResult(firstIndex);
			Object obj = query.uniqueResult();
			return (T)obj;
		} catch (Exception e) {
			log.error("通过记录开始位置以及条件查询对象出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 通过指定 的Id查找一个对象
	 */
	/*
	 * public Object get(int id){ return
	 * hibernateTemplate.get(entityClass.getName(), id); }
	 */
	/**
	 * 获取随机记录
	 */
	public T getRandom(String whereCondition) {
		int count = this.getCount(whereCondition);
		if (count == 0)
			return null;
		int recordIndex = (int) (Math.random() * count);
		return this.get(recordIndex, whereCondition);
	}
	/**
	 * 查询所有的记录的分页数目
	 */
	public int getCount(String whereCondition) {
		String hql = "select count(o) from " + entityClass.getSimpleName() + " o ";
		if (whereCondition != null)
			hql += whereCondition;
		// 得到Hibernate Query对象
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery(hql);
			// 执行查询，并返回结果
			if (!query.iterate().hasNext())
				return 0;
			Object object = query.iterate().next();
			if (object == null)
				return 0;
			if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 查询所有的记录的分页数目自定义SQL
	 */
	public int getCountByHQL(String hql) {
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery(hql);
			Iterator<T> it=query.iterate();
			// 执行查询，并返回结果
			if (!it.hasNext())return 0;
			Object object = it.next();
			if (object == null)return 0;
			if (object instanceof Long) {
				int count=((Long) object).intValue();
				return count;
			}else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			}else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 通过HQL语句以及字段投影查询
	 */
	public List<?> findByHQL(String [] selectColumns,int firstIndex, int maxResult,String hql){
		Session session = null;
		try {
			session = this.getSession();
			Query query = (Query) session.createQuery(hql);
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
			List  <Object[]> querylist=query.list();
			List list = null;
			if(querylist!=null){
				list = new ArrayList();
				for (Object[] dataObjs : querylist) {	
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add(obj);
				}
			}
			return list;
		}catch(Exception e){
			log.error("通过条件查询集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	/**
	 * 通过HQL语句
	 */
	public List<?> findByHQL(String [] selectColumns,String hql,int limit){
		Session session = null;
		try {
			session = this.getSession();
			Query query = (Query) session.createQuery(hql);
			query.setFirstResult(0).setMaxResults(limit);//查询前limit条，否则查询所有
			List  <Object[]> querylist=query.list();
			List list = null;
			if(querylist!=null){
				list = new ArrayList();
				for (Object[] dataObjs : querylist) {	
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add(obj);
				}
			}
			return list;
		}catch(Exception e){
			log.error("通过条件查询集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	/**
	 * 通过HQL语句
	 */
	public List<?> findByHQL(String [] selectColumns,String hql){
		Session session = null;
		try {
			session = this.getSession();
			Query query = (Query) session.createQuery(hql);
			List  <Object[]> querylist=query.list();
			List list = null;
			if(querylist!=null){
				list = new ArrayList();
				for (Object[] dataObjs : querylist) {	
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add(obj);
				}
			}
			return list;
		}catch(Exception e){
			log.error("通过条件查询集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	/**
	 * 获取一些数据 (多个表)wjy
	 * firstIndex 开始索引
	 * maxResult 需要获取的最大记录数
	 * params where 条件参数
	 */
	@SuppressWarnings("rawtypes")
	public List findSomeManyTable(int firstIndex, int maxResult,String whereCondition){
		String hql=" ";
		if(whereCondition!=null)hql+=whereCondition;
		Session session=null;
		try {
			session=this.getSession();
			Query query = (Query) session.createQuery(hql);
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
			List list=query.list();
			return list;
		}catch(Exception e){
			log.error("分页查询出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	/**
	 * 查询所有的记录的分页数目(多个表 )wjy
	 */
	public int getCountManyTable(String params){
		String hql = "";
		if(params!=null)hql+=params;
		// 得到Hibernate Query对象
		Session session=null;
		try {
			session=this.getSession();
			Query query = session.createQuery(hql);
			// 执行查询，并返回结果
			if (!query.iterate().hasNext())return 0;
			Object object = query.iterate().next();
			if (object == null) return 0;
			if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Integer){
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		}catch(Exception e){
			log.error("通过条件查询统计数量出现异常:"+e);
			e.printStackTrace();
			return 0;
		}finally{
			session.close();
		}
	}
	/**
	 * 通过HQL语句
	 */
	public List<?> findByHQL(String hql){
		Session session = null;
		try {
			session = this.getSession();
			Query query = (Query) session.createQuery(hql);
			List  <Object[]> querylist=query.list();
			return querylist;
		}catch(Exception e){
			log.error("通过条件查询集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	/**
	 * 获取一些数据 column：排重的列 params：where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findUnSame(String column, String whereCondition) {
		String hql = "select distinct o." + column + " from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			List<T> list = hibernateTemplate.find(hql);
			return list;
		} catch (Exception e) {
			log.error("通过条件查询不重复的集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取一些数据 firstIndex 开始索引 maxResult 需要获取的最大记录数 params where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findSome(int firstIndex, int maxResult, String whereCondition) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
		if (whereCondition != null)
			sb.append(whereCondition);
		Session session = null;
		try {
			session = this.getSession();
			Query query = (Query) session.createQuery(sb.toString());
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
			List<T> list = query.list();
			return list;
		} catch (Exception e) {
			log.error("分页查询出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 获取一些数据 selectMap:投影的字段map firstIndex 开始索引 maxResult 需要获取的最大记录数 params
	 * where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List <T> findSome(String[] selectColumns, int firstIndex, int maxResult, String whereCondition) {
		StringBuffer sb = new StringBuffer();
		Session session = null;
		try {
				List <T> list = new ArrayList<T>();
				session = this.getSession();
				if (selectColumns != null) {
					sb.append("select ");
					for (int i = 0; i < selectColumns.length; i++) {
						if (i == selectColumns.length - 1) {
							sb.append(" o.").append(selectColumns[i]);
						} else {
							sb.append(" o.").append(selectColumns[i]).append(" ,");
						}
					}
					sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
					if (whereCondition != null)sb.append(whereCondition);
					Query query = (Query) session.createQuery(sb.toString());
					query.setFirstResult(firstIndex).setMaxResults(maxResult);
					List <Object[]> querylist = query.list();
					for (Object[] dataObjs : querylist) {
						Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
						list.add((T) obj);
					}
				} else {
					sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
					if (whereCondition != null)sb.append(whereCondition);
					Query query = (Query) session.createQuery(sb.toString());
					query.setFirstResult(firstIndex).setMaxResults(maxResult);
					list = query.list();
				}
			return list;
		} catch (Exception e) {
			log.error("分页查询出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 求和 field 开始索引 params where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public T getSum(String field, String whereCondition) {
		String hql = "select sum(o." + field + ") from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		Session session = null;
		try {
			session = this.getSession();
			Object obj = session.createQuery(hql).list().get(0);
			session.close();
			return (T)obj;
		} catch (Exception e) {
			log.error("统计求和出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 查询所有的记录
	 */
	@SuppressWarnings("unchecked")
	public List <T> findObjects(String whereCondition) {
		String hql = " from " + entityClass.getSimpleName() + "  o ";
		if (whereCondition != null)
			hql += whereCondition;
		Session session = null;
		try {
			session = this.getSession();
			List <T> list = hibernateTemplate.find(hql);
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 查询所有的记录
	 */
	public List<T> findObjects(String [] selectColumns,String whereCondition){
		StringBuffer sb = new StringBuffer();
		Session session = null;
		try {
			List<T> list = new ArrayList<T>();
			session = this.getSession();
			if (selectColumns != null) {
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				if (whereCondition != null)sb.append(whereCondition);
				Query query = (Query) session.createQuery(sb.toString());
				List <T[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {
					Object obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T)obj);
				}
			} else {
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				if (whereCondition != null)sb.append(whereCondition);
				Query query = (Query) session.createQuery(sb.toString());
				list = query.list();
			}
			return list;
		}catch(Exception e){
			log.error("通过条件查询集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	/**
	 * 创建nativeQuery 查询所有的记录
	 */
	@SuppressWarnings("unchecked")
	public List <T> findByNative(String whereCondition) {
		String hql = "select o from " + entityClass.getSimpleName() + "  o ";
		if (whereCondition != null)
			hql += whereCondition;
		return hibernateTemplate.find(hql);
	}
	/**
	 * 根据一个列的所有数据
	 * 
	 * @author 刘青松
	 */
	@SuppressWarnings("unchecked")
	public List<T> findUnList(String column, String whereCondition) {
		String hql = "select o.'" + column + "' from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			return hibernateTemplate.find(hql);
		} catch (Exception e) {
			log.error("制定列集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据一个列的所有数据，去重复
	 * 
	 * @author 刘青松
	 */
	@SuppressWarnings("unchecked")
	public List<T> findUnDistinctList(String column, String params) {
		String hql = "select distinct o." + column + " from " + entityClass.getSimpleName() + " as o ";
		if (params != null)
			hql += params;
		try {
			return hibernateTemplate.find(hql);
		} catch (Exception e) {
			log.error("去重查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据一个列查询最大数据
	 * 
	 * @author 刘青松
	 */
	@SuppressWarnings("unchecked")
	public T getMaxData(String column, String whereCondition) {
		String hql = "select max(o." + column + ") from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		Session session = null;
		try {
			session = this.getSession();
			Object obj = session.createQuery(hql).list().get(0);
			return (T)obj;
		} catch (Exception e) {
			log.error("指定列求出最大值出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 根据一个列查询最大数据，执行SQL查询
	 * 
	 * @author 崔云松
	 */
	public Object getMaxDataSQL(String sql) {
		Session session = null;
		try {
			session = this.getSession();
			Object obj = session.createSQLQuery(sql).list().get(0);
			return obj;
		} catch (Exception e) {
			log.error("指定列求出最大值出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 获取一些数据 column：排重的列 params：where 条件参数
	 */
	@SuppressWarnings("unchecked")
	public List <T> findProdProgressSame(String column, String whereCondition) {
		String hql = "select distinct o." + column + " ,o.pattern from " + entityClass.getSimpleName() + " as o ";
		if (whereCondition != null)
			hql += whereCondition;
		try {
			return hibernateTemplate.find(hql);
		} catch (Exception e) {
			log.error("指定列求出最大值出现异常:" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取list<map>集合
	 * 自定义Hql 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findListMapByHql(String hql){
		Session session = null;
		try{
		session = hibernateTemplate.getSessionFactory().openSession();
		List<Map> list = session.createQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
		}catch(Exception e ){
			log.error("获取list<map>集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	/**
	 * 获取list<map>集合
	 * 分页自定义Hql 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findListMapPage(String hql,int firstIndex,int maxResult){
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			List<Map> list = session.createQuery(hql).setFirstResult(firstIndex).setMaxResults(maxResult).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}catch(Exception e ){
			log.error("获取list<map>集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	/**
	 * 获取list<map>集合
	 * 分页自定义SQL 语句，返回结果为list<map>
	 * @param sql
	 * @return
	 * @author 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findListMapPageBySql(String sql,int firstIndex,int maxResult){
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			List<Map<String,Object>> list = session.createSQLQuery(sql).setFirstResult(firstIndex).setMaxResults(maxResult).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}catch(Exception e ){
			log.error("获取list<map>集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	/**
	 * 获取list<map>集合
	 * 自定义SQL 语句，返回结果为list<map>
	 * @param Hql
	 * @return
	 * @author 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findListMapBySql(String sql){
		Session session = null;
		try{
		session = hibernateTemplate.getSessionFactory().openSession();
		List<Map<String,Object>> list = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
		}catch(Exception e ){
			log.error("获取list<map>集合出现异常:"+e);
			e.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}
	/**
	 * 获取多表联合查询的数据总数
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMultilistCount(String hql) {
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery(hql);
			// 执行查询，并返回结果
			if (!query.iterate().hasNext())
				return 0;
			Object object = query.iterate().next();
			if (object == null)
				return 0;
			if (object instanceof Long) {
				return ((Long) object).intValue();
			} else if (object instanceof Integer) {
				return ((Integer) object).intValue();
			} else {
				try {
					throw new Exception("统计HQL存在错误。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 获取查询的数据总数(多表，单表都可以)
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMoreTableCount(String hql) {
		Session session = null;
		try {
			session = this.getSession();
			Long a =  (Long) session.createQuery(hql).uniqueResult();
			return a.intValue();
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 获取查询的数据总数(多表，单表都可以)
	 * 自定义Hql 语句，返回结果为int总数
	 * @param Hql
	 * @return
	 * @author
	 */
	public int getMoreTableCountBySQL(String sqlCount) {
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery createSQLQuery = session.createSQLQuery(sqlCount);
			return Integer.parseInt(createSQLQuery.uniqueResult().toString());
		} catch (Exception e) {
			log.error("通过条件查询统计数量出现异常:" + e);
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 通过sql语句修改单个对象或者批量对象
	 */
	public boolean updateObject(String sql){
		Session session=null;
		try{
			session=this.getSession();
			SQLQuery sql_query=session.createSQLQuery(sql);
			int result=sql_query.executeUpdate();
			if(result>=0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			log.error("通过条件查询对象出现异常:"+e);
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}
	
	/**
	 * 通过占位符的方式进行查询一个分页集合
	 * @param whereHQL 条件语句
	 * @param paramsMap 参数Map
	 * @param sortParam 排序字段
	 * @return List
	 */
	public List <T> findListByParamsMap(String selectColumns[],int firstIndex, int maxResult,String whereHQL,Map<Object, Object> paramsMap, String sortParam) {
		Query query = null;
		Session session = null;
		Object obj = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = this.getSession();
			List <T> list = new ArrayList<T>();
			if (selectColumns != null) {
				//1、组装select 指定字段语句片段
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				//2、组装from语句片段
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				//3、组装where语句片段
				if (whereHQL != null&&paramsMap!=null){
					//4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						if(sortParam!=null)sb.append(sortParam);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					}else{
						if(sortParam!=null)sb.append(sortParam);
						query = session.createQuery(sb.toString());
					}
				}else{
					if(sortParam!=null)sb.append(sortParam);
					query = session.createQuery(sb.toString());
				}
				List <T[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {	
					Object query_obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T)query_obj);
				}
			} else {
				//1、组装select 字段语句和from语句片段
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				//2、组装where语句片段
				//3、设置占位符的参数
				if (whereHQL != null&&paramsMap!=null){
					//4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						if(sortParam!=null)sb.append(sortParam);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					}else{
						if(sortParam!=null)sb.append(sortParam);
						query = session.createQuery(sb.toString());
					}
				}else{
					if(sortParam!=null)sb.append(sortParam);
					query = session.createQuery(sb.toString());
				}
				query.setFirstResult(firstIndex).setMaxResults(maxResult);
				list=query.list();
			}
			return list;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	/**
	 * 通过占位符的方式进行查询一个对象
	 * @param whereHQL 条件语句
	 * @param paramsMap 参数Map
	 * @param sortParam 排序字段
	 * @return T
	 */
	public T findObjectByParamsMap(String selectColumns[],String whereHQL,Map<Object, Object> paramsMap) {
		Query query = null;
		Session session = null;
		Object obj = null;
		StringBuffer sb = new StringBuffer();
		try {
			session = this.getSession();
			List <T> list = new ArrayList<T>();
			if (selectColumns != null) {
				//1、组装select 指定字段语句片段
				sb.append("select ");
				for (int i = 0; i < selectColumns.length; i++) {
					if (i == selectColumns.length - 1) {
						sb.append(" o.").append(selectColumns[i]);
					} else {
						sb.append(" o.").append(selectColumns[i]).append(" ,");
					}
				}
				//2、组装from语句片段
				sb.append(" from ").append(entityClass.getSimpleName()).append(" o ");
				//3、组装where语句片段
				if (whereHQL != null&&paramsMap!=null){
					//4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					}else{
						query = session.createQuery(sb.toString());
					}
				}else{
					query = session.createQuery(sb.toString());
				}
				List <T[]> querylist = query.list();
				for (Object[] dataObjs : querylist) {	
					Object query_obj = EncapsulationObject.getObject(selectColumns, entityClass, dataObjs);
					list.add((T)query_obj);
				}
			} else {
				//1、组装select 字段语句和from语句片段
				sb.append("select o from ").append(entityClass.getSimpleName()).append(" o ");
				//2、组装where语句片段
				//3、设置占位符的参数
				if (whereHQL != null&&paramsMap!=null){
					//4、设置占位符的参数
					Set<Entry<Object, Object>> set = paramsMap.entrySet();
					if (set != null) {
						sb.append(whereHQL);
						query = session.createQuery(sb.toString());
						Iterator<Entry<Object, Object>> it = set.iterator();
						while (it.hasNext()) {
							Map.Entry mapentry = it.next();
							query.setParameter(mapentry.getKey().toString(), mapentry.getValue().toString());
						}
					}else{
						query = session.createQuery(sb.toString());
					}
				}else{
					query = session.createQuery(sb.toString());
				}
				query.setMaxResults(1);
				list=query.list();
				if(list!=null&&list.size()>0){
					obj=list.get(0);
				}
			}
			return (T) obj;
		} catch (Exception e) {
			log.error("通过条件查询集合出现异常:" + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}
