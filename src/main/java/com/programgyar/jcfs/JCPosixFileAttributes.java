package com.programgyar.jcfs;

import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Set;

public class JCPosixFileAttributes implements PosixFileAttributes {
	private JCPath path;

	public JCPosixFileAttributes(JCPath path) {
		this.path = path;
	}

	@Override
	public FileTime lastModifiedTime() {
		// TODO Auto-generated method stub
		return FileTime.fromMillis(0);
	}

	@Override
	public FileTime lastAccessTime() {
		// TODO Auto-generated method stub
		return FileTime.fromMillis(0);
	}

	@Override
	public FileTime creationTime() {
		// TODO Auto-generated method stub
		return FileTime.fromMillis(0);
	}

	@Override
	public boolean isRegularFile() {
		return !isDirectory();
	}

	@Override
	public boolean isDirectory() {
		if(path.getFile() == null) {
			return true;
		} else {
			 return path.getFile().getMimeType().equals("application/vnd.google-apps.folder");
		}
	}

	@Override
	public boolean isSymbolicLink() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object fileKey() {
		return null;
	}

	@Override
	public UserPrincipal owner() {
		return null;
	}

	@Override
	public GroupPrincipal group() {
		return null;
	}

	@Override
	public Set<PosixFilePermission> permissions() {
		return new HashSet<>();
	}
	
	public JCPath getPath() {
		return path;
	}
}