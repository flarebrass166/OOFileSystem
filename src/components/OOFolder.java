package components;
import java.util.ArrayList;
import java.util.List;
public class OOFolder {
    private String type;
    private String name;
    private String path;
    private String content;
    private List<OOFolder> children;
    private OOFolder parentFolder;
    private OODrive parentDrive;
    private double size;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public OOFolder(String type, String name, String path) {
        super();
        this.type = type;
        this.path = path + "\\" + name ;
        this.name = name;
        if(type.equalsIgnoreCase("textfile")){
             this.content = "";
        }else {
            children = new ArrayList<>();
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public OOFolder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(OOFolder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public OODrive getParentDrive() {
        return parentDrive;
    }

    public void setParentDrive(OODrive parent) {
        parentDrive = parent;
    }

    public void removeNode(){
        double adjustment;
        if (this.getType().equalsIgnoreCase("textfile")){
            if (this.getContent().length() == 0){
                adjustment = 1;
            }else{
                adjustment = this.getContent().length();
            }
        }else  if (!this.hasChildren() && this.getParentFolder().getType().equalsIgnoreCase("zipfile")){
            adjustment = 0.5;
        }else if (!this.hasChildren()){
            adjustment = 1;
        }else{
            adjustment = this.getSize();
        }
        if (this.getParentFolder() != null){
            sizeUpdate(this, adjustment);
            this.getParentFolder().getChildren().remove(this);
        }
        if (this.getParentDrive() != null) {
            sizeUpdate(this, adjustment);
            this.getParentDrive().getChildren().remove(this);
        }
    }
    public OOFolder findParent(OOFolder d, String p){
        OOFolder parent = null;
        if (d.getType().equalsIgnoreCase("textfile")){
            return null;
        }
        if (d.getNumberOfChildren() == 0){
            return null;
        }
        for (OOFolder f: d.getChildren()){

            if (!(f.getPath().equals(p))){
                parent = f.findParent(f, p);
            }else {
                parent = f;
                return parent;
            }
        }
        return parent;
    }

//    public int setSize(OOFolder node) {
//            if(node == null)
//                return 0;
//            this.size += node.getNumberOfChildren();
//            node.getChildren().forEach(c -> setSize(c));
//        return size;
//    }
//
//    public int getSize(OOFolder node){
//        setSize(node);
//        int temp = size;
//        size = 0;
//        return temp;
//    }

//    public OOFolder(T data) {
//        this();
//        setData(data);
//    }

    public OOFolder getParent() {
        return parentFolder;
    }

    public List<OOFolder> getChildren() {
        return children;
    }

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public void sizeCounterZip(OOFolder node){
            node.setSize(node.getSize()+ .5);
            if (node.getParentFolder() != null){
                sizeCounterZip(node.getParentFolder());
            }
            if(node.getParentDrive() != null){
                node.getParentDrive().setSize(node.getParentDrive().getSize() +.5);

            }
    }
    public void sizeCounter(OOFolder node){
        node.setSize(node.getSize()+1);
        if (node.getParentFolder() != null){
            sizeCounter(node.getParentFolder());
        }
        if(node.getParentDrive() != null){
            node.getParentDrive().setSize(node.getParentDrive().getSize()+1);
        }
    }
    public void sizeUpdate(OOFolder node, double adjust){
        if (node.getParentFolder() != null){
            node.getParentFolder().setSize(node.getParentFolder().getSize() - adjust);
            sizeUpdate(node.getParentFolder(), adjust);
        }
        if(node.getParentDrive() != null){
            node.getParentDrive().setSize(node.getParentDrive().getSize()- adjust);
        }
    }
    public boolean hasChildren() {
        return (getNumberOfChildren() > 0);
    }

    public void setChildren(List<OOFolder> children) {
        for(OOFolder child : children) {
            child.parentFolder = this;
        }

        this.children = children;
    }

    public void addChild(OOFolder child) {
        if (this.type.equalsIgnoreCase("textfile")){
            throw new IllegalArgumentException("Text files cannot contain any other entities");
        }
            if (!(this.getNumberOfChildren() == 0)){
                List<OOFolder> siblings = this.getChildren();
                for (OOFolder e : siblings){
                    if (e.getName().equals(child.name)){
                        throw new IllegalArgumentException("path already exists, please try again with a different name or path");
                    }
                }
        }
        child.setParentFolder(this);
        if (this.type.equalsIgnoreCase("zipfile")){
            sizeCounterZip(this);
        }else{
            sizeCounter(this);
        }
        children.add(child);
    }

    public void addChildAt(int index, OOFolder child) throws IndexOutOfBoundsException {
        child.parentFolder = this;
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
        return name + " " + path ;
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

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        OOFolder<?> other = (OOFolder<?>) obj;
//        if (data == null) {
//            if (other.data != null) {
//                return false;
//            }
//        } else if (!data.equals(other.data)) {
//            return false;
//        }
//        return true;
//    }
//
//    /* (non-Javadoc)
//    * @see java.lang.Object#hashCode()
//    */
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((data == null) ? 0 : data.hashCode());
//        return result;
//    }
//
//    public String toStringVerbose() {
//        String stringRepresentation = getData().toString() + ":[";
//
//        for (OOFolder<T> node : getChildren()) {
//            stringRepresentation += node.getData().toString() + ", ";
//        }
//
//        //Pattern.DOTALL causes ^ and $ to match. Otherwise it won't. It's retarded.
//        Pattern pattern = Pattern.compile(", $", Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(stringRepresentation);
//
//        stringRepresentation = matcher.replaceFirst("");
//        stringRepresentation += "]";
//
//        return stringRepresentation;
//    }
}