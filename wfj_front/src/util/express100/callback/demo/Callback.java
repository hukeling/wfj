package util.express100.callback.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.express100.bean.NoticeRequest;
import util.express100.bean.NoticeResponse;
import util.express100.bean.Result;



public class Callback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Callback() {
		super();
	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		NoticeResponse resp = new NoticeResponse();
		resp.setResult(false);
		resp.setReturnCode("500");
		resp.setMessage("保存失败");
		try {
			String param = request.getParameter("param");
			NoticeRequest nReq = JacksonHelper.fromJSON(param,
					NoticeRequest.class);

			Result result = nReq.getLastResult();
			// 处理快递结果
			
			resp.setResult(true);
			resp.setReturnCode("200");
			PrintWriter out = response.getWriter();
			out.print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
			out.flush();
			out.close();
		} catch (Exception e) {
			resp.setMessage("保存失败" + e.getMessage());
			PrintWriter out = response.getWriter();
			out.print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
			out.flush();
			out.close();
		}
	}

}
