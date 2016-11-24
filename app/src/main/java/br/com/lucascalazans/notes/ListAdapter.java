package br.com.lucascalazans.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<Note> notes;
    private LayoutInflater layoutInflater;
    private RVClickListener clickListener;

    public ListAdapter(Context context, List<Note> list) {
        this.notes = list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setClickInterface(RVClickListener clickInterface) {
        this.clickListener = clickInterface;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_list_layout, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
        holder.description.setText(notes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        public ListViewHolder(View listItem) {
            super(listItem);

            title = (TextView) listItem.findViewById(R.id.item_title);
            description = (TextView) listItem.findViewById(R.id.item_description);

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClickListener(getLayoutPosition());
                }
            });

            listItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickListener.onLongClickListener(getLayoutPosition());
                    return true;
                }
            });
        }
    }
}
