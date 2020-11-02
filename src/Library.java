public class Library {
    /**
     * data members of the class
     */
    final int maxBooksNum;
    final int maxPatronNum;
    final int maxBorrowedByPatron;
    int curBooksNum = 0;
    int curPatronsNum = 0;
    Book[] booksArray;
    int[] booksId;


    /**
     * a library constructor
     * @param maxBookCapacity - The maximal number of books this library can hold.
     * @param maxBorrowedBooks - The maximal number of books this library allows a single patron to borrow at the
     * same time.
     * @param maxPatronCapacity - The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity)
    {
        maxBooksNum = maxBookCapacity;
        maxBorrowedByPatron = maxBorrowedBooks;
        maxPatronNum = maxPatronCapacity;
        booksArray = new Book[maxBooksNum]; // null filled array of Books
        booksId = new int[maxBooksNum];
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book - The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book)
    {
        if(curBooksNum < maxBooksNum)
        {
            if(checkIfBookInLib(book))
            {
                for(int i=0; i < maxBooksNum ; i++)
                {
                    if(booksArray[i]== book)
                        return i;
                }
            }
            for (int i=0; i < maxBooksNum ; i++)
            {
                if (booksArray[i] == null)
                {
                    booksArray[i] = book;
                    booksId[i] = i;
                    curBooksNum++;
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * checks if the given book is in the library or not.
     * @param book - the checked book
     * @return - true if the book is in the library, false otherwise.
     */
    boolean checkIfBookInLib(Book book)
    {
        for(int i=0; i<maxBooksNum ; i++)
        {
            if(booksArray[i]== book)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId - The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId)
    {
        return bookId >= 0 && bookId < maxBooksNum;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book - The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book)
    {
        
    }


}
