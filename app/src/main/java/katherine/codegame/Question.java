package katherine.codegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Question extends AppCompatActivity implements View.OnClickListener {

    private TextView tvAnswer1;
    private TextView tvAnswer2;
    private TextView tvAnswer3;
    private TextView tvAnswer4;
    private TextView tvQuestion;
    private TextView tvNumberQuestion;
    private TextView tvTopic;
    private LinearLayout layoutTopic;

    //answer
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String question;
    private int correctAnswer;

    private JSONArray objects;
    private JSONObject object;
    private int index;
    private int size;

    private Boolean isCorrect=false;
    //topic
    private String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        Intent intent = getIntent();
        try {
            objects = new JSONArray(intent.getStringExtra("objects"));
            size    = objects.length();
            index   = intent.getIntExtra("index", 0);
            topic   = intent.getStringExtra("topic");
            //save length of questions
            SharedPreferences sharedPref = getSharedPreferences("app_questions", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(topic+"_size", size+"");
            editor.apply();

            object  = objects.getJSONObject(index);
            //answer
            answer1         = object.getString("answer1");
            answer2         = object.getString("answer2");
            answer3         = object.getString("answer3");
            answer4         = object.getString("answer4");
            question        = object.getString("question");
            correctAnswer   = object.getInt("correct");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init views
        initViews();

    }
    private void initViews(){
        tvAnswer1 = (TextView) findViewById(R.id.tvAnswer1);
        tvAnswer2 = (TextView) findViewById(R.id.tvAnswer2);
        tvAnswer3 = (TextView) findViewById(R.id.tvAnswer3);
        tvAnswer4 = (TextView) findViewById(R.id.tvAnswer4);
        tvQuestion= (TextView) findViewById(R.id.tvQuestion);
        tvTopic   = (TextView) findViewById(R.id.tvTopic);
        layoutTopic = (LinearLayout) findViewById(R.id.layoutTitleTopic);
        //set onclick listener
        tvAnswer1.setOnClickListener(this);
        tvAnswer2.setOnClickListener(this);
        tvAnswer3.setOnClickListener(this);
        tvAnswer4.setOnClickListener(this);

        tvNumberQuestion= (TextView) findViewById(R.id.tvNumberQuestions);

        tvAnswer1.setText(answer1);
        tvAnswer2.setText(answer2);
        tvAnswer3.setText(answer3);
        tvAnswer4.setText(answer4);
        tvQuestion.setText(question);
        tvNumberQuestion.setText(index+1+" " +getResources().getString(R.string.of)+" "+ size);
        //init topic title and color
        initTopic();
    }

    @Override
    public void onClick(View view) {
        view.setBackgroundColor(Color.GRAY);

        if(view == tvAnswer1){
            if(correctAnswer!=1){
                inflateIconAnswer(false);
            }else{
                isCorrect=true;
                inflateIconAnswer(true);
            }
            tvAnswer2.setClickable(false);
            tvAnswer3.setClickable(false);
            tvAnswer4.setClickable(false);
        }else if(view == tvAnswer2){
            if(correctAnswer!=2){
                inflateIconAnswer(false);
            }else{
                isCorrect=true;
                inflateIconAnswer(true);
            }
            tvAnswer1.setClickable(false);
            tvAnswer3.setClickable(false);
            tvAnswer4.setClickable(false);
        }else if(view == tvAnswer3){
            if(correctAnswer!=3){
                inflateIconAnswer(false);
            }else{
                isCorrect=true;
                inflateIconAnswer(true);
            }
            tvAnswer2.setClickable(false);
            tvAnswer1.setClickable(false);
            tvAnswer4.setClickable(false);
        }else if(view == tvAnswer4){
            if(correctAnswer!=4){
                inflateIconAnswer(false);
            }else{
                isCorrect=true;
                inflateIconAnswer(true);
            }
            tvAnswer2.setClickable(false);
            tvAnswer3.setClickable(false);
            tvAnswer1.setClickable(false);
        }
        //save number of correct answer
        saveData();

        if(index<size-1) {
            getQuestionActivity();
        }else{
            getResumeItem();
        }

    }
    private void inflateIconAnswer(Boolean isCorrect){
        //Create inflate to contain  popup
        final LayoutInflater inflater = (LayoutInflater) Question.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //create  layout
        final View layout = inflater.inflate(R.layout.answer_popup,null);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //popupWindow
        PopupWindow popup = new PopupWindow(layout, metrics.widthPixels, metrics.heightPixels, false);

        ImageView imgIcon = (ImageView) layout.findViewById(R.id.imgIcon);
        if(isCorrect){
            imgIcon.setImageResource(R.drawable.correct);
        }else{
            imgIcon.setImageResource(R.drawable.error);
        }
        popup.showAtLocation(findViewById(R.id.LayoutMainQuestion), Gravity.CENTER, 0, 0);

        popup.setTouchable(true);
        popup.setOutsideTouchable(false);
    }
    private void getQuestionActivity(){
        //wait 1 second to start activity
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("Exception", "Exception" + e);
                }finally {
                    Intent intent = new Intent(getApplicationContext(),Question.class);
                    intent.putExtra("objects", objects.toString());
                    intent.putExtra("index", index+1);
                    intent.putExtra("topic", topic);
                    startActivity(intent);
                    finish();
                }

            }
        };
        myThread.start();
    }
    private void getResumeItem(){

        Intent intent = new Intent(getApplicationContext(),Resume.class);
        String text =getResources().getString(R.string.label_correct)+" "+ getSharedData(topic + "_correct")+" "+getResources().getString(R.string.of)+" "+ size;
        intent.putExtra("correct_text",text);
        intent.putExtra("topic", topic);
        startActivity(intent);
        finish();
    }
    private void initTopic(){
        if(topic.equals("java")){
            tvTopic.setText("JAVA");
            layoutTopic.setBackgroundColor(ContextCompat.getColor(this,R.color.java));
        }else if(topic.equals("android")){
            tvTopic.setText("ANDROID");
            layoutTopic.setBackgroundColor(ContextCompat.getColor(this,R.color.android));
        }else if(topic.equals("php")){
            tvTopic.setText("PHP");
            layoutTopic.setBackgroundColor(ContextCompat.getColor(this,R.color.php));
        }else if(topic.equals("javascript")){
            tvTopic.setText("JAVASCRIPT");
            layoutTopic.setBackgroundColor(ContextCompat.getColor(this,R.color.javascript));
        }

    }
    public void saveData(){
        int val=0;
        String value;
        SharedPreferences sharedPref = getSharedPreferences("app_questions", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(index==0){
            if(isCorrect){
                editor.putString(topic+"_correct", "1");
            }else{
                editor.putString(topic+"_correct", "0");
            }

        }else{
            value = sharedPref.getString(topic+"_correct", "0");
            if(isCorrect){
                val     = Integer.parseInt(value)+1;
            }else{
                val     = Integer.parseInt(value);
            }
            editor.putString(topic+"_correct", val+"");
        }

        editor.apply();
    }
    public String getSharedData(String key){
        SharedPreferences sharedPref = getSharedPreferences("app_questions", Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "0");
        return value;
    }
}
