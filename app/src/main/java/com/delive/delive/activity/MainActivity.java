package com.delive.delive.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.delive.delive.R;
import com.delive.delive.model.User;
import com.delive.delive.restfull.ApiClient;
import com.delive.delive.restfull.UserClient;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private IntlPhoneInput phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        phoneInput = findViewById(R.id.login_cellphone);
    }

    public void sendNumber(View view) {

        if(phoneInput.isValid()){

            User user = new User();
            user.setPhoneNumber(phoneInput.getNumber());

            UserClient client = ApiClient.create(UserClient.class);
            final Call<User> call = client.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), R.string.confirmation_sms, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), R.string.confirmation_sms_failure, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getBaseContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, R.string.invalid_number, Toast.LENGTH_SHORT).show();
        }
    }
}
