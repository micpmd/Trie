package junit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

import tries.ITrie;
import tries.impl.Trie;
//import tries.sol.Trie;


public class TestTrie
{
    // testing helper function to create a dictionary
    private ITrie readDictionary() throws Exception {
        ITrie root=new Trie();
        Scanner scanner=new Scanner(new FileInputStream("dictionary.txt"));
        while (scanner.hasNext()) {
            String word=scanner.next().toLowerCase();
            root.insert(word);
        }
        scanner.close();
        return root;
    }
    
    @Test
    public void testInsertDoesNotCrash() throws Exception {
        ITrie t = new Trie();
        t.insert("dog");
    }
    
    @Test
    public void testHasChild() throws Exception {
        ITrie t = new Trie();
        t.insert("dog");
        assertTrue(t.hasChild('d'));
        for (char ch = 'a'; ch <= 'z'; ch++){
            if (ch == 'd'){
                continue;
            }
            assertFalse(t.hasChild(ch));
        }
    }
    
    @Test
    public void testGetChild() throws Exception {
        ITrie t = new Trie();
        t.insert("dog");
        assertTrue(t.getChild('d')!=null);
        for (char ch = 'a'; ch <= 'z'; ch++){
            if (ch == 'd'){
                continue;
            }
            assertEquals(null, t.getChild(ch));
        }
    }
    
    @Test
    public void testFollowPath() throws Exception {
        ITrie t = new Trie();
        t.insert("dog");
        t.insert("doctor");
        t.insert("dogmatic");
        t.insert("dogmonster");
        ITrie node = t.followPath("dog");
        assertTrue(node != null);

        node = t.followPath("cat");
        assertTrue(node == null);
        
        node = t.followPath("do");
        assertTrue(node != null);
        
        node = t.followPath("dogs");
        assertTrue(node == null);
        
        node = t.followPath("dogma");
        assertTrue(node != null);
    }
    
    @Test
    public void testGetParent() throws Exception {
        ITrie t = new Trie();
        t.insert("dog");
        assertTrue(t.getChild('d')!=null);
        ITrie dNode = t.followPath("d");
        // make sure that the parent of the 'd' node is the root
        assertTrue(t == dNode.getParent());
        
        ITrie oNode = t.followPath("do");
        // check the parent of the 'o' node
        assertTrue(dNode == oNode.getParent());
        
        ITrie gNode = t.followPath("dog");
        // check the parent of the 'g' node
        assertTrue(oNode == gNode.getParent());
    }
    
    @Test
    public void testInsertAndContains1() throws Exception {
        ITrie t=new Trie();
        t.insert("dog");
        assertEquals(true, t.contains("dog"));
        assertEquals(false, t.contains("d"));
        assertEquals(false, t.contains("do"));
        assertEquals(false, t.contains("dogs"));
    }
    
    @Test
    public void testInsertAndContains() throws Exception {
        ITrie root = readDictionary();
        assertTrue(root.contains("ably"));
        assertTrue(root.contains("abnegated"));
        assertTrue(root.contains("phooey"));
        assertFalse(root.contains("spacco"));
        assertFalse(root.contains("gnb"));
        assertFalse(root.contains("thebibibib"));
    }
    
    @Test
    public void testAllWords() throws Exception {
        ITrie root = readDictionary();
        assertEquals(178691, root.findAllWords().size());
    }
    
    @Test
    public void testFindStartsWith() throws Exception {
        ITrie root = readDictionary();
        Set<String> words=root.findWordsBeginningWith("aard");
        assertEquals(4, words.size());
        assertTrue(words.contains("aardvark"));
        assertTrue(words.contains("aardvarks"));
        assertTrue(words.contains("aardwolf"));
        assertTrue(words.contains("aardwolves"));
    }
    
    @Test
    public void testFindEndsWith() throws Exception {
        ITrie root = readDictionary();
        Set<String> words=root.findWordsEndingWith("inging");
        // assert we get the right number of results
        assertEquals(49, words.size());
        // check that a few results are there
        for (String s : Arrays.asList("swinging", "hinging", "mudslinging", "kinging", "bringing", "upswinging")){
            assertTrue(words.contains(s));
        }
    }
    
    @Test
    public void testFindContains() throws Exception {
        ITrie root = readDictionary();
        Set<String> words=root.findWordsContaining("dog");
        // check that the size is correct
        assertEquals(224, words.size());
        // check that a sample of words with dog are in the 
        for (String s : new String[] {"dog", "seadogs", "undogmatic", "dogma", "endogenous", "firedog"}){
            assertTrue(words.contains(s));
        }
    }
    
    @Test
    public void testCloseWordsChangedLetters() throws Exception {
        ITrie root = readDictionary();
        
        String word = "soul";
        Set<String> words = root.findCloseWordsChangedLetters(word, 1);
        // make sure the set of words is the right size
        assertEquals(9, words.size());
        // make sure we have all 9 words
        for (String s : Arrays.asList("sous", "sour", "soul", "souk", "shul", "soup", "foul", "saul", "soil")){
            assertTrue(words.contains(s));
        }
    }
    
    @Test
    public void testCloseWords() throws Exception {
        ITrie root = readDictionary();
        
        String word="dance";
        int dist=1;
        
        Set<String> words=root.findCloseWordsChangedLetters("", dist);
        System.out.println("There are " +words.size()+" words within "+dist+" of "+word);
        for (String s : words) {
            System.out.println(s);
        }
    }
    
    @Test
    public void testCloseWordsAddedLetters() throws Exception {
        ITrie t = new Trie();
        t.insert("dog");
        t.insert("dogs");
        t.insert("dosg");
        t.insert("dsog");
        t.insert("dogaa");
        Set<String> words = t.findCloseWordsAddedLetters("dog", 1);        
        assertTrue(words.contains("dog"));
        assertTrue(words.contains("dogs"));
        assertTrue(words.contains("dosg"));
        assertTrue(words.contains("dsog"));
        assertTrue(!words.contains("dogaa"));
    }
    
    @Test
    public void testCloseWordsRemovedLetters() throws Exception {
        /*ITrie root = readDictionary();
        
        String word = "author";
        Set<String> words = root.findCloseWordsRemovedLetters(word, 3);*/
    	ITrie t = new Trie();
        t.insert("ogaa");
        t.insert("doaa");
        t.insert("dgaa");
        t.insert("doga");
        t.insert("dogaa");
        Set<String> words = t.findCloseWordsRemovedLetters("dogaa", 1); 
        
        for (String s : words) {
            System.out.println(s);
        }
    }
    
    @Test
    public void testCloseWordsAllChanges() throws Exception {
        ITrie root = readDictionary();
        
        String word = "soul";
        Set<String> words = root.findCloseWordsAllChanges(word, 3);
        
        for (String s : words) {
            System.out.println(s);
        }
    }
    
    @Test
    public void testCountLeafNotes() throws Exception {
    	ITrie t = new Trie();
    	t.insert("ogaa");
        t.insert("doaa");
        t.insert("dgaa");
        t.insert("doga");
        t.insert("dogaa");
        
        int count = t.countLeafNotes();
        
        System.out.println(count);
    }
    
    @Test
    public void testGetNumDeeperThan() throws Exception {
    	ITrie t = new Trie();
    	t.insert("abc");
        t.insert("def");
        t.insert("ghi");
        t.insert("gkl");
        
        int count = t.getNumDeeperThan(0);
        
        System.out.println(count);
    }
}
