 
import java.util.HashMap;
import java.util.Map;

// Simple stream representation
class Stream {
    public final String descriptor;

    public Stream(String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return descriptor;
    }
}

// Adapter interface for different media sources
interface MediaSource {
    Stream open();
}

class LocalFileSource implements MediaSource {
    private final String path;

    public LocalFileSource(String path) {
        this.path = path;
    }

    @Override
    public Stream open() {
        // In real code we'd open a FileInputStream. Here we simulate.
        return new Stream("LocalFile(" + path + ")");
    }
}

class HlsStreamSource implements MediaSource {
    private final String url;

    public HlsStreamSource(String url) {
        this.url = url;
    }

    @Override
    public Stream open() {
        return new Stream("HLS(" + url + ")");
    }
}

class RemoteApiSource implements MediaSource {
    private final String id;

    public RemoteApiSource(String id) {
        this.id = id;
    }

    @Override
    public Stream open() {
        // Simulate network adaptation
        return new Stream("RemoteApi(id=" + id + ")");
    }
}

// Proxy that caches opened streams (very simple cache)
class CachingProxy implements MediaSource {
    private final MediaSource backend;
    private static final Map<String, Stream> cache = new HashMap<>();

    public CachingProxy(MediaSource backend) {
        this.backend = backend;
    }

    @Override
    public Stream open() {
        String key = backend.getClass().getSimpleName() + "@" + System.identityHashCode(backend);
        if (cache.containsKey(key)) {
            System.out.println("[Proxy] Cache hit for " + key);
            return cache.get(key);
        }
        System.out.println("[Proxy] Cache miss for " + key + " - fetching");
        Stream s = backend.open();
        cache.put(key, s);
        return s;
    }
}
