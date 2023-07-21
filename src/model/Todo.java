package model;

public class Todo {
    private int id;
    private String title;
    private String note;
    private User user;

    public Todo() {
    }

    public Todo(int id, String title, String note, User user) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}