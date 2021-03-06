package lilylicious.staticchunkmanager.chunk;


import lilylicious.staticchunkmanager.util.MathUtils;
import lilylicious.staticchunkmanager.util.csLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.chunk.storage.RegionFile;

import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Chunks {

    public static Map<Point, ChunkRequest> chunkMap = new HashMap();
    public static Map<Point, ChunkRequest> worldChunkMap = new HashMap();

    public static void addChunk(ChunkRequest cReq) {
        chunkMap.put(new Point(cReq.cx, cReq.cy), cReq);
    }

    public static void addWorldChunk(ChunkRequest cReq) {
        worldChunkMap.put(new Point(cReq.cx, cReq.cy), cReq);
    }

    public static NBTTagCompound chunkData(int cx, int cy) {
        NBTTagCompound chunkNBT = null;

        int regionX = MathUtils.floorDiv(cx, 32);
        int regionZ = MathUtils.floorDiv(cy, 32);

        IntegratedServer intServ = Minecraft.getMinecraft().getIntegratedServer();

        File worldFolder = new File("saves/" + intServ.getFolderName());
        RegionFile region = new RegionFile(new File(worldFolder, "/region/r." + regionX + "." + regionZ + ".mca"));

        try {
            // chunkX - regionX*32
            DataInputStream datainputstream = region.getChunkDataInputStream(cx - (regionX * 32), cy - (regionZ * 32));
            if (datainputstream == null) {
                csLogger.logWarn("Failed to fetch input stream, make sure the chunks are saved");
            } else {
                chunkNBT = CompressedStreamTools.read(datainputstream);
            }
            datainputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chunkNBT;
    }
}
