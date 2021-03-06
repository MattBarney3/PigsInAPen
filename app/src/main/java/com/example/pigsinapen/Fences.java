package com.example.pigsinapen;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Represents the interactable fences seen in GameDisplay.
 *
 * @author Jared Matson unless specified otherwise
 *
 * <p>Methods:
 *        - Fences(Integer row, Integer col,Boolean horizontal,Context context,
 *            GameDisplay gameDisplay);
 *        - SetFenceCharacterisics (Boolean Horizontal);
 *        - getButton();
 *        - changeColor(Color color);
 *        - getOnClickDoSomething(Button button);
 *        - isVertical(Button fenceButton);
 *        - setButtonClicked(Boolean buttonClicked);
 *        - isButtonClicked();
 *        - getFenceButton();
 */
public class Fences {
  public Boolean visible = false;
  private Boolean horizontal = true;
  private Integer row;
  private Integer col;
  private Context context;
  private Button fenceButton;
  private GameDisplay currentDisplay;
  private float alphaColor;

  /* Alvee addedd */
  private Boolean buttonClicked;

  /**
   * Constructor for Fences.
   *
   * @param row sets what row the fence will be in
   * @param col sets what col the fence be will in
   * @param horizontal set line to either vertical or horizontal
   * @param context sets which activity the fence is being created
   * @param gameDisplay the display the fence is being created in
   */
  public Fences(
      Integer row, Integer col, Boolean horizontal, Context context, GameDisplay gameDisplay) {
    this.row = row;
    this.col = col;
    this.horizontal = horizontal;
    this.context = context;
    this.currentDisplay = gameDisplay;
    setFenceCharacteristics(horizontal);
  } // Fences

  /**
   * Adds characteristics to the fence that is being created.
   *
   * @param horizontal is the fence horizontal
   */
  void setFenceCharacteristics(Boolean horizontal) {
    buttonClicked = false;
    fenceButton = new Button(context);
    fenceButton.setBackgroundColor(Color.LTGRAY);
    fenceButton.setLayoutParams(new LinearLayout.LayoutParams(27, 120));
    // LinearLayout automatically rotates the button to be veritcal
    fenceButton.setAlpha(0.30f);
    fenceButton.setOnClickListener(getOnClickDoSomething(fenceButton));

    if (horizontal) {
      fenceButton.setRotation(90);
    } // if
  } // setFenceCharacteristics

  /** Returns the fence's button. */
  public View getButton() {
    return fenceButton;
  } // getButton

  /**
   * Changes the color of the fence to a desired color.
   *
   * @param color color to change fence too
   */
  public void changeColor(Integer color) {
    fenceButton.setBackgroundColor(color);
  } // changeColor

  /**
   * Will run playerturn using the dimensions of the fence that is clicked, will also make the
   * button uninteractable.
   *
   * @param button the fence that is being clicked
   */
  View.OnClickListener getOnClickDoSomething(final Button button) {
    return new View.OnClickListener() {
      public void onClick(View v) {
        fenceButton.setEnabled(false);
        setButtonClicked(true);
        fenceButton.setAlpha(1);
        currentDisplay.runTurn(row, col, horizontal);
      } // onClick
    };
  } // getOnClickDoSomething

  /**
   * @since 2019-11-11 To implement GameBoard class, I just added few methods, and an instance
   *     variable.
   *     <p>Alvee added these methods.
   */

  /**
   * Checks the button is vertical or horizontal.
   *
   * @param fenceButton Button
   * @return Boolean true - vertical , false - horizontal
   */
  public Boolean isVertical(Button fenceButton) {
    return horizontal;
  } // isVertical

  /**
   * Sets the Button value true when it is clicked.
   *
   * @param buttonClicked ButtonFee
   */
  public void setButtonClicked(Boolean buttonClicked) {
    this.buttonClicked = buttonClicked;
  } // setButtonClicked

  /**
   * Checks if the Button is clicked or not clicked.
   *
   * @return Boolean true - clicked, false - not clicked
   */
  public Boolean isButtonClicked() {
    return buttonClicked;
  } // isButtonClicked

  /**
   * Sets the Fences button transparency to 0.
   *
   * @param alphaColor float number
   */
  public void setAlphaColor(float alphaColor) {
    fenceButton.setAlpha(alphaColor);
  }
} // Class Fences
