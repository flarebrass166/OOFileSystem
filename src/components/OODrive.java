package components;
import java.util.ArrayList;
import java.util.List;
public class OODrive {
    private String path;
    private String type;
    private String name;
    private List<OOFolder> children;
    private double size;

    public OODrive( String type, String name, String path) {
        super();
        this.type = type;
        this.name = name;
        this.path = name + path ;
        this.size = 0;
        children = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OOFolder findParent(OODrive d, String p){
        OOFolder parent = null;
        if (d.getNumberOfChildren() == 0){
            return null;
        }
        for (OOFolder f: d.getChildren()) {
            if (f.getPath().equals(p)) {
                return f;
            }
            parent = f.findParent(f, p);
            if (parent != null){
                if (parent.getPath().equals(p)){
                    break;
                }
            }
        }
        if (parent == null) {
            throw new IllegalArgumentException("Path not found");
        }else
            return parent;
    }

//    public int setSize(OOFolder node) {
//        if(node == null)
//            return 0;
//        this.size += node.getNumberOfChildren();
//        node.getChildren().forEach(c -> setSize(c));
//        return size;
//    }

//    public int getSize(OOFolder node){
//        setSize(node);
//        int temp = size;
//        size = 0;
//        return temp;
//    }

    public List<OOFolder> getChildren() {
        return children;
    }

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public boolean hasChildren() {
        return (getNumberOfChildren() > 0);
    }

    public void setChildren(List<OOFolder> children) {
        for(OOFolder child : children) {
            child.setParentDrive(this);
        }

        this.children = children;
    }

    public void addChild(OOFolder child) {
        if (child == null){
            throw new IllegalArgumentException("you must specify file being added");
        }
            if (!(this.getNumberOfChildren() == 0)) {
                List<OOFolder> siblings = this.getChildren();
                for (OOFolder e : siblings) {
                    if (e.getName().equals(child.getName())) {
                        throw new IllegalArgumentException("path already exists, please try again with a different name or path");
                    }
                }
            }

        child.setParentDrive(this);
        this.size++;
        children.add(child);
    }

    public void addChildAt(int index, OOFolder child) throws IndexOutOfBoundsException {
        child.setParentDrive(this);
        children.add(index, child);
    }

    public void removeChildren() {
        this.children = new ArrayList<OOFolder>();
    }

    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }

    public OOFolder getChildAt(int index) throws IndexOutOfBoundsException {
        return children.get(index);
    }
    @Override
    public String toString(){
        return "Name: " + name + " Path  " + path ;
    }
//    public T getData() {
//        return this.data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

//    public String toString() {
//        return getData().toString();
//    }
}