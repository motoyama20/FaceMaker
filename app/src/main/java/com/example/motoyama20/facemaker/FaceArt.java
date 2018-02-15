package com.example.motoyama20.facemaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;



/**
 * Created by motoyama20 on 2/9/2018.
 *
 * This FaceArt class draws the face images (i.e. eyes, head, hair styles, mouth) and sets the
 * color values for the paints corresponding to each. The color values are changed in this class
 * whenever the user adjusts the color using the seek bar views.
 */

public class FaceArt extends SurfaceView{

    //to declare bitmap for the mouth
    private Bitmap smileImage = BitmapFactory.decodeResource(getResources(), R.drawable.smile);
    private int eyeRedVal; //red color value for eyes
    private int eyeGreenVal; //green color value for eyes
    private int eyeBlueVal; //blue color value for eyes
    private int skinRedVal; //red color val for skin
    private int skinGreenVal; //green color val for skin
    private int skinBlueVal; //blue color val for skin
    private int hairRedVal; //red color val for hair
    private int hairGreenVal; //green color val for hair
    private int hairBlueVal; //blue color val for hair
    private int hairStyle; //to represent hair styles: 0-baby hairs. 1-crazy hair. 2-afro hair.

    public FaceArt(Context context) {
        super(context);
        generalInit();
        randomize(); //to randomize color and hair values from the start
    }

    public FaceArt(Context context, AttributeSet attrs) {
        super(context, attrs);
        generalInit();
        randomize(); //to randomize color and hair values from the start
    }

    public FaceArt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        generalInit();
        randomize(); //to randomize color and hair values from the start
    }



    /**\
     * generalInit()
     *
     * Initialization stuff used by all constructors
     *
     */
    private void generalInit()
    {
        setWillNotDraw(false);
    }



    /**\
     * randomize()
     *
     * Uses Math.random to generate random values for eye, skin, and hair color and hair style
     * (by red, green, and blue values separately).
     *
     */
    public void randomize()
    {
        eyeRedVal = (int) (Math.random()*255+1);
        eyeGreenVal = (int) (Math.random()*255+1);
        eyeBlueVal = (int) (Math.random()*255+1);

        skinRedVal = (int) (Math.random()*255+1);
        skinGreenVal = (int) (Math.random()*255+1);
        skinBlueVal = (int) (Math.random()*255+1);

        hairRedVal = (int) (Math.random()*255+1);
        hairGreenVal = (int) (Math.random()*255+1);
        hairBlueVal = (int) (Math.random()*255+1);

        hairStyle = ((int) (Math.random()*3));
    }



    //setter and getter methods for color vals

    //for eye color
    public void setEyeRedVal(int redVal)
    {
        eyeRedVal = redVal;
    }
    public void setEyeGreenVal(int greenVal)
    {
        eyeGreenVal = greenVal;
    }
    public void setEyeBlueVal(int blueVal)
    {
        eyeBlueVal = blueVal;
    }

    public int getEyeRedVal()
    {
        return eyeRedVal;
    }
    public int getEyeGreenVal()
    {
        return eyeGreenVal;
    }
    public int getEyeBlueVal()
    {
        return eyeBlueVal;
    }

    //for skin color
    public void setSkinRedVal(int redVal)
    {
        skinRedVal = redVal;
    }
    public void setSkinGreenVal(int greenVal)
    {
        skinGreenVal = greenVal;
    }
    public void setSkinBlueVal(int blueVal)
    {
        skinBlueVal = blueVal;
    }

    public int getSkinRedVal()
    {
        return skinRedVal;
    }
    public int getSkinGreenVal()
    {
        return skinGreenVal;
    }
    public int getSkinBlueVal()
    {
        return skinBlueVal;
    }

    //for hair color
    public void setHairRedVal(int redVal)
    {
        hairRedVal = redVal;
    }
    public void setHairGreenVal(int greenVal)
    {
        hairGreenVal = greenVal;
    }
    public void setHairBlueVal(int blueVal)
    {
        hairBlueVal = blueVal;
    }

    public int getHairRedVal()
    {
        return hairRedVal;
    }
    public int getHairGreenVal()
    {
        return hairGreenVal;
    }
    public int getHairBlueVal()
    {
        return hairBlueVal;
    }

    //for hair style
    public void setHairStyle(int styleInt) { hairStyle = styleInt; }
    public int getHairStyle()
    {
        return hairStyle;
    }


    /**\
     * onDraw( Canvas canvas )
     * Creates and sets the paint colors for face. Calls draw methods for each part of the
     * face to be drawn on the surface view at the appropriate location.
     *
     * @param canvas : frame for the face to be drawn on
     *
     */
    @Override
    public void onDraw( Canvas canvas )
    {

        super.onDraw(canvas);
        canvas.drawColor( Color.WHITE ); //sets background to white

        //create and set hair, skin, and eye paints based on current RGB vals at run time
        Paint hairPaint = new Paint();
        hairPaint.setColor(Color.rgb(hairRedVal,hairGreenVal,hairBlueVal));
        Paint skinPaint = new Paint();
        skinPaint.setColor( Color.rgb(skinRedVal, skinGreenVal, skinBlueVal) );
        Paint eyePaint = new Paint();
        eyePaint.setColor(Color.rgb(eyeRedVal,eyeGreenVal,eyeBlueVal));

        Paint whitePaint = new Paint(); //create white paint for non-changing parts of eyes
        whitePaint.setColor(Color.rgb(255, 255, 255)); //set RGB vals for white
        Paint blackPaint = new Paint(); //create black paint for non-changing parts of eyes
        blackPaint.setColor(Color.rgb(0, 0, 0)); //set RGB vals for black

        //if hairStyle int is 1, call CrazyHair draw method
        if( hairStyle == 1 ) { drawCrazyHair(canvas, hairPaint); }
        //if hairStyle int is 2, call AfroHair draw method
        if( hairStyle == 2 ) { drawAfroHair(canvas, hairPaint); }

        //call drawHead method to draw the head and the mouth
        drawHead(canvas, skinPaint);

        //if hairStyle int is 0, call BabyHair draw method
        if( hairStyle == 0 ) { drawBabyHair(canvas, hairPaint); }

        //call drawEyes method to draw both eyes
        drawEyes(canvas, eyePaint, whitePaint, blackPaint);
    }


    /**\
     * drawHead( Canvas canvas, Paint skinPaint )
     * Draws the circle for the head and bitmap for the mouth at appropriate coordinates.
     *
     * @param canvas : frame for the face to be drawn on
     * @param skinPaint : current color of skin based on user interaction
     *
     */
    private void drawHead( Canvas canvas, Paint skinPaint )
    {
        //draw circle for head at bottom center of surface view
        canvas.drawCircle( 600.0f, 700.0f, 500.0f, skinPaint );

        //scale bitmap so that it is an appropriate size
        smileImage = smileImage.createScaledBitmap( smileImage, 500, 300, true );
        //draw bitmap for the mouth of face
        canvas.drawBitmap(smileImage, 250.0f, 700.0f, null);

        /**
         External Citation
         Date: 9 February 2018
         Problem: Could not change the size of the bitmap (kept cropping rather than getting
         smaller.
         Resource: https://developer.android.com/reference/android/graphics
         /Bitmap.html#createScaledBitmap(android.graphics.Bitmap, int, int, boolean)
         Solution: I used the syntax displayed and parameter descriptions from this post.
         */
    }


    /**\
     *  drawEyes( Canvas canvas, Paint eyePaint, Paint whitePaint, Paint blackPaint )
     * Draws circles for the eyes at appropriate coordinates.
     *
     * @param canvas : frame for the face to be drawn on
     * @param eyePaint : current color of iris of eyes based on user interaction
     * @param whitePaint : white paint for whites of eyes
     * @param blackPaint : black paint for outline and pupil of eyes
     *
     */
    private void drawEyes( Canvas canvas, Paint eyePaint, Paint whitePaint, Paint blackPaint )
    {
        canvas.drawCircle(400.0f, 550.0f, 100.0f, whitePaint); //left eye whites
        canvas.drawCircle(800.0f, 550.0f, 100.0f, whitePaint); //right eye white

        canvas.drawCircle(400.0f, 550.0f, 70.0f, eyePaint ); //left iris
        canvas.drawCircle(800.0f, 550.0f, 70.0f, eyePaint); //right iris
        canvas.drawCircle(400.0f, 550.0f, 50.0f, blackPaint); //left pupil
        canvas.drawCircle(800.0f, 550.0f, 50.0f, blackPaint); //right pupil

        canvas.drawCircle(460.0f, 525.0f, 30.0f, whitePaint); //left eye shine
        canvas.drawCircle(860.0f, 525.0f, 30.0f, whitePaint); //right eye shine

        blackPaint.setStrokeWidth(5.0f); //to set stroke for outline of eye
        blackPaint.setStyle(Paint.Style.STROKE); //to set the stroke style
        canvas.drawCircle(400.0f,550.0f, 100.0f, blackPaint); //left eye outline
        canvas.drawCircle(800.0f,550.0f, 100.0f, blackPaint); //right eye outline
    }


    /**\
     * drawBabyHair( Canvas canvas, Paint hairColor )
     * Draws a small patch of hair (baby hairs) at the top of the head using a Path.
     *
     * @param canvas : frame for the face to be drawn on
     * @param hairColor : current color of hair based on user interaction
     *
     */
    private void drawBabyHair( Canvas canvas, Paint hairColor )
    {
        hairColor.setStyle(Paint.Style.FILL); //set style so that hair Path is filled ("painted")

        Path babyHairs = new Path(); //create path
        babyHairs.reset();

        //sets points for the baby hair patch
        babyHairs.moveTo( 650.0f, 202.0f);
        babyHairs.lineTo(800.0f, 350.0f);
        babyHairs.lineTo(620.0f, 250.0f);
        babyHairs.lineTo(570.0f, 350.0f);
        babyHairs.lineTo(550.0f, 250.0f);
        babyHairs.lineTo(510.0f, 300.0f);
        babyHairs.lineTo(470.0f, 215.0f);
        babyHairs.lineTo(560.0f, 190.0f);
        babyHairs.moveTo( 650.0f, 202.0f);

        canvas.drawPath(babyHairs, hairColor); //draw baby hair patch

        /**
         External Citation
         Date: 9 February 2018
         Problem: Could not figure out how to draw and fill a polygon of custom shape (shape
         other than a rectangle, circle, oval, or arc).
         Resource:
         1) https://developer.android.com/reference/android/graphics/Path.html
         2) https://stackoverflow.com/questions/39611044/draw-custom-shape-on-canvas-in
         -android-using-path
         Solution: I used the method descriptions from source 1 and the example from source 2
         to solve this problem.
         */
    }


    /**\
     * drawCrazyHair( Canvas canvas, Paint hairColor )
     * Draws a crazy, spikey hair style at the top of the head using a Path.
     *
     * @param canvas : frame for the face to be drawn on
     * @param hairColor : current color of hair based on user interaction
     *
     */
    private void drawCrazyHair( Canvas canvas, Paint hairColor )
    {
        hairColor.setStyle(Paint.Style.FILL); //set style of Path so that hair is fille din

        Path crazyHair = new Path(); //create crazyHair path
        crazyHair.reset();

        //sets points for crazyHair
        crazyHair.moveTo( 170.0f, 450.0f);
        crazyHair.lineTo(50.0f, 350.0f);
        crazyHair.lineTo(150.0f, 300.0f);
        crazyHair.lineTo(200.0f, 150.0f);
        crazyHair.lineTo(350.0f, 200.0f);
        crazyHair.lineTo(450.0f, 100.0f);
        crazyHair.lineTo(530.0f, 150.0f);
        crazyHair.lineTo(620.0f, 100.0f);
        crazyHair.lineTo(680.0f, 150.0f);
        crazyHair.lineTo(750.0f, 100.0f);
        crazyHair.lineTo(800.0f, 200.0f);
        crazyHair.lineTo(950.0f, 200.0f);
        crazyHair.lineTo(960.0f, 450.0f);
        crazyHair.moveTo( 150.0f, 450.0f);

        //draws crazy hair path based on points set previously
        canvas.drawPath(crazyHair, hairColor);
    }


    /**\
     * drawAfroHair( Canvas canvas, Paint hairColor )
     * Draws a curly, afro-type hair style at the top of the head using circles.
     *
     * @param canvas : frame for the face to be drawn on
     * @param hairColor : current color of hair based on user interaction
     *
     */
    private void drawAfroHair( Canvas canvas, Paint hairColor )
    {
        //draws overlapping circles of the same color to create an afro-like hair style
        canvas.drawCircle(150.0f, 420.0f, 80.0f, hairColor);
        canvas.drawCircle(350.0f, 290.0f, 200.0f, hairColor);
        canvas.drawCircle(600.0f, 190.0f, 170.0f, hairColor);
        canvas.drawCircle(850.0f, 290.0f, 200.0f, hairColor);
        canvas.drawCircle(1050.0f, 420.0f, 80.0f, hairColor);
    }
}
