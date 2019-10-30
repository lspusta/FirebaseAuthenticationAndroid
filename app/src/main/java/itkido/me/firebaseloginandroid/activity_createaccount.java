package itkido.me.firebaseloginandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_createaccount extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    FirebaseAuth mAuth;
    RelativeLayout rootView;
    Button createAccountButton;
    EditText emailEditText;
    EditText passwordEditText;
    TextView txtLogIn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        mAuth = FirebaseAuth.getInstance();
        initViews();


    }

    private void initViews() {
        rootView = findViewById(R.id.rootView);
        createAccountButton = findViewById(R.id.createAccountButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        txtLogIn = findViewById(R.id.txtLogIn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_login.class));
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount(){

        if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){


            emailEditText.setError( "Email is required!" );

            passwordEditText.setError( "Password is required!" );



        }else {


            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(activity_createaccount.this, "Hello " + user.getEmail(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(activity_createaccount.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                            }

                            // ...
                        }
                    });


        }


    }

}
