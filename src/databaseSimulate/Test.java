package databaseSimulate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {

	  public static void loadall()  
	    {  
	        Query q = session.createSQLQuery("select c.id,c.med_spec from formularytest as c where c.id=1 ");  
	        
	        List l = q.list();  
	        for(int i=0;i<l.size();i++)  
	        {  
	            //FormularyTest ft = (FormularyTest)l.get(i);  
	            //System.out.println(ft.getUsername());   
	        	
	              Object[] row = (Object[])l.get(i);;  
	              int id = (Integer)row[0];  
	              String med_spec = (String)row[1];    
	              System.out.println(id+" "+med_spec);  
	        }  
	    }  
	      
	    //读取  
	    public static void load()  
	    {  
	        FormularyTest obj = (FormularyTest) session.load(FormularyTest.class, new Integer(2));  
	        System.out.println(obj.getMed_spec());  
	    }  
	      
	    //更新  
	    public static void update()  
	    {  
	    	FormularyTest obj = (FormularyTest) session.load(FormularyTest.class, new Integer(2));  
	        obj.setMed_spec("Abccc");  
	    }  
	      
	    //插入  
	    public static void insert()  
	    {  
	    	FormularyTest ft = new FormularyTest();  
	        ft.setMed_spec("shit");
	  
	        session.save(ft);  
	    }  
	      
	    static SessionFactory sessionFactory;  
	    static Session session ;  
	    static Transaction tx ;  
	      
	    private static void init()  
	    {  
	        sessionFactory = new Configuration().configure().buildSessionFactory();  
	        session = sessionFactory.openSession();  
	        tx = session.beginTransaction();  
	    }  
	      
	    private static void close()  
	    {  
	        tx.commit();  
	        session.close();  
	        sessionFactory.close();  
	    } 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();  
		//insert(); 
		loadall();
        close();  
	}

}
