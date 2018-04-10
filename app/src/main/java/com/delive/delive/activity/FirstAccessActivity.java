package com.delive.delive.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.delive.delive.R;
import com.delive.delive.model.User;
import com.delive.delive.restfull.ApiClient;
import com.delive.delive.restfull.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class FirstAccessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_first_access);
    }

    public void sendUserInfo(View view) {

        final String id = (String)getIntent().getSerializableExtra("id");
        final EditText name = findViewById(R.id.firstAccess_name);
        final EditText last_name = findViewById(R.id.firstAccess_last_name);
        final EditText mail = findViewById(R.id.firstAccess_mail);
        final EditText document = findViewById(R.id.firstAccess_document);
        final EditText password = findViewById(R.id.firstAccess_password);

        //if(!validateRegister(this) && isEmailValid((CharSequence) mail)){

            User user = new User();
            user.setName(name.getText().toString());
            user.setLastName(last_name.getText().toString());
            user.setEmail(mail.getText().toString());
            user.setDocumentId(document.getText().toString());
            user.setPassword(password.getText().toString());
            user.setStatus(User.status_completed);

            final UserClient client = ApiClient.create(UserClient.class);
            final Call<User> call = client.updateUser(id, user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User user = (User)response.body();

                    Intent intent = new Intent(getBaseContext(), LoginPasswordActivity.class);
                    intent.putExtra("id", user.getId());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        //}
    }

    @NonNull
    private Boolean validateRegister(Activity activity){

        AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(this);

        mAwesomeValidation.addValidation(activity, R.id.firstAccess_name, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(activity, R.id.firstAccess_last_name, "[a-zA-Z\\s]+", R.string.err_last_name);
        mAwesomeValidation.addValidation(activity, R.id.firstAccess_password, "(?=.*[a-z])(?=.*[\\d]).{8,}", R.string.err_password);
        mAwesomeValidation.addValidation(activity, R.id.firstAccess_user_confirm_password, R.id.firstAccess_password, R.string.err_confirm_password);
        mAwesomeValidation.addValidation(activity, R.id.firstAccess_mail, Patterns.EMAIL_ADDRESS, R.string.err_email);

        if(mAwesomeValidation.validate())
            return true;

        return false;
    }

    @NonNull
    private Boolean isEmailValid (CharSequence mail){

        if (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            return true;
        }

        Toast.makeText(getBaseContext(), R.string.mail_not_valid, Toast.LENGTH_SHORT);
        return false;
    }
}
