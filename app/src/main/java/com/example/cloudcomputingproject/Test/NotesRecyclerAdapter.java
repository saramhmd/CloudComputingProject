package com.example.cloudcomputingproject.Test;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<Note> notesItemArrayList;
    CollectionReference notesCollection;

    public NotesRecyclerAdapter(Context context, ArrayList<Note> notesItemArrayList) {
        this.context = context;
        this.notesItemArrayList = notesItemArrayList;
        notesCollection = FirebaseFirestore.getInstance().collection("medical consulting");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = notesItemArrayList.get(position);

        holder.textName.setText("Name: " + note.getNameNote());


        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, note.getIdNote(), note.getNameNote());
            }
        });
        holder.buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideNoteAndRemoveFromList(note);
            }
        });

//        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
//                viewDialogConfirmDelete.showDialog(context, note.getIdNote());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return notesItemArrayList.size();
    }
    private void hideNoteAndRemoveFromList(Note note) {
        note.setHidden(true);
        notesCollection.document(note.getIdNote()).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Note hidden successfully!", Toast.LENGTH_SHORT).show();
                        notesItemArrayList.remove(note);
                        notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to hide note", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        Button buttonDelete;
        Button buttonUpdate , buttonHide;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonHide = itemView.findViewById(R.id.buttonHidden);


        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String name) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dilog);
            EditText textName = dialog.findViewById(R.id.textName);
            textName.setText(name);
            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
            buttonUpdate.setText("UPDATE");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newName = textName.getText().toString();

                    if (newName.isEmpty()) {
                        Toast.makeText(context, "Please enter all data...", Toast.LENGTH_SHORT).show();
                    } else {
                        if (newName.equals(name)) {
                            Toast.makeText(context, "You didn't change anything", Toast.LENGTH_SHORT).show();
                        } else {
                            Note updatedNote = new Note(id, newName);
                            notesCollection.document(id).set(updatedNote)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, "Note updated successfully!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Failed to update note", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_delete);

            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesCollection.document(id).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}