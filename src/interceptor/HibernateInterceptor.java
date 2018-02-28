package interceptor;

import java.io.ByteArrayInputStream;


import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.hibernate.StaleObjectStateException;
import org.hibernate.TransactionException;

import util.HibernateUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class HibernateInterceptor implements Interceptor {

	private static final long	serialVersionUID	= 1L;
	static Logger					logger				= Logger.getLogger(HibernateInterceptor.class);

	@Override
	public void destroy() {

	}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		logger.info("Transação iniciada!");
		
		
		String result = actionInvocation.invoke();
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			logger.info("Transação commit!");
		} catch (StaleObjectStateException e) {
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			logger.info("Transação rollback!");
			InputStream inputStream = (InputStream) actionInvocation.getStack().findValue(
					"inputStream");
			inputStream = new ByteArrayInputStream(("{map:{items:[], success:false, totalCount : 0, msg: 'Erro ao tentar retornar itens gravados. Informe a administração do sistema.'}}")
							.getBytes());
			actionInvocation.getStack().setValue("inputStream", inputStream);
			result = "txtRes";
			throw e;
		} catch (TransactionException e) {
			System.err.println("Ja houve um commit realizado para esta transacao.");
		} catch (Throwable e) {
			e.printStackTrace();
			try {
				if (HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().isActive()) {
					HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
					logger.info("Transação rollback!");
				}
			} catch (Throwable ex) {
				System.err.println("Não foi possível efetuar rollback depois da exceção");
			}
			throw new ServletException(e);
		}
		return result;
	}

}
