package features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import utilities.In;

public class CacheOperations {
	
	public static void addToCache(String cache) throws IOException {

		File cacheFile = new File("Cache.txt");
		if(!cacheFile.exists()){
			cacheFile.createNewFile();
		}
		BufferedWriter write = new BufferedWriter(new FileWriter(cacheFile, true));
		write.append(cache);
		write.newLine();
		write.flush();
		write.close();
	}
	
	public static void deleteCache() throws IOException {
		File cacheFile = new File("Cache.txt");
	
		if (cacheFile.exists()) {
			cacheFile.delete();
			System.out.println("The cache file has been deleted. ");
		} else {
			System.out.println("Failed to delete the file.");
		}
		File file = new File("TextFiles//");
		if(file.exists()) {
		FileUtils.cleanDirectory(file);
		file.delete();
		}
		
		System.out.println("Cache has been sucesfully deleted");
	}
	
	public static Boolean isURLInCache(String Url) {
		Boolean flag = false;
		try {
			In in = new In("Cache.txt");

			while (!in.isEmpty()) {
				String str = in.readLine();
				String strArray[]= str.split(" ");
				if (strArray[0].equals(Url))
					flag = true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

}
