package br.com.lucascalazans.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

@EActivity(R.layout.activity_list)
public class ListActivity extends AppCompatActivity implements RVClickListener {

    @ViewById(R.id.add_note)FloatingActionButton fabAdd;
    @ViewById(R.id.notes_list)RecyclerView rvNotesList;

    static private String TAG = "testes";

    List<Note> notes;

    @AfterViews
    protected void init() {
        notes = new ArrayList<>();

        final ListAdapter adapter = new ListAdapter(this, notes);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notesRef = database.getReference("notes");
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notes.clear();
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    note.setKey(noteSnapshot.getKey());
                    notes.add(note);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        fabAdd.setImageBitmap(textAsBitmap("+", 50, Color.WHITE));

        LinearLayoutManager llm = new LinearLayoutManager(this);

        adapter.setClickInterface(this);

        rvNotesList.setHasFixedSize(true);
        rvNotesList.setLayoutManager(llm);
        rvNotesList.setAdapter(adapter);
    }

    @Click(R.id.add_note)
    public void newNote() {
        Intent intent = new Intent(this, NewActivity_.class);
        startActivity(intent);
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    @Override
    public void onClickListener(int position) {
        Intent intent = new Intent(this, EditActivity_.class);
        intent.putExtra("note", notes.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClickListener(int position) {
        Log.d(TAG, "onLongClickListener");
    }
}
