package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;

public class JCDirectoryStream implements DirectoryStream<Path> {

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Path> iterator() {
		try {
			return GoogleDriveHandler.getFileList("/").stream().map(p -> {
				JCPath res = new JCPath(p);
				
				return (Path) res;
			}).iterator();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
