package com.example.jab;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

public class HomeActivity extends AppCompatActivity{
    private JellyToggleButton toggle;
    private Button messageBtn;
    private TextView textLeft;
    private TextView textRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_overlay);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthScreen = size.x;
        int heightScreen = size.y;

        double buttonWidth = heightScreen*.1;
        toggle = findViewById(R.id.localCitySwitch);
        textLeft = findViewById(R.id.newMessageTextLeft);
        textRight = findViewById(R.id.newMessageTextRight);
        messageBtn = findViewById(R.id.new_message);

        ViewGroup.LayoutParams messageBtnLayoutParams = messageBtn.getLayoutParams();
        messageBtnLayoutParams.width = (int) buttonWidth;

        messageBtn.setLayoutParams(messageBtnLayoutParams);


        ViewGroup.LayoutParams textLeftLayoutParams = textLeft.getLayoutParams();
        textLeftLayoutParams.width = widthScreen - ((int) buttonWidth) - 15;

        textLeft.setLayoutParams(textLeftLayoutParams);


        ViewGroup.LayoutParams textRightLayoutParams = textRight.getLayoutParams();
        textRightLayoutParams.width = 15;

        textRight.setLayoutParams(textRightLayoutParams);

        toggle.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if(state.equals(State.LEFT)){
                    messageBtn.setBackground(getResources().getDrawable(R.drawable.round_button_green));
                }
                else if(state.equals(State.RIGHT)){
                    messageBtn.setBackground(getResources().getDrawable(R.drawable.round_button_purple));
                }
            }
        });
                //textLeft.setLayoutParams(textLeftLayoutParams);
                //textLeft.setWidth(widthScreen-(int) buttonWidth);

                //toggle.setLeftBackgroundColor(Integer.parseInt("45ff9c",16));
                //toggle.setRightBackgroundColor(Integer.parseInt("7e3dff",16));

    }

}