package util;

public class CriaBanco {
  public static void main(String[] args) {
    HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
  }
}
