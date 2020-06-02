package com.example.sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GameView gV;

    // 1. 定义菜单项的标识
    final private int  OPEN = 111;
    public static ArrayList quizzes=new ArrayList<int [][]>();
    public static ArrayList solutions=new ArrayList<int [][]>();
    public static int[][][] partQuizzes= new int[30][9][9];
    public static int[][][] partSolutions= new int[30][9][9];
    public static String record="Name : Jeff\n"+"Time: 15 mins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        //int[][] gdata= (int[][]) quizzes.get(0);
        gV = (GameView) findViewById(R.id.game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载 XML 菜单目录
        getMenuInflater().inflate(R.menu.menu, menu);

        //menu.add(1,OPEN,0,"Open");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 菜单项被选中时触发
        int id = item.getItemId();
        String label = "";

        Log.d("OptionMenu",String.valueOf(id));

        switch (id){
            case R.id.create_new_game:
                label = "New Game";
                gV.repeat();
                break;
            case R.id.record:
                label = "Highest Record";
                showRecordDialog();
                break;
            case R.id.help:
                label = "Game Help";
                showHelpDialog();
                break;
            /*case OPEN:
                label = "Open";
                break;*/
        }

        Toast.makeText(getApplicationContext(),"What you clicked is :" + label,Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    public void rePay(View v){
        gV.repeat();
    }
    public void newPay(View v){
        gV.play();
    }

    //Show the /game record
    public void showRecordDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Highest Record");
        builder.setMessage(record);
        builder.setPositiveButton("Ok, I know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    //Show the /game Help dialog
    public void showHelpDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Game Helper");
        builder.setMessage(
                "Fill in the other spaces with numbers 1-9. " +
                "Each number from 1 to 9 appears only once in each row, column, and house, so it is also called 'nine palaces.'\n\n"+
                "Notice: If you complete the Sudoku in a shortest time, you can save your name.");

        builder.setPositiveButton("Ok, I know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }



    public void initData(){

        ArrayList newList=new ArrayList<String>();
        try {
            InputStream instream = getResources().openRawResource(R.raw.sudoku);
            if (instream != null)
            {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while (( line = buffreader.readLine()) != null) {
                    newList.add(line+"\n");
                }
                instream.close();
            }
        }
        catch (java.io.FileNotFoundException e)
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        catch (IOException e)
        {
            Log.d("TestFile", e.getMessage());
        }

        for(int i = 0; i < newList.size(); i++){
            String temp=newList.get(i).toString();
            int[][] questions= new int[9][9];
            int[][] answers= new int[9][9];
            int tempRow=0;
            int tempCol=0;

            for(int j=0; j<temp.length();j++)
            {
                if(j<81){
                    tempRow=j/9;
                    tempCol=j%9;
                    questions[tempRow][tempCol]=Character.getNumericValue(temp.charAt(j));
                }
                if(j==81);// ','
                if(j>81&&j<163){
                    tempRow=(j-82)/9;
                    tempCol=(j-82)%9;
                    answers[tempRow][tempCol]=Character.getNumericValue(temp.charAt(j));
                }
            }
            quizzes.add(questions);
            solutions.add(answers);

            if(i<30){
                for(int x=0; x<9; x++){
                    for(int y=0; y<9; y++){
                        partQuizzes[i][x][y]=questions[x][y];
                        partSolutions[i][x][y]=answers[x][y];
                    }
                }
            }
        }
    }

    public ArrayList getQuizzes(){
        return quizzes;
    }

    public ArrayList getSolutions(){
        return solutions;
    }

    public static int[][][] getPartQuizzes(){return partQuizzes;}
    public static int[][][] getPartSolutions(){return partSolutions;}


}
