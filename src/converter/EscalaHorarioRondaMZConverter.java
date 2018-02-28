package converter;

import java.util.ArrayList;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import bean.EscalaHorarioRondaMZ;



public class EscalaHorarioRondaMZConverter implements Converter {

	@Override
	public boolean canConvert(Class arg0) {
		return true;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (value.getClass().equals(ArrayList.class)) {
			for (EscalaHorarioRondaMZ escalaHorarioRondaMZ: (ArrayList<EscalaHorarioRondaMZ>) value) {
				writer.startNode("escalaHorarioRondaMZ");
				
				writer.startNode("id");
				writer.setValue(String.valueOf(escalaHorarioRondaMZ.getId()));
				writer.endNode();
				
				writer.startNode("nomesc");
				writer.setValue(String.valueOf(escalaHorarioRondaMZ.getNomesc()));
				writer.endNode();
				
				writer.startNode("horSem");
				writer.setValue(String.valueOf(escalaHorarioRondaMZ.getHorSem()));
				writer.endNode();
				
				writer.startNode("horMes");
				writer.setValue(String.valueOf(escalaHorarioRondaMZ.getHorMes()));
				writer.endNode();
/*
				writer.startNode("classeEscalaRonda.claesc");
				writer.setValue(String.valueOf(escalaHorarioRondaMZ.getClasseEscalaRonda().getClaEsc()));
				writer.endNode();
	*/			
				writer.endNode();
			}
		}
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

}
