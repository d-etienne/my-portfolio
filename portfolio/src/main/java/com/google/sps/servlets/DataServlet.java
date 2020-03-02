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

import java.io.IOException;
import com.google.gson.Gson;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  ArrayList<String> quotes = new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    quotes.add("It isnt where you came from. Its where you are going that counts. - Ella Fitzgerald");
    quotes.add("Try to be the rainbow to someone elses clouds. - Maya Angelou");
    quotes.add("Beauty is being the best possible version of yourself, inside and out - Audrey Hepburn");
    for (int idx = 0; idx < quotes.size(); idx++ ){
        String json = convertToJson(quotes.get(idx));
        response.setContentType("/application/json;");
        response.getWriter().println(json);
    }
  }
  
    Gson gson = new Gson();
    String json = gson.toJson(quote);
    return json;
  }


}

