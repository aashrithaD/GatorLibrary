import java.time.LocalDateTime;
import java.util.List;

// Class representing a Book
public class Book {
    // Fields
    private int bookId;
    private String bookName;
    private String authorName;
    private String availabilityStatus;
    private int borrowedBy;
    private MinHeap reservationHeap;
    private Book parent;
    private Book left;
    private Book right;
    private int color;
    private int transformations;

    // Constructor
    public Book(int bookId, String bookName, String authorName, String availabilityStatus) {
        // Initializing fields
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.availabilityStatus = availabilityStatus;
        this.borrowedBy = -1; // Assuming -1 indicates no borrower
        this.reservationHeap = new MinHeap(); // Assuming MinHeap is implemented
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = 1;
        this.transformations = -1;
    }

    // Getter methods
    public int getColor() {
        return this.color;
    }

    public int getBookId() {
        return this.bookId;
    }

    public int getTransformations() {
        return this.transformations;
    }

    public MinHeap getReservationHeap() {
        return this.reservationHeap;
    }

    public Book getLeft() {
        return this.left;
    }

    // Setter method for the left child
    public void setLeft(Book left){
        this.left = left;
    }

    public Book getRight() {
        return this.right;
    }

    // Setter method for the right child
    public void setRight(Book right){
        this.right = right;
    }

    public Book getParent() {
        return this.parent;
    }

    // Setter method for the parent
    public void setParent(Book parent){
        this.parent = parent;
    }

    // Setter method for the color with transformation tracking
    public void setColor(int value) {
        this.transformations += 1;
        this.color = value;
    }

    // String representation of the Book
    public String toString() {
        List<Patron> patrons = reservationHeap.getAllElements();
        String reservationsStr = "";

        for (int i = 0; i < patrons.size(); i++) {
            reservationsStr += patrons.get(i).getPatronId();

            if (i < patrons.size() - 1) {
                reservationsStr += ", ";
            }
        }

        String bookInfo = String.format(
                "BookId = %d\nTitle = %s\nAuthor = %s\nAvailability = %s\nBorrowedBy = %s\nReservations = %s%n",
                this.bookId, this.bookName, this.authorName, this.availabilityStatus, this.borrowedBy == -1 ? "None" : this.borrowedBy, reservationsStr == "" ? "[]" : "["+reservationsStr+"]");
        return bookInfo;
    }

    // Private method to insert a reservation into the heap
    private void insertIntoReservation(int patronId, int patronPriority, LocalDateTime timeOfReservation) {
        Patron patron = new Patron(patronId, patronPriority, timeOfReservation); // Assuming Patron class is implemented
    
        Patron alreadyReservedPatron = null;
        for (Patron p : reservationHeap.getAllElements()) {
            if (p.getPatronId() == patronId) {
                alreadyReservedPatron = p;
                break;
            }
        }
    
        if (alreadyReservedPatron != null) {
            alreadyReservedPatron.setPatronPriority(patronPriority);
            System.out.printf("Book %d Reservation Updated for Patron %d%n", this.bookId, patronId, timeOfReservation);
        } else {
            System.out.printf("Book %d Reserved By Patron %d%n", this.bookId, patronId, timeOfReservation);
            reservationHeap.insert(patron);
        }
    }
    
    // Method to borrow a book by a patron
    public String borrowBook(int patronId, int patronPriority, LocalDateTime timeOfReservation) {
        if (this.borrowedBy == -1) {
            this.borrowedBy = patronId;
            this.availabilityStatus = "No";
            return String.format("Book %d Borrowed by patron %d%n", this.bookId, this.borrowedBy);
        } else {
            // Add patron to reservations
            this.availabilityStatus = "No";
            this.insertIntoReservation(patronId, patronPriority, timeOfReservation);
            return String.format("Book %d Reserved by patron %d%n", this.bookId, patronId);
        }
    }

    // Method to return a book by a patron
    public String returnBook(int patronId) {
        if (this.borrowedBy == patronId) {
            this.borrowedBy = -1;
            this.availabilityStatus = "Yes";
            String res = String.format("Book %d Returned by patron %d%n", this.bookId, patronId);

            // Check if there are reservations
            if (!reservationHeap.isEmpty()) {
                Patron nextPatron = reservationHeap.removeMin();
                this.borrowedBy = nextPatron.getPatronId();
                this.availabilityStatus = "No";
                return res + String.format("Book %d Allotted to patron %d%n", this.bookId, this.borrowedBy);
            }
            return res;
        }
        return "";
    }

    // Method to delete a book and cancel reservations
    public String deleteBook(Book book) {
        String reservationsStr = String.join(", ",
                    book.getReservationHeap().getAllElements().stream()
                            .map(patron -> String.valueOf(patron.getPatronId()))
                            .toArray(String[]::new));

        if (!book.reservationHeap.isEmpty()) {
            if(book.reservationHeap.getAllElements().size()> 1){
                 return String.format("Book %d is no longer available. Reservations made by Patrons %s have been cancelled!\n",
                        book.getBookId(), reservationsStr);
            }
            else if(book.reservationHeap.getAllElements().size() == 1){
                return String.format("Book %d is no longer available. Reservations made by Patron %s have been cancelled!\n",
                        book.getBookId(), reservationsStr);
            }
            else {
                return String.format("Book %d is no longer available%n", book.getBookId());
            }
        }
        else{
            return String.format("Book %d is no longer available %n", book.getBookId());
        }
    }

}
