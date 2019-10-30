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

public class activity_login extends AppCompatActivity {
    private static final String TAG = "activity_login";
    private FirebaseAuth mAuth;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView;
    private Button loginButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView txtSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        initViews();



    }

    private void initViews() {
        rootView = findViewById(R.id.rootView);
        loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        txtSignUp = findViewById(R.id.txtSignUp);



        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_login.this, activity_createaccount.class));
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }


    private void login(){
        if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){


            emailEditText.setError( "Email is required!" );

            passwordEditText.setError( "Password is required!" );



        }else {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                startActivity(new Intent(activity_login.this, MainActivity.class));
                                Toast.makeText(activity_login.this, "Hello " + user.getEmail(), Toast.LENGTH_LONG).show();

                            } else {
// If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(activity_login.this, task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();

                                Toast.makeText(activity_login.this, "Try to Re Login", Toast.LENGTH_LONG).show();
                            }

// ...
                        }
                    });
        }


    }
}
