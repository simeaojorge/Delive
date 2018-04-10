package com.delive.delive.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.delive.delive.R;
import com.delive.delive.model.User;
import com.delive.delive.restfull.ApiClient;
import com.delive.delive.restfull.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_account_activation);

        final PinEntryEditText pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        final String verification_code = (String)getIntent().getSerializableExtra("verification_code");
        final String id = (String)getIntent().getSerializableExtra("id");
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equalsIgnoreCase(verification_code)) {

                        User user = new User();
                        user.setStatus(User.status_verified);
                        //user.setId(id);
                        UserClient client = ApiClient.create(UserClient.class);
                        final Call<User> call = client.updateUser(id, user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                                Toast.makeText(AccountActivation.this, R.string.successfully_verified, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                                Toast.makeText(AccountActivation.this, R.string.verification_failed, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(AccountActivation.this, R.string.verification_failed, Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }
                }
            });
        }
    }
}
