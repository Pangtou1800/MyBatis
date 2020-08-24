package pt.joja.dao;

import org.apache.ibatis.annotations.Select;
import pt.joja.bean.Employee;

public interface EmployeeDaoAnno {

    @Select("select * from t_employee where id = #{id}")
    public Employee getEmpById(Integer id);
}
