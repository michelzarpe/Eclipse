package result;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import converter.EstadoConverter;
import converter.RequisicaoEstoqueConverter;
import converter.SolicitacaoCompraConverter;

public class JSONResult implements Result {
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
		Class classe = (Class) valueStack.findValue("tipo");

		XStream xStream = new XStream(new JettisonMappedXmlDriver());
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("success", resultado);
		mapa.put("totalCount", total);
		mapa.put("msg", msg);
		mapa.put("items", jsonModel);
		
		xStream.alias("items", mapa.getClass());
		if (classe.getName().equals("bean.RequisicaoEstoque")) {
			xStream.registerConverter(new RequisicaoEstoqueConverter(xStream.getMapper()),
					XStream.PRIORITY_VERY_HIGH);
		} else if (classe.getName().equals("bean.Estado")) {
			xStream.registerConverter(new EstadoConverter(xStream.getMapper()),
					XStream.PRIORITY_VERY_HIGH);
		} else if(classe.getName().equals("bean.SolicitacaoCompra")){
			xStream.registerConverter(new SolicitacaoCompraConverter(xStream.getMapper()),
					XStream.PRIORITY_VERY_HIGH);
		}
		if (classAlias == null) {
			classAlias = "object";
		}
		xStream.toXML(mapa, responseStream);
	}

}
