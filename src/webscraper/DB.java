package webscraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

	public Connection conn;
	private String url = "<URL>"; // editted out DB Credentials for privacy reasons
	private String user = "<USER>"; // editted out DB Credentials for privacy reasons
	private String pass = "<PASSWORD>"; //editted out DB Credentials for privacy reasons

	public DB() throws SQLException {
		conn = DriverManager.getConnection(url, user, pass);
	}

	public void closeDB() throws SQLException {
		this.conn.close();
		return;
	}

}
