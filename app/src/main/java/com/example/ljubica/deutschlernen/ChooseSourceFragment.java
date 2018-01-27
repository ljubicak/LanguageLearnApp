package com.example.ljubica.deutschlernen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseSourceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ChooseSourceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button showAddNewWord;
    private Button chooseFromLessons;
    private Button chooseRandomWords;
    private Button translation;

    public ChooseSourceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_source, container, false);
    }

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

        //initialize components
        showAddNewWord = (Button) getView().findViewById(
                R.id.btn_add_new_word);
        chooseFromLessons = (Button) getView().findViewById(
                R.id.btn_choose_from_lessons);
        chooseRandomWords = (Button) getView().findViewById(
                R.id.btn_choose_random_words);
        translation = (Button)getView().findViewById(R.id.btn_translation);

        //add listeners
        showAddNewWord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddNewWord addNewWordFragment = new AddNewWord();
                getFragmentManager().beginTransaction().replace(R.id.main_activity, addNewWordFragment)
                    .addToBackStack("ADD_WORD").commit();
            }
        });
        chooseFromLessons.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChooseLesson chooseLesson = new ChooseLesson();
                getFragmentManager().beginTransaction().replace(R.id.main_activity, chooseLesson)
                        .addToBackStack("CHOOSE_LESSON").commit();
            }
        });
        chooseRandomWords.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShowWords showWords = new ShowWords();
                getFragmentManager().beginTransaction().replace(R.id.main_activity, showWords)
                        .addToBackStack("SHOW_WORDS").commit();
            }
        });
        translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Translation translation = new Translation();
                getFragmentManager().beginTransaction().replace(R.id.main_activity, translation)
                        .addToBackStack("TRANSLATION").commit();
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
}
