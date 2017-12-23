import static java.lang.System.out;

public class ClassAltered {

    public static void main(String[] args) {
        String upper = upper("Piotr Czyzak");
        out.println(upper + " = " + count(upper));
    }

    private static String upper(String s) {
        String aux = s.toUpperCase();
        log("Nazwa metody: upper, Return: aux");
        return aux;
    }

    public static int count(String s) {
        int length = s.length();
        log("Nazwa metody: count, Return: length");
        return length;
    }

    private static void log(String str) {
        out.println(str);
    }
}
