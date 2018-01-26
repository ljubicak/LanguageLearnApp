package com.example.ljubica.deutschlernen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewWord.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewWord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewWord extends Fragment {
    private Button addNewWord;
    private DBHelper dbHelper;



    private OnFragmentInteractionListener mListener;

    public AddNewWord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewWord.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewWord newInstance(String param1, String param2) {
        AddNewWord fragment = new AddNewWord();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_word, container, false);
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

        addNewWord = (Button) getView().findViewById(
                R.id.btn_add_word);
        addNewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner definiterArtikel = (Spinner) getView().findViewById(R.id.definiter_artikel);
                AppCompatEditText word = (AppCompatEditText) getView().findViewById(R.id.word);

                Spinner pluralForm = (Spinner) getView().findViewById(R.id.plural_forms);

                AppCompatEditText translation = (AppCompatEditText) getView().findViewById(R.id.word_translation);
                Spinner lesson = (Spinner) getView().findViewById(R.id.lessons);

                String wordStr = word.getText().toString().substring(0, 1).toUpperCase() + word.getText().toString().substring(1).toLowerCase();
                String translationStr = translation.getText().toString().substring(0,1).toUpperCase() + translation.getText().toString().substring(1).toString().toLowerCase();

                dbHelper.insertWord(definiterArtikel.getSelectedItem().toString().toLowerCase(), wordStr, pluralForm.getSelectedItem().toString(), translationStr, lesson.getSelectedItem().toString());

                Toast.makeText(getContext(), "New word added!", Toast.LENGTH_SHORT).show();
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
}
