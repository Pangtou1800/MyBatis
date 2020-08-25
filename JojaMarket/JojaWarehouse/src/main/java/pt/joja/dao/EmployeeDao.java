package pt.joja.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import pt.joja.bean.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    public List<Employee> getAllEmps();
    
    @MapKey("id")
    public Map<String, Employee> getAllEmpsToMap();

    public Employee getEmpById(Integer id);

    public Employee getEmpByIdAndName(@Param("id") Integer id, @Param("empName") String empName);

    public Employee getEmpByIdAndName2(Map<String, Object> map);

    public Map<String, Object> getEmpByIdToMap(Integer id);

    public int updateEmployee(Employee employee);

    public boolean deleteEmployee(Integer id);

    public int insertEmployee(Employee employee);

    public int insertEmployee2(Employee employee);

}
