package Ignite.Test.quickstart;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.cache.affinity.AffinityUuid;
import org.apache.ignite.configuration.CacheConfiguration;
import java.util.concurrent.*;

public class CacheConfig 
{
	public static CacheConfiguration<String, Long> wordCache() 
	{
		CacheConfiguration<String, Long> cfg = new CacheConfiguration<String, Long>("words");
		// Index individual words.
		cfg.setIndexedTypes(AffinityUuid.class, /*word type*/String.class);
		// Sliding window of 1 seconds.
		cfg.setExpiryPolicyFactory(FactoryBuilder.factoryOf(
				new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 10))));
		return cfg;
	}
}