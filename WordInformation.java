public class WordInformation implements Comparable<WordInformation> {
	String word;
	LinkedList<WordOccurrence> occList;
	int size;

	public WordInformation(String myWord) {
		word = myWord;
		WordOccurrence wordOcc = new WordOccurrence(1, 1);
		occList = new LinkedList<>();
		occList.addFirst(wordOcc);
		size = 1;
	}

	public WordInformation(String myWord, int myLineNo, int myPosition) {
		word = myWord;
		WordOccurrence wordOcc = new WordOccurrence(myLineNo, myPosition);
		occList = new LinkedList<>();
		occList.addFirst(wordOcc);
		size = 1;
	}

	public int getSize() {
		return size;
	}

	public String getWord() {
		return word;
	}

	@Override
	public int compareTo(WordInformation o) {
		if (word != null)
			return word.compareTo(o.word);
		else
			return -1;
	}

	@Override
	public String toString() {
		return "(" + word + ", " + size + ")";
	}
}
