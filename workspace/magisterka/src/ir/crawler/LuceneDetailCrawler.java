package ir.crawler;

import ir.analyzer.Writer;
import ir.crawler.parser.html.PageParser;
import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class LuceneDetailCrawler extends Thread{
	
	List<Container> container;
	List<Container> container_fail;
	Writer writer;
	int number;
	
	public LuceneDetailCrawler(List<Container> container, int number) throws IOException {
		this.container = container;
		this.writer = Writer.instance();
		this.number = number;
	}
	
	
	@Override
	public void run() {
		int c = 0;
		container_fail = new ArrayList<Container>();
		//System.out.println("A");
		while(true){
			try {
				System.out.println("thread: " + number + " LOOP: " + c);
				c++;
				crawl();
				if (c == 100) break;
				if (container_fail.size() != 0){
					container = container_fail;
					container_fail = new ArrayList<Container>();
					
				}
				else {
					break;
				}
				//sleep(WAIT_TIME);
			} catch (Exception e) {
				System.out.println("except: " + number + " LOOP: " + c);
				//e.printStackTrace();
			}
		}
	}

	public String fetch_target_page(String url) throws IOException{
		PageParser pp = new PageParser(url);
		String clean_page = pp.get_cleaned_page();
		return clean_page;
	}
	
	
	public void update_database(long id){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, id);
		doc.setPage_fetch(true);
		session.save(doc);
		tx.commit();
		session.close();
	}
	
	
	private void crawl() {
		System.out.println(container.size());
		for (Container c : container){
			try{
				System.out.println("thread: " + number + " download page: " + c.url);
				String page = fetch_target_page(c.url);
				writer.addDocument(page, c.id);
				System.out.println("thread: " + number + " saving database: " + c.id);
				update_database(c.id);
				
			}catch (Exception e) {
				e.printStackTrace();
				container_fail.add(c);
			}
			
		}
		
	}
	
	
}
