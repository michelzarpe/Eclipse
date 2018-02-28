package converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bean.Cotacao;
import bean.SolicitacaoCompra;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class SolicitacaoCompraConverter extends AbstractCollectionConverter {

	public SolicitacaoCompraConverter(Mapper mapper) {
		super(mapper);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Map<String, Object> source = (Map<String, Object>) value;
		for (Iterator iterator = source.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (String.valueOf(entry.getKey()).equals("items")) {
				if (entry.getValue() != null) {
					List<SolicitacaoCompra> solicitacoes = (ArrayList<SolicitacaoCompra>) entry.getValue();
					if (solicitacoes.size() > 0) {
						for (SolicitacaoCompra solicitacao : (solicitacoes)) {
							writer.startNode("items");

							writer.startNode("id");
							writer.setValue(String.valueOf(solicitacao.getId()));
							writer.endNode();

							writer.startNode("cplPro");
							writer.setValue(solicitacao.getCplPro());
							writer.endNode();

							writer.startNode("datSol");
							if (solicitacao.getDatSol() != null)
								writer.setValue(formato.format(solicitacao.getDatSol()));
							else
								writer.setValue("");
							writer.endNode();

							for (Cotacao cotacao : solicitacao.getCotacoes()) {
								writer.startNode("cotacao");

								writer.startNode("id");
								writer.setValue(String.valueOf(cotacao.getId()));
								writer.endNode();

								writer.startNode("fornecedor");

								writer.startNode("id");
								writer.setValue(String.valueOf(cotacao.getFornecedor().getId()));
								writer.endNode();

								writer.startNode("cgcCpf");
								writer.setValue(cotacao.getFornecedor().getCgcCpf());
								writer.endNode();

								writer.startNode("tipFor");
								writer.setValue(cotacao.getFornecedor().getTipFor());
								writer.endNode();

								writer.endNode();// fornecedor

								writer.startNode("qtdCot");
								writer.setValue(String.valueOf(cotacao.getQtdCot()));
								writer.endNode();

								writer.startNode("preCot");
								writer.setValue(String.valueOf(cotacao.getPreCot()));
								writer.endNode();

								writer.startNode("przEnt");
								writer.setValue(new SimpleDateFormat("dd/MM/yyyy").format(cotacao
										.getPrzEnt()));
								writer.endNode();

								writer.startNode("condicaoPagamento");

								writer.startNode("id");
								writer.setValue(cotacao.getCondicaoPagamento().getId());
								writer.endNode();

								writer.endNode();// condicaoPagamento

								writer.startNode("obsCot");
								writer.setValue(cotacao.getObsCot());
								writer.endNode();

								writer.endNode();// cotacao
							}

							writer.startNode("usuSol");
							writer.startNode("codUsu");
							writer.setValue(solicitacao.getUsuSol().getCodUsu());
							writer.endNode();// codUsu
							writer.startNode("id");
							writer.setValue(String.valueOf(solicitacao.getUsuSol().getId()));
							writer.endNode();// id
							writer.startNode("centro");
							writer.startNode("id");
							writer.setValue(String.valueOf(solicitacao.getUsuSol().getCentro()
									.getId()));
							writer.endNode();// id
							writer.startNode("nomCcu");
							writer.setValue(solicitacao.getUsuSol().getCentro().getNomCcu());
							writer.endNode();
							writer.endNode();// centro
							writer.endNode();// usuSol

							writer.endNode();// items
						}
					}
				}
			}
			if (String.valueOf(entry.getKey()).equals("success")) {
				writer.startNode("success");
				context.convertAnother((Boolean) entry.getValue());
				writer.endNode();
			} else if (String.valueOf(entry.getKey()).equals("totalCount")) {
				writer.startNode("totalCount");
				writer.setValue(String.valueOf(entry.getValue()));
				writer.endNode();
			} else if (String.valueOf(entry.getKey()).equals("msg")) {
				writer.startNode("msg");
				writer.setValue(String.valueOf(entry.getValue()));
				writer.endNode();
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	public boolean canConvert(Class type) {
		return type.equals(HashMap.class) || type.equals(Hashtable.class)
				|| type.getName().equals("java.util.LinkedHashMap")
				|| type.getName().equals("sun.font.AttributeMap");
	}

}
