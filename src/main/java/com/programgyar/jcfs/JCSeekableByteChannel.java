package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;

public class JCSeekableByteChannel implements SeekableByteChannel {

	private Path path;

	public JCSeekableByteChannel(Path path) {
		this.path = path;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SeekableByteChannel position(long newPosition) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SeekableByteChannel truncate(long size) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
