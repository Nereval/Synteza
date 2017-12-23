import static java.lang.System.out;

public class Class {

    public static void main(String[] args) {
        String upper = upper("Piotr Czyzak");
        out.println(upper + " = " + count(upper));
    }

    private static String upper(String s) {
        String aux = s.toUpperCase();
        return aux;
    }

    public static int count(String s) {
        int length = s.length();
        return length;
    }

    private static void log(String str) {
        out.println(str);
    }
}
