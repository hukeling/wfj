package util.other;
/**
 * 创建查询的where子语句
 * @author 刘青松
 *
 */
public class CreateWhereSQLForSelect {
	/**
	 * like运算
	 * 
	 * @param paramName
	 * @param compareSign
	 * like_left,like_right,like三种值类型
	 * @param paramValue
	 * @return StringBuffer对象
	 */
  public static StringBuffer appendLike(String paramName,String compareSign,String paramValue){
	  StringBuffer sb=new StringBuffer();
	  if(paramValue!=null&&!"".equals(paramValue.trim())){
		  sb.append(" (o.").append(paramName);
		  if("like_left".equals(compareSign))
		  sb.append(compareSign).append(" '%").append(paramValue).append("' ");
		  else if("like_right".equals(compareSign))
		  sb.append(" ").append(compareSign).append(" '").append(paramValue).append("%' ");
		  else if("like".equals(compareSign))
		  sb.append(" ").append(compareSign).append(" '%").append(paramValue).append("%' ");
		  sb.append(")").append(" and ");
	  }
	  return sb;
  }
  /**
	 * =运算
	 * 
	 * @param paramName
	 * @param compareSign
	 * @param paramValue
	 * @return StringBuffer对象
	 */
  public static StringBuffer appendEqual(String paramName,String paramValue){
	  StringBuffer sb=new StringBuffer();
	  if(paramValue!=null&&!"".equals(paramValue.trim())){
		  sb.append(" (o.").append(paramName).append("=").append("'").append(paramValue).append("'");
		  sb.append(")").append(" and ");
	  }
	  return sb;
  }
  /**
	 *compare运算
	 * 
	 * @param paramName
	 * @param compareSign
	 * @param paramValue
	 * @return StringBuffer对象
	 */
  public static StringBuffer appendCompareScope(String paramName,String compareSign1,String paramValue1,String compareSign2,String paramValue2){
	  StringBuffer sb=new StringBuffer();
	  if((paramValue1!=null&&!"".equals(paramValue1.trim()))||(paramValue2!=null&&!"".equals(paramValue2.trim()))){
		  sb.append(" (o.");
		  if(paramValue1!=null&&!"".equals(paramValue1)&&paramValue2!=null&&!"".equals(paramValue2)){
			 sb.append(paramName).append(" ").append(compareSign1).append(paramValue1).append(" and ")
			  .append(" o.").append(paramName).append(" ").append(compareSign2).append(paramValue2);
		  }else if(paramValue1!=null&&!"".equals(paramValue1)){
			  sb.append(paramName).append(" ").append(compareSign1).append(paramValue1);
		  }else if(paramValue2!=null&&!"".equals(paramValue2)){
			  sb.append(paramName).append(" ").append(compareSign2).append(paramValue2);
		  }
		  sb.append(")").append(" and ");
	  }
	  return sb;
  }
  /**
   *  between_and运算
   * @param paramName
   * @param compareSign
   * @param paramValue
   * @return StringBuffer对象
   */
  public static StringBuffer appendBetweenAnd(String paramName,String paramValue1,String paramValue2){
	  StringBuffer sb=new StringBuffer();
	  if(paramValue1!=null&&!"".equals(paramValue1.trim())&&paramValue2!=null&&!"".equals(paramValue2.trim())){
		  sb.append(" (o.").append(paramName).append(" between '").append(paramValue1).append("' and '").append(paramValue2).append("'");
		  sb.append(")").append(" and ");
	  }
	  return sb;
  }
  /**
   *  between_and运算
   * @param paramName
   * @param compareSign
   * @param paramValue
   * @return StringBuffer对象
   */
  public static StringBuffer appendOrderBy(String  orderParams){
	  StringBuffer sb=new StringBuffer();
	  if(orderParams!=null){
		  sb.append(" order by ").append(orderParams);
	  }
	  return sb;
  }
  /**
   * createSQL 生成最终的sql语句
   * @return StringBuffer对象
   */
  public static StringBuffer createSQL(StringBuffer sb){
	  String sql=null;
	  if(!"".equals(sb.toString())){
		  sql="where "+sb.toString();
		  sql=sql.substring(0,sql.lastIndexOf(")")+1)+sql.substring(sql.lastIndexOf("and")+3);
	  }
	  return new StringBuffer().append(sql);
  }
}
