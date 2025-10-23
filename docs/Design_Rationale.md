Design Rationale

This project is designed to be small but focused on showing how different structural design patterns can be used together in a media player system. The main goal is to demonstrate how these patterns help organize code, make it more flexible, and allow new features to be added without changing the existing structure. Instead of focusing on performance or real media playback, this project highlights clean architecture and design clarity.

Patterns Used:

1.	Adapter Pattern
The Adapter pattern is applied through the MediaSource interface and its implementations. These act as adapters for different types of media sources such as local files, HLS streams, or remote APIs. Each one adapts to a common Stream abstraction, which allows the player to handle all media sources in the same way. This makes the playback system independent from how the data is retrieved, making it easy to add new sources in the future without modifying the player code.
2.	Bridge Pattern
The Bridge pattern is used to separate the player’s abstraction from the rendering implementation. The player communicates with a RenderImpl interface, which can have multiple implementations such as SoftwareRenderImpl or HardwareRenderImpl. This separation allows the system to switch between rendering strategies at runtime without duplicating player logic. It also makes the player easier to maintain since changes in rendering do not affect the playback core.
3.	Decorator Pattern
The PlayerDecorator and its subclasses, like subtitle, equalizer, and watermark decorators, demonstrate the Decorator pattern. These decorators wrap around the main MediaPlayer to add new behaviors or features without modifying the original code. This makes the system more flexible, since decorators can be added, removed, or combined at runtime. For example, a user could enable subtitles and an equalizer at the same time, and the player would handle both smoothly.
4.	Proxy Pattern
The Proxy pattern is shown in the CachingProxy class, which acts as a middle layer between the player and the media source. Its job is to cache opened Stream objects to avoid repeated fetching. This is especially useful when simulating remote streaming, where data may be accessed multiple times. By caching, the system reduces repeated load operations and makes playback faster for already-accessed content.
5.	Composite Pattern
The Composite pattern is implemented through the Playlist class, which implements the same Media interface as single media items. A playlist can contain multiple Playable objects, and even other playlists. This allows both single media and playlists to be treated the same way by the player. It simplifies playback management and adds flexibility, such as playing nested playlists or mixed media collections.

Why These Patterns Were Chosen:
The main reason for choosing these patterns is decoupling and flexibility. Using Adapter and Bridge helps separate responsibilities—data handling, rendering, and playback—so that changes in one part do not break others. Decorator and Proxy allow new features and behaviors to be added without modifying core classes, following the Open/Closed Principle. Meanwhile, the Composite pattern promotes simplicity and uniform handling of both single media and collections.

Overall, the combination of these patterns creates a clean, modular design that is easy to understand and extend. This makes the project a practical demonstration of how structural design patterns can work together in a small but well-organized system.