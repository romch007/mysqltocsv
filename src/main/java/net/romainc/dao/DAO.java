package net.romainc.dao;

import net.romainc.ui.InformationUI;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAO {

    private Connection conn;
    private List<String> HEADERS;
    private List<List<String>> data;
    private String table;

    public DAO(String host, String user, String password, String schema, String table) {
        this.table = table;
        data = new ArrayList<>();
        HEADERS = new ArrayList<>();
        try {
            String prefix = "jdbc:mysql://";
            String suffix = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(prefix +host+"/"+schema+ suffix,user,password);

            System.out.println("Successfully connected !");

            new InformationUI("Connection", "Successfully connected !", "info");

        } catch (SQLException e) {
            System.out.println("Error in connection :");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace();
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string

            new InformationUI("Error", sStackTrace, "error");
        }
        getHeaders();
        fetchData();
        try {
            createCSVFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getHeaders() {
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SHOW columns FROM " + table);
            while (rs.next()) {
                HEADERS.add(rs.getString(1));
                // System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void fetchData() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + table);

            while (rs.next()) {
                List<String> tmp = new ArrayList<>();
                for (String header : HEADERS) {
                    tmp.add(rs.getString(header));
                }
                data.add(tmp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createCSVFile() throws IOException {
        FileWriter out = new FileWriter("output.csv");
        String [] HEADERS_ARRAY = HEADERS.toArray(new String[0]);


        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS_ARRAY))) {
            for (List<String> tmp : data) {
                printer.printRecord(tmp);
            }
            Desktop.getDesktop().open(new File("output.csv"));
        }
    }

}
