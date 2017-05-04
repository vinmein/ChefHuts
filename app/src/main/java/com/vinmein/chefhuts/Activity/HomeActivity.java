package com.vinmein.chefhuts.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vinmein.chefhuts.R;
import com.vinmein.chefhuts.DataClass.dataprocess;
import com.vinmein.chefhuts.Network.HttpPostClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    String Json = null;
    private String Url;

    dataprocess processor;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        processor = dataprocess.getInstance(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", email);
            jsonObject.accumulate("password", password);
            Json = jsonObject.toString();
            Log.i("log", Json);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpPostClass postdata=new HttpPostClass();
        try{
            Url = processor.gethost()+"/api/auth/login";
            postdata.PostData(getApplicationContext(), Url,Json, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

                @Override
                public void onResponse(Call call,   final Response response) throws IOException {
                    if(response.isSuccessful()){
                        final String respnseString = response.body().string();
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (respnseString != null && respnseString!="{}"){
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    try {
                                        JSONObject test=new JSONObject(respnseString);
                                        Log.i("HomeActvtyLog",test.toString());

                                        if(test.has("token")) {
                                            String Tokenvalue = test.getString("token");
                                            if (Tokenvalue != null) {
                                                onLoginSuccess();
                                                processor.setUserData(respnseString);
                                                Intent MainTrans = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(MainTrans);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                                                Intent MainTrans = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(MainTrans);
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                                            Intent MainTrans = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(MainTrans);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                                else {
                                    onLoginFailed();
                                    Toast.makeText(getApplicationContext(),"Invalid Login",Toast.LENGTH_SHORT);

                                }
                                progressDialog.dismiss();

                            }
                        });

                    }

                }
            });

        }
        catch (IOException e) {
            e.printStackTrace();
        }

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isEmpty() ) {
            _emailText.setError("enter a valid username");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
