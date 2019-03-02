package Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *Hibernate������
 * @author Administrator
 *
 */
public class HibernateUtils {
	// ��׼�Ͻ�д��
  private static final Configuration configuration ;
  private static final SessionFactory sessionFactory;
	static{
		//���غ��������ļ�hibernate.cfg.xml
		 configuration = new Configuration().configure("hibernate.cfg.xml");
		 sessionFactory = configuration.buildSessionFactory();
	}
	/**
	 * �ṩ����sessionFactory���� ��Configuration��SessionFactory��ȡ��ȫ�ֶ��󣬲��ܷ���
	 * @return
	 */
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
		
	}
	/**
	 * �ṩ���ر����̰߳󶨵�session�ķ���
	 * @return
	 */
	public static Session getSessionObject(){
		return sessionFactory.getCurrentSession();
	}
}
