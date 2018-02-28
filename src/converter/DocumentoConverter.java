package converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bean.Documento;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class DocumentoConverter extends AbstractCollectionConverter {

	public DocumentoConverter(Mapper mapper) {
		super(mapper);
	}

	public boolean canConvert(Class type) {
		return type.equals(HashMap.class) || type.equals(Hashtable.class)
				|| type.getName().equals("java.util.LinkedHashMap")
				|| type.getName().equals("sun.font.AttributeMap");
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map<String, Object> source = (Map<String, Object>) value;
		for (Iterator iterator = source.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (String.valueOf(entry.getKey()).equals("items")) {
				if (entry.getValue() != null) {
					List<Documento> documentos = (ArrayList<Documento>) entry.getValue();
					if (documentos.size() > 0) {
						for (Documento documento : (documentos)) {
							writer.startNode("key");
							writer.setValue(String.valueOf(documento.getId()));
							writer.endNode();

							writer.startNode("title");
							writer.setValue(String.valueOf(documento.getNomDoc()));
							writer.endNode();
							
							if(!documento.getDocFilhos().isEmpty()){
								
								writer.startNode("folder");
								writer.setValue("true");
								writer.endNode();
								
								writer.startNode("lazy");
								writer.setValue("true");
								writer.endNode();
							}
						}
					}
				}
			}
		}
	}

	@Override
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
