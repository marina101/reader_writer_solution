/**
 * Driver method to test out reader writer simulation
 * Starts 10 new reader and writer threads
 * @author Marina Chirchikova
 *
 */
public class Main {
	public static void main(String[] args){
		FileControl fc = new FileControl();
		
		WriterThread[] wt = new WriterThread[10];
		ReaderThread[] rt = new ReaderThread[10];
		
		wt[0] = new WriterThread(fc, 1);
		wt[1] = new WriterThread(fc, 2);
		wt[2] = new WriterThread(fc, 3);
		wt[3] = new WriterThread(fc, 4);
		wt[4] = new WriterThread(fc, 5);
		wt[5] = new WriterThread(fc, 6);
		wt[6] = new WriterThread(fc, 7);
		wt[7] = new WriterThread(fc, 8);
		wt[8] = new WriterThread(fc, 9);
		wt[9] = new WriterThread(fc, 10);
		
		rt[0] = new ReaderThread(fc, 1);
		rt[1] = new ReaderThread(fc, 2);
		rt[2] = new ReaderThread(fc, 3);
		rt[3] = new ReaderThread(fc, 4);
		rt[4] = new ReaderThread(fc, 5);
		rt[5] = new ReaderThread(fc, 6);
		rt[6] = new ReaderThread(fc, 7);
		rt[7] = new ReaderThread(fc, 8);
		rt[8] = new ReaderThread(fc, 9);
		rt[9] = new ReaderThread(fc, 10);
		
		for(int i = 0; i < wt.length; i++){
			wt[i].start();
			rt[i].start();
		}
		
		
		for(int i=0; i<10; i++){
			try {
				rt[i].join();
				wt[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
