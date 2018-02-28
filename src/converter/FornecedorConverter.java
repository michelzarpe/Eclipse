package converter;

import java.util.ArrayList;

import bean.Fornecedor;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FornecedorConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (value.getClass().equals(ArrayList.class)) {
			for (Fornecedor fornecedor : (ArrayList<Fornecedor>) value) {
				writer.startNode("fornecedor");

				writer.addAttribute("nomFor", fornecedor.getNomFor());
				writer.addAttribute("apeFor", fornecedor.getApeFor());
				writer.addAttribute("endFor", fornecedor.getEndFor());
				if (fornecedor.getCidFor() != null)
					writer.addAttribute("cidFor", fornecedor.getCidFor());
				else
					writer.addAttribute("cidFor", "");
				if (fornecedor.getCepFor() != null)
					writer.addAttribute("cepFor", fornecedor.getCepFor());
				else
					writer.addAttribute("cepFor", "");
				if (fornecedor.getFonFor() != null)
					writer.addAttribute("fonFor", fornecedor.getFonFor());
				else
					writer.addAttribute("fonFor", "");
				if (fornecedor.getFaxFor() != null)
					writer.addAttribute("faxFor", fornecedor.getFaxFor());
				else
					writer.addAttribute("faxFor", "");
				if (fornecedor.getIntNet() != null)
					writer.addAttribute("intNet", fornecedor.getIntNet());
				else
					writer.addAttribute("intNet", "");
				writer.addAttribute("id", String.valueOf(fornecedor.getId()));
				writer.endNode();
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

	@Override
	public boolean canConvert(Class type) {
		return true;
	}

}
