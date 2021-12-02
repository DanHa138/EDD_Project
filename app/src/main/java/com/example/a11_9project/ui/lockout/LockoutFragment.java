package com.example.a11_9project.ui.lockout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentLockoutBinding;
import com.example.a11_9project.ui.lockout.Model.BlockerModel;
import com.shuhart.stepview.StepView;

import java.util.List;
import java.util.ResourceBundle;

public class LockoutFragment extends AppCompatActivity {

    StepView stepView;
    LinearLayout normalLayout;
    TextView status_password;
    RelativeLayout relativeLayout;

    BlockerModel utilsBlock;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lockout);

        initLayout();

        initPatternListener();
    }

    private void initPatternListener() {
        PatternLockView patternLockView = findViewById(R.id.pattern_view);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                String password = PatternLockUtils.patternToString(patternLockView, pattern);

                if(password.length() < 4){
                    status_password.setText(utilsBlock.SHEMA_FAILED);
                    patternLockView.clearPattern();
                    return;
                }

                if(utilsBlock.getPassword() == null){
                    if(utilsBlock.isFirstStep()){
                        userPassword = password;
                        utilsBlock.setFirstStep(false);
                        status_password.setText(utilsBlock.STATUS_NEXT_STEP);
                        stepView.go(1, true);
                    }
                    else {
                        if(userPassword.equals(password)){
                            utilsBlock.setPassword(userPassword);
                            status_password.setText(utilsBlock.STATUS_PASSWORD_CORRECT);
                            stepView.done(true);

                            startAct();
                        }
                        else{
                            status_password.setText(utilsBlock.STATUS_PASSWORD_INCORRECT);
                        }
                    }
                }
                else{
                    if(utilsBlock.isCorrect(password)){
                        status_password.setText(utilsBlock.STATUS_PASSWORD_CORRECT);
                        startAct();
                    }
                    else{
                        status_password.setText(utilsBlock.STATUS_PASSWORD_INCORRECT);
                    }
                }
                patternLockView.clearPattern();
            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void startAct() {

        startActivity(new Intent(this, AppList.class));
        finish();

    }

    private void initLayout() {

        stepView = findViewById(R.id.step_view);
        normalLayout = findViewById(R.id.normal_layout);
        relativeLayout = findViewById(R.id.root);
        status_password = findViewById(R.id.status_password);
        utilsBlock = new BlockerModel(this);
        status_password.setText(utilsBlock.STATUS_FIRST_STEP);

        if(utilsBlock.getPassword() == null){   
            normalLayout.setVisibility(View.GONE);
            stepView.setVisibility(View.VISIBLE);
            stepView.setStepsNumber(2);
            stepView.go(0, true);
        }
        else{
            normalLayout.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.GONE);

            int backColor = ResourcesCompat.getColor(getResources(), R.color.black, null);
            relativeLayout.setBackgroundColor(backColor);
            status_password.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed() {
        if(utilsBlock.getPassword() == null && !utilsBlock.isFirstStep()){
            stepView.go(0, true);
            utilsBlock.setFirstStep(true);
            status_password.setText(utilsBlock.STATUS_FIRST_STEP);
        }
        else{
            finish();
            super.onBackPressed();
        }
    }
}