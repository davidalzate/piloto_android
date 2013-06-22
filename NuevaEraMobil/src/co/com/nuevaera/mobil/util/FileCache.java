package co.com.nuevaera.mobil.util;

import java.io.File;
import android.content.Context;
import android.util.Log;
  
public class FileCache {
  
    private File cacheDir;
  
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TempImages");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
  


	public File getFile(String url){
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
  
    }
  
    public void clear(){
        File[] files=cacheDir.listFiles();
        Log.v("FileCache", "Files " + files.length);
        if(files==null)
            return;
        for(File f:files){
        	Log.v("FileCache", "Deleting... " + f.getAbsolutePath() + "- " +f.getName());
        	f.delete();
        }
            
    }
  
}