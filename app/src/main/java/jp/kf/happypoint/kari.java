package jp.kf.happypoint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class kari extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kari_main);

        SetUpPieChart mSetUpPieChart = new SetUpPieChart();
        
    }
}
