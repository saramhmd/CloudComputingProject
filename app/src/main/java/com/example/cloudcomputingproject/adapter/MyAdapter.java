package com.example.cloudcomputingproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.TopicsAvailableActivity;
import com.example.cloudcomputingproject.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> tasks;
    private List<Task> filteredTaskList;
//    private Context context;

//    private AdapterView.OnItemClickListener listener;

    public MyAdapter(TopicsAvailableActivity topicsAvailableActivity, List<Task> tasks) {
        this.tasks = tasks;
        this.filteredTaskList = new ArrayList<>(tasks);
//        this.context = context;

//        this.listener = listener;
    }

//    public void setFilteredTaskList(List<Task> filteredTaskList){
//        this.tasks = filteredTaskList;
//        notifyDataSetChanged();
//
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.checkBox.setText(task.getTitle());
        holder.checkBox.setChecked(task.isChecked());
        Task task1 = filteredTaskList.get(position);
//        holder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return filteredTaskList.size();
    }
//
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String query = constraint.toString().toLowerCase();
//                if (query.isEmpty()) {
//                    filteredTaskList = tasks;
//                } else {
//                    List<Task> filteredList = new ArrayList<>();
//
//                    for (Task task : tasks) {
//                        if (task.getTitle().toLowerCase().contains(query)) {
//                            filteredList.add(task);
//                        }
//                    }
//                    filteredTaskList = filteredList;
//
//                }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = filteredTaskList;
//                    return filterResults;
//                }
//
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                filteredTaskList = (List<Task>) results.values;
//                notifyDataSetChanged();
////                filteredTaskList.clear();
////                filteredTaskList.addAll((List<Task>) results.values);
////                notifyDataSetChanged();
//            }
//        };
//    }


//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String query = constraint.toString();
//
////                List<Task> filteredList = new ArrayList<>();
//                if (query.isEmpty()) {
//                    filteredTaskList = tasks;
//                } else {
//                    List<Task> filteredList = new ArrayList<>();
//                    for (Task task : tasks) {
//                        if (task.getTitle().toLowerCase().contains(query.toLowerCase())) {
//                            filteredList.add(task);
//                        }
//                    }
//                    filteredTaskList = filteredList;
//
//                }
//                FilterResults results = new FilterResults();
//                results.values = filteredTaskList;
//                return results;
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                filteredTaskList.clear();
//                filteredTaskList.addAll((List<Task>) results.values);
//                notifyDataSetChanged();
//            }
//        };
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
        }
//        public void bind(Task task, AdapterView.OnItemClickListener listener) {
//            checkBox.setText(task.getTitle());
//            checkBox.setChecked(task.isChecked());
//
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean isChecked = checkBox.isChecked();
//                    listener.onItemClick(task, isChecked);
//                }
    }
}
