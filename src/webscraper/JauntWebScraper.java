package webscraper;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class JauntWebScraper {

	public static void main(String[] args) {
		
		try{
			  UserAgent userAgent = new UserAgent();
			  userAgent.visit("http://portforward.com/cports.htm");
			   
			  Element table = userAgent.doc.findFirst("<table>");  //find table element
			  Elements tds = table.findEach("<td>");                         //find non-nested td/th elements
			  for(Element td: tds){                                             //iterate through td/th's
				  Element anchor = userAgent.doc.findFirst("<a href>");            //find 1st anchor element with href attribute
				  System.out.println("anchor element: " + anchor);                 //print the anchor element
				  System.out.println("anchor's tagname: " + anchor.getName());     //print the anchor's tagname
				  System.out.println("anchor's href attribute: " + anchor.getAt("href"));  //print the anchor's href attribute
				  System.out.println("anchor's parent Element: " + anchor.getParent());
				  System.out.println(td.outerHTML());                             //print each td/th element
			  }
			}
			catch(JauntException e){
			  System.err.println(e);
			}
	}

}
// http://portforward.com/cports.htm