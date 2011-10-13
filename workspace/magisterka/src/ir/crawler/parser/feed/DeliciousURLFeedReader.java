package ir.crawler.parser.feed;

import ir.crawler.parser.data.DeliciousDocumentData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;

public class DeliciousURLFeedReader extends FeedReader {

	public DeliciousURLFeedReader(File file) throws IllegalArgumentException, FeedException, IOException {
		super(file);
	}

	public DeliciousURLFeedReader(URL url) throws IllegalArgumentException, FeedException, IOException {
		super(url);
	}
	
	
	/* (non-Javadoc)
	 * @see ir.crawler.parser.feed.FeedReader#parse()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public DeliciousDocumentData parse() throws FeedException, IOException{
		List<SyndEntry> entries = feed.getEntries();
		
		if (entries.size() == 0){
			return null;
		}
		String url = entries.get(0).getLink();
		if (url == null)
			return null;
		if (url.length() > 255)
			return null;
		DeliciousDocumentData data = new DeliciousDocumentData(url);
		/*try {
			// this is ugly, just for test purpose
			System.out.println("download page:");
			if (!test) data.fetch_target_page(); 
		} catch (IOException e) {
			throw e;
		}*/
		//System.out.println("working with doc details (tag - usr), data size: " + entries.size());
		for (SyndEntry entry: entries){
			data.parse_entry(entry);
			}
		
		return data;
	}
	
}
