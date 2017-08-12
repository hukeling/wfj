package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import phone.pojo.PhoneLBT;
import phone.service.IPhoneLBTService;
import util.action.BaseAction;

@SuppressWarnings("serial")
@Controller
public class PhoneLBTAction extends BaseAction {
	@Resource
	private IPhoneLBTService phoneLBTService;

	private PhoneLBT phontLBT;
	
	// 返回手机首页轮播图数据
	public void getPhoneLbt() throws IOException {
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		String hql = "select h.broadcastingIamgeUrl as broadcastingIamgeUrl,h.interlinkage as interlinkage from PhoneLBT h where h.isShow=1 order by sortCode asc";
		List<Map> list = phoneLBTService.findListMapByHql(hql);
		jo.accumulate("Status", "true");
		jo.accumulate("phoneLbt", list);
		response.setContentType("text/html;charset=utf-8");
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	public void setPhoneLBTService(IPhoneLBTService phoneLBTService) {
		this.phoneLBTService = phoneLBTService;
	}

	public void setPhontLBT(PhoneLBT phontLBT) {
		this.phontLBT = phontLBT;
	}

	public IPhoneLBTService getPhoneLBTService() {
		return phoneLBTService;
	}

	public PhoneLBT getPhontLBT() {
		return phontLBT;
	}

}
