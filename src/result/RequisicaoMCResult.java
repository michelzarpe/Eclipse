package result;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

import flexjson.JSONSerializer;

public class RequisicaoMCResult implements Result {
	private static final long	serialVersionUID	= 1L;
	public static final String DEFAULT_PARAM = "classAlias";
	String classAlias;

	public String getClassAlias() {
		return classAlias;
	}

	public void setClassAlias(String classAlias) {
		this.classAlias = classAlias;
	}

	@Override
	public void execute(ActionInvocation actionInvocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter responseStream = ServletActionContext.getResponse().getWriter();

		ValueStack valueStack = actionInvocation.getStack();
		Object jsonModel = valueStack.findValue("JSONModel");
		boolean resultado = (Boolean) valueStack.findValue("resultado");
		String msg = (String) valueStack.findValue("mensagem");
		int total = (Integer) valueStack.findValue("total");

		Map<String, Object> wrapper = new HashMap<String, Object>();
		wrapper.put("items", jsonModel);
		wrapper.put("success", resultado);
		wrapper.put("msg", msg);
		wrapper.put("totalCount", total);

		String json = new JSONSerializer().exclude("*.class").serialize(wrapper);

		responseStream.println(json);
	}
}
