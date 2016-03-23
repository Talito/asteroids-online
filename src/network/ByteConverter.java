package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteConverter {

	/**
	 * Sufficient for the whole application.
	 */
	public static int byteSize = 4096;
	
	public static <E> byte[] convertToByteArray(E obj) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(ByteConverter.byteSize);
		ObjectOutputStream objectOutputStream;
		byte[] objectBytes = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectBytes = byteArrayOutputStream.toByteArray();
			objectOutputStream.close();
		} catch(Exception e) {
			
		}
		return objectBytes;
	}
	
	public static <E> E getObjectFromByteArray(byte[] buf, Class<E> type) {
		E obj = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf));
			obj = type.cast(objectInputStream.readObject());
		} catch(Exception e) {
			
		}
		return obj;
	}
	
	
}
