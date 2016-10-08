package com.programgyar.jcfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;

import com.google.api.services.drive.model.File;

public class JCSeekableByteChannel implements SeekableByteChannel {

	private Path path;
	private File file;
	private long position;
	private Long filesize;
	private ReadableByteChannel channel;
	private long counter;

	public JCSeekableByteChannel(Path path) {
		this.path = path;
	}

	@Override
	public boolean isOpen() {
		return channel != null;
	}

	@Override
	public void close() throws IOException {
		channel.close();
		channel = null;
		counter = 0;
		filesize = 0L;
		blocks.clear();
	}

	@Override
	public synchronized int read(ByteBuffer dst) throws IOException {
		if (channel == null) {
			file = JCFileSystem.getByFilename(path.toString());
			channel = Channels.newChannel(GoogleDriveHandler.readFile(file));
			filesize = file.getFileSize();
		}

		int n = 0;

		long nextIndex = position + dst.capacity();
		ByteBuffer buf = ByteBuffer.allocateDirect((int)(nextIndex - counter));
		do {
			
			int num = channel.read(buf);
			
			if (num > 0) {
				n += num;
				counter += num;
				
				if(counter >= filesize) {
					break;
				}
			}
		} while (n != buf.capacity());
		blocks.add(new Block(position, nextIndex ,buf));
		
		dst.put(getBlock(position, dst.capacity()));

		return n;
	}
	
	private byte[] getBlock(long position, int capacity) {
		byte[] res = new byte[capacity];
		
		int i = 0;
		while(i < capacity) {
		//	blocks.stream().filter(b -> b.firstIndex >= position && )
		}
		
		return res;		
	}

	private Set<Block> blocks = new TreeSet<>((c1, c2) -> (int)(c1.firstIndex - c2.firstIndex));

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
	
	class Block {
		long firstIndex;
		long lastIndex;
		ByteBuffer bytes;
		public Block(long firstIndex, long lastIndex, ByteBuffer bytes) {
			super();
			this.firstIndex = firstIndex;
			this.lastIndex = lastIndex;
			this.bytes = bytes;
		}
	}

}
