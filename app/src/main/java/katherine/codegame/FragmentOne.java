package katherine.codegame;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentOne extends Fragment {

    private TextView tvJava;
    private TextView tvAndroid;
    private TextView tvPhp;
    private TextView tvJavascript;
    JSONArray objects = null;
    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

    }
    private void initViews(){

        tvJava          = (TextView) getActivity().findViewById(R.id.tvJava);
        tvAndroid       = (TextView) getActivity().findViewById(R.id.tvAndroid);
        tvPhp           = (TextView) getActivity().findViewById(R.id.tvPhp);
        tvJavascript    = (TextView) getActivity().findViewById(R.id.tvJavascript);
        tvJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadXml("java");
                getQuestionActivity("java");
            }
        });
        tvAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadXml("android");
                getQuestionActivity("android");
            }
        });
        tvPhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadXml("php");
                getQuestionActivity("php");
            }
        });
        tvJavascript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadXml("javascript");
                getQuestionActivity("javascript");
            }
        });
    }
    private void getQuestionActivity(String topic)
    {
        Intent intent = new Intent(getActivity(),Question.class);
        intent.putExtra("objects", objects.toString());
        intent.putExtra("index", 0);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }
    private void loadXml(String topic){
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getActivity().getApplicationContext().getAssets().open("questions_" + topic+".xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            try {
                parseXML(parser);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException, JSONException {

        int eventType = parser.getEventType();
        JSONObject currentObject = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    objects = new JSONArray();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("item")) {

                        currentObject = new JSONObject();
                    } else if (currentObject != null) {

                        if (name.equalsIgnoreCase("itemid")) {
                            currentObject.put("id",parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("itemQuestion")) {
                            currentObject.put("question",parser.nextText());
                        } else if (name.equalsIgnoreCase("itemanswer1")) {
                            currentObject.put("answer1",parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("itemanswer2")) {
                            currentObject.put("answer2",parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("itemanswer3")) {
                            currentObject.put("answer3",parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("itemanswer4")) {
                            currentObject.put("answer4",parser.nextText());
                        }
                        else if (name.equalsIgnoreCase("itemCorrect")) {
                            currentObject.put("correct",parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    //System.out.println("namexml1"+name.equalsIgnoreCase("item"));
                    //System.out.println("namexml2"+(currentProduct != null));

                    if (name.equalsIgnoreCase("item") && currentObject != null) {
                        objects.put(currentObject);
                    }
            }
            eventType = parser.next();
        }
    }

}
