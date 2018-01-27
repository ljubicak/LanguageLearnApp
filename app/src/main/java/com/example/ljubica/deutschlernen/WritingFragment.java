package com.example.ljubica.deutschlernen;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class WritingFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private DBHelper dbHelper;
    private ArrayList<Word> words;
    private LinearLayout lettersLayout;
    private TextView wordTextView;
    private TextView writingTextView;
    private int counter;
    private HashMap<String, String> wordsMap;
    private Button checkBtn;
    private Button nextBtn;

    public WritingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());
        words = dbHelper.getAllWords();
        counter = 0;

        wordsMap = new HashMap<String, String>();
        for(Word word: words){
            wordsMap.put(word.getPluralForm(), word.getWord());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lettersLayout = (LinearLayout) getView().findViewById(R.id.letters);
        wordTextView = (TextView) getView().findViewById(R.id.txtWord);
        writingTextView = (TextView) getView().findViewById(R.id.txtWriting);
        checkBtn = (Button)getView().findViewById(R.id.btn_checkWord);
        checkBtn.setOnClickListener(this);
        nextBtn = (Button)getView().findViewById(R.id.btn_nextWord);
        nextBtn.setOnClickListener(this);


        newWord();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_writing, container, false);
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
    public void onClick(View v) {

        if(v.getId() == R.id.btn_checkWord){
            String word = wordTextView.getText().toString();
            String writtenWord = writingTextView.getText().toString();

            String value = wordsMap.get(word);
            if(!value.equals(writtenWord)){
                writingTextView.setTextColor(Color.RED);
            }
            else{
                writingTextView.setTextColor(Color.GREEN);
            }
        }
        else if(v.getId() == R.id.btn_nextWord){
            counter++;
            newWord();
        }
        else{
            Button clickedBtn = (Button)v;
            String letter = (String) clickedBtn.getText();

            String writtenWord = writingTextView.getText().toString();
            writtenWord+=letter;
            writingTextView.setText(writtenWord);
            clickedBtn.setVisibility(View.INVISIBLE);
        }

    }

    public void newWord(){
        writingTextView.setText("");
        if(counter < words.size()){
            Word word = words.get(counter);
            wordTextView.setText(word.getPluralForm());

            String germanWord = word.getWord();
            ArrayList<Character> letters = new ArrayList<>();
            for(int i=0; i<germanWord.length(); i++){
                letters.add(germanWord.charAt(i));
            }

            lettersLayout.removeAllViews();
            writingTextView.setTextColor(Color.BLACK);
            Collections.shuffle(letters);

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            int countBtns = 0;
            for(Character letter: letters){
                Button btn = new Button(getContext());
                btn.setOnClickListener(this);
                btn.setText(letter.toString());

                countBtns++;
                if(countBtns == 5){
                    countBtns=0;
                    lettersLayout.addView(row);
                    row = new LinearLayout(getContext());
                    row.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                }
                row.addView(btn);
            }
            lettersLayout.addView(row);
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
