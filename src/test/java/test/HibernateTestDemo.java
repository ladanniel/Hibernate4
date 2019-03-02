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
	 * 测试绑定本地线程session
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
			 user.setUsername("小马");
			 user.setPassword("123");
			 user.setAddress("北京市");
			 session.save(user);
			 //模拟异常
			/* int i = 10 / 0 ;*/
			 
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		}finally{
			session.close();
			/*sessionFactory.close();不需要关闭，因为写在静态快中*/
		}
	}
	//Hibernate事务(Affair)规范写法
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
			 user.setUsername("小马");
			 user.setPassword("123");
			 user.setAddress("北京市");
			 session.save(user);
			 //模拟异常
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
	@Test//测试一级缓存的特性
	public void testFistCacheCharacter(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		User user = session.get(User.class, 3);//持久态：不需要调用update、save、saveOrUpdate等方法也能实现修改
		user.setUsername("jack");
		//session.update(user);不用update方法也能更新数据库 
		trans.commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testHibernateFirstCache(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		//执行第一个get方法，是否查询数据库，是否发送SQL语句
		User user = session.get(User.class, 2);
		System.out.println("第一次测试"+user);
		//执行第二个get方法，是否查询数据库，是否发送SQL语句
		User user2 = session.get(User.class, 2);
		System.out.println("第二次测试Hibernate的cache"+user2);
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
		//瞬时态的时候做的是保存操作
		User user = new User();
		user.setUsername("张三风");
		user.setAddress("日本广岛");
		System.out.println("查询结果"+user);
		//托管态的时候做的是修改操作
		/*User user = new User();
		user.setUid(2);
		user.setUsername("rose");
		user.setAddress("法国巴黎");*/
		session.saveOrUpdate(user);
		trans.commit();
		session.close();
		sessionFactory.close();
		System.out.println("执行完毕");
	}
	//删除操作
	@Test
	public void testDelete(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		//第一种：建议用先查询后删除 修改方法也建议如此
		User user = session.get(User.class, 1);
		System.out.println("输出结果："+user);
		session.delete(user);
	/*	第二种，查询方法也一样
	 * User user1 = new User();
		user1.setUid(2);
		session.delete(user1);*/
		trans.commit();
		session.close();
		sessionFactory.close();
	}
	//查询操作
	@Test
	public void testGet(){
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		User user = session.get(User.class, 1);
		System.out.println("输出结果："+user);
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testAdd(){
		//第二步;调用工具类方法
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		//第三步：使用sessionFactory创建session对象 类似jdbc连接
		Session session = sessionFactory.openSession();
		//第四步：开启事务
		Transaction tx = session.beginTransaction();
		//第五步：写具体逻辑crud操作
		User user = new User();
		user.setUsername("周三");
		user.setPassword("1234567");
		user.setAddress("天京市");
		//调用session方法提交
		session.save(user);
		tx.commit();
		session.close();
		sessionFactory.close();
	}

}
