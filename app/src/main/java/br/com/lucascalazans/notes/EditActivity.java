package br.com.lucascalazans.notes;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_new)
public class EditActivity extends AppCompatActivity {

    @ViewById(R.id.note_title) EditText noteTitle;
    @ViewById(R.id.note_description) EditText noteDescription;

    Note note;

    @AfterViews
    public void init() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        noteTitle.setText(note.getTitle());
        noteDescription.setText(note.getDescription());
    }

    @Click(R.id.cancel_action)
    public void cancelAction() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Click(R.id.save_action)
    public void saveAction() {
        if( noteTitle.getText().toString().length() == 0 &&
            noteDescription.getText().toString().length() == 0) {
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notesRef = database.getReference("notes").child(note.getKey());

        note.setTitle(noteTitle.getText().toString());
        note.setDescription(noteDescription.getText().toString());
        note.setKey(null);

        notesRef.setValue(note);
        cancelAction();
    }
}
