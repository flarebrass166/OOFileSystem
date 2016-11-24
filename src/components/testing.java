package components;


import java.util.ArrayList;


public class testing {
    public static ArrayList<OODrive> OOFileSystem = new ArrayList<>();
    public static void main(String[] args){
        //create a drive entity
        create("Drive", "C", "");
        create("Drive", "D", "");
        //uncomment this to test the exception thrown when trying to create a drive that has the same name and path as another drive
        //create("Drive", "C", "");

        //adding some files to the system.
        create("zipfile", "zip", "C");
        create("textfile","text", "C" );
        create("folder", "folder", "C");

        // uncomment this to try and add a file to a text file
        //create("folder", "folder", "C\\text");

        //adding some more files deeper in the system.
        create("folder", "folder", "C\\folder");
        create("folder", "folder", "C\\folder\\folder");
        create("zipfile", "zip", "C\\folder\\folder\\folder");
        create("textfile", "text", "C\\folder\\folder\\folder\\zip");
        create("folder", "folder2", "C\\folder\\folder\\folder\\zip");
        create("zipfile", "zip23", "C\\folder\\folder\\folder\\zip");
        create("folder", "pictures", "C\\folder\\folder\\folder\\zip");

        // uncomment these to try adding duplicates to the same path.
        //create("folder", "folder","C\\folder");
        //create("folder", "folder", "C\\folder\\folder");
        //create("textfile","text", "C" );

        //uncomment this to try adding a drive entity to another drive or other entity.
        //create("Drive", "D", "C");
        //create("Drive", "D", "C\\folder");

        // uncomment this to try adding files to paths that don't exist
        //create("folder", "folder", "yoda");
        //create("folder", "folder", "luke");
        //create("zipfile", "zip", "C\\folder\\folder\\folder\\zip345");

        //adding many files to the system and printing the size after.
//        for (int i = 0; i < 100; i++){
//            create("folder", "folder"+ i, "C\\zip");
//        }
//        System.out.printf("Size of drive C after adding 100 folders to a zip file plus previous additions: %.1f%n%n", OOFileSystem.get(0).getSize());
//        for (int i = 0; i < 100; i++) {
//             create("textfile","text"+i, "C");
//        }
//        System.out.printf("Size of drive C after adding 100 text files to the main C drive plus previous additions: %.1f%n%n", OOFileSystem.get(0).getSize());
//        for (int i = 0; i < 100; i++) {
//             create("folder","folder"+i, "C\\folder\\folder\\folder\\zip");
//        }
//        System.out.printf("Size of drive C after adding 100 zip files to a zip file  plus previous additions: %.1f%n%n", OOFileSystem.get(0).getSize());

        // deleting some files and printing the size out
        delete("C\\text");
        System.out.printf("Size of drive C after deleting a normal text file: %.1f%n%n", OOFileSystem.get(0).getSize());
        delete("C\\folder\\folder\\folder\\zip\\zip23");
        delete("C\\folder\\folder\\folder\\zip\\pictures");
        System.out.printf("Size of drive C after deleting some files from a zip: %.1f%n%n", OOFileSystem.get(0).getSize());

        //uncomment this to try and delete files from unknown paths
        //delete("C\\folder\\11");
        //delete("C\\folder\\folder\\folder\\zip\\pictures122");


        //uncomment this to try and move a drive
        move("C", "D\\folder");



    }
    // create method that is used to create and add files to the system
    public static void create(String type, String name, String path){
        //if argument type entered is a drive, then create a new drive and add it to the system.
        if(type.equalsIgnoreCase("drive")){
            //create the new drive.
            OODrive drive = new OODrive(type, name, path);
            //check the file system to see if the drive already exists.
            for (OODrive d: OOFileSystem) {
                if (d.findParent(d, path).getPath().equals(path) || d.getName().equals(path)){
                    throw new IllegalArgumentException("A drive entity cannot be added to any other entity in the file system");
                }
                if (d.getName().equals(name)) {
                    throw new IllegalArgumentException("Drive already exists in system. please try again with a different name");
                }
            }
            //if it doesn't already exist add the new drive to the file system.
            OOFileSystem.add(drive);
            //if arguments passed are one of the other 3 entities, then create the entity and add it to the system.
        }else if (type.equalsIgnoreCase("folder") || type.equalsIgnoreCase("zipfile") || type.equalsIgnoreCase("textfile")) {
            OOFolder file = new OOFolder(type, name, path);
                for (OODrive f: OOFileSystem){
                    if (f.getName().equals(path)){
                        f.addChild(file);
                        break;
                    }else{
                        f.findParent(f, path).addChild(file);
                        break;
                    }
                }
        }

    }

    public static void delete(String p){
        for (OODrive d: OOFileSystem){
            if (d.getName().equals(p)){
                OOFileSystem.remove(d);
            }else{
                d.findParent(d, p).removeNode();
                break;
            }

        }
    }

    public static void move(String source, String destination){
        for (OODrive d: OOFileSystem){
            if(d.getPath().equals(source)){
                throw new IllegalArgumentException("Drive entities cannot be moved, because they cannot be contained by any entity");
            }
//            }else{
//                d.findParent(d, p).removeNode();
//                break;
//            }

        }
    }
}
