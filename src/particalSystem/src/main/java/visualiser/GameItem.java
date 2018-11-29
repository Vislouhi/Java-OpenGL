package visualiser;

import org.joml.Quaternionf;
import org.joml.Vector3f;


public class GameItem {

    private MeshFromBuffers[] meshes;

    private final Vector3f position;

    private float scale;

    private final Quaternionf rotation;

    private int textPos;
            
    public GameItem() {
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Quaternionf();
        textPos = 0;
    }

    public GameItem(MeshFromBuffers mesh) {
        this();
        this.meshes = new MeshFromBuffers[]{mesh};
    }

    public GameItem(MeshFromBuffers[] meshes) {
        this();
        this.meshes = meshes;
    }
   

    public Vector3f getPosition() {
        return position;
    }

    public int getTextPos() {
        return textPos;
    }

    public final void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public final void setRotation(Quaternionf q) {
        this.rotation.set(q);
    }

    public MeshFromBuffers getMesh() {
        return meshes[0];
    }
    
    public MeshFromBuffers[] getMeshes() {
        return meshes;
    }

    public void setMeshes(MeshFromBuffers[] meshes) {
        this.meshes = meshes;
    }

    public void setMesh(MeshFromBuffers mesh) {
        this.meshes = new MeshFromBuffers[]{mesh};
    }
    
    public void cleanup() {
        int numMeshes = this.meshes != null ? this.meshes.length : 0;
        for(int i=0; i<numMeshes; i++) {
            this.meshes[i].cleanUp();
        }
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
    }
}