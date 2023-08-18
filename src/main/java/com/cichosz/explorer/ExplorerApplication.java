package com.cichosz.explorer;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.attribute.FileStoreAttributeView;
import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExplorerApplication.class, args);
	}
}
