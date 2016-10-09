package com.programgyar.jcfs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;

import com.google.api.services.drive.model.File;

public class JCSeekableByteChannel implements SeekableByteChannel {
	private Path path;
	private File file;
	private long position;
	private Long filesize;
	private long counter;

	public JCSeekableByteChannel(Path path) {
		this.path = path;
	}

	@Override
	public boolean isOpen() {
		return file != null;
	}

	@Override
	public void close() throws IOException {
		filesize = 0L;
		file = null;
		counter = 0L;
	}

	@Override
	public synchronized int read(ByteBuffer dst) throws IOException {
		if (file == null) {
			file = JCFileSystem.getByFilename(path.toString());
			filesize = file.getFileSize();
			counter = 0;
		}
		String range = String.format("bytes=%s-%s", position, position + dst.capacity());
		InputStream in = GoogleDriveHandler.readFile(file, range);
		ReadableByteChannel ch = Channels.newChannel(in);
		int n = 0;
		do {
			int len = ch.read(dst);
			if (len > 0) {
				n += len;
				counter += len;
			}
			if (counter >= filesize) {
				break;
			}
		} while (dst.hasRemaining());

		return n;
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
		return null;
	}
}