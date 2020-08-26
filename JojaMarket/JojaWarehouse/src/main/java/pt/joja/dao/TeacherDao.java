package pt.joja.dao;

import org.apache.ibatis.annotations.Param;
import pt.joja.bean.Teacher;

import java.util.List;

public interface TeacherDao {

    public Teacher getTeacherById(int id);

    public List<Teacher> getTeacherByCond(Teacher teacher);

    public List<Teacher> getTeacherByCond2(Teacher teacher);

    public List<Teacher> getTeacherByIdList(@Param("idList") List<Integer> idList);

    public int updateTeacher(Teacher teacher);

}
