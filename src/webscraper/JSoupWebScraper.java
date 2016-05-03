package webscraper;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupWebScraper {

	public static void main(String[] args) {

		Document doc;
		try {
			DB DB = new DB();
			doc = Jsoup.connect("http://portforward.com/cports.htm").get();

			Element table = doc.select("table").get(0); // select the first
														// table.
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filename.txt")))) {
				writer.write(table.toString());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Elements rows = table.select("tr");
			System.out.println(rows.size());
			System.out.println(table.childNodeSize());

			int appID = 0;

			for (int i = 1; i < rows.size(); i++) { // first row is the col
													// names so skip it.
				Element row = rows.get(i);
				Elements cols = row.select("td");
				String sql = "";
				for (int j = 0; j < cols.size(); j++) {
					String str = cols.get(j).text().trim();
					if (j > 0) {
						String[] token = str.split(",[\\s]");
						for (String tok : token) {
							tok = tok.trim();
							if (j == 1) {
								sql = "INSERT INTO TCP(tcp_port,app_id) VALUES (?,?);";
							} else if (j == 2) {
								sql = "INSERT INTO UDP(udp_port,app_id) VALUES (?,?);";
							}
							PreparedStatement pstmt = DB.conn.prepareStatement(sql);
							pstmt.setString(1, tok);
							pstmt.setInt(2, appID);
							pstmt.executeQuery();
						}
					} else {
						sql = "INSERT INTO Application(app_name) VALUES (?);";
						PreparedStatement pstmt = DB.conn.prepareStatement(sql);
						pstmt.setString(1, str);
						pstmt.executeQuery();
					}
					PreparedStatement pstmt = DB.conn
							.prepareStatement("SELECT app_id FROM Application WHERE app_name=?;");
					pstmt.setString(1, str);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						appID = rs.getInt(1);
					}
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

	}

}
