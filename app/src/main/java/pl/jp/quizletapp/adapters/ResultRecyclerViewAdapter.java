package pl.jp.quizletapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.jp.quizletapp.R;
import pl.jp.quizletapp.databinding.FragmentResultBinding;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.models.Session;


public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    @Getter
    @Setter
    private List<Session> sessions = new ArrayList<>();

    public ResultRecyclerViewAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentResultBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.session = sessions.get(position);
        holder.mTitle.setText(sessions.get(position).getLectureTitle());
        holder.mDescription.setText(sessions.get(position).getResult()+"/"+sessions.get(position).getTotalPoints());
        holder.bind(sessions.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Session session);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitle;
        public final TextView mDescription;
        public Session session;

        public ViewHolder(FragmentResultBinding binding) {
            super(binding.getRoot());
            mTitle = binding.tvLectureTitle;
            mDescription = binding.tvResult;
        }

        public void bind(final Session session, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(session));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

}