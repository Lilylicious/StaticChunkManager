package lilylicious.staticchunkmanager.api;

import cpw.mods.fml.common.Loader;
import lilylicious.staticchunkmanager.chunk.Chunks;
import lilylicious.staticchunkmanager.util.csLogger;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class csAPI {


    /**
     * Adds the specified file to the map of requested chunks.
     * The file must be a .chunk file generated by the in-game
     * command /cs. Overlapping requests replace the previous,
     * this is logged as a warning in the log.
     *
     * @param file .chunk file
     * @param cx   X chunk coordinate
     * @param cy   Y chunk coordinate
     */
    public static void requestChunk(File file, int cx, int cy) {

        if (!FilenameUtils.getExtension(file.getPath()).equals("nbt")) {
            csLogger.logWarn("Mod %s tried to load an invalid chunk file.", Loader.instance().activeModContainer().getModId());
        } else if (file.exists()) {
            Chunks.addChunk(file, cx, cy);
        } else {
            csLogger.logWarn("Specified file does not exist.");
        }

    }
}