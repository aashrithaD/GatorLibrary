import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class GatorLibrary {

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             FileWriter writer = new FileWriter("output_file.txt")) {

            String line;
            while ((line = reader.readLine()) != null) {
                String inputLine = line.trim();
                String[] inParams = inputLine.substring(inputLine.indexOf("(") + 1, inputLine.indexOf(")")).split(",");

                if (inputLine.startsWith("InsertBook")) {
                    int bookId = Integer.parseInt(inParams[0].trim());
                    String bookName = inParams[1].trim().replace("\"", "");
                    String authorName = inParams[2].trim().replace("\"", "");
                    String availabilityStatus = inParams[3].trim().replace("\"", "");
                     // Insert the book into the Red-Black Tree
                    redBlackTree.insertBook(bookId, bookName, authorName, availabilityStatus);
                    // Assuming the insertBook method does not return any value

                    writer.write("\n");
                } else if (inputLine.startsWith("PrintBook")) {
                    // Print book details or a range of books based on the command
                    if (inParams.length == 1) {
                        int bookId = Integer.parseInt(inParams[0].trim());
                        Book book = redBlackTree.searchBook(bookId);
                        if (book != redBlackTree.getBook()) {
                            writer.write(book.toString());
                        } else {
                            writer.write("Book " + bookId + " not found in the Library\n");
                        }
                        writer.write("\n");
                    } else {
                        int bookId1 = Integer.parseInt(inParams[0].trim());
                        System.out.println(bookId1);
                        int bookId2 = Integer.parseInt(inParams[1].trim());
                        List<String> arr = redBlackTree.printBooks(bookId1, bookId2);
                        for (String i : arr) {
                            writer.write(i);
                        }
                        writer.write("\n");
                    }
                } else if (inputLine.startsWith("BorrowBook")) {
                    // Borrow a book and record the transaction
                    int patronId = Integer.parseInt(inParams[0].trim());
                    int bookId = Integer.parseInt(inParams[1].trim());
                    int patronPriority = Integer.parseInt(inParams[2].trim());
                    LocalDateTime timeOfReservation = java.time.LocalDateTime.now();

                    writer.write(redBlackTree.borrowBook(patronId, bookId, patronPriority, timeOfReservation));
                    // Assuming borrowBook method does not return any value
                    // writer.write("Book " + bookId + " Reserved by Patron " + patronId + "\n\n");
                    writer.write("\n");
                } else if (inputLine.startsWith("ReturnBook")) {
                    int patronId = Integer.parseInt(inParams[0].trim());
                    int bookId = Integer.parseInt(inParams[1].trim());
                    writer.write(redBlackTree.returnBook(patronId, bookId));
                    // Assuming returnBook method does not return any value
                    // writer.write("Book " + bookId + " returned by Patron " + patronId + "\n\n");
                    writer.write("\n");
                } else if (inputLine.startsWith("FindClosestBook")) {
                    int targetId = Integer.parseInt(inParams[0].trim());
                    List<String> p = redBlackTree.findClosestBook(targetId);
                    for (String i : p) {
                        writer.write(i);
                    }
                    writer.write("\n");
                } else if (inputLine.startsWith("DeleteBook")) {
                    int bookId = Integer.parseInt(inParams[0].trim());
                    writer.write(redBlackTree.deleteBook(bookId));
                    writer.write("\n");
                } else if (inputLine.startsWith("ColorFlipCount")) {
                    writer.write(redBlackTree.colorFlipCount());
                    // Assuming colorFlipCount directly prints to the console.
                    writer.write("\n");
                } else if (inputLine.startsWith("Quit")) {
                    writer.write("Program Terminated!!\n");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

