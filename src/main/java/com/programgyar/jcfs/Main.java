package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		JCFileSystem jcFileSystem = new JCFileSystem();
		Path foo = jcFileSystem.getPath("/foo");
		
		try {
			Files.createFile(foo);
			
			Path hello = foo.resolve("hello.txt"); // /foo/hello.txt
			Files.write(hello, Arrays.asList("hello world"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("done.");
	}

}
