package util.action;
/**
 * SecurityCode - 类描述信息
 */
import java.util.Arrays;
/**
 * 工具类，生成随机验证码字符串
 * @version 1.0 2012/08/21
 * @author dongliyang
 *
 */
public class SecurityCode {
    /**
     * 验证码难度级别，Simple只包含数字，Medium包含数字和小写英文，Hard包含数字和大小写英文
     */
    public enum SecurityCodeLevel {Simple,Medium,Hard,onlyChar};
    /**
     * 产生默认验证码，4位中等难度
     * @return  String 验证码
     */
    public static String getSecurityCode(){
        return getSecurityCode(4,SecurityCodeLevel.Medium,false);
    }
    /**
     * 产生长度和难度任意的验证码
     * @param length  长度
     * @param level   难度级别
     * @param isCanRepeat  是否能够出现重复的字符，如果为true，则可能出现 5578这样包含两个5,如果为false，则不可能出现这种情况
     * @return  String 验证码
     */
    public static String getSecurityCode(int length,SecurityCodeLevel level,boolean isCanRepeat){
        int len=length;
        char[] codes={'1','2','3','4','5','6','7','8','9',
                      'a','b','c','d','e','f','g','h','i',
                      'j','k','m','n','p','q','r','s','t',
                      'u','v','w','x','y','z','A','B','C',
                      'D','E','F','G','H','I','J','K','L',
                      'M','N','P','Q','R','S','T','U','V',
                      'W','X','Y','Z'};
        if(level==SecurityCodeLevel.Simple){
            codes=Arrays.copyOfRange(codes, 0,9);
        }else if(level==SecurityCodeLevel.Medium){
            codes=Arrays.copyOfRange(codes, 0,33);
        }else if(level==SecurityCodeLevel.onlyChar){
        	codes=Arrays.copyOfRange(codes, 9,33);
        }
        int n=codes.length;
        if(len>n&&isCanRepeat==false){
            throw new RuntimeException(
                    String.format("调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" +
                                   "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s",
                                   len,level,isCanRepeat,n));
        }
        char[] result=new char[len];
        if(isCanRepeat){
            for(int i=0;i<result.length;i++){
                int r=(int)(Math.random()*n);
                result[i]=codes[r];
            }
        }else{
            for(int i=0;i<result.length;i++){
                int r=(int)(Math.random()*n);
                result[i]=codes[r];
                codes[r]=codes[n-1];
                n--;
            }
        }
        return String.valueOf(result);
    }
}
