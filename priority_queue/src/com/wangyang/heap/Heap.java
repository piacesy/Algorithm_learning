package com.wangyang.heap;

import java.util.ArrayList;

public class Heap<AnyType extends Comparable<AnyType>>{
    ArrayList<AnyType> list;
    public Heap(){
        list=new ArrayList<>();
    }
    public void clear(){
        list.clear();
    }
    public boolean isEmpty(){
        return list.size()==0;
    }
    public AnyType pop(){
        if(isEmpty()){
            return null;
        }else{
            AnyType head=list.get(0);
            list.set(0,list.get(list.size()-1));
            list.remove(list.size()-1);
            filterDown(0);
            return head;
        }
    }
    public void push(AnyType t){
        list.add(list.size(),t);
        filterUp(list.size()-1);
    }
    public void filterDown(int index){
        int index1=index*2+1;
        int index2=index*2+2;
        if(index1>=list.size()&&index2>=list.size()){
            return;
        }else if(index1>=list.size()){
            if(list.get(index).compareTo(list.get(index2))<0){
                AnyType temp=list.get(index);
                list.set(index,list.get(index2));
                list.set(index2,temp);
                filterDown(index2);
            }else{
                return;
            }
        }else if(index2>=list.size()){
            if(list.get(index).compareTo(list.get(index1))<0){
                AnyType temp=list.get(index);
                list.set(index,list.get(index1));
                list.set(index1,temp);
                filterDown(index1);
            }else{
                return;
            }
        }else{
            int max=list.get(index1).compareTo(list.get(index2))>0?index1:index2;
            if(list.get(index).compareTo(list.get(max))<0){
                AnyType temp=list.get(index);
                list.set(index,list.get(max));
                list.set(max,temp);
                filterDown(max);
            }else{
                return;
            }
        }
    }
    public void filterUp(int index){
        if(index==0){
            return;
        }
        int preIndex=(index-1)/2;
        if(list.get(index).compareTo(list.get(preIndex))>0){
            AnyType temp=list.get(index);
            list.set(index,list.get(preIndex));
            list.set(preIndex,temp);
            filterUp(preIndex);
        }else{
            return;
        }
    }
    public void addAll(AnyType[] arr){
        if(!isEmpty()){
            for(int i=0;i<arr.length;i++){
                push(arr[i]);
            }
        }else{
            for(int i=0;i<arr.length;i++) {
                list.add(arr[i]);
            }
            for(int i=list.size()-1;i>=0;i--){
                filterUp(i);
            }
        }
    }
}
