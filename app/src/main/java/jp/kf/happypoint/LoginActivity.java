package jp.kf.happypoint;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailEditText;    //作成ボタンをタップしたときに入力されている文字列を取得するため。
    EditText mPasswordEditText; //作成ボタンをタップしたときに入力されている文字列を取得するため。
    ProgressDialog mProgress;   //ログイン、アカウント作成中はプログレスダイアログを表示。
    Intent mIntent;

    //firebase関連クラスとリスナー読み込み。
    FirebaseAuth mAuth;
    OnCompleteListener<AuthResult> mLoginListener;          //処理の完了を受け取るリスナークラス
    DatabaseReference mDataBaseReference;

    /**
     onCreateメソッドでは以下の処理を実装します。
        ・データベースへのリファレンスを取得
        ・FirebaseAuthクラスのインスタンスを取得
        ・ログイン処理のリスナーを作成
        ・タイトルバーのタイトルを変更
        ・UIをメンバ変数に保持
        ・ログインボタンのOnClickListenerを設定
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //firebaseのレファレンスを取得
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        // FirebaseAuthのオブジェクトを取得する
        mAuth = FirebaseAuth.getInstance();

        // ログイン処理のリスナー
        mLoginListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // 成功した場合
                    FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());

                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Map data = (Map) snapshot.getValue();
                            }
                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                            }
                        });

                    // プログレスダイアログを非表示にする
                    mProgress.dismiss();

                    // Activityを閉じる
                    finish();

                    mIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mIntent);

                } else {
                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "ログインに失敗しました", Snackbar.LENGTH_LONG).show();

                    // プログレスダイアログを非表示にする
                    mProgress.dismiss();
                }
            }
        };

        // UIの準備
        setTitle("Welcome !! Plz login");

        mEmailEditText = (EditText) findViewById(R.id.emailText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordText);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("処理中...");

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (email.length() != 0 && password.length() >= 6) {

                    login(email, password);

                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }

            }
        });


    }//oncreate last


    private void login(String email, String password) {
        // プログレスダイアログを表示する
        mProgress.show();

        // ログインする
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mLoginListener);
    }

}//class last