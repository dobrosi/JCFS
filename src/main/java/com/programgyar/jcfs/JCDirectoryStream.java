package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class JCDirectoryStream implements DirectoryStream<Path> {

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Path> iterator() {
		ArrayList<Path> res = new ArrayList<Path>();
		res.add(new JCPath("ok"));
		return res.iterator();
	}

}
