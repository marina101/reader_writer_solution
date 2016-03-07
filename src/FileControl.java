import java.util.concurrent.locks.*;
/**
 * This class is a simulation of a monitor that controls the entry and exit 
 * of reader and writer threads - it is a possible solution to the reader
 * writer problem. It satisfies the following constraints:
 * -at most one writer can be active on the file at a specific time
 * -when a writer is writing to the file, no reader can read from the file
 * -more than one reader can be reading from the file simultaneously
 * -when a writer is waiting to write, no more new readers should be allowed to read
 * -when a writer is writing and some other writer is waiting to write, the 
 * writer is given more preference over a reader waiting to read 
 * @author Marina Chirchikova
 *
 */
public class FileControl {
	private final Lock lock = new ReentrantLock();
	private final Condition canRead = lock.newCondition();
	private final Condition canWrite = lock.newCondition();
	private int waitingWriters;
	private int activeWriters;
	private int waitingReaders;
	private int activeReaders;
	
	/**
	 * Constructor method
	 */
	public FileControl(){
		waitingWriters = 0;
		activeWriters = 0;
		waitingReaders = 0;
		activeReaders = 0;
	}
	
	/**
	 * Sets up the conditions for writer threads to enter
	 * @param id of thread
	 */
	public void WriterEntry(int id){
		lock.lock();
		try{
			while(activeWriters == 1 || activeReaders > 0){
				++waitingWriters;
				System.out.println("Writer thread " + id + ": waiting to write");
				canWrite.await();
				--waitingWriters;
			}
			System.out.println("Writer thread " + id + ": ready to write");
			activeWriters = 1;
		}
		catch(InterruptedException e){
			System.out.println("Error - interrupted exception");
		}
		finally{
			lock.unlock();
		}
	}
	
	/**
	 * Controls the exit of writer threads
	 * @param id of thread
	 */
	public void WriterExit(int id){
		lock.lock();
		try{
			activeWriters = 0;
			System.out.println("Writer thread " + id + ": finished writing");
			if (waitingWriters > 0)
				canWrite.signal();
			else
				canRead.signal();
			}
		finally{
			lock.unlock();
		}
	}	
	
	/**
	 * Controls the entry of reader threads
	 * @param id of thread
	 */
	public void ReaderEntry(int id){
		lock.lock();
		try{
			while(activeWriters == 1 || waitingWriters > 0){
				++waitingReaders;
				System.out.println("Reader thread " + id + ": waiting to read");
				canRead.await();
				--waitingReaders;
			}
			++activeReaders;
			System.out.println("Reader thread " + id + ": ready to read");
			canRead.signal();
			
		}
		catch(InterruptedException e){
			System.out.println("Error - interrupted exception");
		}
		finally{
			lock.unlock();
		}
	}
	
	/**
	 * Controls the exit of reader threads
	 * @param id of thread
	 */
	public void ReaderExit(int id){
		lock.lock();
		System.out.println("Reader thread " + id + ": finished reading");
		try{
			--activeReaders;
			if(activeReaders == 0){
				canWrite.signal();
			}
		}
		finally{
			lock.unlock();
		}
	}
}
