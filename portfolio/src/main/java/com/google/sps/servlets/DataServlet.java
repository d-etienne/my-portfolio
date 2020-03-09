// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import com.google.sps.data.Comment;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  ArrayList<String> comments = new ArrayList<String>();
  ArrayList<String> names = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Comments").addSort("timestamp", SortDirection.DESCENDING);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        
        ArrayList<Comment> savedComments = new ArrayList<>();
            for (Entity entity : results.asIterable()) {
                String name = (String) entity.getProperty("name");
                String message = (String) entity.getProperty("message");
                long sentimentScore = (long) entity.getProperty("sentiment-score");

            Comment comment = new Comment(name, message, sentimentScore);
            savedComments.add(comment);
    }
        String json = convertToJson(comments); // converts array of comments to JSON
        response.setContentType("application/json;"); // Identifies to server the type of data to expect
        response.getWriter().println(json);// writes to server the JSON data
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String comment = request.getParameter("comment-box"); //grabs written response from the comment box 
        String name = request.getParameter("name-box");
        names.add(name); // adds name to array of names
        comments.add(comment); // adds comment to the array of comments

        //creates a document with the entered comment and scores the sentiment value of it
        Document doc = Document.newBuilder().setContent(comment).setType(Document.Type.PLAIN_TEXT).build();
        LanguageServiceClient languageService = LanguageServiceClient.create();
        Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
        float score = sentiment.getScore(); 
        languageService.close(); 

        //stores the messages, names, and sentiment scores of the comments
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("name", name);
        commentEntity.setProperty("message", comment);
        commentEntity.setProperty("sentiment-score", score);
        datastore.put(commentEntity); 
        response.sendRedirect("/index.html"); //redirects the person back to the original page 
    }

// converts array of comments into a JSON
  private String convertToJson(ArrayList comments){
        Gson gson = new Gson();
        String json = gson.toJson(comments);
        return json;
  }
 
}

