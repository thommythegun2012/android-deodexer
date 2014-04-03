/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package androiddeodexer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Luis Peregrina
 */
public class SysUtils {
    public String adb;
    protected String sep = File.separator;
    protected ArrayList<String> fileList = new ArrayList<>();
    public String CWD;
    
    public SysUtils(){
        try{
            setCWD();
        }catch(Exception e){
            System.err.println("Unable to set CWD");
            e.printStackTrace();
        }
        
    }
    
    //Linux doesnt open file on CWD http://stackoverflow.com/a/1127606
    public void setCWD() throws Exception{
        CWD = new File(AndroidDeodexer.class.getProtectionDomain()
                .getCodeSource().getLocation().toURI()).getParentFile()
                .getCanonicalPath() + sep;
    }
    
    public int OSCommands(){
        int ret = -1;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            //Set Windows commands
            adb = "win.exe";
            ret = 0;
            
        } else if (os.contains("linux") || os.contains("unix")) {
            //Set *NIX commands
            adb = "nix";
            ret = 1;
        } else {
            // Set Mac OS commands
            adb = "mac";
            ret = 2;
        }
        return ret;
    }
    
    //Print and return
    protected String echo(String e){
        System.out.println(e);
        return e;
    }
    
    //execute("C:\Users\", "rm", "-r");
    protected boolean execute(String working_dir, List<String> args) throws Exception{
        echo("execute(" + CWD+working_dir + ", "+ args + ")");
        boolean ret = true;
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.directory(new File(CWD+working_dir));
        pb.inheritIO();
        final Process shell = pb.start();
        int shellExitStatus = shell.waitFor();
        if(shellExitStatus!=0){
            ret = false;
        }
        return ret;
    }
    
    
    //fileCopy("original.txt","folder/new.txt");
    protected void fileCopy(String source, String dest) throws IOException {
        File fin = new File(new File(CWD+source).getCanonicalPath());
        File fout = new File(new File(CWD+dest).getCanonicalPath());
        echo("fileCopy( " + CWD+source + ", "+ CWD+dest + " )");
        Files.copy(fin.toPath(), fout.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);

    }
    
    //fileDelete("old.txt");
    protected boolean fileDelete(String fi) throws IOException {
        echo("fileDelete(" + fi + ")");
        File f = new File(new File(fi).getCanonicalPath());
        if (f.isDirectory()) {
            for (File c : f.listFiles()){
                fileDelete(c.getCanonicalPath().toString());
            }
        }
        
        if (f.exists() & !f.delete()){
            throw new IOException("Failed to fileDelete file: " + f);
        }
        return true;
    }
    
    //directoryMake("out");
    public boolean directoryMake(String dir) throws IOException {
        echo("directoryMake(" + CWD+dir+sep + ")");
        File f = new File(CWD+dir+sep);
        if(!f.mkdirs() && !f.isDirectory()){
            throw new IOException("Unable to make directory.");
        }
        return true;
    }
    
    public void directoryOpen(String dir) throws Exception{
        echo("Opening " + dir);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(CWD+dir));
        } catch (Exception e) {
            System.out.println("Directory not found");
        }
    }
    
    //rename("tmp/","perm/");
    protected void rename(String orig_name, String new_name) throws IOException {
        // File (or directory) with old name
        File file = new File(new File(CWD+orig_name).getCanonicalPath());
        // File (or directory) with new name
        File file2 = new File(new File(CWD+new_name).getCanonicalPath());
        
        echo("rename(" + orig_name + ", " + new_name + ")");
        
        if(file2.exists()){
            throw new IOException("File/directory exists.");
        }
        // Rename file (or directory)
        boolean success = file.renameTo(file2);
        if (!success) {
            // File was not successfully renamed
            throw new IOException("Unable to rename.");
        }
    }
    
    //fileZip("final.zip", "build/", 9);
    //http://www.mkyong.com/java/how-to-compress-files-in-zip-format/
    //////////////////////////////////////

    public void directoryZip( String OUTPUT_ZIP_FILE, String SOURCE_FOLDER, int comp ) throws Exception
    {
        fileList = new ArrayList<>();
        OUTPUT_ZIP_FILE = CWD+OUTPUT_ZIP_FILE;
        SOURCE_FOLDER = CWD+SOURCE_FOLDER;
    	System.out.println("directoryZip(" + OUTPUT_ZIP_FILE + ", " + SOURCE_FOLDER + ", " + comp);
        SOURCE_FOLDER = (new File(SOURCE_FOLDER)).getCanonicalPath().toString();
        OUTPUT_ZIP_FILE = (new File(OUTPUT_ZIP_FILE)).getCanonicalPath().toString();
    	generateFileList(new File(SOURCE_FOLDER), SOURCE_FOLDER);
    	zipIt(OUTPUT_ZIP_FILE, SOURCE_FOLDER, comp);
    }
 
    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile, String SOURCE_FOLDER, int comp) throws Exception{
 
     byte[] buffer = new byte[1024];

        
        File dir = new File(new File(zipFile).getParent());
        if( !dir.exists() ){
            dir.mkdirs();
        }
        
    	ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zos.setLevel(comp);
        
 
    	for(String file : this.fileList){
 
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
 
        	FileInputStream in = 
                       new FileInputStream(SOURCE_FOLDER + File.separator + file);
 
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
 
        	in.close();
    	}
 
    	zos.closeEntry();
    	//remember close it
    	zos.close();
 
    	System.out.println("Done");

   }
 
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public void generateFileList(File node, String SOURCE_FOLDER){
 
    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString(), SOURCE_FOLDER));
	}
 
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename), SOURCE_FOLDER);
		}
	}
 
    }
 
    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file, String SOURCE_FOLDER){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
    
    /////////////////////////////////////////////////////////////////////////////////
  
    //fileUnzip("app.zip", "out/");
    //http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
    public boolean fileUnzip(String zipFile, String outputFolder) throws IOException{
        String zI = new File(CWD+zipFile).getCanonicalPath().toString();
        String zO = new File(CWD+outputFolder+sep).getCanonicalPath().toString();
        
        echo("fileUnzip( " + zI + ", " + zO + " )");
        byte[] buffer = new byte[1024];
        try{

            //create output directory is not exists
            File folder = new File(zO);
            if(!folder.exists()){
                folder.mkdir();
            }
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zI));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while(ze!=null){
                String fileName = ze.getName();
                File newFile = new File(zO + sep + fileName);
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile); 
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();   
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            return true;
            }catch(IOException e){
                throw new IOException(e); 
            }
   }   
            
    //http://stackoverflow.com/a/13379744
    public void fileCopyFromJar(String fileInside, String fileOutside){
        echo("copyFromJar "+fileInside + " to "+CWD+fileOutside);
        InputStream stream = SysUtils.class.getResourceAsStream(fileInside);
        OutputStream resStreamOut;
        int readBytes;
        byte[] buffer = new byte[4096];
        try {
            resStreamOut = new FileOutputStream(new File(CWD+fileOutside).getCanonicalPath());
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
            stream.close();
            resStreamOut.close();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
    
    
}
