package com.delive.delive.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        if(isUserLoggedIn()){
            Intent intent = new Intent(getBaseContext(), FrontPageActivity.class);
            startActivity(intent);
        }
    }

    public void sendNumber(final View view) {

        if(phoneInput.isValid()){

            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_translate_right);

            //FloatingActionButton sendTel = findViewById(R.id.login_float_button);
            view.startAnimation(animation);

            User user = new User();
            user.setPhoneNumber(phoneInput.getNumber());

            UserClient client = ApiClient.create(UserClient.class);
            final Call<User> call = client.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    final User userArray = response.body();

                    if (response.isSuccessful()) {
                        //Toast.makeText(getBaseContext(), R.string.confirmation_sms, Toast.LENGTH_SHORT).show();
                        Intent intent = null;
                        if(userArray.getStatus().equalsIgnoreCase(User.status_pending)) {
                            intent = new Intent(view.getContext(), AccountActivation.class);
                            intent.putExtra("verification_code", userArray.getVerificationCode());
                            intent.putExtra("id", userArray.getId());
                        }
                        else if(userArray.getStatus().equalsIgnoreCase(User.status_verified)){
                            intent = new Intent(view.getContext(), FirstAccessActivity.class);
                            intent.putExtra("id", userArray.getId());
                        }
                        else{
                            intent = new Intent(view.getContext(), LoginPasswordActivity.class);
                            intent.putExtra("id", userArray.getId());
                        }
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_translate_left, R.anim.anim_translate_from_right);
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

    private boolean isUserLoggedIn(){

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        return prefs.getString("access_token", null) != null;
    }
}
