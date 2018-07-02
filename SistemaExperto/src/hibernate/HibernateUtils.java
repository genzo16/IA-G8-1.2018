package hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Analisis;
import model.Antecedentes;
import model.Diagnostico;
import model.Dolor;
import model.Paciente;

public class HibernateUtils {

    private HibernateUtils() {}

    private static final SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Analisis.class);
            config.addAnnotatedClass(Antecedentes.class);
            config.addAnnotatedClass(Dolor.class);
            config.addAnnotatedClass(Diagnostico.class);
            config.addAnnotatedClass(Paciente.class);
            
            sessionFactory = config.buildSessionFactory();
            session = HibernateUtils.getSessionFactory().openSession();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    
   public static void shutdown() {
	 //   session.close();
        getSessionFactory().close();
    }    
   
   
   public static List<Paciente> listPacientes()
   {
	      Transaction tx = null;
	      List<Paciente> pacientes = new ArrayList<Paciente>();
	      try {
	    	  session = HibernateUtils.getSessionFactory().openSession();
	         tx = session.beginTransaction();
	         for(Object o :session.createQuery("FROM Paciente").list())
	        	 pacientes.add((Paciente)o);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return pacientes;
	}
}