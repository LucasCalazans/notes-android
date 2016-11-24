package br.com.lucascalazans.notes;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_new)
public class NewActivity extends AppCompatActivity {

    @ViewById(R.id.note_title)
    EditText noteTitle;

    @ViewById(R.id.note_description)
    EditText noteDescription;

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
        Note note = new Note(
                noteTitle.getText().toString(),
                noteDescription.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notesRef = database.getReference("notes");
        notesRef.push().setValue(note);
        cancelAction();
    }
}