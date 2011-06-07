package ir.crawler.parser.feed;

import ir.crawler.parser.data.EntryData;

public interface IFeedReader {

	public abstract EntryData parse() throws Exception;

}