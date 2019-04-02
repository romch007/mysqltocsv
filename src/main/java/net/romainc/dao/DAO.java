package net.romainc.dao;

import net.romainc.Variables;
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

    /**
     * Create a new connection to a MySQL database
     * @param host the hostname
     * @param user the username
     * @param password the password (if required)
     * @param schema the default schema
     * @param table the table
     * @param allData tables mode or data mode
     */
    public DAO(String host, String user, String password, String schema, String table, boolean allData) {
        this.table = table;
        data = new ArrayList<>();
        HEADERS = new ArrayList<>();
        try {
            String prefix = "jdbc:mysql://";
            String suffix = "characterEncoding=latin1&characterSetResults=latin1&serverTimezone=UTC&useSSL=false";
            conn = DriverManager.getConnection(prefix +host+"/"+schema+"?"+suffix,user,password);

            System.out.println("Successfully connected !");

            new InformationUI("Connection", "Successfully connected !", Variables.UIInfo);

        } catch (SQLException e) {
            System.out.println("Error in connection :");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace();
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string

            new InformationUI("Error", sStackTrace, Variables.UIError);
        }
        if (allData) {
            getHeaders();
            fetchData();
            try {
                createCSVFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            List<String> tables = getTables();
            StringBuilder sb = new StringBuilder();
            tables.forEach(e -> sb.append(e).append("; "));
            new InformationUI("Tables", "Available tables : " + sb.toString(), Variables.UIInfo);
        }
    }

    /**
     * Get the headers of the table (stored in HEADERS array)
     */
    private void getHeaders() {
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SHOW columns FROM " + table);
            while (rs.next()) {
                HEADERS.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Select all the data from a table (stored in data array)
     */
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

    /**
     * Create a CSV file with the fetched data
     * @throws IOException error in CSV creation
     */

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

    /**
     * Get all the tables of a schema
     * @return an list of the tables
     */
    private List<String> getTables() {
        Statement st;
        List<String> tables = new ArrayList<>();
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SHOW TABlES");
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }
}
