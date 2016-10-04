package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import co.paralleluniverse.javafs.JavaFS;

public class Main {

	public static void main(String[] args) {
		JCFileSystem jcFileSystem = JCFileSystem.getInstance();
//		Path foo = jcFileSystem.getPath("/foo");
//		
//		try {
//			Files.createFile(foo);
//			
//			Path hello = foo.resolve("hello.txt"); // /foo/hello.txt
//			Files.write(hello, Arrays.asList("hello world"), StandardCharsets.UTF_8);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println("done.");
		
		Map<String, String> options = new HashMap<>();
		
		try {
			JavaFS.mount(jcFileSystem, Paths.get("test-drive"), false, true, options);
			Thread.sleep(Long.MAX_VALUE);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
