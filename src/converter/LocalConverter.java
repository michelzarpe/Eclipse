package converter;

import java.util.Collection;

import bean.Hierarquia;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LocalConverter implements Converter {
	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		for (Hierarquia hierarquia : (Collection<Hierarquia>) value) {
			writer.startNode("items");

			writer.startNode("id");
			writer.setValue(String.valueOf(hierarquia.getCodLoc()));
			writer.endNode();

			writer.startNode("name");
			writer.setValue(hierarquia.getLocal().getNomLoc());
			writer.endNode();

			writer.startNode("empresa");
			writer.setValue(hierarquia.getCodLoc().substring(0, 2));
			writer.endNode();
			
			writer.startNode("unidade");
			if (hierarquia.getCodLoc().length() >= 6) {
				writer.startNode("codLoc");
				writer.setValue(hierarquia.getCodLoc().substring(0, 6));
				writer.endNode();
			}
			writer.endNode();

			writer.startNode("cliente");
			if (hierarquia.getCodLoc().length() >= 11) {
				writer.startNode("codLoc");
				writer.setValue(hierarquia.getCodLoc().substring(0, 11));
				writer.endNode();
			}
			writer.endNode();

			writer.startNode("cidade");
			if (hierarquia.getCodLoc().length() >= 16) {
				writer.startNode("codLoc");
				writer.setValue(hierarquia.getCodLoc().substring(0, 16));
				writer.endNode();
			}
			writer.endNode();

			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	@Override
	public boolean canConvert(Class clazz) {
		return true;
	}

}
