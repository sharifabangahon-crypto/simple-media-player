 
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Choose media source:");
            System.out.println("1 - Local File (No Proxy)");
            System.out.println("2 - HLS Stream (With Proxy Cache)");
            System.out.println("3 - Remote API (With Proxy Cache)");
            System.out.print("> ");
            String choiceLine = sc.nextLine().trim();
            int choice = 1;
            try { choice = Integer.parseInt(choiceLine); } catch (Exception ex) { /* default 1 */ }

            MediaSource source;
            Media media;
            // Ask for a media identifier (filename, URL or id)
            System.out.println();
            System.out.println("Enter media identifier (filename for Local, URL for HLS, id for Remote). Press Enter to use default:");
            System.out.print("> ");
            String mediaId = sc.nextLine().trim();
            if (mediaId.isEmpty()) mediaId = null;

            // Build final source depending on user's choice and mediaId
            if (choice == 1) {
                String filename = (mediaId != null) ? mediaId : "song.mp3";
                source = new LocalFileSource(filename);
                media = new MediaItem((mediaId != null) ? mediaId : "Local Song", source);
            } else if (choice == 2) {
                String url = (mediaId != null) ? mediaId : "https://example.com/stream.m3u8";
                source = new CachingProxy(new HlsStreamSource(url));
                media = new MediaItem((mediaId != null) ? mediaId : "Live HLS", source);
            } else if (choice == 3) {
                String id = (mediaId != null) ? mediaId : "track-42";
                source = new CachingProxy(new RemoteApiSource(id));
                media = new MediaItem((mediaId != null) ? id : "Remote Track", source);
            } else {
                System.out.println("Invalid source choice, defaulting to Local File.");
                source = new LocalFileSource("song.mp3");
                media = new MediaItem("Local Song", source);
            }
            System.out.println();
            System.out.println("Available plugins: subtitle, equalizer, watermark");
            System.out.println("Enter plugins to enable (comma-separated or 'none'):");
            System.out.print("> ");
            String pluginsLine = sc.nextLine().trim().toLowerCase();
            Set<String> enabled = new HashSet<>();
            if (!pluginsLine.isEmpty() && !pluginsLine.equals("none")) {
                String[] toks = pluginsLine.split("\\s*,\\s*");
                for (String t : toks) enabled.add(t);
            }

            System.out.println();
            System.out.println("Switch renderer (hardware/software/quit):");
            RenderImpl renderer = null;
            while (renderer == null) {
                System.out.print("> ");
                String r = sc.nextLine().trim().toLowerCase();
                if (r.equals("hardware")) renderer = new HardwareRenderImpl();
                else if (r.equals("software")) renderer = new SoftwareRenderImpl();
                else if (r.equals("quit")) {
                    System.out.println("Exiting.");
                    return;
                } else {
                    System.out.println("Please type 'hardware', 'software' or 'quit'.");
                }
            }

            // Build player and apply plugins deterministically
            MediaPlayer player = new MediaPlayer(renderer);
            if (enabled.contains("subtitle")) player = new SubtitlePlugin(player);
            if (enabled.contains("equalizer")) player = new EqualizerPlugin(player);
            if (enabled.contains("watermark")) player = new WatermarkPlugin(player);

                System.out.println();
                System.out.println("Playing with settings:");
                System.out.println(" - Media: " + media.getName());
                System.out.println(" - Renderer: " + (renderer instanceof HardwareRenderImpl ? "hardware" : "software"));
                System.out.println(" - Plugins: " + (enabled.isEmpty() ? "none" : String.join(", ", enabled)));
                System.out.println();

                player.play(media);

                // Ask to perform another transaction
                System.out.println();
                System.out.print("Play another? (y/n): ");
                String again = sc.nextLine().trim().toLowerCase();
                if (!again.equals("y") && !again.equals("yes")) {
                    System.out.println("Exiting.");
                    break;
                }
                System.out.println();
            }
        } finally {
            sc.close();
        }
    }
}
