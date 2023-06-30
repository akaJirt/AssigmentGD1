package com.example.assigmentgd1.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.assigmentgd1.R;
import com.example.assigmentgd1.service.LoginService;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private SignInClient oneTapClient;

    private BeginSignInRequest signUpRequest;

    private Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout edtUsername = findViewById(R.id.edtName);
        TextInputLayout edtPassword = findViewById(R.id.edtPassword);
        TextInputEditText edtUsernameInput = findViewById(R.id.tiedtName);
        TextInputEditText edtPasswordInput = findViewById(R.id.tiedtPassword);
        CheckBox cbRemember = findViewById(R.id.chkRemember);
        Button btnLogin = findViewById(R.id.btnLogin);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("remember", false)) {
            edtUsernameInput.setText(sharedPreferences.getString("username", ""));
            edtPasswordInput.setText(sharedPreferences.getString("password", ""));
            cbRemember.setChecked(true);
        }


        btnLogin.setOnClickListener(v -> {
            String username = edtUsernameInput.getText().toString().trim();
            String password = edtPasswordInput.getText().toString().trim();
            if (edtUsernameInput.getText().toString().trim().isEmpty()) {
                edtUsername.setError("Please enter username");
                return;
            }
            if (edtPasswordInput.getText().toString().trim().isEmpty()) {
                edtPassword.setError("Please enter password");
                return;
            }
            username = username.toLowerCase();
            password = password.toLowerCase();
            if (username.contains("@") || username.contains("#") || username.contains("$") || username.contains("%") || username.contains("^") || username.contains("&") || username.contains("*") || username.contains("(") || username.contains(")") || username.contains("-") || username.contains("+") || username.contains("/") || username.contains("?") || username.contains("!") || username.contains("~") || username.contains("`") || username.contains(".") || username.contains(",") || username.contains("<") || username.contains(">") || username.contains(";") || username.contains(":") || username.contains("'") || username.contains("{") || username.contains("}") || username.contains("[") || username.contains("]") || username.contains("|") || username.contains("=") || username.contains("_") || username.contains(" ")) {
                edtUsername.setError("Username not contain special character");
                return;
            }

            if (cbRemember.isChecked()) {
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putBoolean("remember", true);
                editor.commit();
            } else {
                editor.clear();
                editor.commit();
            }
            Intent intent = new Intent(LoginActivity.this, LoginService.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startService(intent);
        });

        btnGoogle = findViewById(R.id.btnLoginGoogle);

        oneTapClient = Identity.getSignInClient(this);

        signUpRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                        BeginSignInRequest.PasswordRequestOptions.builder()
                                .setSupported(true)
                                .build())
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.default_web_client_id))
                                .setFilterByAuthorizedAccounts(true)
                                .build())
                .build();

        ActivityResultLauncher<IntentSenderRequest> starActivityForRerult = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int x = 2;
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Successfully signed in
                    try {
                        SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                        String idToken = credential.getGoogleIdToken();
                        if (idToken != null) {
                            String email = credential.getId();
                            String name = credential.getDisplayName();
                            Uri profilePictureUri = credential.getProfilePictureUri();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("profilePictureUri", profilePictureUri.toString());
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    // ...
                } else {
                    Toast.makeText(LoginActivity.this, "Error: " + result.getData().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoogle.setOnClickListener(view -> {
            oneTapClient.beginSignIn(signUpRequest)
                    .addOnSuccessListener(this,
                            result -> {
                                try {
                                    IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender())
                                            .build();
                                    starActivityForRerult.launch(intentSenderRequest);
                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(this, e -> {
                        Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LoginService");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            switch (intent.getAction()) {
                case "LoginService":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check");
                    if (check) {
                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}