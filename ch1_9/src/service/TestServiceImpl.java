package service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import dao.TestDao;
import entity.MyUser;
@Service
@Transactional
//@Transactional( rollbackFor= {Exception.class} )
public class TestServiceImpl implements TestService{
	@Autowired
	public TestDao testDao;
	@Override
	public void testJDBC() {
		String insertSql = "insert into user values(null,?,?)";
		//数组param的值与insertSql语句中?一一对应
		Object param1[] = {"chenheng1", "男"};
		Object param2[] = {"chenheng2", "女"};
		Object param3[] = {"chenheng3", "男"};
		Object param4[] = {"chenheng4", "女"};
		String insertSql1 = "insert into user values(?,?,?)";
		Object param5[] = {1,"chenheng5", "女"};
		Object param6[] = {1,"chenheng6", "女"};
		//try {
		//添加用户
		testDao.update(insertSql, param1);
		testDao.update(insertSql, param2);
		testDao.update(insertSql, param3);
		testDao.update(insertSql, param4);
		//添加两个ID相同的用户，出现唯一性约束异常，使事物回滚。
		testDao.update(insertSql1, param5);
		testDao.update(insertSql1, param6);
		//查询用户
		String selectSql ="select * from user";
		List<MyUser> list = testDao.query(selectSql, null);
		for(MyUser mu : list) {
			System.out.println(mu);
		};
	/*}catch(Exception e) {
			System.out.println("主键重复，事务回滚。");
			throw new RuntimeException(); //主动抛异常引发回滚,控制台显示异常信息
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //回滚，控制台不显示异常信息
		} */
	} 
}
