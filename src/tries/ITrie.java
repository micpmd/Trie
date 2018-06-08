package tries;

import java.util.Set;

/**
 * Interface for a Trie data structure that stores a compressed dictionary of words.
 * A trie is a tree structure, but it is not a binary tree because each node
 * can have up to 26 children, one for each letter of the Latin alphabet.
 * In addition, each node needs to know whether a word is stored there.
 * 
 * To check if a Trie contains a word, one would "walk" a path from the root
 * to child nodes using each letter of the word in order, remembering each letter followed.
 * 
 * @author jspacco
 */
public interface ITrie
{
    /**
     * Insert the given word into the ITrie.
     * @param word
     */
    public void insert(String word);
    
    /**
     * Return true if the current node has a child node labeled with the given
     * letter, and false otherwise.
     * @param letter
     * @return
     */
    public boolean hasChild(char letter);
    
    /**
     * Return the child node that corresponds to the given letter.
     * If no such child node exists, return null.
     * This method should <b>not</b> throw an exception. 
     * @param letter
     * @return
     */
    public ITrie getChild(char letter);
    
    /**
     * Return the parent of this node, or null if this node is the root.
     * @return
     */
    public ITrie getParent();
    
    /**
     * Follow the given path, and return the ITrie node. If no such node exists, return null.
     * This method should <b>not</b> throw an exception.
     * @param path
     * @return
     */
    public ITrie followPath(String path);

    /**
     * Return true if the Trie contains the given word, and false otherwise.
     * @param word
     * @return
     */
    public boolean contains(String word);
    
    /**
     * Return a set of all words in the ITrie. Implement this method recursively.
     * It may be helpful to use a private helper method with the following signature:
     * 
     * <pre>
     * private void findAllWordsHelper(Set&lt;String&gt; results)
     * </pre>
     * 
     * There are other ways to do this, but it is simplest to do recursively with
     * a helper method.
     * 
     * @return
     */
    public Set<String> findAllWords();
    
    /**
     * Return a set of all words that begin with the given prefix.
     * It may be helpful to implement this using a helper method
     * with one of the following signatures:
     * <pre>
     * private void findAllWordsHelper(String prefix, String path, Set&lt;String&gt; results)
     * </pre>
     * 
     * or:
     * 
     * <pre>
     * private void findAllWordsHelper(String prefix, int index, Set&lt;String&gt; results)
     * </pre>
     * 
     * @param prefix
     * @return
     */
    public Set<String> findWordsBeginningWith(String prefix);
    
    /**
     * Return a set of all words containing the given pattern as a substring.
     * This pattern can occur anywhere inside the String. It may be helpful to
     * use a private helper method with one of the following signatures:
     * <pre>
     * private void findWordsContainingHelper(String pattern, String path, Set&lt;String&gt;)
     * </pre>
     * 
     * or maybe this helper method:
     * 
     * <pre>
     * private void findWordsContainingHelper(String pattern, int index, Set&lt;String&gt;)
     * </pre>
     * @param pattern
     * @return
     */
    public Set<String> findWordsContaining(String pattern);
    
    /**
     * Return a set of all words in the Trie that end with the given suffix.
     * You may want to approach this using a private recursive helper method 
     * with the following signature:
     * <pre>
     * private void findWordsEndingWithHelper(String suffix, String path, Set&lt;String&gt;)
     * </pre>
     * 
     * or maybe this:
     * <pre>
     * private void findWordsEndingWithHelper(String suffix, int index, Set&lt;String&gt;)
     * </pre>
     * @param suffix
     * @return
     */
    public Set<String> findWordsEndingWith(String suffix);
    
    /**
     * Return a set of all words that are within the given number of edits
     * of the given word, where edits are only allowed to be changed letters.
     * For example, 'hog' is an edit distance of 1 changed letter away from 'dog', 
     * while 'foo' is an edit distance of 2 changed letters away from 'hog'.
     * 
     * You may find it helpful to use a helper method with the following signature:
     * <pre>
     * private void findCloseWordsChangedLetters(String remaining, String path, int distance, Set&lt;String&gt; results)
     * </pre>
     * 
     * or possibly this signature:
     * 
     * <pre>
     * private void findCloseWordsChangedLetters(String word, int index, int distance, Set&lt;String&gt; results)
     * </pre>
     * 
     * 
     * @param word
     * @param distance
     * @return
     */
    public Set<String> findCloseWordsChangedLetters(String word, int distance);
    
    /**
     * <b>CHALLENGE PROBLEM:</b>
     * This is to show off your programming skills. There are not test cases for
     * this so you need to write your own in a new file.
     * 
     * Return a set of all words that are within the given edit distance of 
     * the given word, where the edits are added letters.
     * 
     * @param word
     * @param distance
     * @return
     */
    public Set<String> findCloseWordsAddedLetters(String word, int distance);
    
    /**
     * <b>CHALLENGE PROBLEM:</b>
     * This is to show off your programming skills. There are not test cases for
     * this so you need to write your own in a new file.
     * 
     * Return a set of all words that are within the given edit distance of 
     * the given word, where the edits are removed letters.
     * 
     * @param word
     * @param distance
     * @return
     */
    public Set<String> findCloseWordsRemovedLetters(String word, int distance);

    /**
     * <b>CHALLENGE PROBLEM:</b>
     * This is to show off your programming skills. There are not test cases for
     * this so you need to write your own in a new file.
     * 
     * Return a set of all words that are within the given edit distance of 
     * the given word, where the edits are either added, removed, or changed letters.
     * 
     * @param word
     * @param distance
     * @return
     */
    public Set<String> findCloseWordsAllChanges(String word, int distance);
    
    /*
     * Counts the number of leaf nodes (i.e. nodes with no children) in a Trie.
     */
    public int countLeafNotes();
    
    /*
     * Takes an int parameter depth and returns the number of nodes 
     * (word or non-word nodes) that are deeper than the given depth.
     */
    public int getNumDeeperThan(int depth); 
}
