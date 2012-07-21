package ir.crawler;

import ir.crawler.parser.json.ReadJSON;
import ir.crawler.saver.JSONDataBaseSaver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FileReader {

	public String read_file(String filename){
		boolean exists = (new File(filename)).exists();
		String txt  = null;
		if (!exists) {
			System.out.println("a");
			return null;
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fis);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  txt = "";
			  while ((strLine = br.readLine()) != null) {
			  // Print the content on the console
				  txt= txt + " " + strLine;
			  }
			  //Close the input stream
			  in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return txt;
	}
	
	public List<Object[]> read_json(){
		ReadJSON r = new ReadJSON();
		List<Object[]> json = r.getJSON_test_data(read_file("data_biol.txt"));

		return json;
		
	}
	
	public double get_gauss(){
		Random r = new Random();
		double g = (r.nextGaussian() + 2.5)/5;
		return g;
		
	}
	public Integer get_gauss(int max){
		
		double r = get_gauss();
		while(r<=0 || r > 1){
			r = get_gauss();
		}
		int random = (int)(r * max);
		random++;
		if (random==max)random--;
		return random;
		
	}
	//wypelnia liste wielkosci count losowymi liczbami z przedzialu 1-max
	public List<Integer> random(int range, int list_lenght){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i<list_lenght; i++){
			int rand = get_gauss(range);
			while (list.contains(rand))
				rand = get_gauss(range);
			list.add(rand);
		}
		return list;
	}


	public List<String> parse_tags(String tag_str){
		String[] t = tag_str.split("[:;,\\s]");
		
		
		//System.out.println(Arrays.toString(t));
		List<String> tags = new ArrayList<String>();
		
		for (String elem:t){
			if(elem.equals("") )
				continue;
			elem = elem.replaceAll("[()=]", "");
			elem = elem.toLowerCase();
			//System.out.println(elem);
			tags.add(elem);
		}
		/*for(String s: tags){
			System.out.println("!");
			System.out.println(s);
		}*/
		return tags;
	}
	              
	
	
	public List<Integer> random_list(int range, int list_length){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i<list_length; i++){
			int rand = get_gauss(range);
			list.add(rand);
		}
		return list;
	}
	
	public List<?>[] get_tag_usr_list(int tags, int users, int user_range){
		//System.out.println("a");
		//dla wszystkich tagow wybierz ile uzytkownikow go dodalo
		List<Integer> tags_list = random_list(users, tags);
		//System.out.println("a");
		//ze wszystkich uzytkownikow wybierz doc_user ktorzy zostana dodanie
		//System.out.println("range "+user_range);
		//System.out.println("users "+users);
		List<Integer> user_list = random(user_range, users);
		//System.out.println("a");
		List<List<Integer>> tag_user_list = new ArrayList<List<Integer>>();
		//System.out.println("user list");
		//System.out.println(user_list);
		//System.out.println("a");
		for (int i : tags_list){
			//wybierz i uzytkownikow dla kazdego taga
			List<Integer> rands_users = random(users, i);
			List<Integer> temp_users = new ArrayList<Integer>();
			//System.out.println("user list"+user_list);
			//System.out.println("rand list"+rands_users);
			for (int j: rands_users){
				//System.out.println(j);
				
			//	System.out.println(temp_users);
				temp_users.add(user_list.get(j-1));
			}
			tag_user_list.add(temp_users);
			
		}
		List<?>[] tab = new List[2];
		tab[0] = tag_user_list;
		tab[1] = user_list;
		//System.out.println("a");
		return tab;
		
		
	}
	public Map<Integer, List<Integer>> get_users(List<List<Integer>> tag_usr_list, List<Integer> user_list){
		//System.out.println(tag_usr_list);
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (Integer user: user_list){
			List<Integer> temp = new ArrayList<Integer>();
			map.put(user, temp);
			
		}
		
		int i =0;
		for (List<Integer> list : tag_usr_list){
			for (Integer elem: list){
				List<Integer> tmp = map.get(elem);
				tmp.add(i);
			}
			i++;
		}
	//	System.out.println(map);
		for(Integer user :map.keySet()){
			if (map.get(user).isEmpty()){
				Random rand = new Random();
				int r = rand.nextInt(tag_usr_list.size()-1)+1;
			//	System.out.println("!!!!!!!!!!!");
				List<Integer> tmp = random(tag_usr_list.size(), r);
			//	System.out.println("!!!!!!!!!!!");
			//	System.out.println(tmp);
				map.put(user, tmp);
			}
		}
		
		return map;
	}
	public void read(){
		List<Object[]> json = read_json();
		process_json(json);
		
	}
	@SuppressWarnings("unchecked")
	public void process_json(List<Object[]>  json_struct){
		int i =0;
		Random r = new Random(10);
		for (Object[] struct : json_struct){
			i++;
			String doc_name = (String)struct[0];
			String tags_ = (String)struct[1];
			Integer users = (Integer)struct[2];
			if (users == 0)
				users=r.nextInt(15)+1;
			List<String> tags = parse_tags(tags_);
			List<?>[] tag_user_array = get_tag_usr_list(tags.size(), users, 200);
			List<List<Integer>> tag_usr= (List<List<Integer>>)tag_user_array[0];
			List<Integer> usr_list= (List<Integer> )tag_user_array[1];
			
			Map<Integer, List<Integer>> users_tag = get_users(tag_usr, usr_list);
			JSONDataBaseSaver saver = new JSONDataBaseSaver(doc_name, tags, users_tag);
			saver.save_to_db();
			
			
		}
	}
	public static void main(String[] args) {
		FileReader fr = new FileReader();
		fr.read();
		
		//List<?>[] ob = fr.get_tag_usr_list(2, 10, 20);
		//
		//System.out.println(m);

		
	}
}
