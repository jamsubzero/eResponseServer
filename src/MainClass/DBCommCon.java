/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClass;

import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Dan G
 */
public class DBCommCon {
    
    public static final String CONULR_GODADDY = "jdbc:mysql://148.66.138.153/emergencydb?"
         + "user=emergencyRoot&password=emergencyPass";
     public static final String CONURL_LOCAL = "jdbc:mysql://localhost/emergencydbase?"
         + "user=root&password=";
    public static final int TYPE_POLICE = 1;
    public static final int TYPE_FIRE = 2;
    public static final int TYPE_LDRRMO = 3;
    public static final int TYPE_HEALTH = 4;
       
    public static final int CURRENT_TYPE = TYPE_FIRE;
    
    public static final int STATUS_UNREAD = 0;
    public static final int STATUS_READ = 1;
    public static final int STATUS_RESPONDED = 2;
    
    public static final String SMSHOST = "localhost";
    public static final int SMSPORT = 9500;
    public static final String SMSUSERNAME = "admin";
    public static final String SMSPASSWORD = "jamsubzero";
    
    
          
    
    public static boolean loginEnabled = true;
    
   public static ArrayList<JFrame> MapUIInstances = new ArrayList<JFrame>();
   
   public static String currentUser = null;
   
}
