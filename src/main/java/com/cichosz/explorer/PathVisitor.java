package com.cichosz.explorer;

import java.io.IOException;
import java.awt.Desktop;
import java.io.File;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitOption;



public class PathVisitor {
	public static DirectoryContents visit( String path ){
    	Path directoryPath = Paths.get(path);
        List<FileContents> folders = new ArrayList<>();
        List<FileContents> files = new ArrayList<>();
        DirectoryContents dc = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path entry : stream) {
            	FileContents fc = new FileContents();

                fc.setName(entry.getFileName().toString());
                fc.setParent(entry.getParent().toString());
                fc.setRoot(entry.getRoot().toString());

                if (Files.isDirectory(entry)) {
                	fc.setType("FOLDER");
                    folders.add(fc);
                } else {
                	fc.setType("FILE");
                    files.add(fc);
                }
            }
            
            dc = new DirectoryContents(folders, files, path);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return dc;
    }
	
	private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
	
	public static FileProperties getFileProperties(String filePath) {
		Path path = Paths.get(filePath);

        FileProperties fileProperties = null;

        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            fileProperties = new FileProperties();
            fileProperties.setFilePath(filePath);
            fileProperties.setFileSize(attributes.size());
            fileProperties.setFileName(path.getFileName().toString());
            fileProperties.setFileExtension(getFileExtension(fileProperties.getFileName()));
            fileProperties.setCreatedDate(attributes.creationTime().toMillis());
            fileProperties.setModifiedDate(attributes.lastModifiedTime().toMillis());
            fileProperties.setAccessedDate(attributes.lastAccessTime().toMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return fileProperties;
	}
	
	public static List<DriveContents> getFileSystems() {
		Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();
        
		List<DriveContents> drives = new ArrayList<>();
		for (FileStore fileStore : fileStores) {
            String description = fileStore.toString();
            int startIndex = description.indexOf("(") + 1;
            int endIndex = description.indexOf(")");

            if (startIndex >= 0 && endIndex >= 0) {
                String rootPath = description.substring(startIndex, endIndex);
                try {
	                String total = convertBytes(fileStore.getTotalSpace());
	                String used = convertBytes(fileStore.getUsableSpace());
	                
	                double percentage = (double)fileStore.getUsableSpace()/(double)fileStore.getTotalSpace();
	                
	                DriveContents dc = new DriveContents(rootPath, total, used, percentage);
	                drives.add(dc);
                }catch(Exception e) {}
            } 
        }
		
		return drives;
	}
	
	public static void openFile(String filePath) {
        try {
            // Create a File object for the specified file path
            File file = new File(filePath);
            
            // Check if the Desktop class is supported
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                
                // Check if the file exists
                if (file.exists()) {
                    // Open the file using the default associated application
                    desktop.open(file);
                } else {
                    System.out.println("File does not exist.");
                }
            } else {
                System.out.println("Desktop is not supported.");
                
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", filePath);
                    Process process = processBuilder.start();
                    
                    // Wait for the process to complete
                    int exitCode = process.waitFor();
                    
                    System.out.println("Command executed. Exit code: " + exitCode);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void openLocation(String pathToOpen) {
		try {
            ProcessBuilder processBuilder = new ProcessBuilder("explorer.exe", pathToOpen);
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            System.out.println("Command executed. Exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public static String convertBytes(long bytes) {
        String[] units = {"Bytes", "KB", "MB", "GB", "TB", "PB"};
        int unitIndex = 0;
        double convertedValue = bytes;

        while (convertedValue >= 1024 && unitIndex < units.length - 1) {
            convertedValue /= 1024;
            unitIndex++;
        }

        return String.format("%.2f %s", convertedValue, units[unitIndex]);
    }
	
	
	
	
	public static DirectoryContents searchPath(String startingDirectory, String filter) {
		List<Path> matchedPaths = new ArrayList<>();
        
		try {
            Files.walkFileTree(Paths.get(startingDirectory), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        try {
                        	
                            if (file.getFileName().toString().toLowerCase().contains(filter.toLowerCase())) {
                                matchedPaths.add(file);
                            }
                        } catch (Exception e) {
                            // Handle any exceptions here (e.g., access denied)
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        try {
                            if (dir.getFileName().toString().toLowerCase().contains(filter.toLowerCase())) {
                                matchedPaths.add(dir);
                            }
                        } catch (Exception e) {
                            // Handle any exceptions here (e.g., access denied)
                        }
                        return FileVisitResult.CONTINUE;
                    }
                    
                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException e)
                        throws IOException {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        List<FileContents> folders = new ArrayList<>();
        List<FileContents> files = new ArrayList<>();
        
        for(Path entry : matchedPaths) {
        	FileContents fc = new FileContents();

            fc.setName(entry.getFileName().toString());
            fc.setParent(entry.getParent().toString());
            fc.setRoot(entry.getRoot().toString());

            if (Files.isDirectory(entry)) {
            	fc.setType("FOLDER");
                folders.add(fc);
            } else {
            	fc.setType("FILE");
                files.add(fc);
            }
        }
        
        DirectoryContents dc = new DirectoryContents(folders, files, startingDirectory);

        return dc;
	}	
}

