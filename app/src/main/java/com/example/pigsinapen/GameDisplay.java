package com.example.pigsinapen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameDisplay extends AppCompatActivity {

  LinearLayout game_board_layout;

  Player player1, player2;
  GameBoard gameBoard;
  Fences fence;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game_display);
    gameBoard = new GameBoard(6,6,GameDisplay.this, this);
    showGrid(5,5);


    player1 = new Player("Alvee", 0, true);
    player2 = new Player("Jared", 0, false);


  }

  //  back button
  public void GoBackToMenu(View v) {
    Intent goBackToMainMenu = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(goBackToMainMenu);
  } // goBackToMenu


  public void playerTurn(int row, int col, boolean horizontal) {
    System.out.println(row + " " + col + " " + horizontal);
    Player currentPlayer = getCurrentPlayer();
    Player otherPlayer = getOtherPlayer();
    Integer closedBoxes = gameBoard.checkBoxes(row, col, horizontal);
    if (closedBoxes > 0) currentPlayer.addToScore(closedBoxes);
    else currentPlayer = getCurrentPlayer();
    otherPlayer = getOtherPlayer();
    checkGameEnd();
    System.out.println(row + " " + col + " " + horizontal +"\t"+closedBoxes);
  }

  Player getCurrentPlayer() {
    if (player1.checkCurrentPlayer()) return player1;
    else return player2;
  }

  Player getOtherPlayer() {
    if (player1.checkCurrentPlayer()) return player2;
    else return player1;
  }

  void checkGameEnd() {
    Integer currentScore = player1.getScore() + player2.getScore();
    if (currentScore == gameBoard.getMaxScore()) displayWinner();
  }

  void displayWinner() {
    if (player1.getScore() > player2.getScore()){
    //if(true){
      Toast.makeText(GameDisplay.this, player1.getName() + "wins", Toast.LENGTH_SHORT).show();
      Intent indent = new Intent(getApplicationContext(), Popup.class);
      indent.putExtra("player_name" , player1.getName());
      startActivity(indent);
    }
    else if (player1.getScore() < player2.getScore()) {
      Toast.makeText(GameDisplay.this, player2.getName() + "wins", Toast.LENGTH_SHORT).show();
      Intent indent = new Intent(getApplicationContext(), Popup.class);
      indent.putExtra("player_name" , player2.getName());
      startActivity(indent);
    }
    else {
//      Intent indent = new Intent(getApplicationContext(), Popup.class);
//      indent.putExtra("player_name", "AI ");
//      startActivity(indent);
}
    //displayWinner
  }

  // ------CALL QUIT and REPLAY--------
  void quit(){
  }

  /**
   * Will display grid in GameDisplay activity
   * @param row amount of rows the user wants
   * @param col amount of cols the user wants
   */
  void showGrid(int row, int col){
    int putHorFenceX = setHorFenceX(row, col);
    int putHorFenceY = setHorFenceY(row, col);
    int putButtonX = setButtonX(row, col);
    int putButtonY = setButtonY(row, col);
    int putVertFenceX = setVertFenceY(row, col);
    int putVertFenceY = setVertFenceX(row, col);

    createHorizontalFencesAndDots(putHorFenceX,putHorFenceY,putButtonX,putButtonY,row, col);
    createVerticalFences(putVertFenceX,putVertFenceY, row, col);
  }//sizeOfGridToMake

  /**
   * This function will begin to create the horizontal fences and button visuals
   * @param putHorFenceX initial X coordinate of horizontal fence
   * @param putHorFenceY initial Y coordinate of horizontal fence
   * @param putButtonX initial X coordinate of the fence button
   * @param putButtonY initial Y coordinate of the fence button
   * @param amountOfRows amount of rows the grid has
   * @param amountOfCols amount of columns the grid has
   */
  void createHorizontalFencesAndDots(int putHorFenceX, int putHorFenceY, int putButtonX, int putButtonY, int amountOfRows, int amountOfCols){
    for (int row = 0; row < amountOfRows; row += 1) { // total amount of rows
      putHorFenceY += 183;
      putButtonY += 183;
      createHorizontalFences(putHorFenceX, putHorFenceY, row, amountOfCols);
      createDots(putButtonX, putButtonY, amountOfCols);
    } // for
  }//createHorizontalFencesAndDots

  /**
   * Takes information from CreateHorizontalFencesAndDots and produces the horizontal fences
   * @param putHorFenceX starting x coordinate of horizontal fence
   * @param putHorFenceY starting y coordinate of horizontal fence
   * @param row what row the horizontal fences are being created in
   */
  void createHorizontalFences(int putHorFenceX, int putHorFenceY, int row, int amountOfCols) {
    for (int col = 0; col < amountOfCols - 1; col += 1) { // num of hor lines per row
      ConstraintLayout layout = findViewById(R.id.boardGameConstraint);
      gameBoard.getOneFence(row, col, true).getButton().setX(putHorFenceX);
      gameBoard.getOneFence(row, col, true).getButton().setY(putHorFenceY);
      layout.addView( gameBoard.getHorizontalFences(row, col).getButton());
      putHorFenceX += 170;
    } // forHorfences
  }

  /**
   * Takes information from CreateHorizontalFencesAndDots and produces the dot visuals.
   * @param putButtonX starting x coordiante of dot button
   * @param putButtonY starting y coordinate of dot button
   */
  void createDots(int putButtonX, int putButtonY, int amountOfCols) {
    for (int col = 0; col < amountOfCols; col += 1) { // dots per row
      ConstraintLayout layout = findViewById(R.id.boardGameConstraint);
      ImageView dot = new ImageView(this);
      dot.setX(putButtonX);
      dot.setY(putButtonY);
      dot.setBackgroundResource(R.drawable.dotnew);
      layout.addView(dot);
      android.view.ViewGroup.LayoutParams layoutParams = dot.getLayoutParams();
      layoutParams.width = 35;
      layoutParams.height = 35;
      putButtonX += 170;
      dot.setLayoutParams(layoutParams);
    } // fordot
  }

  /**
   *
   * @param putVertFenceX
   * @param putVertFenceY
   * @param amountOfRows
   * @param amountOfCols
   */
  void createVerticalFences(int putVertFenceX, int putVertFenceY, int amountOfRows, int amountOfCols) {
    for (int row = 0; row < amountOfRows - 1; row += 1) { // num rows
      putVertFenceY += 183;
      placeVertFences(putVertFenceX,putVertFenceY, row, amountOfCols);

      } // innerFor
  } // createVerticalFences
  void placeVertFences(int putVertFenceX, int putVertFenceY, int row, int amountOfCols){
    for (int col = 0; col < amountOfCols; col += 1) { // lines per row
      ConstraintLayout layout = findViewById(R.id.boardGameConstraint);

      gameBoard.getOneFence(row, col, false).getButton().setX(putVertFenceX);
      gameBoard.getOneFence(row, col, false).getButton().setY(putVertFenceY);
      putVertFenceX += 170;
      layout.addView(gameBoard.getVerticalFences(row, col).getButton());
    }//for
  }///placeVertFences
  int setHorFenceX(int rowSize,int colSize){
    if(rowSize == 4 && colSize == 4){
      return 100;
    }//else if
    else if(rowSize == 5 && colSize == 5){
      return 170;
    }//else if
    else if(rowSize == 6 && colSize == 6){
      return 90;
    }//else if
    return 0;
  }//setHorFenceX

  int setHorFenceY(int rowSize,int colSize){
    if(rowSize == 4 && colSize == 4){
      return 250;
    }//else if
    else if(rowSize == 5 && colSize == 5){
      return 0;
    }//else if
    else if(rowSize == 6 && colSize == 6){
      return 0;
    }//else if
    return 0;
  }//setHorFenceY
  int setButtonX(int rowSize,int colSize){
    if(rowSize == 4 && colSize == 4){
      return 160;
    }//else if
    else if(rowSize == 5 && colSize == 5){
      return 80;
    }//else if
    else if(rowSize == 6 && colSize == 6){
      return 0;
    }//else if
    return 0;
  }//setButtonX
  int setButtonY(int rowSize,int colSize){
    if(rowSize == 4 && colSize == 4){
      return 144;
    }//else if
    else if(rowSize == 5 && colSize == 5){
      return 44;
    }//else if
    else if(rowSize == 6 && colSize == 6){
      return 44;
    }//else if
    return 0;
  }//setButtonY
  int setVertFenceX(int rowSize,int colSize){
    if(rowSize == 4 && colSize == 4){
      return 166;
    }//else if
    else if(rowSize == 5 && colSize == 5){
      return 86;
    }//else if
    else if(rowSize == 6 && colSize == 6){
      return 6;
    }//else if
    return 0;
  }//setVertFenceX
  int setVertFenceY(int rowSize,int colSize){
    if(rowSize == 4 && colSize == 4){
      return 189;
    }//else if
    else if(rowSize == 5 && colSize == 5){
      return 89;
    }//else if
    else if(rowSize == 6 && colSize == 6){
      return 89;
    }//else if
    return 0;
  }//setVertFenceY
}
