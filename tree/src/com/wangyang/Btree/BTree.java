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
       return contains(x,root)==null?false:true;
   }
   public BTreeNode<AnyType>  contains(AnyType x,BTreeNode<AnyType> t){
       int pos=0;
       while(pos<t.getKeyNumber()&&x.compareTo(t.getKey(pos))<0){
           pos++;
       }
       if(x.compareTo(t.getKey(pos))==0){
           return t;
       }else{
           if(t.isLeaf()){
               return null;
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
   public boolean remove(AnyType t){
       return !(remove(t,root)==null);
   }
   private AnyType remove(AnyType t,BTreeNode<AnyType> x){
       BTreeNode<AnyType> target=contains(t,x);
       if(target==null){
           return null;
       }
       if(target.isLeaf()){
            target.keys.remove(t);
            target.childs.pop();
            if(target==root){
                return t;
            }else{
                if(target.getKeyNumber()<min){
                    changeOrMerge(t,target.parent);
                }
                return t;
            }
       }else{
           int index=target.keys.indexOf(t);
           BTreeNode<AnyType> child1=target.childs.get(index);
           BTreeNode<AnyType> child2=target.childs.get(index+1);
           if(child1.keys.size()>min){
               int mid=(int)Math.ceil((child1.keys.size()-1)/2.0);
               AnyType temp=remove(child1.keys.get(mid),child1);
               target.keys.remove(index);
               target.keys.add(index,temp);
           }else if(child2.keys.size()>min){
               int mid=(int)Math.ceil((child2.keys.size()-1)/2.0);
               AnyType temp=remove(child2.keys.get(mid),child2);
               target.keys.remove(index);
               target.keys.add(index,temp);
           }else{
               if(target!=this.root&&target.keys.size()==min){
                   changeOrMerge(t,target.parent);
               }else{
                   merge(target,index,index+1);
               }
           }
           return t;
       }
   }
   private void changeOrMerge(AnyType x,BTreeNode<AnyType> t){
       int index=0;
       while(t.keys.get(index).compareTo(x)>=0){
           index++;
       }
       if(index==t.getKeyNumber()){
           if(t.childs.get(index-1).keys.size()==min){
               merge(t,index,index-1);
           }else{
               change(t,index,index-1);
           }
       }else if(index==0){
           if(t.childs.get(index+1).keys.size()==min){
               merge(t,index,index+1);
           }else{
               change(t,index,index+1);
           }
       }else{
           if(t.childs.get(index-1).keys.size()>min){
               change(t,index,index-1);
           }else if(t.childs.get(index+1).keys.size()>min){
               change(t,index,index+1);
           }else{
               merge(t,index,index-1);
           }
       }
   }
   private void change(BTreeNode<AnyType> t,int index1,int index2){
       BTreeNode<AnyType> child1=t.childs.get(index1);
       BTreeNode<AnyType> child2=t.childs.get(index2);
       if(index1>index2){
           AnyType temp1=t.keys.get(index2);
           int index=(int)Math.ceil((child2.getKeyNumber()-1)/2.0);
           AnyType temp2=child2.keys.get(index);
           remove(temp2,child2);
           child2.childs.pop();
           t.keys.set(index2,temp2);
           child1.keys.add(0,temp1);
           child1.childs.push(null);
       }else{
           AnyType temp1=t.keys.get(index1);
           int index=(int)Math.ceil((child2.getKeyNumber()-1)/2.0);
           AnyType temp2=child2.keys.get(index);
           remove(temp2,child2);
           child2.childs.pop();
           t.keys.set(index1,temp2);
           child1.keys.addLast(temp1);
           child1.childs.push(null);
           t.keys.set(index1,temp2);
       }
   }
   private void merge(BTreeNode<AnyType> t,int index1,int index2){
       if(index1>index2){
           int temp=index1;
           index1=index2;
           index2=temp;
       }
       BTreeNode<AnyType> child1=t.childs.get(index1);
       BTreeNode<AnyType> child2=t.childs.get(index2);
       AnyType temp1=t.keys.get(index1);
       remove(temp1,t);
       child1.keys.add(temp1);
       child1.childs.add(child2.childs.get(0));
       for(int i=0;i<child2.getKeyNumber();i++){
           child1.keys.add(child2.keys.get(i));
           child2.childs.add(child2.childs.get(i+1));
       }
       t.childs.remove(child2);
   }
}
