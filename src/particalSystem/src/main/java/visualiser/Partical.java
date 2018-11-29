package visualiser;
import org.joml.Vector3f;
public class Partical {
	 

		 	private float x;
			private float y;
			private float z;
			private Vector3f u;
			private int ttl;
			private Vector3f position;
			
			//private boolean died=false;
		//¬вести переменную дл€ скорости частицы
			public Partical(float x,float y,float z, Vector3f u)
			{//¬ конструктор добавить определение скорости
				this.x=	x;
				this.y=	y;
				this.z=	z;
				this.u=	u;
				this.ttl=100;
			
			}
			
			public Partical( Vector3f position, Vector3f u, float razbros, int ttl)
			{//¬ конструктор добавить определение скорости
				
		
				this.x=	position.x+razbros*(float)Math.random();
				this.y=	position.y+razbros*(float)Math.random();
				this.z=	position.z+razbros*(float)Math.random();
				this.u=	u;
				this.ttl=ttl;
			
			}
			
			public boolean updatePartical() {
			//ћен€ть положение частицы в соответствии с ее скоростью
			ttl--;
			this.x=this.x+u.x+(0.5f-(float)Math.random())*0.25f;
			this.y=this.y+u.y;
			this.z=this.z+u.z+(0.25f-(float)Math.random());
			if(ttl==0) {
				return true;
				
			}
			else
			{
				return false;
						}
			
			}
		
			public void setPosition(float x,float y,float z)
			{
				this.x=x;
				this.y=y;
				this.z=z;	
			}
			
		
			public  Vector3f getPosition()
			{
				return new Vector3f(this.x,this.y,this.z);	
			}


		}
