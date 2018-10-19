[staticScene.obj]

status=static

[ruchka.obj]

status=oneAdditionalPosition

rotationPoint1=0.3 3.8 0.0

rotationAxis1=0.3 3.8 0.0

rotationAngle1=0.3

actionTime=1.5

actoin0Id=razomknutCep

actoin1Id=zamknutCep

[strelkaAmpermetra.obj]

status=rotationVariable

rotationPoint=0.3 3.8 0.0

rotationAxis=0.3 3.8 0.0

rotationAngle1=0.3

rotationAngle2=2.3

onAction=razomknutCep
{
setValue=0
}
onAction=zamknutCep
{
setValue=resistance*0.5
} 
 
[krokodil.obj]

status=dragAndDrop

fromPoint=0.0 0.0 0.0

toPoint=0.3 3.8 4.0

value=resistance
 
 
 
 
