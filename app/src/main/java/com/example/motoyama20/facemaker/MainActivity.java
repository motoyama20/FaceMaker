package com.example.motoyama20.facemaker;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;


/**
 * Created by motoyama20 on 2/9/2018.
 *
 * This Main Activity class registers each view (i.e. button, seek bar, spinner) by id and sets
 * each to a listener. Listeners for buttons, seekbars, and spinners have been implemented.
 * This allows the activity of the user to be recorded and this class tells the program to
 * carry out certain actions based on the user's activity.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Spinner.OnItemSelectedListener {

    private FaceArt faceArt; //declares an instance of the class FaceArt
    private String[] hairStyles = {"Baby Hairs", "Crazy Hair", "Afro Hair" }; //set spinner labels
    private RadioButton eyeRadio, skinRadio, hairRadio; //declares radio buttons
    private SeekBar redSeek, greenSeek, blueSeek; //declares seekbars
    private Spinner hairStyle; //declares hairStyle Spinner



    /**\
     * onCreate(Bundle savedInstanceState)
     *
     * Initializes views and sets them to their appropriate listener methods.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //random face button
        Button randomFace = (Button)findViewById(R.id.randomFaceButton);
        randomFace.setOnClickListener(this);

        //connects the instance of FaceArt to the surface view
        faceArt = (FaceArt)findViewById(R.id.surfaceView);

        //radio buttons
        eyeRadio = (RadioButton)findViewById(R.id.eyeRadioB); //radio button for eyes
        eyeRadio.setOnClickListener(this);
        hairRadio = (RadioButton)findViewById(R.id.hairRadioB); //radio button for hair
        hairRadio.setOnClickListener(this);
        skinRadio = (RadioButton)findViewById(R.id.skinRadioB); //radio button for skin
        skinRadio.setOnClickListener(this);

        //seek bars
        redSeek = (SeekBar)findViewById(R.id.redSeek); //seek bar for red color value
        redSeek.setOnSeekBarChangeListener(this);
        greenSeek = (SeekBar)findViewById(R.id.greenSeek); //seek bar for green color value
        greenSeek.setOnSeekBarChangeListener(this);
        blueSeek = (SeekBar)findViewById(R.id.blueSeek); //seek bar for blue color value
        blueSeek.setOnSeekBarChangeListener(this);

        //spinner for hair style choice
        //sets up drop down option so that labels are displayed when spinner is clicked
        ArrayAdapter<String> hairAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hairStyles);
        hairStyle = (Spinner)findViewById(R.id.hairStyleSpin);
        hairStyle.setAdapter(hairAdapter);
        hairStyle.setOnItemSelectedListener(this);
        //randomized hair style from start (when the program is first run)
        hairStyle.setSelection((int) (Math.random()*3));
    }



    /**\
     * onClick(View v)
     *
     * Actions to be carried out when a View is clicked.
     *      Unclicks other two radio buttons when one is clicked.
     *      Sets random RGB color values and random hair style int value when random face
     *      button is preseed.
     *      Calls a helper method to update the progress of the seekBars when radio buttons are
     *      pressed.
     *
     * @param v : view that was clicked
     *
     */
    @Override
    public void onClick(View v) {

        //if one radio button is pressed, unmark any other two radio buttons by comparing the
        // clicked view id to the id of one of the radio buttons
        if( v.getId() == R.id.eyeRadioB)
        {
            skinRadio.setChecked(false); //to "unmark" a radio button
            hairRadio.setChecked(false);
            updateOnRadioB();
        }
        if( v.getId() == R.id.skinRadioB)
        {
            eyeRadio.setChecked(false);
            hairRadio.setChecked(false);
            updateOnRadioB();
        }
        if( v.getId() == R.id.hairRadioB)
        {
            eyeRadio.setChecked(false);
            skinRadio.setChecked(false);
            updateOnRadioB();
        }

        /**
         External Citation
         Date: 9 February 2018
         Problem: Did not know the method to set a radio button to "checked" or "unchecked."
         Resource: https://developer.android.com/reference/android/widget/Checkable.html
         Solution: I used the method descriptions to solve this problem.
         */

        //if clicked view id matches the random face button id, randomly set color and hair values
        if( v.getId() == R.id.randomFaceButton )
        {
            faceArt.randomize(); //generate random RGB and hairStyle

            //to set the spinner so it corresponds with the hairStyle on face
            hairStyle.setSelection(faceArt.getHairStyle());

            /**
             External Citation
             Date: 9 February 2018
             Problem: Did not know the method to set the label displayed on spinner through program
             Resource: https://stackoverflow.com/questions/11072576/set-selected-item-of
             -spinner-programmatically
             Solution: I used the examples to figure out how the method works and apply to this
             situation.
             */

            updateOnRadioB(); //to update the seekbar thumbs to correspond with actual values

            faceArt.invalidate(); //to redraw

            /**
             External Citation
             Date: 9 February 2018
             Problem: Seekbars and progress values were working (I tested this by putting print
             lines in different areas of the code and having it print certain values), but the
             image wasn't redrawing.
             Resource: https://stackoverflow.com/questions/10647558/when-its-necessary-to
             -execute-invalidate-on-a-view
             Solution: I used the examples to figure out how the method works and where to place
             it to work with my code.
             */
        }

    }



    /**\
     * updateOnRadioB()
     *
     * Checks to see which radio button is pressed and uses that info to update the progress on
     * seekbars accordingly.
     *
     */
    private void updateOnRadioB()
    {
        //if eye radioButton is checked update progress for seekBars based on random eye color
        if( eyeRadio.isChecked() )
        {
            //set progresses based on random eye RGB vals
            redSeek.setProgress(faceArt.getEyeRedVal());
            greenSeek.setProgress(faceArt.getEyeGreenVal());
            blueSeek.setProgress(faceArt.getEyeBlueVal());
        }
        //if skin radioButton is checked update progress for seekBars based on random skin color
        if( skinRadio.isChecked() )
        {
            //set progresses based on random skin RGB vals
            redSeek.setProgress(faceArt.getSkinRedVal());
            greenSeek.setProgress(faceArt.getSkinGreenVal());
            blueSeek.setProgress(faceArt.getSkinBlueVal());
        }
        //if hair radioButton is checked update progress for seekBars based on random hair color
        if( hairRadio.isChecked() )
        {
            //set progresses based on random hair RGB vals
            redSeek.setProgress(faceArt.getHairRedVal());
            greenSeek.setProgress(faceArt.getHairGreenVal());
            blueSeek.setProgress(faceArt.getHairBlueVal());
        }
    }



    /**\
     * onItemSelected(AdapterView<?> parent, View view, int position, long id)
     *
     * Actions to be carried out when an item is selected on the hair style spinner.
     *      Sets the hairStyle int in FaceArt to correspond with the position of the
     *      spinner label selected by user.
     *
     * @param position : position of spinner label selected by user
     *
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //pass position to setHairStyle method in FaceArt class to determine hair style of face
        faceArt.setHairStyle(position);
        faceArt.invalidate();
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //this method does nothing
    }



    /**\
     * onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
     *
     * Actions to be carried out when the thumb of a seekbar is moved.
     *      Call set methods in FaceArt class to set the RGB values for color of eye, skin,
     *      and hair.
     *      Determines which face part color to change based on the radio button that is checked
     *
     * @param seekBar : seekBar moved
     * @param progress : position of spinner label selected by user
     *
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //if eye radio button is checked, adjust RGB values of EYES accordingly
        if( eyeRadio.isChecked() )
        {
            updateEyeColorProgress(seekBar, progress);
        }
        //same strategy for skin color (if skin radio button is checked instead)
        else if( skinRadio.isChecked() )
        {
            updateSkinColorProgress(seekBar, progress);
        }
        //same strategy for hair color (if hair radio button is checked instead)
        else if( hairRadio.isChecked() )
        {
            updateHairColorProgress( seekBar, progress);
        }

        faceArt.invalidate(); //update drawing

    }



    /**\
     * updateEyeColorProgress( SeekBar seekBar, int progress)
     *
     * Carries out the following actions
     *      Checks if the seekbar being used matches the red, green, or blue seekbar.
     *      Updates the red, green, or blue val for eye color based on the progress
     *      (where the seek bar is moved to).
     *
     * @param seekBar : seekBar being used
     * @param progress : value that the seekBar is moved to
     *
     */
    private void updateEyeColorProgress( SeekBar seekBar, int progress)
    {
        //if dragged seekBar ID matches the red seekBar ID
        if( seekBar.getId() == R.id.redSeek )
        {
            //call setEyeRedVal method in FaceArt class to set red value for eye color to
            // the seekBar's current progress
            faceArt.setEyeRedVal(progress);
        }
        //same strategy for green and blue seekBars/values below
        else if( seekBar.getId() == R.id.greenSeek )
        {
            faceArt.setEyeGreenVal(progress);
        }
        else if( seekBar.getId() == R.id.blueSeek )
        {
            faceArt.setEyeBlueVal(progress);
        }
    }



    /**\
     * updateSkinColorProgress( SeekBar seekBar, int progress )
     *
     * Carries out the following actions
     *      Checks if the seekbar being used matches the red, green, or blue seekbar.
     *      Updates the red, green, or blue val for skin color based on the progress
     *      (where the seek bar is moved to).
     *
     * @param seekBar : seekBar being used
     * @param progress : value that the seekBar is moved to
     *
     */
    private void updateSkinColorProgress( SeekBar seekBar, int progress )
    {
        //if dragged seekBar ID matches the red seekBar ID
        if( seekBar.getId() == R.id.redSeek )
        {
            //call setSkinRedVal method in FaceArt class to set red value for skin color to
            // the seekBar's current progress
            faceArt.setSkinRedVal(progress);
        }
        //same strategy for green and blue seekBars/values below
        else if( seekBar.getId() == R.id.greenSeek )
        {
            faceArt.setSkinGreenVal(progress);
        }
        else if( seekBar.getId() == R.id.blueSeek )
        {
            faceArt.setSkinBlueVal(progress);
        }
    }


    /**\
     * updateHairColorProgress( SeekBar seekBar, int progress )
     *
     * Carries out the following actions
     *      Checks if the seekbar being used matches the red, green, or blue seekbar.
     *      Updates the red, green, or blue val for hair color based on the progress
     *      (where the seek bar is moved to).
     *
     * @param seekBar : seekBar being used
     * @param progress : value that the seekBar is moved to
     *
     */
    private void updateHairColorProgress( SeekBar seekBar, int progress )
    {
        //if dragged seekBar ID matches the red seekBar ID
        if( seekBar.getId() == R.id.redSeek )
        {
            //call setSkinRedVal method in FaceArt class to set red value for skin color to
            // the seekBar's current progress
            faceArt.setHairRedVal(progress);
        }
        //same strategy for green and blue seekBars/values below
        else if( seekBar.getId() == R.id.greenSeek )
        {
            faceArt.setHairGreenVal(progress);
        }
        else if( seekBar.getId() == R.id.blueSeek )
        {
            faceArt.setHairBlueVal(progress);
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //This method does nothing (only needed to be included for listener implementation).
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //This method does nothing (only needed to be included for listener implementation).
    }
}
