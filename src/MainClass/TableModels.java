/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClass;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dan G
 */
public class TableModels {
    public DefaultTableModel notifModel = new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "Notif Number", "Sender ID", "Name", "Contact #", "Date & Time Recieved", "Latitude", "Longitude", "Geocoded Address"
    }
) {
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
            
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
};
    //===============================================
     public DefaultTableModel notifHistoryModel = new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "Notif Number", "Sender ID", "Name", "Contact #", "Date & Time Recieved", "Latitude", "Longitude", "Geocoded Address","Date & Time Responded" , "Incident Report"
    }
) {
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, false
            
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
};
    
    //=======================================
    
    public DefaultTableModel custRegModel = new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "Client ID", "First Name", "Middle Name", "Last Name", "Age", "Gender", "Contact", "Address"
    }
) {
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
};
    
    
    
    
    
}
