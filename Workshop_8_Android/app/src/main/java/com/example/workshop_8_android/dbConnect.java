package com.example.workshop_8_android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class dbConnect {
    public static void main(String[] args) {
            Connection conn = null;
            try {
                    String url = "jdbc:mariadb://localhost:3306/travelexperts";
                    String user      = "admin";
                    String password  = "password";


                } finally {
                    try{
                        if(conn != null)
                            conn.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
