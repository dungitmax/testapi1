package com.example.testapi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapi.R;

import static com.example.testapi.Const.EXTRA_CLASSNAME;

public class MainActivity extends AppCompatActivity {
    private EditText mClassNameEdt;
    private Button mOkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClassNameEdt = findViewById(R.id.edt_class_name);
        mOkBtn = findViewById(R.id.btn_ok);

        mOkBtn.setOnClickListener((v) -> {
            String className = mClassNameEdt.getText().toString();
            if (className.isEmpty()) {
                mClassNameEdt.setError(getResources().getString(R.string.please_enter_class_name));
            } else {
                Intent i = new Intent(MainActivity.this, TestActivity.class);
                i.putExtra(EXTRA_CLASSNAME, className);
                startActivity(i);
            }
        });
    }
}
