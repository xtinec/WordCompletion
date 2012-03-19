package com.github.cdchambers.completion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import com.github.cdchambers.completion.trie.WordTrie;

@SuppressWarnings({"serial" })
public class CompletionServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("application/json");
    PrintWriter out = resp.getWriter();
    
    //Get an instance of the WordTrie
    WordTrie wt = new WordTrie();
    wt.addAll("foo", "bar", "baz", "food", "fox", "foot", "bad", "good", "band");
    Collection<String> completions = wt.getCompletions("fo", 3);
    
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
