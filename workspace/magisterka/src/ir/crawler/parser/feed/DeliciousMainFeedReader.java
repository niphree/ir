package ir.crawler.parser.feed;

import ir.crawler.parser.data.DeliciousMainData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;

public class DeliciousMainFeedReader extends FeedReader {

	public DeliciousMainFeedReader(File file) throws IllegalArgumentException,
			FeedException, IOException {
		super(file);
	}
	public DeliciousMainFeedReader(URL url) throws IllegalArgumentException,
			FeedException, IOException {
		super(url);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeliciousMainData parse() {
		List<SyndEntry> entries = feed.getEntries();
		
		DeliciousMainData data = new DeliciousMainData();
		
		
		for (SyndEntry entry: entries){
			data.parse_entry(entry);
			}
		
		return data;
	}
	

}
