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
	public int read(ByteBuffer dst) throws IOException {
		long p = position();
		if (file == null) {
			startReaderThread();
		}

		int capacity = dst.capacity();
		long endPosition = Math.min(file.getFileSize(), p + capacity);
		long waitLength = Math.min(capacity, endPosition - p);

		do {
			System.out.print(".");
		} while (file.getFileSize() != (tempFile = new java.io.File(tempFile.getPath())).length());

		RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r");
		FileChannel fileChannel = randomAccessFile.getChannel();
		randomAccessFile.seek(p);

		long num = 0;
		do {
			int i = fileChannel.read(dst);
			if(i > 0) {
				num += i;
			}
		} while (num != waitLength && fileChannel.position() != num);

		fileChannel.close();
		randomAccessFile.close();

		return (int) num;
	}

	public synchronized void startReaderThread() throws IOException {
		file = JCFileSystem.getByFilename(path.toString());
		tempFile = java.io.File.createTempFile(file.getOriginalFilename(), "");
		InputStream in = GoogleDriveHandler.readFile(file, null);
		FileOutputStream out = new FileOutputStream(tempFile);
		Runnable r = () -> {
			try {
				IOUtils.copy(in, out);
				System.out.println("FINISHED DOWNLOADING");
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