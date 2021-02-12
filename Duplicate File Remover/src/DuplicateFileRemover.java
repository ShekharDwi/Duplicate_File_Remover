import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class DuplicateFileRemover {


    static String[] HashSet = new String [80000];

    static int index=0, dp=0, mfls=0,fcout=0;




    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter path to be scanned");
        String dir_path = s.nextLine();

        System.out.println("Enter path to used as bin");
        String bin_path = s.nextLine();

        // TODO Auto-generated method stub
        Arrays.fill(HashSet, "0");
        try (Stream<Path> paths = Files.walk(Paths.get(dir_path))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(x -> {
                        try {
                            fcout+=1;
                            Hash(x,bin_path);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Total Files: " + fcout);
        System.out.println("Duplicate Files: " + dp);
        System.out.println("Files Moves Succesfully: " + mfls);
        int code=-1;
        if(dp==mfls && (dp!=0 && mfls!=0)) {code=0;}


        System.out.println("EXIT CODE: "  + code );

    }

    static void Hash (Path path,String bin_path) throws IOException {

        byte[] b = null;
        try {
            b = Files.readAllBytes(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(b);
            String actual = DatatypeConverter.printHexBinary(hash);
            //System.out.println(actual + "\t");
            //if (isCopy(actual))
            if(HashDuplicate(actual))
            {
                //System.out.println("DUPLICATE Found");
                dp++;
                moveFile(path.toFile(), bin_path);
            }
            else
            {
                //writer(actual);

                HashSet[index]=actual;
                index++;
            }



        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }

    private static void moveFile(File path,String bin_path) {
        // TODO Auto-generated method stub

        double a = Math.random()*87654;
        double b = Math.random()*39856;
        try {
            path.renameTo(new File(  bin_path + ((int)a*b) + ".jpg"));
            mfls++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private static boolean HashDuplicate(String actual) {
        // TODO Auto-generated method stub
        for(int i=0; i<8000;i++)
        {
            //System.out.println("HASHVAL: "+ HashSet[i] + " CompareTO: "+ actual);
            if (HashSet[i]=="0"  )
            {
                return false;
            }
            else if(HashSet[i].equals(actual))
            {
                return true;
            }

        }



        return false;
    }

    static void writer(String str) {



        try {
            FileWriter myWriter = new FileWriter("A:\\checksum.txt", true);
            myWriter.write(str+"\n");
            myWriter.close();
            // System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }





    }

    static boolean isCopy(String str) throws IOException {
        String file = "A:\\photoshop\\checksum.txt";
        //final  Scanner scanner = new Scanner(file);
        BufferedReader br = new BufferedReader(new FileReader(file));
        //System.out.println("matching:  " + str);
        String line;
        while ((line = br.readLine()) != null) {


            if (line.equals(str) || line.equals(str+"\n")) {
                //   System.out.println("I found " +str+ " in file ");

                br.close();
                return true;
            }

            br.close();
            return false;
        }



        br.close();
        return false;



    }



}
