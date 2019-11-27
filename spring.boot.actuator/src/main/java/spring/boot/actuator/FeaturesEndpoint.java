package spring.boot.actuator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "features")
public class FeaturesEndpoint {
 
    private Map<String, Feature> features = new ConcurrentHashMap<>();
 
    @ReadOperation
    public Map<String, Feature> features() {
    	
    	features.put("Split View", new Feature(true,"for better multitasking"));
    	features.put("iPad keyboard", new Feature(true,"Turn into a trackpad"));
    	features.put("Notes app", new Feature(true,"Use as a built-in scanner"));
    	features.put("Files app", new Feature(true,"for better organization"));
    	features.put("Spotlight", new Feature(true,"Search anything"));
    	
        return features;
    }
 
    @ReadOperation
    public Feature feature(@Selector String name) {
        return features.get(name);
    }
 
    @WriteOperation
    public void configureFeature(@Selector String name, Feature feature) {
        features.put(name, feature);
    }
 
    @DeleteOperation
    public void deleteFeature(@Selector String name) {
        features.remove(name);
    }
 
    public static class Feature {
    	
        private Boolean enabled;
        private String use;
        
		public Feature(Boolean enabled, String use) {
			super();
			this.enabled = enabled;
			this.use = use;
		}
		
		public Boolean getEnabled() {
			return enabled;
		}
		
		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}
		
		public String getUse() {
			return use;
		}
		
		public void setUse(String use) {
			this.use = use;
		}
        
    }
 
}
