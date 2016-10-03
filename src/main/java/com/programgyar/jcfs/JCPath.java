package com.programgyar.jcfs;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

import com.google.api.services.drive.model.File;

public class JCPath implements Path {
	private String filename;
	private File file;

	public JCPath(String filename) {
		this.filename = filename;
	}

	public JCPath(File file) {
		this.file = file;
	}
	
	public int compareTo(Path arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean endsWith(Path arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean endsWith(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Path getFileName() {
		return new JCPath(file == null ? filename : file.getName() );
	}

	public FileSystem getFileSystem() {
		return JCFileSystem.getInstance();
	}

	public Path getName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNameCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Path getParent() {
		return null;
	}

	public Path getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAbsolute() {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<Path> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Path normalize() {
		// TODO Auto-generated method stub
		return null;
	}

	public WatchKey register(WatchService arg0, Kind<?>... arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public WatchKey register(WatchService arg0, Kind<?>[] arg1, Modifier... arg2) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Path relativize(Path arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Path resolve(Path arg0) {
		return null;
	}

	public Path resolve(String filename) {
		return new JCPath(filename);
	}

	public Path resolveSibling(Path arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Path resolveSibling(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean startsWith(Path arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean startsWith(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Path subpath(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Path toAbsolutePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public java.io.File toFile() {
		return new java.io.File(filename);
	}

	public Path toRealPath(LinkOption... options) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public URI toUri() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return filename == null ? file.getName() : filename;
	}

	public File getFile() {
		return file;
	}
}
