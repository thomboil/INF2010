package tp1;

import java.awt.*;

public class Point2d extends AbstractPoint {
    private final Integer X = 0;
    private final Integer Y = 1;

    // TODO creer un point en 2d avec 2 donnees
    public Point2d(Double x, Double y) {

        super(new Double[]{x,y});
    }
    //okok
    // TODO creer un point a partir d'un vecteur de donnees
    public Point2d(Double[] vector) {
        super(new Double[]{vector[0], vector[1]});
    }

    public Double X() { return vector[X];}
    public Double Y() { return vector[Y];}

    // TODO prendre un vecteur de donnees et appliquer la translation.
    @Override
    public Point2d translate(Double[] translateVector) {

        Point2d newPoint = new Point2d(PointOperator.translate(this.vector, translateVector));
        return newPoint;
    }

    // TODO prendre un point et appliquer la translation.
    public Point2d translate(Point2d translateVector) {
        return null;
    }

    // TODO prendre un vecteur de donnees et appliquer la translation.
    @Override
    public Point2d rotate(Double[][] rotationMatrix) {
        return null;
    }

    // TODO prendre un angle de rotation, creer une matrice et appliquer la rotation.
    public Point2d rotate(Double angle) {

        Double rotationMatrix[][] = new Double[1][1];
        //rotationMatrix[0][0] = cos(angle);
        //rotationMatrix[0][1] = sin(angle);
        //rotationMatrix[1][0] = -sin(angle);
        //rotationMatrix[1][1] = cos(angle);
        // PointOperator.rotate(this.vector, rotationMatrix);

        return null;
    }

    // TODO prendre un facteur de division et l'appliquer.
    @Override
    public Point2d divide(Double divider) {
        return null;
    }

    // TODO prendre un facteur de multiplication et l'appliquer.
    @Override
    public Point2d multiply(Double multiplier) {
        return null;
    }

    // TODO prendre un facteur d'addition et l'appliquer.
    @Override
    public Point2d add(Double adder) {
        return null;
    }

    // TODO creer un nouveau point.
    @Override
    public Point2d clone() {
        Point2d pointClone = new Point2d(this.vector);
        return pointClone;
    }
}
