package org.inspire.breath.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inspire.breath.R;
import org.inspire.breath.data.Test;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter {
    private List<Test> tests;

    public class TestHolder extends RecyclerView.ViewHolder {
        private View root;
        private Test test;
        public TestHolder(@NonNull View itemView, Test test) {
            super(itemView);
            this.root = itemView;
            this.test = test;
        }

        public void bind(Test test) {
            System.out.println(test);
        }
    }

    public TestListAdapter(List<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_test_item, viewGroup), tests.get(i));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((TestHolder) viewHolder).bind(tests.get(i));
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }
}
