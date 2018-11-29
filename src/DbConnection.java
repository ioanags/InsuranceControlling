
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DbConnection{
    Connection conn;
    Statement stmt;
    //Connection to server;
    public void connection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/insurance?useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false","root","1234");
            System.out.println("Connected");
        } catch (ClassNotFoundException ex){
            System.out.println("Driver not found");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }/*finally{
            try{
                if (conn !=null){
                    conn.close();}
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }*/
    }
    //This method search the input plate in DB and returns status of insurance.
    public void searchTable(String plate) {
        try {
            stmt = conn.createStatement();
            String query = "select * From insurance.insuranceinfo WHERE plates = '" + plate + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.isBeforeFirst()) {
                System.out.println("Plate doesn't exist");
                return;
            }
            while (rs.next()) {
                String ownersName = rs.getString("Owner");
                String plates = rs.getString("Plates");
                String vehicleName = rs.getString("Vehicle");
                Date expirationDate = rs.getDate("ExpirationDate");
                boolean isValid = rs.getBoolean("Status");
                Person owner = new Person(plates, expirationDate, isValid, vehicleName, ownersName);
                if (isValid) {

                    System.out.println("Insurance status is Active");
                    System.out.println(owner.toString());
                } else {
                   // Person owner = new Person(plates, expirationDate, isValid, vehicleName, ownersName);
                    System.out.println("Insurance has expired");
                    System.out.println(owner.toString());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try{
                if (conn !=null){
                    conn.close();}
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
        //Search vehicle's insurance about to expire in given days (Console)
        public void aboutToExpire ( int days){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Calendar currentDate = Calendar.getInstance();
            String dateNow = dateFormat.format(currentDate.getTime());
            currentDate.setTime(new java.util.Date());
            currentDate.add(Calendar.DATE, days);
            String outputDate = dateFormat.format(currentDate.getTime());
            try {
                stmt = conn.createStatement();
                String query = "select * from insurance.insuranceinfo WHERE ExpirationDate BETWEEN '" + dateNow + "' AND '" + outputDate + "'";
                ResultSet rs = stmt.executeQuery(query);
                if (!rs.isBeforeFirst()) {
                    System.out.println("Wrong input");
                    return;
                }
                while (rs.next()) {
                    String ownersName = rs.getString("Owner");
                    String plates = rs.getString("Plates");
                    String vehiclesName = rs.getString("Vehicle");
                    Date expirationDate = rs.getDate("ExpirationDate");
                    boolean isValid = rs.getBoolean("Status");

                    Person owner = new Person(plates, expirationDate, isValid, vehiclesName, ownersName);
                    System.out.println(owner.getName() + " license is about to expire at " + owner.getDate());
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
            ////Search vehicle's insurance about to expire in given days (Export to file)
        public void aboutToExpireCSV ( int daysCsv) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Calendar currentDate = Calendar.getInstance();
            String dateNow = dateFormat.format(currentDate.getTime());
            currentDate.setTime(new java.util.Date());
            currentDate.add(Calendar.DATE, daysCsv);
            String outputDate = dateFormat.format(currentDate.getTime());
            String FILE_HEADER = "Plate,Vehicle,Name,ExpirationDate,InsuranceStatus";
            FileWriter fw = null;
            List<String> name = new ArrayList<>();
            List<String> plates = new ArrayList<>();
            List<String> vehiclesName = new ArrayList<>();
            List<String> expirationDate= new ArrayList<>();
            List<String> isValid = new ArrayList<>();
            try {
                fw = new  FileWriter("C:\\Users\\Admin\\Desktop\\test.csv");
                stmt = conn.createStatement();
                String query = "select * from insurance.insuranceinfo WHERE ExpirationDate BETWEEN '" + dateNow + "' AND '" + outputDate + "'";
                ResultSet rs = stmt.executeQuery(query);
                if (!rs.isBeforeFirst()) {
                    System.out.println("Wrong input");
                    return;
                }
                fw.append(FILE_HEADER);
                fw.append('\n');
                while (rs.next()) {
                    name.add(rs.getString("Owner"));
                    plates.add(rs.getString("Plates"));
                    vehiclesName.add(rs.getString("Vehicle"));
                    expirationDate.add(rs.getString("ExpirationDate"));
                    isValid.add(rs.getString("Status"));
                    }
                for(int i = 0; i < name.size(); i++) {
                    fw.append(name.get(i));
                    fw.append(',');
                    fw.append(plates.get(i));
                    fw.append(',');
                    fw.append(vehiclesName.get(i));
                    fw.append(',');
                    fw.append(expirationDate.get(i));
                    fw.append(',');
                    fw.append(isValid.get(i));
                    fw.append("\n");
                }


            } catch (Exception e) {
                System.out.println("Error in CsvFileWriter !!!");
                e.printStackTrace();
            } finally {
                try {
                    fw.flush();
                    fw.close();
                    System.out.println("File created successfully ");
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter");
                    e.printStackTrace();
                }
            }
        }
    public void expiredAscendingInList(){
        List<String> name = new ArrayList<>();
        List<String> plates = new ArrayList<>();
        List<String> vehiclesName = new ArrayList<>();
        List<String> expirationDate= new ArrayList<>();
        List<String> isValid = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            String query = "select * From insurance.insuranceinfo";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.isBeforeFirst()) {
                System.out.println("Db is empty");
                return;
            }
            while (rs.next()) {
                if(rs.getInt("Status") == 1){
                    name.add(rs.getString("Owner"));
                    plates.add(rs.getString("Plates"));
                    vehiclesName.add(rs.getString("Vehicle"));
                    expirationDate.add(rs.getString("ExpirationDate"));
                    isValid.add(rs.getString("Status"));
                }
            }
               //System.out.println(name.get(0));
            //bubblesort
            int n = plates.size();
            String temp ;
            for (int i = 0 ; i<n; i++){
                for (int j = 1; j <(n-i );j++ ){
                  int comp = plates.get(j).compareTo(plates.get(j-1));
                 if (comp < 0){
                     temp = plates.get(j-1);
                     plates.set(j-1,plates.get(j));
                     plates.set(j,temp);
                    temp = name.get(j-1);
                     name.set(j-1,name.get(j));
                     name.set(j,temp);
                     temp = vehiclesName.get(j-1);
                     vehiclesName.set(j-1,vehiclesName.get(j));
                     vehiclesName.set(j,temp);
                     temp = expirationDate.get(j-1);
                     expirationDate.set(j-1,expirationDate.get(j));
                     expirationDate.set(j,temp);
                     temp = isValid.get(j-1);
                     isValid.set(j-1,isValid.get(j));
                     isValid.set(j,temp);
                 }


                //System.out.println(name.get(i)+" "+plates.get(i)+" "+vehiclesName.get(i)+" Expired at:"+expirationDate.get(i)+" "+"\n");
            }
            }
           for (int i=0;i < plates.size();i++) {
                System.out.println("Plates: "+plates.get(i) + " Name: "+ name.get(i)+" expiration date "+expirationDate.get(i));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    }







