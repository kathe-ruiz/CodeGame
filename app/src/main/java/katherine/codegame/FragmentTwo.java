package katherine.codegame;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private ProgressBar progressBarJava;
    private ProgressBar progressBarAndroid;
    private ProgressBar progressBarPhp;
    private ProgressBar progressBarJavascript;
    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init views an values
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh fragment
        initViews();
    }

    private void initViews(){

        int maxJava,maxAndroid,maxPhp,maxJavascript,progressJava,progressAndroid,progressPhp,progressJavascript;
        progressBarJava         = (ProgressBar) getActivity().findViewById(R.id.progressBarJava);
        progressBarAndroid      = (ProgressBar) getActivity().findViewById(R.id.progressBarAndroid);
        progressBarPhp          = (ProgressBar) getActivity().findViewById(R.id.progressBarPhp);
        progressBarJavascript   = (ProgressBar) getActivity().findViewById(R.id.progressBarJavascript);

        //get length of objects
        maxJava         = Integer.parseInt(getSharedData("java_size"));
        maxAndroid      = Integer.parseInt(getSharedData("android_size"));
        maxPhp          = Integer.parseInt(getSharedData("php_size"));
        maxJavascript   = Integer.parseInt(getSharedData("javascript_size"));

        //get correct answer of objects
        progressJava         = Integer.parseInt(getSharedData("java_correct"));
        progressAndroid      = Integer.parseInt(getSharedData("android_correct"));
        progressPhp          = Integer.parseInt(getSharedData("php_correct"));
        progressJavascript   = Integer.parseInt(getSharedData("javascript_correct"));
        //set max and progress
        progressBarJava.setMax(maxJava);
        progressBarJava.setProgress(progressJava);
        progressBarAndroid.setMax(maxAndroid);
        progressBarAndroid.setProgress(progressAndroid);
        progressBarPhp.setMax(maxPhp);
        progressBarPhp.setProgress(progressPhp);
        progressBarJavascript.setMax(maxJavascript);
        progressBarJavascript.setProgress(progressJavascript);

    }
    //get data
    public String getSharedData(String key){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("app_questions", Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "0");
        System.out.println(key + "keydata" + value);
        return value;
    }
}
