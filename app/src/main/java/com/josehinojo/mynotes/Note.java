package com.josehinojo.mynotes;

public class Note {

    private int id;
    private String title;
    private String content;

    public Note(){}

    public Note(String content){
        this.content = content;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent(){
        return content;
    }

    @Override
    public String toString() {
        return title + "\n" + content;
    }
}
