package visualiser;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.Version;
import org.lwjgl.system.MemoryUtil;

public class MeshFromBuffers {

	private final int vaoId;

    private final int posVboId;
    
    private final int normVboId;
    private final int textVboId;

    private final int idxVboId;
    
    //private final int textVboId;

    private final int vertexCount;

  //���� ����� ��������� � ����������� ������ �� ������� � ������� � ������
    public MeshFromBuffers(FloatBuffer posBuffer, IntBuffer indicesBuffer,FloatBuffer normBuffer, FloatBuffer textsBuf  ) {
    	//Vertex Array Object -  VAO - ������, ������� �������� ����� Vertex Buffer Object
    	//Vertex Buffer Object -  VBO - ����� ������ � �������
        
        try {      	
            vertexCount = indicesBuffer.capacity();
            //������������ ��������� �� VAO
            vaoId = glGenVertexArrays();
            //������������ VAO � �������� vaoId
            glBindVertexArray(vaoId);

          //������������ ��������� �� VBO  � ������������ ������
            posVboId = glGenBuffers();
            //������������ VBO � �������� posVboId
            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
          //� ������������ VBO ����������� ������ �� posBufer
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            //�����������, ��� ������ ����� ������������ � ������ � ���������� � location = 0
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

          //������������ ��������� �� VBO c ������������������� ��������� ������
            idxVboId = glGenBuffers();
            
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            
          //������������ ��������� �� VBO � ������������ �������� ��������
            normVboId = glGenBuffers();
            
            glBindBuffer(GL_ARRAY_BUFFER, normVboId);
            glBufferData(GL_ARRAY_BUFFER, normBuffer, GL_STATIC_DRAW);
           
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
            
            textVboId = glGenBuffers();
            
            glBindBuffer(GL_ARRAY_BUFFER, textVboId);
            glBufferData(GL_ARRAY_BUFFER, textsBuf, GL_STATIC_DRAW);
          
           
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            
           /* textVboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, textVboId);
            glBufferData(GL_ARRAY_BUFFER, textBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);*/
            
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
            
        } finally {
           /* if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }*/
        }
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(2);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVboId);
        glDeleteBuffers(idxVboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
