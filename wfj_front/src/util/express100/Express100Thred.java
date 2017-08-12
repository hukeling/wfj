package util.express100;
import util.express100.bean.TaskResponse;
import util.express100.postOrder.demo.JacksonHelper;
import util.other.Utils;

/**  
* 订阅快递100线程类，在订单中使用，避免订单流程异常
* @author 
*/    
public class Express100Thred implements Runnable{
	
	/**快递100响应实体类*/
	private TaskResponse resp;
	/**向快递100发送信息*/
	private String ret;
	/**订阅失败后休眠时间*/
	private int time;
	/**请求失败默认重新请求次数*/
	private int configNumber;
	
	public Express100Thred() {
		super();
	}
	
	/**
	 * @param ret 向快递100发送信息
	 * @param time 失败后，多长时间后再次发起请求
	 * @param configNumber 请求失败默认重新请求次数
	 */
	public Express100Thred(String ret,int time,int configNumber) {
		super();
		this.resp = new TaskResponse();
		this.ret = ret;
		this.time = time;
		this.configNumber = configNumber;
	}
	public void run() {
		if(Utils.objectIsNotEmpty(ret)){
			try {
				while (this.configNumber>0) {
					resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
					if(resp.getResult()==true){
						this.configNumber = -1;
						//System.out.println("订阅快递信息成功！");
					}else{
						this.configNumber--;
//						if(this.configNumber==0){
//							System.out.println("对不起，您的请求次数已用完，订阅快递信息失败！");
//						}else{
//							System.out.println("订阅快递信息失败，"+this.time+"秒后重新发起请求！最多还可请求【"+this.configNumber+"】次");
//						}
						//休眠时间
						Thread.sleep(this.time*1000);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
