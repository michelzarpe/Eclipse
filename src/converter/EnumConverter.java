package converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class EnumConverter implements Converter {

	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
		Class fieldType = obj.getClass().getComponentType();
		if (fieldType.isEnum()) {
			for (Object enumConstant : fieldType.getEnumConstants()) {
				try {
					String idEnum = (String) enumConstant.getClass().getMethod("name").invoke(enumConstant);
					String descriptionValue = (String) enumConstant.getClass().getMethod("getDescricao")
							.invoke(enumConstant);
					writer.startNode("item");
					writer.startNode("id");
					writer.setValue(String.valueOf(idEnum));
					writer.endNode();
					writer.startNode("name");
					writer.setValue(descriptionValue);
					writer.endNode();
					writer.endNode();
				} catch (Exception e) {
					e.printStackTrace();
				}
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
