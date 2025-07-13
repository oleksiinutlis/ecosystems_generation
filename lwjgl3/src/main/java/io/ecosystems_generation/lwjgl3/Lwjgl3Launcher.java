    package io.ecosystems_generation.lwjgl3;

    import com.badlogic.gdx.Graphics;
    import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
    import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

    import io.ecosystems_generation.Main;

    /** Launches the desktop (LWJGL3) application. */
    public class Lwjgl3Launcher {
        public static void main(String[] args) {
            if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
            createApplication();
        }

        private static Lwjgl3Application createApplication() {
            return new Lwjgl3Application(new Main(), getDefaultConfiguration());
        }

        private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
            Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
            configuration.setTitle("ecosystems-generation");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disab|le Vsync; this can cause screen tearing.
        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
//        configuration.setWindowedMode(1024,1024);
// Get the current monitor's display mode (native resolution)

// Set window size to match screen resolution
        configuration.setWindowedMode(displayMode.width, displayMode.height);

// Remove window borders and title bar
        configuration.setDecorated(false);

// Optional: Center on screen (doesn't matter since it fills whole screen)
        configuration.setWindowPosition(0, 0);

        return configuration;
    }
}
