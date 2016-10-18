package com.programgyar.jcfs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;

import com.google.api.client.util.IOUtils;
import com.google.api.services.drive.model.File;

public class JCSeekableByteChannel implements SeekableByteChannel {
	private Path path;
	private File file;
	private java.io.File tempFile;
	private long position;

	public JCSeekableByteChannel(Path path) {
		this.path = path;
	}

	@Override
	public boolean isOpen() {
		return file != null;
	}

	@Override
	public void close() throws IOException {
		file = null;
		if (tempFile != null && tempFile.exists()) {
			tempFile.delete();
		}
	}

	@Override
	public synchronized int read(ByteBuffer dst) throws IOException {
		if (file == null) {
			startReaderThread();
		}

		int capacity = dst.capacity();
		long endPosition = Math.min(tempFile.length(), position + capacity);
		long waitLength = endPosition - position;

		while (endPosition < tempFile.length()) {
			System.out.print(".");
		}
		
		RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r");
		FileChannel fileChannel = randomAccessFile.getChannel();
		
		int num = 0;
		do {
			num += fileChannel.read(dst);
		} while((num < waitLength));
		
		fileChannel.close();
		randomAccessFile.close();
		
		return num;
	}

	public void startReaderThread() throws IOException {
		file = JCFileSystem.getByFilename(path.toString());
		tempFile = java.io.File.createTempFile(file.getOriginalFilename(), "");
		InputStream in = GoogleDriveHandler.readFile(file, null);
		FileOutputStream out = new FileOutputStream(tempFile);
		Runnable r = () -> {
			try {
				IOUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		new Thread(r).start();
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
		return file.getFileSize();
	}

	@Override
	public SeekableByteChannel truncate(long size) throws IOException {
		return null;
	}
}