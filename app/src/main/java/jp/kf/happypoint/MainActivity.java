package jp.kf.happypoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    SetUpPieChart mSetUpPieChart;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetUpPieChart = new SetUpPieChart();
        mSetUpPieChart.setupPieChart(this);

    /**
     ログイン画面へ遷移（ログイン画面へ遷移)
     **/
    Button mButton_login = (Button) findViewById(R.id.button_login);
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // ログインしていなければログイン画面に遷移させる
                    if (user == null) {
                        mIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(mIntent);
                    }
            }
        });
    } //onCreate last


    // メニューをActivity上に設置する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // メニューが選択されたときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            mIntent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}//class last

//Log.d("UI_PARTS", "ボタンをタップしました");
