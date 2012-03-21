package com.github.cdchambers.completion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class WordTrieSource implements Iterable<String> {
  
  private File inputFile;
  
  public WordTrieSource(File file) {
    inputFile = file;
    System.out.println(inputFile.getAbsolutePath());
  }
  
  @Override
  public Iterator<String> iterator() {
    try {
      FileInputStream fstream = new FileInputStream(inputFile);
      return new WordIterator(fstream);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static class WordIterator implements Iterator<String> {
   
    private InputStream in;
    private BufferedReader reader;
    private String nextLine;
    
    public WordIterator(InputStream in) {
      this.in = in;
      this.reader = new BufferedReader(new InputStreamReader(in));
      readLine();
    }
    
    private void readLine() {
      try {
        nextLine = reader.readLine();
        if (nextLine == null) {
          in.close();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      } 
    }
    
    @Override
    public boolean hasNext() {
      return nextLine != null;
    }
    
    @Override
    public String next() {
      String ret = nextLine;
      readLine();
      return ret;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
