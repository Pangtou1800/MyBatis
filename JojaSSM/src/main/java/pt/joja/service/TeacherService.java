package pt.joja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.joja.bean.Teacher;
import pt.joja.dao.TeacherDao;

@Service
public class TeacherService {

    @Autowired
    TeacherDao teacherDao;

    public Teacher getTeacher(Integer id) {

        return teacherDao.getTeacherById(id);
    }
}
