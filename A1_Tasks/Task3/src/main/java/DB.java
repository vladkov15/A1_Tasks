import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;



public  class DB {
    /*
    использованна локальная бд
     */
    public static String  url="jdbc:postgresql://localhost:5432/A1";
    public static String username="postgres";
    public static String  password="14082001";

    public static void AddLoginsToDB() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("logins.csv"), ',' , '"' , 1);
        String[] nextLine;
        while ((nextLine= reader.readNext()) != null) {
            if (nextLine != null) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i <nextLine.length ; i++) {
                    list.add(nextLine[i]);

                }
                LoginParams(list);
            }

        }

    }
        public static void LoginParams(ArrayList<String> list) throws SQLException {
            Connection connection = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO logins (app,appaccountname,isactive,jobtitle,department)" + "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,list.get(0));
            preparedStatement.setString(2,list.get(1));
            preparedStatement.setString(3,list.get(2));
            preparedStatement.setString(4,list.get(3));
            preparedStatement.setString(5,list.get(4));

            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows>0){
                try(ResultSet rs = preparedStatement.getGeneratedKeys()) {

                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }

            }
            preparedStatement.close();
            connection.close();

        }

    public static void AddPostingsToDB() throws Exception {

        //tb mat doc
        BufferedReader br = new BufferedReader(new FileReader("postings.csv"));
        String line;
        ArrayList<String>doc = new ArrayList<>();
        int counter= 0;
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(";");
            if (counter>0){
                doc.add(cols[0]);
            }
            counter++;
        }
          ArrayList<String> newdoc = new ArrayList<>(doc);
        for (int i = 0; i <doc.size() ; i++) {
            for (int j = 0; j < doc.size(); j++) {
                if (i!=j) {
                    if (doc.get(i).equals(doc.get(j))) {
                        doc.remove(j);
                    }
                }
            }
        }

        for (int i = 0; i <doc.size() ; i++) {
            ParamToMatDoc(doc.get(i),i+1);
        }
        for (int i = 0; i <doc.size() ; i++) {//Params2
            for (int j = 0; j <newdoc.size() ; j++) {
              if (doc.get(i).equals(newdoc.get(j))){
                 newdoc.set(j, String.valueOf((i+1)));
              }
            }

        }

        //tb material
        BufferedReader br1 = new BufferedReader(new FileReader("postings.csv"));
        ArrayList<String>material = new ArrayList<>();
        String sline;
         counter= 0;
        while ((sline = br1.readLine()) != null) {
            String[] cols = sline.split(";");
            if (counter>0){
                material.add(cols[4]);
            }
            counter++;
        }
        ArrayList<String> newmaterial = new ArrayList<>(material);
        for (int i = 0; i <material.size() ; i++) {
            for (int j = 0; j < material.size(); j++) {
                if (i!=j) {
                    if (material.get(i).equals(material.get(j))) {
                        material.remove(j);
                    }
                }
            }
        }
        for (int i = 0; i <material.size() ; i++) {
            ParamToMaterial(material.get(i),i+1);
        }
        for (int i = 0; i <material.size() ; i++) {//Params2
            for (int j = 0; j <newmaterial.size() ; j++) {
                if (material.get(i).equals(newmaterial.get(j))){
                    newmaterial.set(j, String.valueOf((i+1)));
                }
            }

        }
        // name tb
        BufferedReader br2 = new BufferedReader(new FileReader("postings.csv"));
        ArrayList<String> name = new ArrayList<>();
        String lin;
        counter= 0;
        while ((lin = br2.readLine()) != null) {
            String[] cols = lin.split(";");
            if (counter>0){
                name.add(cols[9]);
            }
            counter++;
        }

        ArrayList<String> newname = new ArrayList<>(name);
        for (int i = 0; i <name.size() ; i++) {
            for (int j = 0; j < name.size(); j++) {
                if (i!=j) {
                    if (name.get(i).equals(name.get(j))) {
                        name.remove(j);
                    }
                }
            }
        }
        for (int i = 0; i <name.size() ; i++) {
            ParamToName(name.get(i),i+1);
        }
        for (int i = 0; i <name.size() ; i++) {//Params2
            for (int j = 0; j <newname.size() ; j++) {
                if (name.get(i).equals(newname.get(j))){
                    newname.set(j, String.valueOf((i+1)));
                }
            }

        }
        BufferedReader br3 = new BufferedReader(new FileReader("postings.csv"));
        ArrayList<String> posting = new ArrayList<>();
        String str;
        counter= 0;
        while ((str = br3.readLine()) != null) {
            String[] cols = str.split(";");
            if (counter>0){
                posting.add(cols[0]+";"+cols[1]+";"+cols[2]+";"+cols[3]+";"+cols[4]+";"+cols[5]+";"+cols[6]+";"+cols[7]+";"+cols[8]+";"+cols[9]+";"+cols[10]);
            }
            counter++;
        }
        for (int i = 0; i <posting.size() ; i++) {
            ParamToPosting(posting,newdoc.get(i),newmaterial.get(i),newname.get(i));
        }

    }
    public static void ParamToPosting(ArrayList<String> posting,String matdoc,String material,String name) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        String str = posting.get(0);
        String[]mass=str.split(";");

       String sql = "INSERT INTO posting (idmatdoc,item,docdate,pstngdate,idmaterial,bun,almostlc,crcy,idusername,autdelivery)" + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, Integer.parseInt(matdoc));
        preparedStatement.setString(2,mass[1]);
        preparedStatement.setString(3,mass[2]);
        preparedStatement.setString(4,mass[3]);
        preparedStatement.setInt(5, Integer.parseInt(material));
        preparedStatement.setString(6,mass[5]);
        preparedStatement.setString(7,mass[7]);
        preparedStatement.setString(8,mass[8]);
        preparedStatement.setInt(9, Integer.parseInt(name));
        preparedStatement.setString(10,mass[10]);
        int affectedRows=preparedStatement.executeUpdate();
        if(affectedRows>0){
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        preparedStatement.close();
        connection.close();



    }
    public static void ParamToMatDoc(String doc, int row) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);

        String sql = "INSERT INTO matdoc (idmatdoc,matdoc)" + "VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, row);
        preparedStatement.setString(2,doc);

        int affectedRows=preparedStatement.executeUpdate();
        if(affectedRows>0){
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        preparedStatement.close();
        connection.close();

    }
    public static void ParamToMaterial(String material, int row) throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);

        String sql = "INSERT INTO materialdescription (idmaterial,materialdescription)" + "VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, row);
        preparedStatement.setString(2,material);

        int affectedRows=preparedStatement.executeUpdate();
        if(affectedRows>0){
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        preparedStatement.close();
        connection.close();

    }
    public static void ParamToName(String username, int row) throws SQLException {

        Connection connection = DriverManager.getConnection(url,"postgres", password);

        String sql = "INSERT INTO username (idusername,username)" + "VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, row);
        preparedStatement.setString(2,username);

        int affectedRows=preparedStatement.executeUpdate();
        if(affectedRows>0){
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        preparedStatement.close();
        connection.close();

    }



}



