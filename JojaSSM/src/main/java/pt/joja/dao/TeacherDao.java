package pt.joja.dao;

import org.apache.ibatis.annotations.Param;
import pt.joja.bean.Teacher;

import java.util.List;

public interface TeacherDao {

    Teacher getTeacherById(int id);

    List<Teacher> getTeacherByCond(Teacher teacher);

    List<Teacher> getTeacherByCond2(Teacher teacher);

    List<Teacher> getTeacherByIdList(@Param("idList") List<Integer> idList);

    int updateTeacher(Teacher teacher);

}
