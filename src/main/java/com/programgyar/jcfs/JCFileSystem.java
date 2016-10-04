package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.api.services.drive.model.File;

public class JCFileSystem extends FileSystem {

	private static JCFileSystem fileSystem;

	private static Map<String, File> store;

	private JCFileSystem() {
	}

	public static JCFileSystem getInstance() {
		if (fileSystem == null) {
			fileSystem = new JCFileSystem();
		}
		return fileSystem;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<FileStore> getFileStores() {
		return Arrays.asList();
	}

	@Override
	public Path getPath(String arg0, String... arg1) {
		return new JCPath(null, arg0);
	}

	@Override
	public PathMatcher getPathMatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Path> getRootDirectories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSeparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPrincipalLookupService getUserPrincipalLookupService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WatchService newWatchService() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileSystemProvider provider() {
		return JCFileSystemProvider.getInstance();
	}

	@Override
	public Set<String> supportedFileAttributeViews() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Map<String, File> getStore() {
		if (store == null) {
			store = new HashMap<>();
			try {
				GoogleDriveHandler.getFileList("/").forEach(f -> store.put(f.getId(), f));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return store;
	}

}
