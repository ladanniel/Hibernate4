package Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *Hibernate工具类
 * @author Administrator
 *
 */
public class HibernateUtils {
	// 标准严谨写法
  private static final Configuration configuration ;
  private static final SessionFactory sessionFactory;
	static{
		//加载核心配置文件hibernate.cfg.xml
		 configuration = new Configuration().configure("hibernate.cfg.xml");
		 sessionFactory = configuration.buildSessionFactory();
	}
	/**
	 * 提供返回sessionFactory方法 把Configuration和SessionFactory提取成全局对象，才能返回
	 * @return
	 */
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
		
	}
	/**
	 * 提供返回本地线程绑定的session的方法
	 * @return
	 */
	public static Session getSessionObject(){
		return sessionFactory.getCurrentSession();
	}
}
