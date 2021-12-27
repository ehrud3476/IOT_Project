package com.example.ledcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button onButton;
    Button offButton;
    TextView textView;
    ImageView led;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onButton = findViewById(R.id.btn01);
        offButton = findViewById(R.id.btn02);
        textView = findViewById(R.id.textView);
        led = findViewById(R.id.ledImg);
        setTitle(("LED Remote Control"));   //어플의 제목을 설정

        //Firebase의 키,밸류값을 읽어오는 코드
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LED_STATUS");

        myRef.setValue("OFF"); //앱을 실행 했을 때 초기의 Led 상태
        textView.setText(myRef.getKey()); //TextView에 LedStatus의 값을 set한다


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue(); //데이터베이스의 값을 ledstate라는 변수에 저장

                textView.setText("LED STATUS : " + ledState); //textview에 ledstate값을 set해준다.
                if(ledState.equals("ON")){ //조건문을 사용하여 DB에서 받아온 값이 on 일때 실행
                    textView.setBackgroundColor(Color.parseColor("#F3E040")); //textview의 배경색을 노란색으로 변경
                    textView.setTextColor(Color.parseColor("Black")); // textview의 글자색을 검정색으로 변경
                    led.setImageResource(R.drawable.onimg);//이미지뷰 자리에 onimge, 즉 전구의 켜짐 상태를 연출하는 이미지를 set해준다.
                }else{ //ledstate가 on이 아닌 off 일때 실행
                    textView.setBackgroundColor(Color.parseColor("#423B3B")); //textview의 배경색을 회색으로 변경
                    textView.setTextColor(Color.parseColor("White")); //textview의 글자색을 하얀색으로 변경
                    led.setImageResource(R.drawable.offimg); //이미지뷰 자리에 offimge, 즉 전구의 꺼짐 상태를 연출하는 이미지를 set해준다.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onButton.setOnClickListener(new View.OnClickListener() { // Led_on 버튼을 클릭 하였을 경우 실행되는 EventListner
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#F3E040"));    //textview의 배경색을 노란색으로 변경
                myRef.setValue("ON");   //DB의 값을 ON으로 변경
                led.setImageResource(R.drawable.onimg); //이미지뷰 자리에 onimge, 즉 전구의 켜짐 상태를 연출하는 이미지를 set해준다.
            }
        });

        offButton.setOnClickListener(new View.OnClickListener() { // Led_off 버튼을 클릭 하였을 경우 실행되는 EventListner
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.parseColor("#423B3B"));  //textview의 배경색을 회색으로 변경
                myRef.setValue("OFF");  //DB의 값을 OFF으로 변경
                led.setImageResource(R.drawable.offimg); //이미지뷰 자리에 offimge, 즉 전구의 꺼짐 상태를 연출하는 이미지를 set해준다.
            }
        });
    }
}