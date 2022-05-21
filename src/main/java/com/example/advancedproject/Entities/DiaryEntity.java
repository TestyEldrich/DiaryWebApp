package com.example.advancedproject.Entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(
        name = "diary"
)

public class DiaryEntity {
    @Id
    @SequenceGenerator(
            name = "diary_sequence",
            sequenceName = "diary_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "diary_sequence"
    )
    @Column(
            name = "diary_id",
            updatable = false
    )
    private Long diary_id;
    @Column(
            name = "title",
            columnDefinition = "TEXT"
    )
    private String title;
    @Column(
            name = "text",
            columnDefinition = "TEXT"
    )
    private String text;
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long user_id;

    public DiaryEntity(String title, String text, Long user_id) {
        this.title = title;
        this.text = text;
        this.user_id = user_id;
    }

    public DiaryEntity() {

    }

    public Long getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(Long diary_id) {
        this.diary_id = diary_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "diary{" +
                "diary_id=" + diary_id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
