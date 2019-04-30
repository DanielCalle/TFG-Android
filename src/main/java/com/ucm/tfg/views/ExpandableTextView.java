package com.ucm.tfg.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.ucm.tfg.R;

public class ExpandableTextView extends AppCompatTextView {

    private final int DEFAULT_TRIM_LENGTH = 200;

    private CharSequence originalText;
    private CharSequence trimmedText;
    private Boolean trimmed;
    private BufferType bufferType;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        trimmed = false;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = trim(text);
        bufferType = type;
        super.setText(text, type);
    }

    private CharSequence trim(CharSequence text) {
        if (text != null && text.length() > DEFAULT_TRIM_LENGTH) {
            return new SpannableStringBuilder(text, 0, DEFAULT_TRIM_LENGTH).append(" ...");
        }
        return text;
    }

    public void setExpandListener(ImageButton imageButton) {
        imageButton.setBackgroundResource(trimmed ? R.drawable.ic_keyboard_arrow_down_black_24dp : R.drawable.ic_keyboard_arrow_up_black_24dp);
        imageButton.setOnClickListener((View v) -> {
            trimmed = !trimmed;
            imageButton.setBackgroundResource(trimmed ? R.drawable.ic_keyboard_arrow_down_black_24dp : R.drawable.ic_keyboard_arrow_up_black_24dp);
            super.setText(trimmed ? trimmedText : originalText, bufferType);
        });
    }
}
