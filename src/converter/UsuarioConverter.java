package converter;

import java.util.ArrayList;

import bean.Centro;
import bean.Usuario;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UsuarioConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext arg2) {
		if (value.getClass().equals(ArrayList.class)) {
			for (Usuario usuario : (ArrayList<Usuario>) value) {
				writer.startNode("usuario");
				writer.addAttribute("nomFun", usuario.getNomFun());
				writer.addAttribute("email", usuario.getEmail());
				writer.addAttribute("codUsu", usuario.getCodUsu());
				writer.addAttribute("recebeAviso", String.valueOf(usuario.isRecebeAviso()));
				writer.addAttribute("senUsu", usuario.getSenUsu());

				writer.startNode("usuarioId");
				writer.setValue(String.valueOf(usuario.getId()));
				writer.endNode();

				writer.startNode("tipUsu");
				writer.addAttribute("descricao", usuario.getTipUsu().getDescricao());
				writer.addAttribute("id", usuario.getTipUsu().name());
				writer.endNode();

				writer.startNode("afastamento");
				writer.startNode("id");
				writer.setValue(String.valueOf(usuario.getAfastamento().getId()));
				writer.endNode();//id
				writer.startNode("desSit");
				writer.setValue(usuario.getAfastamento().getDesSit());
				writer.endNode();//desSit
				writer.endNode();//afastamento

				writer.startNode("gerente");
				if (usuario.getGerente() != null) {
					writer.addAttribute("id", String.valueOf(usuario.getGerente().getId()));
					writer.addAttribute("nomGer", usuario.getGerente().getNomFun());
				}
				writer.endNode();//gerente

				writer.startNode("centros");
				for (Centro centro : usuario.getCentrosPermitidos()) {
					writer.startNode("centro");
					writer.addAttribute("id", String.valueOf(centro.getId()));
					writer.addAttribute("nomCcu", centro.getNomCcu());
					writer.endNode();
				}
				writer.endNode();

				writer.endNode();
			}
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
