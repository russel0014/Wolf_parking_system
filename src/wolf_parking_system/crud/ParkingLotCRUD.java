package wolf_parking_system.crud;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import wolf_parking_system.dbclasses.*;
import wolf_parking_system.connection.*;


public class ParkingLotCRUD {
    // view parking lots
    private  Statement statement;
    private Connection connection;
    public ParkingLotCRUD(Statement statement,Connection connection){
        this.statement=statement;
        this.connection=connection;

    }
   
    
    public  ArrayList<ParkingLot> viewParkingLots() {
        try {
          
           
            ResultSet rs = statement.executeQuery("Select * from ARTICLE");
            ArrayList<ParkingLot> list = new ArrayList<>();
            while (rs.next()) {
                ParkingLot p = new ParkingLot(rs.getString("LotName"),rs.getString("Address"));
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

// insert in parking lots
        public  Boolean insertParkinglot(String LotName,String Address) {
            try {
                
                String query = "insert into ParkingLot(LotName, Address ) values (?,?)";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, LotName);
                st.setString(2, Address);
                st.executeUpdate();
                ResultSet rs = st.executeQuery("select  LotName from ParkingLot"); //Is this for displaying data being inserted or not
                while (rs.next())
                    LotName = rs.getString("LotName");
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }

//update article  
    
        public  Boolean updateParkingLot(String LotName,String Address) {
            try {
                
    //            Update ARTICLE set Text='Qwerty' where PID=2 AND ArticleID=3;
                String query = "Update ParkingLot set LotName=? where Address=? ";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, LotName);
                st.setString(2, Address);
                st.executeUpdate();
                ResultSet rs = st.executeQuery("Select count(*) as count_val from ParkingLot where Address="+ Address);
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt("count_val");
    
                }
                if (count!=0){
                    return Boolean.valueOf(true);;
                }
                return Boolean.valueOf(false);;
                //return Boolean.valueOf(true);
            } catch (SQLException e) {
                e.printStackTrace();
                return Boolean.valueOf(false);
            }
        }
    
        public Boolean deleteParkingLot(String LotName,String Address) {
            try {
               
                String query = "DELETE FROM ParkingLot WHERE Address ";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, LotName);
                st.setString(2, Address);
                st.executeUpdate();
                return true;
    
            } catch (SQLException ex) {
                ex.printStackTrace();
                return Boolean.valueOf(false);
            }
        }
    }
   
    

    

}
