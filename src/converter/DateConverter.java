package converter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/*http://x-stream.github.io/converter-tutorial.html*/
public class DateConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Date date = (Date) value;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		writer.setValue(String.valueOf(sdf.format(date)));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(Date.class) ;
	}
	
}
