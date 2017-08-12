package shop.product.pojo;
import java.util.List;
/**
 * ParamGroupInfo - 商品参数详细内容
 */
public class ParamGroup {
	private Integer paramGroupId;//参数组	
	private String name;//参数组名称
	private Integer Order ;//参数组排序
	private List<ParamGroupInfo>  paramGroupInfo;
	public Integer getParamGroupId() {
		return paramGroupId;
	}
	public void setParamGroupId(Integer paramGroupId) {
		this.paramGroupId = paramGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrder() {
		return Order;
	}
	public void setOrder(Integer order) {
		Order = order;
	}
	public List<ParamGroupInfo> getParamGroupInfo() {
		return paramGroupInfo;
	}
	public void setParamGroupInfo(List<ParamGroupInfo> paramGroupInfo) {
		this.paramGroupInfo = paramGroupInfo;
	}
}
