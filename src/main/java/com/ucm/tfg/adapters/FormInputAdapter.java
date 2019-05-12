package com.ucm.tfg.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.activities.FormActivity;

import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// This is used for recycler view, see its doc in android developer
public class FormInputAdapter extends RecyclerView.Adapter<FormInputAdapter.RecyclerViewHolder> {

    private Context context;
    private Map<Integer, Pair<String, String>> data;
    private Map<String, String> result;

    public FormInputAdapter(Context context) {
        this.context = context;
        this.data = new HashMap<>();
        this.result = new HashMap<>();
    }

    @NonNull
    @Override
    public FormInputAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.form_input_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FormInputAdapter.RecyclerViewHolder recyclerViewHolder, int i) {
        Pair<String, String> p = data.get(i);
        recyclerViewHolder.label.setText(StringUtils.capitalize(p.first));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        switch (p.second) {
            case "date":
                TextView textView = new TextView(context);
                textView.setLayoutParams(layoutParams);
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                textView.setOnClickListener((View v) -> {
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            String date = new StringBuilder()
                                    .append(year)
                                    .append("-")
                                    .append(month + 1)
                                    .append("-")
                                    .append(day)
                                    .toString();
                            textView.setText(date);
                            result.put(p.first, date);
                        }
                    }, year, month, day).show();
                });
                textView.setText(context.getText(R.string.date_format));
                recyclerViewHolder.container.addView(textView);
                break;
            case "text":
                EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        result.put(p.first, s.toString());
                    }
                });
                editText.setLayoutParams(layoutParams);
                recyclerViewHolder.container.addView(editText);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public FrameLayout container;

        public RecyclerViewHolder(View view) {
            super(view);
            label = view.findViewById(R.id.label);
            container = view.findViewById(R.id.container);
        }
    }

    public void setData(Map<Integer, Pair<String, String>> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Map<String, String> getResult() {
        return result;
    }
}
