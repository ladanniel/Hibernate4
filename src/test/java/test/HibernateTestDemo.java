package test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import Utils.HibernateUtils;

import com.itcast.entity.User;

public class HibernateTestDemo {
	/**
	 * ���԰󶨱����߳�session
	 */
	@Test
	public void testBingingNativeThreadSession(){
		Session session = null;
		Transaction trans = null;
		try {
			 session = HibernateUtils.getSessionObject();
			 trans = session.beginTransaction();
			 User user = new User();
			 user.setUid(1);
			 user.setUsername("С��");
			 user.setPassword("123");
			 user.setAddress("������");
			 session.save(user);
			 //ģ���쳣
			/* int i = 10 / 0 ;*/
			 
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		}finally{
			session.close();
			/*sessionFactory.close();����Ҫ�رգ���Ϊд�ھ�̬����*/
		}
	}
	//Hibernate����(Affair)�淶д��
	@Test
	public void testHibernateAffair(){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction trans = null;
		try {
			 sessionFactory = HibernateUtils.getSessionFactory();
			 session = sessionFactory.openSession();
			 trans = session.beginTransaction();
			 User user = new User();
			 user.setUid(1);
			 user.setUsername("С��");
			 user.setPassword("123");
			 user.setAddress("������");
			 session.save(user);
			 //ģ���쳣
			 int i = 10 / 0 ;
			 
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		}finally{
			session.close();
			sessionFactory.close();
		}
	}
	@Test//����һ�����������
	public void testFistCacheCharacter(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		User user = session.get(User.class, 3);//�־�̬������Ҫ����update��save��saveOrUpdate�ȷ���Ҳ��ʵ���޸�
		user.setUsername("jack");
		//session.update(user);����update����Ҳ�ܸ������ݿ� 
		trans.commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testHibernateFirstCache(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		//ִ�е�һ��get�������Ƿ��ѯ���ݿ⣬�Ƿ���SQL���
		User user = session.get(User.class, 2);
		System.out.println("��һ�β���"+user);
		//ִ�еڶ���get�������Ƿ��ѯ���ݿ⣬�Ƿ���SQL���
		User user2 = session.get(User.class, 2);
		System.out.println("�ڶ��β���Hibernate��cache"+user2);
		trans.commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testSaveOrUpdate(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		/*User user = session.get(User.class, 2);*/
		//˲ʱ̬��ʱ�������Ǳ������
		User user = new User();
		user.setUsername("������");
		user.setAddress("�ձ��㵺");
		System.out.println("��ѯ���"+user);
		//�й�̬��ʱ���������޸Ĳ���
		/*User user = new User();
		user.setUid(2);
		user.setUsername("rose");
		user.setAddress("��������");*/
		session.saveOrUpdate(user);
		trans.commit();
		session.close();
		sessionFactory.close();
		System.out.println("ִ�����");
	}
	//ɾ������
	@Test
	public void testDelete(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		//��һ�֣��������Ȳ�ѯ��ɾ�� �޸ķ���Ҳ�������
		User user = session.get(User.class, 1);
		System.out.println("��������"+user);
		session.delete(user);
	/*	�ڶ��֣���ѯ����Ҳһ��
	 * User user1 = new User();
		user1.setUid(2);
		session.delete(user1);*/
		trans.commit();
		session.close();
		sessionFactory.close();
	}
	//��ѯ����
	@Test
	public void testGet(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		User user = session.get(User.class, 1);
		System.out.println("��������"+user);
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testAdd(){
		//�ڶ���;���ù����෽��
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		//��������ʹ��sessionFactory����session���� ����jdbc����
		Session session = sessionFactory.openSession();
		//���Ĳ�����������
		Transaction tx = session.beginTransaction();
		//���岽��д�����߼�crud����
		User user = new User();
		user.setUsername("����");
		user.setPassword("1234567");
		user.setAddress("�쾩��");
		//����session�����ύ
		session.save(user);
		tx.commit();
		session.close();
		sessionFactory.close();
	}

}
