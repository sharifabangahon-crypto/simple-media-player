 
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Media {
    private final String name;
    private final List<Playable> items = new ArrayList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void add(Playable p) {
        items.add(p);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void playWith(RenderImpl renderer) {
        System.out.println("[Playlist] " + name + " start");
        for (Playable p : items) {
            p.playWith(renderer);
        }
        System.out.println("[Playlist] " + name + " end");
    }
}
