package com.example.android.miwook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class WordAdapter extends ArrayAdapter <Word>{

    private int backgroundColor;

    public WordAdapter(Context context, ArrayList<Word> words, int backgroundColor) {
        super(context, 0, words);
        this.backgroundColor = backgroundColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }

        Word currentWord = getItem(position);

        LinearLayout linearLayout = listItemView.findViewById(R.id.ll_background);
        int color = ContextCompat.getColor(getContext(), backgroundColor);
        linearLayout.setBackgroundColor(color);

        TextView miwokTranslation = listItemView.findViewById(R.id.tv_first);
        miwokTranslation.setText(currentWord.getMiwokTranslation());

        TextView defaultTranslation = listItemView.findViewById(R.id.tv_second);
        defaultTranslation.setText(currentWord.getDefaultTranslation());


        return listItemView;
    }
}
