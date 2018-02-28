package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import dao.hibernate.HHorarioRondaDAO;
import util.HibernateUtil;

public class HibernetClassP {

	@Autowired 
	@Qualifier("HHorarioRondaDAO")
	static HHorarioRondaDAO dao;
	
	public static void main(String args[]) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		
		dao = new HHorarioRondaDAO(sf);
		dao.retHorRonda(21);
		s.close();

	}
}
