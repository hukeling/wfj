package shop.logistics.pojo;

import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 物流公司实体类
 * @author Administrator
 * 2014-07-25
 */
@SuppressWarnings("serial")
public class Logistics extends BaseEntity implements Serializable{
	/**
	 * 物流ID
	 */
	private Integer logisticsId;
	/**
	 * 物流公司代码
	 */
	private String code;
	/**
	 * 物流公司名称
	 */
	private String deliveryCorpName;
	/**
	 * 物流公司网址
	 */
	private String deliveryUrl;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * 是否常用
	 */
	private Integer isCommon;

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}

	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}

	public String getDeliveryUrl() {
		return deliveryUrl;
	}

	public void setDeliveryUrl(String deliveryUrl) {
		this.deliveryUrl = deliveryUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getSortCode() {
		return sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	public Integer getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(Integer isCommon) {
		this.isCommon = isCommon;
	}

}
