package com.evansitzes.razorbot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by evan on 2/27/17.
 */
@Entity
@Table(name = "swear_words")
public class SwearWordEntity {

    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column(name = "swear_words")
    private String swearWords;

    public SwearWordEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSwearWords() {
        return swearWords;
    }

    public void setSwearWords(final String swearWords) {
        this.swearWords = swearWords;
    }

    @Override
    public String toString() {
        return name + " score: " + swearWords;
    }
}
