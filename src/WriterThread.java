import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * This class defines the writer thread. Each thread writes the characters of the 
 * alphabet to the shared file 1000 times.
 * @author Marina Chirchikova
 *
 */
public class WriterThread extends Thread{
	private int id;
	private FileControl fc;
	private PrintWriter outputStream = null;
	private String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	/**
	 * Constructor
	 * @param f shared monitor object
	 * @param id thread id
	 */
	public WriterThread(FileControl f, int id){
		this.id = id;
		fc = f;
		
	}
	
	/**
	 * Run method - each thread writes to the shared file 1000 times
	 */
	public void run(){
		int count = 0;
		while(count < 2){		
			fc.WriterEntry(id);
			Write("Task1out.txt");
			fc.WriterExit(id);
			count++;	
		}
		System.out.println("Writer thread id " + id + " has finished all its loops");
	}
	
	/**
	 * Method for accessing the shared file
	 * @param filename
	 */
	public void Write(String filename){
		try{
			outputStream = new PrintWriter(new FileOutputStream(filename, true));
		}
		catch(FileNotFoundException e){
			System.out.println("Error opening the file " + filename);
		}
		for(int i = 0; i < alphabet.length; i++){
			outputStream.print(alphabet[i]);
		}
		
		outputStream.close();
	}

}
