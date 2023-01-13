package com.wangyang.spraytree;

import com.wangyang.avl_tree.AvlTree;
import com.wangyang.binarysearchtree.BinarySearchTree;

public class SprayTree<AnyType extends Comparable<AnyType>> {
    private static class SprayNode<AnyType>{
        public AnyType element;
        public SprayNode<AnyType> left;
        public SprayNode<AnyType> right;
        public SprayNode(AnyType element,SprayNode<AnyType>left,SprayNode<AnyType>right){
            this.element=element;
            this.left=left;
            this.right=right;
        }
    }
    private SprayNode<AnyType> root;
    public  SprayTree(){
        root=null;
    }
    public void makeEmpty(){
        root=null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public boolean contains(AnyType x){
        if((root=contains(x,root)).element.compareTo(x)==0){
            return true;
        }else{
            return false;
        }
    }
    public void insert(AnyType x){
        insert(x,root);
    }
    private SprayNode<AnyType> rotateWithRightChild(SprayNode<AnyType> t){
        SprayNode<AnyType> newRoot=t.right;
        t.right=t.left;
        newRoot.left=t;
        return newRoot;
    }
    private SprayNode<AnyType> rotateWithLeftChild(SprayNode<AnyType> t){
        SprayNode<AnyType> newRoot =t.left;
        t.left=newRoot.right;
        newRoot.right=t;
        return newRoot;
    }
    private SprayNode<AnyType> doubleWithRightChild(SprayNode<AnyType> t){
        t.right=rotateWithLeftChild(t.right);
        return rotateWithRightChild(t);
    }
    private SprayNode<AnyType> doubleWithLeftChild(SprayNode<AnyType> t){
        t.left=rotateWithRightChild(t.left);
        return rotateWithLeftChild(t);
    }
    private SprayNode<AnyType> contains(AnyType x,SprayNode<AnyType> t){
        if(t==null){
            return null;
        }else{
            if(t.element.compareTo(x)==0){
                return t;
            }else if(t.element.compareTo(x)<0){
                if(t.right==null){
                    return t;
                }else if(t.right.element.compareTo(x)==0){
                    t=rotateWithRightChild(t);
                    return t;
                }else if(t.right.element.compareTo(x)<0){
                    t.right.right=contains(x,t.right.right);
                    if(t.right.right!=null&&
                            t.right.right.element.compareTo(x)==0){
                        t=rotateWithRightChild(t);
                        t=rotateWithRightChild(t);
                    }
                    return t;
                }else if(t.right.element.compareTo(x)>0){
                    t.right.left=contains(x,t.right.left);
                    if(t.right.left!=null&&
                            t.right.left.element.compareTo(x)==0){
                        t=doubleWithRightChild(t);
                    }
                    return t;
                }
            }else if(t.element.compareTo(x)>0){
                if(t.left==null){
                    return t;
                }else if(t.left.element.compareTo(x)==0){
                    t=rotateWithLeftChild(t);
                    return t;
                }else if(t.left.element.compareTo(x)>0){
                    t.left.left=contains(x,t.left.left);
                    if(t.left.left!=null&&
                            t.left.left.element.compareTo(x)==0){
                        t=rotateWithLeftChild(t);
                        t=rotateWithLeftChild(t);
                    }
                    return t;
                }else if(t.left.element.compareTo(x)<0){
                    t.left.right=contains(x,t.left.right);
                    if(t.left.right!=null&&
                            t.left.right.element.compareTo(x)==0){
                        t=doubleWithLeftChild(t);
                    }
                    return t;
                }
            }
        }
        return t;
    }
    private SprayNode<AnyType> insert(AnyType x, SprayNode<AnyType> t){
        if(t==null){
            return new SprayNode<AnyType>(x,null,null);
        }else if(t.element.compareTo(x)<0){
            t.right=insert(x,t.right);
        }else if(t.element.compareTo(x)>0){
            t.left=insert(x,t.left);
        }
        return t;
    }
}
