package ir.crawler.parser.feed;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class FeedGenerator {
	
	SyndFeed feed;
	private static final DateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");
	List<SyndEntry> entries = new ArrayList<SyndEntry>();
	
	public FeedGenerator(String title, String link, String desc){
		
		feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		
		feed.setTitle(title);
		feed.setLink(link);
		feed.setDescription(desc);
	}
	
	public void addEntry(String title, String link, String date, String desc){
		SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(title);
        entry.setLink(link);
        try {
			entry.setPublishedDate(DATE_PARSER.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        SyndContent description = new SyndContentImpl();
        description.setType("text/plain");
        description.setValue(desc);
        entry.setDescription(description);
        entries.add(entry);
	}
	
	public void writeFeed(String filename){
		feed.setEntries(entries);

		try {
			Writer writer = new FileWriter(filename);
		
	        SyndFeedOutput output = new SyndFeedOutput();
	        output.output(feed,writer);
	        writer.close();
		} catch (IOException e) {
			System.out.println("b");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			System.out.println("a");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("c");
			e.printStackTrace();
		}
	}
	
	
}
