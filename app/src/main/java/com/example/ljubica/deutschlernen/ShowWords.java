package com.example.ljubica.deutschlernen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowWords.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowWords#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowWords extends Fragment {
    private DBHelper dbHelper;
    private ArrayList<Word> words; //list of words to be shown
    private Integer wordPointer = 0;
    private Integer prevWordPointer = 0;
    private String lessonName;
    private Handler handler;
    private Integer increaseProgres;
    private Integer correctWordsNum = 0;
    private Integer incorrectWordsNum = 0;

    private OnFragmentInteractionListener mListener;
    private Button selectDer;
    private Button selectDie;
    private Button selectDas;
    private TextView correctWords;
    private TextView incorrectWords;
    private Button repeat;
    private Button goBack;
    private ProgressBar progressBar;

    public ShowWords() {
        // Required empty public constructor
    }

    public static ShowWords newInstance() {
        ShowWords fragment = new ShowWords();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        if (getArguments() != null) {
            lessonName = (String) getArguments().getSerializable("lessonName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_words, container, false);
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get the words from db
        words = lessonName != null? dbHelper.getDataByLesson(lessonName) : dbHelper.getAllWords();
        Collections.shuffle(words);

        //intialize components
        handler = new Handler();
        correctWords = (TextView) getView().findViewById(R.id.percent_correct_words);
        incorrectWords = (TextView) getView().findViewById(R.id.percent_incorrect_words);
        repeat = (Button) getView().findViewById(R.id.repeat);
        goBack = (Button) getView().findViewById(R.id.goBack);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        progressBar.setMax(words.size());
        progressBar.setProgress(0);
        increaseProgres = Integer.valueOf(1/words.size());

        selectDer = (Button) getView().findViewById(
                R.id.select_der);
        selectDie = (Button) getView().findViewById(
                R.id.select_die);
        selectDas = (Button) getView().findViewById(
                R.id.select_das);

        displayWords();

        //set on click listeners
        selectDer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isCorrect = getWordArtikel(v).trim().equals("der");
                toggleButtons(selectDer, isCorrect, selectDas, selectDie);
            }
        });

        selectDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isCorrect = getWordArtikel(v).trim().equals("die");
                toggleButtons(selectDie, isCorrect, selectDer, selectDas);
            }
        });

        selectDas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isCorrect = getWordArtikel(v).trim().equals("das");
                toggleButtons(selectDas, isCorrect, selectDer, selectDie);
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //helpers
    private String getWordArtikel(View v){
        String textWord = ((TextView)getView().findViewById(R.id.show_word)).getText().toString();
        Word foundWord = filterWords(textWord);
        return foundWord != null ? foundWord.getDefiniterArtikel().trim() : null;
    }
    private Word filterWords(String textWord){
        Word foundWord = null;
        for(Word word : words){
            if(word.getWord() == textWord){
                foundWord = word;
                break;
            }
        }
        return foundWord;
    }
    private void toggleButtons(Button btnClicked, Boolean isCorrect, Button btn1, Button btn2){
        if(isCorrect){
            if(prevWordPointer != wordPointer)
                correctWordsNum++;

            btnClicked.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
            btn1.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
            btn2.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
            displayWords();

            //increment progress bar
            progressBar.incrementProgressBy(1);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetButtons();
                }
            }, 200);
        }else{
            prevWordPointer = wordPointer;

            btnClicked.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
            btn1.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
            btn2.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
        }
    }
    private void displayWords(){
        if(wordPointer >= words.size()){
            DecimalFormat df = new DecimalFormat("####0.00");
            Double perCorrectWords = correctWordsNum*100.0/words.size();
            Double perIncorrectWords = 100-perCorrectWords;

            String perCorrectWordsDStr = df.format(perCorrectWords) + "%";
            String perIncorrectWordsStr = df.format(perIncorrectWords) + "%";

            correctWords.setText(perCorrectWordsDStr);
            incorrectWords.setText(perIncorrectWordsStr);

            correctWords.setVisibility(View.VISIBLE);
            incorrectWords.setVisibility(View.VISIBLE);

            if(perCorrectWords < 90) {
                repeat.setVisibility(View.VISIBLE);
            }
            goBack.setVisibility(View.VISIBLE);
            return;
        }
        TextView showWod = (TextView) getView().findViewById(R.id.show_word);
        Word word = words.get(wordPointer);
        showWod.setText(word.getWord());
        prevWordPointer = wordPointer;
        wordPointer++;
    }
    private void resetButtons(){
        selectDer.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
        selectDie.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
        selectDas.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.MULTIPLY);
    }

    private void restart(){
        Collections.shuffle(words);
        wordPointer = 0;
        prevWordPointer = 0;
        correctWordsNum = 0;
        incorrectWordsNum = 0;

        repeat.setVisibility(View.INVISIBLE);
        goBack.setVisibility(View.INVISIBLE);

        correctWords.setVisibility(View.INVISIBLE);
        incorrectWords.setVisibility(View.INVISIBLE);

        progressBar.setProgress(0);
        displayWords();
    }

}
