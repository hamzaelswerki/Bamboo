package com.example.bamboo.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.utils.Constants;
import com.example.bamboo.view.BoradingActivity;
import com.example.bamboo.view.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MobileNumberFragment extends Fragment {
    FirebaseAuth mAuth;
    String codeSent;
    EditText editTextLoginNumber;
    ImageView imageNext;
    TextView skip;
    String phoneNumber;
    SweetAlertDialog pDialog;
    public MobileNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      mAuth = FirebaseAuth.getInstance();
          pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobile_number, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextLoginNumber = getView().findViewById(R.id.edit_text_login_number);
        skip=getView().findViewById(R.id.skip);
        imageNext = getView().findViewById(R.id.image_next);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createEditeTextLogin();
        createImageNext();

        creatSkipText();



}

    private void creatSkipText() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             getActivity().startActivity(new Intent(getContext(), BoradingActivity.class));
            }
        });
    }

    private void createEditeTextLogin() {
        editTextLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 9) {
                    imageNext.setVisibility(View.VISIBLE);
                } else {
                    imageNext.setVisibility(View.GONE);

                }
            }
        });

    }
    private void createImageNext() {
        imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProg( );
                sendVerificationCode();
            }
        });
    }

    void  showProg(){
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
            pDialog.show();

    }
    private void sendVerificationCode() {


          phoneNumber =editTextLoginNumber.getText().toString();
        if (!phoneNumber.startsWith("5")){
            editTextLoginNumber.setError("Invalid Number");
            editTextLoginNumber.requestFocus();
            return;
        }
        phoneNumber= Constants.CODE_PALESTINE+phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        Log.d("ttt",phoneNumber+">>*----");

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    Log.d("ttt","complete");
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.d("ttt","failed"+e.getMessage());
                    Toast.makeText(getContext(),"Enter vaild Number",Toast.LENGTH_SHORT).show();
                    pDialog.hide();                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s,forceResendingToken);
                    codeSent=s;

                    Log.d("ttt","code sent");
                    Bundle bundle=new Bundle();
                    bundle.putString(Constants.SEND_CODE,codeSent);
                    bundle.putString(Constants.PHONE,phoneNumber);


                    MobileCodeFragment mobileCodeFragment= new MobileCodeFragment(null);
                    mobileCodeFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().replace
                            (R.id.frame_login, mobileCodeFragment).addToBackStack("back").commit();
                    pDialog.hide();

                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                    Log.d("ttt","onCodeAutoRetrievalTimeOut");
                    pDialog.hide();

                }
            };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            openLoginNameAndEmailFragment();
                            pDialog.hide();

                        }else {
                            pDialog.hide();
                            // Sign in failed, display a message and update the UI
                            Log.d("ttt", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Log.d("ttt", "the verification code entered was invalid", task.getException());

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", "failurrrrrre  "+e.getMessage());

            }
        });

    }

    void openLoginNameAndEmailFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out)
                .replace(R.id.frame_login, new LoginNameAndEmailFragment( phoneNumber))
                .commit();
    }
}

