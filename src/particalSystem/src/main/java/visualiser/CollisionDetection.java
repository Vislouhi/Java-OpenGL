package visualiser;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CollisionDetection {
	
	
	public CollisionDetection(Triangle triangle1,Triangle triangle2)
	{
		
		Vector3f s = new Vector3f(triangle2.q).sub(triangle2.p);
		Vector3f t = new Vector3f(triangle2.r).sub(triangle2.p);
		Vector3f u = new Vector3f(triangle1.q).sub(triangle1.p);
		Vector3f v = new Vector3f(triangle1.r).sub(triangle1.p);
		Vector3f ap = new Vector3f(triangle2.p).sub(triangle1.p);
		
		
		float delta = u.dot(v);
		delta*=delta;
		Vector3f alpha = new Vector3f(s).mul(u.dot(v)/delta);
		Vector3f beta = new Vector3f(t).mul(u.dot(v)/delta);
		Vector3f gamma = new Vector3f(ap).mul(u.dot(v)/delta);
		System.out.println(gamma.dot(u));
		System.out.println(gamma.dot(u));
		/*
		System.out.println("triangle= "+triangle2.p.x);
		Matrix4f collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.q.x,triangle2.r.x,triangle2.p.x,
				triangle1.p.y,triangle1.q.y,triangle2.r.y,triangle2.p.y,
				triangle1.p.z,triangle1.q.z,triangle2.r.z,triangle2.p.z,
				1f,1f,1f,1f);
		//collisionMatrix.transpose();
		System.out.println("firstStep = "+collisionMatrix.determinant());
		if(collisionMatrix.determinant()>0f) {
			collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.r.x,triangle2.r.x,triangle2.p.x,
					triangle1.p.y,triangle1.r.y,triangle2.r.y,triangle2.p.y,
					triangle1.p.z,triangle1.r.z,triangle2.r.z,triangle2.p.z,
					1f,1f,1f,1f);
			//collisionMatrix.transpose();
			if(collisionMatrix.determinant()>0f) {
				System.out.println("no1"+collisionMatrix.determinant());
			}else {
				collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.r.x,triangle2.q.x,triangle2.p.x,
						triangle1.p.y,triangle1.r.y,triangle2.q.y,triangle2.p.y,
						triangle1.p.z,triangle1.r.z,triangle2.q.z,triangle2.p.z,
						1f,1f,1f,1f);
				//collisionMatrix.transpose();
				if(collisionMatrix.determinant()>0f) {
					System.out.println("yes2"+collisionMatrix.determinant());
				}else {
					System.out.println("no3"+collisionMatrix.determinant());
				}
			}
			
			
		}else {
			collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.q.x,triangle2.q.x,triangle2.p.x,
					triangle1.p.y,triangle1.q.y,triangle2.q.y,triangle2.p.y,
					triangle1.p.z,triangle1.q.z,triangle2.q.z,triangle2.p.z,
					1f,1f,1f,1f);
			//collisionMatrix.transpose();
			System.out.println("secondStep = "+collisionMatrix.determinant());
			if(collisionMatrix.determinant()<0f) {
				System.out.println("no4"+collisionMatrix.determinant());
			}else {
				collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.r.x,triangle2.q.x,triangle2.p.x,
						triangle1.p.y,triangle1.r.y,triangle2.q.y,triangle2.p.y,
						triangle1.p.z,triangle1.r.z,triangle2.q.z,triangle2.p.z,
						1f,1f,1f,1f);
				//collisionMatrix.transpose();
				if(collisionMatrix.determinant()<0f) {
					System.out.println("yes5"+collisionMatrix.determinant());
					
				}else
				{
					System.out.println("no6"+collisionMatrix.determinant());
				}
		}
	}	
		//System.out.println(collisionMatrix.determinant());
		
		
		collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.q.x,triangle2.p.x,triangle2.q.x,
				triangle1.p.y,triangle1.q.y,triangle2.p.y,triangle2.q.y,
				triangle1.p.z,triangle1.q.z,triangle2.p.z,triangle2.q.z,
				1f,1f,1f,1f);
		if(collisionMatrix.determinant()>0f) {
			System.out.println("no7"+collisionMatrix.determinant());
			
		}else
		{
			System.out.println("yes8"+collisionMatrix.determinant());
			collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.r.x,triangle2.r.x,triangle2.p.x,
					triangle1.p.y,triangle1.r.y,triangle2.r.y,triangle2.p.y,
					triangle1.p.z,triangle1.r.z,triangle2.r.z,triangle2.p.z,
					1f,1f,1f,1f);
			if(collisionMatrix.determinant()>0f) {
				System.out.println("yes9"+collisionMatrix.determinant());
				
			}else
			{
				System.out.println("no10 "+collisionMatrix.determinant());
				collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.r.x,triangle2.q.x,triangle2.p.x,
						triangle1.p.y,triangle1.r.y,triangle2.q.y,triangle2.p.y,
						triangle1.p.z,triangle1.r.z,triangle2.q.z,triangle2.p.z,
						1f,1f,1f,1f);
				if(collisionMatrix.determinant()>0f) {
					System.out.println("yes11"+collisionMatrix.determinant());
					collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.q.x,triangle2.r.x,triangle2.p.x,
							triangle1.p.y,triangle1.q.y,triangle2.r.y,triangle2.p.y,
							triangle1.p.z,triangle1.q.z,triangle2.r.z,triangle2.p.z,
							1f,1f,1f,1f);
					if(collisionMatrix.determinant()<0f) {
						System.out.println("yes13"+collisionMatrix.determinant());
						
					}else
					{
						System.out.println("no14"+collisionMatrix.determinant());
					}
					
				}else
				{
					System.out.println("no12"+collisionMatrix.determinant());
					collisionMatrix = new Matrix4f(triangle1.p.x,triangle1.q.x,triangle2.r.x,triangle2.p.x,
							triangle1.p.y,triangle1.q.y,triangle2.r.y,triangle2.p.y,
							triangle1.p.z,triangle1.q.z,triangle2.r.z,triangle2.p.z,
							1f,1f,1f,1f);
					if(collisionMatrix.determinant()<0f) {
						System.out.println("yes15"+collisionMatrix.determinant());
						
					}else
					{
						System.out.println("no16"+collisionMatrix.determinant());
					}
				}
			}
			
		}
	
	
*/
	}
}
