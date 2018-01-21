package com.example.ljubica.deutschlernen;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private String lessonName;

    private OnFragmentInteractionListener mListener;
    private Button selectDer;
    private Button selectDie;
    private Button selectDas;

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
                if(getWordArtikel(v).trim().equals("der")){
                    selectDer.setBackgroundColor(Color.GREEN);
                    selectDas.setBackgroundColor(Color.TRANSPARENT);
                    selectDie.setBackgroundColor(Color.TRANSPARENT);
                    displayWords();
                }
                else{
                    selectDer.setBackgroundColor(Color.RED);
                    selectDas.setBackgroundColor(Color.TRANSPARENT);
                    selectDie.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        selectDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getWordArtikel(v).equals("die")){
                    selectDie.setBackgroundColor(Color.GREEN);
                    selectDer.setBackgroundColor(Color.TRANSPARENT);
                    selectDas.setBackgroundColor(Color.TRANSPARENT);
                    displayWords();
                }
                else{
                    selectDie.setBackgroundColor(Color.RED);
                    selectDer.setBackgroundColor(Color.TRANSPARENT);
                    selectDas.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        selectDas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getWordArtikel(v).trim().equals("das")){
                    selectDas.setBackgroundColor(Color.GREEN);
                    selectDie.setBackgroundColor(Color.TRANSPARENT);
                    selectDer.setBackgroundColor(Color.TRANSPARENT);
                    displayWords();
                }
                else{
                    selectDas.setBackgroundColor(Color.RED);
                    selectDie.setBackgroundColor(Color.TRANSPARENT);
                    selectDer.setBackgroundColor(Color.TRANSPARENT);
                }
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
    private void displayWords(){
        if(wordPointer >= words.size())
            return;
        TextView showWod = (TextView) getView().findViewById(R.id.show_word);
        Word word = words.get(wordPointer);
        showWod.setText(word.getWord());
        wordPointer++;
    }
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
}
