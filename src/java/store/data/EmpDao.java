/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import store.business.Product;

/**
 *
 * @author curti
 */
public class EmpDao {
    public static Connection getConnection(){  
        Connection con=null;  
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","","");  
        }catch(Exception e){System.out.println(e);}  
        return con;  
    }  
  
    public static List<Product> getRecords(int start,int total){  
        List<Product> list=new ArrayList<Product>();  
        try{  
            Connection con=getConnection();  
            PreparedStatement ps=con.prepareStatement(  
"select * from emp limit "+(start-1)+","+total);  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Product p = new Product();  
                p.setId(rs.getLong(1));  
                p.setCode(rs.getString(2));  
                p.setDescription(rs.getString(3));  
                list.add(p);  
            }  
            con.close();  
        }catch(Exception e){System.out.println(e);}  
        return list;  
    }  
}
