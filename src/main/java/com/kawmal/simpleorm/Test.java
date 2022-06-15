package com.kawmal.simpleorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "1234");


        ORMSessionFactory ormSessionFactory=new ORMSessionFactory();
            ormSessionFactory.addAnnotatedClass(Student.class).setConnection(connection).bootstrap();
    }
}
