package pt.joja;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pt.joja.bean.*;
import pt.joja.dao.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
    public void test19() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();
        teacher.setId(1);
        //teacher.setTeacherName("Laura");
        teacher.setClassName("毛概");
        teacherDao.updateTeacher(teacher);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test18() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();
        //teacher.setId(0);
        teacher.setTeacherName("%L%");
        teacher.setBirthday(new Date());
        List<Teacher> teacher2 = teacherDao.getTeacherByCond2(teacher);
        System.out.println(teacher2);

        sqlSession.close();
    }

    @Test
    public void test17() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);

        List<Integer> idList = null;
        idList = Arrays.asList(1, 2);
        List<Teacher> teacher2 = teacherDao.getTeacherByIdList(idList);
        System.out.println(teacher2);

        sqlSession.close();
    }

    @Test
    public void test16() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();
        //teacher.setId(0);
        teacher.setTeacherName("%L%");
        teacher.setBirthday(new Date());
        List<Teacher> teacher2 = teacherDao.getTeacherByCond(teacher);
        System.out.println(teacher2);

        sqlSession.close();
    }

    @Test
    public void test15() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = teacherDao.getTeacherById(2);
        System.out.println(teacher);

        sqlSession.close();
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

    @Test
    public void test11() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        KeyDao keyDao = sqlSession.getMapper(KeyDao.class);

        Key key = keyDao.getKeyById(1);
        System.out.println(key);

        sqlSession.close();
    }

    @Test
    public void test12() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        LockDao lockDao = sqlSession.getMapper(LockDao.class);

        Lock lock = lockDao.getLockById(3);
        System.out.println(lock);

        sqlSession.close();
    }

    @Test
    public void test13() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        KeyDao keyDao = sqlSession.getMapper(KeyDao.class);

        Key key = keyDao.getKeyByIdSimple(3);
        System.out.println(key.getKeyName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(key);

        sqlSession.close();
    }

    @Test
    public void test14() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        LockDao lockDao = sqlSession.getMapper(LockDao.class);

        Lock lock = lockDao.getLockByIdByStep(3);
        System.out.println(lock);

        sqlSession.close();
    }

}
