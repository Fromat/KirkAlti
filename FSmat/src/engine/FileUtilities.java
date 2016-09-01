package engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class FileUtilities {

	public static void saveAssetImage(Context context, String asset_name){
		File fileDirectory = context.getFilesDir();
		File fileToWrite =  new File(fileDirectory, asset_name);
		
		AssetManager assetManager = context.getAssets();
		
		try {
			InputStream in = assetManager.open(asset_name);
			FileOutputStream out = new FileOutputStream(fileToWrite);
			copyFile(in, out);
			
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void copyFile(InputStream in,FileOutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
}
