package de.enzaxd.viaforge;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import de.enzaxd.viaforge.loader.VRBackwardsLoader;
import de.enzaxd.viaforge.loader.VRProviderLoader;
import de.enzaxd.viaforge.platform.VRInjector;
import de.enzaxd.viaforge.platform.VRPlatform;
import de.enzaxd.viaforge.utils.JLoggerToLog4j;
import io.netty.channel.EventLoop;
import io.netty.channel.local.LocalEventLoopGroup;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class ViaForge {

    public final static int SHARED_VERSION = 340;

    private static final ViaForge instance = new ViaForge();

    public static ViaForge getInstance() {
        return instance;
    }

    private final Logger jLogger = new JLoggerToLog4j(LogManager.getLogger("ViaForge"));
    private final CompletableFuture<Void> initFuture = new CompletableFuture<>();

    private ExecutorService asyncExecutor;
    private EventLoop eventLoop;

    private File file;
    private int version;
    private String lastServer;

    public void start() {
        ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ViaForge-%d").build();
        asyncExecutor = Executors.newFixedThreadPool(8, factory);

        eventLoop = new LocalEventLoopGroup(1, factory).next();
        eventLoop.submit(initFuture::join);

        setVersion(SHARED_VERSION);
        this.file = new File("ViaForge");
        if (this.file.mkdir())
            this.getjLogger().info("Creating ViaForge Folder");

        Via.init(
                ViaManagerImpl.builder()
                .injector(new VRInjector())
                .loader(new VRProviderLoader())
                .platform(new VRPlatform(file))
                .build()
        );

        MappingDataLoader.enableMappingsCache();
        ((ViaManagerImpl) Via.getManager()).init();

        new VRBackwardsLoader(file);

        initFuture.complete(null);
    }

    public Logger getjLogger() {
        return jLogger;
    }

    public CompletableFuture<Void> getInitFuture() {
        return initFuture;
    }

    public ExecutorService getAsyncExecutor() {
        return asyncExecutor;
    }

    public EventLoop getEventLoop() {
        return eventLoop;
    }

    public File getFile() {
        return file;
    }

    public String getLastServer() {
        return lastServer;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setLastServer(String lastServer) {
        this.lastServer = lastServer;
    }
}
