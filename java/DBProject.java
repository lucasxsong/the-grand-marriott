/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

    // reference to physical database connection.
    private Connection _connection = null;

    // handling the keyboard inputs through a BufferedReader
    // This variable can be global for convenience.
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // global query strings for generating new IDs
    static String getNumCustomers = "SELECT COUNT(*) FROM Customer;";
    static String getNumCompanies = "SELECT COUNT(*) FROM MaintenanceCompany;";
    static String getNumRepairs = "SELECT COUNT(*) FROM Repair;";
    static String getNumBookings = "SELECT COUNT(*) FROM Booking;";
    static String getNumAssigned = "SELECT COUNT(*) FROM Assigned;";
    static String getNumRequests = "SELECT COUNT(*) FROM Request;";

    /**
     * Creates a new instance of DBProject
     *
     * @param hostname the MySQL or PostgreSQL server hostname
     * @param database the name of the database
     * @param username the user name used to login to the database
     * @param password the user login password
     * @throws java.sql.SQLException when failed to make a connection.
     */
    public DBProject(String dbname, String dbport, String user, String passwd) throws SQLException {

        System.out.print("Connecting to database...");
        try {
            // constructs the connection URL
            String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
            System.out.println("Connection URL: " + url + "\n");

            // obtain a physical connection
            this._connection = DriverManager.getConnection(url, user, passwd);
            System.out.println("Done");
        } catch (Exception e) {
            System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
            System.out.println("Make sure you started postgres on this machine");
            System.exit(-1);
        } // end catch
    }// end DBProject

    /**
     * Method to execute an update SQL statement. Update SQL instructions includes
     * CREATE, INSERT, UPDATE, DELETE, and DROP.
     *
     * @param sql the input SQL string
     * @throws java.sql.SQLException when update failed
     */
    public void executeUpdate(String sql) throws SQLException {
        // creates a statement object
        Statement stmt = this._connection.createStatement();

        // issues the update instruction
        stmt.executeUpdate(sql);

        // close the instruction
        stmt.close();
    }// end executeUpdate

    /**
     * Method to execute an input query SQL instruction (i.e. SELECT). This method
     * issues the query to the DBMS and outputs the results to standard out.
     *
     * @param query the input query string
     * @return the number of rows returned
     * @throws java.sql.SQLException when failed to execute the query
     */
    public int executeQuery(String query) throws SQLException {
        // creates a statement object
        Statement stmt = this._connection.createStatement();

        // issues the query instruction
        ResultSet rs = stmt.executeQuery(query);

        /*
         ** obtains the metadata object for the returned result set. The metadata
         ** contains row and column info.
         */
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCol = rsmd.getColumnCount();
        int rowCount = 0;

        // iterates through the result set and output them to standard out.
        boolean outputHeader = true;
        while (rs.next()) {
            if (outputHeader) {
                for (int i = 1; i <= numCol; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
                System.out.println();
                outputHeader = false;
            }
            for (int i = 1; i <= numCol; ++i)
                System.out.print(rs.getString(i) + "\t");
            System.out.println();
            ++rowCount;
        } // end while
        stmt.close();
        return rowCount;
    }// end executeQuery

    /**
     * Method to gather the number of rows (or ids) in order to generate a new one
     */
    public int getNewID(String countQuery) throws SQLException {
        // creates a statement object
        Statement stmt = this._connection.createStatement();

        // Issues the query instruction
        ResultSet rs = stmt.executeQuery(countQuery);

        int rowCount = 0;

        // iterates through the result set to find the return statement with the number
        // of rows
        boolean outputHeader = true;
        while (rs.next()) {
            if (outputHeader) {
                outputHeader = false;
            } else {
                rowCount = rs.getInt(0);
                break;
            }
        } // end while
        stmt.close();

        // return new ID : row count
        return rowCount;
    }

    /**
     * Method to close the physical connection if it is open.
     */
    public void cleanup() {
        try {
            if (this._connection != null) {
                this._connection.close();
            } // end if
        } catch (SQLException e) {
            // ignored.
        } // end try
    }// end cleanup

    /**
     * The main execution method
     *
     * @param args the command line arguments this inclues the <mysql|pgsql> <login
     *             file>
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: " + "java [-classpath <classpath>] " + DBProject.class.getName()
                    + " <dbname> <port> <user>");
            return;
        } // end if

        Greeting();
        DBProject esql = null;
        try {
            // use postgres JDBC driver.
            Class.forName("org.postgresql.Driver").newInstance();
            // instantiate the DBProject object and creates a physical
            // connection.
            String dbname = args[0];
            String dbport = args[1];
            String user = args[2];
            esql = new DBProject(dbname, dbport, user, "");

            boolean keepon = true;
            while (keepon) {
                // These are sample SQL statements
                System.out.println("MAIN MENU");
                System.out.println("---------");
                System.out.println("1. Add new customer");
                System.out.println("2. Add new room");
                System.out.println("3. Add new maintenance company");
                System.out.println("4. Add new repair");
                System.out.println("5. Add new Booking");
                System.out.println("6. Assign house cleaning staff to a room");
                System.out.println("7. Raise a repair request");
                System.out.println("8. Get number of available rooms");
                System.out.println("9. Get number of booked rooms");
                System.out.println("10. Get hotel bookings for a week");
                System.out.println("11. Get top k rooms with highest price for a date range");
                System.out.println("12. Get top k highest booking price for a customer");
                System.out.println("13. Get customer total cost occurred for a give date range");
                System.out.println("14. List the repairs made by maintenance company");
                System.out.println("15. Get top k maintenance companies based on repair count");
                System.out.println("16. Get number of repairs occurred per year for a given hotel room");
                System.out.println("17. < EXIT");

                switch (readChoice()) {
                case 1:
                    addCustomer(esql);
                    break;
                case 2:
                    addRoom(esql);
                    break;
                case 3:
                    addMaintenanceCompany(esql);
                    break;
                case 4:
                    addRepair(esql);
                    break;
                case 5:
                    bookRoom(esql);
                    break;
                case 6:
                    assignHouseCleaningToRoom(esql);
                    break;
                case 7:
                    repairRequest(esql);
                    break;
                case 8:
                    numberOfAvailableRooms(esql);
                    break;
                case 9:
                    numberOfBookedRooms(esql);
                    break;
                case 10:
                    listHotelRoomBookingsForAWeek(esql);
                    break;
                case 11:
                    topKHighestRoomPriceForADateRange(esql);
                    break;
                case 12:
                    topKHighestPriceBookingsForACustomer(esql);
                    break;
                case 13:
                    totalCostForCustomer(esql);
                    break;
                case 14:
                    listRepairsMade(esql);
                    break;
                case 15:
                    topKMaintenanceCompany(esql);
                    break;
                case 16:
                    numberOfRepairsForEachRoomPerYear(esql);
                    break;
                case 17:
                    keepon = false;
                    break;
                default:
                    System.out.println("Unrecognized choice!");
                    break;
                }// end switch
            } // end while
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            // make sure to cleanup the created table and close the connection.
            try {
                if (esql != null) {
                    System.out.print("Disconnecting from database...");
                    esql.cleanup();
                    System.out.println("Done\n\nBye !");
                } // end if
            } catch (Exception e) {
                // ignored.
            } // end try
        } // end try
    }// end main

    public static void Greeting() {
        // System.out.println("\n\n*****WELCOME TO DATABATES HOTEL*****\n" + "               /\\\\  \\\n"
        //         + "            __// \\\\  \\____\n" + "          /\\ //   \\\\ /\\   \\\n"
        //         + "         //\\\\/ ' ' \\//\\\\   \\\n" + "        // '\\\\ ' ' //  \\\\___\\\n"
        //         + "         | ' ' ' ' ' ' |. .|\n" + "         | ' ' ' ' ' ' |. .|\n" + "         | ' ' '_' ' ' |. .|\n"
        //         + "         | ' ' / \\ ' ' |. .|\n" + "         |_'_'_|#|_'_'_|_.-'\n"
        //         + "************************************\n");
            String greet[] ={"*****WELCOME TO DATABATES HOTEL*****" , "               /\\\\  \\"
            , "            __// \\\\  \\____" , "          /\\ //   \\\\ /\\   \\"
            , "         //\\\\/ ' ' \\//\\\\   \\" , "        // '\\\\ ' ' //  \\\\___\\"
            , "         | ' ' ' ' ' ' |. .|" , "         | ' ' ' ' ' ' |. .|" , "         | ' ' '_' ' ' |. .|"
            , "         | ' ' / \\ ' ' |. .|" , "         |_'_'_|#|_'_'_|_.-'"
            , "************************************"};
            for (int i = 0; i < greet.length; ++i ) {
                try {
                    Thread.sleep(200);   
                    System.out.println(greet[i]);
                }
                catch (Exception e) {

                }
                // TimeUnit.SECONDS.sleep(1);
            }
            try {
                Thread.sleep(3000);  
            }
            catch (Exception e) {
                
            }
        
    }// end Greeting

    /*
     * Reads the users choice given from the keyboard
     * 
     * @int
     **/
    public static int readChoice() {
        int input;
        // returns only if a correct value is given.
        do {
            System.out.print("Please make your choice: ");
            try { // read the integer, parse it and break.
                input = Integer.parseInt(in.readLine());
                break;
            } catch (Exception e) {
                System.out.println("Your input is invalid!");
                continue;
            } // end try
        } while (true);
        return input;
    }// end readChoice

    public static void addCustomer(DBProject esql) {
        try {
            System.out.print("\tEnter customer's first name: ");
            String fname = in.readLine();
            System.out.print("\tEnter customer's last name: ");
            String lname = in.readLine();
            System.out.print("\tEnter customer's address :");
            String address = in.readLine();
            System.out.print("\tEnter customer's phone number: ");
            String phoneNum = in.readLine();
            System.out.print("\tEnter DOB \'YYYY-MM-DD\': ");
            String customerDOB = in.readLine();
            System.out.print("\tPlease choose gender (Male, Female, Other): ");
            String gender = in.readLine();

            String query = "INSERT INTO Customer (customerID, fName, lName, Address, phNo, DOB, gender)";
            query += "VALUES (\''";
            query += "\'', \''" + fname + "\'', \''" + lname + "\'', \''" + address + "\'', \''" + phoneNum + "\'', \''"
                    + customerDOB + "\'', \''" + gender + "\');'";

        } catch (Exception e) {

        }
        // Given customer details add the customer in the DB
        // Your code goes here.
        // ...
        // ...
    }// end addCustomer

    public static void addRoom(DBProject esql) {
        // Given room details add the room in the DB
        // Your code goes here.
        // ...
        // ...
    }// end addRoom

    public static void addMaintenanceCompany(DBProject esql) {
        // Given maintenance Company details add the maintenance company in the DB
        // ...
        // ...
    }// end addMaintenanceCompany

    public static void addRepair(DBProject esql) {
        // Given repair details add repair in the DB
        // Your code goes here.
        // ...
        // ...
    }// end addRepair

    public static void bookRoom(DBProject esql) {
        // Given hotelID, roomNo and customer Name create a booking in the DB
        // Your code goes here.
        // ...
        // ...
    }// end bookRoom

    public static void assignHouseCleaningToRoom(DBProject esql) {
        // Given Staff SSN, HotelID, roomNo Assign the staff to the room
        // Your code goes here.
        // ...
        // ...
    }// end assignHouseCleaningToRoom

    public static void repairRequest(DBProject esql) {
        // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request
        // in the DB
        // Your code goes here.
        // ...
        // ...
    }// end repairRequest

    public static void numberOfAvailableRooms(DBProject esql) {
        // Given a hotelID, get the count of rooms available
        // Your code goes here.
        try {
            String query = "SELECT COUNT(*) FROM Room R WHERE R.hotelID =";
            String query2 = " AND R.roomNo NOT IN (SELECT B.roomNo FROM Booking B WHERE R.hotelID = B.hotelID AND B.hotelID = ";
            System.out.print("\tEnter hotelID: ");
            String input = in.readLine();
            input = "\'" + input + "\'";
            query += input;
            query += query2;
            query += input;
            query += ");";
            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }// end numberOfAvailableRooms

    public static void numberOfBookedRooms(DBProject esql) {
        // Given a hotelID, get the count of rooms booked
        try {
            String query = "SELECT COUNT(*) FROM Booking B WHERE B.hotelID =";
            System.out.print("\tEnter hotelID: ");
            String input = in.readLine();
            input = "\'" + input + "\';";
            query += input;
            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end numberOfBookedRooms

    public static void listHotelRoomBookingsForAWeek(DBProject esql) {
        // Given a hotelID, date - list all the rooms available for a week(including the
        // input date)
        try {
            String query = "SELECT R.roomNo, R.roomType FROM Room R WHERE R.hotelID = ";
            System.out.print("\tEnter hotelID: ");
            String input = in.readLine();
            input = "\'" + input + "\'";
            query += input;

            String query2 = " AND R.roomNo NOT IN (SELECT B.roomNo FROM Booking B WHERE R.hotelID = B.hotelID AND B.bookingDate >= ";
            System.out.print("\tEnter date: ");
            String date = in.readLine();
            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
            date = "\'" + date + "\'";
            query += query2;
            query += date;
            int noOfDays = 7; // i.e two weeks
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
            Date dateend = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
            String dateendstr = dateFormat.format(dateend);
            dateendstr = "\'" + dateendstr + "\');";

            String query3 = " AND B.bookingDate <= ";
            query += query3;
            query += dateendstr;
            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end listHotelRoomBookingsForAWeek

    public static void topKHighestRoomPriceForADateRange(DBProject esql) {
        // List Top K Rooms with the highest price for a given date range
        try {
            String query = "SELECT B.price, R.roomNo, R.roomType FROM Booking B, Room R WHERE B.roomNo = R.roomNo AND B.bookingDate >= ";
            System.out.print("\tEnter Beginning Date: ");
            String input = in.readLine();
            input = "\'" + input + "\'";
            query += input;

            query += " AND B.bookingDate <= ";
            System.out.print("\tEnter Ending Date: ");
            String input2 = in.readLine();
            input2 = "\'" + input2 + "\'";
            query += input2;
            query += "ORDER BY B.price DESC LIMIT ";
            System.out.print("\tEnter number of results: ");
            String input3 = in.readLine();
            input3 = input3 + ";";
            query += input3;
            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end topKHighestRoomPriceForADateRange

    public static void topKHighestPriceBookingsForACustomer(DBProject esql) {
        // Given a customer Name, List Top K highest booking price for a customer
        try {
            String query = "SELECT B.price FROM Booking B, Customer C WHERE ";
            System.out.print("\tEnter Customer First Name: ");
            String input = in.readLine();
            input = "\'" + input + "\' = C.fname AND ";
            query += input;
            System.out.print("\tEnter Customer Last Name: ");
            String input2 = in.readLine();
            input2 = "\'" + input2 + "\' = C.lname AND B.customer = C.customerID ORDER BY B.price DESC LIMIT  ";
            query += input2;
            System.out.print("\tEnter Number of results: ");
            String input3 = in.readLine();
            query += input3 + ';';
            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end topKHighestPriceBookingsForACustomer

    public static void totalCostForCustomer(DBProject esql) {
        // Given a hotelID, customer Name and date range get the total cost incurred by
        // the customer
        try {
            String query = "SELECT sum(B.price) FROM Booking B, Room R, Customer C WHERE B.hotelID =  ";

            System.out.print("\tEnter Hotel ID: ");
            String input = in.readLine();
            input = "\'" + input + "\' AND B.customer = C.customerID AND C.fname = ";
            query += input;

            System.out.print("\tEnter Customer First Name: ");
            String input2 = in.readLine();
            input2 = "\'" + input2 + "\' AND C.lname = ";
            query += input2;

            System.out.print("\tEnter Customer Last Name: ");
            String input3 = in.readLine();
            input3 = "\'" + input3 + "\'";
            query += input3 + " AND B.bookingDate >= ";

            System.out.print("\tEnter Start Date: ");
            String input4 = in.readLine();
            input4 = "\'" + input4 + "\'";
            query += input4 + " AND B.bookingDate <= ";

            System.out.print("\tEnter End Date: ");
            String input5 = in.readLine();
            input5 = "\'" + input5 + "\';";
            query += input5;

            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end totalCostForCustomer

    public static void listRepairsMade(DBProject esql) {
        // Given a Maintenance company name list all the repairs along with repairType,
        // hotelID and roomNo
        try {
            String query = "SELECT R.repairType, R.hotelID, R.roomNo FROM Repair R, MaintenanceCompany M WHERE R.mcompany = M.cmpID AND M.name = ";

            System.out.print("\tEnter Maintenance Company Name: ");
            String input = in.readLine();
            input = "\'" + input + "\';";
            query += input;

            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end listRepairsMade

    public static void topKMaintenanceCompany(DBProject esql) {
        // List Top K Maintenance Company Names based on total repair count (descending
        // order)
        try {
            String query = "SELECT M2.name, M.count FROM (SELECT R1.mCompany as id, COUNT(*) as count FROM Repair R1 GROUP BY R1.mCompany) as M, MaintenanceCompany M2 WHERE M2.cmpID = M.id ORDER BY M.count DESC LIMIT";

            System.out.print("\tEnter number of results: ");
            String input = in.readLine();
            input = "\'" + input + "\';";
            query += input;

            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end topKMaintenanceCompany

    public static void numberOfRepairsForEachRoomPerYear(DBProject esql) {
        // Given a hotelID, roomNo, get the count of repairs per year
        try {
            String query = "SELECT extract(year from R.repairDate) as ryear, count(*) FROM Repair R WHERE R.roomNo = ";

            System.out.print("\tEnter Room number: ");
            String input = in.readLine();
            input = "\'" + input + "\' AND R.hotelID = ";
            query += input;

            System.out.print("\tEnter Hotel ID: ");
            String input2 = in.readLine();
            input2 = "\'" + input2 + "\' GROUP BY ryear;";
            query += input2;

            int rowCount = esql.executeQuery(query);
            System.out.println("total row(s): " + rowCount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// end listRepairsMade

}// end DBProject
