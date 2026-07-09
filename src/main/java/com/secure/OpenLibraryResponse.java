package com.secure;

import java.util.List;

public class OpenLibraryResponse {
      private List<book> docs;
      public List<book> getDocs(){
    	  return docs;
      }
      public void setDocs(List<book> docs) {
    	  this.docs=docs;
      }
}
