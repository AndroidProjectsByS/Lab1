package com.example.dariuszn.lab1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by DariuszN on 18.01.2017.
 */

public class RatesAdapter extends BaseAdapter {
    //private ArrayList rates = new ArrayList();
    private int rates[];
    private LayoutInflater inflater;

    public interface OnCheckedChangeListener {
        public void onCheckedChange(int position, int value);
    }

    private OnCheckedChangeListener onCheckedChangeListener;

    public RatesAdapter(Activity activity, int[] rates) {
        this.rates = rates;
        Context context = activity.getApplicationContext();
        inflater = LayoutInflater.from(context);
        this.onCheckedChangeListener = (OnCheckedChangeListener) activity;
    }



    @Override
    public int getCount() {
        return rates.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolderPattern {
        TextView rateLabel;
        RadioGroup radioGroup;
    }

    ViewHolderPattern view_holder;

    private View.OnFocusChangeListener focusListener;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_layout, parent, false);

            view_holder = new ViewHolderPattern();
            view_holder.rateLabel = (TextView) convertView.findViewById(R.id.rateLabel);
            //view_holder.rateEdit = (EditText) convertView.findViewById(R.id.rateView);
            view_holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.rateGroup);
            convertView.setTag(view_holder);
        }
        else {
            view_holder = (ViewHolderPattern) convertView.getTag();
        }

        int checkedPosition = findCheckedCorrectButtons(position);
        RadioButton radioButton = (RadioButton) view_holder.radioGroup.getChildAt(checkedPosition);
        radioButton.setChecked(true);

        view_holder.radioGroup.setTag(position);
        view_holder.rateLabel.setText("Ocena " + ++position + ": ");

        view_holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String position = group.getTag().toString();
                String value = "";
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        value = rb.getText().toString();
                    }
                }
                onCheckedChangeListener.onCheckedChange(Integer.parseInt(position), Integer.parseInt(value));
            }
        });


        return convertView;
    }

    private int findCheckedCorrectButtons(int position) {
        int index = 0;
        for (int i = 0; i < 4; i++) {
            if (rates[position] == (i + 2)) {
                index = i;
                break;
            }
        }

        return index;
    }

}