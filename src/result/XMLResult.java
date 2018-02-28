package result;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import converter.EnumConverter;

public class XMLResult implements Result {
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
		// ServletActionContext.getResponse().setContentType("text/plain");

		response.setContentType("text/xml;charset=utf-8");

		PrintWriter responseStream = response.getWriter();

		responseStream.println("<?xml version='1.0' encoding='utf-8' ?>");

		ValueStack valueStack = actionInvocation.getStack();
		Object xmlModel = valueStack.findValue("xmlModel");
		// String page = (String) valueStack.findValue("page");
		// String pages = (String) valueStack.findValue("pages");
		String records = (String) valueStack.findValue("totalCount");
		/**
		 * Se quiser a resposta em json, usar esse construtor XStream xStream =
		 * new XStream(new JettisonMappedXmlDriver());
		 **/
		XStream xStream = new XStream(new DomDriver());
		if (classAlias == null) {
			classAlias = "object";
		}
		xStream.alias(classAlias, xmlModel.getClass());
		xStream.alias("rows", List.class);
		xStream.alias("rows", xmlModel.getClass());
		xStream.autodetectAnnotations(true);
		Class fieldType = xmlModel.getClass().getComponentType();
		
		if (fieldType != null)
			if (fieldType.isEnum())
				xStream.registerConverter(new EnumConverter());
		
		/*xStream.setMode(XStream.NO_REFERENCES);*/
		xStream.setMode(XStream.ID_REFERENCES);
		
		String xml = xStream.toXML(xmlModel);
		int ultimo = 0;
		if (!("<rows/>").equals(xml)) {
			ultimo = xml.lastIndexOf(">");
			String corpo = xml.substring(0, ultimo - 7);
			corpo = corpo + ("<totalCount>" + records + "</totalCount>\n") + ("</rows>");
			responseStream.println(corpo);
		} else {
			responseStream.println(xml);
		}
		
	}

}