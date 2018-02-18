import org.jsoup.Jsoup;

import java.io.IOException;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
 
import org.json.simple.JSONObject;

public class HTMLtoJSON {
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		
		FileWriter file = new 
FileWriter("/Users/snowf/Desktop/ProjSearch/imdb.json", true); // , true    
for append
		
		int count = 0;			
									
		for(int a = 1; a <= 9000000; ++a) { 
		
		try {
			String Title = "", Year = "", Rating = "", Image 
= "", Summary = "";
			String Genre1 = "";
			String Genre = "";
			String Actors1 = "";
			String Actors = "";
		
			//String url = 
"https://www.imdb.com/title/tt9990586";
		
			String url = "https://www.imdb.com/title/tt" + 
String.format("%07d", a); // from 0000001 to 9000000
			
			Document doc = Jsoup.connect(url).get();
		
			Elements titleYear = doc.select("h1");					
// gets title and year "title ( year )"
			Elements genre = 
doc.select("div[class=subtext]").select("a[href]");	// gets genre in 
array but also gets date released
			Elements rating = 
doc.select("span[itemprop=ratingValue]");				
// gets rating out of 10
		
			Elements summary = 
doc.select("div[class=summary_text]");				// gets 
movie summary
		
			Elements stars = 
doc.select("div[class=credit_summary_item]").select("a[href]"); // gets 
cast and crew
		
			Elements imgurl = 
doc.select("div[class=poster]").select("img[alt]");
		
			System.out.println(url);
		
			for (Element link : titleYear) {
		
				String[] temp = 
link.text().split("\\(");
				
				Title = temp[0];	
				Title = Title.substring(0, 
Title.length() - 1); // Title is now assigned 
			
				String[] temp2 = temp[1].split("\\)");
			
				Year = temp2[0];								
// Year is now assigned
			
				System.out.println(Title);
				System.out.println(Year);
			}
		
			for (Element link : genre) {
			
				Genre1 += link.text() + " , ";
			}
				loop: for(int i = 0; i < 
Genre1.length(); ++i) {
				
					for(int j = 1; j <= 9; ++j) {
						String temp = "" + 
Genre1.charAt(i);
					
						try{
							
if(Integer.parseInt(temp) == j) {
								int 
tempSize = i - 3;
								Genre = 
Genre1.substring(0,tempSize);			// Genre is now assigned
								break 
loop;
							}
						}catch(Exception e) {}
					}	
				}
				System.out.println(Genre);
			
			for (Element link : rating) {
				
				Rating = link.text();											
// Rating is now assignee	
				System.out.println(Rating);
		    
			}
			for (Element link : summary) {
				
				Summary = link.text();											
// Summary is now assigned
				System.out.println(Summary);		
			}
			
			if(stars != null) {
				for (Element link : stars) {		
					
					Actors1 += link.text() + ", ";
				}
			}
			
			//System.out.println(Actors1);
			String[] temp = Actors1.split("\\,");
			
			for(int i = 0; i < temp.length - 2; ++i) {
				
				Actors += temp[i] + " ,";
			}
			
			if( Actors.length() > 0) {
				
			
				Actors = Actors.substring(0, 
Actors.length() - 1);					// Actors is set
				System.out.println(Actors);
			
			}
				
			for (Element link : imgurl) {
				
				Image = link.attr("abs:src");
				
				System.out.println(Image);					
// image src
	            //print(" * a: <%s>  (%s)", link.attr("abs:href"), 
trim(link.text(), 35));
			
			}
			
			
			
			 JSONObject obj = new JSONObject();
			 
			 obj.put("title", Title);
			 obj.put("year", Year);
			 obj.put("rating", Rating);
			 obj.put("url", url);
			 obj.put("image", Image);
			 obj.put("actors", Actors);
			 obj.put("genre", Genre);
			 obj.put("summary", Summary);
			 
			 String header = 
"{\"index\":{\"_index\":\"imdb\",\"_id\":" + count++ + "}}";
					 
//{"index":{"_index":"shakespeare","_id":0}}
			 
			 try {
				 file.write(header + "\n");
				 file.write(obj.toJSONString());
				 file.write("\n");
			 }catch(Exception e) {}
			 
//			 for(int i = 1; i <= 9000000; i++) {
//			      String formatted = String.format("%06d", 
i);
//			      System.out.println(formatted);
//			    }

		}catch(Exception e) {
			System.out.println("Error 404");
		}
		
			file.flush();
		}
		
		 	
		
		 	file.close();

		 	
	}
		

}

