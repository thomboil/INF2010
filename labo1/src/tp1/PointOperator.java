package tp1;

import java.util.*;
import java.util.Collection;
import java.util.Iterator;

public final class PointOperator {
    // TODO appliquer la translation sur le vecteur d'entree.
    public static Double[] translate(Double[] vector, Double[] translateVector) {

        for(int i=0; i < vector.length; i++)
        {
            vector[i] += translateVector[i];
        }

        return vector;
    }
    //test git que juste java marche
    // TODO appliquer la rotation sur le vecteur d'entree.
    public static Double[] rotate(Double[] vector, Double[][] rotationMatrix) {

        Double[] vectorResultant = new Double[vector.length];

        for(int i = 0; i<rotationMatrix.length; i++)
        {
            double sum = 0;
            for(int j = 0; j<rotationMatrix[i].length; j++)
            {
                sum += rotationMatrix[i][j] * vector[j];
            }
            vectorResultant[i] = sum;

        }
        return vectorResultant;
    }

    // TODO appliquer le facteur de division sur le vecteur d'entree.
    public static Double[] divide(Double[] vector, Double divider) {
        for(int i = 0 ; i<vector.length; i++)
        {
            double quotient = 0;
            quotient += vector[i] / divider;
            vector[i] = quotient;
        }
        return vector;
    }

    // TODO appliquer le facteur de multiplication sur le vecteur d'entree.
    public static Double[] multiply(Double[] vector, Double multiplier) {
        for(int i = 0 ; i<vector.length; i++)
        {
            double produit = 0;
            produit += vector[i] * multiplier;
            vector[i] = produit;
        }
        return vector;
    }

    // TODO appliquer le facteur d'addition sur le vecteur d'entree.
    public static Double[] add(Double[] vector, Double adder) {
        for(int i = 0 ; i<vector.length; i++)
        {
            double sum = 0;
            sum += vector[i] + adder;
            vector[i] = sum;
        }
        return vector;
    }

    // TODO retourne la coordonnee avec les plus grandes valeurs en X et en Y.
    public static Point2d getMaxCoord(Collection<Point2d> coords) {
        Double coordX = 0.0;
        Double coordY = 0.0;
        for (Point2d point : coords) {
            if(point.X() > coordX)
            {
                coordX = point.X();
            }
            if(point.Y() > coordY)
            {
                coordY = point.Y();
            }
        }
        Point2d maxPoint = new Point2d(coordX, coordY);
        return maxPoint;
    }

    // TODO retourne la coordonnee avec les plus petites valeurs en X et en Y.
    public static Point2d getMinCoord(Collection<Point2d> coords) {
        Double coordX = 10000.0;
        Double coordY = 10000.0;
        for (Point2d point : coords) {
            if(point.X() < coordX)
            {
                coordX = point.X();
            }
            if(point.Y() < coordY)
            {
                coordY = point.Y();
            }
        }
        Point2d maxPoint = new Point2d(coordX, coordY);
        return maxPoint;
    }
}
