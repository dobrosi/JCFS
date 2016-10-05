package com.programgyar.jcfs;

import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Set;

import com.google.api.services.drive.model.File;

public class JCPosixFileAttributes implements PosixFileAttributes {
	private String fileId;

	public JCPosixFileAttributes(String fileId) {
		this.fileId = fileId;
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
		File f = JCFileSystem.get(fileId);
		if( f == null ) {
			return true;
		} else {
			 return f.getMimeType().equals("application/vnd.google-apps.folder");
		}
		//return true;
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
		if(fileId == null) {
			return 0;
		}
		File f = JCFileSystem.get(fileId);
		if(f == null) {
			return 0;
		}
		Long size = f.getSize();
		
		return size == null ? 0 : size;
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
}