package com.programgyar.jcfs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;

import com.google.api.services.drive.model.File;

public class JCSeekableByteChannel implements SeekableByteChannel {

	private Path path;
	private InputStream in;
	private File file;
	private long position;
	private Long filesize;

	public JCSeekableByteChannel(Path path) {
		this.path = path;
	}

	@Override
	public boolean isOpen() {
		return in != null;
	}

	@Override
	public void close() throws IOException {
		in = null;
	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		if (in == null) {
			file = JCFileSystem.getByFilename(path.toString());
			filesize = file.getFileSize();
			in = GoogleDriveHandler.readFile(file);
			position = 0l;
		}
		byte[] bs = new byte[1024];
		int len = in.read(bs, (int) position, (int) Math.min(1024, filesize - position));
		dst.put(bs, (int) position, len);
		position += len;
		return len;
	}

	@Override
	public int write(ByteBuffer src) throws IOException {
		byte[] buffer = new byte[src.remaining()];
		src.get(buffer);
		System.out.println(">" + new String(buffer));
		return buffer.length;
	}

	@Override
	public long position() throws IOException {
		return position;
	}

	@Override
	public SeekableByteChannel position(long newPosition) throws IOException {
		this.position = newPosition;
		return this;
	}

	@Override
	public long size() throws IOException {
		return filesize;
	}

	@Override
	public SeekableByteChannel truncate(long size) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
