package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Collectors;

public class JCDirectoryStream implements DirectoryStream<Path> {

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Path> iterator() {
		return (Iterator<Path>)(Iterator<?>)JCFileSystem.getStore().values().stream().map(f -> new JCPath(f)).collect(Collectors.toList()).iterator();
	}
}