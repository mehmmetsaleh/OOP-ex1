public class Library {
    /**
     * data members of the class
     */
    final int maxBooksNum;
    final int maxPatronsNum;
    final int maxBorrowedByPatron;
    int curBooksNum = 0;
    int curPatronsNum = 0;
    Book[] booksArray;
    Patron[] patronsArray;

    /**
     * a library constructor
     *
     * @param maxBookCapacity   - The maximal number of books this library can hold.
     * @param maxBorrowedBooks  - The maximal number of books this library allows a single patron to borrow at the
     *                          same time.
     * @param maxPatronCapacity - The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        maxBooksNum = maxBookCapacity;
        maxBorrowedByPatron = maxBorrowedBooks;
        maxPatronsNum = maxPatronCapacity;
        booksArray = new Book[maxBooksNum]; // null filled array of Books
        patronsArray = new Patron[maxPatronsNum]; // null filled array of Patrons
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @param book - The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        for (int i = 0; i < maxBooksNum; i++) {
            if (booksArray[i] == book) {
                return i;
            }
        }
        if (curBooksNum < maxBooksNum) {
            for (int i = 0; i < maxBooksNum; i++) {
                if (booksArray[i] == null) {
                    booksArray[i] = book;
                    curBooksNum++;
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId - The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return bookId >= 0 && bookId < maxBooksNum && booksArray[bookId] != null;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book - The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < maxBooksNum; i++) {
            if (booksArray[i] == book) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId - The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        return isBookIdValid(bookId) && booksArray[bookId].getCurrentBorrowerId() == -1;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron - The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i = 0; i < maxPatronsNum; i++) {
            if (patronsArray[i] == patron) {
                return i;
            }
        }
        if (curPatronsNum < maxPatronsNum) {
            for (int i = 0; i < maxPatronsNum; i++) {
                if (patronsArray[i] == null) {
                    patronsArray[i] = patron;
                    curPatronsNum++;
                    return i;
                }
            }
        }
        return -2;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId - The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return patronId >= 0 && patronId < maxPatronsNum && patronsArray[patronId] != null;
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     *
     * @param patron - The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < maxPatronsNum; i++) {
            if (patronsArray[i] == patron) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed, and if
     * the patron will enjoy this book.
     *
     * @param bookId   - The id number of the book to borrow.
     * @param patronId - The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {

        if (isBookIdValid(bookId) && isPatronIdValid(patronId)) {
            Patron thePatron = patronsArray[patronId];
            Book theBook = booksArray[bookId];
            if (isBookAvailable(bookId) && (thePatron.booksBorrowedByPatron < maxBorrowedByPatron)
                    && thePatron.willEnjoyBook(theBook)) {
                thePatron.booksBorrowedByPatron++;
                theBook.setBorrowerId(patronId);
                return true;
            }
        }
        return false;
    }

    /**
     * Return the given book.
     *
     * @param bookId - The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId) && booksArray[bookId].getCurrentBorrowerId() != -1) {
            int curBorrowerID = booksArray[bookId].getCurrentBorrowerId();
            patronsArray[curBorrowerID].booksBorrowedByPatron--;
            booksArray[bookId].returnBook();
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he will enjoy,
     * if any such exist.
     *
     * @param patronId - The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        if (isPatronIdValid(patronId)) {
            int mostEnjoyed = patronsArray[patronId].enjoymentThreshold;
            Book mostEnjoyedBook = null;
            for (int i = 0; i < maxBooksNum; i++) {
                if (isBookAvailable(i) && patronsArray[patronId].willEnjoyBook(booksArray[i])) {
                    if (patronsArray[patronId].getBookScore(booksArray[i]) > mostEnjoyed) {
                        mostEnjoyed = patronsArray[patronId].getBookScore(booksArray[i]);
                        mostEnjoyedBook = booksArray[i];
                    }
                }
            }
            return mostEnjoyedBook;
        }
        return null;
    }
}
