package pl.jp.quizletapp.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import lombok.NoArgsConstructor;
import pl.jp.quizletapp.models.Option;
import pl.jp.quizletapp.models.Question;
import pl.jp.quizletapp.models.QuestionType;
import pl.jp.quizletapp.databinding.FragmentOptionBinding;

import java.util.List;

@NoArgsConstructor
public class OptionRecyclerViewAdapter extends RecyclerView.Adapter<OptionRecyclerViewAdapter.ViewHolder> {

    private List<Option> items;
    private QuestionType type;

    public OptionRecyclerViewAdapter(Question question) {
        this.items = question.getOptionList();
        this.type = question.getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);
        holder.mContentView.setText(items.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public Option mItem;

        public ViewHolder(FragmentOptionBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}