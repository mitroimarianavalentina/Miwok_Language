package com.example.marry.miwok_starter_code;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//mostenim super clasa ArrayAdapter
public class WordAdapter extends ArrayAdapter<Word> {

    //global variable so we can set the background of each page: numbers, colors, family, phrases
    private int mColorId;

    //constructorul clasei
    //Activity context se foloseste pentru a umple Layout-ul nostru cu elemente
    //ArrayList  contine informatiile, listele, cu care vrem sa umplem layout-ul
    public WordAdapter(Context context, ArrayList<Word>words, int colorId){
        ///@param - context este la fel ca mai sus
        //al doilea  -  @param 0 - se foloseste cand populam ArrayAdapterul cu un singur TextView
        //pt ca in cazul de fata, el va fi populat cu 2 TextView-uri, acest parametru nu va fi folosit, deci poate lua orice valoare
        super (context, 0, words);
        mColorId = colorId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //verificam daca un View existent este refolosit, in caz contrar sa incarcam View-ul
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //get the Word object located at this position in the list
        Word currentWord = getItem(position);

        //find the textView in the list_numbers.xml with the id miwok_text_view
        TextView miwok = listItemView.findViewById(R.id.miwok_text_view);
        // Get the miwok translation from the current Word object and
        // set this text on the name TextView
        miwok.setText(currentWord.getmMiwokTranslation());

        //find the textView in the list_numbers.xml with the id default_text_view
        TextView english = listItemView.findViewById(R.id.default_text_view);
        // Get the english translation from the current Word object and
        // set this text on the name TextView
        english.setText(currentWord.getmDefaultTranslation());


        //find the ImageView in the list_numbers.xml with the id image
        //gaseste ImageView-ul unde va afisa imagine daca exista
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        // Check if an image is provided for the current word or not
        //verificam daca pentru cuvantul ce urmeaza a fi afisat exista si o imagine asociata
        if (currentWord.hasImage()){
            // If an image is available, display the provided image based on the resource ID
            // daca exista o imagine asociata, o vom afisa
            imageView.setImageResource(currentWord.getmMiwokImage());
            // Make sure the view is visible
            // vom seta ImageView-ul ca fiind vizibil
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            //altfel, vom seta ImageView-ul ca fiind ascuns
            // Otherwise hide the ImageView (set visibility to GONE)
            //daca am fi folosit INVISIBLE ar fi fst lasat spatiu cat pentru imagine inaintea textului
            //fiind setata ca GONE, nu se va patra acest spatiu, si se va afisa textul de la inceputul randului
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        View view = listItemView.findViewById(R.id.text);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorId);
        // Set the background color of the text View
        view.setBackgroundColor(color);

        return listItemView;

    }
}
