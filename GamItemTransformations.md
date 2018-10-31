# Геометрические преобразования


Класс GameItem должен иметь в себе следующие переменные:

Для параллельного переноса

Vector3f position;

Для вращения около заданной точки, вокруг заданной оси:

Vector3f rotationPoint;

Vector3f rotationAxis;

float rotationAngle;

Зная эти праметры генерируются матрицы параллельного переноса поворота.

Для параллельного переноса

Matrix4f transMatrix= new Matrix4f().identity.translate(position);

Для поворота

Matrix4f rotMatrix= new Matrix4f().identity.translate(rotationPoint).rotation(rotationAngle,rotationAxis).translate(new Vector3f(rotationPoint).mul(-1f));

В шейдере должны быть переменные uniform для этих матриц.

Позиции точек в шейдере должны в первую очередь домножаться на эти матрицы.

Координаты нормалей домножаются только на матрицу поворота.

То же должно происходить в шейдере теней.

