// Zainab Syed

import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		// Instantiate the class BookList here ...
		
		System.out.println("Welcome to the book program!");
		System.out.println("Would you like to create a book object? (yes/no)");
		
		// creates array to hold all the books added
		BookList b = new BookList();
		
		// gets yes or no input for creating a book
		Scanner scanOption = new Scanner(System.in);
		String option = scanOption.next();
		
		while(!option.equalsIgnoreCase("yes") && !option.equalsIgnoreCase("no")) {
			System.out.println("I'm sorry but "+ option +" is not a valid answer. Please enter yes or no:");
			option = scanOption.next();
		}
		
		// while the answer is yes to keep adding books
		while(!option.equalsIgnoreCase("no")) {
			// Input for author name, title and isbn
			System.out.println("Please enter the author, title and the isbn of the book seperated by /: ");
			String bookInfo = (new Scanner(System.in)).nextLine();
			System.out.println("Got it");
			String [] array;
			array = bookInfo.split("/");
			
			
			//======================================================
//			System.out.println("Author: " + array[0]);
//			System.out.println("Title: " + array[1]);
//			System.out.println("ISBN: " + array[2]);
			//========================================================
			
			// Library book or Bookstore book?
			System.out.println("Now, is it a bookstore book or a library book (enter BB for bookstore or LB for library book): ");
			String type = (new Scanner(System.in)).next();
			
			while(!type.equalsIgnoreCase("BB") && !type.equalsIgnoreCase("LB")) {
				System.out.println("Oops! That's not a valid entry. Please try again: ");
				type = (new Scanner(System.in)).next();
			}
			System.out.println("Got it!");
			
			
			// If Bookstore book 
			if(type.equalsIgnoreCase("BB")) {
				BookstoreBook book = new BookstoreBook(array[0], array[1], array[2]);
				
				// adds book to array list
				b.addBook(book);
				
				// adds price
				System.out.println("Please enter the list price of "+ book.getTitle()+ " by "+ book.getAuthor() +": ");
				double price = (new Scanner(System.in)).nextDouble();
				book.setPrice(price);
				
				// adds sale if necessary
				System.out.println("Is it on sale? (y/n)");
				String choice = (new Scanner(System.in)).next();
				
				while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
					System.out.println("Try again...");
					choice = (new Scanner(System.in)).next();
				}
				
				if(choice.equalsIgnoreCase("y")) {
					System.out.println("Deduction percentage: ");
					String discount = (new Scanner(System.in)).next();
					int sale = Integer.parseInt(discount.replace("%", ""));
					book.setFinalPrice(price, sale);
					// book.setSale(discount);
				}
				else if(choice.equalsIgnoreCase("n")) {
					book.setFinalPrice(price, 100); // sale set to 100, so it is multiplied by 1
				}
				
				System.out.println("Got it!");
				
				// prints book info
				System.out.println("Here is your bookstore book information");
				System.out.println(book.toString());
			}
			
			// if its a Library Book
			else if (type.equalsIgnoreCase("LB")) {
				LibraryBook Lbook = new LibraryBook(array[0], array[1], array[2]);
				
				// adds book
				b.addBook(Lbook);
				
				System.out.println("Here is your library book information");
				// random floor generator
				Random random = new Random();
				int randNum = random.nextInt(100) + 1;
				
				Lbook.callNumber(randNum, array[0], array[2]);	
				System.out.println(Lbook.toString());
			}
			
			System.out.println("Would you like to create a book object?(yes/no): ");
			option = scanOption.next();
			while(!option.equalsIgnoreCase("yes") && !option.equalsIgnoreCase("no")) {
				System.out.println("I'm sorry but "+ option +" is not a valid answer. Please enter yes or no:");
				option = scanOption.next();
			}
		}
		// sorts book list
		b.sortByType();
		System.out.println("Sure! \n\nHere are all your books...\n");
		
		// prints all library books
		b.printLibraryBook();
		System.out.println("_ _ _ _\n");
		
		// prints all bookstore books
		b.printBookstoreBook();
		System.out.println("_ _ _ _\n");
		System.out.println("Take care now!");
		
	}
}

//___________________________
abstract class Book {
	private String author;
	
	private String title;
	
	private String isbn;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public Book(String author, String title, String isbn) {
		this.author = author;
		this.title = title;
		this.isbn = isbn;
	} 
	
	public String toString () {
		return isbn + "-" + title +" by "+ author;
	}
	
	
	//code of the abstract class Book
	//You may add an abstract method if you see fit...
}

//___________________________
class BookstoreBook extends Book {

	// fields and specific code to the BookstoreBook class goes here
	private double price;
	private double sale;
	private double finalPrice;
	
	public double getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale % 100;
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	// calculates the price after sales
	public void setFinalPrice (double price, int sale) {
		this.price = price;
		this.sale = sale % 100;
		double discount = price * (this.sale / 100.0);
		this.finalPrice = price - discount;
	}
	
	public String toString() {
		// System.out.printf("["+ super.toString() +", $"+ price +" listed for $%.2f", finalPrice);
		return "["+ super.toString() +", $"+ price +" listed for $" + String.format("%.2f", finalPrice) +"]";
	}
	
	public BookstoreBook(String author, String title, String isbn) {
		super(author, title, isbn);
	}
}

//___________________________
class LibraryBook extends Book {
	// fields and specific code to the LibraryBook class goes here
	private String callNum;
	
	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	// creates the callNumber using random floor #, first 3 letters of author name, and last digit of isbn
	public String callNumber(int floorNum, String author, String isbn) {
		String name = author.substring(0, 3);
		int length = isbn.length();
		String isbnLast = isbn.substring(length-1);
		String callNum = floorNum + "." + name +"."+ isbnLast;
		this.callNum = callNum;
		return callNum;
	}
	
	// modifies toString from class Book
	public String toString() {
		return "[" + super.toString() + "-" + callNum + "]";
	}
	
	public LibraryBook(String author, String title, String isbn) {
		super(author, title, isbn);
	}
	
}

//___________________________
class BookList {
	// array that holds all books
	private Book[] list;
	
	// initialize the array
	public BookList() {
		list = new Book[100];
		for (int index = 0; index < 100; index++) {
			list[index] = null;
		}
	}
	
	// adds book to the next available spot in the array 
	public void addBook (Book b) {
		boolean full = true;
		for(int i = 0; i < 100; i++) {
			if(list[i] == null) {
				list[i] = b;
				full = false;
				break;
			}
		}
		// means that the if statement above was never reached; no null space in list
		if(full) {
			System.out.println("Book limit reached");
		}
	}
	
	// prints the total amount of Bookstore books and each books information
	public void printBookstoreBook () {
		System.out.print("Bookstore Books");
		int count = 0;
		for (Book e : list) {
			if(e instanceof BookstoreBook) {
				count++;
			}
		}
		System.out.println("("+ count +")\n");
		for (Book e : list) {
			if(e instanceof BookstoreBook) {
				System.out.println("\t" + e);
			}
		}
	}
	
	// prints the total amount of Library books and each books information
	public void printLibraryBook () {
		System.out.print("Library Books");
		int count = 0;
		for (Book b : list) {
			if(b instanceof LibraryBook) {
				count++;
			}
		}
		System.out.println("("+ count +")\n");
		for (Book b : list) {
			if(b instanceof LibraryBook) {
				System.out.println("\t" + b);
			}
		}
	}
	
	// sorts the books by type using another array
	public void sortByType () {
		Book [] buffer = new Book [100];
		int index = 0;
		
		for (Book b : list) {
			if (b instanceof LibraryBook) {
				buffer[index++] = b;
			}
		}
		
		for (Book b : list) {
			if (b instanceof BookstoreBook) {
				buffer[index++] = b;
			}
		}
		
		index = 0;
		for (Book b : buffer) {
			list[index++] = b;
		}
	}
}