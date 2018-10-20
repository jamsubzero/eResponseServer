/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClass;

import static MainClass.DBCommCon.CURRENT_TYPE;
import static MainClass.DBCommCon.STATUS_READ;
import static MainClass.DBCommCon.STATUS_RESPONDED;
import static MainClass.DBCommCon.STATUS_UNREAD;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import java.util.Scanner;
import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;



/**
 *
 * @author Dan G
 */
public final class HelpReciever extends javax.swing.JPanel {

    /**
     * Creates new form HelpReciever
     */
    private final Frame j_frame;
    public HelpReciever(Frame frame) {
        initComponents();
        Thread thOL = new Thread(new OnlineThreader());
        thOL.start();
       
        j_frame = frame;
  
 
    }
    
      
    private class OnlineThreader implements Runnable{

        @Override
        public void run() {
      while(true){    
       
 try{
 Class.forName("com.mysql.jdbc.Driver");

 Connection conOL = DriverManager.getConnection(DBCommCon.CONULR_GODADDY);
 
 Connection conLOCAL = DriverManager.getConnection(DBCommCon.CONURL_LOCAL);

//==============ONLINE=================== 
{
Statement stmtChkUnread = conOL.createStatement();
String sqlchk = "SELECT notifID, notifsbebers.clientUN, IFNULL(CONCAT(clientprofile.fname, ' ', clientprofile.mname, ' ', clientprofile.lname), '(Unnamed)') AS name," 
+"date_Time, clientprofile.cont, lati, longi, type, method, status FROM "
+"notifsbebers LEFT JOIN clientprofile ON notifsbebers.clientUN=clientprofile.clientUN WHERE status = "+STATUS_UNREAD+" AND notifsbebers.type = "+CURRENT_TYPE+""; 
ResultSet rsChkUnread = stmtChkUnread.executeQuery(sqlchk);

int quan = 0;
while(rsChkUnread.next()){
    quan++;
  // updatesql = updatesql + "notifID = " +rsChkUnread.getString("notifID") + " OR ";
Thread soundThread = new Thread(new SoundThreader());
soundThread.start();
ArrayList<String> arr = new ArrayList<>(); 
arr.add(rsChkUnread.getString("notifID"));
arr.add(rsChkUnread.getString("clientUN"));
arr.add(rsChkUnread.getString("name"));
arr.add(rsChkUnread.getString("cont"));
arr.add(rsChkUnread.getString("date_time"));
arr.add(rsChkUnread.getString("lati")); 
arr.add(rsChkUnread.getString("longi"));
String updatesql = "UPDATE `notifsbebers` SET `geocodedAdd`=?, status = "+STATUS_READ +
        " WHERE notifID = " +rsChkUnread.getString("notifID");
PreparedStatement pstmtUpdateStat = conOL.prepareStatement(updatesql);
String addr = getAddress(rsChkUnread.getString("lati"), rsChkUnread.getString("longi"));
pstmtUpdateStat.setString(1, addr);
pstmtUpdateStat.executeUpdate();
arr.add(addr);
showNotif(j_frame, false, arr);

} 
 }

//=============================================
//Statement stmtLoadUnres = conOL.createStatement();
//String sqlLoadUnres = "SELECT notifID, notifsbebers.clientUN, "
//+ "IFNULL(CONCAT(clientprofile.fname, ' ', clientprofile.mname, ' ', clientprofile.lname), '(Unnamed)') AS name, "
//+ "date_Time, clientprofile.cont, lati, longi, type, method, status "
//+ "FROM notifsbebers LEFT JOIN clientprofile ON notifsbebers.clientUN=clientprofile.clientUN \n" +
//"WHERE notifsbebers.type = "+CURRENT_TYPE+" AND notifsbebers.status != " + STATUS_RESPONDED;
//ResultSet rsLoadUnres = stmtLoadUnres.executeQuery(sqlLoadUnres);

int curSelRow = notifTable.getSelectedRow();

filterUnrespondedOL();
 
//notifTable.setModel(model);
if(curSelRow != -1){
    try{
 notifTable.setRowSelectionInterval(curSelRow, curSelRow);
    }catch(java.lang.IllegalArgumentException iae){
        
    }
}
//numOfRec.setText(row+"");

 
loadData();

}catch(ClassNotFoundException e){
    e.printStackTrace();
}catch(com.mysql.jdbc.exceptions.jdbc4.CommunicationsException ce){
     Main.setOLStatus(Main.OL_ERROR);
     ce.printStackTrace();
}catch(SQLException x){
   x.printStackTrace();
}
 
try {
  Thread.sleep(2000);
} catch (InterruptedException ex) {
  Logger.getLogger(HelpReciever.class.getName()).log(Level.SEVERE, null, ex);
}
Main.setOLStatus(Main.OL_GOOD);          
        }//end of WHILE
        }

private void showNotif(Frame j_frame, boolean b, ArrayList<String> arr) {
HelpNotif notif = new HelpNotif(j_frame, false, arr);
notif.setLocationRelativeTo(null);
notif.setVisible(true);
}
    }
    
     
    
   private class SoundThreader implements Runnable{

        @Override
        public void run() {
        play("good.mp3");      
        }
       
   }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        notifTable = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filNameID = new javax.swing.JTextField();
        filDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        allCheck = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numOfRec = new javax.swing.JLabel();

        notifTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Notif Number", "Sender ID", "Name", "Contact #", "Date & Time", "Latitude", "Longitude", "Geocoded Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        notifTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(notifTable);

        jButton3.setBackground(new java.awt.Color(0, 204, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_place_black_24dp_1x.png"))); // NOI18N
        jButton3.setText("View in Map");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter By"));

        jLabel1.setText("Sender ID/Name:");

        filDate.setDate(Calendar.getInstance().getTime());

        jLabel3.setText("Date:");

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        allCheck.setText("All");
        allCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filNameID, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filDate, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filNameID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(allCheck)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Record Statistic"));

        jLabel4.setText("Number of Records:");

        numOfRec.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        numOfRec.setForeground(new java.awt.Color(0, 204, 51));
        numOfRec.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numOfRec, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(numOfRec))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int selRow = notifTable.getSelectedRow();

        if(selRow!=-1){
            //reverse bcoz the app pass the opposite lati and longi
            String notifID = notifTable.getValueAt(selRow, 0).toString();
            String number =  notifTable.getValueAt(selRow, 1).toString();
            String name = notifTable.getValueAt(selRow, 2).toString();
             String cont = notifTable.getValueAt(selRow, 3).toString();
            double lati  = Double.parseDouble(notifTable.getValueAt(selRow, 5).toString());
            double longi  = Double.parseDouble(notifTable.getValueAt(selRow, 6).toString());         
     String geoAdd = notifTable.getValueAt(selRow, 7).toString();
            
MapUI newMap = new MapUI(notifID, number, name, cont, lati, longi, geoAdd);
DBCommCon.MapUIInstances.add(newMap);
  //         go(lati, longi, name, type);          
//            frame.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(getRootPane(), "Please select from the list.", "No selected record.", JOptionPane.ERROR_MESSAGE);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
filterUnrespondedOL();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
filNameID.setText("");
allCheck.setSelected(false);
filDate.setDate(Calendar.getInstance().getTime());
filterUnrespondedOL();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void allCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allCheckActionPerformed
if(allCheck.isSelected()){
    filDate.setDate(null);
    filDate.setEnabled(false);
}else{
     filDate.setEnabled(true);
    filDate.setDate(Calendar.getInstance().getTime());  
}
        // TODO add your handling code here:
    }//GEN-LAST:event_allCheckActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JCheckBox allCheck;
    private static org.jdesktop.swingx.JXDatePicker filDate;
    private static javax.swing.JTextField filNameID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable notifTable;
    private static javax.swing.JLabel numOfRec;
    // End of variables declaration//GEN-END:variables

    
  public void loadData(){
    int q = getUnrespondedOL();
    Main.unresponded.setText(q+"");
  }
   
public static void filterUnrespondedOL(){
    String name_id = filNameID.getText();
    SimpleDateFormat df = new SimpleDateFormat("YYY-MM-dd");  

    try{
 Class.forName("com.mysql.jdbc.Driver");

 Connection con = DriverManager.getConnection(DBCommCon.CONULR_GODADDY);
 /*
 String query = "SELECT notifsbebers.notifID, notifsbebers.clientUN, IFNULL(CONCAT(clientprofile.fname, ' ', clientprofile.mname, ' ', clientprofile.lname), '(Unnamed)') AS name," 
+"notifsbebers.date_Time, notifsbebers.lati, notifsbebers.longi, notifsbebers.method, notifsbebers.status FROM "
+"notifsbebers LEFT JOIN clientprofile ON notifsbebers.clientUN=clientprofile.clientUN "
+"WHERE notifsbebers.status !=  "+STATUS_RESPONDED;
 */
 
String query = "SELECT notifID, notifsbebers.clientUN, IFNULL(CONCAT(clientprofile.fname, ' ', clientprofile.mname, ' ', clientprofile.lname), '(Unnamed)') AS name," 
+"date_Time, clientprofile.cont, lati, longi, type, method, status, geocodedAdd FROM "
+"notifsbebers LEFT JOIN clientprofile ON notifsbebers.clientUN=clientprofile.clientUN "
+"WHERE notifsbebers.type = "+CURRENT_TYPE+" AND notifsbebers.status !=  "+STATUS_RESPONDED;

if(!name_id.isEmpty()){
    query = query + " AND (clientprofile.fname LIKE ? OR clientprofile.mname LIKE ? OR clientprofile.lname LIKE ? OR notifsbebers.clientUN LIKE ?) ";
}
if((!allCheck.isSelected())){
   String date = df.format(filDate.getDate());
   query = query + " AND DATE(date_Time)='"+date+"' ";
}

query = query + " ORDER BY notifID DESC;";
System.out.println(query);
 PreparedStatement pstmt = con.prepareStatement(query);
 if(!name_id.isEmpty()){
    pstmt.setString(1, "%"+name_id+"%");   
     pstmt.setString(2,"%"+name_id+"%");
      pstmt.setString(3, "%"+name_id+"%");
       pstmt.setString(4, "%"+name_id+"%");
 }


//String sql = "SELECT notifs.notifID, notifs.clientUN, "
//        + "IFNULL(CONCAT(clientprofile.fname, ' ', clientprofile.mname, ' ', clientprofile.lname), \"(Unregistered)\") AS name, "
//        + "notifs.date_Time, notifs.lati, notifs.longi, "
//         + "method, notifs.status "
//         + "FROM notifs LEFT JOIN clientprofile ON notifs.clientUN=clientprofile.clientUN "
//        + "WHERE status = 0 "
//         + "ORDER BY notifID DESC;";
System.out.println(query);
 ResultSet rs = pstmt.executeQuery();

 DefaultTableModel model = new TableModels().notifModel;
 int row = 0;
 while(rs.next()){
     model.addRow(new Object[]{}); //insert blank row
     model.setValueAt(rs.getString("notifID"), row, 0);
     model.setValueAt(rs.getString("clientUN"), row, 1);
     model.setValueAt(rs.getString("name"), row, 2);
     model.setValueAt(rs.getString("cont"), row, 3);
     model.setValueAt(rs.getString("date_time"), row, 4);
     model.setValueAt(rs.getString("lati"), row, 5); 
     model.setValueAt(rs.getString("longi"), row, 6);
 model.setValueAt(rs.getString("geocodedAdd"), row, 7);
 
row++;
 } 

 notifTable.setModel(model);
 numOfRec.setText(row+"");
}catch(ClassNotFoundException e){
    e.printStackTrace();
}catch(SQLException x){
   x.printStackTrace();
}

}

public static int getUnrespondedOL(){

   int quan = 0;
    try{
 Class.forName("com.mysql.jdbc.Driver");

 Connection con = DriverManager.getConnection(DBCommCon.CONULR_GODADDY);
 String query = "SELECT COUNT(notifID) quan FROM notifsbebers "
         + "WHERE notifsbebers.type = "+CURRENT_TYPE+" AND status != "+DBCommCon.STATUS_RESPONDED+" LIMIT 1;";

System.out.println(query);
Statement stmt = con.createStatement();

ResultSet rs = stmt.executeQuery(query);

if(rs.next()){
quan =   rs.getInt(1);         
} 

}catch(ClassNotFoundException e){
    e.printStackTrace();
}catch(SQLException x){
   x.printStackTrace();
}
return quan;
}


public static String getClient(String id){
String name = "(Unregistered)";
    try{
Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(DBCommCon.CONULR_GODADDY);

String query = " SELECT IFNULL(CONCAT(clientprofile.fname, ' ', clientprofile.mname, ' ', clientprofile.lname), '(Unregistered)') AS name"
        + " FROM clientUN = '"+id+"' LIMIT 1;";

System.out.println(query);
Statement stmt = con.createStatement();

ResultSet rs = stmt.executeQuery(query);

if(rs.next()){
name =   rs.getString(1);         
} 

}catch(ClassNotFoundException e){
    e.printStackTrace();
}catch(SQLException x){
   x.printStackTrace();
}
return name;
}

void play(String f) {
        try {
           InputStream is = HelpReciever.class.getResourceAsStream(f);
            BufferedInputStream bis = new BufferedInputStream(is);
            Player player = new Player(bis);
            player.play();
        } catch (JavaLayerException ex) {
            Logger.getLogger(HelpReciever.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

String getAddress(String lati, String longi){
//String lati = "7.0659299";
//String longi = "125.5647282";
String add  = "(Unknown Address)";
for(int attempt = 0; attempt<5; attempt++){
LatLng loc = new LatLng(lati, longi); // location means lati longi
Geocoder gc = new Geocoder();
GeocoderRequest gcr = new GeocoderRequestBuilder()
        .setLocation(loc)
        .setLanguage("en").getGeocoderRequest();
GeocodeResponse gcRes = gc.geocode(gcr);

List<GeocoderResult> res =  gcRes.getResults(); //Result is in List format
int size = res.size();
if(size!=0){
 GeocoderResult gres = res.get(0); // first item only
 add =    gres.getFormattedAddress(); 
 break;
}else{
    add  = "(Unknown Address)";
}

}
 System.out.println(add);
return add;
}




}
