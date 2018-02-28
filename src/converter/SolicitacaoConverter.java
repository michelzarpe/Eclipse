package converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bean.Solicitacao;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class SolicitacaoConverter implements Converter {
	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext arg2) {
		/**
		 * Verifica se o que está sendo convertido é uma lista de solicitações
		 * ou é uma só
		 */
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		if (value.getClass().equals(ArrayList.class)) {
			for (Solicitacao solicitacao : (ArrayList<Solicitacao>) value) {
				writer.startNode("solicitacao");
				writer.addAttribute("motivo", String.valueOf(solicitacao.getMotivo().getId()));
				writer.addAttribute("desMtv", solicitacao.getMotivo().getDesMtv());
				writer.addAttribute("numSeq", String.valueOf(solicitacao.getNumSeq()));
				writer.addAttribute("viaWeb", String.valueOf(solicitacao.isViaWeb()));
				if (solicitacao.getDatInc() != null)
					writer.addAttribute("datInc", formato.format(solicitacao.getDatInc()));
				else
					writer.addAttribute("datInc", "");
				geraXML(solicitacao, writer);
				writer.endNode();
			}
		} else {
			writer.startNode("solicitacao");
			writer.addAttribute("motivo", String.valueOf(((Solicitacao) value).getMotivo().getId()));
			writer.addAttribute("desMtv", ((Solicitacao) value).getMotivo().getDesMtv());
			writer.addAttribute("numSeq", String.valueOf(((Solicitacao) value).getNumSeq()));
			writer.addAttribute("viaWeb", String.valueOf(((Solicitacao) value).isViaWeb()));
			if (((Solicitacao) value) != null)
				writer.addAttribute("datInc", formato.format(((Solicitacao) value).getDatInc()));
			else
				writer.addAttribute("datInc", "");
			geraXML((Solicitacao) value, writer);
			writer.endNode();
		}
	}

	private void geraXML(Solicitacao solicitacao, HierarchicalStreamWriter writer) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		writer.startNode("solicitacaoId");
		writer.setValue(String.valueOf(solicitacao.getId()));
		writer.endNode();

		writer.startNode("codSitSol");
		writer.setValue(solicitacao.getSituacao().name());
		writer.endNode();

		writer.startNode("situacao");
		writer.setValue(solicitacao.getSituacao().getDescricao());
		writer.endNode();

		writer.startNode("datEnt");
		writer.setValue(formato.format(solicitacao.getDatEnt()));
		writer.endNode();

		writer.startNode("empresa");
		writer.setValue(String.valueOf(solicitacao.getColaborador().getEmpresa().getNomEmp()));
		writer.endNode();

		writer.startNode("colaborador");
		if (solicitacao.getColaborador().getCargo() != null) {
			writer.addAttribute("cargo", solicitacao.getColaborador().getCargo().getTitCar());
		}
		writer.addAttribute("id", String.valueOf(solicitacao.getColaborador().getId()));
		writer.addAttribute("numCad", String.valueOf(solicitacao.getColaborador().getNumCad()));
		writer.addAttribute("nomFun", solicitacao.getColaborador().getNomFun());
		writer.addAttribute("empresa", solicitacao.getColaborador().getEmpresa().getNomEmp());
		//writer.addAttribute("centro", solicitacao.getColaborador().getCentro().getNomCcu());
		writer.endNode();// colaborador

		// para os casos antigos, importados do SM, que não tem supervisor
		if (solicitacao.getSupervisor() != null) {
			writer.startNode("supervisor");
			writer.addAttribute("nomFun", solicitacao.getSupervisor().getNomFun());
			writer.addAttribute("id", String.valueOf(solicitacao.getSupervisor().getId()));
			writer.endNode();
		}
		// para os casos antigos, importados do SM, que não tem solicitante
		if (solicitacao.getSolicitante() != null) {
			writer.startNode("solicitante");
			writer.addAttribute("nomFun", solicitacao.getSolicitante().getNomFun());
			writer.addAttribute("id", String.valueOf(solicitacao.getSolicitante().getId()));
			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	@Override
	public boolean canConvert(Class arg0) {
		return true;
	}

}
