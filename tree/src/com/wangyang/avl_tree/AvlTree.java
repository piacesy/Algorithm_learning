package com.wangyang.avl_tree;

import com.wangyang.binarysearchtree.BinarySearchTree;

public class AvlTree<AnyType extends Comparable<AnyType>> {
    private static class AvlNode<AnyType>{
        AnyType element;
        AvlNode<AnyType> left;
        AvlNode<AnyType> right;
        int height;
        public AvlNode(AnyType element,AvlNode<AnyType>left,
                       AvlNode<AnyType>right,int height){
            this.element=element;
            this.right=right;
            this.left=left;
            this.height=height;
        }
    }
    private AvlNode<AnyType> root;
    public void makeEmpty(){
        root=null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public boolean contains(AnyType x){
        return contains(x,root);
    }
    public AnyType findMax(){
        return findMax(root);
    }
    public AnyType findMin(){
        return findMin(root);
    }
    public void insert(AnyType x){
        root=insert(x,root);
    }
    public void remove(AnyType x){
        root=remove(x,root);
    }
    private boolean contains(AnyType x,AvlNode<AnyType> t){
        if(t==null){
            return false;
        }else{
            if(t.element.compareTo(x)==0){
                return true;
            }else if(t.element.compareTo(x)<0){
                return contains(x,t.right);
            }else if(t.element.compareTo(x)>0){
                return contains(x,t.left);
            }
        }
        return false;
    }
    private AnyType findMax(AvlNode<AnyType> t){
        if(t==null){
            return null;
        }else if(t.right==null){
            return t.element;
        }else{
            return findMax(t.right);
        }
    }
    private  AnyType findMin(AvlNode<AnyType> t){
        if(t==null){
            return null;
        } else if(t.left==null){
            return t.element;
        }else{
            return findMin(t.left);
        }
    }

    private AvlNode<AnyType> insert(AnyType x,AvlNode<AnyType> t){
        if(t==null){
            return new AvlNode<AnyType>(x,null,null,0);
        }else if(t.element.compareTo(x)<0){
            t.right=insert(x,t.right);
            if(t.right.height-t.left.height==2){
                if(t.right.element.compareTo(x)<0){
                    t=rotateWithRightChild(t);
                }else{
                    t=doubleWithRightChild(t);
                }
            }
        }else if(t.element.compareTo(x)>0){
            t.left=insert(x,t.left);
            if(t.left.height-t.right.height==2){
                if(t.left.element.compareTo(x)>0){
                    t=rotateWithLeftChild(t);
                }else{
                    t=doubleWithLeftChild(t);
                }
            }
        }
        int leftHeight=t.left==null?0:t.left.height;
        int rightHeight=t.right==null?0:t.right.height;
        t.height=Math.max(leftHeight,rightHeight)+1;
        return t;
    }
    private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> t){
        AvlNode<AnyType> newRoot=t.right;
        t.right=t.left;
        newRoot.left=t;
        t.height=Math.max(t.left.height,t.right.height)+1;
        newRoot.height=Math.max(newRoot.left.height,newRoot.right.height)+1;
        return newRoot;
    }
    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> t){
        AvlNode<AnyType>newRoot =t.left;
        t.left=newRoot.right;
        newRoot.right=t;
        t.height=Math.max(t.left.height,t.right.height)+1;
        newRoot.height=Math.max(newRoot.left.height,newRoot.right.height)+1;
        return newRoot;
    }
    private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> t){
        t.right=rotateWithLeftChild(t.right);
        return rotateWithRightChild(t);
    }
    private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> t){
        t.left=rotateWithRightChild(t.left);
        return rotateWithLeftChild(t);
    }
    private AvlNode<AnyType> remove(AnyType x,AvlNode<AnyType>t){
        return t;
    }
}
