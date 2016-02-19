/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Classes.customer;
import Database.DBConnection;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eldridge
 */
public class DB_Manager {
   
    private boolean testConnection()
    {
        DBConnection dbc = new DBConnection();
        Connection conn = null;
        
        conn = dbc.getConnection();
        if(conn == null)
        {
            return false;
        }
        return true;
        
    }
   //Close Connection; 
    private void closeConn(Connection conn, PreparedStatement ps)
    {
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(ps!=null)
                try {
                    ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    // OVERLOAD
    private void closeConn(Connection conn, PreparedStatement ps, ResultSet rs)
    {
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(ps!=null)
                try {
                    ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(rs!=null)
                try {
                    rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean add_customer(Classes.customer new_customer) {
        
        DBConnection db = new DBConnection();
        Connection conn = null;
        PreparedStatement preparedStmt = null;
        boolean added = false;
        try {
            conn = db.getConnection();
            String query = "INSERT INTO customer (customer_name) VALUES (?)";

            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, new_customer.getCustomer_name().toUpperCase());
            preparedStmt.execute();
            
            added = true;
        } 
        catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.closeConn(conn, preparedStmt);
        return added;
    }
    
    public int check_if_customer_exists(String customer_name)
    {
        DBConnection dbc = new DBConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int checkTest = 0;
        try 
        {
            conn = dbc.getConnection();
            ps = conn.prepareStatement("SELECT EXISTS "
                    + " (SELECT id_customer "
                    + " FROM customer WHERE "
                    + " customer_name = ?) "
                    + " AS 'CheckTest'");

            int item = 1;
            ps.setString(item++, customer_name);
            rs = ps.executeQuery();
            if(rs.first())
                checkTest = rs.getInt("CheckTest");
            
        } catch (SQLException ex) {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.closeConn(conn, ps, rs);
        return checkTest;
    }
    
    public ArrayList<String> get_customer_list() 
    {
        DBConnection db = new DBConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> names = new ArrayList<>();
        try
        {
            conn = db.getConnection();  
          
            ps = conn.prepareStatement("SELECT * FROM customer ORDER BY customer_name ASC ");
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                names.add(rs.getString("customer_name"));
            }
            
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.closeConn(conn, ps, rs);
        return names;
    }
    public int get_id_customer_name(customer id_customer)
    {
        DBConnection db = new DBConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id_cus = -1;
        
        try{
            conn = db.getConnection();
            ps = conn.prepareStatement("SELECT id_customer "
                                 + "FROM customer "
                                 + "WHERE customer_name = ? ");
            
            ps.setString(1, id_customer.getCustomer_name());
            
            rs = ps.executeQuery();
            if(rs.first())
            {
                id_cus = rs.getInt("id_customer");
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.closeConn(conn, ps, rs);
        return id_cus;
        
    }
    /***
     * 
     * @param customer_name
     * @return 0 if customer id is not found, else returns customer id taken from database
     */
    public int get_id_customer(String customer_name)
    {
        DBConnection db = new DBConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int customer_id = 0;
        try
        {
            conn = db.getConnection();
            ps = conn.prepareStatement("SELECT id_customer "
                    + "FROM customer "
                    + "WHERE customer_name = '"+ customer_name + "'");
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                customer_id = rs.getInt("id_customer");
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer_id;
    }
    
    //EDITING
    //GET CUSTOMER NAME 
    /**
     * 
     * @param customer_id the primary key of customer
     * @return return a single customer name
     */    
    public String get_customer_name(int customer_id)
    {
        DBConnection db = new DBConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String customer_name = null;
        try
        {
            conn = db.getConnection();
            ps = conn.prepareStatement("SELECT customer_name "
                                                        + "FROM customer "
                                                        + "WHERE id_customer = ?");
            
            int item = 1;
            ps.setInt(item++, customer_id);
            
            rs = ps.executeQuery();
            
            if(rs.first())
            {
                customer_name = rs.getString("customer_name");
                  
            }
              this.closeConn(conn, ps, rs);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.closeConn(conn, ps, rs);
        return customer_name;
    }
    

}
    
   

