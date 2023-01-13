package com.wangyang.binarysearchtree;


public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>{
    public static void main(String[] args) {
        BinarySearchTree<String> binarySearchTree=new BinarySearchTree<>();
        String[] arr=new String[26];
        for(int i=0;i<26;i++){
            Character temp=(char)('a'+i);
            arr[i]=temp.toString();
        }
        for(int i=13;i<26;i++){
            binarySearchTree.insert(arr[i]);
        }
        for(int i=0;i<13;i++){
            binarySearchTree.insert(arr[i]);
        }
       binarySearchTree.printTree();
    }
    private static class BinaryNode<AnyType>{
        public AnyType element;
        public BinaryNode<AnyType> left;
        public BinaryNode<AnyType> right;
        public BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        public BinaryNode(AnyType element){
            this(element,null,null);
        }

        public BinaryNode(){

        }

    }
    private BinaryNode<AnyType> root;
    public BinarySearchTree(){
        root=null;
    }
    public void makeEmpty(){
        root=null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public boolean contains(AnyType x){
        return contains(x,root);
    }
    public AnyType findMin(){
        if(isEmpty()){
            return null;
        }else{
            return findMin(root).element;
        }
    }
    public AnyType findMax(){
        if(isEmpty()){
            return null;
        }else{
            return findMax(root).element;
        }
    }
    public void insert(AnyType x){
        root=insert(x,root);
    }
    public void remove(AnyType x){
        root=remove(x,root);
    }
    public void printTree(){
        printTree(this.root);
    }
    private boolean contains(AnyType x,BinaryNode<AnyType> t){
        if(t==null){
            return false;
        }else{
            if(t.element.compareTo(x)==0){
                return true;
            }else if(t.element.compareTo(x)<0){
                return contains(x,t.right);
            }else if(t.element.compareTo(x)>0){
                return  contains(x,t.left);
            }
        }
        return false;
    }
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t){
        if(t==null){
            return null;
        } else if(t.left==null){
            return t;
        }else{
            return findMin(t.left);
        }
    }
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t){
        if(t==null){
            return null;
        }else if(t.right==null){
            return t;
        }else{
            return findMax(t.right);
        }
    }
    private BinaryNode<AnyType> insert(AnyType x,BinaryNode<AnyType> t){
        if(t==null){
           return new BinaryNode<AnyType>(x,null,null);
        }else if(t.element.compareTo(x)<0){
            t.right=insert(x,t.right);
        }else if(t.element.compareTo(x)>0){
            t.left=insert(x,t.left);
        }
        return t;
    }
    private BinaryNode<AnyType> remove(AnyType x,BinaryNode<AnyType> t){
        if(t==null){
            return null;
        }else if(t.element.compareTo(x)<0){
            t.right=remove(x,t.right);
        }else if(t.element.compareTo(x)>0){
            t.left=remove(x,t.left);
        }else if(t.element.compareTo(x)==0){
            if(t.left==null&&t.right==null){
                return null;
            }else if(t.left==null){
                t.element=t.right.element;
                t.right=null;
            }else if(t.right==null){
                t.element=t.left.element;
                t.left=null;
            }else{
                BinaryNode<AnyType> min=findMin(t.right);
                t.element=min.element;
                remove(min.element,t.right);
            }
        }
        return t;
    }
    private void printTree(BinaryNode<AnyType> t){
        if(t==null){
            return;
        }else{
            System.out.print("[");
            printTree(t.left);
            System.out.print("["+t.element+"]");
            printTree(t.right);
            System.out.print("]");
        }
    }
}
