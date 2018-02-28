package converter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.util.StrutsTypeConverter;

import bean.Centro;

public class CollectionConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Set<Centro> centros = new HashSet<Centro>();
		for (int i = 0; i < values.length; i++) {
			Centro centro = new Centro(Integer.parseInt(values[i]));
			centros.add(centro);
		}
		return centros;
	}

	@Override
	public String convertToString(Map context, Object o) {
		return null;
	}

}
