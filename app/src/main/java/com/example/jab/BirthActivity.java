package com.example.jab;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.stream.IntStream;

import controllers.BirthController;
import controllers.FirstNameController;
import controllers.RegisterController;
import custom_class.NoPhoneUser;
import custom_class.UserProfile;

public class BirthActivity extends AppCompatActivity  {
    private BirthController controller;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private final String TAG = "BirthActivity";
    private NumberPicker monthPicker;
    private NumberPicker dayPicker;
    private NumberPicker yearPicker;
    private TextView youngAge;
    private Button button_continue;
    private UserProfile user;
    private ProgressBar loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new BirthController(this, auth);

        monthPicker = findViewById(R.id.month_birth);
        dayPicker = findViewById(R.id.day_birth);
        yearPicker = findViewById(R.id.year_birth);

        //String[] monthVals = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
        //String[] dayVals = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        //String[] yearVals = new String[]{"1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948","1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"};

        //monthPicker.setDisplayedValues(monthVals);
        //dayPicker.setDisplayedValues(dayVals);
        //yearPicker.setDisplayedValues(yearVals);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        yearPicker.setMinValue(1920);
        yearPicker.setMaxValue(2020);

        monthPicker.setOnValueChangedListener(monthValueChange);
        dayPicker.setOnValueChangedListener(dayValueChange);
        yearPicker.setOnValueChangedListener(yearValueChange);

        monthPicker.setWrapSelectorWheel(false);
        dayPicker.setWrapSelectorWheel(false);
        yearPicker.setWrapSelectorWheel(false);

        loadingBar = findViewById(R.id.loading_phone);
        youngAge = findViewById(R.id.textIncorrectAge);

        youngAge.setVisibility(View.INVISIBLE);
        loadingBar.setVisibility(View.INVISIBLE);

        button_continue = findViewById(R.id.button_continue);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");

        button_continue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Calendar birthNow = Calendar.getInstance();
                birthNow.set(yearPicker.getValue(),monthPicker.getValue()-1,dayPicker.getValue(),0,0,0);
                Timestamp timeBirth = new Timestamp(birthNow.getTime());
                user.setAge((int) timeBirth.getSeconds());
                int age = calculateAge(timeBirth, Instant.now().toEpochMilli());
                System.out.println(age);
                if (age >= 18){
                    youngAge.setVisibility(View.INVISIBLE);
                    controller.addAgeContinue(user);
                }
                else{
                    youngAge.setVisibility(View.VISIBLE);
                }
            }
        });



    }



    NumberPicker.OnValueChangeListener monthValueChange =
            new 	NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Toast.makeText(BirthActivity.this,
                            "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
                    if(i1==1){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                    else if(i1==2){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(28);
                    }
                    else if(i1==3){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                    else if(i1==4){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(30);
                    }
                    else if(i1==5){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                    else if(i1==6){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(30);
                    }
                    else if(i1==7){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                    else if(i1==8){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                    else if(i1==9){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(30);
                    }
                    else if(i1==10){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                    else if(i1==11){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(30);
                    }
                    else if(i1==12){
                        dayPicker.setMinValue(1);
                        dayPicker.setMaxValue(31);
                    }
                }
            };

    NumberPicker.OnValueChangeListener dayValueChange =
            new 	NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Toast.makeText(BirthActivity.this,
                            "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
                }
            };

    NumberPicker.OnValueChangeListener yearValueChange = new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Toast.makeText(BirthActivity.this,
                            "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
                }
            };



    public static int calculateAge(Timestamp birthDate, long currentDate) {

        if ((birthDate != null)) {
            return (int) Math.floor((currentDate/1000-birthDate.getSeconds())/31556952.0);
        } else {
            return 0;
        }
    }


}
