/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import Database.DB_Manager;
import java.util.ArrayList;

/**
 *
 * @author Eldridge
 */
public class customer {
    private int customer_id;
    private String Customer_name;
    private ArrayList<String> Customer_names = new ArrayList<String>();

    /**
     * @return the Customer_id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * @param Customer_id the Customer_id to set
     */
    public void setCustomer_id(int the_customer_id) {
        this.customer_id = the_customer_id;
    }

    /**
     * @return the Customer_name
     */
    public String getCustomer_name() {
        return Customer_name;
    }

    /**
     * @param Customer_name the Customer_name to set
     */
    public void setCustomer_name(String Customer_name) {
        this.Customer_name = Customer_name.toUpperCase();
    }
    
    public void add_new_customer()
    {
        DB_Manager new_conn = new DB_Manager();
        if(!this.check_if_this_customer_exists())
            new_conn.add_customer(this);
        
        this.set_customer_id_from_name();
    }

    /**
     * @return the Customer_names
     */
    public ArrayList<String> getCustomer_names() {
        return Customer_names;
    }

    /**
     * @param Customer_names the Customer_names to set
     */
    public void setCustomer_names(ArrayList<String> Customer_names) {
        this.Customer_names = Customer_names;
    }
    /*
    public void searchCustomer_name() {
        DB_Manager new_conn = new DB_Manager();
        new_conn.Search_Customer_Name(this);
    }
    */
    
    public boolean check_if_this_customer_exists()
    {
        DB_Manager new_conn = new DB_Manager();
        //Returns 1 if it exists 0 if it does not
        if(new_conn.check_if_customer_exists(Customer_name) == 0)
        {
            //it does not exists
            return false;
        }
        //it exists
        return true;
    }
    
    public void get_customer_list() {
        DB_Manager new_conn = new DB_Manager();
        this.Customer_names = new_conn.get_customer_list();
    }
    
    public void set_customer_id_from_name() {
        DB_Manager new_conn = new DB_Manager();
        this.customer_id = new_conn.get_id_customer_name(this);
    }
    
    public void set_customer_name_from_id()
    {
        DB_Manager new_conn = new DB_Manager();
        this.Customer_name = new_conn.get_customer_name(customer_id);
    }
    
    
   
}
