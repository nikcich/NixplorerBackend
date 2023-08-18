package com.cichosz.explorer.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cichosz.explorer.DirectoryContents;
import com.cichosz.explorer.DriveContents;
import com.cichosz.explorer.FileContents;
import com.cichosz.explorer.FileProperties;
import com.cichosz.explorer.PathVisitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/explorer")
public class RestControllerExample {

	@CrossOrigin
    @GetMapping("/strings")
    public List<String> getStringList() {
        return Arrays.asList("String 1", "String 2", "String 3");
    }
    
    @CrossOrigin
    @GetMapping("/fileSystems")
    public List<DriveContents> getFileSystems(){
    	List<DriveContents> ldc = PathVisitor.getFileSystems();
		return ldc;
    }
    
    @CrossOrigin
    @GetMapping("/path")
    public DirectoryContents getPath(@RequestParam(name = "path", required = true) String path) {
    	DirectoryContents dc = PathVisitor.visit(path);
		return dc;
    }
    
    @CrossOrigin
    @GetMapping("/properties")
    public FileProperties getProperties(@RequestParam(name = "path", required = true) String path) {
    	FileProperties fp = PathVisitor.getFileProperties(path);
		return fp;
    }
    
    @CrossOrigin
    @PostMapping("/open")
    public void openFile(@RequestParam(name = "path", required = true) String inputPath) {
    	Path path = Paths.get(inputPath);

        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                PathVisitor.openLocation(inputPath);
            } else if (Files.isRegularFile(path)) {
                PathVisitor.openFile(inputPath);
            } // Else neither file nor directory
        } // Else doesn't exist
    }
    
    @CrossOrigin
    @GetMapping("/search")
    public DirectoryContents searchFilter(@RequestParam(name = "filter", required = true) String filter, @RequestParam(name = "directory", required = true) String directory) {
    	
    	long start = System.currentTimeMillis();
    	DirectoryContents dc = PathVisitor.searchPath(directory, filter);
    	long end = System.currentTimeMillis();
    	System.out.println("Searched within " + directory + " for " + filter + ", and found - ");
    	System.out.println(dc.getFiles().size() + " files");
    	System.out.println(dc.getFolders().size() + " folders");
    	System.out.println("Search took: " + (end-start) + "ms");
    	
    	return dc;
    }
}

