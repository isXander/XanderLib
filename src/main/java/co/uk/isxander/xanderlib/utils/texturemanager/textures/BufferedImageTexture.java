package co.uk.isxander.xanderlib.utils.texturemanager.textures;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageTexture extends AbstractTexture {

    protected BufferedImage image;

    public BufferedImageTexture(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) {
        this.deleteGlTexture();
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), modifyImage(image), false, false);
    }

    protected BufferedImage modifyImage(BufferedImage original) {
        return original;
    }

}
