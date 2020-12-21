package com.example.demo.controllers;

import com.example.demo.repositories.Mapper;
import com.example.demo.models.Project;
import com.example.demo.models.Subproject;
import com.example.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class CreateController {


    Mapper mapper;
    Project project;

    public CreateController(Mapper mapper){
        this.mapper = mapper;
    }

    @GetMapping("/createUser")
    public String createUser(){
        return "createUser";
    }

    @PostMapping("/createProject")
    public String createProject(Project project, HttpSession session, Model model, HttpServletRequest servletRequest) {
        User user = (User) session.getAttribute("login");
        int userid = user.getId();
        mapper.createProject(project, userid);
        int lastProjectID = mapper.getLastProjectID();
        checkLogin(user);
        ArrayList<Project> projectList = mapper.getUserProjects();

        System.out.println("Project created successfully");
        model.addAttribute("project", projectList);
        session = servletRequest.getSession();
        session.setAttribute("projectList", projectList);
        return "redirect:/createNewSubProject/" + lastProjectID;
    }

    @PostMapping("/createSubProject")
    public String createSubProject(Subproject subproject, HttpSession session){
        User user = (User) session.getAttribute("login");
        int userid = user.getId();
        checkLogin(user);
        mapper.createSubProject(subproject, userid);
        int checkIfAdmin = user.getIsAdmin();
        if(checkIfAdmin == 1){
            return "redirect:/projects";
        } else{
            return "redirect:/userProfile";
        }
    }

    @GetMapping("/createNewSubProject/{projectId}")
    public String showCreateSubProject(@PathVariable("projectId") int projectId, Model model, Model model1,
                                       HttpSession session, HttpServletRequest servletRequest) {
        HttpSession httpSession = servletRequest.getSession();
        User theuser = (User) session.getAttribute("login");
        model.addAttribute("subproject", new Subproject());
        model.addAttribute("projectId", projectId);
        ArrayList<Project> projectList = (ArrayList<Project>) httpSession.getAttribute("projectList");
        Project oneProject = null;
        for(Project project:projectList){
            if(project.getId()==projectId){
                oneProject = project;
            }else{
                System.out.println("No project found");
            }
        }
        model1.addAttribute("project", oneProject);
        return "createSubProject";
    }

    @PostMapping("updateProject")
    public String updateProject(@RequestParam Project project, HttpSession session){
        User theuser = (User) session.getAttribute("login");
        checkLogin(theuser);
        mapper.updateProject(project);
        return "userProfile";
    }

    @GetMapping("/createNewProject")
    public String showCreateProject(Project project, Model model, HttpSession session) {// Model model fletter data, og tager dem fra thymeleaf og bruger dem
        User theuser = (User) session.getAttribute("login");
        checkLogin(theuser );
        int userid = theuser.getIsAdmin();
        if (userid == 1){
            theuser.adminID++;
            model.addAttribute("project", project);
            return "createProject";
        } else {
            return "index";
        }
    }


    @GetMapping("updateProject")
    public String updateProject(){
        return "userProfile";
    }

    private void checkLogin(User user) {
        System.out.println("Bruger: " + user + ", er stadig logget ind! ");
    }

    private void setSessionInfo(WebRequest request, User user) {
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
    }
}