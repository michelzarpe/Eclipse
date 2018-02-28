package converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import bean.Dependente;

public class DependenteConverter extends AbstractCollectionConverter  {
	public DependenteConverter(Mapper mapper) {
		super(mapper);
	}

	@Override
	public boolean canConvert(Class type) {
	     if (type != null) { 
	   	   return type.equals(type); 
	      } 
	   	return type.equals(ArrayList.class) 
	   	               || type.equals(HashSet.class) 
	   	               || type.equals(LinkedList.class) 
	   	               || type.getName().equals("java.util.LinkedHashSet"); 
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, Object> source = (Map<String, Object>) value;
		
		for (Iterator iterator = source.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (String.valueOf(entry.getKey()).equals("items")) {
				if (entry.getValue() != null) {
					List<Dependente> dependentes = (ArrayList<Dependente>) entry.getValue();
				   if(dependentes.size()>0){
				   	for (Dependente iterable_element: (dependentes)){
				   		writer.startNode("items");
				   		writer.startNode("codDep");
							writer.setValue(String.valueOf(iterable_element.getCodDep()));
							writer.endNode();
							
							writer.startNode("nomDep");
							writer.setValue(iterable_element.getNomDep());
							writer.endNode();
							
							writer.startNode("NomMae");
							writer.setValue(iterable_element.getNomMae());
							writer.endNode();
							
							writer.startNode("numCpf");
							writer.setValue(iterable_element.getNumCpf());
							writer.endNode();
							
							writer.startNode("datNas");
							writer.setValue(formato.format(iterable_element.getDatNas()));
							writer.endNode();
							
							writer.startNode("datInv");
							writer.setValue(formato.format(iterable_element.getDatInv()));
							writer.endNode();
							
							writer.startNode("iniTut");
							writer.setValue(formato.format(iterable_element.getIniTut()));
							writer.endNode();
							
							writer.startNode("datTut");
							writer.setValue(formato.format(iterable_element.getDatTut()));
							writer.endNode();
							
							writer.startNode("aviImp");
							writer.setValue(iterable_element.getAviImp().getId());
							writer.endNode();
							
							writer.startNode("auxCre");
							writer.setValue(iterable_element.getAuxCre().getId());
							writer.endNode();
							
							writer.startNode("penJud");
							writer.setValue(iterable_element.getPenJud().getId());
							writer.endNode();
							
							writer.startNode("sexo");
							writer.setValue(iterable_element.getSexo().name());
							writer.endNode();
							
							writer.startNode("estCiv");
							writer.setValue(iterable_element.getEstCiv().name());
							writer.endNode();
							
							writer.startNode("tipoDependenteESocial");
							writer.setValue(iterable_element.getTipoDependenteESocial().name());
							writer.endNode();
							
							writer.startNode("grauParantesco");
							writer.setValue(iterable_element.getGrauParantesco().name());
							writer.endNode();
							
							writer.startNode("limIrf");
							writer.setValue(String.valueOf(iterable_element.getLimIrf()));
							writer.endNode();
							
							writer.startNode("limSaf");
							writer.setValue(String.valueOf(iterable_element.getLimSaf()));
							writer.endNode();
							
							writer.startNode("nomCre");
							writer.setValue(String.valueOf(iterable_element.getNomCre()));
							writer.endNode();
							
							writer.startNode("grauInstrucao");
								writer.startNode("id");
									writer.setValue(String.valueOf(iterable_element.getGrauInstrucao().getId()));
								writer.endNode();
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
