package interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import util.WebOperConstantes;
import action.LoginAction;
import bean.Usuario;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {
	private static final long	serialVersionUID	= 1L;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = (Action) invocation.getAction();

		//criando um login
		if (action instanceof LoginAction) {		
			return invocation.invoke();
		} else {
			
			@SuppressWarnings("rawtypes")
			Map mapaSessao = invocation.getInvocationContext().getSession();
			Usuario usuario = (Usuario) mapaSessao.get(WebOperConstantes.USUARIO);
			//HttpServletRequest req =  (HttpServletRequest) mapaSessao.get(StrutsStatics.HTTP_REQUEST);
			//req.setAttribute("ultimoRequest", System.currentTimeMillis());
			
			if (usuario == null) {//n√£o tem usu√°rio logdo
				if(action instanceof ActionSupport)
						((ActionSupport) action).addActionError("Sua sess„o expirou por inatividade ou vocÍ n„o est· logado. Efetue logon.");
				
				return Action.LOGIN;
			} else {//ja tem usu√°rio logado
				if (action instanceof LoginAction) {
					((LoginAction) action).setCodUsu(usuario.getCodUsu());
					((LoginAction) action).setSenUsu(usuario.getSenUsu());
				}
				return invocation.invoke();
			}
		}

	}

}
