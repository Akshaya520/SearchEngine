package features;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import utilities.TST;

public class RankPages {
	
	private static int findNoOfInstances(Path path, String word) {
		
		int occurances;
		TST<Integer> tst = new TST<Integer>();
		List<String> fileLines = null;
		try {
			fileLines = Files.readAllLines(path, StandardCharsets.ISO_8859_1); 
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String str : Objects.requireNonNull(fileLines)) {

			StringTokenizer st = new StringTokenizer(str);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (tst.get(token) == null) {
					tst.put(token, 1);
				} else {
					tst.put(token, tst.get(token) + 1);
				}
			}
		}

		if (tst.get(word) != null)
			occurances = tst.get(word);
		else
			occurances = 0;

		return occurances;

	}

	public static void searchInFile() {
		
		File dir = new File("TextFiles/");
		Scanner s = new Scanner(System.in);
		String restart;
		String[] fileNames = dir.list();
		TreeMap<Integer, String> occurances = new TreeMap<Integer, String>(Collections.reverseOrder());

		do {
			System.out.println("Page Ranking - Search word: ");
			String word = s.nextLine(); 
			for (String fileName : Objects.requireNonNull(fileNames)) {

				String file = "TextFiles/" + fileName;
				File currfile = new File(file);
				if (currfile.exists() && currfile.isFile() && currfile.canRead()) {
					Path path = Paths.get(file);

					occurances.put(findNoOfInstances(path, word), path.getFileName().toString());
					
				}
			}
			
			Set<?> set = occurances.entrySet();
		    Iterator<?> i = set.iterator();
		     while(i.hasNext()) {
		        Map.Entry me = (Map.Entry)i.next();
		        System.out.println("The total number of occurrences of '" + word + "' in '" + me.getValue()
				+ "' is " + me.getKey());
		      }
			System.out.println("Do you want to search another word? Y/N");
			restart = s.nextLine();
		} while (restart.equals("y") || restart.equals("Y"));

		if (restart.equals("N") || restart.equals("n"))
			System.out.println("Search done.");
		

	}

	

}
