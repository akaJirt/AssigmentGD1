package com.example.assigmentgd1.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.example.assigmentgd1.R;
import com.example.assigmentgd1.activity.LoginActivity;
import com.example.assigmentgd1.activity.MainActivity;
import com.example.assigmentgd1.service.LoginService;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnGetStarted, btnGoogle;

    private View mView;

    IntentFilter intentFilter;

    private SignInClient oneTapClient;

    private BeginSignInRequest signUpRequest;


    public IntroFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroFragment3 newInstance(String param1, String param2) {
        IntroFragment3 fragment = new IntroFragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_intro3, container, false);
        btnGetStarted = mView.findViewById(R.id.btnGetStarted);
        intentFilter = new IntentFilter();
        intentFilter.addAction("LoginService");

        ImageView imgIntro3 = mView.findViewById(R.id.imgFragment3);
        Glide.with(getContext()).load(R.drawable.fragment_3).into(imgIntro3);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }

    public void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        TextInputLayout edtUsername = view.findViewById(R.id.edtName);
        TextInputLayout edtPassword = view.findViewById(R.id.edtPassword);
        TextInputEditText edtUsernameInput = view.findViewById(R.id.tiedtName);
        TextInputEditText edtPasswordInput = view.findViewById(R.id.tiedtPassword);
        CheckBox cbRemember = view.findViewById(R.id.chkRemember);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login", getContext().MODE_PRIVATE);
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
            Intent intent = new Intent(getContext(), LoginService.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            getActivity().startService(intent);
        });

        alertDialog.show();
    }



}