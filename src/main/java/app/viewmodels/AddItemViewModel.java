package app.viewmodels;

import app.models.*;

/**
 * ViewModel class that talks to the model on behalf of AddView
 */
public class AddItemViewModel {
    private AddItem model;

    public AddItemViewModel(AddItem model) {
        this.model = model;
    }

    /**
     * Adds a new book to the Inventory
     * @param title The title of the book
     * @param author The author of the book
     * @param ISBN The ISBN for the book
     */
    public void AddBook(String title, String author, String ISBN) {
        Book book = new Book(title, author, ISBN);
        model.addItem(book);
    }

    /**
     * Adds a new article to the Inventory
     * @param title The title of the article
     * @param author The author of the article
     * @param magazine The magazine in which the article is featured in
     */
    public void AddArticle(String title, String author, String magazine) {
        Article article = new Article(title, author, magazine);
        model.addItem(article);
    }

    /**
     * Add a new CD to the Inventory
     * @param title The title of the CD
     */
    public void AddCD(String title) {
        CD cd = new CD(title);
        model.addItem(cd);
    }

    /**
     * Add a new DVD to the Inventory
     * @param title The title of the DVD
     */
    public void AddDVD(String title) {
        DVD dvd = new DVD(title);
        model.addItem(dvd);
    }
}
