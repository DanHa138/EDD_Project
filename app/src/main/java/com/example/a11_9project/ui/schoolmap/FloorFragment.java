package com.example.a11_9project.ui.schoolmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a11_9project.MainActivity;
import com.example.a11_9project.R;

public class FloorFragment extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_floor2);

        button = findViewById(R.id.toFloor1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFloor1();
            }
        });

        button = findViewById(R.id.toHome4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
    }

    public void openFloor1(){
        Intent intent = new Intent(this, SchoolMapFragment.class);
        startActivity(intent);
    }

    public void openHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
