package jp.kf.happypoint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    DatabaseReference mDataBaseReference;
    private EditText mNameText;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Preferenceから表示名を取得してEditTextに反映させる
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString(Const.NameKEY, "");
        mNameText = (EditText) findViewById(R.id.nameText);
        mNameText.setText(name);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        // UIの初期設定
        setTitle("設定");
        Button changeButton = (Button) findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出ていたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // ログイン済みのユーザーを取得する
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user == null) {
                    // ログインしていない場合は何もしない
                    Snackbar.make(v, "ログインしていません", Snackbar.LENGTH_LONG).show();
                    return;
                }

                // 変更した表示名をFirebaseに保存する
                String name = mNameText.getText().toString();
                DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());
                Map<String, String> data = new HashMap<String, String>();
                data.put("name", name);
                userRef.setValue(data);

                // 変更した表示名をPreferenceに保存する
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Const.NameKEY, name);
                editor.commit();

                Snackbar.make(v, "表示名を変更しました", Snackbar.LENGTH_LONG).show();
            }
        });

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

//                mNameText.setText("");
//                Snackbar.make(v, "ログアウトしました", Snackbar.LENGTH_LONG).show();

                  mIntent = new Intent(getApplication(), LoginActivity.class);
                  startActivity(mIntent);

            }
        });

        /**
         ログイン画面へ遷移（ログイン画面へ遷移)
         **/
        Button mButton_backtotop = (Button) findViewById(R.id.BackToTopButton);
        mButton_backtotop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIntent = new Intent(getApplication(), MainActivity.class);
                startActivity(mIntent);
            }
        });
    }//oncreate last

}//class last
