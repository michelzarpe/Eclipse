package converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.hibernate.collection.PersistentSet;

import util.ServiceFactory;
import bean.Colaborador;
import bean.ItemSolicitacao;
import bean.Solicitacao;
import bean.Uniforme;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import dao.ItemSolicitacaoDAO;

public class ItemSolicitacaoConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		ItemSolicitacaoDAO itemSolicitacaoDAO = (ItemSolicitacaoDAO) ServiceFactory.getBean("HItemSolicitacaoDAO");
		int i = 1;
		if (value.getClass().equals(PersistentSet.class)) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formatoHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			for (Iterator iterator = ((PersistentSet) value).iterator(); iterator.hasNext();) {
				ItemSolicitacao item = (ItemSolicitacao) iterator.next();
				Solicitacao solicitacao = new Solicitacao();
				solicitacao = item.getSolicitacao();
				Colaborador colaborador = new Colaborador();
				colaborador = solicitacao.getColaborador();
				Uniforme uniforme = new Uniforme();
				uniforme = item.getUniforme();
				writer.startNode("item");

				writer.addAttribute("solicitacaoId", String.valueOf(solicitacao.getId()));
				writer.addAttribute("cobCol", String.valueOf(item.isCobCol()));
				if (item.getDatInc() != null)// data de inclusão do item na solicitação
					writer.addAttribute("datInc", formatoHora.format(item.getDatInc()));
				else
					writer.addAttribute("datInc", "");

				writer.startNode("datEnt");
				writer.setValue(formato.format(solicitacao.getDatEnt()));
				writer.endNode();

				writer.startNode("datEnv");
				if (item.getDatEnv() != null)
					writer.setValue(formato.format(item.getDatEnv()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("datPro");
				if (item.getDatPro() != null)
					writer.setValue(formato.format(item.getDatPro()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("datDev");
				if (item.getDatDev() != null)
					writer.setValue(formato.format(item.getDatDev()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("datExp");
				if (item.getDatExp() != null)
					writer.setValue(formato.format(item.getDatExp()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("datAtu");
				if (item.getDatAtu() != null)
					writer.setValue(formato.format(item.getDatAtu()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("nroItm");
				writer.setValue(String.valueOf(i));
				writer.endNode();

				writer.startNode("qtdEnt");
				writer.setValue(String.valueOf(item.getQtdEnt()));
				writer.endNode();

				writer.startNode("mtvSol");
				if (item.getMtvSol() != null)
					writer.setValue(item.getMtvSol());
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("motLib");
				if (item.getMotLib() != null)
					writer.setValue(item.getMotLib());
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("entAut");
				if (item.getEntAut() != null)
					writer.setValue(item.getEntAut());
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("usuLib");
				if (item.getUsuarioLiberacao() != null) {
					writer.addAttribute("id", String.valueOf(item.getUsuarioLiberacao().getId()));
					writer.setValue(item.getUsuarioLiberacao().getNomFun());
				} else
					writer.setValue("null");
				writer.endNode();

				writer.startNode("datLib");
				if (item.getDatLib() != null)
					writer.setValue(formato.format(item.getDatLib()));
				else
					writer.setValue("");

				writer.endNode();

				writer.startNode("datAte");
				if (item.getDatAte() != null)
					writer.setValue(formato.format(item.getDatAte()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("datNeg");
				if (item.getDatNeg() != null)
					writer.setValue(formato.format(item.getDatNeg()));
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("obs");
				writer.setValue(item.getObsDis());
				writer.endNode();

				writer.startNode("codSit");
				writer.setValue(item.getSitItm().name());
				writer.endNode();

				writer.startNode("sitItm");
				writer.setValue(item.getSitItm().getDescricao());
				writer.endNode();

				writer.startNode("uniforme");

				writer.startNode("id");
				writer.setValue(String.valueOf(uniforme.getId()));
				writer.endNode();

				writer.startNode("desEpi");
				writer.setValue(uniforme.getDesEpi());
				writer.endNode();

				writer.startNode("cplDes");
				writer.setValue(uniforme.getCplDes());
				writer.endNode();

				writer.startNode("qtdMax");
				writer.setValue(String.valueOf(uniforme.getQtdMax()));
				writer.endNode();

				writer.startNode("tipo");
				writer.startNode("id");
				writer.setValue(String.valueOf(uniforme.getTipoEPI().getId()));
				writer.endNode();

				writer.startNode("desSvc");
				writer.setValue(uniforme.getTipoEPI().getDesSvc());
				writer.endNode();

				writer.endNode();

				writer.endNode();// uniforme

				writer.startNode("datUltEnt");
				Date dataEntrega = solicitacao.getDatEnt();
				Date dataTroca = new Date();
				if (dataEntrega != null) {
					int idCol = colaborador.getId();
					dataTroca = itemSolicitacaoDAO.getDataPermitidaTroca(idCol, uniforme, dataEntrega);
				}
				if ((dataEntrega != null) && (dataTroca != null)) {
					writer.setValue(formato.format(dataTroca));
				} else
					writer.setValue("");

				writer.endNode();

				writer.startNode("datVen");

				if (dataTroca != null) {
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(dataTroca);
					calendar.add(Calendar.DATE, uniforme.getDiaVal());
					dataTroca = calendar.getTime();
					writer.setValue(formato.format(dataTroca));
				} else
					writer.setValue("");
				writer.endNode();// datVen

				writer.endNode();// item
				i++;
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

	@Override
	public boolean canConvert(Class clazz) {
		return true;
	}
}
