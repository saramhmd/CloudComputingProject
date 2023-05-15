package com.example.cloudcomputingproject.Test;

import com.google.firebase.firestore.ServerTimestamp;

public class Note {
    String nameNote;
    String idNote;


    public Note() {
    }
    @ServerTimestamp
    private Note timestamp;

    public Note(String idNote, String nameNote) {
        this.nameNote = nameNote;
        this.idNote = idNote;
    }

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }
}