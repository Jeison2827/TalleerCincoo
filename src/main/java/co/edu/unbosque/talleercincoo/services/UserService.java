package co.edu.unbosque.talleercincoo.services;

import com.opencsv.bean.*;
import co.edu.unbosque.talleercincoo.dtos.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static Connection conn;
    public UserService(Connection conn){
        UserService.conn = conn;
    }


    public static  List<User> getUsers() throws IOException{
        Statement stmt = null;

        List<User> users = new ArrayList<User>();
        try{
            stmt = conn.createStatement();
            String sql = "SELECT email, password, username, role FROM usuario";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String email = rs.getString("email");
                String password = rs.getString("password");
                String username = rs.getString("username");
                String role= rs.getString("role");

                users.add(new User(email, password, username, role));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null) stmt.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }
    public static User createUser(String username, String email, String password, String role, String path) throws IOException {
        String newLine = "\n" + username + "," + email + "," + password + "," + role;
        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        os.write(newLine.getBytes());
        os.close();

        return new User(username, email, password, role);
    }
}
