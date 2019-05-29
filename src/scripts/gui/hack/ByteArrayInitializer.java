package scripts.gui.hack;

public class ByteArrayInitializer {
	private byte[] data;
	
	public ByteArrayInitializer( byte[]... inputs ) {
		int totalSize = 0;
		for (int i = 0; i < inputs.length; i++) {
			totalSize += inputs[i].length;
		}
		
		data = new byte[totalSize];
		int offset = 0;
		for (int i = 0; i < inputs.length; i++) {
			byte[] input = inputs[i];
			for (int j = 0; j < input.length; j++) {
				data[offset++] = input[j];
			}
		}
	}
	
	public byte[] getData() {
		return this.data;
	}
}
