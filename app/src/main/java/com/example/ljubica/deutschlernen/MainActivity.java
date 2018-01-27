package com.example.ljubica.deutschlernen;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;


public class MainActivity extends AppCompatActivity
implements ChooseSourceFragment.OnFragmentInteractionListener,
        AddNewWord.OnFragmentInteractionListener, ChooseLesson.OnFragmentInteractionListener,
        ShowWords.OnFragmentInteractionListener, Translation.OnFragmentInteractionListener{
    DBHelper dbHelper;

    private ChooseSourceFragment chooseSourceFragment;
    private AddNewWord addNewWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this); //see the db in chrome

        CharSequence selectedWord = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT); //open selected text
        if(selectedWord != null){
            addNewWordFromSelectedText(selectedWord.toString());
            return;
        }

        dbHelper = new DBHelper(this);

        if (findViewById(R.id.fragment_placeholder) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            chooseSourceFragment = new ChooseSourceFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            chooseSourceFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity, chooseSourceFragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
    protected void chooseRandomWords(View v){

    }

    protected void chooseFromLessons(View v){

    }
    protected void addNewWord(View v){
    }
    protected void translation(View v){
    }

    //Helpers
    private void addNewWordFromSelectedText(String word){
        addNewWord = new AddNewWord();
        if (findViewById(R.id.fragment_placeholder) != null) {
            // Pass the selected word to the fragment as arguments
            Bundle bundle = new Bundle();
            bundle.putString("selectedWord", word);
            addNewWord.setArguments(bundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity, addNewWord).commit();
        }
    }
}
