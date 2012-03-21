package com.github.cdchambers.completion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;


@SuppressWarnings({"serial" })
public class CompletionServlet extends HttpServlet {
  
  /**
   * the trie the suggestion servelet uses
   */
  private static WordTrie basicWT;
  /**
   * the maximum number of elements the suggestion servlet returns upon a request
   */
  private static int max;
  
  //This Happens Once when the servelet is loaded and is reused subsequently
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      WordTrieSource source = new WordTrieSource(new File("data/words.txt"));
      basicWT = new WordTrie();
      basicWT.addAll(source);
      
      max = 5;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("application/json");
    PrintWriter out = resp.getWriter();

    String prefix = req.getParameter("p");
    Collection<String> completions = basicWT.getCompletions(prefix, max);
    
    JSONArray outputJSON = new JSONArray();
    outputJSON.addAll(completions);
    
    try {
      outputJSON.write(out);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
