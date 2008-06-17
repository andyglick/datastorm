import java.sql.Connection;
import java.sql.DriverManager;

import org.datastorm.DataStorm;

/**
 * Simple roundtrip test connecting to a mysql db
 * 
 * @author Kasper B. Graversen, (c) 2007-2008
 */
public class DataStormMySql {
public static void main(String[] args) throws Exception {
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mysql", "root", "admin");
	new DataStorm().show(conn);
	new DataStorm().show(conn, "SELECT * FROM help_topic h;");
}

}
