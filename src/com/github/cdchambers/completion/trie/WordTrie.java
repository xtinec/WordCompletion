package com.github.cdchambers.completion.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Basic Description.  There are no duplicate words in the trie. In other words, we cannot tell if
 * there are multiple instances of a word from the trie.  All words are lower-case for 
 * simplification. And, all words are composed of characters from 'a' to 'z'.
 * 
 * @author christine
 */
public class WordTrie {
  
  /**
   * Represents a single node in the WordTrie.
   */
  private class Node {
    /** whether the string from the root to this node is a word */
    private boolean isWord;
    
    /** 
     * For the 26 letters in the alphabet, if there is an edge for a given letter, its corresponding
     * edge Node is initialized 
     */
    private Node[] edges;

    private Node(boolean isWord) {
      this.isWord = isWord;
      this.edges = new Node[26];
    }

    private void addEdge(char ch, boolean isWord) {
      edges[Character.toLowerCase(ch) - 'a'] = new Node(isWord);
    }

    private Node getEdge(char ch) {
      return edges[Character.toLowerCase(ch) - 'a'];
    }

    private boolean isWord(){
      return this.isWord;
    }

    private void setIsWord(boolean isWord){
      this.isWord = isWord;
    }
  }

  /** The root node */
  private Node root;

  public WordTrie() {
    root = new Node(false);
    root.edges = new Node[26];
  }

  /**
   * Add a given word to the trie.
   * 
   * @param word to add
   */
  public void add(String word) {
    getNode(word, true).setIsWord(true);
  }
  
  private Node getNode(String word, boolean createPath) {
    Node n = root;
    for (char ch : word.toCharArray()) {
      if (n.getEdge(ch) == null) {
        if (createPath) {
          n.addEdge(ch, false);
        } else {
          return null;
        }
      }
      n = n.getEdge(ch);
    }
    return n;
  }

  /**
   * Add all given words to the trie
   * 
   * @param words to add
   */
  public void addAll(Iterable<String> words) {
    for (String word : words) {
      add(word);
    }
  }

  /**
   * Add all given 0 and more words to the trie
   * 
   * @param words to add
   */
  public void addAll(String... words) {
    for (String word : words) {
      add(word);
    }
  }
  
  /**
   * Determine if a given word is in the trie
   * @param word to check
   * @return true if the word is in the trie, false otherwise
   */
  public boolean isInTrie(String word) {
    Node n = getNode(word, false);
    return n != null && n.isWord();
  }
  
  /**
   * Determine if the given prefix is potentially a valid word in the trie.
   * 
   * @param prefix
   * @return
   */
  public boolean isPrefixOfWords(String prefix) {
    return getNode(prefix, false) != null;
  }

  /**
   * Return the top num completions of a given prefix based on alphabetical order
   * If it doesn't have up to num completions, the function will return however many completions of prefix it can find
   * @param prefix
   * @return
   */
  public Collection<String> getCompletions(String prefix, int num) {
    List<String> completions = new ArrayList<String>(num);
    StringBuilder buffer = new StringBuilder();
    Node n = root;
    
    for (char ch : prefix.toCharArray()) {
      if (n.getEdge(ch) == null) {
        return completions;
      }
      buffer.append(ch);
      n = n.getEdge(ch);
    }
    
    getCompletionByNode(num, n, buffer, completions);
    return completions;
  }
  
  private void getCompletionByNode(int num, Node n, StringBuilder buffer, List<String> completions) {
    if (n == null || num == completions.size()) {
      return;
    }
    if (n.isWord()) {
      completions.add(buffer.toString());
    }
    for (char ch = 'a'; ch <= 'z'; ch++) {
      buffer.append(ch);
      getCompletionByNode(num, n.getEdge(ch), buffer, completions);
      buffer.deleteCharAt(buffer.length() - 1);
    }
  }

}