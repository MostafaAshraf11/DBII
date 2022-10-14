import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Schema3 {

    // CREATE TABLE Sailors(sid INT PRIMARY KEY, sname CHAR(20), rating INT, age
    // REAL);

    public static long insertSailor(int ID, String Name, int rating, double age, Connection conn) {
        String SQL = "INSERT INTO Sailors(sid,sname,rating,age) "
                + "VALUES(?,?,?,?);";

        long id = 0;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, ID);
            pstmt.setString(2, Name);
            pstmt.setInt(3, rating);
            pstmt.setDouble(4, age);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Number of affected rows is " + affectedRows);
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    // System.out.println(rs.next());
                    if (rs.next()) {
                        id = rs.getLong(1);
                        pstmt.close();
                        conn.commit();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }

    // CREATE TABLE Boat(bid INT PRIMARY KEY, bname CHAR(20), color CHAR(10));
    public static long insertBoat(int ID, String Name, String color, Connection conn) {
        String SQL = "INSERT INTO Boat(bid,bname,color) "
                + "VALUES(?,?,?);";

        long id = 0;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, color);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Number of affected rows is " + affectedRows);
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    // System.out.println(rs.next());
                    if (rs.next()) {
                        id = rs.getLong(1);
                        pstmt.close();
                        conn.commit();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }

    // CREATE TABLE Reserves(sid INT REFERENCES Sailors, bid INT REFERENCES Boat,
    // day date, PRIMARY KEY(sid,bid));
    public static long insertReserves(int sID, int bID, Date day, Connection conn) {
        String SQL = "INSERT INTO Reserves(sid,bid,day) "
                + "VALUES(?,?,?);";

        long id = 0;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, sID);
            pstmt.setInt(2, bID);
            pstmt.setDate(3, day);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Number of affected rows is " + affectedRows);
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    // System.out.println(rs.next());
                    if (rs.next()) {
                        id = rs.getLong(1);
                        pstmt.close();
                        conn.commit();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }

    ///////////////////////////////////////////////////////// Data Population
    ///////////////////////////////////////////////////////// Methods
    ///////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
    public static void populateSailor(Connection conn) {
        for (int i = 1; i <= 19000; i++) {
            if (insertSailor(i, "Sailor" + i, (int) (Math.random() * 10) + 1, (int) (Math.random() * 50) + 20,
                    conn) == 0) {
                System.err.println("insertion of record " + i + " failed");
                break;
            } else
                System.out.println("insertion was successful");
        }
    }

    public static void populateBoat(Connection conn) {
        for (int i = 1; i <= 3000; i++) {
            if (i % 3 == 0) {
                if (insertBoat(i, "Boat" + i, "red", conn) == 0) {
                    System.err.println("insertion of record " + i + " failed");
                    break;
                } else
                    System.out.println("insertion was successful");
            } else {
                if (i % 3 == 1) {
                    if (insertBoat(i, "Boat" + i, "green", conn) == 0) {
                        System.err.println("insertion of record " + i + " failed");
                        break;
                    } else
                        System.out.println("insertion was successful");
                } else {
                    if (i % 3 == 2) {
                        if (insertBoat(i, "Boat" + i, "blue", conn) == 0) {
                            System.err.println("insertion of record " + i + " failed");
                            break;
                        } else
                            System.out.println("insertion was successful");
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void populateReserves(Connection conn) {
        int j = 1;
        int z = 1;
        Random random = new Random();
        for (int i = 1; i <= 34199; i++) {
            if (insertReserves(z, j, new Date(random.nextInt(28 + 1 - 1) + 1, random.nextInt(12 + 1 - 1) + 1,
                    random.nextInt(2020 + 1 - 2000) + 2000), conn) == 0) {

                System.err.println("insertion of record " + i + " failed");
                break;
            } else {
                j++;
                if (j > 3000)
                    j = 1;
                if (i % 2 == 1)
                    z += 2;
                if (z > 19000)
                    z = 2;
                System.out.println("insertion was successful");
            }
        }
        for (int i = 34200; i <=35000; i++) {
            if (insertReserves(z, 103, new Date(random.nextInt(28 + 1 - 1) + 1, random.nextInt(12 + 1 - 1) + 1,
                    random.nextInt(2020 + 1 - 2000) + 2000), conn) == 0) {

                System.err.println("insertion of record " + i + " failed");
                break;
            } else {
                
                if (i % 2 == 1)
                    z += 2;
                if (z > 19000)
                    z = 2;
                System.out.println("insertion was successful");
            }
        }    }

    public static void insertSchema3(Connection connection) {
        populateSailor(connection);
        populateBoat(connection);
        populateReserves(connection);
    }

    public static void main(String[] argv) {

        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5433/schema3", "postgres",
                    "wowowo123");

            insertSchema3(connection);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
