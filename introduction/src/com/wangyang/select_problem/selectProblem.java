package com.wangyang.select_problem;

public class selectProblem{
    public static <T extends Comparable<? super T>> T select(T[] arr){
        int maxIndex=0;
        for(int i=1;i<arr.length;i++){
            if(arr[i].compareTo(arr[maxIndex])>0){
                maxIndex=i;
            }
        }
        return arr[maxIndex];
    }
}
