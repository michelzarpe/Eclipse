package result;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

import converter.LocalConverter;

public class LocalJSONResult implements Result {
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

		XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
		xStream.registerConverter(new LocalConverter());
		xStream.alias("items", List.class);
		String xml = xStream.toXML(jsonModel);
		int ultimo = xml.lastIndexOf("]");
		String corpo = xml.substring(0, ultimo + 1);
		corpo = corpo + ", label:\"nomLoc\", identifier: \"id\"}";
		responseStream.println(corpo);
	}
}
