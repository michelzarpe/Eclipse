package converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bean.AdiantamentoViagem;
import bean.Centro;
import bean.DespesaViagem;
import bean.Viagem;
import bean.Viagem.Situacao;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ViagemConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext arg2) {
		/**
		 * Verifica se o que está sendo convertido é uma lista de solicitações
		 * ou é uma só
		 */
		if (value.getClass().equals(ArrayList.class)) {
			for (Viagem viagem : (ArrayList<Viagem>) value) {
				writer.startNode("viagem");
				geraXML(viagem, writer);
				writer.endNode();// viagem
			}
		} else {
			writer.startNode("viagem");
			geraXML((Viagem) value, writer);
			writer.endNode();// viagem
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

	private void geraXML(Viagem viagem, HierarchicalStreamWriter writer) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		writer.addAttribute("viagemId", String.valueOf(viagem.getId()));
		writer.addAttribute("datSol", formato.format(viagem.getDatSol()));
		writer.addAttribute("itnVgm", viagem.getItnVgm());
		writer.addAttribute("datSai", formato.format(viagem.getDatSai()));
		if(viagem.getDatChe() != null)
		   writer.addAttribute("datChe", formato.format(viagem.getDatChe()));
		else
		   writer.addAttribute("datChe", "");
		writer.addAttribute("datInc", formato.format(viagem.getDatInc()));
		if (viagem.getCplMtv() != null)
			writer.addAttribute("cplMtv", viagem.getCplMtv());

		writer.startNode("empresa");
		writer.addAttribute("id", String.valueOf(viagem.getEmpresa().getId()));
		writer.addAttribute("nomEmp", viagem.getEmpresa().getNomEmp());
		writer.endNode();// empresa

		writer.startNode("colaborador");
		writer.addAttribute("id", String.valueOf(viagem.getColaborador().getId()));
		writer.addAttribute("nomFun", viagem.getColaborador().getNomFun());
		writer.addAttribute("numCad", String.valueOf(viagem.getColaborador().getNumCad()));

		writer.endNode();// colaborador

		writer.startNode("solicitante");
		writer.addAttribute("id", String.valueOf(viagem.getSolicitante().getId()));
		writer.addAttribute("nomFun", viagem.getSolicitante().getNomFun());
		writer.addAttribute("numCad", String.valueOf(viagem.getSolicitante().getNumCad()));
		writer.endNode();// solicitante

		writer.startNode("usuario");
		writer.addAttribute("id", String.valueOf(viagem.getUsuario().getId()));
		writer.addAttribute("nomFun", viagem.getUsuario().getNomFun());
		writer.addAttribute("numCad", String.valueOf(viagem.getUsuario().getNumCad()));
		writer.endNode();// usuário

		writer.startNode("motivo");
		writer.addAttribute("id", String.valueOf(viagem.getMotivo().getId()));
		writer.addAttribute("desMtv", viagem.getMotivo().getDesMtv());
		writer.endNode();// motivo

		int qtdAdtAb = 0;
		writer.startNode("adiantamentos");
		for (AdiantamentoViagem adiantamentoViagem : viagem.getAdiantamentos()) {
			writer.startNode("adiantamento");
			writer.addAttribute("id", String.valueOf(adiantamentoViagem.getId().getId()));
			writer.addAttribute("viagemId", String.valueOf(viagem.getId()));
			writer.addAttribute("vlrAdt", String.valueOf(adiantamentoViagem.getVlrAdt()));
			writer.addAttribute("datAdt", formato.format(adiantamentoViagem.getDatAdt()));
			if (adiantamentoViagem.getDatAce() != null)
				writer.addAttribute("datAce", formato.format(adiantamentoViagem.getDatAce()));
			else
				qtdAdtAb++;
			writer.endNode();// adiantamento
		}
		writer.endNode();

		int qtdDesAb = 0;
		writer.startNode("despesas");
		for (DespesaViagem despesaViagem : viagem.getDespesas()) {
			writer.startNode("despesaViagem");
			writer.addAttribute("viagemId", String.valueOf(viagem.getId()));
			writer.addAttribute("centroId", String.valueOf(despesaViagem.getId().getCentro()
					.getId()));
			writer.addAttribute("despesaId", String.valueOf(despesaViagem.getId().getDespesa()
					.getId()));
			writer.addAttribute("vlrDes", String.valueOf(despesaViagem.getVlrDes()));
			writer.addAttribute("desDes", despesaViagem.getId().getDespesa().getDesDes());
			writer.addAttribute("nomCcu", despesaViagem.getId().getCentro().getNomCcu());
			if (despesaViagem.getDatAce() != null) {
				writer.addAttribute("datAce", formato.format(despesaViagem.getDatAce()));
			} else {
				qtdDesAb++;
			}
			if (despesaViagem.getAdiantamentoViagem() != null) {
				writer.addAttribute("adiantamentoId", String.valueOf(despesaViagem
						.getAdiantamentoViagem().getId().getId()));
				writer.addAttribute("viagemAdtId", String.valueOf(despesaViagem
						.getAdiantamentoViagem().getId().getViagem().getId()));
			}
			writer.endNode();
		}
		writer.endNode();

		writer.startNode("centrosVisitados");
		for (Centro centro : viagem.getCentrosVisitados()) {
			writer.startNode("centro");
			writer.addAttribute("id", String.valueOf(centro.getId()));
			writer.addAttribute("nomCcu", centro.getNomCcu());
			writer.endNode();// centro
		}
		writer.endNode();// centros visitados
		writer.startNode("situacao");
		if (qtdAdtAb + qtdDesAb > 0) {
			writer.addAttribute("id", Situacao.AB.name());
			writer.addAttribute("descricao", Situacao.AB.getDescricao());
		} else {
			writer.addAttribute("id", Situacao.FE.name());
			writer.addAttribute("descricao", Situacao.FE.getDescricao());
		}
		writer.endNode();// situação
	}
}
