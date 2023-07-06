package com.example.calculator3;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView ed;
    String a,b;
    String num;
    int flag = 0,flag1 = 1,flag2=1,closed_par=0,open_par=0,len;
    int z,count=0;
    char c;
    boolean red = true,blue = true,green = true;
    Vibrator vibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = findViewById(R.id.textView);
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }
    public void numberEvent(View view){
        green = true;
        vibrate.vibrate(40);
        if(flag1 == 1){
            ed.setText("");
            flag1 = 0;
        }
        if(count>8){

            if(red) {
                Toast.makeText(this, "number length exceeded", Toast.LENGTH_SHORT).show();
                red = false;

            }

        }
        num = ed.getText().toString();
        switch (view.getId()) {
            case R.id.button16:
                num += "1";
                flag = 1;
                count++;
                break;
            case R.id.button15:
                num += "2";
                flag = 1;
                count++;
                break;
            case R.id.button14:
                num += "3";
                flag = 1;
                count++;
                break;
            case R.id.button12:
                num += "4";
                flag = 1;
                count++;
                break;
            case R.id.button11:
                num += "5";
                flag = 1;
                count++;
                break;
            case R.id.button10:
                num += "6";
                flag = 1;
                count++;
                break;
            case R.id.button8:
                num += "7";
                flag = 1;
                count++;
                break;
            case R.id.button7:
                num += "8";
                flag = 1;
                count++;
                break;
            case R.id.button6:
                num += "9";
                flag = 1;
                count++;
                break;
            case R.id.button9:
                if (flag == 1) {
                    num += "+";
                    flag = 0;
                    flag2 = 1;
                    count=0;
                }
                break;
            case R.id.button13:
                if (flag == 1) {
                    num += "-";
                    flag = 0;
                    flag2 = 1;
                    count=0;
                }
                break;
            case R.id.button5:
                if (flag == 1) {
                    num += "\u00D7";
                    flag = 0;
                    flag2 = 1;
                    count=0;
                }
                break;
            case R.id.button:
                if (flag == 1) {
                    num += "\u00F7";
                    flag = 0;
                    flag2 = 1;
                    count=0;
                }
                break;
            case R.id.button2:
                if (flag == 1) {
                    percentEvent();

                    flag2 = 0;
                    count++;
                }
                break;
            case R.id.button19:
                if (flag == 1 && flag2 == 1) {
                    num += ".";
                    flag = 0;
                    flag2 = 0;
                }
                break;
            case R.id.button20:
                num += "0";
                flag = 1;
                count++;
                break;
            case R.id.button25:
                if(flag == 1) {
                    flag = 0;
                    flag2 = 1;
                    num += "^";
                    count=0;
                }
                break;
        }
            ed.setText(num);

    }
    public void clearEvent(View view){
        vibrate.vibrate(40);
        ed.setText("");
        closed_par = 0;
        open_par = 0;
        flag = 0;
        flag2 = 1;
        count=0;
        blue = true;
        red = true;

    }
    public void parenthesis(View view){
        vibrate.vibrate(40);
        if(flag1 == 1){
            ed.setText("");
            flag1 = 0;
        }

        num = ed.getText().toString();


        if(open_par == closed_par){
            if(num.length() == 0){
                num += "(";
                open_par++;
            }
            else if((num.charAt(num.length()-1) != '+') && (num.charAt(num.length()-1)!= '-') && (num.charAt(num.length()-1)!= '\u00D7') && (num.charAt(num.length()-1)!= '\u00F7')) {
                num = num+"\u00D7"+"(";
                open_par++;
            }
            else{
                num += "(";
                open_par++;
            }
        }
        else if(num.charAt(num.length()-1) == '('){
            num += "(";
            open_par++;
        }
        else if(closed_par < open_par){
            num += ")";
            closed_par++;
        }
        ed.setText(num);
    }
    public void AnswerEvent(View view){
        vibrate.vibrate(40);
        num = ed.getText().toString();
        a = "\u00D7";
        b = "\u00F7";

        num = num.replaceAll(a,"*");
        num = num.replaceAll(b,"/");
        Expression ex = new Expression(num);

        String result = String.valueOf(ex.calculate());
        if(result.length()>19){

            if(blue){
                Toast.makeText(this, "Enter limited number", Toast.LENGTH_SHORT).show();
                blue = false;

            }

        }
        if(result == "NaN"){

            if(green) {
                Toast.makeText(this, "Enter valid expression", Toast.LENGTH_SHORT).show();
                green = false;
            }

        }
        else {
            ed.setText(result);
        }

    }

    public void backEvent(View view){

        vibrate.vibrate(40);
        num = ed.getText().toString();

        if(num.length() == 0){
           ed.setText(num);
        }
        else{
            blue = true;
            red = true;
            green = true;
            count--;
            c = num.charAt(num.length()-1);
            if(c == '+' || c=='-' || c == '^' || c == '\u00D7' || c == '.' || c == '\u00F7'){
                flag = 1;

            }
            else if(c == '('){
                open_par--;
            }
            else if(c == ')'){
                closed_par--;
            }
            num = num.substring(0, num.length() - 1);
            ed.setText(num);
        }
    }
    public void percentEvent(){
        int i;
        num = ed.getText().toString();
        for(i=num.length()-1;i>=0;i--){
            if((num.charAt(i) == '+' )|| (num.charAt(i) == '-') || (num.charAt(i) == '\u00D7') || (num.charAt(i) == '\u00F7')|| (num.charAt(i) == '^')){
                break;
            }
        }
        a = num.substring(i+1,num.length());
        Double d;
        d = Double.parseDouble(a)/100.0;

        b = Double.toString(d);
        num = num.replace(num.substring(i+1,num.length()),b);

    }


}