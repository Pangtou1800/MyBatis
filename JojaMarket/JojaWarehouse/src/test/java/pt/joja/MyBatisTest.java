package pt.joja;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pt.joja.dao.EmployeeDao;
import pt.joja.dao.EmployeeDaoAnno;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {

    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);

        System.out.println(employeeDao.getEmpById(1));
    }

    @Test
    public void test2() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDaoAnno employeeDao = session.getMapper(EmployeeDaoAnno.class);

        System.out.println(employeeDao.getEmpById(1));
    }
}
