package result;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import bean.Documento;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

public class DocumentoJSONResult implements Result {

	private static final long	serialVersionUID	= 1L;

	@Override
	public void execute(ActionInvocation actionInvocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=ISO-8859-1");
		PrintWriter responseStream = ServletActionContext.getResponse().getWriter();

		ValueStack valueStack = actionInvocation.getStack();
		Object jsonModel = valueStack.findValue("JSONModel");

		JSONArray jArray = new JSONArray();
		List<Documento> documentos = new ArrayList<Documento>();
		documentos = (List<Documento>) jsonModel;

		for (Iterator<Documento> iterator = documentos.iterator(); iterator.hasNext();) {
			Documento documento = (Documento) iterator.next();
			JSONObject jsonOne = new JSONObject();
			jsonOne.put("key", documento.getId());
			jsonOne.put("title", documento.getNomDoc());
			if (documento.getArquivo() != null) {
				jsonOne.put("href", "documento!verArquivo.action?documento.id=" + documento.getId());
			}
			Set<Documento> docFilhos = documento.getDocFilhos();
			if (!docFilhos.isEmpty()) {
				JSONArray jaFilhos = new JSONArray();
				jsonOne.put("folder", true);
				for (Iterator<Documento> itFilhos = documento.getDocFilhos().iterator(); itFilhos
						.hasNext();) {
					Documento docFilho = (Documento) itFilhos.next();
					JSONObject jsonFilho = new JSONObject();
					jsonFilho.put("key", docFilho.getId());
					jsonFilho.put("title", docFilho.getNomDoc());

					if (!docFilho.getDocFilhos().isEmpty()) {// se o filho possuir filhos, configurar para lazy e como pasta
						jsonFilho.put("folder", true);
						jsonFilho.put("lazy", true);
					} else {
						jsonFilho.put("folder", false);
						jsonFilho.put("extraClasses", "cssPdf");
					}
					if (docFilho.getArquivo() != null) {
						jsonFilho.put("href",
								"documento!verArquivo.action?documento.id=" + docFilho.getId());
					}
					jaFilhos.put(jsonFilho);
				}
				jsonOne.put("children", jaFilhos);

			} else {
				jsonOne.put("folder", false);
				jsonOne.put("extraClasses", "cssPdf");
			}
			jArray.put(jsonOne);
		}
		responseStream.print(jArray);
	}

}
