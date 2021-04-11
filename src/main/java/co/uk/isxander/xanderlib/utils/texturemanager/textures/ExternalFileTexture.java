package co.uk.isxander.xanderlib.utils.texturemanager.textures;

import co.uk.isxander.xanderlib.utils.Constants;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import java.awt.image.BufferedImage;
import java.io.*;

public class ExternalFileTexture extends AbstractTexture {

    protected final File textureLocation;

    public ExternalFileTexture(File file) {
        this.textureLocation = file;
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        InputStream is = new FileInputStream(textureLocation);

        BufferedImage img = TextureUtil.readBufferedImage(is);
        img = modifyImage(img);

        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), img, false, false);
        is.close();
    }

    protected BufferedImage modifyImage(BufferedImage original) {
        return original;
    }
}
