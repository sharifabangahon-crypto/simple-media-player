 
public class MediaItem implements Media {
    private final String name;
    private final MediaSource source;

    public MediaItem(String name, MediaSource source) {
        this.name = name;
        this.source = source;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void playWith(RenderImpl renderer) {
        // Adapt source to a stream and use renderer
        Stream stream = source.open();
        renderer.renderImpl(stream, name);
    }
}
