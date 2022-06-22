package pl.jp.quizletapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.jp.quizletapp.databinding.FragmentOptionMultiBinding;
import pl.jp.quizletapp.models.Option;
import pl.jp.quizletapp.models.Question;
import pl.jp.quizletapp.models.QuestionType;

@NoArgsConstructor
public class OptionRecyclerViewAdapter extends RecyclerView.Adapter<OptionRecyclerViewAdapter.ViewHolder> {

    private List<Option> items;
    private QuestionType type;
    private OnItemClickListener listener;
    @Getter
    private List<Option> checkedOptions;

    public OptionRecyclerViewAdapter(Question question, OptionRecyclerViewAdapter.OnItemClickListener listener) {
        this.items = question.getOptionList();
        this.type = question.getType();
        this.listener = listener;
        this.checkedOptions = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (type) {
            default:
            case TRUE_FALSE:
            case MULTIPLE_ANSWER:
            case SINGLE_ANSWER:
                return new ViewHolder(FragmentOptionMultiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);
        holder.mContentView.setText(items.get(position).getContent());
        holder.bind(items.get(position), listener);
        holder.mContentView.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    holder.mContentView.setSelected(isChecked);
                    if (isChecked && checkedOptions.indexOf(holder.mItem) < 0)
                        checkedOptions.add(holder.mItem);
                    else if (!isChecked && checkedOptions.indexOf(holder.mItem) >= 0)
                        checkedOptions.remove(holder.mItem);
                }
        );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Option option);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox mContentView;
        public Option mItem;

        public ViewHolder(FragmentOptionMultiBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        public void bind(final Option option, final OptionRecyclerViewAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(option));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}