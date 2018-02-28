package converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.ServiceFactory;
import bean.Produto.TipoProduto;
import bean.RequisicaoEstoque;
import bean.RequisicaoEstoque.MotivoRequisicao;
import bean.RequisicaoEstoque.Situacao;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import dao.RequisicaoEstoqueDAO;

public class RequisicaoEstoqueConverter extends AbstractCollectionConverter {

	public RequisicaoEstoqueConverter(Mapper mapper) {
		super(mapper);
	}

	public boolean canConvert(Class type) {
		return type.equals(HashMap.class) || type.equals(Hashtable.class)
				|| type.getName().equals("java.util.LinkedHashMap")
				|| type.getName().equals("sun.font.AttributeMap");
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Map<String, Object> source = (Map<String, Object>) value;
		RequisicaoEstoqueDAO requisicaoEstoqueDAO = (RequisicaoEstoqueDAO) ServiceFactory
				.getBean("HRequisicaoEstoqueDAO");
		for (Iterator iterator = source.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (String.valueOf(entry.getKey()).equals("items")) {
				if (entry.getValue() != null) {
					List<RequisicaoEstoque> requisicoes = (ArrayList<RequisicaoEstoque>) entry
							.getValue();
					if (requisicoes.size() > 0) {
						for (RequisicaoEstoque requisicao : (requisicoes)) {
							writer.startNode("items");

							writer.startNode("numEme");
							writer.setValue(String.valueOf(requisicao.getNumEme()));
							writer.endNode();

							writer.startNode("seqEme");
							writer.setValue(String.valueOf(requisicao.getSeqEme()));
							writer.endNode();

							writer.startNode("id");
							writer.setValue(String.valueOf(requisicao.getId()));
							writer.endNode();

							writer.startNode("datReq");
							if (requisicao.getDatReq() != null)
								writer.setValue(formato.format(requisicao.getDatReq()));
							else
								writer.setValue("");
							writer.endNode();

							writer.startNode("qtdEme");
							writer.setValue(String.valueOf(requisicao.getQtdEme()));
							writer.endNode();

							writer.startNode("qtdApr");
							writer.setValue(String.valueOf(requisicao.getQtdApr()));
							writer.endNode();

							writer.startNode("obsEme");
							writer.setValue(requisicao.getObsEme());
							writer.endNode();

							writer.startNode("produto");

							writer.startNode("id");

							writer.startNode("codPro");
							writer.setValue(requisicao.getProduto().getId().getCodPro());
							writer.endNode();

							writer.startNode("empresa");

							writer.startNode("id");
							writer.setValue(String.valueOf(requisicao.getProduto().getId().getEmpresa()
									.getId()));
							writer.endNode();

							writer.endNode();// empresa

							writer.endNode();// id

							writer.startNode("desPro");
							writer.setValue(requisicao.getProduto().getDesPro());
							writer.endNode();

							writer.startNode("uniMed");
							writer.setValue(requisicao.getProduto().getUniMed());
							writer.endNode();

							writer.endNode();// produto
							if (requisicao.getProduto().getTipPro().equals(TipoProduto.MC)) {
								writer.startNode("cliente");
								writer.startNode("id");
								writer.startNode("codFil");
								writer.setValue(String.valueOf(requisicao.getCliente().getId().getCodFil()));
								writer.endNode();// codFil
								writer.startNode("empresaId");
								writer.setValue(String.valueOf(requisicao.getCliente().getId()
										.getEmpresaId()));
								writer.endNode();// empresaId
								writer.endNode();// id cliente
								writer.endNode();// cliente
							}

							writer.startNode("usuSol");
							writer.startNode("codUsu");
							writer.setValue(requisicao.getUsuSol().getCodUsu());
							writer.endNode();// codUsu
							writer.startNode("id");
							writer.setValue(String.valueOf(requisicao.getUsuSol().getId()));
							writer.endNode();// id
							writer.endNode();// usuSol

							if (requisicao.getUsuApr() != null) {
								writer.startNode("usuApr");
								writer.startNode("codUsu");
								writer.setValue(requisicao.getUsuApr().getCodUsu());
								writer.endNode();// codUsu
								writer.startNode("id");
								writer.setValue(String.valueOf(requisicao.getUsuApr().getId()));
								writer.endNode();// id
								writer.endNode();// usuApr
							}

							writer.startNode("datApr");
							if (requisicao.getDatApr() != null)
								writer.setValue(formato.format(requisicao.getDatApr()));
							else
								writer.setValue("");
							writer.endNode();

							if (requisicao.getUsuPrc() != null) {
								writer.startNode("usuPrc");
								writer.startNode("codUsu");
								writer.setValue(requisicao.getUsuPrc().getCodUsu());
								writer.endNode();// codUsu
								writer.startNode("id");
								writer.setValue(String.valueOf(requisicao.getUsuPrc().getId()));
								writer.endNode();// id
								writer.endNode();// usuPrc
							}

							writer.startNode("datPrc");
							if (requisicao.getDatPrc() != null)
								writer.setValue(formato.format(requisicao.getDatPrc()));
							else
								writer.setValue("");
							writer.endNode();

							if (requisicao.getProduto().getTipPro().equals(TipoProduto.MC)) {
								writer.startNode("supervisor");
								writer.startNode("id");
								writer.setValue(String.valueOf(requisicao.getSupervisor().getId()));
								writer.endNode();// id
								writer.endNode();// supervisor
							}
							writer.startNode("conCli");
							if (requisicao.getConCli() != null)
								writer.setValue(requisicao.getConCli());
							else
								writer.setValue("");
							writer.endNode();

							writer.startNode("motReq");
							if (requisicao.getMotReq() != null)
								writer.setValue(requisicao.getMotReq().name());
							else
								writer.setValue(MotivoRequisicao.I.name());
							writer.endNode();// motReq

							writer.startNode("sitEme");
							writer.setValue(requisicao.getSitEme().name());
							writer.endNode();
							writer.startNode("idSit");
							writer.setValue(String.valueOf(requisicao.getSitEme().getId()));
							writer.endNode();

							writer.startNode("desSit");
							writer.setValue(requisicao.getSitEme().getDescricao());
							writer.endNode();// descricao

							writer.startNode("aprovada");
							if (requisicao.getSitEme().equals(Situacao.Aprovado))
								writer.setValue(String.valueOf(true));
							else
								writer.setValue(String.valueOf(false));
							writer.endNode();// aprovada

							writer.startNode("cmpReq");
							if (requisicao.getCmpReq() != null)
								writer.setValue(requisicao.getCmpReq());
							else
								writer.setValue("");
							writer.endNode();// cmpReq

							writer.startNode("centro");
							writer.startNode("id");
							writer.setValue(String.valueOf(requisicao.getCentro().getId()));
							writer.endNode();
							writer.startNode("nomCcu");
							writer.setValue(requisicao.getCentro().getNomCcu());
							writer.endNode();// nomCcu
							writer.endNode();// centro

							writer.startNode("setor");
							writer.setValue(requisicao.getSetor().name());
							writer.endNode();

							writer.startNode("desSet");
							writer.setValue(requisicao.getSetor().getDescricao());
							writer.endNode();

							if (requisicao.getSitEme().equals(Situacao.Digitado)) {
								/**
								 * buscar a quantidade aprovada do item no mês anterior
								 **/
								double qtdAprMesAnt = requisicaoEstoqueDAO.buscaQtdAprMesAnt(requisicao);
								writer.startNode("qtdAprMesAnt");
								writer.setValue(String.valueOf(qtdAprMesAnt));
								writer.endNode();
							}

							writer.startNode("preTot");
							writer.setValue(String.valueOf(requisicao.getVlrUni() * requisicao.getQtdEme()));
							writer.endNode();

							writer.startNode("preUni");
							writer.setValue(String.valueOf(requisicao.getVlrUni()));
							writer.endNode();

							writer.endNode();// items
						}
					} else {
						/** sem items **/
						writer.startNode("items");

						writer.startNode("produto");
						writer.startNode("id");
						writer.startNode("codPro");
						writer.endNode();// codPro
						writer.startNode("empresa");
						writer.startNode("id");
						writer.endNode();// id empresa
						writer.endNode();// empresa
						writer.endNode();// id
						writer.endNode();// produto

						writer.startNode("usuSol");
						writer.startNode("codUsu");
						writer.endNode();// codUsu
						writer.startNode("centro");
						writer.startNode("id");
						writer.endNode();// id centro
						writer.endNode();// centro
						writer.endNode();// usuSol

						writer.startNode("centro");
						writer.startNode("id");
						writer.endNode();// id centro
						writer.endNode();// centro

						writer.endNode();// items
					}
				} else {
					writer.startNode("items");

					writer.startNode("usuario");

					writer.startNode("codUsu");
					writer.setValue("");
					writer.endNode();// codUsu vazio

					writer.endNode();// usuario vazio

					writer.startNode("id");
					writer.setValue("0");
					writer.endNode();// id

					writer.startNode("produto");

					writer.startNode("id");

					writer.startNode("codPro");
					writer.setValue("");
					writer.endNode();// codpro vazio

					writer.endNode();// id produto vazio

					writer.startNode("empresa");

					writer.startNode("id");
					writer.setValue("");
					writer.endNode();

					writer.endNode();// empresa

					writer.endNode();// produto vazio

					writer.endNode();// items vazio
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

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map map = (Map) createCollection(context.getRequiredType());
		populateMap(reader, context, map);
		return map;
	}

	protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();

			reader.moveDown();
			Object key = readItem(reader, context, map);
			reader.moveUp();

			reader.moveDown();
			Object value = readItem(reader, context, map);
			reader.moveUp();

			map.put(key, value);

			reader.moveUp();
		}
	}
}
