package pt.joja;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pt.joja.bean.Cat;
import pt.joja.bean.Employee;
import pt.joja.dao.CatDao;
import pt.joja.dao.EmployeeDao;
import pt.joja.dao.EmployeeDaoAnno;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

    SqlSessionFactory sqlSessionFactory;

    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() {
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);
        System.out.println(employeeDao.getEmpById(1));
        session.close();
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

    @Test
    public void test3() {
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);

        Employee employee = new Employee();
        employee.setEmpName("Lucy");
        employee.setGender(1);
        employee.setEmail("lucy@joja.com");
        employee.setLoginAccount("lucy01");
        int i = employeeDao.insertEmployee(employee);

        System.out.println(employee);

        session.commit();
        session.close();
    }

    @Test
    public void test4() {
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);

        Employee employee = new Employee();
        employee.setEmpName("Lily");
        employee.setGender(1);
        employee.setEmail("lily@joja.com");
        employee.setLoginAccount("lilyAndLily");
        int i = employeeDao.insertEmployee2(employee);

        System.out.println(employee);

        session.commit();
        session.close();
    }

    @Test
    public void test5() {
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);

        Employee employee = employeeDao.getEmpByIdAndName(1, "admin");
        System.out.println(employee);

        session.close();
    }

    @Test
    public void test6() {
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);

        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("empName", "admin");
        map.put("tableName", "employee");
        Employee employee = employeeDao.getEmpByIdAndName2(map);

        System.out.println(employee);

        session.close();
    }

    @Test
    public void test7() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

        List<Employee> employeeList = employeeDao.getAllEmps();
        System.out.println(employeeList);

        sqlSession.close();
    }

    @Test
    public void test8() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

        Map<String, Object> map = employeeDao.getEmpByIdToMap(7);
        System.out.println(map);

        sqlSession.close();
    }

    @Test
    public void test9() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

        Map<String, Employee> allEmpsToMap = employeeDao.getAllEmpsToMap();
        System.out.println(allEmpsToMap);

        sqlSession.close();
    }

    @Test
    public void test10() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CatDao catDao = sqlSession.getMapper(CatDao.class);

        Cat cat = catDao.getCatById(2);
        System.out.println(cat);

        sqlSession.close();
    }

}
