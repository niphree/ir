package ir.crawler.parser.feed;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public abstract class FeedReader implements IFeedReader {
	
	String url;
	SyndFeed feed;
	
	Boolean test = false;
	
	public FeedReader(URL url) throws IllegalArgumentException, FeedException, IOException {
		this.url = url.getPath();
		SyndFeedInput input = new SyndFeedInput();
		feed = input.build(new XmlReader(url)); 
	}
	
	/**
	 * for test only
	 * @param file
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 * @throws IOException
	 */
	public FeedReader(File file) throws IllegalArgumentException, FeedException, IOException {
		url = "";
		SyndFeedInput input = new SyndFeedInput();
		feed = input.build(new XmlReader(file)); 
		test = true;
		
	}
	
}
