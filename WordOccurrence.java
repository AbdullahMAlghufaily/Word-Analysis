public class WordOccurrence implements Comparable<WordOccurrence> {
	int lineNo;
	int position;

	public WordOccurrence(int myLineNo, int myPosition) {
		lineNo = myLineNo;
		position = myPosition;
	}

	public boolean isAdjacent(WordOccurrence o) {
		if (lineNo == o.lineNo) {
			int diffResult = 0;
			if (position == 1) {
				diffResult = o.position - position;
			} else {
				diffResult = position - o.position;
			}
			return diffResult == 1;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(WordOccurrence o) {
		return 0;
	}

	@Override
	public String toString() {
		return "(" + lineNo + ", " + position + ")";
	}
}
