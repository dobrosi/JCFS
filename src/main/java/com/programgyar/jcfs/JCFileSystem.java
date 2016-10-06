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

	private static Map<String, String> filenames = new HashMap<>();

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
		File f = JCFileSystem.getByFilename(arg0);
		if (f == null) {
			return new JCPath(arg0);
		}
		return new JCPath(f);
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
				GoogleDriveHandler.getFileList("/").forEach(f -> {
					store.put(f.getId(), f);
					filenames.put("/" + f.getOriginalFilename(), f.getId());
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return store;
	}

	public static File get(String fileId) {
		return getStore().get(fileId);
	}

	public static File getByFilename(String filename) {
		return getStore().get(filenames.get(filename));
	}

	public static long getFileSize(String filename) {
		if (filename == null) {
			return 0;
		}
		File f = JCFileSystem.getByFilename(filename);
		if (f == null) {
			return 0;
		}
		Long size = f.getFileSize();

		return size == null ? 0 : size;
	}

	public static boolean isDirectory(String filename) {
		File f = JCFileSystem.getByFilename(filename);
		if (f == null) {
			return true;
		} else {
			return f.getMimeType().equals("application/vnd.google-apps.folder");
		}
	}
}
