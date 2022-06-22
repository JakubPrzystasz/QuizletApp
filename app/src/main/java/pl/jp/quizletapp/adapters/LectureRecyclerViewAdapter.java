package pl.jp.quizletapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.jp.quizletapp.databinding.FragmentLectureBinding;
import pl.jp.quizletapp.models.Lecture;

public class LectureRecyclerViewAdapter extends RecyclerView.Adapter<LectureRecyclerViewAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    @Getter
    @Setter
    private List<Lecture> lectures = new ArrayList<>();

    public LectureRecyclerViewAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentLectureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.lecture = lectures.get(position);
        holder.mTitle.setText(lectures.get(position).getTitle());
        holder.mDescription.setText(lectures.get(position).getDescription());
        holder.bind(lectures.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Lecture lecture);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitle;
        public final TextView mDescription;
        public Lecture lecture;

        public ViewHolder(FragmentLectureBinding binding) {
            super(binding.getRoot());
            mTitle = binding.tvLectureTitle;
            mDescription = binding.tvLectureDescription;
        }

        public void bind(final Lecture lecture, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(lecture);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

}