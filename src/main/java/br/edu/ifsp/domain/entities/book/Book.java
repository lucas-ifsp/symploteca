package br.edu.ifsp.domain.entities.book;

public class Book {
    private Integer id;
    private Integer edition;
    private Integer numberOfPages;
    private String name;
    private String authors;
    private String publisher;
    private String isbn;

    private BookGenre gender;
    private BookStatus status;

    public Book() {
        status = BookStatus.AVAILABLE;
    }

    public Book(Integer edition, Integer numberOfPages, String name, String authors, String publisher, String isbn, BookGenre gender, BookStatus status) {
        this(null , edition,  numberOfPages,  name,  authors,  publisher,  isbn,  gender,  status);
    }

    public Book(Integer id, Integer edition, Integer numberOfPages, String name, String authors, String publisher, String isbn, BookGenre gender, BookStatus status) {
        this.id = id;
        this.edition = edition;
        this.numberOfPages = numberOfPages;
        this.name = name;
        this.authors = authors;
        this.publisher = publisher;
        this.isbn = isbn;
        this.gender = gender;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookGenre getGenre() {
        return gender;
    }

    public void setGenre(BookGenre gender) {
        this.gender = gender;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", edition=" + edition +
                ", numberOfPages=" + numberOfPages +
                ", name='" + name + '\'' +
                ", authors='" + authors + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", gender=" + gender +
                ", status=" + status +
                '}';
    }
}
