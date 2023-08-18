package com.cichosz.explorer;

import java.nio.file.Path;
import java.util.List;

public class DirectoryContents {

	private List<FileContents> folders;
	private List<FileContents> files;
	private String path;
	
	public DirectoryContents(List<FileContents> fol, List<FileContents> fil, String p) {
		this.folders = fol;
		this.files = fil;
		this.path = p;
	}
	
	public List<FileContents> getFolders(){
		return this.folders;
	}
	
	public List<FileContents> getFiles(){
		return this.files;
	}
	
	public String getPath() {
		return this.path;
	}
}
