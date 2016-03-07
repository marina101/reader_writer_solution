import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * This class defines the reader threads. Each reader thread accesses the shared
 * file 1000 times and reads either the next 26 characters or stops at the end 
 * of the file, whichever is shorter.
 * @author Marina Chirchikova
 *
 */
public class ReaderThread extends Thread{
	private int id;
	private FileControl fc;
	private BufferedReader inputStream;
	private String readerInput = "";
	
	/**
	 * Constructor
	 * @param f the shared "file control" monitor
	 * @param id of thread
	 */
	public ReaderThread(FileControl f, int id){
		this.id = id;
		fc = f;
		try{
			inputStream = new BufferedReader(new FileReader("Task1out.txt"));
		}
		catch(FileNotFoundException e){
			System.out.println("file wasn't found");
		}
	}
	
	/**
	 * The run method - each thread reads a string of lettersfrom the file 1000 times
	 */
	public void run(){
		int count = 0;
		while(count < 2){		
			fc.ReaderEntry(id);
			
			//reading code
			try{
				int c = 0;
				int charac = 0;
				
				while (((charac = inputStream.read()) != -1) && c < 26)
		        {
					String str = "";
					str += Character.toString(((char)charac));
			        readerInput += str;
			        c += 1;	
		        }
			}
			catch (IOException e){
				e.printStackTrace();
			}
			
		
			//reading ended
			
			fc.ReaderExit(id);
			count++;
			
		}
		try{
		inputStream.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("ReaderThread id " + id + " has finished all its loops");
	}
	


}
