package com.example.demo.controller;

import com.example.demo.database.DBManager;
import com.example.demo.database.JDBCWriter;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class myController {

    @Autowired
    JDBCWriter jdbcWriter;

    @GetMapping("/")//her giver du data
    public String index() {
        DBManager.getConnection();
        return "index";
    }

    @PostMapping("/login")//Her
    public String login(@RequestParam String mail, @RequestParam String password){
        boolean user = jdbcWriter.userExist(mail,password);

        if(user == false){
            System.out.println("Der var intet match");
            return "redirect:/";
        } else {
            return "createProject";
        }
    }

}
/*

    @PostMapping("/login")
    public String loginUser(WebRequest request) throws LoginSampleException {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");

        // delegate work + data to login controller
        User user = loginController.login(email, pwd);
        setSessionInfo(request, user);

        // Go to to page dependent on role
        return "userpages/";
    }

 */
