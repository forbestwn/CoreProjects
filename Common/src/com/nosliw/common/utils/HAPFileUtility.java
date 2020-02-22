package com.nosliw.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class HAPFileUtility {
	
	public static String getApplicationLibFolder(){ return HAPSystemUtility.getApplicationResourceLibFolder(); }
	public static String getTagDefinitionFolder(){  return getApplicationLibFolder() + "uitag/";   }
	public static String getActivityPluginFolder(){  return getApplicationLibFolder() + "activity/";   }
	public static String getUIModuleDecorationFolder(){  return getApplicationLibFolder() + "uimoduledecoration/";  }
	public static String getUIAppConfigureFolder(){  return getApplicationLibFolder() + "uiappconfigure/";  }

	public static String getJSLibraryFolder() {   return HAPFileUtility.getJSFolder() + "libresources/";   }
	public static String getApplicationDataFolder(){  return HAPSystemUtility.getApplicationResourceDataFolder();  }
	public static String getProcessFolder(){  return getApplicationDataFolder() + "process/";   }
	public static String getScriptExportFolder(){  return HAPFileUtility.getTempFolder()+"scriptexport/scripts/";  }
	public static String getTaskLogFolder(){  return HAPFileUtility.getTempFolder()+"tasklog/";  }
	public static String getJSFolder(){  return HAPSystemUtility.getJSFolder();  }
	public static String getNosliwJSFolder(String lib){  return getJSFolder()+"libresources/nosliw/"+lib+"/";  }
	public static String getUIPageFolder(){  return getApplicationDataFolder() + "page/";  }
	public static String getUIModuleFolder(){  return getApplicationDataFolder() + "uimodule/";  }
	public static String getMiniAppFolder(){  return getApplicationDataFolder() + "miniapp/";  }
	public static String getCronJobFolder(){  return getApplicationDataFolder() + "cronjob/";  }
	public static String getTemplateFolder(){  return getApplicationDataFolder() + "template/";  }

	public static String getTempFolder(){		return HAPSystemUtility.getTempFolder();	}
	public static String getResourceTempFileFolder(){  return HAPSystemUtility.getJSTempFolder() + "resources/";  }
	public static String getJSLibrayrTempFolder() {    return  HAPSystemUtility.getJSTempFolder() + "libs/";  }

	public static List<File> sortFiles(Set<File> files){
		List<File> sortedList = new ArrayList<File>(files);
		Collections.sort(sortedList, new Comparator<File>() {

			@Override
			public int compare(File arg0, File arg1) {
				return arg0.getPath().compareTo(arg1.getPath());
			}
		});
		return sortedList;
	}
	
    public static Set<File> getAllFiles(String path) {
    	Set<File> out = new HashSet<File>();
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return out;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
            	out.addAll(HAPFileUtility.getAllFiles(fs.getAbsolutePath()));
            } else {
            	out.add(fs);
            }
        }
        return out;
    }
	
    public static String readFile(File file){
    	return HAPFileUtility.readFile(file.getAbsolutePath());
    }
    
	public static String readFile(String filePath){
		return readFile(filePath, "\n");
	}
	
	public static String readFile(String filePath, String nextLine){
		try {
			return FileUtils.readFileToString(new File(filePath) , Charset.forName("UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

/*		
		StringBuffer out = new StringBuffer();
		try{
			FileReader inputFile = new FileReader(filePath);
		    BufferedReader bufferReader = new BufferedReader(inputFile);
            String line;
            while ((line=bufferReader.readLine())!=null){
            	out.append(line+nextLine);
            }
            bufferReader.close(); 			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out.toString();
*/		
	}

	public static String readFile(InputStream stream){
		return readFile(stream, "\n");
	}
	
	public static String writeFile(String fileName, String content){
		try {
			fileName = getValidFileName(fileName);
			File file = new File(fileName);
			//create dir if not exist
			file.getParentFile().mkdirs();
			
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"));
/*			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				try{
					file.createNewFile();
				}
				catch(Exception e){
					e.printStackTrace();
					HAPErrorUtility.invalid("");
				}
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 */
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	private static String getValidFileName(String fileFullName){
		int index = fileFullName.lastIndexOf("/");
		if(index==-1)  index = fileFullName.lastIndexOf("\\");
		
		String path = null;
		String fileName = null;
		if(index==-1)  fileName = fileFullName;
		else{
			fileName = fileFullName.substring(index+1);
			path = fileFullName.substring(0, index+1);
		}
		
		
//		char[] invalidChars = {'|', '[', ']', ';', ':'};
		char[] invalidChars = {'|', ':'};
		for(char invalidChar : invalidChars){
			fileName = fileName.replace(invalidChar, '_');
		}
		String out = "";
		if(path!=null)  out = out + path;
		out = out + fileName;
		return out;
	}
	
	public static String getFileName(File file){
		String name = file.getName();
		int i = name.indexOf(".");
		if(i!=-1){
			name = name.substring(0, i);
		}
		return name;
	}
	
	
	public static String readFile(InputStream stream, String nextLine){
		StringBuffer out = new StringBuffer();
		try{
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(stream));			
            String line;
            while ((line=bufferReader.readLine())!=null){
            	out.append(line+nextLine);
            }
            bufferReader.close(); 			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return out.toString();
	}
	
	public static InputStream getInputStreamOnClassPath(Class c, String fileName){
		InputStream stream = c.getResourceAsStream(fileName);
		return stream;
	}

	public static String getFileNameOnClassPath(Class c, String fileName){
		return c.getResource(fileName).getFile();
	}
	
	public static InputStream getInputStreamFromFile(String location, String fileName){
		InputStream stream = null;
		try {
			stream = new FileInputStream(buildFullFileName(location, fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stream;
	}
	
	public static Path getClassFolderPath(Class cs){
		Path out = null;
		try{
			URI uri = cs.getResource("").toURI();
	        System.out.println("Starting from: " + uri);
		    try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
		    	out = Paths.get(uri);
		    }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	public static String getClassFolderName(Class cs){
		/*
		try{
			{
		     URI uri = cs.getResource("").toURI();
		        System.out.println("Starting from: " + uri);
		        try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
		            Path myPath = Paths.get(uri);
		            Files.walkFileTree(myPath, new SimpleFileVisitor<Path>() { 
		                @Override
		                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		                	if(file.toString().endsWith("xml")){
			                    System.out.println(file);
			                    BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
			                    System.out.println(reader.readLine());
			                    System.out.println(reader.readLine());
			                    System.out.println(reader.readLine());
			                    System.out.println(reader.readLine());
		                	}
		                    return FileVisitResult.CONTINUE;
		                }
		            });
		        }
			}
			
			
		        {
	       URI uri = cs.getResource("").toURI();
	        Path myPath;
	        if (uri.getScheme().equals("jar")) {
	            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
	            myPath = fileSystem.getPath("");
	        } else {
	            myPath = Paths.get(uri);
	        }
	        Stream<Path> walk = Files.walk(myPath, 1);
	        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
	            System.out.println(it.next());
	        }
		        }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		String fileFolder = cs.getResource("").getFile();
		return fileFolder;
	}
	

	
	public static String buildFullFileName(String location, String fileName, String type){
		return location+"/"+fileName+"."+type; 
	}

	public static String buildFullFileName(String location, String fileName){
		return location+"/"+fileName; 
	}
}
