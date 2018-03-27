package com.delive.delive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

public class RegisterActivityOld extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_old);

        final EditText document = (EditText) findViewById(R.id.text_cpf);
        final EditText name = (EditText) findViewById(R.id.text_name);
        final EditText email = (EditText) findViewById(R.id.text_email);
        final EditText password = (EditText) findViewById(R.id.text_password);

        final Button button_send = (Button) findViewById(R.id.button_send);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateRegister(RegisterActivityOld.this)) {

                    User user = new User();

                    UserClient api = ApiClient.create(UserClient.class);
                    final Call<User> call = api.createUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Enviado email de confirmacao", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Não pode ser enviado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getBaseContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private Boolean validateRegister(Activity activity){

        AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(this);

        mAwesomeValidation.addValidation(activity, R.id.text_name, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(activity, R.id.text_password, "(?=.*[a-z])(?=.*[\\d]).{8,}", R.string.err_password);
        mAwesomeValidation.addValidation(activity, R.id.text_confirm_password, R.id.text_password, R.string.err_confirm_password);
        mAwesomeValidation.addValidation(activity, R.id.text_email, Patterns.EMAIL_ADDRESS, R.string.err_email);

        if(mAwesomeValidation.validate())
            return true;

        return false;
    }
}
