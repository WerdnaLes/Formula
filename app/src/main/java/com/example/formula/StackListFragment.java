package com.example.formula;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

public class StackListFragment extends ListFragment {

    private ArrayList<String> listView;
    private ListChanged listener;

    interface ListChanged{
        void onListChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listView = new ArrayList<>();
        fillListView(Database.stackValues);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, listView);
        setListAdapter(listAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.listener=(ListChanged) activity;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int stackId = (int) id + 1;
        if (Database.stackValues.get(stackId) != null) {
            Float oldValue = Database.stackValues.get(stackId);
            Database.backupValues.put((int) id, oldValue);
            Database.stackValues.put(stackId, null);
            listener.onListChanged();
        } else {
            Database.stackValues.put(stackId, Database.backupValues.get((int) id));
            fillListView(Database.stackValues);
            listener.onListChanged();
        }
    }

    private void fillListView(SortedMap<Integer, Float> vals) {
        Iterator<Map.Entry<Integer, Float>> i = vals.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (i.hasNext()) {
            Map.Entry<Integer, Float> e = i.next();
            Integer key = e.getKey();
            Float value = e.getValue();
            sb.append('R');
            sb.append(key);
            sb.append('=');
            sb.append(value);
            listView.add(sb.toString());
            sb.delete(0, sb.length());
        }
    }
}