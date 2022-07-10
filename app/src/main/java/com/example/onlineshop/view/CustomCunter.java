package com.example.onlineshop.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.onlineshop.R;


public class CustomCunter extends ConstraintLayout {

    private int number = 1;
//    private MutableLiveData<Integer> numberLiveData =new MutableLiveData<>(1);

    private Button add, mines;
    private EditText countEditText;

    public CustomCunter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.custom_counter, this);
        add = findViewById(R.id.addButton);
        mines = findViewById(R.id.minesButton);
        countEditText = findViewById(R.id.countEditText);

        countEditText.setText(String.valueOf(number));

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
//                numberLiveData.setValue(number);
                countEditText.setText(String.valueOf(number));
            }
        });


        mines.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
//                numberLiveData.setValue(number);
                countEditText.setText(String.valueOf(number));
            }
        });


        countEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    number = 1;
                } else {
                    number = Integer.parseInt(charSequence.toString());
                }
//                numberLiveData.setValue(number);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public int getNumber() {
        return number;
    }
//
//    public MutableLiveData<Integer> getNumberLiveData(){return numberLiveData;}


}
