package ir.crawler.parser.data;

import com.sun.syndication.feed.synd.SyndEntry;

public interface EntryData {

	public abstract void parse_entry(SyndEntry entry);
}
