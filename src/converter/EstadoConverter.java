package converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bean.Estado;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class EstadoConverter extends AbstractCollectionConverter {

	public EstadoConverter(Mapper mapper) {
		super(mapper);
	}

	@Override
	public boolean canConvert(Class arg0) {
		return true;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map<String, Object> source = (Map<String, Object>) value;
		
		for (Iterator iterator = source.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (String.valueOf(entry.getKey()).equals("items")) {
				if (entry.getValue() != null) {
					List<Estado> estados = (ArrayList<Estado>) entry.getValue();
					if (estados.size() > 0) {
						for (Estado estado : (estados)) {
							writer.startNode("items");
							
							writer.startNode("sigUFS");
							writer.setValue(estado.getSigUFS());
							writer.endNode();
							
							writer.startNode("nomUFS");
							writer.setValue(estado.getNomUFS());
							writer.endNode();
							
							writer.endNode();
						}
					}
				}
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

}
