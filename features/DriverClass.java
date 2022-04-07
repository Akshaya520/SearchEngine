package features;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DriverClass {
	
	static AutoComplete corrector;
	static String word;
	
	private static boolean isUrlValid(String url) {
		if (Pattern.matches(WebCrawler.URL_REGEX, url))
			return true;
		return false;
	}

	public static void main(String[] args) throws IOException {
		
		while (true) {
			System.out.println("Select a search engine feature listed below.");
			System.out.println("----------------------------------------------\n");
			System.out.println("1. Search a url");
			System.out.println("2. Delete cache");
			System.out.println("3. Rank the web pages according to the occurance of a word");
			System.out.println("4. Words Suggestion");
			System.out.println("5. AutoComplete");
			System.out.println("6. Exit from program\n");
			
			System.out.println("Please enter your choice");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			sc = new Scanner(System.in);
			switch (choice) {
				case 1:
					System.out.println("URL to be searched");
					String url = sc.nextLine();
					if (isUrlValid(url)) {
						if (!CacheOperations.isURLInCache(url)) {
							WebCrawler.crawlWeb(1, url, new ArrayList<String>());
	
						} else {
							System.out.println("This URL has already been crawled.");
						}
	
					} else {
						System.out.println("Please enter a valid url");
					}
	
					break;
	
				case 2:
					CacheOperations.deleteCache();
					break;
	
				case 3:
					System.out.println("Page Ranking - Search word: ");
					RankPages.searchInFile();
					break;
	
				case 4:
					System.out.println("Words Suggestion - Search word: ");
					word = sc.nextLine();
					corrector = new AutoComplete();
	
					corrector.loadSpellCorrector();
					String identicalWord = corrector.findIdenticalWord(word);
					if (identicalWord.length() == 0)
						System.out.println("There are no similar words. Please enter the valid word to search");
					else
						System.out.println("Identical suggestion found: " + identicalWord);
	
					break;
	
				case 5:
					System.out.println("Auto Complete - Enter word: ");
					word = sc.nextLine();
					corrector = new AutoComplete();
	
					corrector.loadSpellCorrector();
					ArrayList<String> autoCompleteStr = corrector.autoComplete(word);
					System.out.println(autoCompleteStr.toString());
					break;
					
				case 6:
					System.out.println("Exiting program.");
					break;
	
				default:
					System.out.println("Please select a valid number");
					break;
				}
			sc.close();
		}
		
		


	}

}
