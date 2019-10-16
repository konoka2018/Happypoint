package jp.kf.happypoint;

import android.content.Context;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SetUpPieChart {

    float rainfall[] = {2f, 1f };
    String monthNames[] = {"Happy", "Sad"};

    public void setupPieChart(Context context) {
        //PieEntriesのリストを作成する:
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < rainfall.length; i++) {
            pieEntries.add(new PieEntry(rainfall[i], monthNames[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(dataSet);

        //PieChartを取得する.
        PieChart piechart = ((jp.kf.happypoint.MainActivity) context).
                findViewById(R.id.Chart);
        piechart.setData(data);
        piechart.invalidate();

        piechart.setCenterText("Happy Point");
        piechart.setRotationAngle(270);

        // 更新
        piechart.invalidate();
        // アニメーション
        piechart.animateXY(1500, 1500); // 表示アニメーション
        piechart.setDescription(null);

    }
}
