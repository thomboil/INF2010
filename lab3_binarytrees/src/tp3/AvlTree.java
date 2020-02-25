package tp3;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

public class AvlTree<ValueType extends Comparable<? super ValueType> > {

    private BinaryNode<ValueType> root;

    public AvlTree() { }

    /**
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * @param value value to add to the tree
     */
    public void insert(ValueType value) {
        if (root == null) {
            root = new BinaryNode<ValueType>(value, null);
        } else {
            insert(value, root);
        }
    }

    /**
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * @param value value to add to the tree
     */
    public void remove(ValueType value){
        remove(value, root);
    }

    /**
     * Verifies if the tree contains value
     * @param value value to verify
     * @return if value already exists in the tree
     */
    public boolean contains(ValueType value) {
        return contains(value, root);
    }

    /**
     * Returns the max level contained in our root tree
     * @return Max level contained in our root tree
     */
    public int getHeight() {
        return getLevelCount(root) - 1;
    }

    /**
     * Returns the minimal value contained in our root tree
     * @return Minimal value contained in our root tree
     */
    public ValueType findMin() {
        BinaryNode<ValueType> minimalNode = findMin(root);
        if (minimalNode == null) return null;
        return minimalNode.value;
    }

    /**
     * Returns all values contained in the root tree in ascending order
     * @return Values contained in the root tree in ascending order
     */
    public List<ValueType> infixOrder() {
        List<ValueType> items = new LinkedList<ValueType>();
        infixOrder(root, items);
        return items;
    }

    /**
     * Returns all values contained in the root tree in level order from top to bottom
     * @return Values contained in the root tree in level order from top to bottom
     */
    public List<ValueType> levelOrder(){
        List<ValueType> items = new LinkedList<ValueType>();

        ArrayDeque<BinaryNode<ValueType>> nodesToCheck = new ArrayDeque<BinaryNode<ValueType>>();

        if (root != null){
            nodesToCheck.push(root);
            levelOrder(nodesToCheck, items);
        }

        return items;
    }

    /** TODO O( log n )
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * Should call balance only if insertion succeeds
     * AVL Trees do not contain duplicates
     *
     * @param value value to add to the tree
     * @param currentNode Node currently being accessed in this recursive method
     * @return if parent node should balance
     */
    private boolean insert (ValueType value, BinaryNode<ValueType> currentNode){
        int compareValue = value.compareTo(currentNode.value);

        if(currentNode.left == null && compareValue < 0){
            currentNode.left = new BinaryNode<ValueType>(value, currentNode);
            return true;
        }else if(currentNode.right == null && compareValue > 0){
            currentNode.right = new BinaryNode<ValueType>(value, currentNode);
            return true;
        }

        if(currentNode.left != null && compareValue < 0){
            insert(value, currentNode.left);
        }else if(currentNode.right != null && compareValue > 0){
            insert(value, currentNode.right);
        }else { return false; }

        balance(currentNode);
        return true;

    }

    /** TODO O ( log n )
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * Should call balance only if removal succeeds
     * @param value value to remove from the tree
     * @param currentNode Node currently being accessed in this recursive method
     * @return if parent node should balance
     */
    private boolean remove(ValueType value, BinaryNode<ValueType> currentNode) {
        int comparaison = value.compareTo(currentNode.value);
        BinaryNode<ValueType> parentCurrent = currentNode.parent;
        if (comparaison < 0 && currentNode.left == null || comparaison > 0 && currentNode.right == null) {
            return false;
        }

        if (comparaison > 0) {
            remove(value,currentNode.right);
        }
        else if(comparaison < 0) {
            remove(value,currentNode.left);
        } else if (currentNode.left != null && currentNode.right != null) {
            currentNode.value = findMin(currentNode.right).value;
            remove(currentNode.value,currentNode.right);
            return true;
        } else {
            if(currentNode.left != null) {
                parentCurrent.left = currentNode.left;
                currentNode.left.parent = parentCurrent;
                currentNode.parent = null;
                return true;
            }
            else if(currentNode.right != null) {
                parentCurrent.right = currentNode.right;
                currentNode.right.parent = parentCurrent;
                currentNode.parent = null;
                return true;
            } else {
                if(currentNode.parent == null) {
                    root = null;
                } else if (currentNode.parent.value.compareTo(currentNode.value) > 0) {
                    parentCurrent.left = null;
                    currentNode.parent = null;
                } else {
                    parentCurrent.right = null;
                    currentNode.parent = null;
                }
            }
        }
        balance(currentNode);
        return true;

    }


    /** TODO O( n )
     * Balances the subTree
     * @param subTree SubTree currently being accessed to verify if it respects the AVL balancing rule
     */
    private void balance(BinaryNode<ValueType> subTree) {
        BinaryNode<ValueType> leftChild = subTree.left;
        BinaryNode<ValueType> rightChild = subTree.right;

        if(subTree == null){
            return;
        }
        if ((getLevelCount(subTree.right)- getLevelCount(subTree.left)) > 1){
            if(getLevelCount(rightChild.right) >= getLevelCount(rightChild.left)){
                rotateRight(subTree);
            }else {
                doubleRotateOnRightChild(subTree);
            }
        }
        if((getLevelCount(subTree.left) - getLevelCount(subTree.right)) > 1){
            if(getLevelCount(leftChild.left) >= getLevelCount(leftChild.right)){
                rotateLeft(subTree);
            }else {
                doubleRotateOnLeftChild(subTree);
            }
        }

    }

    /** TODO O( 1 )
     * Single rotation to the left child, AVR Algorithm
     * @param node1 Node to become child of its left child
     */
    private void rotateLeft(BinaryNode<ValueType> node1){

        BinaryNode<ValueType> node2 = node1.left;
        BinaryNode<ValueType> node3 = node2.right;
        BinaryNode<ValueType> parent1 = node1.parent;

        if  (node1.parent != null) {
            if (parent1.value.compareTo(node2.value) < 0 ) {
                parent1.right = node2;
            }
            else if (parent1.value.compareTo(node2.value) > 0 ) {
                parent1.left = node2;
            }
        }
        if(node1.value.compareTo(root.value) == 0) {
            root = node2;
        }
        if (node3 == null) {
            node2.parent = parent1;
            node2.right = node1;
            node1.parent = node2;
            node1.left = null;
        }
        else {
            node2.parent = parent1;
            node2.right = node1;
            node1.parent = node2;
            node1.left = node3;
            node3.parent = node1;
        }
    }

    /** TODO O( 1 )
     * Single rotation to the right, AVR Algorithm
     * @param node1 Node to become child of its right child
     */
    private void rotateRight(BinaryNode<ValueType> node1){
        BinaryNode<ValueType> node2 = node1.right;
        BinaryNode<ValueType> node3 = node2.left;
        BinaryNode<ValueType> parent1 = node1.parent;

        if  (parent1 != null) {
            if (parent1.value.compareTo(node2.value) < 0 ) {
                parent1.right = node2;
            }
            else if (parent1.value.compareTo(node2.value) > 0 ) {
                parent1.left = node2;
            }
        }
        if(node1.value.compareTo(root.value) == 0) {
            root = node2;
        }
        if (node3 == null) {
            node2.parent = parent1;
            node2.left = node1;
            node1.parent = node2;
            node1.right = null;

        } else {
            node2.parent = parent1;
            node2.left = node1;
            node1.parent = node2;
            node1.right = node3;
            node3.parent = node1;
        }
    }

    /** TODO O( 1 )
     * Double rotation on left child, AVR Algorithm
     * @param node1 Node to become child of the right child of its left child
     */
    private void doubleRotateOnLeftChild(BinaryNode<ValueType> node1){
        rotateRight(node1.left);
        rotateLeft(node1);
    }

    /** TODO O( 1 )
     * Double rotation on right child, AVR Algorithm
     * @param node1 Node to become child of the left child of its right child
     */
    private void doubleRotateOnRightChild(BinaryNode<ValueType> node1){
        rotateLeft(node1.right);
        rotateRight(node1);
    }

    /** TODO O( log n )
     * Verifies if the root tree contains value
     * @param value value to verify
     * @param currentNode Node currently being accessed in this recursive method
     * @return if value already exists in the root tree
     */
    private boolean contains(ValueType value, BinaryNode<ValueType> currentNode){
        if(currentNode == null){
            return false;
        }
        boolean found = false;
        int compareValue = value.compareTo(currentNode.value);

        if (currentNode.left != null && compareValue < 0){
            found = contains(value, currentNode.left);
        }else if(currentNode.right != null && compareValue > 0){
            found = contains(value, currentNode.right);
        }else if(compareValue == 0){
            return  true;
        }
        return found;
    }

    /** TODO O( n )
     * Returns the number of level contained in subTree including subTree node level
     * @return Number of level contained in subTree including subTree node level
     */
    private int getLevelCount(BinaryNode<ValueType> subTree){
        if(subTree != null){
            return Math.max(getLevelCount(subTree.right), getLevelCount(subTree.left))+1;
        }
        return 0;
    }

    /** TODO O( log n )
     * Returns the node which has the minimal value contained in our root tree
     * @return Node which has the minimal value contained in our root tree
     */
    private BinaryNode<ValueType> findMin(BinaryNode<ValueType> currentNode) {
        if(currentNode == null){
            return null;
        } else {
            while (currentNode.left != null){
                return findMin(currentNode.left);
            }
        }
        return currentNode;
    }

    /** TODO O( n )
     * Builds items which should contain all values within the root tree in ascending order
     * @param currentNode Node currently being accessed in this recursive method
     * @param items List being modified to contain all values in the root tree in ascending order
     */
    private void infixOrder(BinaryNode<ValueType> currentNode, List<ValueType> items){
        if(currentNode.left != null) {
            infixOrder(currentNode.left, items);
        }
        items.add(currentNode.value);
        if(currentNode.right != null) {
            infixOrder(currentNode.right, items);
        }
    }

    /** TODO O( n )
     * Builds items which should contain all values within the root tree in level order from top to bottom
     * @param nodesToCheck Queue for non-recursive algorithm
     * @param items List being modified to contain all values in the root tree in level order from top to bottom
     */
    private void levelOrder(ArrayDeque<BinaryNode<ValueType>> nodesToCheck, List<ValueType> items) {
        while(!nodesToCheck.isEmpty()) {
            BinaryNode<ValueType> currentNode = nodesToCheck.pop();
            if(currentNode.left != null){
                nodesToCheck.add(currentNode.left);
            }
            if(currentNode.right != null){
                nodesToCheck.add(currentNode.right);
            }
            items.add(currentNode.value);
        }
    }

    static class BinaryNode<ValueType> {
        ValueType value;

        BinaryNode<ValueType> parent; // Pointer to the node containing this node

        BinaryNode<ValueType> left = null; // Pointer to the node on the left which should contain a value below this.value
        BinaryNode<ValueType> right = null; // Pointer to the node on the right which should contain a value above this.value

        BinaryNode(ValueType value, BinaryNode<ValueType> parent)
        {
            this.value = value;
            this.parent = parent;
        }
    }
}