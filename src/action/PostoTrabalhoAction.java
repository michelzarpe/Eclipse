package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import bean.PostoTrabalho;
import dao.PostoTrabalhoDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class PostoTrabalhoAction	 extends ActionSupport {
			private static final long serialVersionUID = 1L;
			private Object					JSONModel;
			private Object					xmlModel;
			private String					totalCount;
			
			@Autowired
			@Qualifier("HPostoTrabalhoDAO")
			private PostoTrabalhoDAO postoTrabalhoDAO;

			public Object getJSONModel() {
				return JSONModel;
			}

			public void setJSONModel(Object jSONModel) {
				JSONModel = jSONModel;
			}

			public Object getXmlModel() {
				return xmlModel;
			}

			public void setXmlModel(Object xmlModel) {
				this.xmlModel = xmlModel;
			}

			public String getTotalCount() {
				return totalCount;
			}

			public void setTotalCount(String totalCount) {
				this.totalCount = totalCount;
			}

			public PostoTrabalhoDAO getPostoTrabalhoDAO() {
				return postoTrabalhoDAO;
			}

			public void setPostoTrabalhoDAO(PostoTrabalhoDAO postoTrabalhoDAO) {
				this.postoTrabalhoDAO = postoTrabalhoDAO;
			}
			
			public String listAll() throws Exception {
				setJSONModel(postoTrabalhoDAO.listAll("idPosTrab"));
				return SUCCESS;
			}

			public String listAllXML() throws Exception {
				List<PostoTrabalho> postoTrabalho= postoTrabalhoDAO.listAll("idPosTrab");
				setXmlModel(postoTrabalho);
				this.totalCount = String.valueOf(postoTrabalho.size());
				return "resultXML";
			}
			
			
			
			
}
