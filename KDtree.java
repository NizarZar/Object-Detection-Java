
public class KDtree {

    public class KDNode {

        public Point3D point;
        public int axis;
        public double value;
        public KDNode left;
        public KDNode right;



        //KDnode takes the axis, point.
        public KDNode(Point3D point, int axis){
            this.point = point;
            this.axis = axis;
            this.value = point.get(axis);
            left=right=null;
        }
    }

    private KDNode root;
    public KDtree(){
        root = null;
    }
    // we make a tree from the point and axis
    public KDtree(Point3D p, int axis){
        root = new KDNode(p,axis);
    }


    // insert method of KDtree
    public KDNode insert(Point3D p, KDNode node, int axis){
        if(node == null){
            node = new KDNode(p,axis);
        } else if (p.get(axis) <= node.value){
            node.left = insert(p,node.left,(axis+1)%3);
        } else {
            node.right = insert(p,node.right,(axis+1)%3);
        }
        return node;
    }

    public KDNode getRoot() {
        return root;
    }
}
