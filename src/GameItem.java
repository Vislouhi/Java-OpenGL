package com.vislouh.kumarcatcher;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;

import org.joml.Vector3f;
import org.joml.Vector4f;


import java.nio.FloatBuffer;


import de.javagl.obj.Mtl;
import de.javagl.obj.MtlReader;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjSplitting;
import de.javagl.obj.ObjUtils;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class GameItem {

    private  Vector3f translationVector;

    private Material material;



    private FloatBuffer vertexBuffer;
    private FloatBuffer normalBuffer;
    private FloatBuffer textureBuffer;
    private IntBuffer indexBuffer;
    private FloatBuffer mCubePositions;
    /** Size of the position data in elements. */
    private final int mPositionDataSize = 3;

    /** Size of the normal data in elements. */
    private final int mNormalDataSize = 3;
    private final int mTextureDataSize = 2;
    private List<Material> materialList;
    private List<BuffersForRender> bufferList = new ArrayList<>();
    private List<FloatBuffer> buffer = new ArrayList<>();


    public GameItem(Context context, String model)
    {
        translationVector=new Vector3f();



        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("obj/" + model);
            Obj obj = ObjReader.read(is);

            List<String> mtlNames = obj.getMtlFileNames();
            String mtlFileName = mtlNames.get(0);

            obj = ObjUtils.convertToRenderable(obj);
            Map<String, Obj> materialGroups = ObjSplitting.splitByMaterialGroups(obj);

            InputStream mtlInputStream =am.open("mtl/"+mtlFileName);
            List<Mtl> mtls = MtlReader.read(mtlInputStream);


            for (Map.Entry<String, Obj> entry : materialGroups.entrySet())
            {
                String materialName = entry.getKey();
                Obj materialGroup = entry.getValue();

                Mtl mtl = findMtlForName(mtls, materialName);
                BuffersForRender currentBuffer = new BuffersForRender();
                currentBuffer.setIndexBuffer(ObjData.getFaceVertexIndices(materialGroup, 3));
                currentBuffer.setVertexBuffer(ObjData.getVertices(materialGroup));
                currentBuffer.setNormalBuffer(ObjData.getNormals(materialGroup));

                String fullPath = mtl.getMapKd();

                if (fullPath!=null) {

                    currentBuffer.setTextureBuffer(ObjData.getTexCoords(materialGroup,2));

                    int index = fullPath.lastIndexOf("\\");
                    String fileName = fullPath.substring(index + 1);
                    String[] parts = fileName.split("\\.");
                    Texture tex = new Texture(context, fileName);

                    currentBuffer.setMaterial(new Material(new Vector4f(mtl.getKa().getX(), mtl.getKa().getY(), mtl.getKa().getZ(), 1.0f),
                            new Vector4f(mtl.getKd().getX(), mtl.getKd().getY(), mtl.getKd().getZ(), 1.0f),
                            new Vector4f(mtl.getKs().getX(), mtl.getKs().getY(), mtl.getKs().getZ(), 1.0f), tex, 1f));


                }else{

                    currentBuffer.setMaterial(new Material (new Vector4f(mtl.getKa().getX(), mtl.getKa().getY(), mtl.getKa().getZ(), 1.0f),
                            new Vector4f(mtl.getKd().getX(), mtl.getKd().getY(), mtl.getKd().getZ(), 1.0f),
                            new Vector4f(mtl.getKs().getX(), mtl.getKs().getY(), mtl.getKs().getZ(), 1.0f),null,1f));

                }

                bufferList.add(currentBuffer);

            }

        }catch (Exception excp) {
            excp.printStackTrace();
        }


    }

    public void loadMaterial(Material material)
    {
        this.material=material;

    }
    public void mainRender(ShaderProgram program)
    {
        int count=0;

        for (BuffersForRender currentBuffer : bufferList) {
            count++;


            System.out.println("Count: " + count);
            int translatedPositionHandle = GLES20.glGetUniformLocation(program.getsProgram(), "translationVector");
            GLES20.glUniform3f(translatedPositionHandle, translationVector.x, translationVector.y,translationVector.z);

                GLES20.glUniform4f(program.getAmbientHandler(), currentBuffer.material.getAmbientColour().x, currentBuffer.material.getAmbientColour().y, currentBuffer.material.getAmbientColour().z, currentBuffer.material.getAmbientColour().w);
                GLES20.glUniform4f(program.getDifuseHandler(), currentBuffer.material.getDiffuseColour().x, currentBuffer.material.getDiffuseColour().y, currentBuffer.material.getDiffuseColour().z, currentBuffer.material.getDiffuseColour().w);
                GLES20.glUniform4f(program.getSpecularHandler(), currentBuffer.material.getAmbientColour().x, currentBuffer.material.getAmbientColour().y, currentBuffer.material.getAmbientColour().z, currentBuffer.material.getAmbientColour().w);
                GLES20.glUniform1f(program.getReflectanceHandler(), currentBuffer.material.getReflectance());
                GLES20.glUniform1i(program.getHasTextureHandle(), currentBuffer.material.isTextured() ? 1 : 0);
            System.out.println("Count: " + currentBuffer.material.getDiffuseColour().y);
                currentBuffer.vertexBuffer.position(0);
                GLES20.glVertexAttribPointer(program.getVertexHandle(), mPositionDataSize, GLES20.GL_FLOAT, false, 0, currentBuffer.vertexBuffer);
                GLES20.glEnableVertexAttribArray(program.getVertexHandle());

                currentBuffer.normalBuffer.position(0);
                GLES20.glVertexAttribPointer(program.getNormalHandle(), mNormalDataSize, GLES20.GL_FLOAT, false, 0, currentBuffer.normalBuffer);
                GLES20.glEnableVertexAttribArray(program.getNormalHandle());

                if (currentBuffer.material.isTextured()) {
                    System.out.println("!");
                    currentBuffer.textureBuffer.position(0);
                    GLES20.glVertexAttribPointer(program.getTextureHandle(), mTextureDataSize, GLES20.GL_FLOAT, false, 0, currentBuffer.textureBuffer);
                    GLES20.glEnableVertexAttribArray(program.getTextureHandle());
                }

                if (currentBuffer.material.isTextured()) {
                System.out.println("!!!!!!!!!!!");
                    currentBuffer.material.getTexture().bindTexture();
                }
                currentBuffer.indexBuffer.position(0);

                GLES20.glDrawElements(GLES20.GL_TRIANGLES, currentBuffer.indexBuffer.capacity(), GLES20.GL_UNSIGNED_INT, currentBuffer.indexBuffer);

            }

    }
    private static Mtl findMtlForName(Iterable<? extends Mtl> mtls, String name)
    {
        for (Mtl mtl : mtls)
        {
            if (mtl.getName().equals(name))
            {
                return mtl;
            }
        }
        return null;
    }

    public void setTranslationVector(Vector3f translationVector) {
        this.translationVector = translationVector;
    }

    public Vector3f getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(float x, float y, float z) {
        translationVector = new Vector3f(x,y,z);
    }

    private class BuffersForRender
    {

        public IntBuffer indexBuffer;
        public FloatBuffer vertexBuffer;
        public FloatBuffer normalBuffer;
        public FloatBuffer textureBuffer;
        public Material material;

        public void setIndexBuffer(IntBuffer indexBuffer) {
            this.indexBuffer = indexBuffer;
        }

        public void setNormalBuffer(FloatBuffer normalBuffer) {
            this.normalBuffer = normalBuffer;
        }

        public void setTextureBuffer(FloatBuffer textureBuffer) { this.textureBuffer = textureBuffer;  }

        public void setVertexBuffer(FloatBuffer vertexBuffer) {
            this.vertexBuffer = vertexBuffer;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }



        public Material getMaterial() {
            return material;
        }
    }

}
