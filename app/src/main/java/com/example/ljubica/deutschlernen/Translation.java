package com.example.ljubica.deutschlernen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Translation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Translation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Translation extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private Button btnGerman1;
    private Button btnEnglish1;
    private Button btnGerman2;
    private Button btnEnglish2;
    private Button btnGerman3;
    private Button btnEnglish3;
    private Button btnGerman4;
    private Button btnEnglish4;
    private Button btnGerman5;
    private Button btnEnglish5;
    private Button clickedGermanBtn;
    private String clickedGermanWord;
    private ArrayList<Word> words;
    private OnFragmentInteractionListener mListener;
    private DBHelper dbHelper;
    private HashMap<String, String> wordsMap;
    private Button btnRestart;

    public Translation() {
        // Required empty public constructor
    }


    public static Translation newInstance(String param1, String param2) {
        Translation fragment = new Translation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        clickedGermanWord = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnRestart = (Button) getView().findViewById(R.id.btn_restart_words);
        btnRestart.setOnClickListener(this);

        btnGerman1 = (Button) getView().findViewById(R.id.word_german1);
        btnGerman1.setOnClickListener(this);
        btnGerman2 = (Button) getView().findViewById(R.id.word_german2);
        btnGerman2.setOnClickListener(this);
        btnGerman3 = (Button) getView().findViewById(R.id.word_german3);
        btnGerman3.setOnClickListener(this);
        btnGerman4 = (Button) getView().findViewById(R.id.word_german4);
        btnGerman4.setOnClickListener(this);
        btnGerman5 = (Button) getView().findViewById(R.id.word_german5);
        btnGerman5.setOnClickListener(this);

        btnEnglish1 = (Button) getView().findViewById(R.id.word_english1);
        btnEnglish1.setOnClickListener(this);
        btnEnglish2 = (Button) getView().findViewById(R.id.word_english2);
        btnEnglish2.setOnClickListener(this);
        btnEnglish3 = (Button) getView().findViewById(R.id.word_english3);
        btnEnglish3.setOnClickListener(this);
        btnEnglish4 = (Button) getView().findViewById(R.id.word_english4);
        btnEnglish4.setOnClickListener(this);
        btnEnglish5 = (Button) getView().findViewById(R.id.word_english5);
        btnEnglish5.setOnClickListener(this);

        //set the words in buttons
        words = dbHelper.getAllWords();

        setButtons();

        wordsMap = new HashMap<String,String>();

        for(Word word : words){
            wordsMap.put(word.getWord(), word.getPluralForm());
        }


    }

    @Override
    public void onClick(View v){

        if(v.getId()==R.id.word_german1 || v.getId()==R.id.word_german2 || v.getId()==R.id.word_german3 || v.getId()==R.id.word_german4 || v.getId()==R.id.word_german5){
            clickedGermanBtn = (Button)v;
            clickedGermanWord = clickedGermanBtn.getText().toString();
            clickedGermanBtn.setTextColor(getResources().getColor(R.color.green));
        }

        if(v.getId()==R.id.word_english1 || v.getId()==R.id.word_english2 || v.getId()==R.id.word_english3 || v.getId()==R.id.word_english4 || v.getId()==R.id.word_english5){
            Button clickedButton = (Button)v;
            String englishWord = clickedButton.getText().toString();
            if(clickedGermanWord != ""){

                String wordTranslation = wordsMap.get(clickedGermanWord);
                if(wordTranslation == englishWord){
                    clickedButton.setVisibility(View.INVISIBLE);
                    clickedGermanBtn.setVisibility(View.INVISIBLE);
                }
                else{
                    clickedGermanWord = "";
                    clickedGermanBtn.setTextColor(Color.BLACK);                }
            }

        }
        if(v.getId() == R.id.btn_restart_words){
            setButtons();
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void setButtons(){

        btnGerman1.setVisibility(View.VISIBLE);
        btnEnglish1.setVisibility(View.VISIBLE);
        btnGerman2.setVisibility(View.VISIBLE);
        btnEnglish2.setVisibility(View.VISIBLE);
        btnGerman3.setVisibility(View.VISIBLE);
        btnEnglish3.setVisibility(View.VISIBLE);
        btnGerman4.setVisibility(View.VISIBLE);
        btnEnglish4.setVisibility(View.VISIBLE);
        btnGerman5.setVisibility(View.VISIBLE);
        btnEnglish5.setVisibility(View.VISIBLE);

        btnGerman1.setTextColor(Color.BLACK);
        btnGerman2.setTextColor(Color.BLACK);
        btnGerman3.setTextColor(Color.BLACK);
        btnGerman4.setTextColor(Color.BLACK);
        btnGerman5.setTextColor(Color.BLACK);

        Collections.shuffle(words);
        ArrayList<String> germanWords = new ArrayList<>();
        ArrayList<String> englishWords = new ArrayList<>();

        for(Word word: words){
            germanWords.add(word.getWord());
            englishWords.add(word.getPluralForm());
        }

        Collections.shuffle(englishWords);

        if(words.size() > 0){
            btnGerman1.setText(germanWords.get(0));
            btnEnglish1.setText(englishWords.get(0));
        }
        else{
            btnGerman1.setVisibility(View.INVISIBLE);
            btnEnglish1.setVisibility(View.INVISIBLE);
        }
        if(words.size() > 1){
            btnGerman2.setText(germanWords.get(1));
            btnEnglish2.setText(englishWords.get(1));
        }
        else{
            btnGerman2.setVisibility(View.INVISIBLE);
            btnEnglish2.setVisibility(View.INVISIBLE);
        }
        if(words.size() > 2){
            btnGerman3.setText(germanWords.get(2));
            btnEnglish3.setText(englishWords.get(2));
        }
        else{
            btnGerman3.setVisibility(View.INVISIBLE);
            btnEnglish3.setVisibility(View.INVISIBLE);
        }
        if(words.size() > 3){
            btnGerman4.setText(germanWords.get(3));
            btnEnglish4.setText(englishWords.get(3));
        }
        else{
            btnGerman4.setVisibility(View.INVISIBLE);
            btnEnglish4.setVisibility(View.INVISIBLE);
        }
        if(words.size() > 4){
            btnGerman5.setText(germanWords.get(4));
            btnEnglish5.setText(englishWords.get(4));
        }
        else{
            btnGerman5.setVisibility(View.INVISIBLE);
            btnEnglish5.setVisibility(View.INVISIBLE);
        }

    }
}
