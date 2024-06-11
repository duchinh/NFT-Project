/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Tweet {
    private String id;
    private List<String> hashtag;
    private String author;
    private String date;
    private String content;
    private long like;

    @Override
    public String toString() {
        return "Tweet{" + "id=" + id + ", hashtag=" + hashtag + ", author=" + author + ", date=" + date + ", content=" + content + ", like=" + like + '}';
    }

    public Tweet() {
    }
    
    public Tweet(String id, List<String> hashtag, String author, String date, String content, long like) {
        this.id = id;
        this.hashtag = hashtag;
        this.author = author;
        this.date = date;
        this.content = content;
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }
   



}