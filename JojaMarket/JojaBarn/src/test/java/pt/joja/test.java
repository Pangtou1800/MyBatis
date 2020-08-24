package pt.joja;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import pt.joja.bean.Employee;
import pt.joja.dao.EmployeeDao;

import java.io.IOException;
import java.io.InputStream;

public class test {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

        System.out.println(employeeDao.getEmpById(1));
        sqlSession.close();
    }

    @Test
    public void test02() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(true);
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

            System.out.println(employeeDao.getClass());

            Employee employee = new Employee();
            employee.setEmpName("Laura");
            employee.setGender(1);
            employee.setEmail("laura@joja.com");
            System.out.println(employeeDao.insertEmployee(employee));
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
            sqlSession = null;
        }
    }


}
