package com.wangyang.leftlist_heap;



public class LeftlistHeap<AnyType extends Comparable<AnyType>>{
    public static class HeapNode<AnyType>{
        private int npl;
        private AnyType element;
        private HeapNode<AnyType> left;
        private HeapNode<AnyType> right;
        public HeapNode(){
            npl=0;
            element=null;
            left=right=null;
        }
        public HeapNode(int npl, AnyType element,HeapNode<AnyType> left,HeapNode<AnyType> right){
            this.npl=npl;
            this.element=element;
            this.left=left;
            this.right=right;
        }
    }
    private HeapNode<AnyType> root;
    public void merge(LeftlistHeap<AnyType> h){
           this.root=merge(this.root,h.root);
    }
    private HeapNode<AnyType> merge(HeapNode<AnyType> r1,HeapNode<AnyType> r2){
        if(r1==null){
            return r2;
        }else if(r2==null){
            return r1;
        }else{
            HeapNode<AnyType> temp=null;
            if(r1.element.compareTo(r2.element)<0){
                temp=r1;
                r1=r2;
                r2=temp;
            }
            r1.right=merge(r1.right,r2);
            if(r1.right.npl<r1.left.npl){
                temp=r1.left;
                r1.left=r1.right;
                r1.right=temp;
            }
            r1.npl=r1.right.npl+1;
            return r1;
        }
    }
    public void push(AnyType x){
        HeapNode<AnyType> newNode=new HeapNode<>(0,x,null,null);
        this.root=merge(this.root,newNode);
    }
    public AnyType pop(){
        AnyType temp=this.root.element;
        this.root=merge(this.root.left,this.root.right);
        return temp;
    }
}
