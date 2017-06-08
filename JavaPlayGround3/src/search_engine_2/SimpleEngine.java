/*
 * Name: Jose Terrones Jr.
 * Purpose: A search engine that uses the inverted indexing method.
 * Reads in text files and that will be used for indexing. Once the files
 * are indexed each word within will be stored with the chapter they are 
 * found in. Once completed a user will see the results and be allowed to
 * enter a word and the chapters its found in will be displayed if the 
 * word is contained in the index.
 */

package search_engine_2;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

/**
A very simple search engine. Uses an inverted index over a folder of TXT files.
*/
public class SimpleEngine {

   public static void main(String[] args) throws IOException {
      final Path currentWorkingPath = Paths.get("").toAbsolutePath();
      
      // the inverted index
      final NaiveInvertedIndex index = new NaiveInvertedIndex();
      
      // the list of file names that were processed
      final List<String> fileNames = new ArrayList<String>();
 
      // This is our standard "walk through all .txt files" code.
      Files.walkFileTree(currentWorkingPath, new SimpleFileVisitor<Path>() {
         int mDocumentID  = 0;
         
         public FileVisitResult preVisitDirectory(Path dir,
          BasicFileAttributes attrs) {
            // make sure we only process the current working directory
            if (currentWorkingPath.equals(dir)) {
               return FileVisitResult.CONTINUE;
            }
            return FileVisitResult.SKIP_SUBTREE;
         }

         public FileVisitResult visitFile(Path file,
          BasicFileAttributes attrs) {
            // only process .txt files
            if (file.toString().endsWith(".txt")) {
               // we have found a .txt file; add its name to the fileName list,
               // then index the file and increase the document ID counter.
               System.out.println("Indexing file " + file.getFileName());
               
               fileNames.add(file.getFileName().toString());
               indexFile(file.toFile(), index, mDocumentID);
               mDocumentID++;
            }
            return FileVisitResult.CONTINUE;
         }

         // don't throw exceptions if files are locked/other errors occur
         public FileVisitResult visitFileFailed(Path file,
          IOException e) {

            return FileVisitResult.CONTINUE;
         }

      });
      
      printResults(index, fileNames);
      
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter a term to search for: ");
      String word = scan.next();
      while(!word.equals("quit"))
      {
    	  if(fileNames.isEmpty()){
    		  System.out.println("The word " + word + " was not found");
    	  }
    	  else{
    		  word = PorterStemmer.processToken(word);
    		  if(index.getPostings(word) != null){
        		  System.out.println("The word is found in " + index.getPostings(word));
    		  }
    		  else
    			  System.out.println("Word was not found.");
    	  }
    	  System.out.print("\nEnter a term to search for: ");
    	  word = scan.next();
      }
      System.out.println("bye");
      scan.close();
   }
     

   /**
   Indexes a file by reading a series of tokens from the file, treating each 
   token as a term, and then adding the given document's ID to the inverted
   index for the term.
   @param file a File object for the document to index.
   @param index the current state of the index for the files that have already
   been processed.
   @param docID the integer ID of the current document, needed when indexing
   each term from the document.
   */
   private static void indexFile(File file, NaiveInvertedIndex index, 
    int docID) {
	  try {
		SimpleTokenStream token = new SimpleTokenStream(file);
		while(token.hasNextToken()){
			index.addTerm(PorterStemmer.processToken(token.nextToken()), docID);
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }

   private static void printResults(NaiveInvertedIndex index, 
    List<String> fileNames) {
     
      // TO-DO: print the inverted index.
      // Retrieve the dictionary from the index. (It will already be sorted.)
      // For each term in the dictionary, retrieve the postings list for the
      // term. Use the postings list to print the list of document names that
      // contain the term. (The document ID in a postings list corresponds to 
      // an index in the fileNames list.)
      
      // Print the postings list so they are all left-aligned starting at the
      // same column, one space after the longest of the term lengths. Example:
      // 
      // as:      document0 document3 document4 document5
      // engines: document1
      // search:  document2 document4  
	   int longWord = 0;
	   for(String word : index.getDictionary()){
		   longWord = Math.max(longWord, word.length());
	   }
	   for(String word : index.getDictionary()){
		   System.out.format("%-" + longWord + "s: %s\n", word, index.getPostings(word));
	   }
   }
}
