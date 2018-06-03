package com.example.marry.miwok_starter_code;


public class Word {
    /*
    * default translation for the word
    */
    private String mDefaultTranslation;

    /*
     * miwok translation for the word
     */
    private String mMiwokTranslation;

    /*
     * image resource ID for the word
     * pornim de la premiza ca actualul cuvant nu are o imagine asociata
     * daca va avea, contructorul va modifica id-ul lui mMiwokImage si va seta metoda hasImage cu true
     */
    private int mMiwokImage = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    //ii dam -1, pt ca id-ul inei poze nu poate fi niciodata un nr. negativ
    private  static  final int NO_IMAGE_PROVIDED = -1;

    /*
     * miwok audio for the word
     */
    private int mMiwokAudio;

    /*
     * constructor to instantiate the members of the class
     *
     * @param defaultTranslation - is the word of the user's language
     *
     * @param miwokTranslation - is the word in Miwok language
     */
    public Word(String defaultTranslation, String miwokTranslation, int MiwokAudio) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mMiwokAudio = MiwokAudio;
    }

    /*
     * constructor to instantiate the members of the class
     *
     * @param defaultTranslation - is the word of the user's language
     *
     * @param miwokTranslation - is the word in Miwok language
     *
     * @param MiwokImage - is the specific image for each word
     */
    public Word(String DefaultTranslation, String MiwokTranslation, int MiwokImage, int MiwokAudio){
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mMiwokImage = MiwokImage;
        mMiwokAudio = MiwokAudio;
    }

    /*
     * default translation for the word
     */
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    /*
     * miwok translation for the word
     */
    public String getmMiwokTranslation() { return mMiwokTranslation;}

    /*
     * image for the word
     */
    public int getmMiwokImage() { return mMiwokImage;}

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mMiwokImage != NO_IMAGE_PROVIDED;
    }

    /*
     * audio for the word
     */
    public int getmMiwokAudio() { return mMiwokAudio;}
}
