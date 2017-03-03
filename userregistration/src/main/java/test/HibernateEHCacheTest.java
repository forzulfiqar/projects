package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.journaldev.hibernate.model.Employee;
//import com.journaldev.hibernate.util.HibernateUtil;

import com.userregistration.model.*;

//import net.sf.ehcache.hibernate.HibernateUtil;

public class HibernateEHCacheTest {

	public static void main(String[] args) {

		System.out.println("Temp Dir:" + System.getProperty("java.io.tmpdir"));
		
		ApplicationContext context = null;
		org.hibernate.internal.SessionFactoryImpl sessionFactory = null;

		try {
			if (null == context) {
				context = new ClassPathXmlApplicationContext("applicationContext.xml");
			}
			if (null == sessionFactory) {
				sessionFactory = (org.hibernate.internal.SessionFactoryImpl) context.getBean("hibernate4AnnotatedSessionFactory");
			}
		} catch(Exception e) {
			System.out.println("Exception=" +e);
		}
		
		

		// Initialize Sessions
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		System.out.println("context=" +context);
		System.out.println("sessionFactory=" +sessionFactory);
		System.out.println("sessionFactory.getObject()=" +sessionFactory);
		Statistics stats = sessionFactory.getStatistics();
		System.out.println("Stats enabled=" + stats.isStatisticsEnabled());
		stats.setStatisticsEnabled(true);
		System.out.println("Stats enabled=" + stats.isStatisticsEnabled());

		Session session = sessionFactory.openSession();
		Session otherSession = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Transaction otherTransaction = otherSession.beginTransaction();

		printStats(stats, 0);

		User user = (User) session.load(User.class, 29L);
		printData(user, stats, 1);

		user = (User) session.load(User.class, 29L);
		printData(user, stats, 2);
		// clear first level cache, so that second level cache is used
		session.evict(user);
		user = (User) session.load(User.class, 29L);
		printData(user, stats, 3);

		user = (User) session.load(User.class, 29L);
		printData(user, stats, 4);

		user = (User) otherSession.load(User.class, 29L);
		printData(user, stats, 5);

		// Release resources
		transaction.commit();
		otherTransaction.commit();
		sessionFactory.close();
	}
		

	private static void printStats(Statistics stats, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count=" + stats.getEntityFetchCount());
		System.out.println("Second Level Hit Count=" + stats.getSecondLevelCacheHitCount());
		System.out.println("Second Level Miss Count=" + stats.getSecondLevelCacheMissCount());
		System.out.println("Second Level Put Count=" + stats.getSecondLevelCachePutCount());
	}

	private static void printData(User user, Statistics stats, int count) {
		System.out.println(count + ":: User Name=" + user.getUserName());
		printStats(stats, count);
	}

}