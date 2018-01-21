package com.example.ljubica.deutschlernen;

import java.io.Serializable;

/**
 * Created by Ljubica on 16.12.2017.
 */

public class Word implements Serializable {
    String definiterArtikel;
    String word;
    String translation;
    String pluralForm;

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    String lesson;

    public Word(String definiterArtikel, String word, String translation, String pluralForm, String lesson) {
        this.definiterArtikel = definiterArtikel;
        this.word = word;
        this.translation = translation;
        this.pluralForm = pluralForm;
        this.lesson = lesson;
    }

    public String getDefiniterArtikel() {
        return definiterArtikel;
    }

    public void setDefiniterArtikel(String definiterArtikel) {
        this.definiterArtikel = definiterArtikel;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPluralForm() {
        return pluralForm;
    }

    public void setPluralForm(String pluralForm) {
        this.pluralForm = pluralForm;
    }

    @Override
    public String toString() {
        String value = definiterArtikel + " " + word + " " + pluralForm + " " + translation + " " + lesson;
        return value;
    }

    public Word fromString(String word) {
        String parts[] = word.split(" ");
        Word deSerialize = new Word(parts[0], parts[1], parts[2], parts[3], parts[4]);
        return deSerialize;
    }
}
