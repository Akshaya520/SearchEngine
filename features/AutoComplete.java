package features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import utilities.*;

public class AutoComplete {
	
	private static final String DIR_PATH = "TextFiles/";

	Trie trie = new Trie();
	Map<String, Integer> wordFrequencyDict = new HashMap<>();
	
	
	 public static boolean isAlpha(String word) {
		 
		 return ((word != null) && (!word.equals("")) && (word.matches("^[a-zA-Z]*$")));
	 }
	
	public void saveEntryInDict(File file) throws IOException {

		try {
			String line = null;
			BufferedReader write = new BufferedReader(new FileReader(file));
			

			while ((line = write.readLine()) != null) {
				String word = line.toLowerCase();
				if (!line.contains(" ")) {
					word = word.toLowerCase();
					if(isAlpha(word)){
						wordFrequencyDict.put(word, wordFrequencyDict.getOrDefault(word, 0) + 1);
						trie.addWord(word);
					}
				} else {
					String[] wordArray = line.split("\\s");
					
					for (String str : wordArray) {
						str = str.toLowerCase();
						if(isAlpha(str))
						{
							wordFrequencyDict.put(str, wordFrequencyDict.getOrDefault(str, 0) + 1);
							trie.addWord(str);
						}
					}
				}
			}
			write.close();
		} catch (Exception e) {
			System.out.println("Exception in word frequency count block 2");
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void loadSpellCorrector() {
		File[] files = new File(DIR_PATH).listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				try {
					saveEntryInDict(files[i]);
				} catch (IOException e) {
					System.out.println("Exception in word frequency count block 2");
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String findIdenticalWord(String str) {
		String result = "";
		
		if(str.length()==0 || str==null ) {
			return result;
		}
		str = str.toLowerCase();
			
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();	
		TrieNode node = trie.search(str);
		
		if(node == null) {
			for (String word: wordFrequencyDict.keySet()) {
				int distance = Sequences.editDistance(word, str);				
				TreeMap<Integer, TreeSet<String>> identicalWords = map.getOrDefault(distance, new TreeMap<>());
				
				int iFrequency = wordFrequencyDict.get(word);
				TreeSet<String> set = identicalWords.getOrDefault(iFrequency, new TreeSet<>());
				set.add(word);			
				identicalWords.put(iFrequency, set);
				map.put(distance, identicalWords);		
			}		
			
			result = map.firstEntry().getValue().lastEntry().getValue().first();
		} 
		else if (node !=null) 
			result = str;
			 
		return result;
	}

	public ArrayList<String> autoComplete(String str) {
		ArrayList<String> arr = new ArrayList<String>();

		if (str.length() == 0 || str == null) {
			return arr;
		}

		str = str.toLowerCase();
		

		TrieNode node = trie.search(str);

		if (node == null) {
			for (String word : wordFrequencyDict.keySet()) {
				if(!word.isEmpty()){
					if(word.startsWith(str)){
						arr.add(word);
					}
				}
			}
		}

		return arr;

	}

	

}
