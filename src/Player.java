 
// Bridge/Implementor: render implementations
interface RenderImpl {
    void renderImpl(Stream stream, String title);
}

class SoftwareRenderImpl implements RenderImpl {
    @Override
    public void renderImpl(Stream stream, String title) {
        System.out.println("[SoftwareRenderer] Playing '" + title + "' from " + stream);
    }
}

class HardwareRenderImpl implements RenderImpl {
    @Override
    public void renderImpl(Stream stream, String title) {
        System.out.println("[HardwareRenderer] Playing '" + title + "' using hardware from " + stream);
    }
}

// The MediaPlayer: can play Playable (MediaItem or Playlist)

class MediaPlayer implements Playable {
    protected RenderImpl impl;

    public MediaPlayer(RenderImpl impl) {
        this.impl = impl;
    }

    public void setRenderer(RenderImpl i) {
        this.impl = i;
    }

    @Override
    public void playWith(RenderImpl renderer) {
        // no-op for player when used as Playable
    }

    public void play(Media media) {
        System.out.println("[Player] Start playing: " + media.getName());
        media.playWith(impl);
        System.out.println("[Player] Finished: " + media.getName());
    }

    // For decorator plugins to call underlying player
    public void playDirect(Media media, RenderImpl override) {
        RenderImpl r = (override != null) ? override : this.impl;
        System.out.println("[Player] (direct) " + media.getName());
        media.playWith(r);
    }
}

// Decorator base - wraps a MediaPlayer to add features
abstract class PlayerDecorator extends MediaPlayer {
    protected final MediaPlayer wrapped;

    public PlayerDecorator(MediaPlayer wrapped) {
        super(wrapped != null ? wrapped.impl : new SoftwareRenderImpl());
        this.wrapped = wrapped;
    }

    @Override
    public void setRenderer(RenderImpl r) {
        wrapped.setRenderer(r);
    }

    @Override
    public void play(Media media) {
        wrapped.play(media);
    }
}

class SubtitlePlugin extends PlayerDecorator {
    public SubtitlePlugin(MediaPlayer wrapped) {
        super(wrapped);
    }

    @Override
    public void play(Media media) {
        // Play first, then indicate subtitle is on (per requested output format)
        super.play(media);
        System.out.println("[Plugin] Subtitle is on");
    }
}

class EqualizerPlugin extends PlayerDecorator {
    public EqualizerPlugin(MediaPlayer wrapped) {
        super(wrapped);
    }

    @Override
    public void play(Media media) {
        System.out.println("[EqualizerPlugin] Applying EQ preset: Bass Boost");
        super.play(media);
        System.out.println("[EqualizerPlugin] Reverting EQ");
    }
}

class WatermarkPlugin extends PlayerDecorator {
    public WatermarkPlugin(MediaPlayer wrapped) {
        super(wrapped);
    }

    @Override
    public void play(Media media) {
        // Apply watermark during playback; indicate it's on after play (consistent with SubtitlePlugin)
        super.play(media);
        System.out.println("[Plugin] Watermark is on");
    }
}
