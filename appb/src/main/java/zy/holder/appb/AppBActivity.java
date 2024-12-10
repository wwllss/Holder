package zy.holder.appb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import zy.holder.OneAdapter;
import zy.holder.model.AModel;
import zy.holder.model.BModel;
import zy.holder.model.CModel;

import java.util.ArrayList;
import java.util.List;

public class AppBActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AppBActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_b);
        RecyclerView listView = findViewById(R.id.listView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        listView.setLayoutManager(manager);
        OneAdapter<Object> adapter = new OneAdapter<>();
        adapter.register(AModel.class, BModel.class, CModel.class);
        listView.setAdapter(adapter);

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                list.add(new AModel());
            } else if (i % 3 == 1) {
                list.add(new BModel());
            } else {
                list.add(new CModel());
            }
        }
        adapter.notifyDataSetChanged(list);
    }
}
