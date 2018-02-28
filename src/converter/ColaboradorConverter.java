package converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bean.Colaborador;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ColaboradorConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext arg2) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		if (value.getClass().equals(ArrayList.class)) {
			for (Colaborador colaborador : (ArrayList<Colaborador>) value) {
				writer.startNode("colaborador");
				writer.addAttribute("nomFun", colaborador.getNomFun());

				writer.startNode("colaboradorId");
				writer.setValue(String.valueOf(colaborador.getId()));
				writer.endNode();

				writer.startNode("sexo");
				writer.setValue(String.valueOf(colaborador.getSexo().name()));
				writer.endNode();
				
				writer.startNode("numCad");
				writer.setValue(String.valueOf(colaborador.getNumCad()));
				writer.endNode();

				writer.startNode("empresa");
				writer.startNode("id");
				writer.setValue(String.valueOf(colaborador.getEmpresa().getId()));
				writer.endNode();
				writer.startNode("name");
				writer.setValue(colaborador.getEmpresa().getNomEmp());
				writer.endNode();
				writer.endNode();

				writer.startNode("centro");
				writer.startNode("nomCcu");
				if(colaborador.getCentro()!= null)
				   writer.setValue(colaborador.getCentro().getNomCcu());
				else
					writer.setValue("");
				writer.endNode();
				writer.endNode();

				writer.startNode("sitAdm");
				if (colaborador.getSitAdm() != null)
					writer.setValue(colaborador.getSitAdm());
				else
					writer.setValue("");
				writer.endNode();

				writer.startNode("datAdm");
				writer.setValue(formato.format(colaborador.getDatAdm()));
				writer.endNode();

				writer.startNode("cargo");
				
				writer.startNode("name");
				if (colaborador.getCargo() != null)
					writer.setValue(colaborador.getCargo().getTitCar());
				else
					writer.setValue("");
				writer.endNode();
				
				writer.startNode("codigo");
				writer.setValue(colaborador.getCargo().getCodCar());
				writer.endNode();
				
				writer.endNode();

				writer.startNode("afastamento");
				
				writer.startNode("id");
				writer.setValue(String.valueOf(colaborador.getAfastamento().getId()));
				writer.endNode();
				writer.startNode("desSit");
				writer.setValue(colaborador.getAfastamento().getDesSit());
				writer.endNode();
				
				writer.endNode();//afastamento
				
/*				
            writer.startNode("escalaRonda");
					writer.startNode("codEsc");
						writer.setValue(String.valueOf(colaborador.getEscala().getId()));
					writer.endNode();
				writer.endNode();
				
	*/			
				
				if(colaborador.getUsuCad()!=null){
					writer.startNode("usuCad");
					writer.startNode("codUsu");
					writer.setValue(colaborador.getUsuCad().getCodUsu());
					writer.endNode();
					writer.endNode();
				}
				
				writer.endNode();//colaborador
			}
		}

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	@Override
	public boolean canConvert(Class type) {
		return true;
	}

}
