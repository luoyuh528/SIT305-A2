package com.example.sudoku;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Matrix {

    private static int[][] GAMEDATA= {{6,5,0,8,7,3,0,9,0},
                                             {0,0,3,2,5,0,0,0,8},
                                             {9,8,0,1,0,4,3,5,7},
                                             {1,0,5,0,0,0,0,0,0},
                                             {4,0,0,0,0,0,0,0,2},
                                             {0,0,0,0,0,0,5,0,3},
                                             {5,7,8,3,0,1,0,2,6},
                                             {2,0,0,0,4,8,9,0,0},
                                             {0,9,0,6,2,5,0,8,1}};
    private int [][]mData ; // 原始数据
    private int [][]mCutData; // 当前数据
    //private  ArrayList quizzes= new MainActivity().getQuizzes();
    //private  ArrayList solutions= new MainActivity().getSolutions();
    private  int[][][] partQuizzes= MainActivity.getPartQuizzes();
    private  int[][][] partSolutions= MainActivity.getPartSolutions();

    public Matrix() {


        int i = (int)(Math.random()*0);
        //mainActivity.initData();
        //int i = (int)(Math.random()* new MainActivity().getCount());
        //quizzes=new MainActivity().getQuizzes(i);
        //solutions=new MainActivity().getSolutions(i);
        //int[][] gdata= new int[9][9];
        //gdata  = ((int[][]) quizzes.get(0));

        //initialize GAMEDATA
        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                GAMEDATA[x][y]=partQuizzes[i][x][y];
            }
        }

        switch (i) {
            case 1:
                mData = GAMEDATA;
                break;
            case 2:
                mData = GAMEDATA;
                break;
            case 3:
                mData = GAMEDATA;
                break;
            case 4:
                mData = GAMEDATA;
                break;
            case 0:
                mData = GAMEDATA;
                break;

        }
        initCutData();
        //initData();
        Log.e("Matrix", "random :"+i);

    }



    /** 得到当前坐标上的文字 */
    public String getText(int x, int y){

        String index = mData[x][y]+"";

        if ("0".equals(index)) {
            index = "";
        }

        return index;

    }

    /** 判断该坐标是否可以点击 */
    public boolean getOnClicked(int x, int y){

        if (mData[x][y] == 0) {
            return true;
        }
        return false;

    }

    /** 判断该坐标有哪些数不可用 */
    public int[] getFalseData(int x, int y){
        Set<Integer> set = new TreeSet<Integer>();

        // 检查X 轴有哪些不能点

        for (int i = 0; i < 9; i++) {
            int d = mData[y][i];

            if (d!=0) {
                set.add(d);
//  LogUtils.e("x: "+d);
            }
        }
        // 检查 y 轴有哪些不能点
        for (int i = 0; i < 9; i++) {
            int d = mData[i][x];
            if (d!=0) {
                set.add(d);
//  LogUtils.e("Y: "+d);
            }
        }

        // 检查 3*3 方格哪些不能点

        x = x/3*3;
        y = y/3*3;

// LogUtils.e(" x "+x+" Y "+y);

        for (int i = x; i < x+3; i++) {
            for (int j = y; j < y+3; j++) {
                int d = mData[j][i];
                if (d!=0) {
                    set.add(d);
//   LogUtils.e("i "+i+"j "+j+" xy: "+d);
                }
            }
        }


        Integer[] arr2 = set.toArray(new Integer[0]);
        // 数组的包装类型不能转 只能自己转；吧Integer转为为int数组；
        int[] result = new int[arr2.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr2[i];
        }
        System.out.println("false Number ： "+Arrays.toString(result));


        return result;
    }



    /** 当前棋盘数据 */
    public void initCutData(){

        mCutData = new int[9][9];

        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[i].length; j++) {
                mCutData[i][j] = mData[i][j];
            }
        }


        for (int i = 0; i < mCutData.length; i++) {
            System.out.println(Arrays.toString(mCutData[i]));
        }


    }

    public void setCutData(int x, int y, int data){
        if (getOnClicked(x, y)) {
            mCutData[x][y] = data;
        }

    }


    public int getCutData(int x, int y){
        return mCutData[x][y];
    }
}
