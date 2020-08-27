package pt.joja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.joja.bean.Teacher;
import pt.joja.service.TeacherService;

@Controller
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @RequestMapping("getTeacher")
    public String getTeacher(@RequestParam("id")Integer id, Model model) {
        Teacher teacher = teacherService.getTeacher(id);
        model.addAttribute("teacher", teacher);
        return "success";
    }

}
