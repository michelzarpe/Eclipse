package converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class EnumTypeConverter extends StrutsTypeConverter {

	@Override
	// DE STRING PARA ENUM
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Object retorno = null;
		for (int i = 0; i < values.length; i++) {
			Enum e = Enum.valueOf(toClass, values[i]);
			retorno = e;
		}
		return retorno;
	}

	@Override
	// DE ENUM PARA STRING
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

}