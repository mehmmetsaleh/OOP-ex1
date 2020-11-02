import java.util.regex.Pattern;

public class Patron {
    /**
     * data members of the class.
     */

    final String firstName;
    final String lastName;
    int comicVal;
    int dramaticVal;
    int educationalVal;
    int enjoymentThreshold;
    int booksBorrowedByPatron = 0;

    /**
     * class constructor
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency,
           int dramaticTendency, int educationalTendency, int patronEnjoymentThreshold) {
        firstName = patronFirstName;
        lastName = patronLastName;
        comicVal = comicTendency;
        dramaticVal = dramaticTendency;
        educationalVal = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name,
     * separated by a single white space. For example, if the patron's first name is "Ricky" and his last name
     * is "Bobby", this method will return the String "Ricky Bobby".
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * Parameters:
     *
     * @param book - The book to asses.
     * @return - the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        return comicVal * book.comicValue + dramaticVal * book.dramaticValue + educationalVal * book.educationalValue;
    }

    /**
     * Returns true if this patron will enjoy the given book, false otherwise.
     *
     * @param book - The book to asses.
     * @return true if this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return this.getBookScore(book) > enjoymentThreshold;
    }
}
