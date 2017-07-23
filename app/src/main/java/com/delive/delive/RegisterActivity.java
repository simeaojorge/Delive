package com.delive.delive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = (EditText) findViewById(R.id.text_name);
        final EditText password = (EditText) findViewById(R.id.text_password);
        final EditText conf_password = (EditText) findViewById(R.id.text_confirm_password);

        final Button button_send = (Button) findViewById(R.id.button_send);
    }
}
