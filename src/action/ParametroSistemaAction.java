package action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import dao.ParametroSistemaDAO;

public class ParametroSistemaAction extends ActionSupport implements ServletRequestAware {
	private static final long	serialVersionUID	= 1L;
	private HttpServletRequest	servletRequest;
	@Autowired
	@Qualifier("HAdiantamentoViagemDAO")
	private ParametroSistemaDAO	parametroSistemaDAO;

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;	
		
	}

	public ParametroSistemaDAO getParametroSistemaDAO() {
		return parametroSistemaDAO;
	}

	public void setParametroSistemaDAO(ParametroSistemaDAO parametroSistemaDAO) {
		this.parametroSistemaDAO = parametroSistemaDAO;
	}

}
