package ir.hibernate;

import ir.database.DocumentTable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;



/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

  public static final SessionFactory sessionFactory;


  static {
      try {
    	  Configuration config = new Configuration();
    	  sessionFactory = config.configure()
          .addPackage("ir.analyzer.model") //the fully qualified package name
          .addAnnotatedClass(DocumentTable.class)
          //.addAnnotatedClass(UserTable.class)
          //.addAnnotatedClass(TagTable.class)
          //.addAnnotatedClass(UserTagDocTable.class)
          //.addAnnotatedClass(Term.class)
          .buildSessionFactory();
    	  
    	  new SchemaExport(config).create(true, true); 

      } catch (Throwable ex) {
          // Log exception!
          throw new ExceptionInInitializerError(ex);
      }
  }

  public static Session getSession()
          throws HibernateException {
      return sessionFactory.openSession();

  }

}


