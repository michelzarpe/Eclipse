package util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	static Logger logger = Logger.getLogger(HibernateUtil.class);

	static {
		try {
			// Cria o SessionFactory a partir de hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
			logger.info("F�brica de sess�es criada");
		} catch (Exception e) {
			System.err.println("F�brica de sess�es n�o pode ser criada. " + e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}
