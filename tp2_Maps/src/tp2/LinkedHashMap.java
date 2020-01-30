package tp2;

public class LinkedHashMap<KeyType, DataType> {

    private static final double COMPRESSION_FACTOR = 2; // 50%
    private static final int DEFAULT_CAPACITY = 20;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int capacity;
    private int size = 0;

    public LinkedHashMap() {
        capacity = DEFAULT_CAPACITY;
        map = new Node[DEFAULT_CAPACITY];
    }

    public LinkedHashMap(int capacity) {
        this.capacity = capacity;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * @param key Value used to access to a particular instance of a DataType within map
     * @return The index value attached to a particular key
     */
    private int getIndex(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return keyHash < 0 ? -keyHash : keyHash;
    }

    private boolean shouldRehash() {
        return size * COMPRESSION_FACTOR > capacity;
    }

    /** TODO
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {

        capacity = capacity*CAPACITY_INCREASE_FACTOR;
        Node<KeyType, DataType>[] newMap = new Node[capacity];
        Node<KeyType, DataType> currentNode;
        int index;

        for(int i = 0; i < map.length; i++) {
            do {
                if(map[i]!= null){
                    currentNode = map[i];
                    index = getIndex(currentNode.key);

                    //Placer les nodes maintenant
                    if (newMap[index] == null) {
                        newMap[index] = currentNode;
                    }else{
                        //Tant que cest pas la fin, si cest la fin place le truc au next (a la place de null)
                        do{
                            if(newMap[index].next == null)
                            {
                                newMap[index].next = currentNode;
                            }

                        }while (newMap[index].next != null);
                    }
                }
            } while (map[i].next != null);
        }
        //on doit rehash pcq la taille du tableau change et placer a cet nouvel indice dans le new tableau double cap
        map = new Node[capacity];

        for (int i = 0; i< newMap.length; i++)
        {
            map[i] = newMap[i];
            while (newMap[i].next != null){
                map[i].next = newMap[i].next;
            }
        }

    }

    public int size()
    {
        return size;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    /** TODO
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        int index = getIndex(key);

        if(map[index] != null) {
            do {
                if(map[index].key == key){
                    return true;
                }
            }while (map[index].next != null);
        }
        return false;
    }

    /** TODO
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        DataType data = null;
        int index = getIndex(key);

        if(containsKey(key)) {
            do {
                if(map[index].key == key) {
                    data = map[index].data;
                    break;
                }
            }while (map[index].next != null);
        }

        return data;

    }

    /** TODO
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        DataType data = null;
        int index = getIndex(key);

        if(containsKey(key)){
            do {
                if(map[index].key == key) {
                    data = map[index].data;
                    map[index].data = value;
                    break;
                }
            }while (map[index].next != null);
        }else{
            map[index] = new Node<KeyType, DataType>(key, value);
            size ++;
        }

//        if(shouldRehash())
//        {
//            rehash();
//        }

        return data;
    }

    /** TODO
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {

        DataType data = null;
        int index = getIndex(key);

        if(containsKey(key)){
            do {
                //Cas: [present et Lui qu'on veut retirer ]---> Null
                if(map[index].key == key || map[index].next == null) {
                    data = map[index].data;
                    map[index] = null;
                    size--;
                    break;
                }
                //Cas: [present et Lui qu'on veut retirer ]--->[Node]
                if(map[index].key == key || map[index].next != null) {
                    data = map[index].data;
                    map[index] = map[index].next;
                    size--;
                    break;
                }
                //Cas: [present]--->[Lui qu'on veut retirer ]--->[Node]
                if(map[index].next.key == key || map[index].next.next != null) {
                    data = map[index].data;
                    map[index].next = map[index].next.next;
                    size--;
                    break;
                }
                //Cas: [present]--->[Lui qu'on veut retirer ]--->Null
                if(map[index].next.key == key || map[index].next.next == null) {
                    data = map[index].data;
                    map[index].next = null;
                    size--;
                    break;
                }

            }while (map[index].next != null);
        }
        return data;
    }

    /** TODO
     * Removes all nodes contained within the map
     */
    public void clear() {
        for(int i = 0; i < capacity; i++){
            map[i] = null;
        }
        size = 0;
    }


    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}


