package katherine.codegame;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Resume extends AppCompatActivity {

    private TextView tvTopic;
    private TextView tvTextCorrect;
    private Button btnResume;

    private String topic;
    private String correct_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        Intent intent = getIntent();
        correct_text   = intent.getStringExtra("correct_text");
        topic           = intent.getStringExtra("topic");

        initViews();
    }
    private void initViews(){
        tvTopic         = (TextView) findViewById(R.id.tvTopic);
        tvTextCorrect   = (TextView) findViewById(R.id.tvNumberCorrectResume);
        btnResume       = (Button) findViewById(R.id.btnResume);
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("position",1);
                startActivity(intent);
            }
        });
        tvTextCorrect.setText(correct_text);
        if(topic.equals("java")){
            tvTopic.setText("JAVA");
            tvTopic.setBackgroundResource(R.drawable.border_rounded_one);
        }else if(topic.equals("android")){
            tvTopic.setText("ANDROID");
            tvTopic.setBackgroundResource(R.drawable.border_rounded_two);
        }else if(topic.equals("php")){
            tvTopic.setText("PHP");
            tvTopic.setBackgroundResource(R.drawable.border_rounded_three);
        }else if(topic.equals("javascript")){
            tvTopic.setText("JAVASCRIPT");
            tvTopic.setBackgroundResource(R.drawable.border_rounded_four);
        }
    }
}
