package com.delive.delive.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.delive.delive.R;
import com.delive.delive.model.Auth;
import com.delive.delive.model.LoginInformation;
import com.delive.delive.restfull.ApiClient;
import com.delive.delive.restfull.AuthClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class LoginPasswordActivity extends Activity {

    private String id = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        id = (String)getIntent().getSerializableExtra("id");
        password = (String)getIntent().getSerializableExtra("password");

        if(!TextUtils.isEmpty(password)){
            final Button send = findViewById(R.id.login_send);
            loginUser(send);
        }
    }

    public void loginUser(View view) {

        Boolean valid = true;

        if(password == null) {
            AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
            mAwesomeValidation.setContext(this);

            mAwesomeValidation.addValidation(this, R.id.login_password, "(?=.*[a-z])(?=.*[\\d]).{8,}", R.string.error_invalid_password);
            valid = mAwesomeValidation.validate();
        }

        if(valid) {

            password = ((EditText)findViewById(R.id.login_password)).getText().toString();
            LoginInformation loginInformation = new LoginInformation(id, password);

            AuthClient client = ApiClient.create(AuthClient.class);
            final Call<Auth> call = client.login(loginInformation);
            call.enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(Call<Auth> call, Response<Auth> response) {

                    if (response.isSuccessful()) {

                        if(response.body() != null) {
                            SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                            editor.putString("access_token", response.body().getAccessToken());
                            editor.apply();
                            Toast.makeText(getBaseContext(), R.string.logging_in, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getBaseContext(), FrontPageActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getBaseContext(), R.string.wrong_password, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    Toast.makeText(getBaseContext(), R.string.internal_error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
