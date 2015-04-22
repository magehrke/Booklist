/** License (BSD Style License):
 *  Copyright (c) 2015
 *  Maximilian Alexander Gehrke
 *  All rights reserved.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package model;


import java.io.BufferedWriter;
import java.io.File;					//to open a file
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner; 				//to get a parser
import java.util.Vector;

/**
 * This class represents a library in which
 * all the books are stored and which holds
 * all the methods to change the order, the
 * details and the amount of the books.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 */

public class Library {
	
	/**
	 * Stores all books of the library.
	 */
	private Vector<Book> books;
	
	/**
	 * The file where the details of
	 * the books are written in.
	 */
	private File file;
	
	/*
	 * The sorter for the Books' Vector,
	 * to sort it if needed.
	 */
	private BookSortTitle bookSortTitle = new BookSortTitle();
	private BookSortAuthor bookSortAuthor = new BookSortAuthor();
	private BookSortDate bookSortDate = new BookSortDate();
	
	/**
	 * Constructor with no arguments.
	 */
	public Library() {
		books = new Vector<Book>();
	}
	
	/**
	 * Constructor which is needed for
	 * a deep Copy of the library.
	 * 
	 * @param books stores all the books,
	 * which shall exists in the library
	 */
	public Library(Vector<Book> books) {
		this.books = books;
	}
	
	/**
	 * Constructor which is normally used
	 * to create a library
	 * 
	 * @param file the file
	 * @throws FileNotFoundException 
	 * is thrown when the source of the
	 * scanner the <i> file </i> is not found
	 */
	public Library(File file) throws FileNotFoundException {
		
		this.file = file;
		books = new Vector<Book>();
		
		/*
		 * Parse the content of the file
		 * and create Books.
		 */
		Scanner sc = new Scanner(file);
		sc.useDelimiter("[#\n]"); //we use '#' and '\n' as delimiter

		try{
			while(sc.hasNext()) { 		//parse as long as there are tokes
				String title = sc.next();
				String surName = sc.next();
				String lastName = sc.next();
				String month = sc.next();
				String year = sc.next();
				
				/*
				 * Tokens in the file for empty
				 * strings:
				 * 
				 * "XXXXXX" for surName
				 * "XXXXXX" for lastName
				 * "00" for month
				 * "0000" for year
				 */
				
				if(surName.equals("XXXXXX")) {
					surName = "";
				}
				if(lastName.equals("XXXXXX")) {
					lastName = "";
				}
				if(month.equals("00")) {
					month = "";
				}
				if(year.equals("0000")) {
					year = "";
				}
				
				books.add(new Book(title, surName, lastName, month, year));
				
			}
		} catch (NoSuchElementException e) {
			System.out.println("\n\nThere are no further lines or tokens\n"
					+ ">> Maybe an amount of tokens, which isnt divided by 5\n"
					+ "(Exceptionmessage: "
					+ e.getMessage() + ").");
		}
		
		sc.close(); //close Scanner
		
	}
	
	//#######################################################################
	
	/**
	 * Sorts books by title
	 * 
	 * @param countSort
	 * 0 for sorting A to Z
	 * 1 for sorting Z to A
	 */
	public void sortByTitles(int countSort) {
		bookSortTitle.setCount(countSort);
		Collections.sort(books, bookSortTitle);
	}
	
	/**
	 * Sort books by author
	 * 
	 * @param countSort
	 * 0 for <i>surName + lastName </i>
	 * 1 for <i>lastName + "," + surName </i>
	 */
	public void sortByAuthors(int countSort) {
		bookSortAuthor.setCount(countSort);
		Collections.sort(books, bookSortAuthor);
	}
	
	/**
	 * Sort books by date
	 * 
	 * @param countSort
	 * 0 for oldest date first
	 * 1 for newest date first
	 */
	public void sortByDate(int countSort) {
		bookSortDate.setCount(countSort);
		Collections.sort(books, bookSortDate);
	}
	
	//#######################################################################
	
	/**
	 * This method "saves" the state of the Booklist
	 * to the file which is specified in the Library-class.
	 * 
	 * <i> This step cannot be undone! </i>
	 */
	public void writeToFile() {
		
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i < books.size(); i++) {
				Book book = books.get(i);
				String title = book.getTitle();
				String surName = book.getSurName();
				String lastName = book.getLastName();
				String month = book.getMonth();
				String year = book.getYear();
				
				/*
				 * Tokens in the file for empty
				 * strings:
				 * 
				 * "XXXXXX" for surName
				 * "XXXXXX" for lastName
				 * "00" for month
				 * "0000" for year
				 */
				
				if(surName.isEmpty()) {
					surName = "XXXXXX";
				}
				if(lastName.isEmpty()) {
					lastName = "XXXXXX";
				}
				if(month.isEmpty()) {
					month = "00";
				}
				if(year.isEmpty()) {
					year = "0000";
				}
				
				bw.write(title);
				bw.write('#');
				bw.write(surName);
				bw.write('#');
				bw.write(lastName);
				bw.write('#');
				bw.write(month);
				bw.write('#');
				bw.write(year);
				if(i != (books.size() - 1)) {
					bw.newLine();
				}
			}
			
			bw.close();
			fw.close();

		} catch (Exception e) {
			System.out.println("Save did not work properly.\n"
					+ "Errormessage is" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Export the books with the details
	 * and form intended by the programmer.
	 * 
	 * <i> Can be rewritten by other users
	 * to produce other outputs </i>
	 * 
	 * @param file
	 * The file where the output shall be written to.
	 */
	public void export(File file) {
		
		try {
			if(!books.isEmpty()) {
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				Library copyLib = this.getLibraryDeepCopy();
				Vector<Book> copyBooks = copyLib.getBooks();
				
				BookSortTitle titleSorter = new BookSortTitle();
				BookSortAuthor authorSorter = new BookSortAuthor();
				
				bw.write("Titles:");
				
				Collections.sort(copyBooks, titleSorter);
				
				Book book = null;
				
				for(int i = 0; i < copyBooks.size(); i++) {
					book = copyBooks.get(i);
					
					bw.newLine();
					bw.write("\t");
					bw.write("- ");
					bw.write(book.getTitle());
					bw.write(" (");
					bw.write(book.getAuthor());
					bw.write(")");
				}
				
				bw.newLine();
				bw.newLine();
				bw.write("Authors:");
				
				Collections.sort(copyBooks, authorSorter);
								
				book = copyBooks.get(0);
				String lastAuthor = book.getAuthor();
				
				bw.newLine();
				bw.write("\t" + "- " + book.getLastName() + ", " + book.getSurName() + ": ");
				bw.write(": ");
				bw.write(book.getTitle());
					
				for(int i = 1; i < copyBooks.size(); i++) {
					book = copyBooks.get(i);
					
					if(lastAuthor.equals(book.getAuthor())) {
						bw.write(", ");
						bw.write(book.getTitle());
					} else {
						bw.newLine();
						bw.write("\t" + "- " + book.getLastName() + ", " + book.getSurName() + ": ");
						bw.write(": ");
						bw.write(book.getTitle());
					}
					lastAuthor = book.getAuthor();
				}
				

				//Output by date
				BookSortDate dateSorter = new BookSortDate();

				bw.newLine();
				bw.newLine();
				bw.write("Date:");
				bw.newLine();
				
				Collections.sort(copyBooks, titleSorter);
				Collections.sort(copyBooks, dateSorter);
				
				for(int i = 0; i < copyBooks.size(); i++) {
					book = copyBooks.get(i);
					
					if(! (book.getYear().isEmpty())) {
						bw.write("\t" + "- ");
						bw.write(book.getDateWithWords());
						bw.write(": " + book.getTitle());
						
						if(i != (copyBooks.size() - 1)) { //at last iteration don't add a newline
							bw.newLine();
						}
					}
				}
				
				bw.close();
				fw.close();
			}
		} catch (IOException e) {
			System.out.println("Export did not work properly.\n"
					+ "Errormessage is" + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	//#######################################################################

	/**
	 * @param book 
	 * the book which shall be added to the library
	 */
	public void addBook(Book book) {
		books.add(book);
	}

	/**
	 * Delete books in the library.
	 * 
	 * @param delete
	 * the books that shall be deleted
	 */
	public void deleteBooks(int[] delete) {
		
		for(int i = (delete.length - 1); i >= 0; i--) {
			books.remove(delete[i]);
		}
	}
	
	/**
	 * Get all books of the library.
	 * @return Vector with all books.
	 */
	public Vector<Book> getBooks() {
		return books;
	}
	

	/**
	 * Returns the file where the books are stored
	 * @return 
	 * the file where the books are stored
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Get a deep copy of the Library
	 * 
	 * @return A deep copied Vector of
	 * the books.
	 */
	public Library getLibraryDeepCopy() {
		Vector<Book> vectorCopy = new Vector<Book>();
		
		for(int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			vectorCopy.add(new Book(book.getTitle(), book.getSurName(), book.getLastName(), book.getMonth(), book.getYear()));
		}
		
		return new Library(vectorCopy);
	}

}
