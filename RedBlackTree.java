import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    // Instance variables
    private Book book;  // Sentinel node representing null
    private Book root;  // Root of the Red-Black Tree
    private int colorFlip;  // Counter to track color flip operations
    private MinHeap reservationHeap;  // MinHeap for managing reservations

    // Constructor
    public RedBlackTree() {
        // Initialize the sentinel node
        book = new Book(0, "", "", "");
        book.setColor(0);
        book.setLeft(null);
        book.setRight(null);
        
        // Initialize the root and other variables
        root = book;
        colorFlip = 0;
        reservationHeap = new MinHeap();
    }

    // Helper method to search the tree for a given key
    private Book searchTreeHelper(Book node, int key) {
        if (node == book || key == node.getBookId()) {
            return node;
        }
        if (key < node.getBookId()) {
            return searchTreeHelper(node.getLeft(), key);
        }
        return searchTreeHelper(node.getRight(), key);
    }

    // Public method to get the sentinel node
    public Book getBook(){
        return this.book;
    }

    // Balancing the tree after deletion
    private void deleteFix(Book x) {
        while (x != root && x.getColor() == 0) {
            if (x == x.getParent().getLeft()) {
                Book s = x.getParent().getRight();
                if (s.getColor() == 1) {
                    colorFlip += 2;
                    s.setColor(0);
                    x.getParent().setColor(1);
                    leftRotate(x.getParent());
                    s = x.getParent().getRight();
                }
                if (s.getLeft().getColor() == 0 && s.getRight().getColor() == 0) {
                    colorFlip += 1;
                    s.setColor(1);
                    x = x.getParent();
                } else {
                    if (s.getRight().getColor() == 0) {
                        colorFlip += 2;
                        s.getLeft().setColor(0);
                        s.setColor(1);
                        rightRotate(s);
                        s = x.getParent().getRight();
                    }
                    colorFlip += 2;
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(0);
                    s.getRight().setColor(0);
                    leftRotate(x.getParent());
                    x = root;
                }
            } else {
                Book s = x.getParent().getLeft();
                if (s.getColor() == 1) {
                    colorFlip += 2;
                    s.setColor(0);
                    x.getParent().setColor(1);
                    rightRotate(x.getParent());
                    s = x.getParent().getLeft();
                }
                if (s.getRight().getColor() == 0 && s.getRight().getColor() == 0) {
                    colorFlip += 1;
                    s.setColor(1);
                    x = x.getParent();
                } else {
                    if (s.getLeft().getColor() == 0) {
                        colorFlip += 2;
                        s.getRight().setColor(0);
                        s.setColor(1);
                        leftRotate(s);
                        s = x.getParent().getLeft();
                    }
                    colorFlip += 2;
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(0);
                    s.getLeft().setColor(0);
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(0);
    }

    // Method to replace one subtree with another
    private void rbTransplant(Book u, Book v) {
        if (u.getParent() == null) {
            this.root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
    
        v.setParent(u.getParent());
    }
    
    // Helper method to find the minimum node in a subtree
    private Book findMinimum(Book node) {
        while (node.getLeft() != book) {
            node = node.getLeft();
        }
        return node;
    }

    // Helper method to delete a node with a given key
    private void deleteNodeHelper(Book node, int key) {
        Book z = book;
    
        while (node != book) {
            if (node.getBookId() == key) {
                z = node;
            }
            if (node.getBookId() <= key) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }
    
        if (z == book) {
            System.out.println("Cannot find key in the tree");
            return;
        }
    
        Book y = z;
        int yOriginalColor = y.getColor();
    
        Book x;
    
        if (z.getLeft() == book) {
            x = z.getRight();
            rbTransplant(z, z.getRight());
        } else if (z.getRight() == book) {
            x = z.getLeft();
            rbTransplant(z, z.getLeft());
        } else {
            y = findMinimum(z.getRight());
            yOriginalColor = y.getColor();
            x = y.getRight();
    
            if (y.getParent() == z) {
                x.setParent(y);
            } else {
                rbTransplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
    
            rbTransplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
    
        if (yOriginalColor == 0) {
            deleteFix(x);
        }
    }
    
    // Helper method to fix the Red-Black Tree after insertion
    private void fixInsert(Book k) {
            while (k.getParent()!=null && k.getParent().getColor() == 1) {
            if ( k.getParent().getParent() != null && k.getParent() == k.getParent().getParent().getRight()) {
                Book u = k.getParent().getParent().getLeft();
                if (u.getColor() == 1) {
                    colorFlip += 3;
                    u.setColor(0);
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getLeft()) {
                        k = k.getParent();
                        rightRotate(k);
                    }
                    colorFlip += 2;
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    leftRotate(k.getParent().getParent());
                }
            } else {
                Book u = k.getParent().getParent().getRight();
                if (u.getColor() == 1) {
                    colorFlip += 3;
                    u.setColor(0);
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getRight()) {
                        k = k.getParent();
                        leftRotate(k);
                    }
                    colorFlip += 2;
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    rightRotate(k.getParent().getParent());
                }
                if (k == root) {
                    break;
                }
            }
        }
        root.setColor(0);
    }
    
    // public void fixInsert(Book curr_node) {
    //     while (curr_node.getParent().getColor() == 0) {
    //         if (curr_node.getParent() == curr_node.getParent().getParent().getLeft()) {
    //             Book parent_sibling = curr_node.getParent().getParent().getRight();

    //             if (parent_sibling.getColor() == 0) {
    //                 if (curr_node == curr_node.getParent().getRight()) {
    //                     curr_node = curr_node.getParent();
    //                     leftRotate(curr_node);
    //                 }
    //                 updateColorFlip(curr_node.getParent(),true);
    //                 updateColorFlip(curr_node.getParent().getParent(),false);
    //                 rightRotate(curr_node);(curr_node.getParent().getParent());
    //             } else {
    //                 updateColorFlip(parent_sibling,true);
    //                 updateColorFlip(curr_node.parent,true);
    //                 updateColorFlip(curr_node.parent.parent,false);
    //                 curr_node = curr_node.parent.parent;
    //             }
    //         } else {
    //             RBTreeNode parent_sibling = curr_node.parent.parent.left;
    //             if (parent_sibling.isBlackNode) {
    //                 if (curr_node == curr_node.parent.left) {
    //                     curr_node = curr_node.parent;
    //                     r_rotation(curr_node);
    //                 }
    //                 updateColorFlip(curr_node.parent,true);
    //                 updateColorFlip(curr_node.parent.parent,false);
    //                 lRotation(curr_node.parent.parent);
    //             } else {
    //                 updateColorFlip(parent_sibling,true);
    //                 updateColorFlip(curr_node.parent,true);
    //                 updateColorFlip(curr_node.parent.parent,false);
    //                 curr_node = curr_node.parent.parent;
    //             }
    //         }

    //         if (curr_node == root) {
    //             break;
    //         }
    //     }
    //     root.isBlackNode = true;
    // }
   
    // Helper method for left rotation in Red-Black Tree
    private void leftRotate(Book x) {
        Book y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != book) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    // Helper method for right rotation in Red-Black Tree
    private void rightRotate(Book x) {
        Book y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != book) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }

    // Helper method to find the closest node to a given key
    private Book closestNode(Book node, int bookId, int[] minDiff, Book[] minDiffBook) {
        if (node == book) {
            return null;
        }
        if (node.getBookId() == bookId) {
            minDiffBook[0] = node;
            return node;
        }
        // Update minDiff and minDiffBook by checking current node value
        if (minDiff[0] > Math.abs(node.getBookId() - bookId)) {
            minDiff[0] = Math.abs(node.getBookId() - bookId);
            minDiffBook[0] = node;
        }
        // If k is less than node's key, move in the left subtree, else in the right subtree
        if (bookId < node.getBookId()) {
            return closestNode(node.getLeft(), bookId, minDiff, minDiffBook);
        } else {
            return closestNode(node.getRight(), bookId, minDiff, minDiffBook);
        }
    }

    // Public method to find the closest book to a target ID
    public List<String> findClosestBook(int targetId) {
        Book closestBook = findClosestNode(root, targetId, null);
        if (closestBook == null) {
            System.out.println("No books found in the tree.");
            return new ArrayList<>();
        }

        List<Book> closestBooks = new ArrayList<>();
        int distance = Math.abs(closestBook.getBookId() - targetId);
        findTies(root, targetId, distance, closestBooks);

        closestBooks.sort((book1, book2) -> Integer.compare(book1.getBookId(), book2.getBookId()));

        List<String> closestBooksStr = new ArrayList<>();
        for (Book book : closestBooks) {
            closestBooksStr.add(book.toString());
        }

        return closestBooksStr;
    }

    // Helper method to find the closest node to a given key
    private Book findClosestNode(Book node, int targetId, Book currentClosest) {
        if (node == book) {
            return currentClosest;
        }
        if (currentClosest == null || Math.abs(node.getBookId() - targetId) < Math.abs(currentClosest.getBookId() - targetId)) {
            currentClosest = node;
        }
        if (node.getBookId() > targetId) {
            return findClosestNode(node.getLeft(), targetId, currentClosest);
        } else {
            return findClosestNode(node.getRight(), targetId, currentClosest);
        }
    }

    // Helper method to find tied books at the same distance
    private void findTies(Book node, int targetId, int distance, List<Book> closestBooks) {
        if (node == book) {
            return;
        }
        int currentDistance = Math.abs(node.getBookId() - targetId);
        if (currentDistance == distance && !closestBooks.contains(node)) {
            closestBooks.add(node);
        }
        findTies(node.getLeft(), targetId, distance, closestBooks);
        findTies(node.getRight(), targetId, distance, closestBooks);
    }

    // Public method to insert a book into the Red-Black Tree
    public void insertBook(int bookId, String bookName, String authorName, String availabilityStatus) {
        Book book = new Book(bookId, bookName, authorName, availabilityStatus);
        book.setParent(null);
        book.setLeft(this.book);
        book.setRight(this.book);
        book.setColor(1);

        Book y = null;
        Book x = this.root;

        while (x != this.book) {
            y = x;
            if (book.getBookId() < x.getBookId()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        book.setParent(y);

        if (y == null) {
            this.root = book;
        } else if (book.getBookId() < y.getBookId()) {
            y.setLeft(book);
        } else {
            y.setRight(book);
        }

        if (book.getParent() == null) {
            book.setColor(0);
            return;
        }

        if (book.getParent().getParent() == null) {
            return;
        }

        fixInsert(book);
    }

    // Public method to delete a book from the Red-Black Tree
    public String deleteBook(int bookId) {
        Book book = searchBook(bookId);
        String res = "";
        if (book != null) {
           
            res = book.deleteBook(book);

            deleteNodeHelper(this.root, bookId);
        }
        return res;
    }

    // Public method to search for a book by its ID
    public Book searchBook(int bookId) {
        return searchTreeHelper(this.root, bookId);
    }

    // Public method to print information about a book by its ID
    public String printBook(int bookId) {
        Book book = searchTreeHelper(this.root, bookId);
        if (book != this.book) {
            return book.toString();
        }
        return null;
    }

    // Public method to print information about a range of books
    public List<String> printBooks(int bookId1, int bookId2) {
        List<String> arr = new ArrayList<>();
        for (int bookId = bookId1; bookId <= bookId2; bookId++) {
            String printedBook = printBook(bookId);
            if (printedBook != null) {
                arr.add(printedBook);
            }
        }
        return arr;
    }

    // Public method to borrow a book by a patron
    public String borrowBook(int patronId, int bookId, int patronPriority, LocalDateTime timeOfReservation) {
        Book book = searchBook(bookId);
        String res= "";
        if (book != null) {
            res = book.borrowBook(patronId, patronPriority, timeOfReservation);
        }
        return res;
    }

    // Public method to return a book by a patron
    public String returnBook(int patronId, int bookId) {
        Book book = searchBook(bookId);
        String res= "";
        if (book != null) {
            res = book.returnBook(patronId);
            System.out.print(res);
        }
        return res;
    }

    // Public method to get the color flip count
    public String colorFlipCount() {
        System.out.println(this.colorFlip);
        return String.format("Colour Flip Count: %d\n", this.colorFlip);
    }

    // Helper method for in-order traversal to count transformations
    public int inOrderHelper(Book node) {
        int transformations = 0;
        if (node != this.book) {
            transformations += inOrderHelper(node.getLeft());
            if (node.getColor() == 0) {
                transformations += node.getTransformations();
            }
            transformations += inOrderHelper(node.getRight());
        }
        return transformations;
    }

    // Public method to terminate the program
    public void quit() {
        System.out.println("Program Terminated!!");
    }
}
