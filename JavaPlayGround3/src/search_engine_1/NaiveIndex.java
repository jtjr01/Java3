/*
 * Name: Jose Terrones Jr.
 * Purpose: Given the basic skeleton for a search engine structure. From the basic
 * structure a few items were ommited that needed to be complete. This will use the
 * first ten chapters of Moby Dick as a corpus. Then the user will be allowed to
 * search for words within the corpus and type quit to exit the program.
 */

package search_engine_1;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

/**
 Represents a naive index over a set of documents in a single directory.
 */
public class NaiveIndex {

   private String[] mTermArray;
   private String[] mFileArray;
   private boolean[][] mIndex;

   /**
    Indexes all .txt files in the specified directory. First builds a dictionary
    of all terms in those files, then builds a boolean term-document matrix as
    the index.

    @param directory the Path of the directory to index.
    */
   public void indexDirectory(final Path directory) {
      // will need a data structure to store all the terms in the document
      // HashSet: a hashtable structure with constant-time insertion; does not
      // allow duplicate entries; stores entries in unsorted order.

      final HashSet<String> dictionary = new HashSet<>();
      final HashSet<String> files = new HashSet<>();

      try {
         // go through each .txt file in the working directory

         System.out.println(directory.toString());

         Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

            public FileVisitResult preVisitDirectory(Path dir,
             BasicFileAttributes attrs) {
               // make sure we only process the current working directory
               if (directory.equals(dir)) {
                  return FileVisitResult.CONTINUE;
               }
               return FileVisitResult.SKIP_SUBTREE;
            }

            public FileVisitResult visitFile(Path file,
             BasicFileAttributes attrs) {
               // only process .txt files
               if (file.toString().endsWith(".txt")) {
                  buildDictionary(file, dictionary);
                  files.add(file.getFileName().toString());
               }
               return FileVisitResult.CONTINUE;
            }

            // don't throw exceptions if files are locked/other errors occur
            public FileVisitResult visitFileFailed(Path file,
             IOException e) {

               return FileVisitResult.CONTINUE;
            }
         });

         // convert the dictionaries to sorted arrays, so we can use binary
         // search for finding indices.
         mTermArray = dictionary.toArray(new String[0]);
         mFileArray = files.toArray(new String[0]);

         Arrays.sort(mTermArray);
         Arrays.sort(mFileArray);

         // construct the term-document matrix. docs are rows, terms are cols.
         mIndex = new boolean[files.size()][dictionary.size()];

         // walk back through the files -- a second time!! 
         Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            public FileVisitResult preVisitDirectory(Path dir,
             BasicFileAttributes attrs) {
               if (directory.equals(dir)) {
                  return FileVisitResult.CONTINUE;
               }
               return FileVisitResult.SKIP_SUBTREE;
            }

            public FileVisitResult visitFile(Path file,
             BasicFileAttributes attrs) {

               if (file.toString().endsWith(".txt")) {
                  // add entries to the index matrix for this file
                  indexFile(file, mIndex, mTermArray, mFileArray);
               }
               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFileFailed(Path file, IOException e) {
               return FileVisitResult.CONTINUE;
            }

         });
      }
      catch (Exception ex) {
      }

   }

   public static void main(String[] args) {
	   
	   Scanner scan = new Scanner(System.in);
      // Example use: construct a NaiveIndex object, then use it to index all
      // *.txt files in a specified directory. Then print the results.

      NaiveIndex index = new NaiveIndex();

      // this gets the current working path.
      final Path currentWorkingPath = Paths.get("").toAbsolutePath();

      index.indexDirectory(currentWorkingPath);
      index.printResults();
      System.out.print("Enter a term to search for: ");
      String word = scan.next();
      while(!word.equals("quit"))
      {
    	  int location = Arrays.binarySearch(index.mTermArray, word);
    	  for(int i = 0; i < index.mFileArray.length;i++)
    	  {
    		  if(location > 0 && index.mIndex[i][location] == true)
    		  {
    			  System.out.print(index.mFileArray[i] + "   ");
    		  }
    	  }
    	  if(location < 0){
    		  System.out.print("Word not found.");
    	  }
    	  System.out.print("\nEnter a term to search for: ");
    	  word = scan.next();
      }
      System.out.println("bye");
   }

   // reads the file given by Path; adds each term from file to the dictionary
   private static void buildDictionary(Path file, HashSet<String> dictionary) {
      try {
         try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
               // read one word at a time; process and add it to dictionary.

               String word = processWord(scan.next());
               if (word.length() > 0) {
                  dictionary.add(word);
               }
            }
         }
      }
      catch (IOException ex) {
      }

   }

   private static void indexFile(Path file, boolean[][] index,
    String[] dictArray, String[] fileArray) {

      // get the row# for this file in the index matrix.
      int fileRow
       = Arrays.binarySearch(fileArray, file.getFileName().toString());

      try {
         // read one word at a time; process and update the matrix.
         try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
               String word = processWord(scan.next());
               if (word.length() > 0) {
                  int wordCol = Arrays.binarySearch(dictArray, word);

                  index[fileRow][wordCol] = true;
               }
            }
         }
      }
      catch (IOException ex) {
      }

   }

   private static String processWord(String next) {
      return next.replaceAll("\\W", "").toLowerCase();
   }

   /**
   Prints the result of the indexing, as a matrix of 0 and 1 entries. Warning:
   prints a lot of text :).
   */
   public void printResults() {

      // find the longest file name in the index
      int longestFile = 0;
      for (String file : mFileArray) {
         longestFile = Math.max(longestFile, file.length());
      }

      // find the longest word length in the index
      int longestWord = 0;
      for (String dict : mTermArray) {
         longestWord = Math.max(longestWord, dict.length());
      }

      // print all the words in one row
      System.out.print("TERMS:");
      printSpaces(longestFile + 2 - 6);
      for (String word : mTermArray) {
         System.out.print(word);
         printSpaces(longestWord - word.length() + 1);
      }
      System.out.println("");

      // for each file: print file name, then a 1 or 0 in each column of each 
      // word in the index.
      int fNdx = 0;
      for (String file : mFileArray) {
         System.out.print(file + ": ");
         printSpaces(longestFile - file.length());

         for (int i = 0; i < mIndex[fNdx].length; i++) {
            System.out.print(mIndex[fNdx][i] ? "1" : "0");
            printSpaces(longestWord);
         }
         System.out.println("");
         fNdx++;
      }
   }

   // prints a bunch of spaces
   private static void printSpaces(int spaces) {
      for (int i = 0; i < spaces; i++) {
         System.out.print(" ");
      }
   }
}
