package src.code;
import java.util.Arrays;

public class Box {
    int[] dimensoes;
    private static int idContador = 0;
    private int id;

    public Box(int dim1, int dim2, int dim3) {
        this.dimensoes = new int[]{dim1, dim2, dim3};
        Arrays.sort(this.dimensoes);
        this.id = idContador++;
    }

    @Override
    public String toString() {
        //return Arrays.toString(dimensoes);
        return id + " ";
    }

    public int[] getDimensoes() {
        return dimensoes;
    }

    

    public int getId() {
        return id;
    }

    public boolean caixaCabe(Box v) {
        if(
            dimensoes[0] < v.dimensoes[0] &&
            dimensoes[1] < v.dimensoes[1] &&
            dimensoes[2] < v.dimensoes[2]
        ) {
            return true;
        } else {
            return false;
        }
    }
}