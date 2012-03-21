package com.github.cdchambers.completion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.cdchambers.completion.WordTrie;

@RunWith(JUnit4.class)
public class WordTrieTest {

  WordTrie wt = new WordTrie();

  @Test
  public void addOneElement() {
    wt.add("foo");

    assertTrue(wt.isInTrie("foo"));
    assertFalse(wt.isInTrie("bar"));
    assertFalse(wt.isInTrie("food"));		
  }

  @Test
  public void addSimilarElements() {
    wt.add("foo");
    wt.add("bar");
    wt.add("food");

    assertTrue(wt.isInTrie("foo"));
    assertTrue(wt.isInTrie("bar"));
    assertTrue(wt.isInTrie("food"));
    assertFalse(wt.isInTrie("fo"));
    // more assertions
  }

  @Test
  public void addSimilarElements2() {
    wt.add("food");
    wt.add("bar");
    wt.add("foo");

    assertTrue(wt.isInTrie("foo"));
    assertTrue(wt.isInTrie("bar"));
    assertTrue(wt.isInTrie("food"));
    assertFalse(wt.isInTrie("fo"));
    // more assertions
  }
  
  @Test
  public void addCapitalElements() {
    wt.add("foo");
    wt.add("Foo");
    
    assertTrue(wt.isInTrie("foo"));
    assertTrue(wt.isInTrie("Foo"));
    assertTrue(wt.isInTrie("fOo"));
  }

  @Test
  public void addThreeElements() {
    wt.add("foo");
    wt.add("bar");
    wt.add("baz");

    assertTrue(wt.isInTrie("foo"));
    assertTrue(wt.isInTrie("baz"));
    assertTrue(wt.isInTrie("bar"));
    assertFalse(wt.isInTrie("ba"));
    assertFalse(wt.isInTrie("food"));		
  }
  
  private void assertContentsInOrder(Iterable<String> s, String... expecteds) {
    Iterator<String> sIter = s.iterator();
    boolean match = true;
    for (String expected : expecteds) {
      match &= sIter.hasNext() && sIter.next().equals(expected); 
    }
    
    assertTrue("Expected " + Arrays.toString(expecteds) + " but was " + s, 
        !sIter.hasNext() && match);
  }
  
  @Test
  public void getCompletion() {
    wt.add("foot");
    wt.add("foo");
    wt.add("food");
    wt.add("bar");
    wt.add("baz");
    wt.add("fox");
    wt.add("bad");
    wt.add("band");
    
    ArrayList<String> al = new ArrayList<String>(3);
    al.add("foo");
    al.add("food");
    al.add("foot");
    
    assertContentsInOrder(wt.getCompletions("foo", 3), "foo", "food", "foot");
    assertContentsInOrder(wt.getCompletions("foo", 2), "foo", "food");
    assertContentsInOrder(wt.getCompletions("ba", 3), "bad", "band", "bar");
    assertContentsInOrder(wt.getCompletions("do", 2));
  }
  
  @Test
  public void getCompletionOnEmpty() {
    wt.add("foot");
    wt.add("foo");
    wt.add("food");
    wt.add("bar");
    wt.add("baz");
    wt.add("fox");
    wt.add("bad");
    wt.add("band");
    
    assertContentsInOrder(wt.getCompletions("", 3), "bad", "band", "bar");
  }
}