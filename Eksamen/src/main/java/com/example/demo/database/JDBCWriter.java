package com.example.demo.database;

import com.example.demo.domain.Project;
import com.example.demo.domain.User;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class JDBCWriter {

    public User createUser(User u) {
        Connection connection = DBManager.getConnection();
        String sqlstr = "INSERT INTO user (mail, password ) VALUES (?, ?);";
        PreparedStatement preparedStatement;
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(sqlstr);
            System.out.println(sqlstr);
            preparedStatement.setString(1, u.getMail());
            preparedStatement.setString(2, u.getPassword());
            int row = preparedStatement.executeUpdate();
            System.out.println(row);
            System.out.println("Tillykke brugeren: " + preparedStatement + " Er oprettet");
        } catch (SQLException sqlerr) {
            System.out.println("Fejl i oprettels =" + sqlerr);
        }

        return user;
    }

    public User logIn(String mail, String password) {
        Connection connection = DBManager.getConnection();
        String searchLog = "select * FROM user WHERE mail = ? and password = ?; ";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(searchLog);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == false) {
                return user;
            }
            user = new User(resultSet.getInt("id"),resultSet.getString("mail"), resultSet.getString("password"));


        } catch (SQLException sqlerr) {
            System.out.println("Fejl i søgning = " + sqlerr.getMessage());
        }

        return user;
    }


    public Boolean userExist(String mail) {
        Connection connection = DBManager.getConnection();
        String searchString = "select * FROM user WHERE mail = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(searchString);
            preparedStatement.setString(1, mail);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Dette login passer ikke: " + e.getMessage());
            return false;
        }

    }

        public Boolean loginCredentialsCorrect(String mail, String password) {
        Connection connection = DBManager.getConnection();
        String searchStr = "SELECT * FROM user where mail = ? and password = ? ";
        PreparedStatement preparedStatement;
        int res = -1;
        String theMail = mail;
        String thePassword = password;
        ResultSet resset;
        Boolean exist = false;
        try {
            preparedStatement = connection.prepareStatement(searchStr);
            preparedStatement.setString(1, theMail);
            preparedStatement.setString(2, thePassword);
            System.out.println(searchStr);
            System.out.println(preparedStatement);
            resset = preparedStatement.executeQuery();
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
                System.out.println("fundet id: = " + res);
            }
            if (res == 1) {
                exist = true;
                System.out.println("Id " + res + "Eksistere ");
            } else {
            }

        } catch (SQLException sqlerr) {
            System.out.println("fejl i exist = " + sqlerr.getMessage());
        }

        return exist;
    }
    
    public void createNewProject(Project project){
        /*LocalDate temp = project.getDeadlineDate();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String s = format.format(temp);
        Date date = Date.valueOf(temp);*/

        System.out.println("Så langt så godt");
        Connection connection = DBManager.getConnection();
        String sqlstr = "INSERT INTO projects(name, deadlineDate, DeadlineTime, description) VALUES(?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(sqlstr);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setObject(2,  project.getDeadlineDate());
            preparedStatement.setTime(3, project.getDeadlineTime());
            preparedStatement.setString(4, project.getDescription());
            //preparedStatement.setDate(2, s.);
            //int row = preparedStatement.executeUpdate();
            preparedStatement.executeUpdate(sqlstr);
            System.out.println(preparedStatement);
        } catch(SQLException sqlerror){
            System.out.println("Fejl i oprettelse af projekt=" + sqlerror);
        }
    }
    public ArrayList<Project> getProjects(){
        ArrayList<Project> projectList = new ArrayList<>();
        try {
            Connection connection = DBManager.getConnection();
            String sqlproject = "SELECT * FROM projects";
            PreparedStatement prepareStatement;
            prepareStatement = connection.prepareStatement(sqlproject);
            ResultSet resultSet = prepareStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String projectName = resultSet.getString("projectName");
                String description = resultSet.getString("description");
                int numberOfEmployees = resultSet.getInt("numberOfEmployees");
                //Date deadlineDate = resultSet.getDate("deadlineDate");
                //Time deadlineTime = resultSet.getTime("deadlineTime");

                Project project = new Project(id, projectName, description, numberOfEmployees);
                projectList.add(project);
            }
        } catch(SQLException exception){
            System.out.println("Fejl i nedhentning af projekter");
        }
        return projectList;
    }

}
