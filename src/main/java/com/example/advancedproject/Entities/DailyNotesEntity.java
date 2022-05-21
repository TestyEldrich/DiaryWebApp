package com.example.advancedproject.Entities;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Daily_Notes")
@Table(
        name = "Daily_Notes"
)
public class DailyNotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "daily_note_id",
            updatable = false
    )
    private Long daily_note_id;
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long user_id;
    @Column(
            name = "text",
            columnDefinition = "TEXT"
    )
    private String text;
    @Column(
            name = "date",
            columnDefinition = "VARCHAR"
    )
    private String date;

    public DailyNotesEntity(Long daily_note_id, Long user_id, String text, String date) {
        this.daily_note_id = daily_note_id;
        this.user_id = user_id;
        this.text = text;
        this.date = date;
    }

    public DailyNotesEntity() {
    }

    public Long getDaily_note_id() {
        return daily_note_id;
    }

    public void setDaily_note_id(Long daily_note_id) {
        this.daily_note_id = daily_note_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DailyNotesEntity{" +
                "daily_note_id=" + daily_note_id +
                ", user_id=" + user_id +
                ", text='" + text + '\'' +
                '}';
    }
}
