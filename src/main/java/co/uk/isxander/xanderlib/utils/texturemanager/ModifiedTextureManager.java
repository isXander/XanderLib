package co.uk.isxander.xanderlib.utils.texturemanager;

import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.texturemanager.textures.ExternalFileTexture;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ITickable;
import net.minecraft.util.ReportedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifiedTextureManager implements Constants {

    private final Map<File, ITextureObject> fileTextureMap;

    public ModifiedTextureManager() {
        this.fileTextureMap = new HashMap<>();
    }

    public void bindTexture(File file) {
        ITextureObject texture = fileTextureMap.get(file);
        if (texture == null) {
            texture = new ExternalFileTexture(file);
            loadTexture(file, texture);
        }

        GlStateManager.bindTexture(texture.getGlTextureId());
    }

    private boolean loadTexture(File file, ITextureObject texture) {
        boolean success = true;

        try {
            texture.loadTexture(mc.getResourceManager());
        } catch (IOException e) {
            XanderLib.LOGGER.warn("Failed to load texture: " + file.getPath(), e);
            texture = TextureUtil.missingTexture;
            this.fileTextureMap.put(file, texture);
            success = false;
        } catch (Throwable throwable) {
            ITextureObject finalTexture = texture;
            CrashReport crash = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashreportcategory = crash.makeCategory("Resource location being registered");
            crashreportcategory.addCrashSection("Resource location", file.getPath());
            crashreportcategory.addCrashSectionCallable("Texture object class", () -> finalTexture.getClass().getName());
            throw new ReportedException(crash);
        }
        this.fileTextureMap.put(file, texture);
        return success;
    }

    public ITextureObject getTexture(File file) {
        return fileTextureMap.get(file);
    }

    public void deleteTexture(File file) {
        GlStateManager.deleteTexture(fileTextureMap.get(file).getGlTextureId());
        fileTextureMap.remove(file);
    }


}
