package tries.impl;

import java.util.*;
import java.util.Set;

import tries.ITrie;

public class Trie implements ITrie {
	private Map<Character, Trie> children = new HashMap<Character, Trie>();
	private Trie parent;
	private boolean isWord = false;

	public Trie() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#insert(java.lang.String)
	 */
	@Override
	public void insert(String word) {
		if (word.equals("")) {
			isWord = true;
		} else {
			char first = word.charAt(0);
			String rest = word.substring(1);
			if (!children.containsKey(first)) {
				Trie child = new Trie();
				child.parent = this;
				children.put(first, child);
			}
			Trie node = children.get(first);
			node.insert(rest);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#hasChild(char)
	 */
	@Override
	public boolean hasChild(char letter) {
		return children.containsKey(letter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#getChild(char)
	 */
	@Override
	public ITrie getChild(char letter) {
		if (children.containsKey(letter))
			return children.get(letter);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#getParent()
	 */
	@Override
	public ITrie getParent() {
		if (this.parent != null)
			return this.parent;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#followPath(java.lang.String)
	 */
	@Override
	public ITrie followPath(String path) {
		ITrie res = null;

		if (path.equals("")) {
			return this;
		} else {
			char first = path.charAt(0);
			String rest = path.substring(1);
			if (children.containsKey(first)) {
				Trie node = children.get(first);
				res = node.followPath(rest);
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String word) {
		boolean res = false;

		if (word.equals("")) {
			return this.isWord;
		} else {
			char first = word.charAt(0);
			String rest = word.substring(1);
			if (children.containsKey(first)) {
				Trie node = children.get(first);
				res = node.contains(rest);
			}
		}
		return res;
	}

	@Override
	public Set<String> findAllWords() {
		Set<String> words = new HashSet<>();
		findAllWordsHelper(words, "");

		return words;
	}

	private void findAllWordsHelper(Set<String> words, String word) {
		if (isWord) {
			words.add(word);
		}

		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			child.findAllWordsHelper(words, word + c);
		}
	}

	@Override
	public Set<String> findWordsBeginningWith(String prefix) {
		Trie node = (Trie) followPath(prefix);

		Set<String> words = new HashSet<>();

		node.findAllWordsHelper(words, prefix);

		return words;
	}

	private void findWordsBeginningWithHelper(Set<String> words, String word,
			String prefix) {
		if (isWord) {
			words.add(word);
		}

		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			child.findAllWordsHelper(words, word + c);
		}
	}

	@Override
	public Set<String> findWordsEndingWith(String suffix) {
		Set<String> words = new HashSet<>();
		findWordsEndingWithHelper(words, "", suffix);
		return words;
	}

	private void findWordsEndingWithHelper(Set<String> words, String word,
			String suffix) {
		if (followPath(suffix) != null) {
			words.add(word + suffix);
		}

		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			child.findWordsEndingWithHelper(words, word + c, suffix);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#findWordsContaining(java.lang.String)
	 */
	@Override
	public Set<String> findWordsContaining(String patttern) {
		Set<String> words = new HashSet<>();
		findWordsContainingHelper(words, "", patttern);
		return words;
	}

	private void findWordsContainingHelper(Set<String> words, String word,
			String patttern) {
		if (word.endsWith(patttern)) {
			findAllWordsHelper(words, word);
		}

		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			child.findWordsContainingHelper(words, word + c, patttern);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#findCloseWordsChangedLetters(java.lang.String, int)
	 */
	@Override
	public Set<String> findCloseWordsChangedLetters(String word, int distance) {
		Set<String> words = new HashSet<>();

		findCloseHelper(words, word, distance, "");
		return words;
	}

	private void findCloseHelper(Set<String> set, String word, int dis,
			String res) {

		if (word.equals("") && isWord) {
			set.add(res);
		} else if (!word.equals("")) {
			char first = word.charAt(0);
			String rest = word.substring(1);

			for (char c : children.keySet()) {
				Trie node = children.get(c);
				if (c == first) {
					node.findCloseHelper(set, rest, dis, res + first);
				} else if (dis > 0) {
					node.findCloseHelper(set, rest, dis - 1, res + c);
				}
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#findCloseWordsAddedLetters(java.lang.String, int)
	 */
	@Override
	public Set<String> findCloseWordsAddedLetters(String words, int distance) {
		Set<String> set = new HashSet<>();
		findCloseHelper2(set, words, distance, "");
		return set;
	}

	private void findCloseHelper2(Set<String> set, String word, int dis,
			String res) {

		if (word.equals("") && isWord) {
			set.add(res);
			if (dis > 0) {
				for (char c : children.keySet()) {
					Trie node = children.get(c);
					node.findCloseHelper3(set, word, dis - 1, res + c);
				}
			}
		} else if (!word.equals("")) {
			char first = word.charAt(0);
			String rest = word.substring(1);

			for (char c : children.keySet()) {
				Trie node = children.get(c);
				if (c == first) {
					node.findCloseHelper2(set, rest, dis, res + first);
				} else if (dis > 0) {
					node.findCloseHelper2(set, word, dis - 1, res + c);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#findCloseWordsRemovedLetters(java.lang.String, int)
	 */
	@Override
	public Set<String> findCloseWordsRemovedLetters(String words, int distance) {
		Set<String> set = new HashSet<>();
		findCloseHelper3(set, words, distance, "");
		return set;
	}

	private void findCloseHelper3(Set<String> set, String word, int dis,
			String res) {

		if (word.equals("") && isWord) {
			set.add(res);
			/*
			 * } else if (word.length() == 1 && dis > 0 && isWord) {
			 * set.add(res);
			 */
		} else if (!word.equals("")) {
			char first = word.charAt(0);
			String rest = word.substring(1);

			for (char c : children.keySet()) {
				Trie node = children.get(c);
				if (c == first) {
					node.findCloseHelper3(set, rest, dis, res + first);
				} else if (dis > 0) {
					findCloseHelper3(set, rest, dis - 1, res);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tries.ITrie#findCloseWordsAllChanges(java.lang.String, int)
	 */
	@Override
	public Set<String> findCloseWordsAllChanges(String word, int distance) {
		Set<String> set = new HashSet<>();

		findCloseHelper4(set, word, distance, "");
		return set;
	}

	private void findCloseHelper4(Set<String> set, String word, int dis,
			String res) {

		if (word.equals("") && isWord) {
			set.add(res);
		} else if (!word.equals("")) {
			char first = word.charAt(0);
			String rest = word.substring(1);

			for (char c : children.keySet()) {
				Trie node = children.get(c);
				if (c == first) {
					node.findCloseHelper4(set, rest, dis, res + first);
				} else if (dis > 0) {
					node.findCloseHelper4(set, rest, dis - 1, res);
					node.findCloseHelper4(set, word, dis - 1, res + c);
					node.findCloseHelper4(set, rest, dis - 1, res + c);
				}
			}

		}

	}

	@Override
	public int countLeafNotes() {
		int count = 0;
		if (children.keySet().isEmpty()) {
			count++;
		}
		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			count = count + child.countLeafNotes();
		}
		return count;
	}

	@Override
	public int getNumDeeperThan(int depth) {
		int count = 0;

		if (depth < 0) {
			count++;
		}

		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			count = count + child.getNumDeeperThan(depth-1);
		}

		return count;
	}

	private void getNumDeeperThanHelper(int count, int depth) {
		if (depth <= 0) {
			count++;
		}
		for (char c : children.keySet()) {
			Trie child = (Trie) getChild(c);
			child.getNumDeeperThanHelper(count, depth--);
		}
	}
}
