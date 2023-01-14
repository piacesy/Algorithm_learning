package com.wangyang.Btree;

import java.util.LinkedList;

public class BTree <AnyType extends Comparable<AnyType>>{
   public static class BTreeNode<AnyType>{
       private LinkedList<AnyType> keys;
       private LinkedList<BTreeNode<AnyType>> childs;
       private boolean isLeaf;
       public BTreeNode<AnyType> parent;
       public BTreeNode(){
           keys=new LinkedList<>();
           childs=new LinkedList<>();
           isLeaf=true;
       }
       public void addKey(AnyType x) {
           keys.add(x);
       }
       public void addKey(AnyType x,int pos){
           keys.add(pos,x);
       }
       public boolean isLeaf(){
           return this.isLeaf;
       }
       public void addChilds(BTreeNode<AnyType> t){
           childs.add(t);
       }
       public void addChilds(BTreeNode<AnyType> t,int pos){
           childs.add(pos,t);
       }
       public int getKeyNumber(){
           return keys.size();
       }
       public AnyType getKey(int index){
           return keys.get(index);
       }
       public BTreeNode<AnyType> getChild(int index){
           return childs.get(index);
       }
       public void setChild(BTreeNode<AnyType> t,int index){
           childs.set(index,t);
       }
       public void setKey(AnyType x,int index){
           keys.set(index,x);
       }
   }
   private BTreeNode<AnyType> root;
   private int order;
   private int max;
   private int min;
   public BTree(int order){
       this.order=order;
       this.max=this.order-1;
       this.min=(int)Math.ceil(order/2)-1;
   }
   public boolean isEmpty(){
       return root==null;
   }
   public void makeEmpty(){
       root=null;
   }
   public boolean contains(AnyType x){
       return contains(x,root);
   }
   public boolean  contains(AnyType x,BTreeNode<AnyType> t){
       int pos=0;
       while(pos<t.getKeyNumber()&&x.compareTo(t.getKey(pos))<0){
           pos++;
       }
       if(x.compareTo(t.getKey(pos))==0){
           return true;
       }else{
           if(t.isLeaf()){
               return false;
           }else{
               return contains(x,t.getChild(pos));
           }
       }
   }
   public void insert(AnyType x){
       root=insert(x,root);
   }
   public BTreeNode<AnyType> insert(AnyType x,BTreeNode<AnyType> t){
       if(this.isEmpty()){
           root=new BTreeNode<>();
           root.parent=null;
           root.addKey(x);
           root.addChilds(null);
           root.addChilds(null);
       }else{
           if(this.root.isLeaf()){
               int pos=0;
               while(pos<root.getKeyNumber()&&x.compareTo(root.getKey(pos))<0){
                   pos++;
               }
               root.addKey(x,pos);
               if(root.getKeyNumber()>this.max){
                   root=split(root);
               }
               return root;
           }else{
               int pos=0;
               while(pos<root.getKeyNumber()&&x.compareTo(root.getKey(pos))<0){
                   pos++;
               }
               if(t.isLeaf()){
                   t.addKey(x,pos);
                   t.addChilds(null);
                   return t;
               }else{
                   t.setChild(insert(x,t.getChild(pos)),pos);
                   if(t.getKeyNumber()>max){
                       split(t.getChild(pos));
                   }else{
                       return t;
                   }
               }
           }
       }
       return null;
   }
   public BTreeNode<AnyType> split(BTreeNode<AnyType> t){
       if(t==this.root){
           int midIndex=(int)Math.ceil((t.getKeyNumber()-1)/2.0);
           AnyType mid =t.getKey(midIndex);
           BTreeNode<AnyType> n1=new BTreeNode<>();
           BTreeNode<AnyType> n2=new BTreeNode<>();
           for(int i=0;i<midIndex;i++){
               n1.addKey(t.getKey(i));
           }
           for(int i=0;i<=n1.getKeyNumber();i++){
               n1.addChilds(null);
           }
           for(int i=midIndex+1;i<t.getKeyNumber();i++){
               n2.addKey(t.getKey(i));
           }
           for(int i=0;i<=n2.getKeyNumber();i++){
               n2.addChilds(null);
           }
           BTreeNode<AnyType> newRoot=new BTreeNode<>();
           n1.parent=n2.parent=newRoot;
           newRoot.addKey(mid);
           newRoot.addChilds(n1);
           newRoot.addChilds(n2);
           return newRoot;
       } else  if(t.isLeaf()){
           int midIndex=(int)Math.ceil((t.getKeyNumber()-1)/2.0);
           AnyType mid =t.getKey(midIndex);
           BTreeNode<AnyType> n1=new BTreeNode<>();
           BTreeNode<AnyType> n2=new BTreeNode<>();
           for(int i=0;i<midIndex;i++){
               n1.addKey(t.getKey(i));
           }
           for(int i=0;i<=n1.getKeyNumber();i++){
               n1.addChilds(t.getChild(i));
           }
           for(int i=midIndex+1;i<t.getKeyNumber();i++){
               n2.addKey(t.getKey(i));
           }
           for(int i=0;i<=n2.getKeyNumber();i++){
               n2.addChilds(t.getChild(i+n1.getKeyNumber()+1));
           }
           n1.parent=n2.parent=t.parent;
           int pos=0;
           while(pos<t.parent.getKeyNumber()&&mid.compareTo(t.parent.getKey(pos))<0){
               pos++;
           }
           t.parent.addKey(mid,pos);
           t.parent.setChild(n1,pos);
           t.parent.addChilds(n2,pos+1);
           return t;
       }
       return null;
   }
}
