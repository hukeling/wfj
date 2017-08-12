package basic.service;
import java.util.List;

import util.service.IBaseService;
import basic.pojo.Purview;
/**
 * 权限Service接口
 * @author ss
 *
 */
public interface IPurviewService  extends IBaseService <Purview> {
	/**
	 * 根据父ID查询列表
	 * @param ID值
	 * @return 返回一个list集合
	 */
	public List<Purview> queryByParentId(String id) ;
	/**
	 * 删除权限
	 * @param id 权限id
	 * @return boolean类型的 true 或 false
	 */
	public Boolean deletePurview(String id);
}
