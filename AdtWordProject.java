import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class AdtWordProject {

	int k;
	LinkedList<WordInformation>[] arrayOfDifferentLengths;

	WordInformation[] sortedArray;

	int wordCount, wordCountUnique;

	public AdtWordProject() {
		k = 20;
		arrayOfDifferentLengths = new LinkedList[k];
		wordCount = 0;
	}

	void readFileAndAnalyse(String filename) {
		try {
			Scanner fileScanner = new Scanner(new File(filename));
			int lineNo = 0;
			while (fileScanner.hasNextLine()) {
				lineNo++;
				String line = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				int colNo = 0;
				while (lineScanner.hasNext()) {
					colNo++;
					String token = lineScanner.next();
					token = token.replaceAll("\\p{Punct}", "").toLowerCase();
					wordCount++;
					int len = token.length();
					WordInformation a = new WordInformation(token, lineNo, colNo);
					if (arrayOfDifferentLengths[len-1] == null) {
						arrayOfDifferentLengths[len-1] = new LinkedList<>();
					}
					WordInformation wInfo = arrayOfDifferentLengths[len-1].findNode(a);
					if (wInfo != null) {
						wInfo.size++;
						wInfo.occList.addLast(new WordOccurrence(lineNo, colNo));
					} else {
						arrayOfDifferentLengths[len-1].addFirst(a);
						wordCountUnique++;
					}
				}
				lineScanner.close();
				// you're at the end of the line here. Do what you have to do.
			}
			fileScanner.close();
			// Let's sort it.
			sortedArray = new WordInformation[wordCountUnique];
			int count = 0;
			for (int i = 0; i < k; i++) {
				if (arrayOfDifferentLengths[i] != null) {
					LinkedList<WordInformation> tmpLinkList = arrayOfDifferentLengths[i];
					tmpLinkList.reset();
					while (tmpLinkList.hasNext()) {
						WordInformation tmp = tmpLinkList.getCurrentData();
						sortedArray[count++] = tmp;
						tmpLinkList.next();
					}
				}
			}
			Arrays.sort(sortedArray, new Comparator<WordInformation>() {
				@Override
				public int compare(WordInformation second, WordInformation first)
				{
					if (first.getSize() != second.getSize()) {
						return first.getSize() - second.getSize();
					}
					return first.getWord().length() - (second.getWord().length());
				}
			});
		} catch (IOException ex) {
			// file not found
		}
	}
	
	int documentLength() {
		return wordCount;
	}
	
	int uniqueWords() {
		return wordCountUnique;
	}
	
	int totalOccurrencesForWord(String w) {
		int totalOcc = 0;
		if (w != null && !w.isEmpty()) {
			WordInformation wInfo = new WordInformation(w);
			WordInformation found = arrayOfDifferentLengths[w.length() - 1].findNode(wInfo);
			totalOcc = found.size;
		}
		return totalOcc;
	}

	int totalWordsForLength(int l) {
		LinkedList<WordInformation> linkList = arrayOfDifferentLengths[l-1];
		linkList.reset();
		int count = 0;
		while(linkList.hasNext()) {
			WordInformation wInfo = linkList.getCurrentData();
			count += wInfo.size;
			linkList.next();
		}
		return count;
	}
	
	String displayUniqueWords() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wordCountUnique; i++) {
			sb.append(sortedArray[i].toString() + ", ");
		}
		String res = sb.toString();
		return res.substring(0, res.length() - 2);
	}
	
	LinkedList<WordOccurrence> occurrences(String w) {
		for (int i = 0; i < k; i++) {
			if (arrayOfDifferentLengths[i] != null) {
				WordInformation wInfo = new WordInformation(w);
				WordInformation found = arrayOfDifferentLengths[i].findNode(wInfo);
				if (found != null) {
					return found.occList;
				}
			}
		}
		return new LinkedList<>();
	}
	
	boolean checkAdjacent(String word1, String word2) {
		if (word1 == null || word2 == null) {
			return false;
		}
		word1 = word1.toLowerCase();
		word1 = word1.toLowerCase();
		WordInformation found1 = null, found2 = null;
		for (int i = 0; i < k; i++) {
			if (arrayOfDifferentLengths[i] != null) {
				WordInformation wInfo1 = new WordInformation(word1);
				WordInformation wInfo2 = new WordInformation(word2);
				if (found1 == null)
					found1 = arrayOfDifferentLengths[i].findNode(wInfo1);
				if (found2 == null)
					found2 = arrayOfDifferentLengths[i].findNode(wInfo2);
			}
		}
		if (found1 != null && found2 != null) {
			LinkedList<WordOccurrence> occList1 = found1.occList;
			LinkedList<WordOccurrence> occList2 = found2.occList;
			occList1.reset();
			while (occList1.hasNext()) {
				WordOccurrence wOcc1 = occList1.getCurrentData();
				occList2.reset();
				while (occList2.hasNext()) {
					WordOccurrence wOcc2 = occList2.getCurrentData();
					if (wOcc1.isAdjacent(wOcc2)) {
						return true;
					}
					occList2.next();
				}
				occList1.next();
			}
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AdtWordProject instance = new AdtWordProject();
		System.out.println("Reading file: dictionary.txt");
		instance.readFileAndAnalyse("dictionary.txt");
		System.out.println("       word Count           : " + instance.documentLength());
		System.out.println("Unique word Count           : " + instance.uniqueWords());
		System.out.println("Occurrence Of the word 'the': " + instance.totalOccurrencesForWord("the"));
		System.out.println("Total words with length 5   : " + instance.totalWordsForLength(5));
		System.out.println("Display unique words        : " + instance.displayUniqueWords());
		System.out.println("Occurrences Line/Col        : " + instance.occurrences("data").display());
		System.out.println("Is Word Adjacent            : " + instance.checkAdjacent("data", "the"));
	}

}
