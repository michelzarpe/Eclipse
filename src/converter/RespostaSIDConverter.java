package converter;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class RespostaSIDConverter implements Converter {

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map<String, String> valores = new HashMap<String, String>();
		int i = 1;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("retorno")) {
				valores.put(String.valueOf(i), reader.getValue());
				i++;
			} else {
				valores.put(reader.getNodeName(), reader.getValue());
			}
			reader.moveUp();
		}

		return valores;
	}

	@Override
	public boolean canConvert(Class type) {
		return true;
	}

}
