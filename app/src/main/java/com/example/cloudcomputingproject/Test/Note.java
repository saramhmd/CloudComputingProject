package com.example.cloudcomputingproject.Test;

import com.google.firebase.firestore.ServerTimestamp;

public class Note {
    String nameNote;
    String idNote;
    private boolean hidden;

    public Note(boolean hidden) {
        this.hidden = hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

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