package Ignite.Test.quickstart;

import java.io.*;
import org.apache.ignite.*;
import org.apache.ignite.stream.StreamSingleTupleExtractor;
import org.apache.ignite.stream.kafka.KafkaStreamer;

public class StreamWords 
{
	public static void main(String[] args) throws Exception 
	{
		StreamWords tester = new StreamWords();
		tester.run();
	}

	public void run () throws IOException
	{
		// Mark this cluster member as client.
		Ignition.setClientMode(true);
 
		Ignite ignite = Ignition.start("/home/jon/workspace/MvnIgnite/example-ignite.xml");
		
		// The cache is configured with sliding window holding 1 second of the streaming data.
		IgniteCache<String, Long> stmCache = ignite.getOrCreateCache(CacheConfig.wordCache());
		
		IgniteDataStreamer<String, Long> stmr = ignite.dataStreamer(stmCache.getName());
		
//		KafkaStreamer<Object, Object, Object> kafkaStreamer = new KafkaStreamer();
        // Stream words from "alice-in-wonderland" book.
		
		while (true) 
		{
			InputStream in = new FileInputStream("/home/jon/Documents/AliceInWonderLand");
			InputStreamReader isreader = new InputStreamReader(in); 
			LineNumberReader reader = new LineNumberReader(isreader);

			for (String line = reader.readLine(); line != null; line = reader.readLine()) 
			{
				for (String word : line.split(" "))
				{
					if (!word.isEmpty())
					{	// Stream words into Ignite.
						// By using AffinityUuid as a key, we ensure that identical
						// words are processed on the same cluster node.
						// Except we don't.
						stmr.addData(word, (long) 2.0);
					}
				}
			}
		}
	}
}










































//
///**
// * Hello world!
// *
// */
//public class IgniteMaven 
//{
//    public static void main( String[] args )
//    {
//        IgniteMaven ignite = new IgniteMaven();
//        try {
//			ignite.run();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//    
//     
//    
//    public IgniteMaven() 
//    {
//		super();
//	}
//
//	public void run() throws IOException
//    {
//		Ignition.setClientMode(true); //not sure if we want this, but it seems right at the moment
//		
////		CacheConfiguration<Integer, Long> config = new CacheConfiguration<Integer, Long>("myStreamCache");
////		config.setExpiryPolicyFactory(FactoryBuilder.factoryOf(clazz));
//		
//		Ignite ignite = Ignition.start();
//		IgniteCache<String, Long> streamCache = ignite.getOrCreateCache(CacheConfig.wordCache());
//		IgniteDataStreamer<String, String> dataStreamer = ignite.dataStreamer(streamCache.getName());
//		while (true)
//		{
//			InputStream in = IgniteMaven.class.getResourceAsStream("~/codeDir/someRandom.txt");
//			LineNumberReader reader = new LineNumberReader(new InputStreamReader(in)); 
//			
//			for (String line = reader.readLine(); line != null; line = reader.readLine()) 
//			{
//				for (String word : line.split(" "))  
//				{
//					if (!word.isEmpty())
//					{
//						// Stream words into Ignite.
//						// By using AffinityUuid as a key, we ensure that identical
//						// words are processed on the same cluster node.
//						dataStreamer.addData(word, word);
//					}
//				}
//			}
//			
//		}
//
//		

		
		// Get the data streamer reference and stream data.
//		try (IgniteDataStreamer<Integer, String> stmr = ignite.dataStreamer("myStreamCache")) {
//		    // Stream entries.
//		    for (int i = 0; i < 100000; i++)
//		        stmr.addData(i, Integer.toString(i));
//		}
//		MemcachedClient client = null;
//
//		try {
//		    client = new MemcachedClient(new BinaryConnectionFactory(),
//		            AddrUtil.getAddresses("localhost:11211"));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//
//		client.set("key", 0, "val");
//
//		System.out.println("Value for 'key': " + client.get("key"));

//    }
//}
