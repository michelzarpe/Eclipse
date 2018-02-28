package result;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import converter.ColaboradorConverter;
import converter.EscalaHorarioRondaMZConverter;
import converter.FornecedorConverter;
import converter.ItemSolicitacaoConverter;
import converter.RequisicaoEstoqueConverter;
import converter.SolicitacaoConverter;
import converter.UsuarioConverter;
import converter.ViagemConverter;

public class BasicResult implements Result {
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
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter responseStream = response.getWriter();
		responseStream.println("<?xml version='1.0' encoding='utf-8' ?>");

		ValueStack valueStack = actionInvocation.getStack();

		Object xmlModel = valueStack.findValue("xmlModel");
		XStream xStream = new XStream(new DomDriver());
		
		String records = (String) valueStack.findValue("totalCount");
		Class classe = (Class) valueStack.findValue("tipo");
		String textoRoot = "";


		if (classe.getName().equals("bean.Colaborador")) {
			xStream.registerConverter(new ColaboradorConverter());
			xStream.alias("colaborador", xmlModel.getClass());
			textoRoot = "list";
		} else if (classe.getName().equals("bean.Solicitacao")) {
			xStream.registerConverter(new SolicitacaoConverter());
			xStream.alias("solicitacao", xmlModel.getClass());
			textoRoot = "list";
		} else if (classe.getName().equals("bean.ItemSolicitacao")) {
			xStream.registerConverter(new ItemSolicitacaoConverter());
			xStream.alias("list", xmlModel.getClass());
			textoRoot = "list";
		} else if (classe.getName().equals("bean.Viagem")) {
			xStream.registerConverter(new ViagemConverter());
			xStream.alias("list", xmlModel.getClass());
			textoRoot = "list";
		} else if (classe.getName().equals("bean.Usuario")) {
			xStream.registerConverter(new UsuarioConverter());
			xStream.alias("usuario", xmlModel.getClass());
			textoRoot = "list";
		}else if (classe.getName().equals("bean.RequisicaoEstoque")){
			xStream.registerConverter(new RequisicaoEstoqueConverter(null));
			xStream.alias("requisicaoEstoque", xmlModel.getClass());
			textoRoot = "list";
		}else if (classe.getName().equals("bean.Fornecedor")){
			xStream.registerConverter(new FornecedorConverter());
			xStream.alias("fornecedor", xmlModel.getClass());
			textoRoot = "list";
		}else if (classe.getName().equals("bean.EscalaHorarioRondaMZ")){
			xStream.registerConverter(new EscalaHorarioRondaMZConverter());
			xStream.alias("escalaHorarioRondaMZ", xmlModel.getClass());
			textoRoot = "list";
		}

		xStream.autodetectAnnotations(true);
		String xml = xStream.toXML(xmlModel);
		int ultimo = 0;
		if (!("<" + textoRoot + "/>").equals(xml)) {
			ultimo = xml.lastIndexOf(">");
			String corpo = xml.substring(0, ultimo - (textoRoot.length() + 3));
			corpo = corpo + ("<totalCount>" + records + "</totalCount>\n")
					+ ("</" + textoRoot + ">");
			responseStream.println(corpo);
		} else {
			responseStream.println(xml);
		}
	}
}
