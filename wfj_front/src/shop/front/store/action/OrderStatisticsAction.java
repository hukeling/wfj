package shop.front.store.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;
/**
 * 订单统计
 * 
 * @author mengqirui
 */
public class OrderStatisticsAction extends BaseAction {
	private static final long serialVersionUID = 4427547701469771135L;
	/** 订单service **/
	private IOrdersService ordersService;
	private List<Map<String, Object>> collectiveOrderList;

	/**
	 * 跳转到订单统计页面
	 */
	public String gotoCollectiveOrderPage() throws Exception{
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		String selectFlag = request.getParameter("selectFlag");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.discount as discount, a.userCoin as userCoin,a.changeAmount as changeAmount, a.virtualCoinNumber as virtualCoinNumber,a.virtualCoin as virtualCoin, a.ordersId as ordersId,a.ordersNo as ordersNo,a.createTime as createTime,b.shopName as shopName,c.loginName as loginName,c.nickName as nickName,a.consignee as consignee,d.payTime as payTime,e.distributionTime as distributionTime,f.deliversTime as deliversTime,g.receivesTime as receivesTime,a.totalAmount as totalAmount,a.freight as freight,a.finalAmount as finalAmount,a.invoiceType as invoiceType from"
				+ " shop_orders a LEFT JOIN shop_shopinfo b on a.shopInfoId=b.shopInfoId"
				+ " LEFT JOIN basic_customer c on a.customerId=c.customerId"
				+ " LEFT JOIN (SELECT a.ordersId as payTimeId,a.operatorTime as payTime FROM shop_ordersoplog a WHERE a.ordersOperateState=2) d on d.payTimeId=a.ordersId"
				+ " LEFT JOIN (SELECT a.ordersId as distributionTimeId,a.operatorTime as distributionTime FROM shop_ordersoplog a WHERE a.ordersOperateState=3) e on e.distributionTimeId=a.ordersId"
				+ " LEFT JOIN (SELECT a.ordersId as deliversTimeId,a.operatorTime as deliversTime FROM shop_ordersoplog a WHERE a.ordersOperateState=4) f on f.deliversTimeId=a.ordersId"
				+ " LEFT JOIN (SELECT a.ordersId as receivesTimeId,a.operatorTime as receivesTime FROM shop_ordersoplog a WHERE a.ordersOperateState=5) g on g.receivesTimeId=a.ordersId where 1=1");
			if(shopInfo!=null){
				sql.append(" and a.shopInfoId = "+shopInfo.getShopInfoId());
			}
			String shopName = request.getParameter("shopName");
			String loginName = request.getParameter("loginName");
			String consignee = request.getParameter("consignee");
			String ordersState = request.getParameter("ordersState");
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			request.setAttribute("beginTime", beginTime);
			request.setAttribute("endTime", endTime);
			if (shopName != null && !"".equals(shopName)) {
				sql.append(" and b.shopName like '%" + shopName.trim() + "%'");
			}
			if (loginName != null && !"".equals(loginName)) {
				sql.append(" and c.loginName like '%" + loginName.trim() + "%'");
			}
			if (consignee != null && !"".equals(consignee)) {
				sql.append(" and a.consignee like '%" + consignee.trim() + "%'");
			}
			if (ordersState != null && !"-1".equals(ordersState)) {
				if ("1".equals(ordersState)) {
					sql.append(" and a.ordersState=0 and a.settlementStatus=0");
				} else if ("2".equals(ordersState)) {
					sql.append(" and a.ordersState=0 and a.settlementStatus=1");
				} else if ("5".equals(ordersState)) {
					sql.append(" and (a.ordersState=" + ordersState.trim()
							+ " or a.ordersState=9) ");
				} else {
					sql.append(" and a.ordersState=" + ordersState.trim());
				}
			}
			if (ordersState == null || "-1".equals(ordersState)) {
				if (beginTime != null && !"".equals(beginTime)) {
					if (endTime != null && !"".equals(endTime)) {
						sql.append(" and a.createTime between '"
								+ beginTime.trim() + " 00:00:00' and '"
								+ endTime.trim() + " 23:59:59'");
					} else {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date d1 = df.parse(beginTime.trim());
						Calendar g = Calendar.getInstance();
						g.setTime(d1);
						g.add(Calendar.MONTH, +1);
						Date d2 = g.getTime();
						endTime = (new SimpleDateFormat("yyyy-MM-dd"))
								.format(d2);
						sql.append(" and a.createTime between '"
								+ beginTime.trim() + " 00:00:00' and '"
								+ endTime + " 23:59:59'");
					}
				} else {
					Calendar cal = Calendar.getInstance();// 获取当前日期
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					sql.append(" and a.createTime like '%" + year + "-" + month
							+ "%'");
				}
			} else {
				if (beginTime != null && !"".equals(beginTime)) {
					if (endTime != null && !"".equals(endTime)) {
						sql.append(" and a.createTime between '"
								+ beginTime.trim() + " 00:00:00' and '"
								+ endTime.trim() + " 23:59:59'");
					} else {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date d1 = df.parse(beginTime.trim());
						Calendar g = Calendar.getInstance();
						g.setTime(d1);
						g.add(Calendar.MONTH, +1);
						Date d2 = g.getTime();
						endTime = (new SimpleDateFormat("yyyy-MM-dd"))
								.format(d2);
						sql.append(" and a.createTime between '"
								+ beginTime.trim() + " 00:00:00' and '"
								+ endTime + " 23:59:59'");
					}
				} else {
					Calendar cal = Calendar.getInstance();// 获取当前日期
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					sql.append(" and a.createTime like '%" + year + "-" + month
							+ "%'");
				}
			}
		int totalRecordCount = 0;
		List<Map<String, Object>> listTemp = ordersService.findListMapBySql(sql
				.toString());
		if (listTemp != null && listTemp.size() > 0) {
			totalRecordCount = listTemp.size();
		}
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		collectiveOrderList = ordersService.findListMapPageBySql( sql.toString(), pageHelper);
		// 格式Map中的时间
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 获取税率
		if (collectiveOrderList != null && collectiveOrderList.size() > 0) {
			for (Map<String, Object> m : collectiveOrderList) {// 格式化创建时间
				if (m.get("createTime") != null) {
					Date date = (Date) m.get("createTime");
					String format = fm.format(date);
					m.put("createTime", format);
				}
				if (m.get("payTime") != null) {
					Date date = (Date) m.get("payTime");
					String format = fm.format(date);
					m.put("payTime", format);
				}
				if (m.get("distributionTime") != null) {
					Date date = (Date) m.get("distributionTime");
					String format = fm.format(date);
					m.put("distributionTime", format);
				}
				if (m.get("deliversTime") != null) {
					Date date = (Date) m.get("deliversTime");
					String format = fm.format(date);
					m.put("deliversTime", format);
				}
				if (m.get("receivesTime") != null) {
					Date date = (Date) m.get("receivesTime");
					String format = fm.format(date);
					m.put("receivesTime", format);
				}
				if (m.get("invoiceType") == null
						|| (Byte) m.get("invoiceType") == 1) {
					m.put("taxRate", 0);
					m.put("taxRateAmount", 0);
				} else if ((Byte) m.get("invoiceType") == 2) {
					String generalRates = (String) fileUrlConfig
							.get("generalRates");
					BigDecimal b = new BigDecimal(generalRates);
					m.put("taxRate", b);
					m.put("taxRateAmount",
							b.multiply((BigDecimal) m.get("totalAmount"))
									.divide(new BigDecimal(100))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				} else if ((Byte) m.get("invoiceType") == 3) {
					String vatRates = (String) fileUrlConfig.get("vatRates");
					BigDecimal b = new BigDecimal(vatRates);
					m.put("taxRate", b);
					m.put("taxRateAmount",
							b.multiply((BigDecimal) m.get("totalAmount"))
									.divide(new BigDecimal(100))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		return SUCCESS;
	}

	// 统计订单详情
	public void getCollectiveOrderInfo() {

	}

	/**
	 * 订单统计表中的导出
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void importExcelTJDD() throws Exception {
		String shopName = request.getParameter("shopName");
		String loginName = request.getParameter("loginName");
		String consignee = request.getParameter("consignee");
		String ordersState = request.getParameter("ordersState");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.discount as discount, a.userCoin as userCoin,a.changeAmount as changeAmount, a.virtualCoinNumber as virtualCoinNumber,a.virtualCoin as virtualCoin, a.ordersId as ordersId,a.ordersNo as ordersNo,a.createTime as createTime,b.shopName as shopName,c.loginName as loginName,c.nickName as nickName,a.consignee as consignee,d.payTime as payTime,e.distributionTime as distributionTime,f.deliversTime as deliversTime,g.receivesTime as receivesTime,a.totalAmount as totalAmount,a.freight as freight,a.finalAmount as finalAmount,a.invoiceType as invoiceType from"
				+ " shop_orders a LEFT JOIN shop_shopinfo b on a.shopInfoId=b.shopInfoId"
				+ " LEFT JOIN basic_customer c on a.customerId=c.customerId"
				+ " LEFT JOIN (SELECT a.ordersId as payTimeId,a.operatorTime as payTime FROM shop_ordersoplog a WHERE a.ordersOperateState=2) d on d.payTimeId=a.ordersId"
				+ " LEFT JOIN (SELECT a.ordersId as distributionTimeId,a.operatorTime as distributionTime FROM shop_ordersoplog a WHERE a.ordersOperateState=3) e on e.distributionTimeId=a.ordersId"
				+ " LEFT JOIN (SELECT a.ordersId as deliversTimeId,a.operatorTime as deliversTime FROM shop_ordersoplog a WHERE a.ordersOperateState=4) f on f.deliversTimeId=a.ordersId"
				+ " LEFT JOIN (SELECT a.ordersId as receivesTimeId,a.operatorTime as receivesTime FROM shop_ordersoplog a WHERE a.ordersOperateState=5) g on g.receivesTimeId=a.ordersId where 1=1");
		if(shopInfo!=null){
			sql.append(" and a.shopInfoId = "+shopInfo.getShopInfoId());
		}
		if (shopName != null && !"".equals(shopName)) {
			sql.append(" and b.shopName like '%" + shopName.trim() + "%'");
		}
		if (loginName != null && !"".equals(loginName)) {
			sql.append(" and c.loginName like '%" + loginName.trim() + "%'");
		}
		if (consignee != null && !"".equals(consignee)) {
			sql.append(" and a.consignee like '%" + consignee.trim() + "%'");
		}
		if (ordersState != null && !"-1".equals(ordersState)
				&& !"".equals(ordersState)) {
			if ("1".equals(ordersState)) {
				sql.append(" and a.ordersState=0 and a.settlementStatus=0");
			} else if ("2".equals(ordersState)) {
				sql.append(" and a.ordersState=0 and a.settlementStatus=1");
			} else if ("5".equals(ordersState)) {
				sql.append(" and (a.ordersState=" + ordersState.trim()
						+ " or a.ordersState=9) ");
			} else {
				sql.append(" and a.ordersState=" + ordersState.trim());
			}
		}
		if (ordersState == null || "-1".equals(ordersState)) {
			if (beginTime != null && !"".equals(beginTime)) {
				if (endTime != null && !"".equals(endTime)) {
					sql.append(" and a.createTime between '" + beginTime.trim()
							+ " 00:00:00' and '" + endTime.trim()
							+ " 23:59:59'");
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date d1 = df.parse(beginTime.trim());
					Calendar g = Calendar.getInstance();
					g.setTime(d1);
					g.add(Calendar.MONTH, +1);
					Date d2 = g.getTime();
					endTime = (new SimpleDateFormat("yyyy-MM-dd")).format(d2);
					sql.append(" and a.createTime between '" + beginTime.trim()
							+ " 00:00:00' and '" + endTime + " 23:59:59'");
				}
			} else {
				// 获取前月的第一天
				Calendar cal = Calendar.getInstance();// 获取当前日期
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				sql.append(" and a.createTime like '%" + year + "-" + month
						+ "%'");
			}
		} else {
			if (beginTime != null && !"".equals(beginTime)) {
				if (endTime != null && !"".equals(endTime)) {
					sql.append(" and a.createTime between '" + beginTime.trim()
							+ " 00:00:00' and '" + endTime.trim()
							+ " 23:59:59'");
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date d1 = df.parse(beginTime.trim());
					Calendar g = Calendar.getInstance();
					g.setTime(d1);
					g.add(Calendar.MONTH, +1);
					Date d2 = g.getTime();
					endTime = (new SimpleDateFormat("yyyy-MM-dd")).format(d2);
					sql.append(" and a.createTime between '" + beginTime.trim()
							+ " 00:00:00' and '" + endTime + " 23:59:59'");
				}
			} else {
				// 获取前月的第一天
				Calendar cal = Calendar.getInstance();// 获取当前日期
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				sql.append(" and a.createTime like '%" + year + "-" + month
						+ "%'");
			}
		}
		List<Map<String, Object>> list = ordersService.findListMapBySql(sql
				.toString());
		// 格式Map中的时间
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 获取税率
		if (list != null && list.size() > 0) {
			for (Map<String, Object> m : list) {// 格式化创建时间
				if (m.get("createTime") != null) {
					Date date = (Date) m.get("createTime");
					String format = fm.format(date);
					m.put("createTime", format);
				}
				if (m.get("payTime") != null) {
					Date date = (Date) m.get("payTime");
					String format = fm.format(date);
					m.put("payTime", format);
				}
				if (m.get("distributionTime") != null) {
					Date date = (Date) m.get("distributionTime");
					String format = fm.format(date);
					m.put("distributionTime", format);
				}
				if (m.get("deliversTime") != null) {
					Date date = (Date) m.get("deliversTime");
					String format = fm.format(date);
					m.put("deliversTime", format);
				}
				if (m.get("receivesTime") != null) {
					Date date = (Date) m.get("receivesTime");
					String format = fm.format(date);
					m.put("receivesTime", format);
				}
				if (m.get("invoiceType") == null
						|| (Byte) m.get("invoiceType") == 1) {
					m.put("taxRate", 0);
					m.put("taxRateAmount", 0);
				} else if ((Byte) m.get("invoiceType") == 2) {
					String generalRates = (String) fileUrlConfig
							.get("generalRates");
					BigDecimal b = new BigDecimal(generalRates);
					BigDecimal taxRateAmount = b
							.multiply((BigDecimal) m.get("totalAmount"))
							.divide(new BigDecimal(100))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					m.put("taxRate", b);
					m.put("taxRateAmount", taxRateAmount);
				} else if ((Byte) m.get("invoiceType") == 3) {
					String vatRates = (String) fileUrlConfig.get("vatRates");
					BigDecimal b = new BigDecimal(vatRates);
					m.put("taxRate", b);
					m.put("taxRateAmount",
							b.multiply((BigDecimal) m.get("totalAmount"))
									.divide(new BigDecimal(100))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		long time = new Date().getTime();
		Boolean b = madeExl(list, time);
		// 相对路径[服务器]
		String filePathXLS = "excel/DingDan_" + time + ".xls";
		// 文件名
		String downloadFileName = "DingDan_" + time + ".xls";
		JSONObject jo = new JSONObject();
		jo.accumulate("filePathXLS", filePathXLS);
		jo.accumulate("downloadFileName", downloadFileName);
		jo.accumulate("isSuccess", b + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	/**
	 * 订单统计列表导出:先保存在服务器,在下载
	 * 
	 * @param list
	 *            数据集合
	 * @return
	 * @throws IOException
	 * @throws RowsExceededException
	 */
	@SuppressWarnings("rawtypes")
	public boolean madeExl(List<Map<String, Object>> list, long time)
			throws IOException, RowsExceededException {
		boolean b = true;
		try {
			String url = (String) fileUrlConfig.get("fileUploadRoot");
			File file = new File(url + "/excel");
			file.mkdirs();
			File toFile = new File(url + "/excel/DingDan_" + time + ".xls");
			if (!toFile.exists()) {
				toFile.createNewFile();
			}// 创建目标文件
			WritableWorkbook wbook = Workbook.createWorkbook(toFile);
			// 创建一个工作表 第一个工作区
			WritableSheet wsheet = wbook.createSheet("SHOPJSP:订单统计列表", 0);
			WritableFont wf3 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false);// 设置字体
			WritableCellFormat wcfmt3 = new WritableCellFormat(wf3);
			wcfmt3.setWrap(true);// 自动换行
			wcfmt3.setVerticalAlignment(VerticalAlignment.TOP);// 顶端对齐
			wcfmt3.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN); // 设置边框
			wcfmt3.setAlignment(jxl.format.Alignment.CENTRE);
			biaoge(wsheet);
			int i = 1;// 拿list中的数据[因为有大表头,所以从2开始生成数据]
			if (list != null && list.size() > 0) {
				for (Map m : list) {
					Label a = new Label(0, i + 1, String.valueOf(i), wcfmt3);// 1序号
					wsheet.addCell(a);
					Label ordersNo = new Label(1, i + 1, String.valueOf(m
							.get("ordersNo")), wcfmt3);// 2订单编号
					wsheet.addCell(ordersNo);
					Label createTime = new Label(2, i + 1, String.valueOf(m
							.get("createTime")), wcfmt3);// 3下单时间
					wsheet.addCell(createTime);
					Label shopName = new Label(3, i + 1, String.valueOf(m
							.get("shopName")), wcfmt3);// 4商户店铺名称
					wsheet.addCell(shopName);
					Label loginName = new Label(4, i + 1, String.valueOf(m
							.get("loginName")), wcfmt3);// 5买家会员名称
					wsheet.addCell(loginName);
					Label consignee = new Label(5, i + 1, String.valueOf(m
							.get("consignee")), wcfmt3);// 6收货人名称
					wsheet.addCell(consignee);
					if (m.get("payTime") == null || "".equals(m.get("payTime"))) {
						Label payTime = new Label(6, i + 1, "--", wcfmt3);// 7付款时间
						wsheet.addCell(payTime);
					} else {
						Label payTime = new Label(6, i + 1, String.valueOf(m
								.get("payTime")), wcfmt3);// 7付款时间
						wsheet.addCell(payTime);
					}
					if (m.get("distributionTime") == null
							|| "".equals(m.get("distributionTime"))) {
						Label distributionTime = new Label(7, i + 1, "--",
								wcfmt3);// 8配货时间
						wsheet.addCell(distributionTime);
					} else {
						Label distributionTime = new Label(7, i + 1,
								String.valueOf(m.get("distributionTime")),
								wcfmt3);// 8配货时间
						wsheet.addCell(distributionTime);
					}
					if (m.get("deliversTime") == null
							|| "".equals(m.get("deliversTime"))) {
						Label deliversTime = new Label(8, i + 1, "--", wcfmt3);// 9发货时间
						wsheet.addCell(deliversTime);
					} else {
						Label deliversTime = new Label(8, i + 1,
								String.valueOf(m.get("deliversTime")), wcfmt3);// 9发货时间
						wsheet.addCell(deliversTime);
					}
					if (m.get("receivesTime") == null
							|| "".equals(m.get("receivesTime"))) {
						Label receivesTime = new Label(9, i + 1, "--", wcfmt3);// 10收货时间
						wsheet.addCell(receivesTime);
					} else {
						Label receivesTime = new Label(9, i + 1,
								String.valueOf(m.get("receivesTime")), wcfmt3);// 10收货时间
						wsheet.addCell(receivesTime);
					}
					Label totalAmount = new Label(10, i + 1, String.valueOf(m
							.get("totalAmount")), wcfmt3);// 11商品总金额
					wsheet.addCell(totalAmount);

					String info = "";
					if (m.get("discount") == null) {
						info = "无折扣";
					} else {
						info = String.valueOf(m.get("discount")) + "折";
					}
					Label discount = new Label(11, i + 1, info, wcfmt3);// 11商品总金额
					wsheet.addCell(discount);

					Label userCoin = new Label(12, i + 1, String.valueOf(m
							.get("userCoin")), wcfmt3);// 11商品总金额
					wsheet.addCell(userCoin);
					Label changeAmount = new Label(13, i + 1, String.valueOf(m
							.get("changeAmount")), wcfmt3);// 11商品总金额
					wsheet.addCell(changeAmount);

					Label freight = new Label(14, i + 1, String.valueOf(m
							.get("freight")), wcfmt3);// 12运费
					wsheet.addCell(freight);
					Label taxRate = new Label(15, i + 1, String.valueOf(m
							.get("taxRate")), wcfmt3);// 13税率
					wsheet.addCell(taxRate);
					Label taxRateAmount = new Label(16, i + 1, String.valueOf(m
							.get("taxRateAmount")), wcfmt3);// 14税金
					wsheet.addCell(taxRateAmount);
					Label finalAmount = new Label(17, i + 1, String.valueOf(m
							.get("finalAmount")), wcfmt3);// 15订单应付金额
					wsheet.addCell(finalAmount);
					Label virtualCoinNumber = new Label(18, i + 1,
							String.valueOf(m.get("virtualCoinNumber")), wcfmt3);// 15订单应付金额
					wsheet.addCell(virtualCoinNumber);
					Label virtualCoin = new Label(19, i + 1, String.valueOf(m
							.get("virtualCoin")), wcfmt3);// 15订单应付金额
					wsheet.addCell(virtualCoin);
					i++;
				}
			} else {
				Label cwqd = new Label(1, i + 1, "错误清单", wcfmt3);// 错误
				wsheet.addCell(cwqd);
			}
			wbook.write();// 写入文件
			wbook.close();
		} catch (WriteException e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 设置表格样式
	 * 
	 * @return
	 * @throws WriteException
	 */
	public void biaoge(WritableSheet wsheet) throws WriteException {
		// 设置 行、 列的 宽度 、高度
		wsheet.setColumnView(0, 5); // 设置列的宽度---序号
		wsheet.setColumnView(1, 25); // 设置列的宽度---订单编号
		wsheet.setColumnView(2, 17); // 设置列的宽度---下单时间
		wsheet.setColumnView(3, 30); // 设置列的宽度---商户店铺名称
		wsheet.setColumnView(4, 25); // 设置列的宽度---买家会员名称
		wsheet.setColumnView(5, 12); // 设置列的宽度---收货人名称
		wsheet.setColumnView(6, 17); // 设置列的宽度---付款时间
		wsheet.setColumnView(7, 17); // 设置列的宽度---配货时间
		wsheet.setColumnView(8, 17); // 设置列的宽度---发货时间
		wsheet.setColumnView(9, 17); // 设置列的宽度---收货时间
		wsheet.setColumnView(10, 17); // 设置列的宽度---商品总金额
		wsheet.setColumnView(11, 10); // 设置列的宽度---运费
		wsheet.setColumnView(12, 10); // 设置列的宽度---税率
		wsheet.setColumnView(13, 10); // 设置列的宽度---税金
		wsheet.setColumnView(14, 20); // 设置列的宽度---订单应付金额
		wsheet.setColumnView(15, 20); // 设置列的宽度---订单赠送商城币
		wsheet.setColumnView(16, 20); // 设置列的宽度---订单赠送佣金币
		wsheet.setColumnView(17, 20); // 设置列的宽度---订单赠送佣金币
		wsheet.setColumnView(18, 20); // 设置列的宽度---订单赠送佣金币
		wsheet.setColumnView(19, 20); // 设置列的宽度---订单赠送佣金币
		// 让 第一列居中
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 20,
				WritableFont.NO_BOLD, false);
		WritableCellFormat wcfmt = new WritableCellFormat(wf);
		wcfmt.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); // 给title设置边框
		wcfmt.setAlignment(jxl.format.Alignment.CENTRE);
		wcfmt.setBackground(jxl.format.Colour.BLACK); // 设置单元格的背景颜色
		Label lable1 = new Label(0, 0, "SHOPJSP:企业订单列表", wcfmt);// 添加 Lable对象 (列、行)
		wsheet.addCell(lable1);
		wsheet.mergeCells(0, 0, 19, 0);// 合并单元格（列、行）
		// 列头
		WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.NO_BOLD, false);
		WritableCellFormat wcfmt2 = new WritableCellFormat(wf2);
		wcfmt2.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); // 给title设置边框
		wcfmt2.setAlignment(jxl.format.Alignment.CENTRE);
		wcfmt2.setBackground(jxl.format.Colour.GRAY_25);// 设置单元格的背景颜色
		String[] title = { "序号", "订单编号", "下单时间", "商户店铺名称", "买家会员名称", "收货人名称",
				"付款时间", "配货时间", "发货时间", "收货时间", "商品总金额(元)", "折扣", "使用商城币",
				"使用商城币金额(元)", "运费(元)", "税率(%)", "税金(元)", "订单应付金额(元)", "赠送商城币",
				"赠送佣金币" };
		for (int m = 0; m < title.length; m++) { // 设置表头
			Label excelTitle = new Label(m, 1, title[m], wcfmt2);// 一列列的打印表头
																	// 按照我们规定的格式
			wsheet.addCell(excelTitle);// 把标头加到我们的工作区
		}
	}

	// setter getter
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public List<Map<String, Object>> getCollectiveOrderList() {
		return collectiveOrderList;
	}
	public void setCollectiveOrderList(
			List<Map<String, Object>> collectiveOrderList) {
		this.collectiveOrderList = collectiveOrderList;
	}
}
